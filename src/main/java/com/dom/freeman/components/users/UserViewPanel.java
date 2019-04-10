package com.dom.freeman.components.users;

import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

public class UserViewPanel extends Panel {

	private Window parent;
	
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
		
	}
}
