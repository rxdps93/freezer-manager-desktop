package com.dom.freeman.components.tags;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyType;

public class TagPanel extends ViewPanel {

	private final Window parent;
	private Interactable interactable;
	
	public TagPanel(Window parent) {
		super(KeyType.F3);
		this.parent = parent;
		configureContent();
	}
	
	public TagPanel(LayoutManager layoutManager, Window parent) {
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
	
	public Window getParentWindow() {
		return this.parent;
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}

}
