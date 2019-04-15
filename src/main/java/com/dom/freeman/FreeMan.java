package com.dom.freeman;

import java.awt.Font;
import java.io.IOException;

import com.dom.freeman.components.MainWindow;
import com.dom.freeman.obj.users.User;
import com.dom.freeman.obj.users.UserOperations;
import com.dom.freeman.utils.Global;
import com.dom.freeman.utils.Utility;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

public class FreeMan {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		SwingTerminalFontConfiguration stfc = SwingTerminalFontConfiguration.newInstance(new Font("Courier New", Font.PLAIN, 12));
		Terminal terminal = new DefaultTerminalFactory()
				.setTerminalEmulatorTitle("Freezer Inventory Management System")
				.setTerminalEmulatorFontConfiguration(stfc)
				.setInitialTerminalSize(new TerminalSize(200, 75))
				.createTerminal();
		
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();
		
		final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
		
		Utility.METHODS.updateInventory();
		
		// TODO: ADMIN ACCOUNT; REMOVE EVENTUALLY
		User admin = new User("Admin", "Adminson", "rxdps93", "trump2020", UserOperations.values());
		Global.OBJECTS.setCurrentUser(admin);
		
		gui.addWindowAndWait(new MainWindow("FREEZER INVENTORY MANAGEMENT SYSTEM"));
	}
}
