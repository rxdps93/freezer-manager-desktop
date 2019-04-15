package com.dom.freeman.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dom.freeman.obj.FileIOResult;
import com.dom.freeman.obj.FileIOStatus;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserOperations;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public enum FileIO {

	METHODS;

	public List<Item> parseItemsFromFile() {

		List<Item> items = Collections.emptyList();
		try {

			CsvToBean<Item> csv = new CsvToBeanBuilder<Item>(Files.newBufferedReader(Paths.get(Global.OBJECTS.getMainPath())))
					.withType(Item.class).build();

			items = csv.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return items;
	}

	public List<ItemTag> parseItemTagsFromFile() {

		List<ItemTag> itemTags = Collections.emptyList();
		if (new File(Global.OBJECTS.getItemTagPath()).length() != 0) {
			try {
				CsvToBean<ItemTag> csv = new CsvToBeanBuilder<ItemTag>(Files.newBufferedReader(Paths.get(Global.OBJECTS.getItemTagPath())))
						.withType(ItemTag.class).build();
				itemTags = csv.parse();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return itemTags;
	}

	public List<User> parseUsersFromFile() {

		List<User> users = Collections.emptyList();
		if (new File(Global.OBJECTS.getUserPath()).length() != 0) {
			try {
				CsvToBean<User> csv = new CsvToBeanBuilder<User>(Files.newBufferedReader(Paths.get(Global.OBJECTS.getUserPath())))
						.withType(User.class).build();
				users = csv.parse();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return users;
	}

	// TODO: This method will eventually give the user the option to have an admin let them do an operation they don't have permission for
	public boolean allowTemporaryAccess() {

		return false;
	}

	/*
	 * EVERYTHING BELOW HERE REQUIRES SOME LEVEL OF PERMISSIONS
	 * TODO: MANAGE PERMISSIONS
	 * 
	 * public method checklist
	 * > validate permissions
	 * > execute method
	 * > ensure the failure reason is returned in the result object
	 */

	public FileIOResult addNewItemToFile(Item item) {

		if (!Global.OBJECTS.getCurrentUser().hasPermission(UserOperations.ADD_ITEM)) {
			if (!this.allowTemporaryAccess()) {
				return new FileIOResult(FileIOStatus.OPERATION_NOT_PERMITTED, "You do not have permission to perform this operation");
			}
		}
		
		return this.addItem(item);
	}

	private FileIOResult addItem(Item item) {

		FileIOResult result;
		try {
			FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getMainPath()).toString()), true);
			CSVWriter csv = new CSVWriter(writer);

			csv.writeNext(item.toCsvString(), false);

			csv.close();
			writer.close();
			result = new FileIOResult(FileIOStatus.OPERATION_SUCCESS, "Item successfully added to inventory!");
		} catch (IOException e) {
			result = new FileIOResult(FileIOStatus.OPERATION_FAILURE, "Some error occurred and the item could not be added to the inventory. Please try again.");
			result.setCause(e);
		}

		return result;
	}

	public boolean addNewItemTagToFile(ItemTag tag) {

		boolean success;
		try {
			FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getItemTagPath()).toString()), true);
			CSVWriter csv = new CSVWriter(writer);

			csv.writeNext(tag.toCsvString(), false);

			csv.close();
			writer.close();
			success = true;
		} catch (IOException e) {
			success = false;
		}

		return success;
	}

	public boolean addNewUserToFile(User user) {

		boolean success;
		try {
			FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getUserPath()).toString()), true);
			CSVWriter csv = new CSVWriter(writer);

			csv.writeNext(user.toCsvString(), false);

			csv.close();
			writer.close();
			success = true;
		} catch (IOException e) {
			success = false;
		}

		return success;
	}

	public boolean modifyExistingItemInFile(Item toModify, UserOperations op) {

		boolean success = false;

		List<Item> items = this.parseItemsFromFile();
		int index = -1;
		for (Item item : items) {
			if (item.getId().equals(toModify.getId())) {
				index = items.indexOf(item);
			}
		}

		if (index != -1) {

			if (op.equals(UserOperations.EDIT_ITEM))
				items.set(index, toModify);
			else if (op.equals(UserOperations.REMOVE_ITEM)) {

				ArrayList<ItemTag> tags = new ArrayList<>();
				for (ItemTag tag : Global.OBJECTS.getItemTags()) {
					if (tag.isItemAssociated(items.get(index))) {
						tags.add(tag);
						tag.unassociateItems(items.get(index));
					}
				}
				if (!tags.isEmpty()) {
					ItemTag [] updatedTags = new ItemTag[tags.size()];
					success = this.modifyExistingItemTagsInFile(UserOperations.EDIT_ITEM_TAG, tags.toArray(updatedTags));
				}

				items.remove(index);
			}

			try {
				FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getMainPath()).toString()), false);
				CSVWriter csv = new CSVWriter(writer);

				for (Item item : items) {
					csv.writeNext(item.toCsvString(), false);
				}

				csv.close();
				writer.close();
				success = true;
			} catch(IOException e) {
				success = false;
			}
		}

		return success;
	}

	public boolean modifyExistingUserInFile(UserOperations op, User toModify) {

		boolean success = false;

		List<User> users = this.parseUsersFromFile();
		int index = -1;
		for (User user : users) {
			if (user.getId().equals(toModify.getId())) {
				index = users.indexOf(user);
			}
		}

		if (index != -1) {

			if (op.equals(UserOperations.EDIT_USER)) {
				users.set(index, toModify);
			} else if (op.equals(UserOperations.REMOVE_USER)) {
				users.remove(index);
			}

			try {
				FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getUserPath()).toString()), false);
				CSVWriter csv = new CSVWriter(writer);

				for (User user : users) {
					csv.writeNext(user.toCsvString());
				}

				csv.close();
				writer.close();
				success = true;
			} catch(IOException e) {
				success = false;
			}
		}

		return success;
	}

	public boolean modifyExistingItemTagsInFile(UserOperations op, ItemTag... toModify) {
		boolean success = false;

		List<ItemTag> itemTags = this.parseItemTagsFromFile();
		ArrayList<Integer> index = new ArrayList<>();

		for (ItemTag tag : itemTags) {
			for (ItemTag updatedTag : toModify) {
				if (tag.getName().equalsIgnoreCase(updatedTag.getName())) {
					index.add(itemTags.indexOf(tag));
				}
			}
		}

		if (!index.isEmpty()) {

			for (int i = 0; i < index.size(); i++) {
				if (op.equals(UserOperations.EDIT_ITEM_TAG))
					itemTags.set(index.get(i), toModify[i]);
				else if (op.equals(UserOperations.REMOVE_ITEM_TAG))
					itemTags.remove(index.get(i).intValue());
			}

			try {
				FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getItemTagPath()).toString()), false);
				CSVWriter csv = new CSVWriter(writer);

				for (ItemTag tag : itemTags) {
					csv.writeNext(tag.toCsvString(), false);
				}

				csv.close();
				writer.close();
				success = true;
			} catch(IOException e) {
				success = false;
			}
		}

		return success;
	}
}
