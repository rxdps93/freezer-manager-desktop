package com.dom.freeman;

import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.dom.freeman.components.MainWindow;
import com.dom.freeman.obj.Item;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

public class FreeMan {

	public static void main(String[] args) throws IOException, InterruptedException {
			
		SwingTerminalFontConfiguration stfc = SwingTerminalFontConfiguration.newInstance(new Font("Courier New", Font.PLAIN, 12));
		Terminal terminal = new DefaultTerminalFactory()
				.setTerminalEmulatorTitle("Freezer Inventory Management System")
				.setTerminalEmulatorFontConfiguration(stfc)
				.setInitialTerminalSize(new TerminalSize(190, 75))
				.createTerminal();
		
		Screen screen = new TerminalScreen(terminal);
		screen.startScreen();
		
		final MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
		
		List<Item> items = Utility.METHODS.parseItemsFromFile();
		Map<String, Integer> types = Utility.METHODS.itemTypeCount(items);
		
		gui.addWindowAndWait(new MainWindow("FREEZER INVENTORY MANAGEMENT SYSTEM", items, types));
	}
}
