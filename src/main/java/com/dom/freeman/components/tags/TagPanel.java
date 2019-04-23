package com.dom.freeman.components.tags;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.input.KeyType;

public class TagPanel extends ViewPanel {

	private Interactable interactable;
	
	public TagPanel() {
		super(KeyType.F3);
		configureContent();
	}
	
	public TagPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F3);
		configureContent();
	}
	
	private void configureContent() {
		
		TagManagementPanel tagPanel = new TagManagementPanel();
		this.interactable = tagPanel.getInteractable();
		this.addComponent(tagPanel);
		this.addComponent(new TagControlPanel());
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}

}
