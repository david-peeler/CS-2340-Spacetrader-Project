package enums;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum TechLevel {
	PREAG("Pre-Agricultural", 1),
	AGRICULTURE("Agricultural", 2),
	MEDIEVAL("Medieval", 3),
	RENAISSANCE("Renaissance", 4),
	INDUSTRIAL("Industrial", 5),
	MODERN("Modern", 6),
	FUTURISTIC("Futuristic", 7);

	private String level;
	private int intLevel; //integer representation of the TechLevel
	private static List<TechLevel> list = new ArrayList<>(Arrays.asList(values()));
  	private static final Random RANDOM = new Random();

  	private TechLevel(String techLevel, int intLevel) {
  		level = techLevel;
  		this.intLevel = intLevel;
  	}

  	//getters
  	public String getLevel() {
  		return level;
  	}

  	public int getIntLevel() {
  		return intLevel;
  	}

	/**
	 * generates a random TechLevel
	 */
  	public static TechLevel randomTechLevel() {
  		int thisRand = RANDOM.nextInt(list.size());
  		TechLevel returnThis = list.get(thisRand);
  		return returnThis;
    }
}