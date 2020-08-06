package space_trader_game;

import interfaces.NPC;
import services.PriceCalculatorService;

import java.util.Random;

public class NPCTrader implements NPC {

    private Player player;
    private Region playerCurrentRegion;
    private Region playerDestination;

    //items and prices
    private Item item1;
    private Item item2;
    private Item item3;
    private int price1;
    private int price2;
    private int price3;
    private int totalPrice;

    private Random rand = new Random();

    public NPCTrader(Player player, Region playerCurrentRegion, Region playerDestination) {
        this.player = player;
        this.playerCurrentRegion = playerCurrentRegion;
        this.playerDestination = playerDestination;

        //items to sell
        item1 = Universe.randomItem();
        price1 = PriceCalculatorService.findBuyingPrice(item1, playerCurrentRegion, player);
        item2 = Universe.randomItem();
        price2 = PriceCalculatorService.findBuyingPrice(item2, playerCurrentRegion, player);
        item3 = Universe.randomItem();
        price3 = PriceCalculatorService.findBuyingPrice(item3, playerCurrentRegion, player);
        totalPrice = price1 + price2 + price3;
    }

    /**
     * calculates if Yondu can be robbed or not
     * fighter points are multiplied by a random number between 10 and 30,
     * then divided by 100
     *
     * @return boolean of success robbing
     */
    public boolean isRobbed() {
        float fighter = player.getFighter();
        int randMultiplier = rand.nextInt(21) + 10; //a number between 10 and 30
        float probability = (fighter * randMultiplier) / 100;
        return (probability > .5);
    }

    /**
     * minimum loss: 1/10 ship's current health
     * maximum loss: 5/10 (half) ship's current health
     * randomly generate number 1-5, and this is how much loss there is
     * (1/10, 2/10, 3/10, 4/10, or 5/10 of total health)
     */
    public int damageShip() {
        Ship ship = player.getShip();
        int randomDamage = rand.nextInt(5) + 1; //generates a number 1 to 5
        return (ship.getCurrentHealth() / 10) * randomDamage;
    }

    /**
     * calculates if Yondu will negotiate or not
     * done the same way as isRobbed()
     *
     * @return boolean of success negotiating
     */
    public boolean willNegotiate() {
        float merch = player.getMerchant();
        int randMultiplier = rand.nextInt(21) + 10; //a number between 10 and 30
        float probability = (merch * randMultiplier) / 100;
        return (probability > .5);
    }

    /**
     * reduces price by 10 to 30%
     */
    public int negotiatedPrice(int itemPrice) {
        int decrease = rand.nextInt(21) + 10; //generates a number 10 to 30
        decrease = decrease / 100;
        int amountOff = itemPrice * decrease;
        return itemPrice - amountOff;
    }

    /**
     * increases price by a random number from 10 to 30
     */
    public int increasedPrice(int itemPrice) {
        int increase = rand.nextInt(21) + 10; //generates a number 10 to 30
        return itemPrice + increase;
    }

    @Override
    public void interact(Player player) {

    }

    public Item getItem1() {
        return item1;
    }

    public Item getItem2() {
        return item2;
    }

    public Item getItem3() {
        return item3;
    }

    public int getPrice1() {
        return price1;
    }

    public int getPrice2() {
        return price2;
    }

    public int getPrice3() {
        return price3;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
