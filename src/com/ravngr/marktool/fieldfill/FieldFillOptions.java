package com.ravngr.marktool.fieldfill;

import java.io.File;

import com.beust.jcommander.Parameter;
import com.ravngr.marktool.util.CellAddress;

public class FieldFillOptions {
	@Parameter(description="Target .xls/.xlsx file")
	private File targetFile;
	
	@Parameter(description="Field")
	private CellAddress targetField;
	
	@Parameter(description="Value")
	private String value;

	public File getTargetFile() {
		return targetFile;
	}

	public CellAddress getTargetField() {
		return targetField;
	}

	public String getValue() {
		return value;
	}
}
