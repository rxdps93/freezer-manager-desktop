package com.dom.freeman.components.users;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.users.dialog.EditUserDialog;
import com.dom.freeman.components.users.dialog.ModifyUserPermissionsDialog;
import com.dom.freeman.components.users.dialog.ModifyUserSummaryDialog;
import com.dom.freeman.components.users.dialog.ViewUserSummaryDialog;
import com.dom.freeman.components.users.tables.UserViewTable;
import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.OperationStatus;
import com.dom.freeman.obj.users.User;
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

public class UserViewPanel extends Panel {

	private UserViewTable<String> userTable;
	
	public UserViewPanel() {
		super();
		this.configureContent();
	}
	
	public UserViewPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel panel = new Panel();
		UserViewTable<String> userTable = new UserViewTable<>("LAST NAME",
				"FIRST NAME", "DISPLAY NAME", "USER GROUP", "UNIQUE ID");
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
						viewUser.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Edit User Details", new Runnable() {
					@Override
					public void run() {
						EditUserDialog editUser = new EditUserDialog("EDIT USER", selectedUser);
						editUser.setHints(Arrays.asList(Hint.CENTERED));
						editUser.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Edit User Permissions", new Runnable() {
					@Override
					public void run() {
						if (Utility.METHODS.editPermissionAuth(selectedUser).isSuccess()) {
							ModifyUserPermissionsDialog userPermissions = new ModifyUserPermissionsDialog("USER PERMISSIONS", selectedUser);
							userPermissions.setHints(Arrays.asList(Hint.CENTERED));
							userPermissions.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
						} else {
							new MessageDialogBuilder().setTitle("Not authorized")
							.setText(OperationStatus.OPERATION_NOT_PERMITTED.getDefaultMessage())
							.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
							.addButton(MessageDialogButton.OK).build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
						}
					}
				})
				.addAction("Remove User", new Runnable() {
					@Override
					public void run() {
						ModifyUserSummaryDialog summary = new ModifyUserSummaryDialog("REMOVE USER",
								UserOperation.REMOVE_USER, selectedUser);
						summary.setHints(Arrays.asList(Hint.CENTERED));
						
						if (summary.showDialog(Global.OBJECTS.getMainWindow().getTextGUI())) {
							
							OperationResult result = FileIO.METHODS.modifyExistingUserInFile(UserOperation.REMOVE_USER, selectedUser);
							
							new MessageDialogBuilder().setTitle("Remove User Results")
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
