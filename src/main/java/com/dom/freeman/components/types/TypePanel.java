package com.dom.freeman.components.types;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
import com.googlecode.lanterna.input.KeyType;

public class TypePanel extends ViewPanel {

	public TypePanel() {
		super(KeyType.F4);
		configureContent();
	}
	
	public TypePanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F4);
		configureContent();
	}
	
	private void configureContent() {
		
		Label content = new Label("Type Management Panel");
		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(content);
	}
}
