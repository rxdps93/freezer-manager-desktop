package com.dom.freeman.components.users.dialog;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

public abstract class AbstractModifyUserDialog extends DialogWindow {

	private TextBox firstNameEntry;
	private TextBox lastNameEntry;
	private TextBox displayNameEntry;
	
	protected AbstractModifyUserDialog(String title) {
		super(title);
		
		Panel buttonPanel = new Panel(new GridLayout(2).setHorizontalSpacing(1));
		buttonPanel.addComponent(new Button(LocalizedString.Save.toString(), new Runnable() {
			@Override
			public void run() {
				onSave();
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
		mainPanel.addComponent(new Label("User arrow keys to navigate between fields.")
				.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		
		// First Name
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("First Name"));
		this.firstNameEntry = new TextBox(new TerminalSize(30, 1));
		mainPanel.addComponent(this.firstNameEntry);
		
		// Last Name
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Last Name"));
		this.lastNameEntry = new TextBox(new TerminalSize(30, 1));
		mainPanel.addComponent(this.lastNameEntry);
		
		// Display Name
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Display Name"));
		this.displayNameEntry = new TextBox(new TerminalSize(30, 1));
		mainPanel.addComponent(this.displayNameEntry);
		
		mainPanel.addComponent(this.dialogSpacer());
		buttonPanel.setLayoutData(GridLayout.createLayoutData(
				Alignment.END, Alignment.CENTER,
				false, false, 2, 1)).addTo(mainPanel);
		this.setComponent(mainPanel);
	}
	
	private EmptySpace dialogSpacer() {
		return new EmptySpace(TerminalSize.ONE).setLayoutData(
				GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER,
						false, false, 2, 1));
	}
	
	public TextBox getFirstNameEntry() {
		return this.firstNameEntry;
	}
	
	public TextBox getLastNameEntry() {
		return this.lastNameEntry;
	}
	
	public TextBox getDisplayNameEntry() {
		return this.displayNameEntry;
	}
	
	public abstract boolean validateUser();
	
	public abstract void onSave();
	
	public abstract void onCancel();
}
