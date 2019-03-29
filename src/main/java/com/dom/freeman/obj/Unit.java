package com.dom.freeman.obj;

public enum Unit {

	OUNCE(1, "Ounces", "OZ", "OZ", 1, 15),
	FLUID_OUNCE(1, "Fluid Ounces", "FL OZ", "FL OZ", 1, 63),
	POUND(16, "Pounds", "LB", "LBS", 1, -1),
	CUP(8, "Cups", "CUP", "CUPS", 1, 15),
	PIECE(200, "Pieces", "PC", "PCS", 1, -1),
	UNIT(250, "Units", "X", "X", 1, -1),
	GRAM(0.04, "Grams", "G", "G", 1, 999),
	KILOGRAM(35.27, "Kilograms", "KG", "KG", 1, -1),
	TEASPOON(0.17, "Teaspoons", "TSP", "TSP", 1, 3),
	TABLESPOON(0.5, "Tablespoons", "TBSP", "TBSP", 1, 15),
	PINT(16, "U.S. Pints", "PT", "PTS", 1, 7),
	QUART(32, "U.S. Quarts", "QT", "QTS", 1, 3),
	GALLON(128, "U.S. Gallons", "GAL", "GALS", 1, -1),
	LITER(33.81, "Liters", "L", "L", 1, -1),
	MILLILITER(0.04, "Milliliters", "ML", "ML", 1, 999),
	ERROR(1000, "Unknown", "ERR", "ERR", -1, -1);
	
	// An ounce is considered the "standard unit" in this application, therefore this value is used to compare units together
	// Not all units are perfectly comparable so this won't always be mathematically based (e.g. "piece" can mean anything weight-wise)
	private final double compareValue;
	private final String commonName;
	private final String abbrevSingular;
	private final String abbrevPlural;
	private final int min;
	private final int max;
	
	private Unit(double compareValue, String commonName, String abbrevSingular, String abbrevPlural, int rangeMin, int rangeMax) {
		this.compareValue = compareValue;
		this.commonName = commonName;
		this.abbrevSingular = abbrevSingular;
		this.abbrevPlural = abbrevPlural;
		this.min = rangeMin;
		this.max = rangeMax;
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
	
	public boolean inRange(int value) {
		return value >= this.min && (value <= this.max || this.max == -1);
	}
	
	@Override
	public String toString() {
		return this.getCommonName();
	}
}
