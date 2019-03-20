package com.dom.freeman.components;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.dom.freeman.obj.ExpirationTableCellRenderer;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class MainWindow extends BasicWindow {

	private final List<Item> items;
	private final Map<String, Integer> types;

	private final String header;
	private final String[] actions = {
			"MANAGE INVENTORY",
			"MANAGE TAGS",
			"MANAGE TYPES",
			"MANAGE USERS",
			"TRANSACTION LOGS",
			"NEXT TABLE",
			"PREV TABLE"
	};

	private final String[] buttons = {
			"F1",
			"F2",
			"F3",
			"F4",
			"F5",
			"TAB",
			"SHIFT TAB"
	};

	public MainWindow(String header, List<Item> items, Map<String, Integer> types) {
		super();
		this.header = header;
		this.items = items;
		this.types = types;
		this.configureContent();
	}

	public MainWindow(String title, String header, List<Item> items, Map<String, Integer> types) { 
		super(title);
		this.header = header;
		this.items = items;
		this.types = types;
		this.configureContent();
	}

	private void configureInventoryPanel(Panel parent) {

		Panel panel = new Panel();
		InventoryTable<String> inventory = new InventoryTable<>("ITEM TYPE", "QUANTITY", "ADDED DATE", "EXPIRATION DATE");
		inventory.setVisibleRows(60);
		inventory.setResetSelectOnTab(true);

		Collections.sort(items, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {

				return o1.getType().compareTo(o2.getType());
			}

		});

		for (Item item : items) {
			inventory.getTableModel().addRow(
					item.getType(),
					String.format("%3d %s", item.getQuantity(), item.getUnit()),
					item.getAdded().toString(),
					item.getExpires().toString());
		}

		inventory.setSelectAction(new Runnable() {
			@Override
			public void run() {
				List<String> data = inventory.getTableModel().getRow(inventory.getSelectedRow());

				String type = data.get(0);
				LocalDate added = LocalDate.parse(data.get(2));
				LocalDate expires = LocalDate.parse(data.get(3));
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
				.build().showDialog(getTextGUI());
			}
		});

		panel.addComponent(inventory.setEscapeByArrowKey(false));
		panel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER,
				Alignment.BEGINNING,
				true,
				false));

		parent.addComponent(panel.withBorder(Borders.singleLine("INVENTORY ITEMS ALPHABETICAL")));
	}

	private void configureExpirationPanel(Panel parent) {

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
						String.format("%3d %s", item.getQuantity(), item.getUnit()),
						item.getExpires().toString(),
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
					info.append("This item expires today!");

				new MessageDialogBuilder().setTitle("ITEM EXPIRATION SUMMARY").setText(info.toString())
				.addButton(MessageDialogButton.Close)
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.build().showDialog(getTextGUI());
			}
		});

		panel.addComponent(expirations.setEscapeByArrowKey(false));
		panel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER,
				Alignment.BEGINNING,
				true,
				false));

		parent.addComponent(panel.withBorder(Borders.singleLine("ITEMS EXPIRING WITHIN 90 DAYS")));
	}

	private void configureTypeCountPanel(Panel parent) {

		Panel panel = new Panel();

		InventoryTable<String> typeCount = new InventoryTable<>("ITEM TYPE", "ITEM COUNT", "EARLIEST EXPIRATION");
		typeCount.setVisibleRows(60);
		typeCount.setResetSelectOnTab(true);

		LocalDate earliestExpiration;
		for (Entry<String, Integer> entry : types.entrySet()) {

			earliestExpiration = null;
			for (Item item : items) {

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
				.build().showDialog(getTextGUI());
			}
		});

		panel.addComponent(typeCount.setEscapeByArrowKey(false));
		panel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER,
				Alignment.BEGINNING,
				true,
				false));

		parent.addComponent(panel.withBorder(Borders.singleLine("TYPE COUNT VIEW")));
	}

	private void configureHeaderLabel(Panel parent) {

		Label headerLabel = new Label(this.header);

		headerLabel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER,
				Alignment.CENTER,
				true,
				true,
				3,
				1));

		parent.addComponent(headerLabel);
	}

	private void configureActionsLabel(Panel parent) {

		StringBuilder actionMessage = new StringBuilder();

		for (int i = 0; i < this.actions.length; i++) {
			actionMessage.append(StringUtils.center(String.format("[%s=%s]", this.buttons[i], this.actions[i]), 25));
		}

		Label actionLabel = new Label(actionMessage.toString());
		actionLabel.setLayoutData(GridLayout.createLayoutData(
				GridLayout.Alignment.CENTER,
				GridLayout.Alignment.CENTER,
				true,
				true,
				3,
				1));

		parent.addComponent(actionLabel);
	}

	private void configureContent() {

		Panel mainPanel = new Panel(new GridLayout(3));
		configureHeaderLabel(mainPanel);

		configureInventoryPanel(mainPanel);
		configureExpirationPanel(mainPanel);
		configureTypeCountPanel(mainPanel);

		configureActionsLabel(mainPanel);

		this.setComponent(mainPanel);
		this.setHints(Arrays.asList(Hint.EXPANDED, Hint.FIXED_POSITION));
		this.setPosition(new TerminalPosition(1, 2));
	}
}
