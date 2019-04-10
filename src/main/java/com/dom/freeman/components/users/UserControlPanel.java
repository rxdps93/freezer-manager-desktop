package com.dom.freeman.components.users;

import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

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
		
	}
}
