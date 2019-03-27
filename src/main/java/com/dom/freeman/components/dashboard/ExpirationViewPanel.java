package com.dom.freeman.components.dashboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dom.freeman.components.InventoryTable;
import com.dom.freeman.obj.ExpirationTableCellRenderer;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class ExpirationViewPanel extends Panel {

	private Window parent;

	public ExpirationViewPanel(List<Item> items, Window parent) {
		super();
		this.parent = parent;
		this.configureContent(items);
	}

	public ExpirationViewPanel(LayoutManager layoutManager, List<Item> items, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent(items);
	}

	private void configureContent(List<Item> items) {

		Panel panel = new Panel();

		InventoryTable<String> expirations = new InventoryTable<>("ITEM TYPE", "QUANTITY", "EXPIRATION DATE", "DAYS LEFT");
		expirations.setVisibleRows(60);
		expirations.setResetSelectOnTab(true);
		expirations.setTableCellRenderer(new ExpirationTableCellRenderer<String>());

		Collections.sort(items, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {

				return o1.getExpires().compareTo(o2.getExpires());
			}

		});

		for (Item item : items) {

			long remain = ChronoUnit.DAYS.between(LocalDate.now(), item.getExpires());
			if (remain >= 0 && remain <= 90) {
				expirations.getTableModel().addRow(
						item.getType(),
						String.format("%3d %s", item.getQuantity(), item.getUnit().getAbbreviationByValue(item.getQuantity())),
						item.getExpiresFormatted().toString(),
						Long.toString(remain));
			}
		}

		expirations.setSelectAction(new Runnable() {
			@Override
			public void run() {
				List<String> data = expirations.getTableModel().getRow(expirations.getSelectedRow());

				String type = data.get(0);
				LocalDate expires = LocalDate.parse(data.get(2));
				long remain = Long.parseLong(data.get(3));

				StringBuilder info = new StringBuilder();
				info.append(String.format("Summary for %s:%n", type));
				info.append(String.format("This item expires on: %s%n", expires.toString()));
				if (remain != 0)
					info.append(String.format("%d days until expiration.", remain));
				else
					info.append("That is today!");

				new MessageDialogBuilder().setTitle("ITEM EXPIRATION SUMMARY").setText(info.toString())
				.addButton(MessageDialogButton.Close)
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.build().showDialog(parent.getTextGUI());
			}
		});

		panel.addComponent(expirations.setEscapeByArrowKey(false));

		this.addComponent(panel.withBorder(Borders.singleLine("ITEMS EXPIRING WITHIN 90 DAYS")));
	}
}
