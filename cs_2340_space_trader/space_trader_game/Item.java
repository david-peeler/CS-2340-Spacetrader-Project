package space_trader_game;

import enums.ItemName;
import services.PriceCalculatorService;

/**
 * @author CS Group 81
 *
 */

//TODO figure out a way to make this a general class for an abudance of differntitems
//TODO make specific items correspond to specific tech levels (e.g. an item that appears in a ag region
//	   will also appear in an agricultural region, but not in a futuristic region
public class Item {
	private String name;
	private int basePrice;
	private int itemLevel;
	private Region regionPurchased;
	private int quantity;

	public Item(ItemName item) {
	    name = item.getItemName();
	    basePrice = item.getItemPrice();
		itemLevel = item.getLevelAvailability();
		quantity = 1;
	}

	public Region getRegionPurchased() {
		return this.regionPurchased;
	}

	public void setRegionPurchased(Region region) {
		this.regionPurchased = region;
	}
	
	public void setQuantity(int a) {
		this.quantity = a;
	}

	//no setter for base price bc it should never change
	public int getBasePrice() {
		return this.basePrice;
	}

	public String getName() {
		return this.name;
	}

	public int getItemLevel() {
		return this.itemLevel;
	}
	public int getQuantity() {
		return this.quantity;
	}
}
