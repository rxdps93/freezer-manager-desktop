package com.dom.freeman.components.tags;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
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
		
		Label content = new Label("Tag Management Panel");
		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(content);
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}

}
