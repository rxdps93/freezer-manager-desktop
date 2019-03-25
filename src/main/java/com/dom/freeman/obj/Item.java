package com.dom.freeman.obj;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

public class Item {

	@CsvBindByPosition(position = 0)
	private String type;
	
	@CsvBindByPosition(position = 1)
	private int quantity;
	
	@CsvCustomBindByPosition(position = 2, converter = UnitConverter.class)
	private Unit unit;
	
	@CsvCustomBindByPosition(position = 3, converter = LocalDateConverter.class)
	private LocalDate added;
	
	@CsvCustomBindByPosition(position = 4, converter = LocalDateConverter.class)
	private LocalDate expires;
	
	public Item(String type, int quantity, Unit unit, LocalDate added, LocalDate expires) {
		this.type = type;
		this.quantity = quantity;
		this.unit = unit;
		this.added = added;
		this.expires = expires;
	}
	
	public Item() {
		
	}

	public String getType() {
		return this.type;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public LocalDate getAdded() {
		return this.added;
	}

	public LocalDate getExpires() {
		return this.expires;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public void setAdded(LocalDate added) {
		this.added = added;
	}

	public void setExpires(LocalDate expires) {
		this.expires = expires;
	}
	
	@Override
	public String toString() {
		
		return String.format("%20s%5d%5s%15s%15s", getType(), getQuantity(), getUnit(), getAdded().toString(), getExpires().toString());
	}
}
