package com.dom.freeman.components;

import org.apache.commons.lang3.StringUtils;

import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;

public class MainPanel extends Panel {
	
	private final String header;
	
	private Label headerLabel;
	private Label actionLabel;
	
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
		
		this.configureHeaderLabel();
		this.configureActionsLabel();
		
		this.setView(initialComponent);
	}
	
	private void configureHeaderLabel() {

		Label headerLabel = new Label(this.header);

		headerLabel.setLayoutData(GridLayout.createLayoutData(
				Alignment.CENTER,
				Alignment.CENTER,
				true,
				true,
				1,
				1));

		this.headerLabel = headerLabel;
	}

	private void configureActionsLabel() {

		StringBuilder actionMessage = new StringBuilder();

		for (int i = 0; i < this.actions.length; i++) {
			actionMessage.append(StringUtils.center(String.format("[%s=%s]", this.buttons[i], this.actions[i]), 25));
		}

		Label actionLabel = new Label(actionMessage.toString());
		actionLabel.setLayoutData(GridLayout.createLayoutData(
				GridLayout.Alignment.CENTER,
				GridLayout.Alignment.CENTER,
				true,
				true,
				1,
				1));

		this.actionLabel = actionLabel;
	}
	
	public void setView(ViewPanel component) {
		this.removeAllComponents();
		this.addComponent(this.headerLabel);
		
		component.setLayoutData(GridLayout.createLayoutData(
				GridLayout.Alignment.CENTER,
				GridLayout.Alignment.CENTER));
		this.addComponent(component);
		this.currentComponent = component;
		
		this.addComponent(this.actionLabel);
	}
	
	public ViewPanel getCurrentComponent() {
		return this.currentComponent;
	}
}
