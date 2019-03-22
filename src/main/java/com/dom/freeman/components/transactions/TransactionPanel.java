package com.dom.freeman.components.transactions;

import com.dom.freeman.components.ViewPanel;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.LinearLayout.Alignment;
import com.googlecode.lanterna.input.KeyType;

public class TransactionPanel extends ViewPanel {
	
	private Interactable interactable;
	
	public TransactionPanel() {
		super(KeyType.F6);
		configureContent();
	}
	
	public TransactionPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F6);
		configureContent();
	}
	
	private void configureContent() {
		
		Label content = new Label("Transaction Management Panel");
		content.setLayoutData(LinearLayout.createLayoutData(Alignment.Center));
		this.addComponent(content);
	}

	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
