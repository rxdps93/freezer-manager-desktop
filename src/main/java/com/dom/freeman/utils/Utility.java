package com.dom.freeman.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.OperationStatus;
import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserGroup;
import com.dom.freeman.obj.users.UserOperation;

public enum Utility {

	METHODS;

	public void refreshViews() {
		for (AbstractInventoryTable<?> table : Global.OBJECTS.getRegisteredTables())
			table.refresh();
	}

	public void updateInventory() {

		Global.OBJECTS.setInventory(FileIO.METHODS.parseItemsFromFile());
		Global.OBJECTS.setTypes(this.itemTypeCount(Global.OBJECTS.getInventory()));
		Global.OBJECTS.setItemTags(FileIO.METHODS.parseItemTagsFromFile());
		Global.OBJECTS.setUsers(FileIO.METHODS.parseUsersFromFile());
	}

	public Item getItemById(String id) {
		for (Item item : Global.OBJECTS.getInventory()) {
			if (item.getId().equals(id))
				return item;
		}
		return null;
	}
	
	public User getUserById(String id) {
		for (User user : Global.OBJECTS.getUsers()) {
			if (user.getId().equals(id))
				return user;
		}
		return null;
	}

	public ItemTag getItemTagByName(String name) {
		for (ItemTag tag : Global.OBJECTS.getItemTags()) {
			if (tag.getName().equalsIgnoreCase(name))
				return tag;
		}
		return null;
	}

	public List<ItemTag> getTagsByItem(Item item) {
		List<ItemTag> tags = new ArrayList<>();

		for (ItemTag tag : Global.OBJECTS.getItemTags()) {
			if (tag.isItemAssociated(item))
				tags.add(tag);
		}

		return tags;
	}

	public Map<String, Integer> itemTypeCount(List<Item> items) {

		Map<String, Integer> types = new TreeMap<String, Integer>();

		for (Item item : items) {

			if (!types.containsKey(item.getType()))
				types.put(item.getType(), 1);
			else
				types.replace(item.getType(), types.get(item.getType()) + 1);
		}

		return types;
	}
	
	public OperationResult editPermissionAuth(User targetUser) {
		
		OperationStatus authorized = OperationStatus.OPERATION_NOT_PERMITTED;
		
		if (Global.OBJECTS.getCurrentUser().equals(targetUser)) {
			if (Global.OBJECTS.getCurrentUser().hasPermission(UserOperation.MODIFY_SELF_PERMISSION))
				authorized = OperationStatus.OPERATION_PERMITTED;
		} else if (Global.OBJECTS.getCurrentUser().hasPermission(UserOperation.MODIFY_USER_PERMISSION)) {
			
			if (Global.OBJECTS.getCurrentUser().getUserGroup().compareTo(targetUser.getUserGroup()) < 0 || Global.OBJECTS.getCurrentUser().getUserGroup().equals(UserGroup.DEVELOPER)) {
				authorized = OperationStatus.OPERATION_PERMITTED;
			}
		}
		
		return new OperationResult(authorized);
	}
}
