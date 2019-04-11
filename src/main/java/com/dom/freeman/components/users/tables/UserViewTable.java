package com.dom.freeman.components.users.tables;

import com.dom.freeman.Global;
import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.inventory.InventorySortMode;
import com.dom.freeman.obj.User;
import com.googlecode.lanterna.gui2.table.TableModel;

public class UserViewTable<V> extends AbstractInventoryTable<V> {
	
	public UserViewTable(String... columnLabels) {
		super(columnLabels);
	}
	
	@Override
	public void refresh() {
		this.sortTable(null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TableModel<V> configureTableModel(String... columnLabels) {
		TableModel<V> model = new TableModel<V>(columnLabels);
		
		for (User user : Global.OBJECTS.getUsers()) {
			model.addRow((V)user.getLastName(),
					(V)user.getFirstName(),
					(V)user.getDisplayName(),
					(V)Integer.valueOf(user.getUserPermissions().size()),
					(V)user.getId());
		}
		
		return model;
	}

	@Override
	public void sortTable(InventorySortMode sortMode) {
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
	}

}
