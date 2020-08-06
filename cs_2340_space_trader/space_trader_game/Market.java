package space_trader_game;

import java.util.Random;

public class Market {
	private Item[] availableItems = new Item[10];
	private String techLevel;
	private int randomPercent;
	
	public Market(String techLevel) {
		this.techLevel = techLevel;
		Random random = new Random();
		this.randomPercent = 10 * (random.nextInt(6) - 3);
	}
	
	public void addItemToMarket(int index, Item item) {
		this.availableItems[index] = item;
	}
	
	public Item addItemToMarket(Item item) {
		for (int i = 0; i < availableItems.length; i++) {
			if (availableItems[i] == null) {
				availableItems[i] = item;
				return item;
			}
		}
		return null;
	}
	
	public void generateMarketItems() {
		//TODO have it push 10 items for them to buy
		//TODO have it update those 10 item's prices using the
		//     service
	}
	
	public void removeItemFromMarket(Item item) {
		for (int i = 0; i < availableItems.length; i++) {
			if (availableItems[i] == item) {
				availableItems[i] = null;
			}
		}
	}
	
	public void removeItemFromMarket(int index) {
		this.availableItems[index] = null;
	}

}
