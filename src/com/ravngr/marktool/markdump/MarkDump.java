package com.ravngr.marktool.markdump;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ravngr.marktool.util.CellAddress;

public class MarkDump {
	private MarkDumpOptions options;
	
	public MarkDump(MarkDumpOptions options) {
		this.options = options;
		
		PrintWriter stream;
		
		if (options.getFile() != null)
			try {
				stream = new PrintWriter(options.getFile());
			} catch (FileNotFoundException e) {
				System.err.println("ERROR: Failed to open specified CSV file");
				e.printStackTrace();
				return;
			}
		else
			stream = new PrintWriter(System.out, true);
		
		for (File target : options.getMarkingList())
			processMarks(stream, target);
		
		stream.flush();
		stream.close();
	}
	
	public void processMarks(PrintWriter stream, File file) {
		// Recurse into directories
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				processMarks(stream, new File(f.getAbsolutePath()));
			}
			
			return;
		}
		
		// Ignore files that don't exist or have the wrong extension
		if (!file.exists() || !(file.getName().toLowerCase().endsWith(".xls"))) {
			if (!options.isQuiet())
				System.err.println("Skipping " + file.getAbsolutePath());
			return;
		}
		
		try {
			Workbook wb = WorkbookFactory.create(file);
			
			// Assume first worksheet
			String studentID;
			
			Sheet sheet = wb.getSheetAt(0);
			
			if (options.getStudentCell() != null) {
				Cell studentCell = options.getStudentCell().getCell(sheet);
				
				if (studentCell.getCellType() != Cell.CELL_TYPE_STRING) {
					if (!options.isQuiet())
						System.err.println("Skipping " + file.getAbsolutePath() + " due to bad student name cell");
					return;
				}
				
				studentID = studentCell.getStringCellValue().trim();
			} else {
				studentID = file.getName();
				
				int n = studentID.lastIndexOf('.');
				
				if (n > 0)
					studentID = studentID.substring(0, studentID.lastIndexOf('.'));
			}
			
			String output = studentID;
			
			double sum = 0;
			
			for (CellAddress c : options.getMarkCells()) {
				Cell markCell = c.getCell(sheet);
				
				if (!(markCell.getCellType() != Cell.CELL_TYPE_FORMULA || markCell.getCellType() != Cell.CELL_TYPE_NUMERIC)) {
					if (!options.isQuiet())
						System.err.println("Skipping " + file.getAbsolutePath() + " due to bad mark cell");
					return;
				}
				
				double n = markCell.getNumericCellValue();
				
				if (options.isAdd()) {
					sum += n;
				} else {
					if (options.isRound())
						output += ", " + Math.round(n);
					else
						output += ", " + n;
				}
			}
			
			if (options.isAdd()) {
				if (options.isRound())
					output += ", " + Math.round(sum);
				else
					output += ", " + sum;
			}
			
			stream.println(output);
			//System.out.println(output);
		} catch (InvalidFormatException e) {
			if (!options.isQuiet())
				System.err.println("Skipping " + file.getAbsolutePath() + " due to bad file format. " + e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			if (!options.isQuiet())
				System.err.println("Skipping " + file.getAbsolutePath() + " file could not be opened. " + e.getMessage());
			//e.printStackTrace();
		}
	}
}
