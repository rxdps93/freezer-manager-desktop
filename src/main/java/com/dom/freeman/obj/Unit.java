package com.dom.freeman.obj;

public enum Unit {

	OUNCE(1, "Ounces", "OZ", "OZ"),
	FLUID_OUNCE(1, "Fluid Ounces", "FL OZ", "FL OZ"),
	POUND(16, "Pounds", "LB", "LBS"),
	CUP(8, "Cups", "CUP", "CUPS"),
	PIECE(200, "Pieces", "PC", "PCS"),
	UNIT(250, "Units", "X", "X"),
	GRAM(0.04, "Grams", "G", "G"),
	KILOGRAM(35.27, "Kilograms", "KG", "KG"),
	TEASPOON(0.17, "Teaspoons", "TSP", "TSP"),
	TABLESPOON(0.5, "Tablespoons", "TBSP", "TBSP"),
	PINT(16, "U.S. Pints", "PT", "PTS"),
	QUART(32, "U.S. Quarts", "QT", "QTS"),
	GALLON(128, "U.S. Gallons", "GAL", "GALS"),
	LITER(33.81, "Liters", "L", "L"),
	MILLILITER(0.04, "Milliliters", "ML", "ML"),
	ERROR(1000, "Unknown", "ERR", "ERR");
	
	// An ounce is considered the "standard unit" in this application, therefore this value is used to compare units together
	// Not all units are perfectly comparable so this won't always be mathematically based (e.g. "piece" can mean anything weight-wise)
	private final double compareValue;
	private final String commonName;
	private final String abbrevSingular;
	private final String abbrevPlural;
	
	private Unit(double compareValue, String commonName, String abbrevSingular, String abbrevPlural) {
		this.compareValue = compareValue;
		this.commonName = commonName;
		this.abbrevSingular = abbrevSingular;
		this.abbrevPlural = abbrevPlural;
	}
	
	public double getCompareValue() {
		return this.compareValue;
	}
	
	public String getCommonName() {
		return this.commonName;
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
