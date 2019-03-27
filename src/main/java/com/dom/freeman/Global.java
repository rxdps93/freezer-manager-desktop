package com.dom.freeman;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.dom.freeman.obj.Item;

public enum Global {

	OBJECTS;
	
	private List<Item> inventory;
	private Map<String, Integer> types;
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	public List<Item> getInventory() {
		return this.inventory;
	}
	
	public Map<String, Integer> getTypes() {
		return this.types;
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
}
