package com.dom.freeman.components.tags.dialog;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

public abstract class AbstractModifyTagDialog extends DialogWindow {

	private TextBox nameEntry;
	private CheckBoxList<String> assignmentList;
	
	protected AbstractModifyTagDialog(String title, String assignLabel) {
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
		mainPanel.addComponent(new Label("Use arrow keys to navigate between fields.").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		
		// Item Name
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Tag Name"));
		this.nameEntry = new TextBox(new TerminalSize(30, 1));
		mainPanel.addComponent(this.nameEntry);
		
		// Optional Assignment
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label(assignLabel));
		this.assignmentList = this.configureAssignmentList();
		mainPanel.addComponent(this.assignmentList);
		
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

	public TextBox getNameEntry() {
		return this.nameEntry;
	}
	
	public CheckBoxList<String> getAssignmentList() {
		return this.assignmentList;
	}
	
	public abstract CheckBoxList<String> configureAssignmentList();
	
	public abstract boolean validateTag();
	
	public abstract void onSave();
	
	public abstract void onCancel();
}
