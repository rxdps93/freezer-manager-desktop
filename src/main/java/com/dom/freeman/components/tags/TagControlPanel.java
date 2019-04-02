package com.dom.freeman.components.tags;

import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;

public class TagControlPanel extends Panel {

	private final Window parent;
	
	public TagControlPanel(Window parent) {
		super();
		this.parent = parent;
		this.configureContent();
	}
	
	public TagControlPanel(LayoutManager layoutManager, Window parent) {
		super(layoutManager);
		this.parent = parent;
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel options = new Panel();
		options.addComponent(new Button("Create New Item Tag", new Runnable() {
			@Override
			public void run() {
				// TODO: Add Item Tag Dialog
				System.out.println("make thing");
			}
		}));
		options.addComponent(new Button("Create New Permanent Tag", new Runnable() {
			@Override
			public void run() {
				// TODO: Future task
			}
		}));
		this.addComponent(options.withBorder(Borders.singleLine("TAG MANAGEMENT CONTROLS")));
	}
	
}
