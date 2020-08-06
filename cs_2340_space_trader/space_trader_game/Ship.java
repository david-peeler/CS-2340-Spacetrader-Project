package space_trader_game;

import enums.ShipTypes;

public class Ship {
	private String name;
	private int cargoSpace;
	private int health;
	private int fuelCapacity;
	private ShipTypes type;
	
	private int currentFuel;
	private int currentHealth;
	private int currentCargoSpace;
	
	public Ship() {
		this.name = null;
		this.type = ShipTypes.TYPE1;
		
		//find the max stats for everything
		this.cargoSpace = this.type.getTypeMaxCargoCapacity();
		this.health = this.type.getTypeMaxHealth();
		this.fuelCapacity = this.type.getTypeMaxFuel();
		//start out with the max stats for the ship
		this.currentCargoSpace = this.cargoSpace;
		this.currentFuel = this.fuelCapacity;
		this.currentHealth = this.health;
	}
	
	public String getName() {
		return this.name;
	}

	public int getCargoSpace() {
		return this.cargoSpace;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getFuelCapacity() {
		return this.fuelCapacity;
	}
	
	public ShipTypes getType() {
		return this.type;
	}
	
	public void setType(ShipTypes type) {
		this.type = type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCurrentCargoSpace() {
		return this.currentCargoSpace;
	}
	
	public void setCurrentCargoSpace(int cargoSpace) {
		this.currentCargoSpace = cargoSpace;
	}
	
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	public void setCurrentHealth(int l) {
		this.currentHealth = l;
	}
	
	public int getCurrentFuel() {
		return this.currentFuel;
	}
	
	public void setCurrentFuel(int l) {
		this.currentFuel = l;
	}
}
