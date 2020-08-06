package space_trader_game.Menus;

import enums.NPCType;
import space_trader_game.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class EncounterMenu extends JFrame {

    //constructor
    private static Player player;
    private Ship ship;
    private Region currentRegion;
    private Region destination;
    private static String gameDifficulty;
    private static NPCType typeOfEncounter;

    //swing
    private JPanel banditPanel;
    private JPanel policePanel;
    private JPanel traderPanel;
    private JPanel traderPanel2;

    public EncounterMenu(NPCType type, Player player, Region currentRegion, Region destination, String gameDifficulty) {
        typeOfEncounter = type;
        this.player = player;
        this.ship = player.getShip();
        this.currentRegion = currentRegion;
        this.destination = destination;
        this.gameDifficulty = gameDifficulty;
//        this.travelable = true;
        if (typeOfEncounter == NPCType.BANDIT) {
            setFrameBandit();
        } else if (typeOfEncounter == NPCType.POLICE) {
            setFramePolice();
        } else if (typeOfEncounter == NPCType.TRADER) {
            setFrameTrader();
        }
    }

    /**
     * probability based on difficulty level (which affects the type of NPC encountered)
     * and a randomness component
     *
     * @return the NPCType of the encounter
     *         null if no encounter occurs and the player can travel safely
     */
    public static NPCType getTypeOfEncounter(Player player, String gameDifficulty) {
        Random rand = new Random();
        int difficulty;
        if (gameDifficulty.equals("Easy")) {
            difficulty = 1;
        } else if (gameDifficulty.equals("Medium")) {
            difficulty = 2;
        } else {
            difficulty = 3;
        }
        int randMultiplier = rand.nextInt(21) + 10; //a number between 10 and 30
        System.out.println("diff: " + difficulty);
        System.out.println("rand: " + randMultiplier);
        double probability = (difficulty * randMultiplier) / 100.0;
        System.out.println("prob: " + probability);
        if (probability > .5) {
            if (player.getInventory().size() > 0) {
                if ((player.getInventory().size() >= 2)
                        || (player.getInventory().get(0).getQuantity() > 1)) {
                    //need at least two items for police to confiscate
                    int enc = rand.nextInt(3) + 1; //number 1, 2, or 3
                    System.out.println("enc2: " + enc);
                    if (enc == 1) {
                        EncounterMenu.setTypeOfEncounter(NPCType.BANDIT);
                    } else if (enc == 2) {
                        EncounterMenu.setTypeOfEncounter(NPCType.POLICE);
                    } else {
                        EncounterMenu.setTypeOfEncounter(NPCType.TRADER);
                    }
                    return typeOfEncounter;
                }
            }
            int enc = rand.nextInt(2) + 1; //number 1 or 2
            System.out.println("enc1: " + enc);
            if (enc == 1) {
                EncounterMenu.setTypeOfEncounter(NPCType.BANDIT);
            } else if (enc == 2) {
                EncounterMenu.setTypeOfEncounter(NPCType.TRADER);
            }
            return typeOfEncounter;
        }
        return null;
    }

    /**
     * menu for bandit encounter
     */
    private void setFrameBandit() {
        this.banditPanel = new JPanel();

        this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        NPCBandit loki = new NPCBandit(player);
        int demand = loki.getDemandCredit();

        JLabel stem = new JLabel("Yikes!! A trickster named Loki has stopped you in your path!");
        JLabel stem2 = new JLabel("He demands " + demand + " credits... or else!");
        JLabel stem3 = new JLabel("Note: You currently have " + player.getCredits() + " credits.");
        JLabel stem4 = new JLabel("You can choose to:");

        JButton payButton = new JButton("Pay Loki and continue to " + destination.getName());
        JButton runButton = new JButton("Attempt to flee from Loki and return to " + currentRegion.getName());
        JButton fightButton = new JButton("Attempt to fight Loki and continue to " + destination.getName());

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                boolean deadShip = false;
                if (player.getCredits() < demand) {
                    if (player.getInventory().size() == 0) {
                        int damage = loki.damageShip();
                        deadShip = damage > ship.getCurrentHealth();
                        if (deadShip) {
                            ship.setCurrentHealth(0);
                            JOptionPane.showMessageDialog(frame,
                                    "You don't have enough credits and items in your inventory to steal,\n"
                                            + "so Loki attacks your ship instead!\n"
                                            + "Your ship lost all points in health.\n"
                                            + "As a result, you must now return to "
                                            + currentRegion.getName() + ".");

                        } else {
                            ship.setCurrentHealth(ship.getCurrentHealth() - damage);
                            JOptionPane.showMessageDialog(frame,
                                    "You don't have enough credits or items in your inventory to steal, "
                                            + "so Loki attacks your ship instead!\n"
                                            + "Your ship lost " + damage + " points in health.\n"
                                            + "Your ship now has " + ship.getCurrentHealth()
                                            + " points in health.\n"
                                            + "You will now continue on to "
                                            + destination.getName() + ".");
                        }
                    } else {
                        player.setInventory(null);
                        player.setCredits(0);
                        ship.setCurrentCargoSpace(ship.getCargoSpace());
                        JOptionPane.showMessageDialog(frame,
                                "You don't have enough credits to steal, "
                                        + "so Loki steals from you as well!\n"
                                        + "You have lost all your credits and\n"
                                        + "all items in your cargo hold.\n"
                                        + "But Loki has been merciful and allowed you to pass.\n"
                                        + "You will now continue on to "
                                        + destination.getName() + ".");
                    }
                } else {
                    player.setCredits(player.getCredits() - demand);
                    JOptionPane.showMessageDialog(frame,
                            "You have agreed to pay Loki, "
                                    + "so you have lost " + demand + " credits.\n"
                                    + "You now have " + player.getCredits() + " credits.\n"
                                    + "However, Loki allows you pass safely.\n"
                                    + "You will now continue on to "
                                    + destination.getName() + ".");
                }
                setVisible(false);
                dispose();
                if (deadShip) { //go back to current Region
                    new Game(gameDifficulty, player, ship, currentRegion);
                } else { //continue to destination
                    new Game(gameDifficulty, player, ship, destination);
                }
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                if (loki.runnable()) {
                    JOptionPane.showMessageDialog(frame,
                            "You have successfully fled from Loki!\n"
                                    + "Your ship has retained its current health,\n"
                                    + "and you have not lost any credits or items.\n"
                                    + "You will now safely return to "
                                    + currentRegion.getName() + ".");

                    setVisible(false);
                    dispose();
                    new Game(gameDifficulty, player, ship, currentRegion);
                } else {
                    int damage = loki.damageShip();
                    String report = "You have failed to escape Loki!\n";

                    //lose credits
                    if (player.getCredits() > demand) {
                        player.setCredits(player.getCredits() - demand);
                        report += ("As a result, Loki has stolen his "
                                + "original demand for " + demand + " credits.\n"
                                + "You now have " + player.getCredits() + " credits.\n");
                    } else {
                        report += ("As a result, Loki has stolen his original demand for "
                                + demand + " credits for attempted evasion,\nbut you only had "
                                + player.getCredits() + " credits.\n"
                                + "You now have 0 credits.\n");
                        player.setCredits(0);
                    }

                    //lose health
                    boolean deadShip = (damage > ship.getCurrentHealth());
                    if (deadShip) {
                        ship.setCurrentHealth(0);
                        report += ("Lastly, your ship lost all of its health in the attack,\n"
                                + "and you must return to " + currentRegion.getName() + ".");
                    } else {
                        ship.setCurrentHealth(ship.getCurrentHealth() - damage);
                        report += ("Lastly, your ship lost "
                                + damage + " points in health when Loki attacked.\n"
                                + "Your ship now has " + ship.getCurrentHealth()
                                + " points in health.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                    }

                    //report what happened
                    JOptionPane.showMessageDialog(frame, report);
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    if (deadShip) { //go back to current Region
                        new Game(gameDifficulty, player, ship, currentRegion);
                    } else { //continue to destination
                        new Game(gameDifficulty, player, ship, destination);
                    }
                }
            }
        });

        fightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                if (loki.fightable()) {
                    player.setCredits(player.getCredits() + demand);
                    JOptionPane.showMessageDialog(frame,
                            "You have successfully fought off Loki!\n"
                                    + "As an act of karma, you stole " + demand
                                    + " credits from him! Haha!\n"
                                    + "You now have " + player.getCredits() + " credits.\n"
                                    + "You will now continue with your full "
                                    + "health and inventory to "
                                    + destination.getName() + ".");
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    new Game(gameDifficulty, player, ship, destination);
                } else {
                    int damage = loki.damageShip();
                    String report = "You have failed to fight off Loki.\n";

                    //lose credits
                    report += "He has mercilessly stolen all of your credits.\n";
                    player.setCredits(0);

                    //lose health
                    boolean deadShip = (damage > ship.getCurrentHealth());
                    if (deadShip) {
                        ship.setCurrentHealth(0);
                        report += ("Your ship also lost all of its health,\n"
                                + "and you must return to " + currentRegion.getName() + ".");
                    } else {
                        ship.setCurrentHealth(ship.getCurrentHealth() - damage);
                        report += ("Your ship also lost "
                                + damage + " points in health.\n"
                                + "Your ship now has " + ship.getCurrentHealth()
                                + " points in health.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                    }

                    //report what happened
                    JOptionPane.showMessageDialog(frame, report);
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    if (deadShip) {
                        new Game(gameDifficulty, player, ship, currentRegion);
                    } else {
                        new Game(gameDifficulty, player, ship, destination);
                    }
                }
                setVisible(false);
                dispose();
            }
        });
        this.add(stem);
        this.add(stem2);
        this.add(stem3);
        this.add(stem4);
        this.add(payButton);
        this.add(runButton);
        this.add(fightButton);
    }

    /**
     * menu for police encounter
     */
    private void setFramePolice() {
        this.policePanel = new JPanel();

        this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        NPCPolice novaCorps = new NPCPolice(player);
        Item stolenItem1 = novaCorps.getStolenItem();
        Item stolenItem2 = novaCorps.getStolenItem();

        JLabel stem = new JLabel("The intergalactic police force, Nova Corps, has met you in your path!");
        JLabel stem2 = new JLabel("A Corpsman says, 'Civilian, stop! You are " + player.getName() + ", are you not?'");
        JLabel stem3 = new JLabel("'We suspect you have stolen things that are not rightfully yours.'");
        JLabel stem4 = new JLabel("'You must hand these items over immediately.'");
        JLabel stolenItem = new JLabel("     - " + stolenItem1.getName());
        JLabel stolenItemCont = new JLabel("     - " + stolenItem2.getName());

        JLabel options = new JLabel("You can choose to:");

        JRadioButton forfeit = new JRadioButton("Forfeit the allegedly stolen items to the Nova Corps.");
        JRadioButton flee = new JRadioButton("Attempt to flee the Nova Corps and return to " + currentRegion.getName() + ".");
        JRadioButton fight = new JRadioButton("Attempt to fight the Nova Corps and continue to " + destination.getName() + ".");

        forfeit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                //report what happened
                JOptionPane.showMessageDialog(frame,
                        "You have agreed to forfeit the items to Nova Corps.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                //update inventory and cargo space
                if (stolenItem1.getQuantity() > 1) {
                    stolenItem1.setQuantity(stolenItem1.getQuantity() - 1);
                } else {
                    player.getInventory().remove(stolenItem1);
                }
                if (stolenItem2.getQuantity() > 1) {
                    stolenItem2.setQuantity(stolenItem2.getQuantity() - 1);
                } else {
                    player.getInventory().remove(stolenItem2);
                }
                ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() + 2);
                //return to Game menu in desired region
                setVisible(false);
                dispose();
                new Game(gameDifficulty, player, ship, destination);
            }
        });

        flee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (novaCorps.areFled()) {
                    //success
                    JFrame frame = new JFrame();
                    //report what happened
                    JOptionPane.showMessageDialog(frame,
                            "You have successfully escaped the Nova Corps!\n"
                                    + "You will now return to safety at "
                                    + currentRegion.getName() + ".");
                    //inventory and cargo don't change
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    new Game(gameDifficulty, player, ship, currentRegion);
                } else {
                    //fail
                    JFrame frame = new JFrame();
                    int fine = novaCorps.assessFine();
                    int damage = novaCorps.damageShip();
                    String report = "You have failed to escape the Nova Corps.\n";

                    //lose credits
                    if (player.getCredits() > fine) {
                        player.setCredits(player.getCredits() - fine);
                        report += ("As a result, the Corpsman has assessed a fee of "
                                + fine + " credits for attempted evasion.\n"
                                + "You now have " + player.getCredits() + " credits.\n");
                    } else {
                        report += ("As a result, the Corpsman has assessed a fee of "
                                + fine + " credits for attempted evasion,\nbut you only had "
                                + player.getCredits() + " credits.\n"
                                + "You now have 0 credits.\n");
                        player.setCredits(0);
                    }

                    //update inventory and cargo
                    report += ("The Corpsman also confiscated the stolen items, "
                            + stolenItem1.getName() + " and " + stolenItem2.getName() + ".\n");
                    if (stolenItem1.getQuantity() > 1) {
                        stolenItem1.setQuantity(stolenItem1.getQuantity() - 1);
                    } else {
                        player.getInventory().remove(stolenItem1);
                    }
                    if (stolenItem2.getQuantity() > 1) {
                        stolenItem2.setQuantity(stolenItem2.getQuantity() - 1);
                    } else {
                        player.getInventory().remove(stolenItem2);
                    }
                    ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() + 2);

                    //lose health
                    boolean deadShip = (damage > ship.getCurrentHealth());
                    if (deadShip) {
                        ship.setCurrentHealth(0);
                        report += ("Lastly, your ship lost all of its health,\n"
                                + "and you must return to " + currentRegion.getName() + ".");
                    } else {
                        ship.setCurrentHealth(ship.getCurrentHealth() - damage);
                        report += ("Lastly, your ship lost "
                                + damage + " points in health.\n"
                                + "Your ship now has " + ship.getCurrentHealth()
                                + " points in health.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                    }

                    //report what happened
                    JOptionPane.showMessageDialog(frame, report);
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    if (deadShip) { //go back to current Region
                        new Game(gameDifficulty, player, ship, currentRegion);
                    } else { //continue to destination
                        new Game(gameDifficulty, player, ship, destination);
                    }
                }
            }
        });

        fight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                if (novaCorps.loseFight()) {
                    //success
                    //report what happened
                    JOptionPane.showMessageDialog(frame,
                            "You have successfully fought the Nova Corps!\n"
                                    + "You will now continue with your full inventory to "
                                    + destination.getName() + ".");
                    //inventory doesn't change
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    new Game(gameDifficulty, player, ship, destination);
                } else {
                    //fail
                    int damage = novaCorps.damageShip();
                    String report = "You have failed to fight off the Nova Corps.";

                    //update inventory and cargo
                    report += ("The Corpsman confiscated the stolen items, "
                            + stolenItem1.getName() + " and " + stolenItem2.getName() + ".\n");
                    if (stolenItem1.getQuantity() > 1) {
                        stolenItem1.setQuantity(stolenItem1.getQuantity() - 1);
                    } else {
                        player.getInventory().remove(stolenItem1);
                    }
                    if (stolenItem2.getQuantity() > 1) {
                        stolenItem2.setQuantity(stolenItem2.getQuantity() - 1);
                    } else {
                        player.getInventory().remove(stolenItem2);
                    }
                    ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() + 2);

                    //lose health
                    boolean deadShip = (damage > ship.getCurrentHealth());
                    if (deadShip) {
                        ship.setCurrentHealth(0);
                        report += ("Lastly, your ship lost all of its health,\n"
                                + "and you must return to " + currentRegion.getName() + ".");
                    } else {
                        ship.setCurrentHealth(ship.getCurrentHealth() - damage);
                        report += ("Lastly, your ship lost "
                                + damage + " points in health.\n"
                                + "Your ship now has " + ship.getCurrentHealth()
                                + " points in health.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                    }
                    //report what happened
                    JOptionPane.showMessageDialog(frame, report);
                    //return to Game menu in desired region
                    setVisible(false);
                    dispose();
                    if (deadShip) {
                        new Game(gameDifficulty, player, ship, currentRegion);
                    } else {
                        new Game(gameDifficulty, player, ship, destination);
                    }
                }
            }
        });

        this.add(stem);
        this.add(stem2);
        this.add(stem3);
        this.add(stem4);
        this.add(stolenItem);
        this.add(stolenItemCont);
        this.add(options);
        this.add(forfeit);
        this.add(flee);
        this.add(fight);
    }

    /**
     * menu for trader encounter
     */
    private void setFrameTrader() {
        this.traderPanel = new JPanel();

        this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        NPCTrader yondu = new NPCTrader(player, currentRegion, destination);

        JLabel instructions = new JLabel("A trader named Yondu has met you in your path!");
        JLabel greeting = new JLabel("Yondu says, 'Howdy, there, " + player.getName() + "! "
                + " What say you to doin' a little tradin'?'");

        JLabel tradeStem = new JLabel("Yondu is offering the following three items: ");
        //item 1
        JLabel tradeProposal1 = new JLabel("         - " + yondu.getItem1().getName()
                + " for " + yondu.getPrice1() + " credits");
        //item 2
        JLabel tradeProposal2 = new JLabel("         - " + yondu.getItem2().getName()
                + " for " + yondu.getPrice2() + " credits");
        //item 3
        JLabel tradeProposal3 = new JLabel("         - " + yondu.getItem3().getName()
                + " for " + yondu.getPrice3() + " credits");

        JLabel total = new JLabel("That would cost a total of " + yondu.getTotalPrice() + " credits.");

        JLabel availableCredits = new JLabel("Note: You currently have " + player.getCredits() + " credits.");

        JLabel options = new JLabel("You can choose to:");

        JRadioButton buyItems = new JRadioButton("Buy Yondu's items for " + yondu.getTotalPrice() + " credits.");
        JRadioButton ignore = new JRadioButton("Decline Yondu and continue travelling.");
        JRadioButton rob = new JRadioButton("Attempt to rob Yondu of one of his items.");
        JRadioButton negotiate = new JRadioButton("Attempt to negotiate with Yondu for a better price.");

        //BUY YONDU'S ITEMS
        buyItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (player.getCredits() < yondu.getTotalPrice()) {
                        throw new Exception();
                    } else {
                        JFrame frame = new JFrame();
                        //report what happened
                        JOptionPane.showMessageDialog(frame,
                                "You have purchased " + yondu.getItem1().getName()
                                        + ", " + yondu.getItem2().getName()
                                        + ", and " + yondu.getItem3().getName()
                                        + " for " + yondu.getTotalPrice() + " credits.\n"
                                        + "You will now continue on to "
                                        + destination.getName() + ".");
                        //pay money
                        player.setCredits(player.getCredits() - yondu.getTotalPrice());
                        //add inventory
                        player.getInventory().add(yondu.getItem1());
                        player.getInventory().add(yondu.getItem2());
                        player.getInventory().add(yondu.getItem3());
                        //subtract cargo space
                        ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() - 3);
                        //return to Game menu in desired region
                        setVisible(false);
                        dispose();
                        new Game(gameDifficulty, player, ship, destination);
                    }
                } catch (Exception ex) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You don't have enough credits to make this trade.");
                    setFrameTrader();
                }
            }
        });

        //DECLINE TO TRADE WITH YONDU AND CONTINUE
        ignore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                //report what happened
                JOptionPane.showMessageDialog(frame,
                        "You have declined to trade with Yondu.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                //return to Game menu in desired region
                setVisible(false);
                dispose();
                new Game(gameDifficulty, player, ship, destination);
            }
        });

        //ATTEMPT TO ROB YONDU
        rob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (yondu.isRobbed()) { //SUCCESS
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame,
                                "You have successfully robbed Yondu. Congrats!\n"
                                        + "You have stolen " + yondu.getItem1().getName()
                                        + " and added it to your inventory.\n"
                                        + "You will now continue on to "
                                        + destination.getName() + ".");
                        //add inventory
                        player.getInventory().add(yondu.getItem1());
                        //subtract cargo space
                        ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() - 1);
                        //return to Game menu in desired region
                        setVisible(false);
                        dispose();
                        new Game(gameDifficulty, player, ship, destination);
                    } else { //FAIL
                        JFrame frame = new JFrame();
                        int damage = yondu.damageShip();
                        if (damage > ship.getCurrentHealth()) {
                            throw new Exception();
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "You have failed to rob Yondu.\n"
                                            + "As a result, your ship has suffered "
                                            + damage + " points in health.\n"
                                            + "Your ship now has " + ship.getCurrentHealth()
                                            + " in health.\nYou will now continue on to "
                                            + destination.getName() + ".");
                            //lose health
                            ship.setCurrentHealth(ship.getCurrentHealth() - damage);
                            //return to Game menu in desired region
                            setVisible(false);
                            dispose();
                            new Game(gameDifficulty, player, ship, destination);
                        }
                    }
                } catch (Exception dead)  {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You have failed to rob Yondu.\n"
                                    + "As a result, your ship has lost all its health!\n"
                                    + "You will now return to "
                                    + currentRegion.getName() + ".");
                    //lose all health
                    ship.setCurrentHealth(0);
                    //return to original location
                    setVisible(false);
                    dispose();
                    new Game(gameDifficulty, player, ship, currentRegion);
                }
            }
        });

        negotiate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (yondu.willNegotiate()) { //SUCCESS
                    setVisible(false);
                    dispose();
                    remove(instructions);
                    remove(tradeStem);
                    remove(tradeProposal1);
                    remove(tradeProposal2);
                    remove(tradeProposal3);
                    remove(total);
                    remove(availableCredits);
                    remove(options);
                    remove(buyItems);
                    remove(ignore);
                    remove(rob);
                    remove(negotiate);
                    setFrameTraderNegotiation(yondu, true);
                } else { //FAILURE
                    setVisible(false);
                    dispose();
                    remove(instructions);
                    remove(tradeStem);
                    remove(tradeProposal1);
                    remove(tradeProposal2);
                    remove(tradeProposal3);
                    remove(total);
                    remove(availableCredits);
                    remove(options);
                    remove(buyItems);
                    remove(ignore);
                    remove(rob);
                    remove(negotiate);
                    setFrameTraderNegotiation(yondu, false);
                }
            }
        });

        this.add(instructions);
        this.add(greeting);
        this.add(tradeStem);
        this.add(tradeProposal1);
        this.add(tradeProposal2);
        this.add(tradeProposal3);
        this.add(total);
        this.add(availableCredits);
        this.add(options);

        this.add(buyItems);
        this.add(ignore);
        this.add(rob);
        this.add(negotiate);

    }

    private void setFrameTraderNegotiation(NPCTrader yondu, boolean negotiate) {
        //same thing as other frame but with new prices and without negotiate option
        this.traderPanel2 = new JPanel();

        this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JLabel instructions;
        JLabel continued;
        JLabel tradeStem;
        JLabel tradeProposal1;
        JLabel tradeProposal2;
        JLabel tradeProposal3;
        JLabel total;

        if (negotiate) { //DID NEGOTIATE
            instructions = new JLabel("Yondu says, 'Well, gee, I reckon we can do a lil somethin' about this.'\n");
            continued = new JLabel("He has agreed to negotiate and has lowered all his prices!");
            tradeStem = new JLabel("Yondu's new offer is:");
            //item 1
            int negotiatedPrice1 = yondu.negotiatedPrice(yondu.getPrice1());
            tradeProposal1 = new JLabel("         - " + yondu.getItem1().getName()
                    + " for " + negotiatedPrice1 + " credits");
            //item 2
            int negotiatedPrice2 = yondu.negotiatedPrice(yondu.getPrice2());
            tradeProposal2 = new JLabel("         - " + yondu.getItem2().getName()
                    + " for " + negotiatedPrice2 + " credits");
            //item 3
            int negotiatedPrice3 = yondu.negotiatedPrice(yondu.getPrice3());
            tradeProposal3 = new JLabel("         - " + yondu.getItem3().getName()
                    + " for " + negotiatedPrice3 + " credits");

            int totalPrice = negotiatedPrice1 + negotiatedPrice2 + negotiatedPrice3;
            total = new JLabel("That would cost a total of " + totalPrice + " credits.");

        } else { //DID NOT NEGOTIATE, IS UPSET
            instructions = new JLabel("Yondu has refused to negotiate.\n");
            continued = new JLabel("He glares at you with the intensity of a thousand suns.");
            tradeStem = new JLabel("He has now increased all of his prices. Yondu's new offer is: ");
            //item 1
            int increasedPrice1 = yondu.increasedPrice(yondu.getPrice1());
            tradeProposal1 = new JLabel("         - " + yondu.getItem1().getName()
                    + " for " + increasedPrice1 + " credits");
            //item 2
            int increasedPrice2 = yondu.increasedPrice(yondu.getPrice2());
            tradeProposal2 = new JLabel("         - " + yondu.getItem2().getName()
                    + " for " + increasedPrice2 + " credits");
            //item 3
            int increasedPrice3 = yondu.increasedPrice(yondu.getPrice3());
            tradeProposal3 = new JLabel("         - " + yondu.getItem3().getName()
                    + " for " + increasedPrice3 + " credits");

            int totalPrice = increasedPrice1 + increasedPrice2 + increasedPrice3;
            total = new JLabel("That would cost a total of " + totalPrice + " credits.");
        }

        JLabel availableCredits = new JLabel("Note: You currently have " + player.getCredits() + " credits.");

        JLabel options = new JLabel("You can choose to:");

        JRadioButton buyItems = new JRadioButton("Buy Yondu's items for " + yondu.getTotalPrice() + " credits.");
        JRadioButton ignore = new JRadioButton("Decline Yondu and continue travelling.");
        JRadioButton rob = new JRadioButton("Attempt to rob Yondu of one of his items.");

        //BUY YONDU'S ITEMS
        buyItems.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (player.getCredits() < yondu.getTotalPrice()) {
                        throw new Exception();
                    } else {
                        JFrame frame = new JFrame();
                        //report what happened
                        JOptionPane.showMessageDialog(frame,
                                "You have purchased " + yondu.getItem1().getName()
                                        + ", " + yondu.getItem2().getName()
                                        + ", and " + yondu.getItem3().getName()
                                        + " for " + yondu.getTotalPrice() + " credits.\n"
                                        + "You will now continue on to "
                                        + destination.getName() + ".");
                        //pay money
                        player.setCredits(player.getCredits() - yondu.getTotalPrice());
                        //add inventory
                        player.getInventory().add(yondu.getItem1());
                        player.getInventory().add(yondu.getItem2());
                        player.getInventory().add(yondu.getItem3());
                        //subtract cargo space
                        ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() - 3);
                        //return to Game menu in desired region
                        setVisible(false);
                        dispose();
                        new Game(gameDifficulty, player, ship, destination);
                    }
                } catch (Exception ex) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You don't have enough credits to make this trade.");
                    setFrameTrader();
                }
            }
        });

        //DECLINE TO TRADE WITH YONDU AND CONTINUE
        ignore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                //report what happened
                JOptionPane.showMessageDialog(frame,
                        "You have declined to trade with Yondu.\n"
                                + "You will now continue on to "
                                + destination.getName() + ".");
                //return to Game menu in desired region
                setVisible(false);
                dispose();
                new Game(gameDifficulty, player, ship, destination);
            }
        });

        //ATTEMPT TO ROB YONDU
        rob.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (yondu.isRobbed()) { //SUCCESS
                        JFrame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame,
                                "You have successfully robbed Yondu. Congrats!\n"
                                        + "You have stolen " + yondu.getItem1().getName()
                                        + " and added it to your inventory.\n"
                                        + "You will now continue on to "
                                        + destination.getName() + ".");
                        //add inventory
                        player.getInventory().add(yondu.getItem1());
                        //subtract cargo space
                        ship.setCurrentCargoSpace(ship.getCurrentCargoSpace() - 1);
                        //return to Game menu in desired region
                        setVisible(false);
                        dispose();
                        new Game(gameDifficulty, player, ship, destination);
                    } else { //FAIL
                        JFrame frame = new JFrame();
                        if (yondu.damageShip() > ship.getCurrentHealth()) {
                            throw new Exception();
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "You have failed to rob Yondu.\n"
                                            + "As a result, your ship has suffered "
                                            + yondu.damageShip()
                                            + " points in health.\n"
                                            + "You will now continue on to "
                                            + destination.getName() + ".");
                            //lose health
                            ship.setCurrentHealth(ship.getCurrentHealth() - yondu.damageShip());
                            //return to Game menu in desired region
                            setVisible(false);
                            dispose();
                            new Game(gameDifficulty, player, ship, destination);
                        }
                    }
                } catch (Exception dead)  {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You have failed to rob Yondu.\n"
                                    + "As a result, your ship has lost all its health!\n"
                                    + "You will now return to "
                                    + currentRegion.getName() + ".");
                    //lose all health
                    ship.setCurrentHealth(0);
                    //return to original location
                    setVisible(false);
                    dispose();
                    new Game(gameDifficulty, player, ship, currentRegion);
                }
            }
        });

        this.add(instructions);
        this.add(continued);
        this.add(tradeStem);
        this.add(tradeProposal1);
        this.add(tradeProposal2);
        this.add(tradeProposal3);
        this.add(total);
        this.add(availableCredits);
        this.add(options);

        this.add(buyItems);
        this.add(ignore);
        this.add(rob);
    }

    public static void setTypeOfEncounter(NPCType type) {
        typeOfEncounter = type;
    }
}
