package com.dom.freeman.obj;

import java.util.Collections;
import java.util.List;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

public class AbstractTag {

	@CsvBindByPosition(position = 0)
	private String name;
	
	@CsvCustomBindByPosition(position = 1, converter = TagAssociationConverter.class)
	private List<String> associatedItemIds;
	
	public AbstractTag(String name) {
		this.name = name;
		associatedItemIds = Collections.emptyList();
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
	
	public void associateItems(String... itemIds) {
		for (String id : itemIds) {
			this.associatedItemIds.add(id);
		}
	}
	
	public boolean isItemAssociated(Item item) {
		return this.associatedItemIds.contains(item.getId());
	}
	
	public String[] toCsvString() {
		String [] csv = new String[this.associatedItemIds.size() + 1];
		csv[0] = this.name;
		for (int i = 0; i < this.associatedItemIds.size(); i++)
			csv[i + 1] = this.associatedItemIds.get(i);
		return csv;
	}
}
