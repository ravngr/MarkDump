package com.ravngr.marktool;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.ravngr.marktool.markdump.MarkDumpOptions;

public class MarkTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MarkDumpOptions options = new MarkDumpOptions();
		
		JCommander jcom = new JCommander(options);
		
		// Parse command line variables
		try {
			jcom.parse(args);
		} catch (ParameterException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		if (options.getMarkingList() == null || options.isHelp()) {
			jcom.usage();
			return;
		}
	}

}
