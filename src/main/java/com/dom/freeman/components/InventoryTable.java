package com.dom.freeman.components;

import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.input.KeyStroke;

public class InventoryTable<V> extends Table<V> {

	private boolean resetSelectOnTab = false;
	
	public InventoryTable(String... columnLabels) {
		super(columnLabels);
	}
	
	public boolean getResetSelectOnTab() {
		return this.resetSelectOnTab;
	}
	
	public void setResetSelectOnTab(boolean reset) {
		this.resetSelectOnTab = reset;
	}

	@Override
	public Result handleKeyStroke(KeyStroke keyStroke) {

		switch(keyStroke.getKeyType()) {
		case PageUp:
			this.setSelectedRow(Math.max(this.getSelectedRow() - 10, 0));
			return Result.HANDLED;
		case PageDown:
			this.setSelectedRow(Math.min(this.getSelectedRow() + 10, this.getTableModel().getRowCount() - 1));
			return Result.HANDLED;
		case Tab:
		case ArrowRight:
			if (this.getResetSelectOnTab())
				this.setSelectedRow(0);
			return Result.MOVE_FOCUS_NEXT;
		case ReverseTab:
		case ArrowLeft:
			if (this.getResetSelectOnTab())
				this.setSelectedRow(0);
			return Result.MOVE_FOCUS_PREVIOUS;
		default:
			return super.handleKeyStroke(keyStroke);
		}
	}
}
