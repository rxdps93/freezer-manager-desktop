package com.dom.freeman.obj.converter;

import com.dom.freeman.obj.Freezer;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class FreezerConverter extends AbstractBeanField<Freezer> {

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		Freezer freezer = Freezer.ERROR;
		
		for (Freezer f : Freezer.values()) {
			if (value.equalsIgnoreCase(f.getFreezerLocation())) {
				freezer = f;
				break;
			}
		}
		
		return freezer;
	}

}
