package com.dom.freeman.obj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvBindByPosition;

public class User {

	@CsvBindByPosition(position = 0)
	private String firstName;
	
	@CsvBindByPosition(position = 1)
	private String lastName;
	
	@CsvBindByPosition(position = 2)
	private String displayName;
	
	@CsvBindByPosition(position = 3)
	private String id;
	
	@CsvBindByPosition(position = 4)
	private List<UserPermission> grantedPermissions;
	
	public User(String firstName, String lastName, String displayName,
			String id, UserPermission... grantedPermissions) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.displayName = displayName;
		this.id = id;
		
		this.grantedPermissions = new ArrayList<>();
		this.grantedPermissions.addAll(Arrays.asList(grantedPermissions));
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
	
	public List<UserPermission> getUserPermissions() {
		return this.grantedPermissions;
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
	
	public void setUserPermissions(List<UserPermission> permissions) {
		this.grantedPermissions = permissions;
	}
	public boolean hasPermission(UserPermission permission) {
		return this.grantedPermissions.contains(permission);
	}
	
	public boolean grantPermission(UserPermission grant) {
		
		if (!this.grantedPermissions.contains(grant)) {
			this.grantedPermissions.add(grant);
			return true;
		}
		return false;
	}
	
	public boolean revokePermission(UserPermission revoke) {
		return this.grantedPermissions.remove(revoke);
	}
	
	public String[] toCsvString() {
		
		StringBuilder permissions = new StringBuilder();
		for (int i = 0; i < this.grantedPermissions.size(); i++) {
			permissions.append(this.grantedPermissions.get(i));
			if (i < this.grantedPermissions.size() - 1)
				permissions.append(";");
		}
		
		return new String[] {
				this.getFirstName(),
				this.getLastName(),
				this.getDisplayName(),
				this.getId(),
				permissions.toString()
		};
	}
}
