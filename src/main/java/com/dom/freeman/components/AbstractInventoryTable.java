package com.dom.freeman.components;

import com.dom.freeman.obj.SortMode;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.gui2.table.TableRenderer;
import com.googlecode.lanterna.input.KeyStroke;

public abstract class AbstractInventoryTable<V> extends Table<V> {

	private boolean resetSelectOnTab = false;
	
	public AbstractInventoryTable(String... columnLabels) {
		super(columnLabels);
	}
	
	public boolean getResetSelectOnTab() {
		return this.resetSelectOnTab;
	}
	
	public void setResetSelectOnTab(boolean reset) {
		this.resetSelectOnTab = reset;
	}
	
	public void hideLastColumn(boolean hide) {
			this.getRenderer().hideLastColumn(hide);
	}
	
    @Override
    public InventoryTableRenderer<V> getRenderer() {
        return (InventoryTableRenderer<V>)super.getRenderer();
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
	
	public String[] getColumnLabelArray() {
		String [] columnLabels = new String[this.getTableModel().getColumnCount()];
		return this.getTableModel().getColumnLabels().toArray(columnLabels);
	}
	
	public abstract void refresh();
	
	public abstract TableModel<V> configureTableModel(String... columnLabels);
	
	public abstract void sortTable(SortMode sortMode);
	
    @Override
    protected TableRenderer<V> createDefaultRenderer() {
        return new InventoryTableRenderer<V>();
    }
}
