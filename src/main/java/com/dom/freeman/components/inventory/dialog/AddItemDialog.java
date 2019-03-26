package com.dom.freeman.components.inventory.dialog;

import java.time.LocalDate;
import java.time.Year;

import com.dom.freeman.components.DateInput;
import com.dom.freeman.obj.Unit;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

public class AddItemDialog extends DialogWindow {

	public AddItemDialog(String title) {
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
		mainPanel.addComponent(new Label("Use arrow keys to navigate between fields").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		
		// Item Type
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Item Type"));
		mainPanel.addComponent(new TextBox(new TerminalSize(30, 1)));
		
		// Unit
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Unit Type"));
		ComboBox<Unit> units = new ComboBox<>();
		for (Unit u : Unit.values()) {
			if (!u.equals(Unit.ERROR))
				units.addItem(u);
		}
		mainPanel.addComponent(units.setPreferredSize(new TerminalSize(30, 1)));
		
		// Quantity
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Quantity"));
		mainPanel.addComponent(new TextBox(new TerminalSize(30, 1)));
		
		// Expiration Date
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Expiration Date"));
		mainPanel.addComponent(new DateInput(Year.now().getValue(), Year.now().getValue() + 20));
		
		// Date Added (default to today)
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Date Added"));
		mainPanel.addComponent(new DateInput(LocalDate.now()));
		
		// Buttons
		mainPanel.addComponent(this.dialogSpacer());
		buttonPanel.setLayoutData(GridLayout.createLayoutData(
				Alignment.END, Alignment.CENTER,
				false, false,
				2, 1)).addTo(mainPanel);
		this.setComponent(mainPanel);
	}
	
	private EmptySpace dialogSpacer() {
		return new EmptySpace(TerminalSize.ONE).setLayoutData(
				GridLayout.createLayoutData(
						Alignment.CENTER, Alignment.CENTER,
						false, false, 2, 1));
	}

	private void onSave() {
		System.out.println("Save");
	}
	
	private void onCancel() {
		close();
	}
}
