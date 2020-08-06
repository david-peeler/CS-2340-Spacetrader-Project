package enums;

public enum ShipTypes {
	TYPE1("Biplane"),
	TYPE2("Jet"),
	TYPE3("Helicarrier"),
	TYPE4("Rocket"),
	TYPE5("Space Station");
	
	private String typeName;
	
	private ShipTypes(String type) {
		typeName = type;
	}
	
	public String getTypeName() {
		return typeName;
	}
	
	public int getTypeMaxFuel() {
		if (this.typeName == "Biplane") {
			return 50;
		} else if (this.typeName == "Jet") {
			return 100;
		} else if (this.typeName == "Helicarrier") {
			return 150;
		} else if (this.typeName == "Rocket") {
			return 250;
		} else {
			return 500;
		}
	}
	
	public int getTypeMaxCargoCapacity() {
		if (this.typeName == "Biplane") {
			return 50;
		} else if (this.typeName == "Jet") {
			return 100;
		} else if (this.typeName == "Helicarrier") {
			return 150;
		} else if (this.typeName == "Rocket") {
			return 250;
		} else {
			return 500;
		}
	}
	
	public int getTypeMaxHealth() {
		if (this.typeName == "Biplane") {
			return 50;
		} else if (this.typeName == "Jet") {
			return 100;
		} else if (this.typeName == "Helicarrier") {
			return 150;
		} else if (this.typeName == "Rocket") {
			return 250;
		} else {
			return 500;
		}
	}
}