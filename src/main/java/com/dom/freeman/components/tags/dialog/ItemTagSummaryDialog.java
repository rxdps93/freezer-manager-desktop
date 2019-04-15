package com.dom.freeman.components.tags.dialog;

import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.dom.freeman.obj.users.UserOperations;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.table.Table;

public class ItemTagSummaryDialog extends DialogWindow {

	private Boolean result;

	public ItemTagSummaryDialog(String title, UserOperations op, ItemTag tag) {
		super(title);
		this.configureContent(op, tag);
	}

	@Override
	public Boolean showDialog(WindowBasedTextGUI textGUI) {
		result = null;
		super.showDialog(textGUI);
		return result;
	}

	private void configureContent(UserOperations op, ItemTag tag) {
		Panel mainPanel = new Panel(new GridLayout(5));

		// Description
		mainPanel.addComponent(new Label(this.labelMessage(op)).setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER, 
						false, false, 5, 1)));

		// Name
		mainPanel.addComponent(this.dialogSpacer(5));
		mainPanel.addComponent(new Label("Item Tag Name").setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER,
						false, false, 2, 1)));
		mainPanel.addComponent(this.dialogSpacer(1));
		mainPanel.addComponent(new Label(tag.getName()).setLayoutData(
				GridLayout.createHorizontallyFilledLayoutData(2)));

		// Table
		Table<String> associatedItemTable = this.addTable(tag);
		Label tableLabel = new Label("").setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER,
						false, false, 5, 1));
		mainPanel.addComponent(this.dialogSpacer(5));
		mainPanel.addComponent(tableLabel);
		if (associatedItemTable.getTableModel().getRowCount() > 0) {
			tableLabel.setText("Associated Items");
			associatedItemTable.setLayoutData(GridLayout.createLayoutData(
					Alignment.CENTER, Alignment.CENTER, true, true, 5, 1));
			associatedItemTable.setEnabled(false);
			mainPanel.addComponent(associatedItemTable);
		} else {
			tableLabel.setText("No associated items were found for this item tag.");
		}

		// Buttons
		mainPanel.addComponent(this.dialogSpacer(5));

		Panel buttons = new Panel(new GridLayout(2));
		if (op.equals(UserOperations.VIEW)) {
			buttons.addComponent(this.dialogSpacer(1));
			buttons.addComponent(new Button(LocalizedString.Close.toString(), new Runnable() {
				@Override
				public void run() {
					result = true;
					close();
				}
			}));
		} else {
			buttons.addComponent(new Button(LocalizedString.Continue.toString(), new Runnable() {
				@Override
				public void run() {
					result = true;
					close();
				}
			}));
			buttons.addComponent(new Button(LocalizedString.Abort.toString(), new Runnable() {
				@Override
				public void run() {
					result = false;
					close();
				}
			}));
		}
		buttons.setLayoutData(GridLayout.createLayoutData(Alignment.END, Alignment.END, false, false, 5, 1));
		mainPanel.addComponent(buttons);

		this.setComponent(mainPanel);
	}

	private Table<String> addTable(ItemTag tag) {
		Table<String> summary = new Table<>("Item Type", "Quantity", "Location", "Added", "Expires");

		Item assoc;
		for (String id : tag.getAssociatedItemIds()) {
			assoc = Utility.METHODS.getItemById(id);
			summary.getTableModel().addRow(
					assoc.getType(),
					String.format("%3d %s", assoc.getQuantity(), assoc.getUnit().getAbbreviationByValue(assoc.getQuantity())),
					assoc.getLocation().getFreezerLocation(),
					assoc.getAddedFormatted(),
					assoc.getExpiresFormatted());
		}

		return summary;
	}

	private EmptySpace dialogSpacer(int gridSize) {
		return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(gridSize));
	}

	private String labelMessage(UserOperations op) {
		String msg;
		switch (op) {
		case VIEW:
			msg = "Summary for selected item tag";
			break;
		case REMOVE_ITEM_TAG:
			msg = "Please carefully review item tag details. Removing an item CANNOT BE UNDONE.\nAssociated items WILL NOT be removed.";
			break;
		case ADD_ITEM_TAG:
		case EDIT_ITEM_TAG:
		default:
			msg = "Please carefully review item tag details before saving it.";
		}
		return msg;
	}

}
