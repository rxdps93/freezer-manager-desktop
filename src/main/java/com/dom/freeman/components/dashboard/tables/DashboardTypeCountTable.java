package com.dom.freeman.components.dashboard.tables;

import java.time.LocalDate;
import java.util.Map.Entry;

import com.dom.freeman.Global;
import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.table.TableModel;

public class DashboardTypeCountTable<V> extends AbstractInventoryTable<V> {
	
	public DashboardTypeCountTable(String... columnLabels) {
		super(columnLabels);
	}
	
	@Override
	public void refresh() {
		this.sortTable(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TableModel<V> configureTableModel(String... columnLabels) {
		TableModel<V> model = new TableModel<>(columnLabels);
		
		LocalDate earliestExpiration;
		for (Entry<String, Integer> entry : Global.OBJECTS.getTypes().entrySet()) {

			earliestExpiration = null;
			for (Item item : Global.OBJECTS.getInventory()) {

				if (item.getType().equals(entry.getKey())) {
					if (earliestExpiration == null || earliestExpiration.isAfter(item.getExpires()))
						earliestExpiration = item.getExpires();
				}
			}

			model.addRow(
					(V)entry.getKey(),
					(V)Integer.toString(entry.getValue()),
					(V)earliestExpiration.toString());
		}
		
		return model;
	}

	@Override
	public void sortTable(InventorySortMode sortMode) {
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
	}

}
