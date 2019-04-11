package com.dom.freeman.components.users.dialog;

import com.dom.freeman.obj.User;
import com.dom.freeman.obj.UserPermission;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.table.Table;

public class ViewUserSummaryDialog extends DialogWindow {

	public ViewUserSummaryDialog(String title, User user) {
		super(title);
		this.configureContent(user);
	}
	
	private void configureContent(User user) {
		
		Panel mainPanel = new Panel(new GridLayout(2));
		
		// Description
		mainPanel.addComponent(new Label("Summary for selected user").setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER,
						false, false, 2, 1)));
		
		// User details
		mainPanel.addComponent(this.dialogSpacer());
		
		mainPanel.addComponent(new Label("First Name").addStyle(SGR.BOLD));
		mainPanel.addComponent(new Label(user.getFirstName()));
		
		mainPanel.addComponent(new Label("Last Name").addStyle(SGR.BOLD));
		mainPanel.addComponent(new Label(user.getLastName()));
		
		mainPanel.addComponent(new Label("Display Name").addStyle(SGR.BOLD));
		mainPanel.addComponent(new Label(user.getDisplayName()));
		
		// User Permissions
		mainPanel.addComponent(this.dialogSpacer());
		if (user.getUserPermissions().size() == 0) {
			mainPanel.addComponent(new Label("This user has no permissions.").setLayoutData(
					GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER,
							false, false, 2, 1)));
		} else {
			Table<String> permissions = new Table<String>("PERMISSION", "DESCRIPTION");
			permissions.setEnabled(false);
			for (UserPermission permission : user.getUserPermissions()) {
				permissions.getTableModel().addRow(
						permission.toString(), permission.getDescription());
			}
			permissions.setLayoutData(GridLayout.createLayoutData(
					Alignment.FILL, Alignment.FILL, true, true, 2, 1));
			mainPanel.addComponent(permissions.withBorder(Borders.singleLine("Permissions")));
		}
		
		// Buttons
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
		mainPanel.addComponent(new Button(LocalizedString.OK.toString(), new Runnable() {
			@Override
			public void run() {
				close();
			}
		}));
		
		this.setComponent(mainPanel);
	}
	
	private EmptySpace dialogSpacer() {
		return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
	}
}
