package space_trader_game.Menus;
import javax.swing.*;

import enums.NPCType;
import interfaces.Menu;
import services.FuelCostCalcService;
import services.LoseService;
import space_trader_game.*;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame implements Menu {
	//from constructor call
	protected Player player;
	private String difficulty;
	private Region currentRegion;
	private Ship ship;
	//swing stuff
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel southPanel;
	private JComboBox<String> dropDownRegions;
	private JButton travelButton;
	public Game(String difficulty, Player player, Ship ship, Region region) {
		this.difficulty = difficulty;
		this.player = player;
		currentRegion = region;
		this.ship = ship;
		this.player.setShip(ship);
		
		if (LoseService.determineLoss(player)) {
		    new LoseMenu();
		} else if (currentRegion.getWinRegion()) {
	       setVisible(false);
	       dispose();
	       new EndGameMenu(player);
	    } else {
		    startGame(player);
		    setFrame();
		}
	}

	private void startGame(Player player) {
	}

	public void setFrame() {
	    player.getShip().setCurrentHealth(0);
	    
	    
	    
		//display window
		this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setResizable(false);
		this.setVisible(true);

		this.setLayout(new BorderLayout(2, 2));

		//NORTH PANEL (title)
		northPanel = new JPanel();
		northPanel.add(new JLabel("YOUR CURRENT LOCATION"));
		this.add(northPanel, BorderLayout.NORTH);

		//CENTER PANEL (picture)

		//WEST PANEL (current region info & ship & market)
		westPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(westPanel, BoxLayout.Y_AXIS);
        westPanel.setLayout(boxlayout);

		JLabel regionName = new JLabel("Region Name: " + currentRegion.getName());
		JLabel coordinates = new JLabel("Coordinates: " + currentRegion.getXCoord() + ", " + currentRegion.getYCoord());
		JLabel techLevel = new JLabel("Technological Level: " + currentRegion.getTechLevel());
		
		JButton shipEditor = new JButton("Edit Ship!");
		shipEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new ShipMenu(player);
            }
        });
		
		JButton shipManager = new JButton("Manage Ship!");
        shipManager.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShipManagerMenu(player);
            }
        });
		
		JButton marketButton = new JButton("Access This Region's Market!");
		marketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new MarketMenu(currentRegion, player);
            }
        });
		
		westPanel.add(regionName);
		westPanel.add(coordinates);
		westPanel.add(techLevel);
		westPanel.add(shipEditor);
		westPanel.add(shipManager);
		westPanel.add(marketButton);
		this.add(westPanel, BorderLayout.WEST);

		//SOUTH PANEL (travel to other regions)
		southPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(southPanel, BoxLayout.Y_AXIS);
		southPanel.setLayout(boxLayout);

		JLabel instruct = new JLabel("Pick a different region to travel to!");

		String[] arr = new String[9];
		String[] regionsArray = Universe.regionsListString.toArray(arr);
		dropDownRegions = new JComboBox<String>(regionsArray);
		instruct.setLabelFor(dropDownRegions);
		
		travelButton = new JButton("Travel!");
		travelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Region selection = Universe.getRegionFromList((String) dropDownRegions.getSelectedItem());
				int fuelCost = FuelCostCalcService.findFuelCost(ship, currentRegion,selection, player);
				if (FuelCostCalcService.isEnoughFuel(ship, fuelCost)) { //can travel
					ship.setCurrentFuel(ship.getCurrentFuel() - fuelCost);
					NPCType typeOfEncounter = EncounterMenu.getTypeOfEncounter(player, difficulty);
					if (typeOfEncounter != null) {
						setVisible(false);
						dispose();
						new EncounterMenu(typeOfEncounter, player, currentRegion, selection, difficulty);
						// EncounterMenu will take care of what to display after
						// the encounter (the original region, a new region, etc.)
					} else {
						new Game(difficulty, player, ship, selection);
						setVisible(false);
						dispose();
					}
				} else { //can't travel
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame,
							"You don't have enough fuel to travel there.");
					new Game(difficulty, player, ship, currentRegion);
					setVisible(false);
                    dispose();
				}
			}
		});

		southPanel.add(instruct);
		southPanel.add(dropDownRegions);
		southPanel.add(travelButton);

		this.add(southPanel, BorderLayout.SOUTH);

		//EAST PANEL (other regions list)
		//eastPanel = new JPanel();

		JLabel otherRegions = new JLabel("Information about Other Regions");
		String information = "";
		for (int i = 0; i < Universe.getRegionsList().size(); i++) {
			information += Universe.getRegionsList().get(i).getName() + ": \n";
			information += "     " + Universe.getRegionsList().get(i).getTechLevel() + "\n";
			information += "     (" + Universe.getRegionsList().get(i).getCoordinates() + ")";
			information += "\n";
		}
		JTextArea info = new JTextArea(information);
		info.setColumns(3);
		info.setLineWrap(true);
		info.setEditable(false);
		//otherRegions.setLabelFor(info);

		JScrollPane scroll = new JScrollPane(info);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scroll.add(otherRegions);
		this.add(scroll);
	}
}