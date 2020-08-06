package space_trader_game;

import enums.RegionName;
import enums.TechLevel;

public class Region {
	
	private int xCoord;
	private int yCoord;
	private TechLevel techLevel;
	private String regionName;
	private Market market;
	private int regionLevel;
	private boolean winRegion;
	
	public Region(int i, int j, TechLevel techLevel2, String string) {
	    xCoord = i;
        yCoord = j;
        techLevel = techLevel2;
        regionName = string;
        regionLevel = techLevel.getIntLevel();
        market = new Market(techLevel.getLevel());
	}
	
	public Region() {
		this(Coordinate.randomXCoord(), Coordinate.randomYCoord(), 
			TechLevel.randomTechLevel(), RegionName.randomRegionName());
	}

	//no setters!
	//getters
	public int getXCoord() {
		return xCoord;
	}

	public int getYCoord() {
		return yCoord;
	}

	public String getCoordinates() {
		return xCoord + ", " + yCoord;
	}

	public TechLevel getTechLevel() {
		return techLevel;
	}

	public String getName() {
		return regionName;
	}
	
	public Market getMarket(){
		return market;
	}
	
	public int getRegionLevel() {
		return this.regionLevel;
	}
	
	public boolean getWinRegion() {
	    return this.winRegion;
	}
	
	public void setWinRegion(boolean bool) {
	    this.winRegion = bool;
	}
}
