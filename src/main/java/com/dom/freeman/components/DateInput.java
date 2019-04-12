package com.dom.freeman.components;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.ComboBox.Listener;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Panel;

public class DateInput extends Panel {
	
	private ComboBox<Year> yearPicker;
	private final ComboBox<Month> monthPicker;
	private final ComboBox<Integer> dayPicker;
	
	private final int minYear;
	private final int maxYear;

	public DateInput(int minimumYear, int maximumYear) {
		super(new GridLayout(3));
		
		this.minYear = minimumYear;
		this.maxYear = maximumYear;
		
		this.yearPicker = new InventoryComboBox<>();
		this.monthPicker = new InventoryComboBox<>();
		this.dayPicker = new InventoryComboBox<>();
		
		configureYearPicker(minimumYear, maximumYear);
		configureMonthPicker(false);
		configureDayPicker(false, 30);
		
		this.yearPicker.addTo(this);
		this.monthPicker.addTo(this);
		this.dayPicker.addTo(this);
	}
	
	public DateInput(LocalDate initialDate) {
		this(initialDate.getYear() - 5, initialDate.getYear() + 5);
		this.setSelectedDate(initialDate);
	}
	
	public LocalDate getSelectedDate() {
		return LocalDate.of(this.yearPicker.getSelectedItem().getValue(), this.monthPicker.getSelectedItem(), this.dayPicker.getSelectedItem());
	}
	
	public void setEnabled(boolean enabled) {
		this.yearPicker.setEnabled(enabled);
		this.monthPicker.setEnabled(enabled);
		this.dayPicker.setEnabled(enabled);
		this.yearPicker.takeFocus();
	}
	
	public void setSelectedDate(LocalDate date) {
		
		if (date.getYear() < this.minYear) {
			this.yearPicker.clearItems();
			this.configureYearPicker(date.getYear(), this.maxYear);
		} else if (date.getYear() > this.maxYear) {
			this.yearPicker.clearItems();
			this.configureYearPicker(this.minYear, date.getYear());
		}
		
		this.yearPicker.setSelectedItem(Year.of(date.getYear()));
		this.monthPicker.setSelectedItem(date.getMonth());
		this.dayPicker.setSelectedItem(date.getDayOfMonth());
		
		this.yearPicker.setEnabled(true);
		this.monthPicker.setEnabled(true);
		this.dayPicker.setEnabled(true);
	}
	
	private void configureYearPicker(int min, int max) {
		
		for (int year = min; year < max + 1; year++) {
			this.yearPicker.addItem(Year.of(year));
		}
		this.yearPicker.addListener(new YearPickerListener(this));
	}
	
	private void configureMonthPicker(boolean enabled) {
		
		this.monthPicker.setEnabled(enabled);
		this.monthPicker.addListener(new MonthPickerListener(this));
		for (Month month : Month.values()) {
			this.monthPicker.addItem(month);
		}
	}
	
	private void configureDayPicker(boolean enabled, int days) {
		
		this.dayPicker.clearItems();
		this.dayPicker.setEnabled(enabled);
		for (int i = 1; i < days + 1; i++) {
			this.dayPicker.addItem(i);
		}
	}
	
	private class YearPickerListener implements Listener {
		
		private DateInput parent;
		
		public YearPickerListener(DateInput parent) {
			this.parent = parent;
		}

		@Override
		public void onSelectionChanged(int selectedIndex, int previousSelection) {
			this.parent.monthPicker.setEnabled(true);
			
			if (this.parent.monthPicker.getSelectedItem().equals(Month.FEBRUARY)) {
				this.parent.configureDayPicker(true, Month.FEBRUARY.length(this.parent.yearPicker.getItem(selectedIndex).isLeap()));
			}
		}
	}
	
	private class MonthPickerListener implements Listener {
		
		private DateInput parent;
		
		public MonthPickerListener(DateInput parent) {
			this.parent = parent;
		}

		@Override
		public void onSelectionChanged(int selectedIndex, int previousSelection) {
			if (selectedIndex != previousSelection)
				this.parent.configureDayPicker(true, this.parent.monthPicker.getItem(selectedIndex).length(this.parent.yearPicker.getSelectedItem().isLeap()));
		}
	}
}
