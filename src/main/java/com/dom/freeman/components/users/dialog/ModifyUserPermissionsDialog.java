package com.dom.freeman.components.users.dialog;

import java.util.Arrays;

import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserGroup;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class ModifyUserPermissionsDialog extends DialogWindow {

	private RadioBoxList<String> userGroups;

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
		this.userGroups = this.configurePermissionList(selectedUser);
		mainPanel.addComponent(this.userGroups);

		mainPanel.addComponent(this.dialogSpacer());
		buttonPanel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER, Alignment.CENTER,
				false, false, 2, 1)).addTo(mainPanel);
		this.setComponent(mainPanel);
	}

	private RadioBoxList<String> configurePermissionList(User selectedUser) {
		RadioBoxList<String> checkList = new RadioBoxList<>(new TerminalSize(50, UserGroup.values().length));

		for (UserGroup group: UserGroup.values()) {
			if (group.equals(UserGroup.DEVELOPER) && !Global.OBJECTS.getCurrentUser().getUserGroup().equals(UserGroup.DEVELOPER))
				continue;

			checkList.addItem(group.toString());
			if (selectedUser.getUserGroup().equals(group))
				checkList.setCheckedItem(group.toString());
		}

		return checkList;
	}

	private void onSave(User selectedUser) {

		boolean authCheck = true;

		// Check if the user can suspend/restore access
		if ((selectedUser.getUserGroup().equals(UserGroup.SUSPENDED) && !this.userGroups.getCheckedItem().equals(UserGroup.SUSPENDED.toString()) ||
				(!selectedUser.getUserGroup().equals(UserGroup.SUSPENDED) && this.userGroups.getCheckedItem().equals(UserGroup.SUSPENDED.toString())))) {
			if (!Global.OBJECTS.getCurrentUser().hasPermission(UserOperation.SUSPEND_USER)) {
				authCheck = false;
				new MessageDialogBuilder().setTitle("Not authorized")
				.setText("You do not have permissions to suspend/restore a user.")
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			}
		}

		if (authCheck) {
			selectedUser.setUserGroup(UserGroup.valueOf(this.userGroups.getCheckedItem()));

			boolean write = FileIO.METHODS.modifyExistingUserInFile(UserOperation.EDIT_USER, selectedUser);

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
