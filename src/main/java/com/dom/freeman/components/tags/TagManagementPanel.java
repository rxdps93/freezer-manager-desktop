package com.dom.freeman.components.tags;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.tags.dialog.EditItemTagDialog;
import com.dom.freeman.components.tags.dialog.ItemTagSummaryDialog;
import com.dom.freeman.components.tags.tables.ItemTagViewTable;
import com.dom.freeman.obj.ItemTag;
import com.dom.freeman.obj.OperationResult;
import com.dom.freeman.obj.users.UserOperation;
import com.dom.freeman.utils.FileIO;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class TagManagementPanel extends Panel {

	private ItemTagViewTable<String> tagList;

	public TagManagementPanel() {
		super();
		this.configureContent();
	}

	public TagManagementPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.configureContent();
	}

	private void configureContent() {

		Panel panel = new Panel();
		ItemTagViewTable<String> tagList = new ItemTagViewTable<>("TAG NAME", "ITEM COUNT");
		tagList.setVisibleRows(30);
		tagList.setResetSelectOnTab(true);

		tagList.refresh();

		tagList.setSelectAction(new Runnable() {
			@Override
			public void run() {
				new ActionListDialogBuilder().setTitle("SELECT AN ACTION")
				.addAction("View Item Tag Summary", new Runnable() {
					@Override
					public void run() {
						List<String> data = tagList.getTableModel().getRow(tagList.getSelectedRow());
						ItemTag tag = Utility.METHODS.getItemTagByName(data.get(0));

						ItemTagSummaryDialog summary = new ItemTagSummaryDialog("ITEM TAG SUMMARY", UserOperation.VIEW, tag);
						summary.setHints(Arrays.asList(Hint.CENTERED));
						summary.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Edit Item Tag", new Runnable() {
					@Override
					public void run() {
						List<String> data = tagList.getTableModel().getRow(tagList.getSelectedRow());
						EditItemTagDialog editItemTag = new EditItemTagDialog("EDIT ITEM TAG", Utility.METHODS.getItemTagByName(data.get(0)));
						editItemTag.setHints(Arrays.asList(Hint.CENTERED));
						editItemTag.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
					}
				})
				.addAction("Remove Item Tag", new Runnable() {
					@Override
					public void run() {
						List<String> data = tagList.getTableModel().getRow(tagList.getSelectedRow());
						ItemTag toRemove = Utility.METHODS.getItemTagByName(data.get(0));

						ItemTagSummaryDialog summary = new ItemTagSummaryDialog("Remove Item Tag Final Summary", UserOperation.REMOVE_ITEM_TAG, toRemove);
						summary.setHints(Arrays.asList(Hint.CENTERED));

						if (summary.showDialog(Global.OBJECTS.getMainWindow().getTextGUI())) {

							OperationResult result = FileIO.METHODS.modifyExistingItemTagsInFile(UserOperation.REMOVE_ITEM_TAG, toRemove);

							new MessageDialogBuilder().setTitle("Remove Item Results")
							.setText(result.getMessage())
							.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
							.addButton(MessageDialogButton.OK).build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());

							if (result.isSuccess()) {
								Utility.METHODS.updateInventory();
								Utility.METHODS.refreshViews();
							}
						}
					}
				})
				.build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
			}
		});

		panel.addComponent(tagList.setEscapeByArrowKey(false));
		this.tagList = tagList;
		Global.OBJECTS.registerTable(this.tagList);
		this.addComponent(panel.withBorder(Borders.singleLine("ITEM TAGS")));
	}

	public ItemTagViewTable<String> getTable() {
		return this.tagList;
	}

	public Interactable getInteractable() {
		return this.tagList;
	}
}
