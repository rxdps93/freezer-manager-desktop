package com.dom.freeman.components.transactions;

import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.ViewPanel;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyType;

public class TransactionPanel extends ViewPanel {

	private Interactable interactable;

	public TransactionPanel() {
		super(KeyType.F6);
		this.configureContent();
	}

	public TransactionPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F6);
		this.configureContent();
	}

	private void configureContent() {

		//		Label content = new Label("Transaction Management Panel");
		//		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		//		this.addComponent(content);

		TestTable<String> table = new TestTable<>("Name", "Quantity", "Location", "Added", "Expires", "Id");
		table.setVisibleRows(60);
		table.refresh();
		table.hideLastColumn(true);

		table.setSelectAction(new Runnable() {
			@Override
			public void run() {

				List<String> data = table.getTableModel().getRow(table.getSelectedRow());
				String msg = String.format("Title: %s\nQuantity: %s\nLocation: %s\nAdded: %s\nExpires: %s\nID: %s", 
						data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5));

				new MessageDialogBuilder().setTitle("Item Description").setText(msg).setExtraWindowHints(Arrays.asList(Hint.CENTERED)).addButton(MessageDialogButton.Cancel)
				.build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
			}
		});

		table.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(table.withBorder(Borders.singleLine("Test Table")));
		this.interactable = table;
		Global.OBJECTS.registerTable(table);
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
