package com.dom.freeman.components.inventory;

import com.dom.freeman.components.MainWindow;
import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.input.KeyType;

public class InventoryPanel extends ViewPanel {
	
	private final MainWindow parent;
	private Interactable interactable;
	
	public InventoryPanel(MainWindow parent) {
		super(KeyType.F2);
		this.parent = parent;
		configureContent();
	}
	
	public InventoryPanel(LayoutManager layoutManager, MainWindow parent) {
		super(layoutManager, KeyType.F2);
		this.parent = parent;
		configureContent();
	}
	
	private void configureContent() {
		
		InventoryManagementPanel invManPanel = new InventoryManagementPanel(this.parent);
		this.interactable = invManPanel.getInteractable();
		this.addComponent(new InventoryExpirationPanel(this.parent));
		this.addComponent(invManPanel);
		this.addComponent(new InventoryManagementControlPanel(new LinearLayout(Direction.VERTICAL), invManPanel, this.parent));
	}
	
	public MainWindow getParentWindow() {
		return this.parent;
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
