package com.dom.freeman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dom.freeman.obj.Item;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public enum Utility {

	METHODS;

	public List<Item> parseItemsFromFile() {

		List<Item> items = Collections.emptyList();
		try {
			
			CsvToBean<Item> csv = new CsvToBeanBuilder<Item>(Files.newBufferedReader(Paths.get("src/main/resources/contents.csv")))
					.withType(Item.class).build();
			
			items = csv.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return items;
	}
	
	public Map<String, Integer> itemTypeCount(List<Item> items) {
		
		Map<String, Integer> types = new TreeMap<String, Integer>();
		
		for (Item item : items) {
			
			if (!types.containsKey(item.getType()))
				types.put(item.getType(), 1);
			else
				types.replace(item.getType(), types.get(item.getType()) + 1);
		}
		
		return types;
	}
}
