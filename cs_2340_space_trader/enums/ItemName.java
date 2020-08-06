package enums;

public enum ItemName {
	CAPE("a rad superhero cape", 10, 2),
	COMPASS("a compass", 10, 3),
	FUEL("additional fuel", 15, 1),
	WOOD("wood", 20, 1),
	IRON("iron", 30, 3),
	ARMOR("body armor", 40, 4),
	MEDICINE("medicine", 100, 5),
	SHIP_DEFENSES("additional ship defenses", 300, 6),
	SHIP_OFFENSES("additional weaponry for ship", 300, 6),
	INFINITY_STONE("an Infinity Stone", 1500, 7);

	private String item;
	/**
	 * the baseline price of the item, regardless of region or merchant skill
	 */
	private int price;
	/**
	 * the tech levels at which this item is available.
	 * levelAvailability and above are the levels
	 * at which the item is available
	 */
	private int levelAvailability;

	private ItemName(String item, int price, int levelAvailability) {
		this.item = item;
		this.price = price;
		this.levelAvailability = levelAvailability;
	}

	//getters
	public String getItemName() {
		return item;
	}

	public int getItemPrice() {
		return price;
	}

	public int getLevelAvailability() {
		return levelAvailability;
	}
}
