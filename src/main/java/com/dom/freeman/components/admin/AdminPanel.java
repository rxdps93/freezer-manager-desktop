package com.dom.freeman.components.admin;

import java.util.Arrays;

import com.dom.freeman.components.ViewPanel;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Interactable;
import com.googlecode.lanterna.gui2.LayoutManager;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyType;

public class AdminPanel extends ViewPanel {

	private Interactable interactable;
	
	public AdminPanel() {
		super(KeyType.F7);
		this.configureContent();
	}
	
	public AdminPanel(LayoutManager layoutManager) {
		super(layoutManager, KeyType.F7);
		this.configureContent();
	}
	
	private void configureContent() {
		
		ActionListBox options = new ActionListBox();
		
		options.addItem("Submit Bug Report", new Runnable() {
			@Override
			public void run() {
				new MessageDialogBuilder().setTitle("Future Task")
				.setText("This functionality will be coming in the future.")
				.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
				.addButton(MessageDialogButton.Close).build()
				.showDialog(Global.OBJECTS.getMainWindow().getTextGUI());
			}
		});
		options.addItem("Logout and Switch User", new Runnable() {
			@Override
			public void run() {
				Global.OBJECTS.getMainWindow().close();
				Global.OBJECTS.setInitialWindow(null);
				Global.OBJECTS.setMainWindow(null);
				Global.OBJECTS.setCurrentUser(null);
			}
		});
		options.addItem("Quit", new Runnable() {
			@Override
			public void run() {
				Global.OBJECTS.setExitFlag(true);
				Global.OBJECTS.getMainWindow().close();
			}
		});
		
		this.addComponent(options.withBorder(Borders.singleLine("Administrative Options")));
		this.interactable = options;
	}
	
	@Override
	public Interactable getPrimaryInteractable() {
		return this.interactable;
	}
}
