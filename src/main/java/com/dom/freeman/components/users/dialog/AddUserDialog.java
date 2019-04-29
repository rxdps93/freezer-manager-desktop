package com.dom.freeman.components.users.dialog;

import java.util.Arrays;
import java.util.UUID;

import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserGroup;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class AddUserDialog extends AbstractModifyUserDialog {

	public AddUserDialog(String title) {
		super(title);
	}

	@Override
	public boolean validateUser() {
		
		boolean valid = true;
		
		if (this.getFirstNameEntry().getText().replaceAll(" ", "").isEmpty()) {
			new MessageDialogBuilder().setTitle("Create User Validation")
			.setText("You did not enter anything for first name. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			valid = false;
		}
		
		if (this.getLastNameEntry().getText().replaceAll(" ", "").isEmpty()) {
			new MessageDialogBuilder().setTitle("Create User Validation")
			.setText("You did not enter anything for last name. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			valid = false;
		}
		
		if (this.getDisplayNameEntry().getText().replaceAll(" ", "").isEmpty()) {
			new MessageDialogBuilder().setTitle("Create User Validation")
			.setText("You did not enter anything for display name. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			valid = false;
		}
		
		return valid;
	}

	@Override
	public void onSave() {
		if (this.validateUser()) {
			User user = new User(
					this.getFirstNameEntry().getText(),
					this.getLastNameEntry().getText(),
					this.getDisplayNameEntry().getText(),
					this.getPasswordEntry().getText(),
					UUID.randomUUID().toString(),
					UserGroup.USER);
			
			ModifyUserSummaryDialog summary = new ModifyUserSummaryDialog(
					"FINAL ADD USER SUMMARY" , UserOperation.ADD_USER, user);
			summary.setHints(Arrays.asList(Hint.CENTERED));
			
			if (summary.showDialog(this.getTextGUI()))
				this.saveUser(user);
		}
	}
	
	private void saveUser(User newUser) {
		
		OperationResult write = FileIO.METHODS.addNewUserToFile(newUser);
		
		new MessageDialogBuilder().setTitle("Add User Results")
		.setText(write.getMessage())
		.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
		.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		
		if (write.isSuccess()) {
			this.close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		}
	}

	@Override
	public void onCancel() {
		this.close();
	}

}
