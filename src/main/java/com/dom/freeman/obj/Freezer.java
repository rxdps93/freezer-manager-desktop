package com.dom.freeman.obj;

public enum Freezer {

	CHEST_FREEZER("Chest Freezer", "Basement"),
	KITCHEN_FREEZER("Fridge Freezer", "Kitchen"),
	ERROR("UNKNOWN", "UNKNOWN");
	
	private final String name;
	private final String location;
	
	private Freezer(String name, String location) {
		this.name = name;
		this.location = location;
	}
	
	public String getFreezerName() {
		return this.name;
	}
	
	public String getFreezerLocation() {
		return this.location;
	}
}
