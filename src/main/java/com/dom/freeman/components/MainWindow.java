package com.dom.freeman.components;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dom.freeman.components.dashboard.DashboardPanel;
import com.dom.freeman.components.inventory.InventoryPanel;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class MainWindow extends BasicWindow {

	private final List<Item> items;
	private final Map<String, Integer> types;
	private final String header;
	
	private ViewPanel dashboardPanel;
	private ViewPanel inventoryPanel;

	public MainWindow(String header, List<Item> items, Map<String, Integer> types) {
		super();
		this.header = header;
		this.items = items;
		this.types = types;
		this.configureContent();
	}

	public MainWindow(String title, String header, List<Item> items, Map<String, Integer> types) { 
		super(title);
		this.header = header;
		this.items = items;
		this.types = types;
		this.configureContent();
	}

	private MainPanel getMainComponent() {
		return (MainPanel)this.getComponent();
	}
	
	private void configureContent() {

		this.dashboardPanel = new DashboardPanel(new GridLayout(3), this.items, this.types, this);
		this.inventoryPanel = new InventoryPanel();
		
		this.setComponent(new MainPanel(new GridLayout(1), this.dashboardPanel, this.header));
		this.setHints(Arrays.asList(Hint.EXPANDED, Hint.FIXED_POSITION));
		this.setPosition(new TerminalPosition(1, 2));

		this.addWindowListener(new WindowListener() {

			@Override
			public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {

				switch (keyStroke.getKeyType()) {
				case F1:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F1))
						getMainComponent().setView(dashboardPanel);
					break;
				case F2:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F2))
						getMainComponent().setView(inventoryPanel);
					break;
				case F3:
					System.out.println("Manage Tags");
					break;
				case F4:
					System.out.println("Manage Types");
					break;
				case F5:
					System.out.println("Manage Users");
					break;
				case F6:
					System.out.println("Transaction Logs");
					break;
				default:
					break;
				}
			}

			@Override
			public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
				// TODO Auto-generated method stub

			}

		});
	}
}
