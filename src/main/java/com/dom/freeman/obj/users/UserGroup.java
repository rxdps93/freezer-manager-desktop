package com.dom.freeman.obj.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dom.freeman.obj.users.UserOperation.*;

public enum UserGroup {

	DEVELOPER("This user is a developer of this application and is granted every possible permission.",
			UserOperation.values()),
	ADMINISTRATOR("This user is a trusted user who can perform administrative tasks within the system.",
			ADD_ITEM,
			EDIT_ITEM,
			REMOVE_ITEM,
			ADD_ITEM_TAG,
			EDIT_ITEM_TAG,
			ASSIGN_ITEM_TAG,
			UNASSIGN_ITEM_TAG,
			ADD_USER,
			EDIT_USER,
			MODIFY_USER_PERMISSION,
			SUSPEND_USER,
			VIEW_TRANSACTIONS_SELF,
			VIEW_TRANSACTIONS_OTHERS,
			VIEW),
	MAINTAINER("An enhanced user with basic inventory access, but with some additional operations to help maintain the system.",
			ADD_ITEM,
			EDIT_ITEM,
			REMOVE_ITEM,
			ASSIGN_ITEM_TAG,
			UNASSIGN_ITEM_TAG,
			MODIFY_USER_PERMISSION,
			VIEW_TRANSACTIONS_SELF,
			VIEW_TRANSACTIONS_OTHERS,
			VIEW),
	ENHANCED_USER("A regular user with some additional operations.",
			ADD_ITEM,
			ASSIGN_ITEM_TAG,
			UNASSIGN_ITEM_TAG,
			VIEW_TRANSACTIONS_SELF,
			VIEW),
	USER("A regular user who can simply view the inventory's contents.",
			VIEW),
	SUSPENDED("This user's access to the application has been suspended.");

	private String groupDescription;
	private List<UserOperation> permissions = new ArrayList<>();

	private UserGroup(String msg, UserOperation... groupPermissions) {
		this.groupDescription = msg;
		this.permissions.addAll(Arrays.asList(groupPermissions));
	}

	public List<UserOperation> getPermissions() {
		return this.permissions;
	}

	public boolean hasPermission(UserOperation permission) {
		return this.permissions.contains(permission);
	}

	public String getGroupDescription() {
		return this.groupDescription;
	}
}
