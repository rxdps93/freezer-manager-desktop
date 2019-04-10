package com.dom.freeman.obj.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class LocalDateConverter extends AbstractBeanField<LocalDate> {
	
	private LocalDate convertRelativeDate(String value) {
		
		LocalDate date = LocalDate.now();
		if (!value.equals("TODAY")) {
			char diff = value.charAt(0);
			long amnt = Long.parseLong(value.substring(2, value.length() - 2));
			char unit = value.charAt(value.length() - 1);
			
			if (diff == 'P') {
				
				switch(unit) {
				case 'D':
					date = date.plusDays(amnt);
					break;
				case 'W':
					date = date.plusWeeks(amnt);
					break;
				case 'M':
					date = date.plusMonths(amnt);
				}
				
			} else {
				
				switch(unit) {
				case 'D':
					date = date.minusDays(amnt);
					break;
				case 'W':
					date = date.minusWeeks(amnt);
					break;
				case 'M':
					date = date.minusMonths(amnt);
				}
				
			}
		}
		
		return date;
	}
	
	@Override
	protected LocalDate convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		LocalDate parse;
		if (!value.contains("/"))
			parse = convertRelativeDate(value);
		else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        	parse = LocalDate.parse(value, formatter);
		}
        return parse;
	}
}
