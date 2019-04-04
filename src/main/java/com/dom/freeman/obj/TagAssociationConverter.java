package com.dom.freeman.obj;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class TagAssociationConverter extends AbstractBeanField<List<String>> {

	@Override
	protected List<String> convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		
		List<String> ids;
		
		if (value.isEmpty())
			ids = Collections.emptyList();
		else
			ids = Arrays.asList(value.split(";"));
		
		return ids;
	}

}
