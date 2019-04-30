package com.dom.freeman.components.inventory;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.inventory.dialog.EditItemDialog;
import com.dom.freeman.components.inventory.dialog.ModifyItemSummaryDialog;
import com.dom.freeman.components.inventory.tables.InventoryExpirationTable;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class InventoryExpirationPanel extends Panel {

	private InventoryExpirationTable<String> expiredItems;
	
	public InventoryExpirationPanel() {
		super();
		this.configureContent();
	}
	
	public InventoryExpirationPanel(LayoutManager layoutManager) {
		super(layoutManager);
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
						
						editItem.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Remove Item from Inventory", new Runnable() {
					@Override
					public void run() {
						ModifyItemSummaryDialog summary = new ModifyItemSummaryDialog("Remove Expired Item Final Summary", UserOperation.REMOVE_ITEM, selectedItem);
						summary.setHints(Arrays.asList(Hint.CENTERED));

						if (summary.showDialog(Global.OBJECTS.getMainWindow().getTextGUI())) {

							OperationResult result = FileIO.METHODS.modifyExistingItemInFile(selectedItem, UserOperation.REMOVE_ITEM);
							
							new MessageDialogBuilder().setTitle("Expired Item Removal Results")
							.setText(result.getMessage())
							.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
							.addButton(MessageDialogButton.OK).build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
							
							if (result.isSuccess()) {
								Utility.METHODS.updateInventory();
								Utility.METHODS.refreshViews();
							}
						}
					}
				}).build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
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
