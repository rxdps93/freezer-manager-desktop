package com.dom.freeman.components.inventory.dialog;

import com.dom.freeman.obj.Item;

public class EditItemDialog extends AbstractItemDialog {

	public EditItemDialog(String title, Item toEdit) {
		super(title);
		
		this.getTypeEntry().setText(toEdit.getType());
		this.getUnitEntry().setSelectedItem(toEdit.getUnit());
		this.getQuantityEntry().setText(Integer.toString(toEdit.getQuantity()));
		this.getAddedEntry().setSelectedDate(toEdit.getAdded());
		this.getExpiresEntry().setSelectedDate(toEdit.getExpires());
	}

	@Override
	public boolean validateItem() {
		return false;
	}

	@Override
	public void onSave() {
		System.out.println("Edit Complete!");
	}

	@Override
	public void onCancel() {
		this.close();
	}

}
