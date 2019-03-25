package com.dom.freeman.obj;

public enum Unit {

	OUNCE(1, "OZ", "OZ"),
	FLUID_OUNCE(1, "FL OZ", "FL OZ"),
	POUND(16, "LB", "LBS"),
	CUP(8, "CUP", "CUPS"),
	PIECE(200, "PC", "PCS"),
	UNIT(250, "X", "X"),
	GRAM(0.04, "G", "G"),
	KILOGRAM(35.27, "KG", "KG"),
	TEASPOON(0.17, "TSP", "TSP"),
	TABLESPOON(0.5, "TBSP", "TBSP"),
	PINT(16, "PT", "PTS"),
	QUART(32, "QT", "QTS"),
	GALLON(128, "GAL", "GALS"),
	LITER(33.81, "L", "L"),
	MILLILITER(0.04, "ML", "ML"),
	ERROR(1000, "ERR", "ERR");
	
	// An ounce is considered the "standard unit" in this application, therefore this value is used to compare units together
	// Not all units are perfectly comparable so this won't always be mathematically based (e.g. "piece" can mean anything weight-wise)
	private final double compareValue;
	private final String abbrevSingular;
	private final String abbrevPlural;
	
	private Unit(double compareValue, String abbrevSingular, String abbrevPlural) {
		this.compareValue = compareValue;
		this.abbrevSingular = abbrevSingular;
		this.abbrevPlural = abbrevPlural;
	}
	
	public double getCompareValue() {
		return this.compareValue;
	}
	
	public String getSingularAbbreviation() {
		return this.abbrevSingular;
	}
	
	public String getPluralAbbreviation() {
		return this.abbrevPlural;
	}
	
	public String getAbbreviationByValue(int quantity) {
		return quantity != 1 ? this.abbrevPlural : this.abbrevSingular;
	}
}
