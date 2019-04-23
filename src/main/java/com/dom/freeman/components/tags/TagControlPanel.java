package com.dom.freeman.components.tags;

import java.util.Arrays;

import com.dom.freeman.components.tags.dialog.AddItemTagDialog;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;

public class TagControlPanel extends Panel {
	
	public TagControlPanel() {
		super();
		this.configureContent();
	}
	
	public TagControlPanel(LayoutManager layoutManager) {
		super(layoutManager);
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel options = new Panel();
		options.addComponent(new Button("Create New Item Tag", new Runnable() {
			@Override
			public void run() {
				AddItemTagDialog addItemTag = new AddItemTagDialog("CREATE NEW ITEM TAG");
				addItemTag.setHints(Arrays.asList(Hint.CENTERED));
				addItemTag.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
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
