package com.dom.freeman.components.inventory.tables;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.SortMode;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.table.TableModel;

public class InventoryExpirationTable<V> extends AbstractInventoryTable<V> {

	private final InventorySortMode lastSortMode = InventorySortMode.EXP_NEWER;
	
	public InventoryExpirationTable(String... columnLabels) {
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
			if (item.getExpires().compareTo(LocalDate.now()) <= 0) {
				model.addRow((V)item.getType(),
						(V)String.format("%3d %s",  item.getQuantity(), item.getUnit().getAbbreviationByValue(item.getQuantity())),
						(V)item.getLocation().getFreezerLocation(),
						(V)item.getExpiresFormatted(),
						(V)Long.toString(ChronoUnit.DAYS.between(item.getExpires(), LocalDate.now())),
						(V)item.getId());
			}
		}
		
		return model;
	}

	@Override
	public void sortTable(SortMode sortMode) {
		Collections.sort(Global.OBJECTS.getInventory(), this.lastSortMode.getSortMethod());
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
	}

}
