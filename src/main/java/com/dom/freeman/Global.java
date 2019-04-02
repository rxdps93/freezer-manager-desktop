package com.dom.freeman;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.obj.Item;

public enum Global {

	OBJECTS;
	
	private List<Item> inventory;
	private Map<String, Integer> types;
	private List<AbstractInventoryTable<?>> registeredTables = new ArrayList<>();
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
//	private final String origPath = "src/main/resources/contents.csv";
//	private final String uuidPath = "src/main/resources/contents_uuid.csv";
	private final String frzrPath = "src/main/resources/contents_location.csv";
	
	public List<Item> getInventory() {
		return this.inventory;
	}
	
	public Map<String, Integer> getTypes() {
		return this.types;
	}
	
	public List<AbstractInventoryTable<?>> getRegisteredTables() {
		return this.registeredTables;
	}
	
	public DateTimeFormatter getDateFormat() {
		return this.dateFormat;
	}
	
	public void setInventory(List<Item> items) {
		this.inventory = items;
	}
	
	public void setTypes(Map<String, Integer> types) {
		this.types = types;
	}
	
	public void registerTable(AbstractInventoryTable<?> table) {
		this.registeredTables.add(table);
	}
	
	public String getMainPath() {
		return this.frzrPath;
	}
}
