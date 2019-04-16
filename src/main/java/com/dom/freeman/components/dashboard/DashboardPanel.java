package com.dom.freeman.components.dashboard;

import com.dom.freeman.components.MainWindow;
import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.input.KeyType;

public class DashboardPanel extends ViewPanel {

	private final MainWindow parent;
	private Interactable interactable;

	public DashboardPanel(MainWindow parent) {
		super(KeyType.F1);
		this.parent = parent;
		this.configureContent();
	}

	public DashboardPanel(LayoutManager layoutManager, MainWindow parent) {
		super(layoutManager, KeyType.F1);
		this.parent = parent;
		this.configureContent();
	}

	private void configureContent() {

		InventoryViewPanel invViewPanel = new InventoryViewPanel(this.parent);
		this.addComponent(invViewPanel);
		this.addComponent(new ExpirationViewPanel(this.parent));
		this.addComponent(new TypeCountViewPanel(this.parent));
		this.interactable = invViewPanel.getInteractable();
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
