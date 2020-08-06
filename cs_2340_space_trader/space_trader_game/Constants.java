package space_trader_game;

import java.awt.Dimension;
import java.awt.Toolkit;

public final class Constants {
	//Screensize (height, width) of menus
	final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final static int MENU_HEIGHT = (int) (screenSize.height * .5);
	public final static int MENU_WIDTH = (int) (screenSize.width * .5);
	
	public final static int MAX_SHIP_CARGO_SPACE = 100;
	public final static int MAX_SHIP_HEALTH = 100;
	public final static int MAX_SHIP_FUEL_CAPACITY = 100;
	
	public final static int BASE_FUEL_TRAVEL_COST_PER_UNIT_DISTANCE = 1;
}
