package com.ravngr.marktool.util;

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.IStringConverter;

public class CellAddressListConverter implements IStringConverter<List<CellAddress>> {

	@Override
	public List<CellAddress> convert(String value) {
		CellAddressConverter converter = new CellAddressConverter();
		List<CellAddress> list = new ArrayList<CellAddress>();
		
		for (String a : value.split(",")) {
			CellAddress ca = converter.convert(a);
			list.add(ca);
		}
		
		return list;
	}

}
