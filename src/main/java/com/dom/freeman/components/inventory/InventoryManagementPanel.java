package com.dom.freeman.components.inventory;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.Global;
import com.dom.freeman.Utility;
import com.dom.freeman.components.inventory.dialog.EditItemDialog;
import com.dom.freeman.components.inventory.tables.InventoryManagementTable;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;

public class InventoryManagementPanel extends Panel {

	private Window parent;
	private InventoryManagementTable<String> inventory;
	
	public InventoryManagementPanel( Window parent) {
		super();
		this.parent = parent;
		configureContent();
	}
	
	public InventoryManagementPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		configureContent();
	}
	
	private void configureContent() {
		
		Panel panel = new Panel();
		InventoryManagementTable<String> inventory = new InventoryManagementTable<>("ITEM TYPE", "QUANTITY", "ADDED DATE", "EXPIRATION DATE", "ID");
		inventory.setVisibleRows(60);
		inventory.setResetSelectOnTab(true);
		inventory.hideLastColumn(true);
		
		inventory.refresh();
		
		inventory.setSelectAction(new Runnable() {
			@Override
			public void run() {
				new ActionListDialogBuilder()
					.setTitle("SELECT AN ACTION")
					.addAction("Edit this item", new Runnable() {
						@Override
						public void run() {
							List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());
							EditItemDialog editItem = new EditItemDialog("EDIT ITEM", Utility.METHODS.getItemById(data.get(4)));
							editItem.setHints(Arrays.asList(Hint.CENTERED));
							editItem.showDialog(parent.getTextGUI());
						}
					})
					.addAction("Delete this item", new Runnable() {
						@Override
						public void run() {
							// TODO: Delete the item via a dialog
						}
					})
					.build().showDialog(parent.getTextGUI());
				
			}
		});
		
		panel.addComponent(inventory.setEscapeByArrowKey(false));
		this.inventory = inventory;
		Global.OBJECTS.registerTable(this.inventory);
		this.addComponent(panel.withBorder(Borders.singleLine("INVENTORY MANAGEMENT")));
	}
	
	public InventoryManagementTable<String> getTable() {
		return this.inventory;
	}
	
	public Interactable getInteractable() {
		return this.inventory;
	}
}
