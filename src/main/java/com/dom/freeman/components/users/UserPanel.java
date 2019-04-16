package com.dom.freeman.components.users;

import com.dom.freeman.components.MainWindow;
import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.input.KeyType;

public class UserPanel extends ViewPanel {

	private final MainWindow parent;
	private Interactable interactable;
	
	public UserPanel(MainWindow parent) {
		super(KeyType.F5);
		this.parent = parent;
		configureContent();
	}
	
	public UserPanel(LayoutManager layoutManager, MainWindow parent) {
		super(layoutManager, KeyType.F5);
		this.parent = parent;
		configureContent();
	}
	
	private void configureContent() {
		
		UserViewPanel viewPanel = new UserViewPanel(this.parent);
		this.interactable = viewPanel.getInteractable();
		this.addComponent(viewPanel.setLayoutData(GridLayout.createLayoutData(Alignment.CENTER, Alignment.FILL)));
		this.addComponent(new UserControlPanel(new LinearLayout(Direction.VERTICAL), this.parent, viewPanel));
	}
	
	public MainWindow getParentWindow() {
		return this.parent;
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
