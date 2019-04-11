package com.dom.freeman.components.users;

import com.dom.freeman.Global;
import com.dom.freeman.components.users.tables.UserViewTable;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;

public class UserViewPanel extends Panel {

	private Window parent;
	private UserViewTable<String> userTable;
	
	public UserViewPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}
	
	public UserViewPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel panel = new Panel();
		UserViewTable<String> userTable = new UserViewTable<>("LAST NAME",
				"FIRST NAME", "DISPLAY NAME", "PERMISSION COUNT", "UNIQUE ID");
		userTable.setResetSelectOnTab(true);
		userTable.hideLastColumn(true);
		
		userTable.refresh();
		
		userTable.setSelectAction(new Runnable() {
			@Override
			public void run() {
				new ActionListDialogBuilder().setTitle("SELECT AN ACTION")
				.addAction("View User Summary", new Runnable() {
					@Override
					public void run() {
						System.out.println("user summary");
					}
				})
				.addAction("Edit User Details", new Runnable() {
					@Override
					public void run() {
						System.out.println("edit user");
					}
				})
				.addAction("Edit User Permissions", new Runnable() {
					@Override
					public void run() {
						System.out.println("edit permissions");
					}
				})
				.addAction("Remove User", new Runnable() {
					@Override
					public void run() {
						System.out.println("remove user");
					}
				}).build().showDialog(parent.getTextGUI());
			}
		});
		
		panel.addComponent(userTable.setEscapeByArrowKey(false));
		this.userTable = userTable;
		Global.OBJECTS.registerTable(this.userTable);
		this.addComponent(panel.withBorder(Borders.singleLine("USERS")));
	}
	
	public UserViewTable<String> getTable() {
		return this.userTable;
	}
	
	public Interactable getInteractable() {
		return this.userTable;
	}
}
