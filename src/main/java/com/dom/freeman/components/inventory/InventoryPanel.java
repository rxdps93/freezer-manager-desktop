package com.dom.freeman.components.inventory;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
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
		
		Label content = new Label("Inventory Management Panel");
		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(content);
	}
}
