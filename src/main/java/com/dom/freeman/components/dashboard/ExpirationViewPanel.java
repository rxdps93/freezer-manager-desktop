package com.dom.freeman.components.dashboard;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.dashboard.tables.DashboardExpirationTable;
import com.dom.freeman.components.dashboard.tables.ExpirationTableCellRenderer;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class ExpirationViewPanel extends Panel {

	public ExpirationViewPanel() {
		super();
		this.configureContent();
	}

	public ExpirationViewPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.configureContent();
	}

	private void configureContent() {

		Panel panel = new Panel();

		DashboardExpirationTable<String> expirations = new DashboardExpirationTable<>("ITEM TYPE", "QUANTITY", "LOCATION", "EXPIRATION DATE", "DAYS LEFT");
		expirations.setVisibleRows(60);
		expirations.setResetSelectOnTab(true);
		expirations.setTableCellRenderer(new ExpirationTableCellRenderer<String>());
		
		expirations.sortTable(InventorySortMode.EXP_NEWER);

		expirations.setSelectAction(new Runnable() {
			@Override
			public void run() {
				List<String> data = expirations.getTableModel().getRow(expirations.getSelectedRow());

				String type = data.get(0);
				String location = data.get(2);
				LocalDate expires = LocalDate.parse(data.get(3), Global.OBJECTS.getDateFormat());
				long remain = Long.parseLong(data.get(4));

				StringBuilder info = new StringBuilder();
				info.append(String.format("Summary for %s:%n", type));
				info.append(String.format("This item is located in the: %s%n", location));
				info.append(String.format("This item expires on: %s%n", expires.toString()));
				if (remain != 0)
					info.append(String.format("%d days until expiration.", remain));
				else
					info.append("That is today!");

				new MessageDialogBuilder().setTitle("ITEM EXPIRATION SUMMARY").setText(info.toString())
				.addButton(MessageDialogButton.Close)
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
			}
		});

		panel.addComponent(expirations.setEscapeByArrowKey(false));
		Global.OBJECTS.registerTable(expirations);

		this.addComponent(panel.withBorder(Borders.singleLine("ITEMS EXPIRING WITHIN 90 DAYS")));
	}
}
