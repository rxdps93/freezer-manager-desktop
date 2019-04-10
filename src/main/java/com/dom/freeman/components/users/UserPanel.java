package com.dom.freeman.components.users;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyType;

public class UserPanel extends ViewPanel {

	private final Window parent;
	private Interactable interactable;
	
	public UserPanel(Window parent) {
		super(KeyType.F5);
		this.parent = parent;
		configureContent();
	}
	
	public UserPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager, KeyType.F5);
		this.parent = parent;
		configureContent();
	}
	
	private void configureContent() {
		
		Label content = new Label("User Management Panel");
		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(content);
	}
	
	public Window getParentWindow() {
		return this.parent;
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
