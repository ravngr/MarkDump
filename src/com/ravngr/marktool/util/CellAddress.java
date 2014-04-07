package com.ravngr.marktool.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

public class CellAddress {
	private int row;
	private int column;
	
	public CellAddress(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Cell getCell(Sheet sheet) {
		return sheet.getRow(row).getCell(column);
	}
}
