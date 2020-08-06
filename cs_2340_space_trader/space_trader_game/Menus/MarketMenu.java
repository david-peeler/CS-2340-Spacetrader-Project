    package space_trader_game.Menus;

import services.PriceCalculatorService;
import space_trader_game.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import interfaces.Menu;

import static space_trader_game.Universe.getItemFromList;
import static space_trader_game.Universe.itemsList;

public class MarketMenu extends JFrame implements Menu {
	private Region currentRegion;
	private Player player;

	public Item itemToSell; //item selected from inventory to sell in market
	public Item itemToBuy; //item selected from market to buy

	public JComboBox<String> currentPlayerInventory;

	public MarketMenu(Region currentRegion, Player player) {
		this.currentRegion = currentRegion;
		this.player = player;
		this.setFrame();
	}

	@Override
	public void setFrame() {
		//display window
		this.setSize(Constants.MENU_WIDTH / 2, Constants.MENU_HEIGHT / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);

		//Textboxes for current region & current credits
		//Dropdown menus for current inventory, current region market
		//Buttons to buy or sell current selected item
		//Button to finish & go back to main
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		JTextArea currentRegionText = new JTextArea(currentRegion.getName() + ": " + currentRegion.getCoordinates() + "; Tech level: " + currentRegion.getTechLevel());
		currentRegionText.setEditable(false);

		JTextArea currentCreditText = new JTextArea("Current credits: " + player.getCredits());
		currentCreditText.setEditable(false);

		//LIST AND BUTTON TO SELL ITEMS

		JTextArea instruct = new JTextArea("Inventory items available to sell:");

		List<String> itemsListString = new ArrayList<>();
		List<String> items = new ArrayList<>();
		/**
		 * populating list with items in inventory that are allowed to be sold
		 * given this region's tech level
		 * then displays those items with the name and selling price
		 */
		for (Item each : player.getInventory()) {
			//converting Items into String representations for the dropdown menu
				String invItem = new String(each.getName() + " (" +each.getQuantity()
						+ ")" + " can sell for "
						+ PriceCalculatorService.findSellingPrice(each, currentRegion, player)
						+ " credits");
				itemsListString.add(invItem);
				items.add(each.getName());
		}
		String[] newArray = new String[items.size()]; //array holding objects
		String[] itemsArray = items.toArray(newArray);
		String[] arr = new String[itemsListString.size()]; //array holding objects
		String[] itemsListArray = itemsListString.toArray(arr);
		this.currentPlayerInventory = new JComboBox<>(itemsListArray);

		JButton sellButton = new JButton("Sell Selected Item");
		sellButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < itemsListArray.length; i++) {
					if (itemsListArray[i].equals(currentPlayerInventory.getSelectedItem())){
						itemToSell = Universe.getItemFromList(itemsArray[i]);
						break;
					}
				}
				//remove from player inventory
				//add selling credits to player credits
				player.setCredits(player.getCredits()
						+ PriceCalculatorService.findSellingPrice(itemToSell, currentRegion, player));
				player.inventoryRemove(itemToSell);
				player.getShip().setCurrentCargoSpace(player.getShip().getCurrentCargoSpace() + 1);
				new MarketMenu(currentRegion, player);
				setVisible(false);
				dispose();
			}
		});

		//LIST AND BUTTON TO BUY ITEMS

		JTextArea instruct2 = new JTextArea("Market items available to buy:");

		List<String> itemsListStringMarket = new ArrayList<>();
		List<String> itemsMarket = new ArrayList<>();
		/**
		 * populating second list with items in market that are allowed to be bought
		 * given this region's tech level
		 * then displays those items with the name and selling price
		 */
		for (Item each : itemsList) { //itemsList from Universe
			//converting Items into String representations for the dropdown menu
			if (each.getItemLevel() <= currentRegion.getRegionLevel()) {
				itemsListStringMarket.add(each.getName() + " can be bought for "
						+ PriceCalculatorService.findBuyingPrice(each, currentRegion, player)
						+ " credits");
				itemsMarket.add(each.getName());
			}
		}
		String[] newArrayMarket = new String[itemsMarket.size()]; //array holding objects
		String[] itemsArrayMarket = itemsMarket.toArray(newArrayMarket);
		String[] arr2 = new String[itemsListStringMarket.size()]; //array holding objects
		String[] itemsArray2 = itemsListStringMarket.toArray(arr2);
		JComboBox<String> currentMarket = new JComboBox<>(itemsArray2);

		JButton buyButton = new JButton("Buy Selected Item");
		buyButton.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					for (int i =0; i < itemsArray2.length; i++) {
						if (itemsArray2[i].equals(currentMarket.getSelectedItem())){
							itemToBuy = Universe.getItemFromList(itemsArrayMarket[i]);
							break;
						}
					}
					int buyPrice = PriceCalculatorService.findBuyingPrice(itemToBuy, currentRegion, player);
					int currentCargo = player.getShip().getCurrentCargoSpace();
					if (player.getCredits() < buyPrice) { //not enough credits
						throw new Exception("You don't have enough credits to purchase this.");
					} else if (currentCargo == 0) { //not enough space
						throw new Exception("You don't have enough cargo space to purchase this.");
					} else {
						//add to player inventory
						//remove buying credits from player credits
						if (!itemToBuy.getName().equals("additional fuel")) {
							player.inventoryAdd(itemToBuy);
							player.getShip().setCurrentCargoSpace(currentCargo - 1);
							player.setCredits(player.getCredits() - buyPrice);
							new MarketMenu(currentRegion, player);
							setVisible(false);
							dispose();
						}else {
							if(player.getShip().getFuelCapacity() - player.getShip().getCurrentFuel() < 10) {
								JFrame frame = new JFrame();
								JOptionPane.showMessageDialog(frame,
										"You have enough fuel in your ship.");
								setFrame();
							}else {
								player.setCredits(player.getCredits() - buyPrice);
								player.getShip().setCurrentFuel(player.getShip().getCurrentFuel() + 10);
								new MarketMenu(currentRegion, player);
								setVisible(false);
								dispose();
							}
						}
					}
				} catch (Exception e2) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,
							"You don't have enough credits and/or cargo space to buy this item.");
					setFrame();
				}
			}
		});


		JButton exitButton = new JButton("Exit Market");
		exitButton.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				setVisible(false);
				dispose();
			}
		});

		this.add(currentRegionText);
		this.add(currentCreditText);
		this.add(instruct);
		this.add(currentPlayerInventory);
		this.add(sellButton);
		this.add(instruct2);
		this.add(currentMarket);
		this.add(buyButton);
		this.add(exitButton);
	}
}
