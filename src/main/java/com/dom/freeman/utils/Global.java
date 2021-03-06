package com.dom.freeman.utils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dom.freeman.components.AbstractInventoryTable;
import com.dom.freeman.components.InitialWindow;
import com.dom.freeman.components.MainWindow;
import com.dom.freeman.obj.Item;
import com.dom.freeman.obj.ItemTag;
import com.dom.freeman.obj.users.User;

public enum Global {

	OBJECTS;
	
	private List<Item> inventory;
	private Map<String, Integer> types;
	
	private List<ItemTag> itemTags;
	
	private List<User> users;
	
	private User currentUser;
	
	private List<AbstractInventoryTable<?>> registeredTables = new ArrayList<>();
	private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	private final String mainHeader = "FREEZER INVENTORY MANAGEMENT SYSTEM";
	private MainWindow mainWindow;
	private InitialWindow initialWindow;
	private boolean exitFlag = false;
	
//	private final String origPath = "src/main/resources/contents.csv";
//	private final String uuidPath = "src/main/resources/contents_uuid.csv";
	private final String frzrPath = "src/main/resources/contents_location.csv";
	private final String userPath = "src/main/resources/users.csv";
	private final String itemTagPath = "src/main/resources/item_tags.csv";
	
	public String getMainHeader() {
		return this.mainHeader;
	}
	
	public MainWindow getMainWindow() {
		if (this.mainWindow == null)
			this.mainWindow = new MainWindow();
		
		return this.mainWindow;
	}
	
	public InitialWindow getInitialWindow() {
		if (this.initialWindow == null)
			this.initialWindow = new InitialWindow();
		
		return this.initialWindow;
	}
	
	public List<Item> getInventory() {
		return this.inventory;
	}
	
	public boolean getExitFlag() {
		return this.exitFlag;
	}
	
	public Map<String, Integer> getTypes() {
		return this.types;
	}
	
	public List<ItemTag> getItemTags() {
		return this.itemTags;
	}
	
	public List<User> getUsers() {
		return this.users;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public List<AbstractInventoryTable<?>> getRegisteredTables() {
		return this.registeredTables;
	}
	
	public DateTimeFormatter getDateFormat() {
		return this.dateFormat;
	}
	
	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public void setInitialWindow(InitialWindow initialWindow) {
		this.initialWindow = initialWindow;
	}
	
	public void setInventory(List<Item> items) {
		this.inventory = items;
	}
	
	public void setExitFlag(boolean exitFlag) {
		this.exitFlag = exitFlag;
	}
	
	public void setTypes(Map<String, Integer> types) {
		this.types = types;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void setCurrentUser(User user) {
		this.currentUser = user;
	}
	
	public void setItemTags(List<ItemTag> itemTags) {
		this.itemTags = itemTags;
	}
	
	public void registerTable(AbstractInventoryTable<?> table) {
		this.registeredTables.add(table);
	}
	
	public String getMainPath() {
		return this.frzrPath;
	}
	
	public String getUserPath() {
		return this.userPath;
	}
	
	public String getItemTagPath() {
		return this.itemTagPath;
	}
}
