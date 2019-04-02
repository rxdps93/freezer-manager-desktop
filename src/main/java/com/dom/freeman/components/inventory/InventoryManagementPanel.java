package com.dom.freeman.components.inventory;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.Global;
import com.dom.freeman.Utility;
import com.dom.freeman.components.FileOperation;
import com.dom.freeman.components.inventory.dialog.EditItemDialog;
import com.dom.freeman.components.inventory.dialog.ItemSummaryDialog;
import com.dom.freeman.components.inventory.tables.InventoryManagementTable;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

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
		InventoryManagementTable<String> inventory = new InventoryManagementTable<>("ITEM TYPE", "QUANTITY", "LOCATION", "ADDED DATE", "EXPIRATION DATE", "ID");
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
						EditItemDialog editItem = new EditItemDialog("EDIT ITEM", Utility.METHODS.getItemById(data.get(5)));
						editItem.setHints(Arrays.asList(Hint.CENTERED));
						editItem.showDialog(parent.getTextGUI());
					}
				})
				.addAction("Delete this item", new Runnable() {
					@Override
					public void run() {
						List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());
						Item toRemove = Utility.METHODS.getItemById(data.get(5));

						ItemSummaryDialog summary = new ItemSummaryDialog("Remove Item Final Summary", FileOperation.REMOVE, toRemove);
						summary.setHints(Arrays.asList(Hint.CENTERED));

						if (summary.showDialog(parent.getTextGUI())) {

							boolean remove = Utility.METHODS.modifyExistingItemInFile(toRemove, FileOperation.REMOVE);

							if (remove) {
								new MessageDialogBuilder().setTitle("Item Removed Successfully")
								.setText("Item successfully removed from inventory!")
								.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
								.addButton(MessageDialogButton.OK).build().showDialog(parent.getTextGUI());
								Utility.METHODS.updateInventory();
								Utility.METHODS.refreshViews();
							} else {
								new MessageDialogBuilder().setTitle("Warning")
								.setText("Some error occurred and the item could not be removed. Please try again.")
								.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
								.addButton(MessageDialogButton.OK).build().showDialog(parent.getTextGUI());
							}
						}
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
