package space_trader_game.Menus;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import interfaces.Menu;
import space_trader_game.Constants;
import space_trader_game.Driver;

public class WinMenu extends JFrame implements Menu {

    @Override
    public void setFrame() {
        //set frame settings
        this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        
        JTextArea winText = new JTextArea("Congratulations on becoming "
                + "the most powerful being in the universe.");
        JButton playAgainButton = new JButton("Play again?");
        playAgainButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                dispose();
                try {
                    Driver.main(new String[0]);
                } catch (Exception e) {
                    //uh oh
                    e.printStackTrace();
                }
            }
        });
        
        JButton exitButton = new JButton("Click here to exit game.");
        exitButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        
        this.add(winText);
        this.add(playAgainButton);
        this.add(exitButton);
    }
    
    public WinMenu() {
        super("You win!");
        this.setFrame();
    }

}
