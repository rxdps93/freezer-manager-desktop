package com.dom.freeman.components.inventory;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.inventory.dialog.EditItemDialog;
import com.dom.freeman.components.inventory.dialog.ModifyItemSummaryDialog;
import com.dom.freeman.components.inventory.tables.InventoryExpirationTable;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.users.UserOperations;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class InventoryExpirationPanel extends Panel {

	private Window parent;
	private InventoryExpirationTable<String> expiredItems;
	
	public InventoryExpirationPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}
	
	public InventoryExpirationPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel panel = new Panel();
		InventoryExpirationTable<String> expiredItems = new InventoryExpirationTable<>(
				"ITEM TYPE", "QUANTITY", "LOCATION", "EXPIRED", "DAYS EXPIRED", "ID");
		expiredItems.setVisibleRows(60);
		expiredItems.setResetSelectOnTab(true);
		expiredItems.hideLastColumn(true);
		
		expiredItems.refresh();
		
		expiredItems.setSelectAction(new Runnable() {
			@Override
			public void run() {
				List<String> data = expiredItems.getTableModel().getRow(expiredItems.getSelectedRow());
				Item selectedItem = Utility.METHODS.getItemById(data.get(data.size() - 1));
				
				new ActionListDialogBuilder()
				.setTitle("SELECT AN ACTION")
				.addAction("Update Expiration Date", new Runnable() {
					@Override
					public void run() {
						EditItemDialog editItem = new EditItemDialog("EDIT EXPIRATION DATE", selectedItem);
						editItem.setHints(Arrays.asList(Hint.CENTERED));
						
						editItem.getTypeEntry().setEnabled(false);
						editItem.getUnitEntry().setEnabled(false);
						editItem.getQuantityEntry().setEnabled(false);
						editItem.getLocationEntry().setEnabled(false);
						editItem.getAddedEntry().setEnabled(false);
						
						editItem.getExpiresEntry().setEnabled(true);
						
						editItem.showDialog(parent.getTextGUI());
					}
				})
				.addAction("Remove Item from Inventory", new Runnable() {
					@Override
					public void run() {
						ModifyItemSummaryDialog summary = new ModifyItemSummaryDialog("Remove Expired Item Final Summary", UserOperations.REMOVE_ITEM, selectedItem);
						summary.setHints(Arrays.asList(Hint.CENTERED));

						if (summary.showDialog(parent.getTextGUI())) {

							boolean remove = FileIO.METHODS.modifyExistingItemInFile(selectedItem, UserOperations.REMOVE_ITEM);

							if (remove) {
								new MessageDialogBuilder().setTitle("Expired Item Removed Successfully")
								.setText("Expired Item successfully removed from inventory!")
								.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
								.addButton(MessageDialogButton.OK).build().showDialog(parent.getTextGUI());
								Utility.METHODS.updateInventory();
								Utility.METHODS.refreshViews();
							} else {
								new MessageDialogBuilder().setTitle("Warning")
								.setText("Some error occurred and the expired item could not be removed. Please try again.")
								.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
								.addButton(MessageDialogButton.OK).build().showDialog(parent.getTextGUI());
							}
						}
					}
				}).build().showDialog(parent.getTextGUI());
			}
		});
		
		panel.addComponent(expiredItems.setEscapeByArrowKey(false));
		this.expiredItems = expiredItems;
		Global.OBJECTS.registerTable(this.expiredItems);
		this.addComponent(panel.withBorder(Borders.singleLine("EXPIRED ITEM MANAGEMENT")));
	}
	
	public InventoryExpirationTable<String> getTable() {
		return this.expiredItems;
	}
	
	public Interactable getInteractable() {
		return this.getTable();
	}
}
