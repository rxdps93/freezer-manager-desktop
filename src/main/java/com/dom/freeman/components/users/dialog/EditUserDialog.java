package com.dom.freeman.components.users.dialog;

import java.util.Arrays;

import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class EditUserDialog extends AbstractModifyUserDialog {

	private User toEdit;
	
	public EditUserDialog(String title, User toEdit) {
		super(title);
		this.toEdit = toEdit;
		
		this.getFirstNameEntry().setText(this.toEdit.getFirstName());
		this.getLastNameEntry().setText(this.toEdit.getLastName());
		this.getDisplayNameEntry().setText(this.toEdit.getDisplayName());
		this.getPasswordEntry().setValidationPattern(null).setMask('*')
		.setText("password").setEnabled(false);
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
		} else {
			for (User user : Global.OBJECTS.getUsers()) {
				if (user.getDisplayName().equalsIgnoreCase(this.getDisplayNameEntry().getText())) {
					new MessageDialogBuilder().setTitle("Create User Validation")
					.setText("That display name already exists. It must be unique.")
					.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
					.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
					valid = false;
					break;
				}
			}
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
					this.toEdit.getPassword(),
					this.toEdit.getId(),
					this.toEdit.getUserGroup());
			
			ModifyUserSummaryDialog summary = new ModifyUserSummaryDialog(
					"FINAL EDIT USER SUMMARY" , UserOperation.EDIT_USER, user, this.toEdit);
			summary.setHints(Arrays.asList(Hint.CENTERED));
			
			if (summary.showDialog(this.getTextGUI()))
				this.saveUser(user);
		}
	}
	
	private void saveUser(User user) {
		
		OperationResult result = FileIO.METHODS.modifyExistingUserInFile(UserOperation.EDIT_USER, user);
		
		new MessageDialogBuilder().setTitle("Edit User Results")
		.setText(result.getMessage())
		.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
		.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		
		if (result.isSuccess()) {
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
