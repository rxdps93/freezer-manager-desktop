package com.dom.freeman.obj.users;

import com.dom.freeman.obj.converter.UserGroupConverter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

public class User {

	@CsvBindByPosition(position = 0)
	private String firstName;
	
	@CsvBindByPosition(position = 1)
	private String lastName;
	
	@CsvBindByPosition(position = 2)
	private String displayName;
	
	@CsvBindByPosition(position = 3)
	private String id;
	
	@CsvCustomBindByPosition(position = 4, converter = UserGroupConverter.class)
	private UserGroup group;
	
	public User(String firstName, String lastName, String displayName,
			String id, UserGroup group) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.id = id;
		this.group = group;
	}
	
	public User() {
		
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public String getId() {
		return this.id;
	}
	
	public UserGroup getUserGroup() {
		return this.group;
	}
	
	public boolean isSuspended() {
		return this.getUserGroup().equals(UserGroup.SUSPENDED);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setUserGroup(UserGroup group) {
		this.group = group;
	}
	
	public boolean hasPermission(UserOperation permission) {
		
		if (permission.equals(UserOperation.VIEW))
			return this.group.hasPermission(permission) && !this.isSuspended();
			
		return this.group.hasPermission(permission);
	}
	
	public String[] toCsvString() {
		
		return new String[] {
				this.getFirstName(),
				this.getLastName(),
				this.getDisplayName(),
				this.getId(),
				this.group.toString(),
				Boolean.toString(this.isSuspended())
		};
	}
}
