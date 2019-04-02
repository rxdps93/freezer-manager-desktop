package com.dom.freeman.components.tags.tables;

import com.dom.freeman.Global;
import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.ItemTag;
import com.googlecode.lanterna.gui2.table.TableModel;

public class ItemTagViewTable<V> extends AbstractInventoryTable<V> {

	public ItemTagViewTable(String... columnLabels) {
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
		
		for (ItemTag tag : Global.OBJECTS.getItemTags()) {
			model.addRow((V)tag.getName(),
					(V)Integer.toString(tag.getAssociatedItemIds().size()));
		}
		
		return model;
	}

	@Override
	public void sortTable(InventorySortMode sortMode) {
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
	}

}
