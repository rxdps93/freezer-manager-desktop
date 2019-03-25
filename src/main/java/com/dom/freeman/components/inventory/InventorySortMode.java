package com.dom.freeman.components.inventory;

import java.util.Comparator;

import com.dom.freeman.obj.Item;

public enum InventorySortMode {
	
	// Sorting on Item Type from A to Z
	TYPE_ASC(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.getType().compareTo(o2.getType());
		}
	}),
	
	// Sorting on Item Type from Z to A
	TYPE_DESC(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return -(o1.getType().compareTo(o2.getType()));
		}
	}),
	
	// Sorting on Quantity: Smaller units first, with a secondary sort by value (e.g. 3g before 5g before 4oz before 1lb)
	QUANT_ASC(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			int result = Double.compare(o1.getUnit().getCompareValue(), o2.getUnit().getCompareValue());
			if (result == 0)
				result = Integer.compare(o1.getQuantity(), o2.getQuantity());
			return result;
		}
	}),
	
	// Sorting on Quantity: Larger units first, with a secondary sort by value
	QUANT_DESC(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			int result = Double.compare(o1.getUnit().getCompareValue(), o2.getUnit().getCompareValue());
			if (result == 0)
				result = Integer.compare(o1.getQuantity(), o2.getQuantity());
			return -result;
		}
	}),
	
	// Sorting on Added Date from newest to oldest
	ADD_NEWER(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.getAdded().compareTo(o2.getAdded());
		}
	}),
	
	// Sorting on Added Date from oldest to newest
	ADD_OLDER(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return -(o1.getAdded().compareTo(o2.getAdded()));
		}
	}),
	
	// Sorting on Expiry Date from future to past
	EXP_NEWER(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return o1.getExpires().compareTo(o2.getExpires());
		}
	}),
	
	// Sorting on Expiry Date from past to future
	EXP_OLDER(new Comparator<Item>() {
		@Override
		public int compare(Item o1, Item o2) {
			return -(o1.getExpires().compareTo(o2.getExpires()));
		}
	});
	
	private final Comparator<Item> sortMethod;
	
	private InventorySortMode(Comparator<Item> sortMethod) {
		this.sortMethod = sortMethod;
	}
	
	public Comparator<Item> getSortMethod() {
		return this.sortMethod;
	}
}
