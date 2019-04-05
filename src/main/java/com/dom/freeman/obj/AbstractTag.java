package com.dom.freeman.obj;

import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

public class AbstractTag {

	@CsvBindByPosition(position = 0)
	private String name;
	
	@CsvCustomBindByPosition(position = 1, converter = TagAssociationConverter.class)
	private List<String> associatedItemIds = new ArrayList<>();
	
	public AbstractTag(String name) {
		this.name = name;
	}
	
	public AbstractTag() {
		
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setAssociatedItemIds(List<String> ids) {
		this.associatedItemIds = ids;
	}
	
	public List<String> getAssociatedItemIds() {
		return this.associatedItemIds;
	}
	
	public void associateItems(Item... items) {
		for (Item item : items) {
			this.associateItems(item.getId());
		}
	}
	
	public void associateItems(String... itemIds) {
		
		if (this.associatedItemIds.isEmpty())
			this.associatedItemIds = new ArrayList<>();
		
		for (String id : itemIds) {
			this.associatedItemIds.add(id);
		}
	}
	
	public boolean isItemAssociated(Item item) {
		return this.associatedItemIds.contains(item.getId());
	}
	
	public String[] toCsvString() {
		String [] csv = new String[2];
		csv[0] = this.name;
		
		StringBuilder ids = new StringBuilder();
		for (int i = 0; i < this.associatedItemIds.size(); i++) {
			ids.append(this.associatedItemIds.get(i));
			if (i < this.associatedItemIds.size() - 1)
				ids.append(";");
		}
		csv[1] = ids.toString();
		return csv;
	}
	
	@Override
	public String toString() {
		String [] str = this.toCsvString();
		return str[0] + ": " + str[1];
	}
}
