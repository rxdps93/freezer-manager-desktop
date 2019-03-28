package com.dom.freeman.components.inventory;

import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.RadioBoxList.Listener;

public class InventorySortListener implements Listener {
	
	private final RadioBoxList<String> otherSortOption;
	private final InventoryManagementPanel invPanel;
	
	public InventorySortListener(RadioBoxList<String> otherSortOption, InventoryManagementPanel invPanel) {
		super();
		this.otherSortOption = otherSortOption;
		this.invPanel = invPanel;
	}

	@Override
	public void onSelectionChanged(int selectedIndex, int previousSelection) {
		
		InventorySortMode sortMode;
		
		// If the other list is the direction one, this must be the field one
		if (this.otherSortOption.getItemCount() == 2) {
			sortMode = selectSortMode(selectedIndex, this.otherSortOption.getCheckedItemIndex());
		} else { // Else this must be the direction one and the other is the field one
			sortMode = selectSortMode(this.otherSortOption.getCheckedItemIndex(), selectedIndex);
		}
		
		this.invPanel.getTable().sortTable(sortMode);
	}
	
	private InventorySortMode selectSortMode(int fieldIndex, int directionIndex) {
		
		InventorySortMode sortMode = null;
		switch(fieldIndex) {
		case 0: // Item Type
			if (directionIndex == 0)
				sortMode = InventorySortMode.TYPE_ASC;
			else
				sortMode = InventorySortMode.TYPE_DESC;
			break;
		case 1: // Quantity
			if (directionIndex == 0)
				sortMode = InventorySortMode.QUANT_ASC;
			else
				sortMode = InventorySortMode.QUANT_DESC;
			break;
		case 2: // Add Date
			if (directionIndex == 0)
				sortMode = InventorySortMode.ADD_NEWER;
			else
				sortMode = InventorySortMode.ADD_OLDER;
			break;
		case 3: // Exp Date
			if (directionIndex == 0)
				sortMode = InventorySortMode.EXP_NEWER;
			else
				sortMode = InventorySortMode.EXP_OLDER;
			break;
			default:
				break;
		}
		return sortMode;
	}

}
