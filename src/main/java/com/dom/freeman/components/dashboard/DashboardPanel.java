package com.dom.freeman.components.dashboard;

import java.util.List;
import java.util.Map;

import com.dom.freeman.components.ViewPanel;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyType;

public class DashboardPanel extends ViewPanel {

	private final Window parent;
	private List<Item> items;
	private Interactable interactable;

	public DashboardPanel(List<Item> items, Map<String, Integer> types, Window parent) {
		super(KeyType.F1);
		this.parent = parent;
		this.items = items;
		this.configureContent(types);
	}

	public DashboardPanel(LayoutManager layoutManager, List<Item> items, Map<String, Integer> types, Window parent) {
		super(layoutManager, KeyType.F1);
		this.parent = parent;
		this.items = items;
		this.configureContent(types);
	}

	private void configureContent(Map<String, Integer> types) {

		InventoryViewPanel invViewPanel = new InventoryViewPanel(this.items, this.parent);
		this.addComponent(invViewPanel);
		this.addComponent(new ExpirationViewPanel(this.items, this.parent));
		this.addComponent(new TypeCountViewPanel(this.items, types, this.parent));
		this.interactable = invViewPanel.getInteractable();
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
