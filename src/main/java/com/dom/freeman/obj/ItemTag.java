package com.dom.freeman.obj;

public class ItemTag extends AbstractTag {
	
	public ItemTag() {
		super();
	}
	
	public ItemTag(String name) {
		super(name);
	}

	public ItemTag(String name, String... itemIds) {
		super(name);
		this.associateItems(itemIds);
	}
	
	public ItemTag(String name, Item... items) {
		super(name);
		this.associateItems(items);
	}
}
