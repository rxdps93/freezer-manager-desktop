package com.dom.freeman.components;

import java.util.Arrays;

import com.dom.freeman.obj.users.User;
import com.dom.freeman.utils.Global;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

public class InitialWindow extends BasicWindow {
	
	public InitialWindow() {
		super();
		this.configureContent();
	}
	
	public InitialWindow(String title) {
		super(title);
		this.configureContent();
	}
	
	private void configureContent() {
		
		Panel loginPanel = new Panel(new LinearLayout(Direction.VERTICAL));
		
		loginPanel.addComponent(new Label("Please select your user account to log in."));
		
		ActionListBox users = new ActionListBox();
		
		for (User u : Global.OBJECTS.getUsers()) {
			users.addItem(String.format("%s %s [%s]",
					u.getFirstName(), u.getLastName(), u.getDisplayName()), new Runnable() {
				@Override
				public void run() {
					Global.OBJECTS.setCurrentUser(u);
					close();
				}
			});
		}
		
		loginPanel.addComponent(users);
		
		this.setComponent(loginPanel);
		this.setHints(Arrays.asList(Hint.CENTERED));
		this.setPosition(new TerminalPosition(1, 2));
	}
}
