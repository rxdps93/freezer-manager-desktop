package com.dom.freeman.components.users.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dom.freeman.obj.FileOperation;
import com.dom.freeman.obj.User;
import com.dom.freeman.obj.UserPermission;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class ModifyUserPermissionsDialog extends DialogWindow {

	private CheckBoxList<String> permissionList;
	
	public ModifyUserPermissionsDialog(String title, User selectedUser) {
		super(title);
		
		Panel buttonPanel = new Panel(new GridLayout(2).setHorizontalSpacing(1));
		buttonPanel.addComponent(new Button(LocalizedString.Save.toString(), new Runnable() {
			@Override
			public void run() {
				onSave(selectedUser);
			}
		})).setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER, true, false));
		
		buttonPanel.addComponent(new Button(LocalizedString.Cancel.toString(), new Runnable() {
			@Override
			public void run() {
				onCancel();
			}
		}));
		
		Panel mainPanel = new Panel(new GridLayout(2).setLeftMarginSize(1).setRightMarginSize(1));
		
		// Description
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Select the permissions to associate with the selected user.").setLayoutData(
				GridLayout.createHorizontallyFilledLayoutData(2)));
		
		// User Info
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Selected User"));
		mainPanel.addComponent(new Label(String.format("%s, %s: %s", 
				selectedUser.getLastName(),
				selectedUser.getFirstName(),
				selectedUser.getDisplayName())));
		
		// Permissions
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Select permissions for this user:"));
		this.permissionList = this.configurePermissionList(selectedUser);
		mainPanel.addComponent(this.permissionList);
		
		mainPanel.addComponent(this.dialogSpacer());
		buttonPanel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER, Alignment.CENTER,
				false, false, 2, 1)).addTo(mainPanel);
		this.setComponent(mainPanel);
	}
	
	private CheckBoxList<String> configurePermissionList(User selectedUser) {
		CheckBoxList<String> checkList = new CheckBoxList<>(new TerminalSize(50, UserPermission.values().length));
		
		for (UserPermission permission : UserPermission.values()) {
			checkList.addItem(permission.toString(), selectedUser.hasPermission(permission));
		}
		return checkList;
	}
	
	private void onSave(User selectedUser) {
		List<UserPermission> permissions = new ArrayList<>();
		for (String str : this.permissionList.getCheckedItems()) {
			permissions.add(UserPermission.valueOf(str));
		}
		selectedUser.setUserPermissions(permissions);
		
		boolean write = FileIO.METHODS.modifyExistingUserInFile(FileOperation.EDIT, selectedUser);
		
		if (write) {
			new MessageDialogBuilder().setTitle("User Permissions Successfully Updated")
			.setText("User permissions have been successfully updated")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			this.close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		} else {
			new MessageDialogBuilder().setTitle("Warning")
			.setText("Some error occurred and the permissions could not be updated for this user. Please try again.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		}
	}
	
	private void onCancel() {
		this.close();
	}
	
	private EmptySpace dialogSpacer() {
		return new EmptySpace(TerminalSize.ONE).setLayoutData(
				GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER,
						false, false, 2, 1));
	}
}
