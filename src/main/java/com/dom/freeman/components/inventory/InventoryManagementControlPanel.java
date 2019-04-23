package com.dom.freeman.components.inventory;

import java.util.Arrays;

import com.dom.freeman.components.inventory.dialog.AddItemDialog;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.Window.Hint;

public class InventoryManagementControlPanel extends Panel {

	private final InventoryManagementPanel invPanel;
	
	public InventoryManagementControlPanel(InventoryManagementPanel invPanel) {
		super();
		this.invPanel = invPanel;
		this.configureContent();
	}
	
	public InventoryManagementControlPanel(LayoutManager layoutManager, InventoryManagementPanel invPanel) {
		super(layoutManager);
		this.invPanel = invPanel;
		this.configureContent();
	}
	
	private Panel initializeSortPanel() {
		
		Panel controls = new Panel(new GridLayout(2));
		
		RadioBoxList<String> sortField = new RadioBoxList<String>();
		RadioBoxList<String> sortDirection = new RadioBoxList<String>();
		
		sortField.addItem("Item Type");
		sortField.addItem("Quantity");
		sortField.addItem("Date Added");
		sortField.addItem("Date Expires");
		
		sortDirection.addItem("Ascending");
		sortDirection.addItem("Descending");
		
		sortField.setCheckedItemIndex(0);
		sortDirection.setCheckedItemIndex(0);
		
		sortField.addListener(new InventorySortListener(sortDirection, this.invPanel));
		sortDirection.addListener(new InventorySortListener(sortField, this.invPanel));
		
		controls.addComponent(sortField.withBorder(Borders.singleLine("SORT BY...")));
		controls.addComponent(sortDirection.withBorder(Borders.singleLine("SORT DIRECTION")));
		
		return controls;
	}
	
	private Panel initializeFilterPanel() {
		
		Panel controls = new Panel();
		controls.addComponent(new Label("Placeholder for filtering"));
		return controls;
	}
	
	private Panel initializeOptionsPanel() {
		
		Panel options = new Panel();
		options.addComponent(new Button("Add Item", new Runnable() {
			@Override
			public void run() {
				AddItemDialog addItem = new AddItemDialog("ADD ITEM TO FREEZER");
				addItem.setHints(Arrays.asList(Hint.CENTERED));
				addItem.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
			}
		}));
		return options;
	}
	
	private void configureContent() {
		
		this.addComponent(initializeSortPanel().withBorder(Borders.singleLine("INVENTORY SORT CONTROLS")));
		this.addComponent(initializeFilterPanel().withBorder(Borders.singleLine("INVENTORY FILTER CONTROLS")));
		this.addComponent(initializeOptionsPanel().withBorder(Borders.singleLine("INVENTORY MANAGEMENT CONTROLS")));
	}
}
