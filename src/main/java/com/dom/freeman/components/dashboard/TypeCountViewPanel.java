package com.dom.freeman.components.dashboard;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.dom.freeman.components.dashboard.tables.DashboardTypeCountTable;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class TypeCountViewPanel extends Panel {

	public TypeCountViewPanel() {
		super();
		this.configureContent();
	}

	public TypeCountViewPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.configureContent();
	}

	private void configureContent() {

		Panel panel = new Panel();

		DashboardTypeCountTable<String> typeCount = new DashboardTypeCountTable<>("ITEM TYPE", "ITEM COUNT", "EARLIEST EXPIRATION");
		typeCount.setVisibleRows(60);
		typeCount.setResetSelectOnTab(true);
		
		typeCount.sortTable(null);

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
				.build().showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
			}
		});

		panel.addComponent(typeCount.setEscapeByArrowKey(false));
		Global.OBJECTS.registerTable(typeCount);

		this.addComponent(panel.withBorder(Borders.singleLine("TYPE COUNT VIEW")));
	}
}
