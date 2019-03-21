package com.dom.freeman.components.dashboard;

import java.util.List;
import java.util.Map;

import com.dom.freeman.components.ViewPanel;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyType;

public class DashboardPanel extends ViewPanel {

	private final Window parent;

	public DashboardPanel(List<Item> items, Map<String, Integer> types, Window parent) {
		super(KeyType.F1);
		this.parent = parent;
		this.configureContent(items, types);
	}

	public DashboardPanel(LayoutManager layoutManager, List<Item> items, Map<String, Integer> types, Window parent) {
		super(layoutManager, KeyType.F1);
		this.parent = parent;
		this.configureContent(items, types);
	}

	private void configureContent(List<Item> items, Map<String, Integer> types) {

		this.addComponent(new InventoryViewPanel(items, this.parent));
		this.addComponent(new ExpirationViewPanel(items, this.parent));
		this.addComponent(new TypeCountViewPanel(items, types, this.parent));
	}
}
