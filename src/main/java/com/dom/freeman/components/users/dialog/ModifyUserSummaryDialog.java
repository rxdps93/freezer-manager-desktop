package com.dom.freeman.components.users.dialog;

import com.dom.freeman.obj.FileOperation;
import com.dom.freeman.obj.SummaryTableCellRenderer;
import com.dom.freeman.obj.User;
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

public class ModifyUserSummaryDialog extends DialogWindow {

	private Boolean result;
	private FileOperation op;
	private User newUser = null;
	private User oldUser = null;
	
	public ModifyUserSummaryDialog(String title, FileOperation op, User newUser) {
		super(title);
		this.op = op;
		this.newUser = newUser;
		this.configureContent(2);
	}
	
	public ModifyUserSummaryDialog(String title, FileOperation op, User newUser, User oldUser) {
		super(title);
		this.op = op;
		this.oldUser = oldUser;
		this.configureContent(3);
	}
	
	@Override
	public Boolean showDialog(WindowBasedTextGUI textGUI) {
		this.result = null;
		super.showDialog(textGUI);
		return this.result;
	}
	
	private void configureContent(int gridSize) {
		
		Panel mainPanel = new Panel(new GridLayout(gridSize));
		
		// Description
		mainPanel.addComponent(new Label(this.labelMessage()).setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER, false, false, gridSize, 1)));
		
		// Table
		Table<String> summary;
		if (gridSize == 2)
			summary = this.addUserTable();
		else
			summary = this.editUserTable();
		
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
	
	private Table<String> addUserTable() {
		Table<String> summary = new Table<>("User Field", this.op.equals(FileOperation.REMOVE) ? "Selected Item" : "New Item");
		
		summary.getTableModel().addRow("First Name", this.newUser.getFirstName());
		summary.getTableModel().addRow("Last Name", this.newUser.getLastName());
		summary.getTableModel().addRow("Display Name", this.newUser.getDisplayName());
		
		return summary;
	}
	
	private Table<String> editUserTable() {
		Table<String> summary = this.addUserTable();
		
		summary.getTableModel().addColumn("Old User", new String[] {
			this.oldUser.getFirstName(),
			this.oldUser.getLastName(),
			this.oldUser.getDisplayName()
		});
		
		return null;
	}
	
	private String labelMessage() {
		
		String msg;
		switch (op) {
		case REMOVE:
			msg = "Please carefully review user details. Removing a user CANNOT BE UNDONE.";
			break;
		case ADD:
		case EDIT:
			default:
				msg = "Please carefully review user details before saving.";
		}
		return msg;
	}
	
	private EmptySpace dialogSpacer(int gridCells) {
		return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(gridCells));
	}
}
