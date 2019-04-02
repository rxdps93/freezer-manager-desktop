package com.dom.freeman.components.inventory.dialog;

import java.time.LocalDate;
import java.time.Year;
import java.util.regex.Pattern;

import com.dom.freeman.components.DateInput;
import com.dom.freeman.components.InventoryComboBox;
import com.dom.freeman.obj.Freezer;
import com.dom.freeman.obj.Unit;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;

public abstract class AbstractModifyItemDialog extends DialogWindow {
	
	private TextBox typeEntry;
	private ComboBox<Unit> unitEntry;
	private TextBox quantityEntry;
	private ComboBox<Freezer> locEntry;
	private DateInput addedEntry;
	private DateInput expiresEntry;

	protected AbstractModifyItemDialog(String title) {
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
		this.typeEntry = new TextBox(new TerminalSize(30, 1));
		mainPanel.addComponent(this.typeEntry);

		// Unit
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Unit Type"));
		this.unitEntry = new InventoryComboBox<>();
		for (Unit u : Unit.values()) {
			if (!u.equals(Unit.ERROR))
				unitEntry.addItem(u);
		}
		mainPanel.addComponent(this.unitEntry.setPreferredSize(new TerminalSize(30, 1)));

		// Quantity
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Quantity"));
		this.quantityEntry = new TextBox(new TerminalSize(30, 1)).setValidationPattern(Pattern.compile("[0-9]+"));
		mainPanel.addComponent(this.quantityEntry);
		
		// Location
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Location"));
		this.locEntry = new InventoryComboBox<>();
		for (Freezer f : Freezer.values()) {
			if (!f.equals(Freezer.ERROR))
				this.locEntry.addItem(f);
		}
		mainPanel.addComponent(this.locEntry.setPreferredSize(new TerminalSize(30, 1)));

		// Expiration Date
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Expiration Date"));
		this.expiresEntry = new DateInput(Year.now().getValue(), Year.now().getValue() + 20);
		mainPanel.addComponent(this.expiresEntry);

		// Date Added (default to today)
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Date Added"));
		this.addedEntry = new DateInput(LocalDate.now());
		mainPanel.addComponent(this.addedEntry);

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
	
	public TextBox getTypeEntry() {
		return this.typeEntry;
	}
	
	public ComboBox<Unit> getUnitEntry() {
		return this.unitEntry;
	}
	
	public TextBox getQuantityEntry() {
		return this.quantityEntry;
	}
	
	public ComboBox<Freezer> getLocationEntry() {
		return this.locEntry;
	}
	
	public DateInput getAddedEntry() {
		return this.addedEntry;
	}
	
	public DateInput getExpiresEntry() {
		return this.expiresEntry;
	}

	public abstract boolean validateItem();
	
	public abstract void onSave();
	
	public abstract void onCancel();
}
