package com.dom.freeman.components.users.tables;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.googlecode.lanterna.gui2.table.TableModel;

public class UserViewTable<V> extends AbstractInventoryTable<V> {
	
	@Override
	public void refresh() {
		this.sortTable(null);
	}

	@Override
	public TableModel<V> configureTableModel(String... columnLabels) {
		TableModel<V> model = new TableModel<V>(columnLabels);
		
		
		
		return model;
	}

	@Override
	public void sortTable(InventorySortMode sortMode) {
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
	}

}
