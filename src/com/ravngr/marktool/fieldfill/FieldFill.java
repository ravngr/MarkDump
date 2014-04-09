package com.ravngr.marktool.fieldfill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class FieldFill {
	private FieldFillOptions options;
	
	public FieldFill(FieldFillOptions options) {
		this.options = options;
	}
	
	public void run() {
		for (File target : options.getTargetFiles())
			processFile(target);
	}
	
	public void processFile(File file) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
			//Workbook wb = WorkbookFactory.create(file);
			
			Sheet sheet = wb.getSheetAt(0);
			
			Cell targetCell = options.getTargetField().getCell(sheet);
			
			targetCell.setCellType(Cell.CELL_TYPE_STRING);
			targetCell.setCellValue(options.getValue());
			
			FileOutputStream fs = new FileOutputStream(file);
			
			wb.write(fs);
			fs.close();
		//} catch (InvalidFormatException e) {
		//	System.err.println("Skipping " + file.getAbsolutePath() + " due to bad file format. " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Skipping " + file.getAbsolutePath() + " file could not be opened. " + e.getMessage());
		}
	}
	
	public static void main(String [] args) {
		FieldFillOptions options = new FieldFillOptions();
		
		JCommander jcom = new JCommander(options);
		
		// Parse command line variables
		try {
			jcom.parse(args);
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		if (options.getTargetFiles() == null || options.isHelp()) {
			jcom.usage();
			return;
		}
		
		FieldFill f = new FieldFill(options);
		f.run();
	}
}
