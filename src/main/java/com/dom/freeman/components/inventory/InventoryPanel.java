package com.dom.freeman.components.inventory;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.input.KeyType;

public class InventoryPanel extends ViewPanel {
	
	private Interactable interactable;
	
	public InventoryPanel() {
		super(KeyType.F2);
		configureContent();
	}
	
	public InventoryPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F2);
		configureContent();
	}
	
	private void configureContent() {
		
		InventoryManagementPanel invManPanel = new InventoryManagementPanel();
		this.interactable = invManPanel.getInteractable();
		this.addComponent(new InventoryExpirationPanel());
		this.addComponent(invManPanel);
		this.addComponent(new InventoryManagementControlPanel(new LinearLayout(Direction.VERTICAL), invManPanel));
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
