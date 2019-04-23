package com.dom.freeman.components.users;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.input.KeyType;

public class UserPanel extends ViewPanel {

	private Interactable interactable;
	
	public UserPanel() {
		super(KeyType.F5);
		configureContent();
	}
	
	public UserPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F5);
		configureContent();
	}
	
	private void configureContent() {
		
		UserViewPanel viewPanel = new UserViewPanel();
		this.interactable = viewPanel.getInteractable();
		this.addComponent(viewPanel.setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.FILL)));
		this.addComponent(new UserControlPanel(new LinearLayout(Direction.VERTICAL), viewPanel));
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
