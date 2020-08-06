package space_trader_game;

import interfaces.NPC;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NPCPolice implements NPC {

    private Player player;
    private Random rand = new Random();
    private List<Item> inventoryCopy = new LinkedList<>();

    public NPCPolice(Player player) {
        this.player = player;
        for (Item each : player.getInventory()) {
            inventoryCopy.add(each);
        }
    }

    public Item getStolenItem() {
        if (player.getInventory().size() == 1) {
            //then quantity of one item must be > 1
            return player.getInventory().get(0);
        }
        int index = rand.nextInt(inventoryCopy.size());
        Item returnThis = inventoryCopy.get(index);
        inventoryCopy.remove(index);
        return returnThis;
    }

    /**
     * calculates if Nova Corps can be fled from or not
     * pilot points are multiplied by a random number between 10 and 30,
     * then divided by 100
     *
     * @return boolean of success fleeing
     */
    public boolean areFled() {
        float pilot = player.getPilot();
        int randMultiplier = rand.nextInt(21) + 10; //a number between 10 and 30
        float probability = (pilot * randMultiplier) / 100;
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
     * assesses a random fine for attempting to escape
     * @return a random number between 50 and 149
     */
    public int assessFine() {
        return rand.nextInt(100) + 50;
    }

    /**
     * calculates if Nova Corps will be fought off or not
     * done the same way as areFled()
     *
     * @return boolean of success fighting
     */
    public boolean loseFight() {
        float fighter = player.getFighter();
        int randMultiplier = rand.nextInt(21) + 10; //a number between 10 and 30
        float probability = (fighter * randMultiplier) / 100;
        return (probability > .5);
    }

    @Override
    public void interact(Player player) {

    }

}
