package com.dom.freeman.components.inventory.dialog;

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
		
		// Item Type
		mainPanel.addComponent(new Label("Item Type"));
		mainPanel.addComponent(new TextBox(new TerminalSize(30, 1)));
		
		// Unit
		mainPanel.addComponent(new Label("Unit Type"));
		ComboBox<Unit> units = new ComboBox<>();
		for (Unit u : Unit.values()) {
			if (!u.equals(Unit.ERROR))
				units.addItem(u);
		}
		mainPanel.addComponent(units.setPreferredSize(new TerminalSize(30, 1)));
		
		// Quantity
		mainPanel.addComponent(new Label("Quantity"));
		mainPanel.addComponent(new TextBox(new TerminalSize(30, 1)));
		
		// Expiration Date
		
		// Date Added (default to today)
		
		buttonPanel.setLayoutData(GridLayout.createLayoutData(
				Alignment.END, Alignment.CENTER,
				false, false,
				2, 1)).addTo(mainPanel);
		this.setComponent(mainPanel);
	}

	private void onSave() {
		System.out.println("Save");
	}
	
	private void onCancel() {
		close();
	}
}
