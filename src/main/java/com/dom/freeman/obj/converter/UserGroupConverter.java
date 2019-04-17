package com.dom.freeman.obj.converter;

import com.dom.freeman.obj.users.UserGroup;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class UserGroupConverter extends AbstractBeanField<UserGroup> {

	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		
		UserGroup group = UserGroup.USER;
		
		for (UserGroup g : UserGroup.values()) {
			if (value.equalsIgnoreCase(g.toString())) {
				group = g;
				break;
			}
		}
		return group;
	}
}
