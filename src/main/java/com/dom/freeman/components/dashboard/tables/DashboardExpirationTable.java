package com.dom.freeman.components.dashboard.tables;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import com.dom.freeman.Global;
import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.SortMode;
import com.googlecode.lanterna.gui2.table.TableModel;

public class DashboardExpirationTable<V> extends AbstractInventoryTable<V> {

	private InventorySortMode lastSortMode = InventorySortMode.EXP_NEWER;
	
	public DashboardExpirationTable(String... columnLabels) {
		super(columnLabels);
	}
	
	@Override
	public void refresh() {
		this.sortTable(this.lastSortMode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TableModel<V> configureTableModel(String... columnLabels) {
		TableModel<V> model = new TableModel<>(columnLabels);
		
		for (Item item : Global.OBJECTS.getInventory()) {

			long remain = ChronoUnit.DAYS.between(LocalDate.now(), item.getExpires());
			if (remain >= 0 && remain <= 90) {
				model.addRow(
						(V)item.getType(),
						(V)String.format("%3d %s", item.getQuantity(), item.getUnit().getAbbreviationByValue(item.getQuantity())),
						(V)item.getLocation().getFreezerLocation(),
						(V)item.getExpiresFormatted().toString(),
						(V)Long.toString(remain));
			}
		}
		
		return model;
	}

	@Override
	public void sortTable(SortMode sortMode) {
		InventorySortMode invSortMode = (InventorySortMode)sortMode;
		Collections.sort(Global.OBJECTS.getInventory(), invSortMode.getSortMethod());
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
		this.lastSortMode = invSortMode;
	}

}
