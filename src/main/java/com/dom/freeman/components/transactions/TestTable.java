package com.dom.freeman.components.transactions;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.SortMode;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.table.TableModel;

public class TestTable<V> extends AbstractInventoryTable<V> {

	private InventorySortMode lastSortMode = InventorySortMode.TYPE_ASC;
	
	public TestTable(String... columnLabels) {
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
					(V)item.getExpiresFormatted(),
					(V)item.getId());
		}
		
		return model;
	}

	@Override
	public void sortTable(SortMode sortMode) {
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
	}

}
