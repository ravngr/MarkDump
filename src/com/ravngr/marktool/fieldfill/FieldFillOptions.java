package com.ravngr.marktool.fieldfill;

import java.io.File;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.ravngr.marktool.util.CellAddress;
import com.ravngr.marktool.util.CellAddressConverter;

public class FieldFillOptions {
	@Parameter(description="Target .xls/.xlsx file(s)")
	private List<File> targetFile;
	
	@Parameter(names="-t", description="Target field", converter=CellAddressConverter.class)
	private CellAddress targetField;
	
	@Parameter(names="-v", description="Value")
	private String value;
	
	@Parameter(names={"-usage", "-help"}, description="Display usage information.")
	private boolean help = false;

	public boolean isHelp() {
		return help;
	}
	
	public List<File> getTargetFiles() {
		return targetFile;
	}

	public CellAddress getTargetField() {
		return targetField;
	}

	public String getValue() {
		return value;
	}
}
