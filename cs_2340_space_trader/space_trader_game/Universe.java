package space_trader_game;
import enums.ItemName;
import services.PriceCalculatorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Universe {

	Region region1;
	Region region2;
	Region region3;
	Region region4;
	Region region5;
	Region region6;
	Region region7;
	Region region8;
	Region region9;
	Region region10;

	private static List<Region> regionsList = new ArrayList<>();
	public static List<String> regionsListString = new ArrayList<>();
	public static List<Item> itemsList = new ArrayList<>();

	private static final Random RANDOM = new Random();
	
	public Universe() {
		//instantiates all regions
		region1 = new Region();
		region2 = new Region();
		region3 = new Region();
		region4 = new Region();
		region5 = new Region();
		region6 = new Region();
		region7 = new Region();
		region8 = new Region();
		region9 = new Region();
		region10 = new Region();

		//adds regions to list
		regionsList.add(region1);
		regionsList.add(region2);
		regionsList.add(region3);
		regionsList.add(region4);
		regionsList.add(region5);
		regionsList.add(region6);
		regionsList.add(region7);
		regionsList.add(region8);
		regionsList.add(region9);
		regionsList.add(region10);

		regionsListString.add(region1.getName());
		regionsListString.add(region2.getName());
		regionsListString.add(region3.getName());
		regionsListString.add(region4.getName());
		regionsListString.add(region5.getName());
		regionsListString.add(region6.getName());
		regionsListString.add(region7.getName());
		regionsListString.add(region8.getName());
		regionsListString.add(region9.getName());
		regionsListString.add(region10.getName());

		//adds items to list
		itemsList.add(new Item(ItemName.CAPE));
		itemsList.add(new Item(ItemName.COMPASS));
		itemsList.add(new Item(ItemName.WOOD));
		itemsList.add(new Item(ItemName.IRON));
		itemsList.add(new Item(ItemName.ARMOR));
		itemsList.add(new Item(ItemName.FUEL));
		itemsList.add(new Item(ItemName.MEDICINE));
		itemsList.add(new Item(ItemName.SHIP_DEFENSES));
		itemsList.add(new Item(ItemName.SHIP_OFFENSES));
		itemsList.add(new Item(ItemName.INFINITY_STONE));
		
		regionsList.get(new Random().nextInt(10)).setWinRegion(true);
	}


    public static Region randomStartingRegion()  {
  		int thisRand = RANDOM.nextInt(regionsList.size());
  		return regionsList.get(thisRand);
    }

    /**
	 * given name, returns corresponding Region object
     */
    public static Region getRegionFromList(String name) {
    	Region returnThis = null;
    	for (int i = 0; i < regionsList.size(); i++) {
    		if (regionsList.get(i).getName().equals(name)) {
    			returnThis = regionsList.get(i);
    		}
    	}
    	return returnThis;
    }

	public static List<Region> getRegionsList() {
		return regionsList;
	}

	public static void setRegionsList(List<Region> regionsList) {
		Universe.regionsList = regionsList;
	}

	/**
	 * given name, returns corresponding Item object
	 */
	public static Item getItemFromList(String name) {
		Item returnThis = null;
		for (int i = 0; i < itemsList.size(); i++) {
			if (itemsList.get(i).getName().equals(name)) {
				returnThis = itemsList.get(i);
			}
		}
		return returnThis;
	}

	public static Item randomItem()  {
		int thisRand = RANDOM.nextInt(itemsList.size());
		return itemsList.get(thisRand);
	}

}