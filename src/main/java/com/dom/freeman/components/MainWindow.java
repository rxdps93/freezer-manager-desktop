package com.dom.freeman.components;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dom.freeman.components.dashboard.DashboardPanel;
import com.dom.freeman.components.inventory.InventoryPanel;
import com.dom.freeman.components.tags.TagPanel;
import com.dom.freeman.components.transactions.TransactionPanel;
import com.dom.freeman.components.types.TypePanel;
import com.dom.freeman.components.users.UserPanel;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

public class MainWindow extends BasicWindow {

	private final String header;
	
	private ViewPanel dashboardPanel;
	private ViewPanel inventoryPanel;
	private ViewPanel tagPanel;
	private ViewPanel typePanel;
	private ViewPanel userPanel;
	private ViewPanel transactionPanel;

	public MainWindow(String header) {
		super();
		this.header = header;
		this.configureContent();
	}

	public MainWindow(String title, String header) { 
		super(title);
		this.header = header;
		this.configureContent();
	}

	public MainPanel getMainComponent() {
		return (MainPanel)this.getComponent();
	}
	
	private void configureContent() {

		this.dashboardPanel = new DashboardPanel(new GridLayout(3), this);
		this.inventoryPanel = new InventoryPanel(new GridLayout(3), this);
		this.tagPanel = new TagPanel(new GridLayout(2), this);
		this.typePanel = new TypePanel(new LinearLayout());
		this.userPanel = new UserPanel(new GridLayout(3), this);
		this.transactionPanel = new TransactionPanel(new LinearLayout(), this);
		
		this.setComponent(new MainPanel(new BorderLayout(), this.dashboardPanel, this.header));
		this.setHints(Arrays.asList(Hint.EXPANDED, Hint.FIXED_POSITION));
		this.setPosition(new TerminalPosition(1, 2));

		this.addWindowListener(new WindowListener() {

			@Override
			public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {

				switch (keyStroke.getKeyType()) {
				case F1:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F1)) {
						getMainComponent().setView(dashboardPanel, 0);
						setFocusedInteractable(dashboardPanel.getPrimaryInteractable());
					}
					break;
				case F2:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F2)) {
						getMainComponent().setView(inventoryPanel, 1);
						setFocusedInteractable(inventoryPanel.getPrimaryInteractable());
					}
					break;
				case F3:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F3)) {
						getMainComponent().setView(tagPanel, 2);
						setFocusedInteractable(tagPanel.getPrimaryInteractable());
					}
					break;
				case F4:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F4)) {
						getMainComponent().setView(typePanel, 3);
						setFocusedInteractable(typePanel.getPrimaryInteractable());
					}
					break;
				case F5:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F5)) {
						getMainComponent().setView(userPanel, 4);
						setFocusedInteractable(userPanel.getPrimaryInteractable());
					}
					break;
				case F6:
					if (!getMainComponent().getCurrentComponent().getTrigger().equals(KeyType.F6)) {
						getMainComponent().setView(transactionPanel, 5);
						setFocusedInteractable(transactionPanel.getPrimaryInteractable());
					}
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
