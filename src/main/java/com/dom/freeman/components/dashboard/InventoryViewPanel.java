package com.dom.freeman.components.dashboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

public class InventoryViewPanel extends Panel {

	private Window parent;
	private InventoryTable<String> inventory;

	public InventoryViewPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}

	public InventoryViewPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}

	private void configureContent() {

		Panel panel = new Panel();
		
		InventoryTable<String> inventory = new InventoryTable<>("ITEM TYPE", "QUANTITY", "ADDED DATE", "EXPIRATION DATE");
		inventory.setVisibleRows(60);
		inventory.setResetSelectOnTab(true);

		Collections.sort(Global.OBJECTS.getInventory(), new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {

				return o1.getType().compareTo(o2.getType());
			}

		});

		for (Item item : Global.OBJECTS.getInventory()) {
			inventory.getTableModel().addRow(
					item.getType(),
					String.format("%3d %s", item.getQuantity(), item.getUnit().getAbbreviationByValue(item.getQuantity())),
					item.getAddedFormatted(),
					item.getExpiresFormatted());
		}

		inventory.setSelectAction(new Runnable() {
			@Override
			public void run() {
				List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());

				String type = data.get(0);
				LocalDate added = LocalDate.parse(data.get(2), Global.OBJECTS.getDateFormat());
				LocalDate expires = LocalDate.parse(data.get(3), Global.OBJECTS.getDateFormat());
				LocalDate today = LocalDate.now();

				long daysIn = ChronoUnit.DAYS.between(added, today);
				long remain = ChronoUnit.DAYS.between(today, expires);

				StringBuilder info = new StringBuilder();
				info.append(String.format("Summary for %s:%n", type));
				info.append(String.format("In freezer for %,d days.%n", daysIn));
				if (remain != 0)
					info.append(String.format("%,d %s %s expiration.", Math.abs(remain), (Math.abs(remain) > 1 ? "days" : "day"), (remain > 0 ? "until" : "past")));
				else
					info.append("This item expires today!");

				new MessageDialogBuilder().setTitle("ITEM SUMMARY").setText(info.toString())
				.addButton(MessageDialogButton.Close)
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.build().showDialog(parent.getTextGUI());
			}
		});

		panel.addComponent(inventory.setEscapeByArrowKey(false));
		this.inventory = inventory;

		this.addComponent(panel.withBorder(Borders.singleLine("INVENTORY ITEMS ALPHABETICAL")));
	}
	
	public InventoryTable<String> getInteractable() {
		return this.inventory;
	}
}
