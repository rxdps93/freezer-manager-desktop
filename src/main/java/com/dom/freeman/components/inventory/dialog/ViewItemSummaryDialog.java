package com.dom.freeman.components.inventory.dialog;

import java.util.List;

import com.dom.freeman.Utility;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LocalizedString;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.dialogs.DialogWindow;
import com.googlecode.lanterna.gui2.table.Table;

public class ViewItemSummaryDialog extends DialogWindow {

	public ViewItemSummaryDialog(String title, Item item) {
		super(title);
		this.configureContent(item);
	}

	private void configureContent(Item item) {

		Panel mainPanel = new Panel(new GridLayout(2));

		// Description
		mainPanel.addComponent(new Label("Summary for selected item").setLayoutData(
				GridLayout.createLayoutData(Alignment.BEGINNING, Alignment.CENTER,
						false, false, 2, 1)));

		// Item Details Table
		Table<String> summary = new Table<>("Item Type", "Quantity", "Unit", "Location", "Add Date", "Expires");

		summary.getTableModel().addRow(
				item.getType(),
				Integer.toString(item.getQuantity()),
				item.getUnit().getCommonName(),
				item.getLocation().getFreezerLocation(),
				item.getAddedFormatted(),
				item.getExpiresFormatted());

		summary.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER, Alignment.CENTER, true, true, 2, 1));
		summary.setEnabled(false);
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(summary);

		Panel itemTagPanel = new Panel(new GridLayout(3)).setLayoutData(
				GridLayout.createHorizontallyFilledLayoutData(2));

		List<ItemTag> associatedTags = Utility.METHODS.getTagsByItem(item);
		if (associatedTags.size() > 0) {
			for (int i = 0; i < associatedTags.size(); i++) {
				itemTagPanel.addComponent(new Label(associatedTags.get(i).getName()).setLayoutData(
						GridLayout.createHorizontallyFilledLayoutData(1)));
			}

			// Will add 1 or 2 empty spaces to finish off the final row, if needed
			for (int i = 0; i < (3 - (associatedTags.size() % 3)) % 3; i++) {
				itemTagPanel.addComponent(new EmptySpace(TerminalSize.ONE)
						.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1)));
			}
		} else {
			itemTagPanel.addComponent(new Label("No item tags found for this item")
					.setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.CENTER,
							true, false, 3, 1)));
		}

		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(itemTagPanel.withBorder(Borders.singleLine("Item Tags")));

		// Buttons
		mainPanel.addComponent(this.dialogSpacer());
		mainPanel.addComponent(new EmptySpace(TerminalSize.ONE));
		mainPanel.addComponent(new Button(LocalizedString.OK.toString(), new Runnable() {
			@Override
			public void run() {
				close();
			}
		}).setLayoutData(GridLayout.createLayoutData(Alignment.END, Alignment.CENTER, false, false)));

		this.setComponent(mainPanel);
	}

	private EmptySpace dialogSpacer() {
		return new EmptySpace().setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2));
	}
}
