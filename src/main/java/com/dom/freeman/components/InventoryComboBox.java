package com.dom.freeman.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.InteractableRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

public class InventoryComboBox<V> extends ComboBox<V> {

	@SafeVarargs
	public InventoryComboBox(V... items) {
		this(Arrays.asList(items));
	}

	public InventoryComboBox(Collection<V> items) {
		this(items, items.isEmpty() ? -1 : 0);
	}

	public InventoryComboBox(Collection<V> items, int selectedIndex) {
		super(items, selectedIndex);
	}

	@Override
	protected InteractableRenderer<ComboBox<V>> createDefaultRenderer() {
		return new DefaultInventoryComboBoxRenderer<V>();
	}

	public static abstract class InventoryComboBoxRenderer<V> implements InteractableRenderer<ComboBox<V>> {
	}

	/**
	 * This class is the default renderer implementation which will be used unless overridden. The combo box is rendered
	 * like a text box with an arrow point down to the right of it, which can receive focus and triggers the popup.
	 * @param <V> Type of items in the combo box
	 */
	public static class DefaultInventoryComboBoxRenderer<V> extends InventoryComboBoxRenderer<V> {

		private int textVisibleLeftPosition;

		/**
		 * Default constructor
		 */
		public DefaultInventoryComboBoxRenderer() {
			this.textVisibleLeftPosition = 0;
		}

		@Override
		public TerminalPosition getCursorLocation(ComboBox<V> comboBox) {
			if(comboBox.isDropDownFocused()) {
				if(comboBox.getThemeDefinition().isCursorVisible()) {
					return new TerminalPosition(comboBox.getSize().getColumns() - 1, 0);
				}
				else {
					return null;
				}
			}
			else {
				int textInputPosition = comboBox.getTextInputPosition();
				int textInputColumn = TerminalTextUtils.getColumnWidth(comboBox.getText().substring(0, textInputPosition));
				return new TerminalPosition(textInputColumn - textVisibleLeftPosition, 0);
			}
		}

		@Override
		public TerminalSize getPreferredSize(final ComboBox<V> comboBox) {
			TerminalSize size = TerminalSize.ONE.withColumns(
					(comboBox.getItemCount() == 0 ? TerminalTextUtils.getColumnWidth(comboBox.getText()) : 0) + 2);
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized(comboBox) {
				for(int i = 0; i < comboBox.getItemCount(); i++) {
					V item = comboBox.getItem(i);
					size = size.max(new TerminalSize(TerminalTextUtils.getColumnWidth(item.toString()) + 2 + 1, 1));   // +1 to add a single column of space
				}
			}
			return size;
		}

		@Override
		public void drawComponent(TextGUIGraphics graphics, ComboBox<V> comboBox) {
			ThemeDefinition themeDefinition = comboBox.getThemeDefinition();
			if(comboBox.isReadOnly()) {
				graphics.applyThemeStyle(themeDefinition.getNormal());
			}
			else {
				if(comboBox.isFocused()) {
					graphics.applyThemeStyle(themeDefinition.getActive());
				}
				else {
					graphics.applyThemeStyle(themeDefinition.getPreLight());
				}
			}

			if (comboBox.isEnabled()) {
				graphics.setBackgroundColor(ANSI.BLUE);
				graphics.setForegroundColor(ANSI.WHITE);
				graphics.setModifiers(EnumSet.of(SGR.BOLD));
			} else {
				graphics.setBackgroundColor(ANSI.WHITE);
			}         	

			graphics.fill(' ');
			int editableArea = graphics.getSize().getColumns() - 2; //This is exclusing the 'drop-down arrow'
			int textInputPosition = comboBox.getTextInputPosition();
			int columnsToInputPosition = TerminalTextUtils.getColumnWidth(comboBox.getText().substring(0, textInputPosition));
			if(columnsToInputPosition < textVisibleLeftPosition) {
				textVisibleLeftPosition = columnsToInputPosition;
			}
			if(columnsToInputPosition - textVisibleLeftPosition >= editableArea) {
				textVisibleLeftPosition = columnsToInputPosition - editableArea + 1;
			}
			if(columnsToInputPosition - textVisibleLeftPosition + 1 == editableArea &&
					comboBox.getText().length() > textInputPosition &&
					TerminalTextUtils.isCharCJK(comboBox.getText().charAt(textInputPosition))) {
				textVisibleLeftPosition++;
			}

			String textToDraw = TerminalTextUtils.fitString(comboBox.getText(), textVisibleLeftPosition, editableArea);
			graphics.putString(0, 0, textToDraw);
			graphics.applyThemeStyle(themeDefinition.getInsensitive());
			graphics.setCharacter(editableArea, 0, themeDefinition.getCharacter("POPUP_SEPARATOR", Symbols.SINGLE_LINE_VERTICAL));
			if(comboBox.isFocused() && comboBox.isDropDownFocused()) {
				graphics.applyThemeStyle(themeDefinition.getSelected());
			}
			graphics.setCharacter(editableArea + 1, 0, themeDefinition.getCharacter("POPUP", Symbols.TRIANGLE_DOWN_POINTING_BLACK));
		}
	}
}
