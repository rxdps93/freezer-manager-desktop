package com.dom.freeman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.obj.FileOperation;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public enum Utility {

	METHODS;

	public void refreshViews() {
		for (AbstractInventoryTable<?> table : Global.OBJECTS.getRegisteredTables())
			table.refresh();
	}

	public void updateInventory() {

		Global.OBJECTS.setInventory(this.parseItemsFromFile());
		Global.OBJECTS.setTypes(this.itemTypeCount(Global.OBJECTS.getInventory()));
		Global.OBJECTS.setItemTags(this.parseItemTagsFromFile());
	}

	public Item getItemById(String id) {
		for (Item item : Global.OBJECTS.getInventory()) {
			if (item.getId().equals(id))
				return item;
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

	public boolean addNewItemToFile(Item item) {

		boolean success;
		try {
			FileWriter writer = new FileWriter(new File(Paths.get(Global.OBJECTS.getMainPath()).toString()), true);
			CSVWriter csv = new CSVWriter(writer);

			csv.writeNext(item.toCsvString(), false);

			csv.close();
			writer.close();
			success = true;
		} catch (IOException e) {
			success = false;
		}

		return success;
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

	public boolean modifyExistingItemInFile(Item toModify, FileOperation op) {

		boolean success = false;

		List<Item> items = this.parseItemsFromFile();
		int index = -1;
		for (Item item : items) {
			if (item.getId().equals(toModify.getId())) {
				index = items.indexOf(item);
			}
		}

		if (index != -1) {
			if (op.equals(FileOperation.EDIT))
				items.set(index, toModify);
			else if (op.equals(FileOperation.REMOVE))
				items.remove(index);

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
	
	public boolean modifyExistingItemTagInFile(ItemTag toModify, FileOperation op) {
		boolean success = false;
		
		List<ItemTag> itemTags = this.parseItemTagsFromFile();
		int index = -1;
		for (ItemTag tag : itemTags) {
			if (tag.getName().equalsIgnoreCase(toModify.getName())) {
				index = itemTags.indexOf(tag);
			}
		}
		
		if (index != -1) {
			if (op.equals(FileOperation.EDIT))
				itemTags.set(index, toModify);
			else if (op.equals(FileOperation.REMOVE))
				itemTags.remove(index);
			
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
