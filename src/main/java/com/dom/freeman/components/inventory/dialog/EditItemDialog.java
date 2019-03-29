package com.dom.freeman.components.inventory.dialog;

import java.time.LocalDate;
import java.util.Arrays;

import com.dom.freeman.Global;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class EditItemDialog extends AbstractItemDialog {
	
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
			if (new MessageDialogBuilder().setTitle("Edit Item Final Summary")
			.setText(
					"Please carefully review item details before saving it\n" +
					"Old Item Type:\t\t" + this.oldItem.getType() + "\n" +
					"New Item Type:\t\t" + this.getTypeEntry().getText() + "\n\n" +
					"Old Item Quantity:\t" + this.oldItem.getQuantity() + "\n" +
					"New Item Quantity:\t" + this.getQuantityEntry().getText() + "\n\n" +
					"Old Item Unit:\t\t" + this.oldItem.getUnit().getCommonName() + "\n" +
					"New Item Unit:\t\t" + this.getUnitEntry().getSelectedItem().getCommonName() + "\n\n" +
					"Old Item Add Date:\t" + this.oldItem.getAddedFormatted() + "\n" +
					"New Item Add Date:\t" + this.getAddedEntry().getSelectedDate().format(Global.OBJECTS.getDateFormat()) + "\n\n" +
					"Old Item Expire Date:\t" + this.oldItem.getExpiresFormatted() + "\n" +
					"New Item Expire Date:\t" + this.getExpiresEntry().getSelectedDate().format(Global.OBJECTS.getDateFormat()))
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.Continue)
			.addButton(MessageDialogButton.Abort).build().showDialog(this.getTextGUI())
			.equals(MessageDialogButton.Continue)) {
				System.out.println("Save Complete");
			}
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
