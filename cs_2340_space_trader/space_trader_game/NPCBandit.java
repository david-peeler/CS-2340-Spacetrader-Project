package space_trader_game;

import interfaces.NPC;

import java.util.Random;

public class NPCBandit implements NPC {

    private Random rand = new Random();
    private Player player;

    public NPCBandit(Player player) {
        this.player = player;
    }
    /**
     * calculates if Loki can be fled from or not
     * pilot points are multiplied by a random number between 10 and 30,
     * then divided by 100
     *
     * @return boolean of success fleeing
     */
    public boolean runnable() {
        float pilot = player.getPilot();
        int randMultiplier = rand.nextInt(21) + 10; //a number between 10 and 30
        float probability = (pilot * randMultiplier) / 100;
        return (probability > .5);
    }

    /**
     * calculates if Nova Corps will be fought off or not
     * done the same way as areFled()
     *
     * @return boolean of success fighting
     */
    public boolean fightable() {
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

    public int getDemandCredit() {
        return rand.nextInt(100) + 50;
    }

    @Override
    public void interact(Player player) {

    }

}
