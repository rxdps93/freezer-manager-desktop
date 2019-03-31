package com.dom.freeman.components.inventory.dialog;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import com.dom.freeman.Global;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class EditItemDialog extends AbstractModifyItemDialog {
	
	private Item oldItem;

	public EditItemDialog(String title, Item toEdit) {
		super(title);

		this.oldItem = toEdit;
		this.getTypeEntry().setText(toEdit.getType());
		this.getUnitEntry().setSelectedItem(toEdit.getUnit());
		this.getQuantityEntry().setText(Integer.toString(toEdit.getQuantity()));
		this.getAddedEntry().setSelectedDate(toEdit.getAdded());
		this.getExpiresEntry().setSelectedDate(toEdit.getExpires());
	}

	@Override
	public boolean validateItem() {
		/*
		 * TODO: Perform the following steps to save:
		 * > Check if type is empty
		 * > Check if quantity is empty
		 * > Check if quantity is out of normal range for the unit
		 * > Check if expiration date is on or before today
		 * > Check if added date is after today
		 * > Check if expiration date is before added date
		 * > Present a summary of the item to user to accept or deny
		 * > Show both old and new values in the summary
		 * > Read CSV into memory again
		 * > Edit item in the list
		 * > Write to file
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

		return result;
	}

	@Override
	public void onSave() {
		// If still true we can present the summary
		if (validateItem()) {
			// TODO: Make a custom dialog for this, Change to a grid layout for better format
			Item newItem = new Item(
					this.getTypeEntry().getText(),
					Integer.parseInt(this.getQuantityEntry().getText()),
					this.getUnitEntry().getSelectedItem(),
					this.getAddedEntry().getSelectedDate(),
					this.getExpiresEntry().getSelectedDate(),
					UUID.randomUUID().toString());
			ItemSummaryDialog summary = new ItemSummaryDialog("Edit Item Final Summary", newItem, this.oldItem);
			summary.setHints(Arrays.asList(Hint.CENTERED));
			summary.showDialog(this.getTextGUI());
		}
	}

	@Override
	public void onCancel() {
		this.close();
	}
	
	private void saveItem(Item newItem) {
		
	}

	private boolean validateType() {
		if (this.getTypeEntry().getText().replace(" ", "").isEmpty()) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("You did not enter anything for item type. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			return false;
		}
		return true;
	}

	private boolean validateQuantity() {
		if (this.getQuantityEntry().getText().isEmpty()) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("You did not enter any value for item quantity. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			return false;
		}
		return true;
	}

	private boolean validateUnit() {
		if (!this.getUnitEntry().getSelectedItem().inRange(Integer.parseInt(this.getQuantityEntry().getText()))) {
			return (new MessageDialogBuilder().setTitle("Add Item Validation")
					.setText(String.format("%s\n%s\n\n%s\t\t%s\n%s\t%d",
							"The quantity and unit combination seem unusual - there may be a better unit of measurement.",
							"Proceed anyway?",
							"Unit:", this.getUnitEntry().getSelectedItem().getCommonName(),
							"Quantity:", Integer.parseInt(this.getQuantityEntry().getText())))
					.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
					.addButton(MessageDialogButton.Yes)
					.addButton(MessageDialogButton.No)
					.build().showDialog(this.getTextGUI()).equals(MessageDialogButton.Yes));
		}
		return true;
	}

	private boolean validateDates() {

		boolean result = true;

		if (!this.getExpiresEntry().getSelectedDate().isAfter(LocalDate.now())) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("The expiration date is INVALID. It must be a date in the future!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			result = false;
		}

		if (this.getAddedEntry().getSelectedDate().isAfter(LocalDate.now())) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("The added date is INVALID. It CANNOT be a future date!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			result = false;
		}

		if (!this.getExpiresEntry().getSelectedDate().isAfter(this.getAddedEntry().getSelectedDate())) {
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText("One or both dates are INVALID. The expiration date CANNOT be before the added date!")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			result = false;
		}

		return result;
	}

}
