package enums;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum RegionName {
  	ASGARD("Asgard"), //Thor
  	WAKANDA("Wakanda"), //Black Panther
  	SOKOVIA("Sokovia"), //Capt. America Age of Ultron
  	NYC("New York City"), //everyone
  	XANDAR("Xandar"), //Guardians of the Galaxy
  	KNOWHERE("Knowhere"), //Guardians of the Galaxy
  	VORMIR("Vormir"), //oof
  	SHIELD_HQ("S.H.I.E.L.D. Headquarters"), //everyone
  	HYDRA_HQ("HYDRA Headquarters"), //Captain America
  	WASHINGTON_DC("Hala"); //Captain Marvel

  	private String name;
  	private static List<RegionName> list = new ArrayList<>(Arrays.asList(values()));
    private static final Random RANDOM = new Random();

  	private RegionName(String regionName) {
  		name = regionName;
  	}

  	public String getName() {
  		return name;
  	}

  	public static String randomRegionName()  {
  		int thisRand = RANDOM.nextInt(list.size());
  		String returnThis = list.get(thisRand).getName();
  		list.remove(thisRand);
  		return returnThis;
    }
}