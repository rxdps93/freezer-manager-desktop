package com.dom.freeman.components.users.tables;

import java.util.Collections;

import com.dom.freeman.Global;
import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.users.UserSortMode;
import com.dom.freeman.obj.SortMode;
import com.dom.freeman.obj.User;
import com.googlecode.lanterna.gui2.table.TableModel;

public class UserViewTable<V> extends AbstractInventoryTable<V> {
	
	private UserSortMode lastSortMode = UserSortMode.LAST_ASC;
	
	public UserViewTable(String... columnLabels) {
		super(columnLabels);
	}
	
	@Override
	public void refresh() {
		this.sortTable(this.lastSortMode);
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
	public void sortTable(SortMode sortMode) {
		UserSortMode usrSort = (UserSortMode)sortMode;
		Collections.sort(Global.OBJECTS.getUsers(), usrSort.getSortMethod());
		this.setTableModel(this.configureTableModel(this.getColumnLabelArray()));
		this.lastSortMode = usrSort;
	}

}
