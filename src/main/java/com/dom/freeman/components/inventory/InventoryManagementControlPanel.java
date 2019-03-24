package com.dom.freeman.components.inventory;

import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;

public class InventoryManagementControlPanel extends Panel {

	public InventoryManagementControlPanel() {
		super();
		this.initializeContent();
	}
	
	public InventoryManagementControlPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.initializeContent();
	}
	
	private void initializeContent() {
		
		Panel controls = new Panel(new GridLayout(2));
		
		RadioBoxList<String> sortField = new RadioBoxList<String>();
		RadioBoxList<String> sortDirection = new RadioBoxList<String>();
		
		sortField.addItem("Item Type");
		sortField.addItem("Date Added");
		sortField.addItem("Date Expires");
		
		sortDirection.addItem("Ascending");
		sortDirection.addItem("Descending");
		
		sortField.setCheckedItemIndex(0);
		sortDirection.setCheckedItemIndex(0);
		
		controls.addComponent(sortField);
		controls.addComponent(sortDirection);
		
		this.addComponent(controls.withBorder(Borders.singleLine("INVENTORY SORT CONTROLS")));
	}
}
