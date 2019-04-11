package com.dom.freeman.components.users;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
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
		
		UserViewPanel viewPanel = new UserViewPanel(this.parent);
		this.interactable = viewPanel.getInteractable();
		this.addComponent(viewPanel.setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.FILL)));
		this.addComponent(new UserControlPanel(this.parent));
	}
	
	public Window getParentWindow() {
		return this.parent;
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
