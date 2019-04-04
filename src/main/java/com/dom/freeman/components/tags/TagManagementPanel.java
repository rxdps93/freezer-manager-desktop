package com.dom.freeman.components.tags;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.Global;
import com.dom.freeman.Utility;
import com.dom.freeman.components.tags.dialog.EditItemTagDialog;
import com.dom.freeman.components.tags.tables.ItemTagViewTable;
import com.dom.freeman.obj.ItemTag;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class TagManagementPanel extends Panel {

	private Window parent;
	private ItemTagViewTable<String> tagList;

	public TagManagementPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}

	public TagManagementPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
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

						StringBuilder msg = new StringBuilder();
						for (String id : tag.getAssociatedItemIds())
							msg.append(Utility.METHODS.getItemById(id).toString() + "\n");

						new MessageDialogBuilder().setTitle("TAG SUMMARY")
						.setText(msg.toString())
						.addButton(MessageDialogButton.Close)
						.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
						.build().showDialog(parent.getTextGUI());
					}
				})
				.addAction("Edit Item Tag", new Runnable() {
					@Override
					public void run() {
						List<String> data = tagList.getTableModel().getRow(tagList.getSelectedRow());
						EditItemTagDialog editItemTag = new EditItemTagDialog("EDIT ITEM TAG", Utility.METHODS.getItemTagByName(data.get(0)));
						editItemTag.setHints(Arrays.asList(Hint.CENTERED));
						editItemTag.showDialog(parent.getTextGUI());
					}
				}).build().showDialog(parent.getTextGUI());
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
