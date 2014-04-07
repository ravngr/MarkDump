package com.ravngr.marktool.markdump;

import java.io.File;
import java.util.List;

import com.beust.jcommander.Parameter;
import com.ravngr.marktool.util.CellAddress;
import com.ravngr.marktool.util.CellAddressConverter;
import com.ravngr.marktool.util.CellAddressListConverter;

public class MarkDumpOptions {
	@Parameter(description="Comma separated list of directories or files containing marks (.xls/.xlsx)")
	private List<File> markingList;
	
	@Parameter(names="-s", description="Cell containing student details", converter=CellAddressConverter.class)
	private CellAddress studentCell = null;
	
	@Parameter(names="-m", description="Cell(s) containing student marks", converter=CellAddressListConverter.class)
	private List<CellAddress> markCells;
	
	@Parameter(names="-f", description="Target CSV file name")
	private File file = null;
	
	@Parameter(names="-a", description="Add all mark cells together")
	private boolean add = false;
	
	@Parameter(names="-r", description="Round all marks to nearest integer")
	private boolean round = false;
	
	@Parameter(names="-q", description="Quiet mode, warnings are suppressed")
	private boolean quiet = false;
	
	@Parameter(names={"-usage", "-help"}, description="Display usage information.")
	private boolean help = false;

	public List<File> getMarkingList() {
		return markingList;
	}

	public CellAddress getStudentCell() {
		return studentCell;
	}

	public List<CellAddress> getMarkCells() {
		return markCells;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean isAdd() {
		return add;
	}
	
	public boolean isQuiet() {
		return quiet;
	}
	
	public boolean isRound() {
		return round;
	}

	public boolean isHelp() {
		return help;
	}
}
