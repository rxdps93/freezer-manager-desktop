package com.dom.freeman.components.inventory.dialog;

import com.dom.freeman.components.FileOperation;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.table.Table;

public class ItemSummaryDialog extends DialogWindow {

	private Boolean result;
	private FileOperation op;

	// For adding an item
	public ItemSummaryDialog(String title, FileOperation op, Item newItem) {
		super(title);
		this.op = op;
		configureContent(2, newItem);
	}

	// For editing an item
	public ItemSummaryDialog(String title, FileOperation op, Item newItem, Item oldItem) {
		super(title);
		this.op = op;
		configureContent(3, newItem, oldItem);
	}

	@Override
	public Boolean showDialog(WindowBasedTextGUI textGUI) {
		this.result = null;
		super.showDialog(textGUI);
		return this.result;
	}

	private void configureContent(int gridSize, Item... items) {
		
		Panel mainPanel = new Panel(new GridLayout(gridSize));

		// Description
		mainPanel.addComponent(new Label(this.labelMessage()).setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER, false, false, gridSize, 1)));

		// Table
		Table<String> summary;
		if (gridSize == 2)
			summary = this.addTable(items[0]);
		else
			summary = this.editTable(items[0], items[1]);
		
		summary.setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER, true, true, gridSize, 1));
		summary.setEnabled(false);
		summary.setTableCellRenderer(new SummaryTableCellRenderer<String>());
		mainPanel.addComponent(this.dialogSpacer(gridSize));
		mainPanel.addComponent(summary);

		// Buttons
		mainPanel.addComponent(this.dialogSpacer(gridSize));
		
		Panel buttons = new Panel(new GridLayout(2));
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
		buttons.setLayoutData(GridLayout.createLayoutData(Alignment.END, Alignment.END, false, false, gridSize, 1));
		mainPanel.addComponent(buttons);

		this.setComponent(mainPanel);
	}

	private Table<String> addTable(Item newItem) {
		Table<String> summary = new Table<>("Item Field", this.op.equals(FileOperation.REMOVE) ? "Selected Item" : "New Item");

		summary.getTableModel().addRow("Item Type", newItem.getType());
		summary.getTableModel().addRow("Quantity", Integer.toString(newItem.getQuantity()));
		summary.getTableModel().addRow("Unit", newItem.getUnit().getCommonName());
		summary.getTableModel().addRow("Location", newItem.getLocation().getFreezerLocation());
		summary.getTableModel().addRow("Add Date", newItem.getAddedFormatted());
		summary.getTableModel().addRow("Expires", newItem.getExpiresFormatted());

		return summary;
	}
	
	private String labelMessage() {
		
		String msg;
		switch (op) {
		case REMOVE:
			msg = "Please carefully review item details. Removing an item CANNOT BE UNDONE.";
			break;
		case ADD:
		case EDIT:
			default:
			msg = "Please carefully review item details before saving it.";
				
		}
		return msg;
	}

	private Table<String> editTable(Item newItem, Item oldItem) {
		Table<String> summary = this.addTable(newItem); 
		
		summary.getTableModel().addColumn("Old Item", new String[] {
				oldItem.getType(),
				Integer.toString(oldItem.getQuantity()),
				oldItem.getUnit().getCommonName(),
				oldItem.getLocation().getFreezerLocation(),
				oldItem.getAddedFormatted(),
				oldItem.getExpiresFormatted()});
		
		return summary;
	}

	private EmptySpace dialogSpacer(int gridCells) {
		return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(gridCells));
	}
}
