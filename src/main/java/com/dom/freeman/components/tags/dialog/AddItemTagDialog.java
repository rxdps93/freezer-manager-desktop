package com.dom.freeman.components.tags.dialog;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.Global;
import com.dom.freeman.Utility;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class AddItemTagDialog extends AbstractModifyTagDialog {

	private List<Item> checkListItems;
	
	public AddItemTagDialog(String title) {
		super(title, "Associate Items\n[Optional]");
	}

	@Override
	public CheckBoxList<String> configureAssignmentList() {
		this.checkListItems = Global.OBJECTS.getInventory();
		this.checkListItems.sort(InventorySortMode.TYPE_ASC.getSortMethod());
		CheckBoxList<String> checkList = new CheckBoxList<>(new TerminalSize(50, 
				Math.min(this.checkListItems.size(), 50)));
		for (Item item : this.checkListItems) {
			checkList.addItem(item.toSimpleString());
		}
		return checkList;
	}

	@Override
	public boolean validateTag() {
		if (this.getNameEntry().getText().replace(" ", "").isEmpty()) {
			new MessageDialogBuilder().setTitle("Create Item Tag Validation")
			.setText("You did not enter anything for tag name. This field cannot be empty.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			return false;
		}
		
		for (ItemTag tag : Global.OBJECTS.getItemTags()) {
			if (this.getNameEntry().getText().equalsIgnoreCase(tag.getName())) {
				new MessageDialogBuilder().setTitle("Create Item Tag Validation")
				.setText("A tag with this name already exists. Please select a unique name.")
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void onSave() {
		if (this.validateTag()) {
			ItemTag tag = new ItemTag(
					this.getNameEntry().getText());
			for (int i = 0; i < this.getAssignmentList().getItemCount(); i++) {
				if (this.getAssignmentList().isChecked(i))
					tag.associateItems(this.checkListItems.get(i));
			}
			
			this.saveItem(tag);
		}
	}
	
	private void saveItem(ItemTag tag) {
		boolean write = Utility.METHODS.addNewItemTagToFile(tag);
		
		if (write) {
			new MessageDialogBuilder().setTitle("Item Tag Added Successfully")
			.setText("Item tag successfully saved.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
			this.close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		} else {
			new MessageDialogBuilder().setTitle("Warning")
			.setText("Some error occurred and the item tag could not be saved. Please try again.")
			.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
			.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		}
	}

	@Override
	public void onCancel() {
		this.close();
	}

}
