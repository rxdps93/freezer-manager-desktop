package com.dom.freeman.components.inventory.dialog;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

import com.dom.freeman.Utility;
import com.dom.freeman.components.DateInput;
import com.dom.freeman.components.InventoryComboBox;
import com.dom.freeman.obj.Item;
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
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class AddItemDialog extends DialogWindow {

	private TextBox typeEntry;
	private ComboBox<Unit> unitEntry;
	private TextBox quantityEntry;
	private DateInput addedEntry;
	private DateInput expiresEntry;

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
	
	private boolean validateType() {
		if (this.typeEntry.getText().replace(" ", "").isEmpty()) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("You did not enter anything for item type. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			return false;
		}
		return true;
	}
	
	private boolean validateQuantity() {
		if (this.quantityEntry.getText().isEmpty()) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("You did not enter any value for item quantity. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			return false;
		}
		return true;
	}
	
	private boolean validateUnit() {
		if (!this.unitEntry.getSelectedItem().inRange(Integer.parseInt(this.quantityEntry.getText()))) {
			return (new MessageDialogBuilder().setTitle("Add Item Validation")
					.setText(String.format("%s\n%s\n\n%s\t\t%s\n%s\t%d",
							"The quantity and unit combination seem unusual - there may be a better unit of measurement.",
							"Proceed anyway?",
							"Unit:", this.unitEntry.getSelectedItem().getCommonName(),
							"Quantity:", Integer.parseInt(this.quantityEntry.getText())))
					.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
					.addButton(MessageDialogButton.Yes)
					.addButton(MessageDialogButton.No)
					.build().showDialog(this.getTextGUI()).equals(MessageDialogButton.Yes));
		}
		return true;
	}
	
	private boolean validateDates() {
		
		boolean result = true;
		
		if (!this.expiresEntry.getSelectedDate().isAfter(LocalDate.now())) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("The expiration date is INVALID. It must be a date in the future!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			result = false;
		}
		
		if (this.addedEntry.getSelectedDate().isAfter(LocalDate.now())) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("The added date is INVALID. It CANNOT be a future date!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			result = false;
		}
		
		if (!this.expiresEntry.getSelectedDate().isAfter(this.addedEntry.getSelectedDate())) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("One or both dates are INVALID. The expiration date CANNOT be before the added date!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			result = false;
		}
		
		return result;
	}

	private void onSave() {
		/*
		 * TODO: Perform the following steps to save:
		 * > Check if type is empty
		 * > Check if quantity is empty
		 * > Check if quantity is out of normal range for the unit
		 * > Check if expiration date is on or before today
		 * > Check if added date is after today
		 * > Check if expiration date is before added date
		 * > Present a summary of the item to user to accept or deny
		 * > Write item to end of CSV
		 * > Update list of items from CSV
		 */
		boolean result = true;

		// Item Type Validation
		result = this.validateType();

		// Quantity Validation
		if (result)
			result = this.validateQuantity();

		// Unit and Quantity Compatibility Validation
		if (result)
			result = this.validateUnit();

		// Date validation
		if (result)
			result = this.validateDates();

		// If still true we can present the summary
		if (result) {
			if (new MessageDialogBuilder().setTitle("Add Item Final Summary")
					.setText(String.format("Please carefully review item details before saving it\nItem Type:\t\t%s\nQuantity:\t\t%d %s\nAdded Date:\t\t%s\nExpire Date:\t%s\n", 
							this.typeEntry.getText(),
							Integer.parseInt(this.quantityEntry.getText()),
							this.unitEntry.getSelectedItem().getAbbreviationByValue(Integer.parseInt(this.quantityEntry.getText())),
							this.addedEntry.getSelectedDate(),
							this.expiresEntry.getSelectedDate()))
					.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
					.addButton(MessageDialogButton.Continue)
					.addButton(MessageDialogButton.Abort).build().showDialog(this.getTextGUI()).equals(MessageDialogButton.Continue)) {

				this.saveItem(new Item(
						this.typeEntry.getText(),
						Integer.parseInt(this.quantityEntry.getText()),
						this.unitEntry.getSelectedItem(),
						this.addedEntry.getSelectedDate(),
						this.expiresEntry.getSelectedDate(),
						UUID.randomUUID().toString()));
			}

		}

	}

	private void saveItem(Item item) {
		boolean write = Utility.METHODS.saveToFile(item);

		if (write) {
			new MessageDialogBuilder().setTitle("Item Added Successfully")
			.setText("Item successfully added to inventory!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		} else {
			new MessageDialogBuilder().setTitle("Warning")
			.setText("Some error occurred and the item could not be added. Please try again.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		}
	}

	private void onCancel() {
		close();
	}
}
