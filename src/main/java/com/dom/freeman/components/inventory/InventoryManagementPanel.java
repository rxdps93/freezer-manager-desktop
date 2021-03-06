package com.dom.freeman.components.inventory;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.inventory.dialog.AssignItemTagDialog;
import com.dom.freeman.components.inventory.dialog.EditItemDialog;
import com.dom.freeman.components.inventory.dialog.ModifyItemSummaryDialog;
import com.dom.freeman.components.inventory.dialog.ViewItemSummaryDialog;
import com.dom.freeman.components.inventory.tables.InventoryManagementTable;
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

public class InventoryManagementPanel extends Panel {

	private InventoryManagementTable<String> inventory;

	public InventoryManagementPanel() {
		super();
		this.configureContent();
	}

	public InventoryManagementPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.configureContent();
	}

	private void configureContent() {

		Panel panel = new Panel();
		InventoryManagementTable<String> inventory = new InventoryManagementTable<>("ITEM TYPE", "QUANTITY", "LOCATION", "ADDED DATE", "EXPIRATION DATE", "TAGS", "ID");
		inventory.setVisibleRows(60);
		inventory.setResetSelectOnTab(true);
		inventory.hideLastColumn(true);

		inventory.refresh();

		inventory.setSelectAction(new Runnable() {
			@Override
			public void run() {
				new ActionListDialogBuilder()
				.setTitle("SELECT AN ACTION")
				.addAction("View Item Summary", new Runnable() {
					@Override
					public void run() {
						List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());
						ViewItemSummaryDialog summary = new ViewItemSummaryDialog("Item Summary", Utility.METHODS.getItemById(data.get(6)));
						summary.setHints(Arrays.asList(Hint.CENTERED));
						summary.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Assign Item Tags", new Runnable() {
					@Override
					public void run() {
						List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());
						AssignItemTagDialog assignTags = new AssignItemTagDialog("Assign Item Tags", Utility.METHODS.getItemById(data.get(6)));
						assignTags.setHints(Arrays.asList(Hint.CENTERED));
						assignTags.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Edit this item", new Runnable() {
					@Override
					public void run() {
						List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());
						EditItemDialog editItem = new EditItemDialog("EDIT ITEM", Utility.METHODS.getItemById(data.get(6)));
						editItem.setHints(Arrays.asList(Hint.CENTERED));
						editItem.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Delete this item", new Runnable() {
					@Override
					public void run() {
						List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());
						Item toRemove = Utility.METHODS.getItemById(data.get(6));

						ModifyItemSummaryDialog summary = new ModifyItemSummaryDialog("Remove Item Final Summary", UserOperation.REMOVE_ITEM, toRemove);
						summary.setHints(Arrays.asList(Hint.CENTERED));

						if (summary.showDialog(Global.OBJECTS.getMainWindow().getTextGUI())) {

							OperationResult result = FileIO.METHODS.modifyExistingItemInFile(toRemove, UserOperation.REMOVE_ITEM);
							
							new MessageDialogBuilder().setTitle("Remove Item Results")
							.setText(result.getMessage())
							.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
							.addButton(MessageDialogButton.OK).build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
							
							if (result.isSuccess()) {
								Utility.METHODS.updateInventory();
								Utility.METHODS.refreshViews();
							}
							
						}
					}
				})
				.build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
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
