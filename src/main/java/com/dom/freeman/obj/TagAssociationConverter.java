package com.dom.freeman.obj;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class TagAssociationConverter extends AbstractBeanField<List<String>> {

	@Override
	protected ArrayList<String> convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		
		ArrayList<String> ids = new ArrayList<>();
		
		if (!value.isEmpty()) {
			for (String id : value.split(";")) {
				ids.add(id);
			}
		}
		
		return ids;
	}

}
