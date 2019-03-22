package com.dom.freeman.components;

import org.apache.commons.lang3.StringUtils;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor.ANSI;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

public class MainPanel extends Panel {
	
	private final String header;
	
	private Panel headerPanel;
	private Panel componentPanel;
	private Panel actionPanel;
	
	private ViewPanel currentComponent;

	private final String[] actions = {
			"DASHBOARD",
			"MANAGE INVENTORY",
			"MANAGE TAGS",
			"MANAGE TYPES",
			"MANAGE USERS",
			"TRANSACTION LOGS"
	};

	private final String[] buttons = {
			"F1",
			"F2",
			"F3",
			"F4",
			"F5",
			"F6"
	};

	public MainPanel(ViewPanel initialComponent, String header) {
		super();
		this.header = header;
		this.initialConfiguration(initialComponent);
	}
	
	public MainPanel(LayoutManager layoutManager, ViewPanel initialComponent, String header) {
		super(layoutManager);
		this.header = header;
		this.initialConfiguration(initialComponent);
	}
	
	private void initialConfiguration(ViewPanel initialComponent) {
		
		this.configureHeaderPanel();
		this.configureComponentPanel(initialComponent);
		this.configureActionsPanel(0);
		
		this.addComponent(this.headerPanel);
		this.addComponent(this.componentPanel);
		this.addComponent(this.actionPanel.withBorder(Borders.singleLine()));
	}
	
	private void configureComponentPanel(ViewPanel initialComponent) {
		
		this.componentPanel = new Panel(new LinearLayout());
		this.componentPanel.setLayoutData(BorderLayout.Location.CENTER);
		initialComponent.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
		this.componentPanel.addComponent(initialComponent);
		
		this.currentComponent = initialComponent;
	}
	
	private void configureHeaderPanel() {

		Label headerLabel = new Label(this.header);

		this.headerPanel = new Panel(new LinearLayout());
		this.headerPanel.setLayoutData(BorderLayout.Location.TOP);
		headerLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
		this.headerPanel.addComponent(headerLabel);
		this.headerPanel.addComponent(new EmptySpace(new TerminalSize(1, 2)));
	}

	private void configureActionsPanel(int activeView) {

		this.actionPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
		this.actionPanel.setLayoutData(BorderLayout.Location.BOTTOM);
		
//		StringBuilder actionMessage = new StringBuilder();

		Label actionLabel;
		for (int i = 0; i < this.actions.length; i++) {
			actionLabel = new Label(StringUtils.center(String.format("[%s=%s]", this.buttons[i], this.actions[i]), 25));
			actionLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
			
			if (i == activeView) {
				actionLabel.setBackgroundColor(ANSI.YELLOW);
			}
			
			actionPanel.addComponent(actionLabel);
		}

//		Label actionLabel = new Label(actionMessage.toString());

//		this.actionPanel.setLayoutData(BorderLayout.Location.BOTTOM);
//		actionLabel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
//		this.actionPanel.addComponent(actionLabel);
	}
	
	public void setView(ViewPanel component, int activeView) {
		
		this.removeAllComponents();
		
		configureActionsPanel(activeView);
		this.addComponent(this.headerPanel);
		
		component.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));
		this.componentPanel.removeAllComponents();
		this.componentPanel.addComponent(component);
		this.addComponent(this.componentPanel);
		
		this.currentComponent = component;
		
		this.addComponent(this.actionPanel.withBorder(Borders.singleLine()));
	}
	
	public ViewPanel getCurrentComponent() {
		return this.currentComponent;
	}
}
