package com.dom.freeman.components.dashboard.tables;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableCellRenderer;

public class ExpirationTableCellRenderer<V> implements TableCellRenderer<V> {
	
	private Map<Integer, TextColor> styleMap;
	
	public ExpirationTableCellRenderer() {
		super();
		this.styleMap = new HashMap<Integer, TextColor>();
	}

	@Override
	public TerminalSize getPreferredSize(Table<V> table, V cell, int columnIndex, int rowIndex) {
        String[] lines = getContent(cell);
        int maxWidth = 0;
        for(String line: lines) {
            int length = TerminalTextUtils.getColumnWidth(line);
            if(maxWidth < length) {
                maxWidth = length;
            }
        }
        return new TerminalSize(maxWidth, lines.length);
	}

	@Override
	public void drawCell(Table<V> table, V cell, int columnIndex, int rowIndex, TextGUIGraphics textGUIGraphics) {
        ThemeDefinition themeDefinition = table.getThemeDefinition();
        if((table.getSelectedColumn() == columnIndex && table.getSelectedRow() == rowIndex) ||
                (table.getSelectedRow() == rowIndex && !table.isCellSelection())) {
            if(table.isFocused()) {
                textGUIGraphics.applyThemeStyle(themeDefinition.getActive());
            }
            else {
                textGUIGraphics.applyThemeStyle(themeDefinition.getSelected());
            }
            textGUIGraphics.fill(' ');  //Make sure to fill the whole cell first
        }
        else {
            textGUIGraphics.applyThemeStyle(themeDefinition.getNormal());
        }
        String[] lines = getContent(cell);
        int rowCount = 0;
        for(String line: lines) {
        	if (columnIndex == 0) {
        		colorRow(Integer.parseInt(table.getTableModel().getCell(table.getTableModel().getColumnCount() - 1, rowIndex).toString()), rowIndex);
        	}
        	
        	if (styleMap.containsKey(rowIndex)) {
        		textGUIGraphics.setForegroundColor(styleMap.get(rowIndex));
        		textGUIGraphics.enableModifiers(SGR.BOLD);
        	}
        	
            textGUIGraphics.putString(0, rowCount++, line);
        }
	}
	
	private void colorRow(int daysRemaining, int rowIndex) {
		
		TextColor color = null;
		if (daysRemaining <= 14)
			color = ANSI.RED;
		else if (daysRemaining <= 30)
			color = ANSI.YELLOW;
		else
			color = ANSI.BLUE;
		
		if (color != null)
			styleMap.put(rowIndex, color);
	}

    private String[] getContent(V cell) {
        String[] lines;
        if(cell == null) {
            lines = new String[] { "" };
        }
        else {
            lines = cell.toString().split("\r?\n");
        }
        return lines;
    }
}
