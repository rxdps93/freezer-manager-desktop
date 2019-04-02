package com.dom.freeman.obj;

import java.time.LocalDate;

import com.dom.freeman.Global;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;

public class Item {

	@CsvBindByPosition(position = 0)
	private String type;

	@CsvBindByPosition(position = 1)
	private int quantity;

	@CsvCustomBindByPosition(position = 2, converter = UnitConverter.class)
	private Unit unit;
	
	@CsvCustomBindByPosition(position = 3, converter = FreezerConverter.class)
	private Freezer location;

	@CsvCustomBindByPosition(position = 4, converter = LocalDateConverter.class)
	private LocalDate added;

	@CsvCustomBindByPosition(position = 5, converter = LocalDateConverter.class)
	private LocalDate expires;
	
	@CsvBindByPosition(position = 6)
	private String id;

	public Item(String type, int quantity, Unit unit, Freezer location, LocalDate added, LocalDate expires, String id) {
		this.type = type;
		this.quantity = quantity;
		this.unit = unit;
		this.location = location;
		this.added = added;
		this.expires = expires;
		this.id = id;
	}
	
	public Item() {
		
	}
	
	public String getId() {
		return this.id;
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
	
	public Freezer getLocation() {
		return this.location;
	}
	
	public LocalDate getAdded() {
		return this.added;
	}
	
	public LocalDate getExpires() {
		return this.expires;
	}

	public String getAddedFormatted() {
		return this.added.format(Global.OBJECTS.getDateFormat());
	}

	public String getExpiresFormatted() {
		return this.expires.format(Global.OBJECTS.getDateFormat());
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
	
	public void setFreezer(Freezer location) {
		this.location = location;
	}

	public void setAdded(LocalDate added) {
		this.added = added;
	}

	public void setExpires(LocalDate expires) {
		this.expires = expires;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String[] toCsvString() {

		return new String[] {
				this.getType().toUpperCase(),
				Integer.toString(this.getQuantity()),
				this.getUnit().getAbbreviationByValue(this.getQuantity()).toUpperCase(),
				this.getLocation().getFreezerLocation().toUpperCase(),
				this.getAddedFormatted(),
				this.getExpiresFormatted(),
				this.getId()
		};
	}

	@Override
	public String toString() {

		return String.format("%20s%5d%5s%10s%15s%15s", this.getType(), this.getQuantity(), this.getUnit(), this.getLocation(), this.getAddedFormatted(), this.getExpiresFormatted());
	}
}
