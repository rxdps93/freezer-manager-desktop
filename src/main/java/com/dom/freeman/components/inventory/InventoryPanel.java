package com.dom.freeman.components.inventory;

import java.util.List;

import com.dom.freeman.components.ViewPanel;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyType;

public class InventoryPanel extends ViewPanel {
	
	private final Window parent;
	private List<Item> items;
	private Interactable interactable;
	
	public InventoryPanel(List<Item> items, Window parent) {
		super(KeyType.F2);
		this.parent = parent;
		this.items = items;
		configureContent();
	}
	
	public InventoryPanel(LayoutManager layoutManager, List<Item> items, Window parent) {
		super(layoutManager, KeyType.F2);
		this.parent = parent;
		this.items = items;
		configureContent();
	}
	
	private void configureContent() {
		
		InventoryManagementPanel invManPanel = new InventoryManagementPanel(this.items, this.parent);
		this.addComponent(invManPanel);
		this.addComponent(new InventoryManagementControlPanel());
		this.interactable = invManPanel.getInteractable();
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
