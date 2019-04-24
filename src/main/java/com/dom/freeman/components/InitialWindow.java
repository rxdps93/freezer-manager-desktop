package com.dom.freeman.components;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.dom.freeman.obj.users.User;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.gui2.GridLayout.Alignment;

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

		Panel loginPanel = new Panel(new GridLayout(2));

		// Description
		loginPanel.addComponent(new Label("Please select your user account to log in.")
				.setLayoutData(GridLayout.createLayoutData(
						Alignment.BEGINNING, Alignment.CENTER, false, false, 2, 1)));

		// Display name
		loginPanel.addComponent(new EmptySpace()
				.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		loginPanel.addComponent(new Label("Select your display name"));
		ComboBox<String> users = new ComboBox<>();
		for (User u : Global.OBJECTS.getUsers()) {
			users.addItem(u.getDisplayName());
		}
		users.setReadOnly(false);
		loginPanel.addComponent(users);
		
		// Password
		loginPanel.addComponent(new EmptySpace()
				.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		loginPanel.addComponent(new Label("Enter your password"));
		TextBox pass = new TextBox();
		pass.setMask('*');
		loginPanel.addComponent(pass);
		
		// Buttons
		Panel buttonPanel = new Panel(new GridLayout(2));
		buttonPanel.addComponent(new Button("Submit", new Runnable() {
			@Override
			public void run() {
				
				if (Utility.METHODS.validateUser(pass.getText(), users.getSelectedItem())) {
					Global.OBJECTS.setCurrentUser(Utility.METHODS.getUserByDisplayName(users.getSelectedItem()));
					close();
				} else {
					new MessageDialogBuilder().setTitle("Unable to log in")
					.setText("The entered password does not match the one stored for the selected user.")
					.setExtraWindowHints(Arrays.asList(Hint.CENTERED))
					.addButton(MessageDialogButton.OK).build().showDialog(getTextGUI());
				}
			}
		}));
		buttonPanel.addComponent(new Button("Clear", new Runnable() {
			@Override
			public void run() {
				users.setSelectedIndex(0);
				pass.setText("");
			}
		}));
		loginPanel.addComponent(new EmptySpace()
				.setLayoutData(GridLayout.createHorizontallyFilledLayoutData(2)));
		loginPanel.addComponent(new EmptySpace());
		loginPanel.addComponent(buttonPanel);

		this.setComponent(loginPanel);
		this.setHints(Arrays.asList(Hint.CENTERED));
		this.setPosition(new TerminalPosition(1, 2));
		
		this.addWindowListener(new WindowListener() {

			@Override
			public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
				
				switch (keyStroke.getKeyType()) {
				case F1:
					if (pass.getMask() == null)
						pass.setMask('*');
					else
						pass.setMask(null);	
					break;
					default:
						break;
				}
			}

			@Override
			public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
