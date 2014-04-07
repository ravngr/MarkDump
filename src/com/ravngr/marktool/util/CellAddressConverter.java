package com.ravngr.marktool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.ParameterException;

public class CellAddressConverter implements IStringConverter<CellAddress> {

	@Override
	public CellAddress convert(String value) {
		Pattern p = Pattern.compile(("^([A-Za-z]+)([0-9]+)$"));
		Matcher m = p.matcher(value);
		
		if (!m.matches())
			throw new ParameterException("Invalid cell address!");

		String rowStr = m.group(2);
		String columnStr = m.group(1).toUpperCase();
		
		int row = Integer.parseInt(rowStr) - 1;
		int column = 0;
		
		if (row < 0)
			throw new ParameterException("Invalid cell address!");
		
		final String lt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		for (int base = 0, i = (columnStr.length() - 1); i >= 0; base++, i--) {
			int n = lt.indexOf(columnStr.charAt(i));
			
			column += n * Math.pow(lt.length(), base);
		}
		
		return new CellAddress(row, column);
	}

}
