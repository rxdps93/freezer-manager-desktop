package com.dom.freeman.components.dashboard;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import com.dom.freeman.Global;
import com.dom.freeman.components.dashboard.tables.DashboardInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class InventoryViewPanel extends Panel {

	private Window parent;
	private DashboardInventoryTable<String> inventory;

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
		
		DashboardInventoryTable<String> inventory = new DashboardInventoryTable<>("ITEM TYPE", "QUANTITY", "LOCATION", "ADDED DATE", "EXPIRATION DATE");
		inventory.setVisibleRows(60);
		inventory.setResetSelectOnTab(true);

		inventory.sortTable(InventorySortMode.TYPE_ASC);

		inventory.setSelectAction(new Runnable() {
			@Override
			public void run() {
				List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());

				String type = data.get(0);
				String location = data.get(2);
				LocalDate added = LocalDate.parse(data.get(3), Global.OBJECTS.getDateFormat());
				LocalDate expires = LocalDate.parse(data.get(4), Global.OBJECTS.getDateFormat());
				LocalDate today = LocalDate.now();

				long daysIn = ChronoUnit.DAYS.between(added, today);
				long remain = ChronoUnit.DAYS.between(today, expires);

				StringBuilder info = new StringBuilder();
				info.append(String.format("Summary for %s:%n", type));
				info.append(String.format("This item is located in the: %s%n", location));
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
		Global.OBJECTS.registerTable(this.inventory);

		this.addComponent(panel.withBorder(Borders.singleLine("INVENTORY ITEMS ALPHABETICAL")));
	}
	
	public Interactable getInteractable() {
		return this.inventory;
	}
}
