package com.dom.freeman.components.users.dialog;

import java.util.Arrays;

import com.dom.freeman.FileIO;
import com.dom.freeman.Utility;
import com.dom.freeman.obj.FileOperation;
import com.dom.freeman.obj.User;
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
					this.toEdit.getId());
			
			ModifyUserSummaryDialog summary = new ModifyUserSummaryDialog(
					"FINAL EDIT USER SUMMARY" , FileOperation.EDIT, user, this.toEdit);
			summary.setHints(Arrays.asList(Hint.CENTERED));
			
			if (summary.showDialog(this.getTextGUI()))
				this.saveUser(user);
		}
	}
	
	private void saveUser(User user) {
		
		boolean write = FileIO.METHODS.modifyExistingUserInFile(FileOperation.EDIT, user);
		
		if (write) {
			new MessageDialogBuilder().setTitle("User Edited Successfully")
			.setText("User successfully updated.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			this.close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		} else {
			new MessageDialogBuilder().setTitle("Warning")
			.setText("Some error occurred and the user could not be updated. Please try again.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		}
	}

	@Override
	public void onCancel() {
		this.close();
	}

}
