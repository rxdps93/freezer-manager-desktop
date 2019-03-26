package com.dom.freeman.components;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Collections;

import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.ComboBox.Listener;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Panel;

public class DateInput extends Panel {
	
	private final ComboBox<Year> yearPicker;
	private final ComboBox<Month> monthPicker;
	private final ComboBox<Integer> dayPicker;

	public DateInput(int minimumYear, int maximumYear) {
		super(new GridLayout(3));
		
		this.yearPicker = new ComboBox<>("Year", Collections.emptyList());
		this.monthPicker = new ComboBox<>("Month", Collections.emptyList());
		this.dayPicker = new ComboBox<>("Day", Collections.emptyList());
		
		configureYearPicker(minimumYear, maximumYear);
		configureMonthPicker(false);
		configureDayPicker(false, 30);
		
		this.yearPicker.addTo(this);
		this.monthPicker.addTo(this);
		this.dayPicker.addTo(this);
	}
	
	public DateInput(LocalDate initialDate) {
		this(initialDate.getYear() - 5, initialDate.getYear() + 5);
		
		this.yearPicker.setSelectedItem(Year.of(initialDate.getYear()));
		this.monthPicker.setSelectedItem(initialDate.getMonth());
		this.dayPicker.setSelectedItem(initialDate.getDayOfMonth());
	}
	
	public LocalDate getSelectedDate() {
		return LocalDate.of(this.yearPicker.getSelectedItem().getValue(), this.monthPicker.getSelectedItem(), this.dayPicker.getSelectedItem());
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