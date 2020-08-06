package services;

import enums.ShipTypes;
import space_trader_game.Ship;
import space_trader_game.Player;
import space_trader_game.Region;

import java.util.Random;

public class FuelCostCalcService {
    private final static Random rand = new Random();

    /**
     * @param ship player's current ship
     * @param currentRegion current location
     * @param destination desired location
     * @param player player
     * @return the amount of fuel required to travel to the destination,
     *         given player's pilot skill and distance to region
     */
    public static int findFuelCost(Ship ship, Region currentRegion, Region destination, Player player) {

        double pilot = player.getPilot();
        int xDiff = Math.abs(currentRegion.getXCoord() - destination.getXCoord());
        int yDiff = Math.abs(currentRegion.getYCoord() - destination.getYCoord());
        int fuel = 5;
        while (xDiff > 0) {
            xDiff = xDiff / 10;
            fuel = fuel + 5;
        }
        while (yDiff > 0) {
            yDiff = yDiff / 10;
            fuel = fuel + 5;
        }
        double decOff = (pilot * 5) / 100;
        int amountOff = (int) (fuel * decOff);

        return fuel - amountOff;
    }

    /**
     * @param ship the ship being used to travel with
     * @param fuelCost the cost of travel to a new region, as
     *                 calculated by findFuelCost method above
     * @return boolean of whether travel is possible or not
     */
    public static boolean isEnoughFuel(Ship ship, int fuelCost) {
        if (fuelCost > ship.getCurrentFuel()) {
            return false;
        } else {
            return true;
        }
    }
}
