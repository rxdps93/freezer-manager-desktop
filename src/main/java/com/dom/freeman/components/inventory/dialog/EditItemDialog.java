package com.dom.freeman.components.inventory.dialog;

import java.time.LocalDate;
import java.util.Arrays;

import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Utility;
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
		this.getLocationEntry().setSelectedItem(toEdit.getLocation());
		this.getAddedEntry().setSelectedDate(toEdit.getAdded());
		this.getExpiresEntry().setSelectedDate(toEdit.getExpires());
	}

	@Override
	public boolean validateItem() {
		
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
			Item newItem = new Item(
					this.getTypeEntry().getText(),
					Integer.parseInt(this.getQuantityEntry().getText()),
					this.getUnitEntry().getSelectedItem(),
					this.getLocationEntry().getSelectedItem(),
					this.getAddedEntry().getSelectedDate(),
					this.getExpiresEntry().getSelectedDate(),
					oldItem.getId());
			ModifyItemSummaryDialog summary = new ModifyItemSummaryDialog("Edit Item Final Summary", UserOperation.EDIT_ITEM, newItem, this.oldItem);
			summary.setHints(Arrays.asList(Hint.CENTERED));
			
			// If we hit save, proceed to make the change
			if (summary.showDialog(this.getTextGUI())) {
				this.saveItem(newItem);
			}
		}
	}

	@Override
	public void onCancel() {
		this.close();
	}
	
	private void saveItem(Item newItem) {
		
		OperationResult result = FileIO.METHODS.modifyExistingItemInFile(newItem, UserOperation.EDIT_ITEM);
		
		new MessageDialogBuilder().setTitle("Edit Item Results")
		.setText(result.getMessage())
		.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
		.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		
		if (result.isSuccess()) {
			this.close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		}
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
		if (this.getQuantityEntry().getText().isEmpty() || Integer.parseInt(this.getQuantityEntry().getText()) == 0) {
			
			String msg = "You did not enter any value for item quantity. This field cannot be empty.";
			if (Integer.parseInt(this.getQuantityEntry().getText()) == 0)
				msg = "You did not enter a valid value for item quantity. This field cannot be zero.";
				
			new MessageDialogBuilder().setTitle("Add Item Validation")
			.setText(msg)
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
