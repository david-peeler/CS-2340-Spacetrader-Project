package space_trader_game;
import enums.ItemName;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Player extends JFrame {

	private String name;
	private int pilot;
	private int fighter;
	private int engineer;
	private int merchant;
	private Region region;
	private int credits;
	private int money;
	private String gameDifficulty;
	private List<Item> inventory = new ArrayList<Item>(10);
	private Ship ship = new Ship();
	
	public Player(String name, int pilot, int fighter, int engineer, int merchant, int credits, int money) {
		this.name = name;
		this.pilot = pilot;
		this.fighter = fighter;
		this.engineer = engineer;
		this.merchant = merchant;
		this.credits = credits;
		this.money = money;
	}
	
	public Player() {
		this(null, 0, 0, 0, 0, 0, 0);
	}

	//setters
	public void setName(String a) {
		this.name = a;
	}

	public void setPilot(int a) {
		this.pilot = a;
	}

	public void setFighter(int a) {
		this.fighter = a;
	}

	public void setEngineer(int a) {
		this.engineer = a;
	}

	public void setMerchant(int a) {
		this.merchant = a;
	}

	public void setRegion(Region a) {
		this.region = a;
	}

	public void setCredits(int a) {
		this.credits = a;
	}

	public void setMoney(int a) {
		this.money = a;
	}

	public void setGameDifficulty(String a) {
		this.gameDifficulty = gameDifficulty;
	}

	public void setInventory(List<Item> a) {
        this.inventory = a;
    }
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}

	//getters
	public String getName() {
		return name;
	}

	public int getPilot() {
		return pilot;
	}

	public int getFighter() {
		return fighter;
	}

	public int getEngineer() {
		return engineer;
	}

	public int getMerchant() {
		return merchant;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public int getCredits() {
		return credits;
	}

	public int getMoney() {
		return money;
	}
	
	public Ship getShip() {
		return ship;
	}

	public String getGameDifficulty() {
		return gameDifficulty;
	}

	public List<Item> getInventory() {
        	return inventory;
    	}
	
	public void inventoryAdd(Item item) {
		boolean found = false;
		for (Item item1 : inventory) {
			if (item1.getName().equals(item.getName())) {
				item1.setQuantity(item1.getQuantity() + 1);
				found = true;
				break;
			}
		}
		if (!found) {
			List<Item> newInven = getInventory();
			newInven.add(item);
			setInventory(newInven);
		}
	}
	
	public void inventoryRemove(Item item) {
		for (Item item1 : inventory) {
			if (item1.getName().equals(item.getName())) {
				if (item1.getQuantity() == 1) {
					List<Item> newInven = getInventory();
					newInven.remove(item1);
					setInventory(newInven);
					break;
				} else if (item1.getQuantity() > 1) {
					item1.setQuantity(item1.getQuantity() - 1);
					break;
				}
			}
		}
	}
}
