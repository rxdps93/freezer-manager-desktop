package com.dom.freeman.components.inventory.dialog;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBoxList;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class AssignItemTagDialog extends DialogWindow {

	private CheckBoxList<String> assignmentList;
	
	public AssignItemTagDialog(String title, Item selectedItem) {
		super(title);
		
		Panel buttonPanel = new Panel(new GridLayout(2).setHorizontalSpacing(1));
		buttonPanel.addComponent(new Button(LocalizedString.Save.toString(), new Runnable() {
			@Override
			public void run() {
				onSave(selectedItem);
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
		mainPanel.addComponent(new Label("Select the item tags to associate with the selected item.").setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		
		// Item Name
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Selected Item"));
		mainPanel.addComponent(new Label(String.format("%s, %3d %s, %s", 
				selectedItem.getType(),
				selectedItem.getQuantity(),
				selectedItem.getUnit().getAbbreviationByValue(selectedItem.getQuantity()),
				selectedItem.getLocation().getFreezerLocation())));
		
		// Tag Association
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new Label("Select tags for your selected item:"));
		if (Global.OBJECTS.getItemTags().size() == 0) {
			mainPanel.addComponent(new Label("No item tags found"));
		} else {
			this.assignmentList = this.configureTagList(selectedItem);
			mainPanel.addComponent(this.assignmentList);
		}
		
		mainPanel.addComponent(this.dialogSpacer());
		buttonPanel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER, Alignment.CENTER,
				false, false, 2, 1)).addTo(mainPanel);
		
		this.setComponent(mainPanel);
	}
	
	private void onSave(Item selectedItem) {

		ItemTag[] toModify = new ItemTag[this.assignmentList.getCheckedItems().size()];
		for (int i = 0; i < this.assignmentList.getCheckedItems().size(); i++) {
			toModify[i] = Utility.METHODS.getItemTagByName(this.assignmentList.getCheckedItems().get(i));
			toModify[i].associateItems(selectedItem);
		}
		
		OperationResult result = FileIO.METHODS.modifyExistingItemTagsInFile(UserOperation.EDIT_ITEM_TAG, toModify);
		
		new MessageDialogBuilder().setTitle("Item Tag Association Results")
		.setText(result.getMessage())
		.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
		.addButton(MessageDialogButton.OK).build().showDialog(this.getTextGUI());
		
		if (result.isSuccess()) {
			this.close();
			Utility.METHODS.updateInventory();
			Utility.METHODS.refreshViews();
		}
	}
	
	private void onCancel() {
		this.close();
	}
	
	private CheckBoxList<String> configureTagList(Item selectedItem) {
		List<ItemTag> tags = Global.OBJECTS.getItemTags();
		CheckBoxList<String> checkList = new CheckBoxList<>(new TerminalSize(50,
				Math.min(tags.size(), 50)));
		for (ItemTag tag : tags) {
			checkList.addItem(tag.getName(), tag.isItemAssociated(selectedItem));
		}
		return checkList;
	}
	
	private EmptySpace dialogSpacer() {
		return new EmptySpace(TerminalSize.ONE).setLayoutData(
				GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER,
						false, false, 2, 1));
	}

}
