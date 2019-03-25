package com.dom.freeman.components.inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.dom.freeman.components.InventoryTable;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.gui2.table.TableModel;

public class InventoryManagementPanel extends Panel {

	private Window parent;
	private List<Item> items;
	private InventoryTable<String> inventory;
	
	public InventoryManagementPanel(List<Item> items, Window parent) {
		super();
		this.parent = parent;
		this.items = items;
		configureContent();
	}
	
	public InventoryManagementPanel(LayoutManager layoutManager, List<Item> items, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.items = items;
		configureContent();
	}
	
	private void configureContent() {
		
		Panel panel = new Panel();
		InventoryTable<String> inventory = new InventoryTable<>("ITEM TYPE", "QUANTITY", "ADDED DATE", "EXPIRATION DATE");
		inventory.setVisibleRows(60);
		inventory.setResetSelectOnTab(true);
		
		inventory.setTableModel(this.configureTableModel(this.items));
		
		inventory.setSelectAction(new Runnable() {
			@Override
			public void run() {
				new MessageDialogBuilder().setTitle("DETAILED ITEM VIEW").setText("WORK IN PROGRESS")
				.addButton(MessageDialogButton.Close)
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.build().showDialog(parent.getTextGUI());
				
			}
		});
		
		panel.addComponent(inventory.setEscapeByArrowKey(false));
		this.inventory = inventory;
		this.sortTable(InventorySortMode.TYPE_ASC);
		this.addComponent(panel.withBorder(Borders.singleLine("INVENTORY MANAGEMENT")));
	}
	
	private TableModel<String> configureTableModel(List<Item> items) {
		TableModel<String> model = new TableModel<>("ITEM TYPE", "QUANTITY", "ADDED DATE", "EXPIRATION DATE");
		
		for (Item item : items) {
			model.addRow(item.getType(),
					String.format("%3d %s",  item.getQuantity(), item.getUnit().getAbbreviationByValue(item.getQuantity())),
					item.getAdded().toString(),
					item.getExpires().toString());
		}
		
		return model;
	}
	
	public void sortTable(InventorySortMode sortMode) {

		Collections.sort(this.items, sortMode.getSortMethod());
		this.inventory.setTableModel(configureTableModel(this.items));
	}
	
	public InventoryTable<String> getInteractable() {
		return this.inventory;
	}
}
