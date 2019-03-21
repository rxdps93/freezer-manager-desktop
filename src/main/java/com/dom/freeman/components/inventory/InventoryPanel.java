package com.dom.freeman.components.inventory;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.input.KeyType;

public class InventoryPanel extends ViewPanel {
	
	public InventoryPanel() {
		super(KeyType.F2);
		configureContent();
	}
	
	public InventoryPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F2);
		configureContent();
	}
	
	private void configureContent() {
		this.addComponent(new Label("Inventory Management Panel"));
	}
}
