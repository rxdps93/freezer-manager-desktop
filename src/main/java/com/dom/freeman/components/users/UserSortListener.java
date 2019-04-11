package com.dom.freeman.components.users;

import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.RadioBoxList.Listener;

public class UserSortListener implements Listener {

	private final RadioBoxList<String> otherSortOption;
	private final UserViewPanel userPanel;
	
	public UserSortListener(RadioBoxList<String> otherSortOption, UserViewPanel userPanel) {
		super();
		this.otherSortOption = otherSortOption;
		this.userPanel = userPanel;
	}
	
	@Override
	public void onSelectionChanged(int selectedIndex, int previousSelection) {
		
		UserSortMode sortMode;
		
		if (this.otherSortOption.getItemCount() == 2) {
			sortMode = selectSortMode(selectedIndex, this.otherSortOption.getCheckedItemIndex());
		} else {
			sortMode = selectSortMode(this.otherSortOption.getCheckedItemIndex(), selectedIndex);
		}
		
		this.userPanel.getTable().sortTable(sortMode);
	}
	
	private UserSortMode selectSortMode(int fieldIndex, int directionIndex) {
		
		UserSortMode sortMode = null;
		
		switch (fieldIndex) {
		case 0: // Last Name
			if (directionIndex == 0)
				sortMode = UserSortMode.LAST_ASC;
			else
				sortMode = UserSortMode.LAST_DESC;
			break;
		case 1: // First Name
			if (directionIndex == 0)
				sortMode = UserSortMode.FIRST_ASC;
			else
				sortMode = UserSortMode.FIRST_DESC;
			break;
		case 2: // Display Name
			if (directionIndex == 0)
				sortMode = UserSortMode.DISP_ASC;
			else
				sortMode = UserSortMode.DISP_DESC;
			break;
			default:
				break;
		}
		
		return sortMode;
	}

}
