package space_trader_game.Menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import interfaces.Menu;
import space_trader_game.Constants;
import space_trader_game.Player;

public class ShipMenu extends JFrame implements Menu {
	private Player player;

	@Override
	public void setFrame() {
		//display window
		this.setSize(Constants.MENU_WIDTH / 2, Constants.MENU_HEIGHT / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		
		//Text areas for: name, cargo space, fuel, health
		//Button to finish & go back to main
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		JLabel nameLabel = new JLabel("Ship Name: " + player.getShip().getName());
		JTextArea nameText = new JTextArea("Enter new ship name!");
		JLabel note = new JLabel("Name will update when Ship Manager is exited.");
		JButton changeShipNameButton = new JButton("Confirm New Ship Name");
		changeShipNameButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	player.getShip().setName(nameText.getText());
            }
        });

		JTextArea cargoSpaceText = new JTextArea("Cargo Space: "
				+ this.player.getShip().getCurrentCargoSpace()
				+ "/" + this.player.getShip().getCargoSpace());
		cargoSpaceText.setEditable(false);
		JTextArea fuelText = new JTextArea("Fuel: "
				+ this.player.getShip().getCurrentFuel()
				+ "/" + this.player.getShip().getFuelCapacity());
		fuelText.setEditable(false);
		JTextArea healthText = new JTextArea("Health: "
				+ this.player.getShip().getCurrentHealth()
				+ "/" + this.player.getShip().getHealth());
		healthText.setEditable(false);
		JTextArea shipTypeText = new JTextArea("Ship Type: "
				+ this.player.getShip().getType().getTypeName());
		shipTypeText.setEditable(false);
		
		JButton exitButton = new JButton("Click here to exit ship manager");
		exitButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	setVisible(false);
            	dispose();
            }
        });

		this.add(nameLabel);
		this.add(nameText);
		this.add(note);
		this.add(changeShipNameButton);
		this.add(cargoSpaceText);
		this.add(fuelText);
		this.add(healthText);
		this.add(shipTypeText);
		this.add(exitButton);
	}
	
	public ShipMenu(Player player) {
		super("Manage Your Ship");
		this.player = player;
		this.setFrame();
	}
}
