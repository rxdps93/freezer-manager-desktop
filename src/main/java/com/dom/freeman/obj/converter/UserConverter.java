package com.dom.freeman.obj.converter;

import java.util.ArrayList;
import java.util.List;

import com.dom.freeman.obj.users.UserOperations;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class UserConverter extends AbstractBeanField<List<UserOperations>> {

	@Override
	protected ArrayList<UserOperations> convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		
		ArrayList<UserOperations> permissions = new ArrayList<>();
		
		if (!value.isEmpty()) {
			for (String permission : value.split(";")) {
				permissions.add(UserOperations.valueOf(permission));
			}
		}
		
		return permissions;
	}

}
