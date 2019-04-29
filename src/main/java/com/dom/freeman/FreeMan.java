package com.dom.freeman;

import java.awt.Font;
import java.io.IOException;

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

		do {
			gui.addWindowAndWait(Global.OBJECTS.getInitialWindow());
			
			if (Global.OBJECTS.getCurrentUser() == null)
				break;
			
			gui.addWindowAndWait(Global.OBJECTS.getMainWindow());
		} while (!Global.OBJECTS.getExitFlag());
		
		System.exit(0);
	}
}
