package space_trader_game.Menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import interfaces.Menu;
import space_trader_game.Constants;
import space_trader_game.Player;



public class EndGameMenu extends JFrame implements Menu {
    private Player player;

    @Override
    public void setFrame() {
        //display window
        this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        JTextArea welcomeText = new JTextArea("Welcome to the End Game.");
        JTextArea bodyText = new JTextArea("You have the opportunity to buy the most \n"
                + "powerful item in the universe \n - the Infinity Gauntlet (also kno "
                + "wn as " + this.player.getName() +"'s Universe) for 5000 credits.");
        JTextArea acceptText = new JTextArea("Do you accept?");
        
        JButton acceptButton = new JButton("Buy Infinity Gauntlet");
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getCredits() >= 0) {
                    new WinMenu();
                    setVisible(false);
                    dispose();
                } else {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You don't have enough credits");
                    setFrame();
                }
            }
        });
        
        JButton exitButton = new JButton("Click here to return");
        exitButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                dispose();
                System.out.println(player.getGameDifficulty() + "" + player + "" + player.getShip() + "" + player.getRegion());
                new Game(player.getGameDifficulty(), player, player.getShip(), player.getRegion());
            }
        });
        
        this.add(welcomeText);
        this.add(bodyText);
        this.add(acceptText);
        this.add(acceptButton);
        this.add(exitButton);
    }
    
    public EndGameMenu(Player player) {
        super("Welcome to the End Game");
        this.player = player;
        this.setFrame();
    }
}
