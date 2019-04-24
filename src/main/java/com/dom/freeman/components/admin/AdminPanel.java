package com.dom.freeman.components.admin;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.input.KeyType;

public class AdminPanel extends ViewPanel {

	private Interactable interactable;
	
	public AdminPanel() {
		super(KeyType.F7);
		this.configureContent();
	}
	
	public AdminPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F7);
		this.configureContent();
	}
	
	private void configureContent() {
		
		this.addComponent(new Label("Hello"));
	}
	
	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
