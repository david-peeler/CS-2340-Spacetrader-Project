package space_trader_game.Menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import interfaces.Menu;
import space_trader_game.Constants;
import space_trader_game.Player;

public class ShipManagerMenu extends JFrame implements Menu {
    private Player player;
    
    @Override
    public void setFrame() {
        //display window
        this.setSize(Constants.MENU_WIDTH / 2, Constants.MENU_HEIGHT / 2);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel("Ship Name: " + player.getShip().getName());
        JLabel moneyLabel = new JLabel("Avaialable Credits: " + player.getCredits());
        
        int refillCost = (player.getShip().getFuelCapacity() - player.getShip().getCurrentFuel());
        JLabel gasLabel = new JLabel("Gas level: " + player.getShip().getCurrentFuel()
                + "/" + player.getShip().getFuelCapacity());
        JLabel refillLabel = new JLabel("Would you like to refill your tank?");
        JButton refillButton = new JButton("Click here to refill for "
                + refillCost + " Credits");
        refillButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (player.getCredits() < refillCost) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You don't have enough credits");
                    setFrame(); 
                } else {
                    player.setCredits(player.getCredits() - player.getShip().getFuelCapacity()
                            + player.getShip().getCurrentFuel());
                    player.getShip().setCurrentFuel(player.getShip().getFuelCapacity());
                    gasLabel.setText("Gas level: " + player.getShip().getCurrentFuel()
                            + "/" + player.getShip().getFuelCapacity());
                    refillButton.setText("Click here to refill for "
                            + (player.getShip().getFuelCapacity()
                                    - player.getShip().getCurrentFuel()) + " Credits");
                    moneyLabel.setText("Avaialable Credits: " + player.getCredits());
                }
            }
        });
        
        JLabel shipHealthLabel = new JLabel("Ship Health: " + player.getShip().getCurrentHealth()
                + "/" + player.getShip().getHealth());
        
        int repairCost = (2 * (16 - player.getEngineer()) + (player.getShip().getHealth()
                - player.getShip().getCurrentHealth()));
        
        JLabel repairLabel = new JLabel("Would you like to repair your ship?");
        JButton repairButton = new JButton("Click to repair your ship for " + 
                    + repairCost + " Credits?");
        repairButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (player.getCredits() < repairCost) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame,
                            "You don't have enough credits");
                    setFrame();
                } else {
                    int repairCost = (2 * (16 - player.getEngineer()) + (player.getShip().getFuelCapacity()
                            - player.getShip().getCurrentFuel()));
                    player.setCredits(player.getCredits() - repairCost);
                    player.getShip().setCurrentHealth(player.getShip().getHealth());
                    shipHealthLabel.setText("Ship Health: " + player.getShip().getCurrentHealth()
                + "/" + player.getShip().getHealth());
                    repairButton.setText("Click to repair your ship for " + repairCost + " Credits?");
                    moneyLabel.setText("Avaialable Credits: " + player.getCredits());
                }
            }
        });
        
        JButton exitButton = new JButton("Click here to exit ship manager");
        exitButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                dispose();
            }
        });
        
        this.add(nameLabel);
        this.add(moneyLabel);
        this.add(gasLabel);
        this.add(refillLabel);
        this.add(refillButton);
        this.add(shipHealthLabel);
        this.add(repairLabel);
        this.add(repairButton);
        this.add(exitButton);
    }
    
    public ShipManagerMenu(Player player) {
        super("Manage your ship!");
        this.player = player;
        this.setFrame();
    }
}
