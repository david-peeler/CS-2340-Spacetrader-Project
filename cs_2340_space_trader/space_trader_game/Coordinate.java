package space_trader_game;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Coordinate {
	
	static List<Integer> xCoords = IntStream.range(-200, 200).boxed().collect(Collectors.toList());
	static List<Integer> yCoords = IntStream.range(-200, 200).boxed().collect(Collectors.toList());
  	private static final Random RANDOM = new Random();

	public static int randomXCoord() {
		int x = randomXHelp();
  		int returnThis = xCoords.get(x);
  
  		//remove that coordinate + the five above and below it
  		xCoords.remove(x);
  		for (int i = -5; i <= 5; i++) {
  			if ((x + i > 0) && (x + i < xCoords.size() - 1)) {
  				xCoords.remove(x + i);
  			}
  		}
  		
  		return returnThis;
	}

	private static int randomXHelp() {
		int x = RANDOM.nextInt(xCoords.size() - 1);
		if (xCoords.get(x) != null) { //index doesn't contain null
			if (x - 5 > 0) { //five coords below is within bounds
				if (xCoords.get(x - 5) == null) { //five coords below has been removed
					return randomXHelp(); //must find new coordinate
				}
			} else {
				return randomXHelp();
			}
			if (x + 5 < xCoords.size() - 1) { //five coords above is within bounds
				if (xCoords.get(x + 5) == null) { //five coords above has been removed
					return randomXHelp(); //must find new coordinate
				}
			} else {
				return randomXHelp();
			}
			return x;
  		} else { //index contains null
  			return randomXHelp(); //must find new coordinate
  		}
	}

	public static int randomYCoord() {
		int y = randomYHelp();
  		int returnThis = yCoords.get(y);
  
  		//remove that coordinate + the five above and below it
  		yCoords.remove(y);
  		for (int i = -5; i <= 5; i++) {
  			if ((y + i > 0) && (y + i < yCoords.size() - 1)) {
  				yCoords.remove(y + i);
  			}
  		}
  		
  		return returnThis;
	}

	private static int randomYHelp() {
		int y = RANDOM.nextInt(yCoords.size() - 1);
		if (yCoords.get(y) != null) { //index doesn't contain null
			if (y - 5 > 0) { //five below is within bounds
				if (yCoords.get(y - 5) == null) { //five coords below has been removed
					return randomYHelp(); //must find new coordinate
				}
			} else {
				return randomYHelp();
			}
			if (y + 5 < yCoords.size() - 1) { //five coords above is within bounds
				if (yCoords.get(y + 5) == null) { //five coords above has been removed
					return randomYHelp(); //must find new coordinate
				}
			} else {
				return randomYHelp();
			}
			return y;
  		} else { //index contains null
  			return randomYHelp(); //must find new coordinate
  		}
	}
}
