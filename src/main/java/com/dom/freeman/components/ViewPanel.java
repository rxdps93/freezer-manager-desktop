package com.dom.freeman.components;

import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyType;

public abstract class ViewPanel extends Panel {

	private final KeyType trigger;
	
	public ViewPanel(KeyType trigger) {
		super();
		this.trigger = trigger;
	}
	
	public ViewPanel(LayoutManager layoutManager, KeyType trigger) {
		super(layoutManager);
		this.trigger = trigger;
	}
	
	public KeyType getTrigger() {
		return this.trigger;
	}
	
	public abstract Interactable getPrimaryInteractable();
}
