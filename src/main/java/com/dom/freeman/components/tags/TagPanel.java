package com.dom.freeman.components.tags;

import com.dom.freeman.components.MainWindow;
import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.input.KeyType;

public class TagPanel extends ViewPanel {

	private final MainWindow parent;
	private Interactable interactable;
	
	public TagPanel(MainWindow parent) {
		super(KeyType.F3);
		this.parent = parent;
		configureContent();
	}
	
	public TagPanel(LayoutManager layoutManager, MainWindow parent) {
		super(layoutManager, KeyType.F3);
		this.parent = parent;
		configureContent();
	}
	
	private void configureContent() {
		
		TagManagementPanel tagPanel = new TagManagementPanel(this.parent);
		this.interactable = tagPanel.getInteractable();
		this.addComponent(tagPanel);
		this.addComponent(new TagControlPanel(this.parent));
	}
	
	public MainWindow getParentWindow() {
		return this.parent;
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}

}
