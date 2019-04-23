package com.dom.freeman.components.dashboard;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.input.KeyType;

public class DashboardPanel extends ViewPanel {

	private Interactable interactable;

	public DashboardPanel() {
		super(KeyType.F1);
		this.configureContent();
	}

	public DashboardPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F1);
		this.configureContent();
	}

	private void configureContent() {

		InventoryViewPanel invViewPanel = new InventoryViewPanel();
		this.addComponent(invViewPanel);
		this.addComponent(new ExpirationViewPanel());
		this.addComponent(new TypeCountViewPanel());
		this.interactable = invViewPanel.getInteractable();
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
