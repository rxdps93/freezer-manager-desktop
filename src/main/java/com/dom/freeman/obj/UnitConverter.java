package com.dom.freeman.obj;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class UnitConverter extends AbstractBeanField<Unit> {

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		Unit unit = Unit.ERROR;
		
		for (Unit u : Unit.values()) {
			if (value.equals(u.getPluralAbbreviation()) || value.equals(u.getSingularAbbreviation())) {
				unit = u;
				break;
			}
		}
		
		return unit;
	}

}
