package services;

import space_trader_game.Player;

public class LoseService {
    
    public static boolean determineLoss(Player player) {
        return player.getShip().getCurrentHealth() <= 0;
    }
    
}
