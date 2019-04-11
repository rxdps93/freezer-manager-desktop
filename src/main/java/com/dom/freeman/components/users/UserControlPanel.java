package com.dom.freeman.components.users;

import java.util.Arrays;

import com.dom.freeman.components.users.dialog.AddUserDialog;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;

public class UserControlPanel extends Panel {

	private final UserViewPanel userPanel;
	private Window parent;
	
	public UserControlPanel(Window parent, UserViewPanel userPanel) {
		super();
		this.parent = parent;
		this.userPanel = userPanel;
		this.configureContent();
	}
	
	public UserControlPanel(LayoutManager layoutManager, Window parent, UserViewPanel userPanel) {
		super(layoutManager);
		this.parent = parent;
		this.userPanel = userPanel;
		this.configureContent();
	}
	
	private Panel initializeSortPanel() {
		
		Panel controls = new Panel(new GridLayout(2));
		
		RadioBoxList<String> sortField = new RadioBoxList<String>();
		RadioBoxList<String> sortDirection = new RadioBoxList<String>();
		
		sortField.addItem("Last Name");
		sortField.addItem("First Name");
		sortField.addItem("Display Name");
		
		sortDirection.addItem("Ascending");
		sortDirection.addItem("Descending");
		
		sortField.setCheckedItemIndex(0);
		sortDirection.setCheckedItemIndex(0);
		
		sortField.addListener(new UserSortListener(sortDirection, this.userPanel));
		sortDirection.addListener(new UserSortListener(sortField, this.userPanel));
		
		controls.addComponent(sortField.withBorder(Borders.singleLine("SORT BY...")));
		controls.addComponent(sortDirection.withBorder(Borders.singleLine("SORT DIRECTION")));
		
		return controls;
	}
	
	private Panel initializeOptionsPanel() {
		Panel options = new Panel();
		options.addComponent(new Button("Create New User", new Runnable() {
			@Override
			public void run() {
				AddUserDialog addUser = new AddUserDialog("CREATE NEW USER");
				addUser.setHints(Arrays.asList(Hint.CENTERED));
				addUser.showDialog(parent.getTextGUI());
			}
		}));
		
		return options;
	}
	
	private void configureContent() {
		
		this.addComponent(this.initializeSortPanel().withBorder(Borders.singleLine("USER VIEW SORT CONTROLS")));
		this.addComponent(this.initializeOptionsPanel().withBorder(Borders.singleLine("USER MANAGEMENT CONTROLS")));
	}
}
