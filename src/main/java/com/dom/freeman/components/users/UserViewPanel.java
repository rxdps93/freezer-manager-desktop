package com.dom.freeman.components.users;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.FileIO;
import com.dom.freeman.Global;
import com.dom.freeman.Utility;
import com.dom.freeman.components.users.dialog.EditUserDialog;
import com.dom.freeman.components.users.dialog.ModifyUserSummaryDialog;
import com.dom.freeman.components.users.dialog.ViewUserSummaryDialog;
import com.dom.freeman.components.users.tables.UserViewTable;
import com.dom.freeman.obj.FileOperation;
import com.dom.freeman.obj.User;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class UserViewPanel extends Panel {

	private Window parent;
	private UserViewTable<String> userTable;
	
	public UserViewPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}
	
	public UserViewPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel panel = new Panel();
		UserViewTable<String> userTable = new UserViewTable<>("LAST NAME",
				"FIRST NAME", "DISPLAY NAME", "PERMISSION COUNT", "UNIQUE ID");
		userTable.setResetSelectOnTab(true);
		userTable.hideLastColumn(true);
		
		userTable.refresh();
		
		userTable.setSelectAction(new Runnable() {
			@Override
			public void run() {
				
				List<String> data = userTable.getTableModel().getRow(userTable.getSelectedRow());
				User selectedUser = Utility.METHODS.getUserById(data.get(userTable.getTableModel().getColumnCount() - 1));
				
				new ActionListDialogBuilder().setTitle("SELECT AN ACTION")
				.addAction("View User Summary", new Runnable() {
					@Override
					public void run() {
						ViewUserSummaryDialog viewUser = new ViewUserSummaryDialog("USER SUMMARY",
								selectedUser);
						viewUser.setHints(Arrays.asList(Hint.CENTERED));
						viewUser.showDialog(parent.getTextGUI());
					}
				})
				.addAction("Edit User Details", new Runnable() {
					@Override
					public void run() {
						EditUserDialog editUser = new EditUserDialog("EDIT USER", selectedUser);
						editUser.setHints(Arrays.asList(Hint.CENTERED));
						editUser.showDialog(parent.getTextGUI());
					}
				})
				.addAction("Edit User Permissions", new Runnable() {
					@Override
					public void run() {
						// TODO: Future task
					}
				})
				.addAction("Remove User", new Runnable() {
					@Override
					public void run() {
						ModifyUserSummaryDialog summary = new ModifyUserSummaryDialog("REMOVE USER",
								FileOperation.REMOVE, selectedUser);
						summary.setHints(Arrays.asList(Hint.CENTERED));
						
						if (summary.showDialog(parent.getTextGUI())) {
							boolean remove = FileIO.METHODS.modifyExistingUserInFile(FileOperation.REMOVE, selectedUser);
							
							if (remove) {
								new MessageDialogBuilder().setTitle("User Removed Successfully")
								.setText("User successfully removed!")
								.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
								.addButton(MessageDialogButton.OK).build().showDialog(parent.getTextGUI());
								Utility.METHODS.updateInventory();
								Utility.METHODS.refreshViews();
							} else {
								new MessageDialogBuilder().setTitle("Warning")
								.setText("Some error occurred and the user could not be removed. Please try again.")
								.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
								.addButton(MessageDialogButton.OK).build().showDialog(parent.getTextGUI());
							}
						}
					}
				}).build().showDialog(parent.getTextGUI());
			}
		});
		
		panel.addComponent(userTable.setEscapeByArrowKey(false));
		this.userTable = userTable;
		Global.OBJECTS.registerTable(this.userTable);
		this.addComponent(panel.withBorder(Borders.singleLine("USERS")));
	}
	
	public UserViewTable<String> getTable() {
		return this.userTable;
	}
	
	public Interactable getInteractable() {
		return this.userTable;
	}
}
