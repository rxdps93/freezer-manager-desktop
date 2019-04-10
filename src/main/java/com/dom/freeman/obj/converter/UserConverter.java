package com.dom.freeman.obj.converter;

import java.util.ArrayList;
import java.util.List;

import com.dom.freeman.obj.UserPermission;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class UserConverter extends AbstractBeanField<List<UserPermission>> {

	@Override
	protected ArrayList<UserPermission> convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		
		ArrayList<UserPermission> permissions = new ArrayList<>();
		
		if (!value.isEmpty()) {
			for (String permission : value.split(";")) {
				permissions.add(UserPermission.valueOf(permission));
			}
		}
		
		return permissions;
	}

}
