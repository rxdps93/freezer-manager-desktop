package com.dom.freeman.components.users;

import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

public class PermissionsViewPanel extends Panel {
	
	private Window parent;
	
	public PermissionsViewPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}
	
	public PermissionsViewPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}
	
	private void configureContent() {
		
	}
}
