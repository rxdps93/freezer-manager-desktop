package com.dom.freeman.components.dashboard.tables;

import java.util.Collections;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.SortMode;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.table.TableModel;

public class DashboardInventoryTable<V> extends AbstractInventoryTable<V> {
	
	private InventorySortMode lastSortMode = InventorySortMode.TYPE_ASC;

	public DashboardInventoryTable(String... columnLabels) {
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
			model.addRow((V)item.getType(),
					(V)String.format("%3d %s",  item.getQuantity(), item.getUnit().getAbbreviationByValue(item.getQuantity())),
					(V)item.getLocation().getFreezerLocation(),
					(V)item.getAddedFormatted(),
					(V)item.getExpiresFormatted());
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
