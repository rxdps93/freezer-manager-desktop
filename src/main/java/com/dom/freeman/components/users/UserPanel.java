package com.dom.freeman.components.users;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
import com.googlecode.lanterna.input.KeyType;

public class UserPanel extends ViewPanel {

	public UserPanel() {
		super(KeyType.F5);
		configureContent();
	}
	
	public UserPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F5);
		configureContent();
	}
	
	private void configureContent() {
		
		Label content = new Label("User Management Panel");
		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(content);
	}
}
