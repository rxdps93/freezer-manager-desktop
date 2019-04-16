package com.dom.freeman.obj.users;

public enum UserOperations {

	ADD_ITEM("Ability to add a new item to the inventory"),
	EDIT_ITEM("Ability to edit an existing item in the inventory"),
	REMOVE_ITEM("Ability to remove an existing item from the inventory"),
	
	ADD_ITEM_TAG("Ability to create a new item tag"),
	EDIT_ITEM_TAG("Ability to edit an existing item tag"),
	REMOVE_ITEM_TAG("Ability to remove an existing item tag"),
	
	ASSIGN_ITEM_TAG("Ability to associate an item and item tag"),
	UNASSIGN_ITEM_TAG("Ability to unassociate an item and item tag"),
	
	ADD_PERMANENT_TAG("Ability to create a new permanent tag"),
	EDIT_PERMANENT_TAG("Ability to edit an existing permanent tag"),
	REMOVE_PERMANENT_TAG("Ability to remove an existing permanent tag"),
	
	ADD_USER("Ability to add a new user to the system"),
	EDIT_USER("Ability to edit an existing user"),
	REMOVE_USER("Ability to remove an existing user"),
	
	GRANT_USER_PERMISSION("Ability to grant a different user's permissions"),
	REVOKE_USER_PERMISSION("Ability to revoke permissions from a different user"),
	
	MODIFY_SELF_PERMISSION("Ability to grant oneself permissions"),
	
	SUSPEND_VIEW_PERMISSION("Ability to suspend a user's ability to view the inventory"),
	
	VIEW_TRANSACTIONS_SELF("Ability to see a history of your actions in the system"),
	VIEW_TRANSACTIONS_ALL("Ability to see a history of all users' actions in the system"),
	
	VIEW("Ability to view the inventory");
	
	private String description;
	
	private UserOperations(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
