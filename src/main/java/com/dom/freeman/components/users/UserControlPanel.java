package com.dom.freeman.components.users;

import java.util.Arrays;

import com.dom.freeman.components.users.dialog.AddUserDialog;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.Window.Hint;

public class UserControlPanel extends Panel {

	private Window parent;
	
	public UserControlPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}
	
	public UserControlPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel options = new Panel();
		options.addComponent(new Button("Create New User", new Runnable() {
			@Override
			public void run() {
				AddUserDialog addUser = new AddUserDialog("CREATE NEW USER");
				addUser.setHints(Arrays.asList(Hint.CENTERED));
				addUser.showDialog(parent.getTextGUI());
			}
		}));
		this.addComponent(options.withBorder(Borders.singleLine("USER MANAGEMENT CONTROLS")));
	}
}
