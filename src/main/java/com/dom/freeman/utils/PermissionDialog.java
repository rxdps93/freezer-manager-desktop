package com.dom.freeman.utils;

import java.util.Arrays;

import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserOperation;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class PermissionDialog extends DialogWindow {

	private Boolean result;

	protected PermissionDialog(String title, UserOperation requiredOp) {
		super(title);
		this.configureContent(requiredOp);
	}

	private void configureContent(UserOperation op) {

		Panel mainPanel = new Panel(new GridLayout(2));

		// Description
		mainPanel.addComponent(new Label(
				"You do not have permission to perform this operation.\n" +
				"If you would like temporary permission to perform this\n" +
				"operation, please contact a user with the proper\n" +
				"permissions (listed below) to enter their password\n" +
				"for you.").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

		// Display name
		mainPanel.addComponent(this.dialogSpacer(2));
		mainPanel.addComponent(new Label("Display name"));
		ComboBox<String> users = new ComboBox<>();
		for (User u : Global.OBJECTS.getUsers()) {
			if (u.hasPermission(op))
				users.addItem(u.getDisplayName());
		}
		users.setReadOnly(false);
		mainPanel.addComponent(users);

		// Password
		mainPanel.addComponent(this.dialogSpacer(2));
		mainPanel.addComponent(new Label("Password"));
		TextBox pass = new TextBox();
		pass.setMask('*');
		mainPanel.addComponent(pass);

		// Buttons
		Panel buttonPanel = new Panel(new GridLayout(3));
		buttonPanel.addComponent(new Button("Submit", new Runnable() {
			@Override
			public void run() {

				if (Utility.METHODS.validateUser(pass.getText(), users.getSelectedItem())) {
					result = true;
					close();
				} else {
					result = false;
					new MessageDialogBuilder().setTitle("Incorrect Password")
					.setText("The entered password does not match the one stored for the selected user.")
					.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
					.addButton(MessageDialogButton.OK).build().showDialog(getTextGUI());
				}
			}
		}));
		buttonPanel.addComponent(new Button("Cancel", new Runnable() {
			@Override
			public void run() {
				result = false;
				close();
			}
		}));
		buttonPanel.addComponent(new Button("Clear", new Runnable() {
			@Override
			public void run() {
				users.setSelectedIndex(0);
				pass.setText("");
			}
		}));
		mainPanel.addComponent(this.dialogSpacer(2));
		mainPanel.addComponent(buttonPanel.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));

		this.setComponent(mainPanel);
	}

	@Override
	public Boolean showDialog(WindowBasedTextGUI textGUI) {
		this.result = null;
		super.showDialog(textGUI);
		return result;
	}

	private EmptySpace dialogSpacer(int width) {
		return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(width));
	}

}
