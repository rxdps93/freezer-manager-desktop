package com.dom.freeman.components.dashboard;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.dom.freeman.Global;
import com.dom.freeman.components.InventoryTable;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class TypeCountViewPanel extends Panel{

	private Window parent;

	public TypeCountViewPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}

	public TypeCountViewPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}

	private void configureContent() {

		Panel panel = new Panel();

		InventoryTable<String> typeCount = new InventoryTable<>("ITEM TYPE", "ITEM COUNT", "EARLIEST EXPIRATION");
		typeCount.setVisibleRows(60);
		typeCount.setResetSelectOnTab(true);

		LocalDate earliestExpiration;
		for (Entry<String, Integer> entry : Global.OBJECTS.getTypes().entrySet()) {

			earliestExpiration = null;
			for (Item item : Global.OBJECTS.getInventory()) {

				if (item.getType().equals(entry.getKey())) {
					if (earliestExpiration == null || earliestExpiration.isAfter(item.getExpires()))
						earliestExpiration = item.getExpires();
				}
			}

			typeCount.getTableModel().addRow(
					entry.getKey(),
					Integer.toString(entry.getValue()),
					earliestExpiration.toString());
		}

		typeCount.setSelectAction(new Runnable() {
			@Override
			public void run() {

				List<String> data = typeCount.getTableModel().getRow(typeCount.getSelectedRow());

				String type = data.get(0);
				int count = Integer.parseInt(data.get(1));
				LocalDate date = LocalDate.parse(data.get(2));

				StringBuilder info = new StringBuilder();
				info.append(String.format("Summary for %s:%n", type));
				if (count == 1) {
					info.append(String.format("There is %d entry for this item type.%n", count));
					info.append("This item ");
				} else {
					info.append(String.format("There are %d entries for this item type.%n", count));
					info.append("The first instance of this item ");
				}


				if (date.equals(LocalDate.now()))
					info.append("expires today!\n");
				else if (date.isAfter(LocalDate.now()))
					info.append(String.format("expires on %s.%n", date.toString()));
				else
					info.append(String.format("has expired! It expired on %s.%n", date.toString()));


				new MessageDialogBuilder().setTitle("ITEM TYPE COUNT SUMMARY").setText(info.toString())
				.addButton(MessageDialogButton.Close)
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.build().showDialog(parent.getTextGUI());
			}
		});

		panel.addComponent(typeCount.setEscapeByArrowKey(false));

		this.addComponent(panel.withBorder(Borders.singleLine("TYPE COUNT VIEW")));
	}
}
