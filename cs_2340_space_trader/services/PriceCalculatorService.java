package services;

import space_trader_game.*;

import java.util.Random;

public class PriceCalculatorService {

	// when we sell or buy, the price of items are all changed. even though we are still in the same region.
	// because of this random. this calculates random price every time it's called.
	// that's why I declared random and made random percent which is not goting to be changed and different in each markets 
	// in the Market class
	private static Random rand = new Random();

	/**
	 * buying price generator.
	 *
	 * merchant points are multiplied by a random number between 1 and 10
	 * this becomes the percentage off the base price
	 * the region level is added to base price, so the higher tech level
	 * you're in, the higher the buying price
	 *
	 * @param item the item being bought
	 * @param currentRegion the region the item is being bought in
	 * @param player the player
	 * @return the price at which the item can be bought
	 */
	public static int findBuyingPrice(Item item, Region currentRegion, Player player) {
		float merchant = player.getMerchant();
		int level = currentRegion.getRegionLevel();
		int randMultiplier = rand.nextInt(11) + 1; //a number between 1 and 10
		double decOff = (merchant * randMultiplier) / 100;
		int amountOff = (int) (item.getBasePrice() * decOff);
		return (item.getBasePrice() - amountOff + level);
	}

	/**
	 * selling price generator for an item.
	 *
	 * merchant points are multiplied by a random number between 1 and 10
	 * this becomes the percentage added to the base price
	 * the region level is added to base price, so the higher tech level
	 * you're in, the higher the selling price.
	 *
	 * @param item the item being sold
	 * @param currentRegion the region the item is being sold in
	 * @param player the player
	 * @return the price at which the item can be sold
	 */
	public static int findSellingPrice(Item item, Region currentRegion, Player player) {
		float merchant = player.getMerchant();
		int level = currentRegion.getRegionLevel();
		int randMultiplier = rand.nextInt(11) + 1; //a number between 1 and 10
		double decAdd = (merchant * randMultiplier) / 100;
		int amountAdd = (int) (item.getBasePrice() * decAdd);
		return (item.getBasePrice() + amountAdd + level);
	}
}
