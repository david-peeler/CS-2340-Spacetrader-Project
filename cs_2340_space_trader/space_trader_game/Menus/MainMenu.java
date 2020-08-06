package space_trader_game.Menus;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import interfaces.Menu;
import space_trader_game.Constants;
import space_trader_game.Player;
import space_trader_game.Ship;
import space_trader_game.Universe;

/**
 * The MainMenu class is responsible for harvesting the data to configure
 * the player Class and Game class used throughout the rest of the game.
 * 
 * @author CS 2340 Group 81 Fall 2019
 *
 */
public class MainMenu extends JFrame implements Menu {
	
	//Used in setFrame (previously MainMenu)
	private JButton toGame;
	private JPanel panel;
	
	//Used in setFrame2 (previously InitialConfig1)
	private Player player = new Player();
	
	private JPanel northPanel;
	private JTextField nameField;
	
	private JPanel centerPanel;
	private ButtonGroup buttonGroup;

	private JRadioButton difficultyEasy;
	private JRadioButton difficultyMed;
	private JRadioButton difficultyHard;
	private static String difficulty;

	private JPanel southPanel;
	private JButton nextPage;
	//Used in setFrame3 (previously InitialConfig2)
	private int totalPoints;
	
	private JLabel displayName;
	
	private JLabel totalSkillPoints;
	private JLabel pilotLabel;
	private JTextField pilotText = new JTextField(5);
	private JLabel fighterLabel;
	private JTextField fighterText = new JTextField(5);
	private JLabel merchantLabel;
	private JTextField merchantText = new JTextField(5);
	private JLabel engineerLabel;
	private JTextField engineerText = new JTextField(5);
	private JButton next;
	
	//Used in setConfirmation (previously InitialConfirmation)
	private JLabel titleLabel;

	private JLabel difficultyConfirmation;
	private JLabel characterNameConfirmation;
	private JLabel engineerPointConfirmation;
	private JLabel pilotPointConfirmation;
	private JLabel fighterPointConfirmation;
	private JLabel merchantPointConfirmation;

	private JLabel startingCreditsText;
	private JLabel startingMoneyText;
	
	
	private JButton confirmationButton;
	
	public void setFrame() {
		this.panel = new JPanel();
		
		this.toGame = new JButton("Start New Game");
		toGame.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent ae) {
		    	setVisible(false);
		    	dispose();
		    	try {
		    		remove(panel);
		    		setFrame2();
		    	} catch (Exception e) {
		    		e.printStackTrace();
		    	}
		    }
		});
		
		panel.add(toGame);
		
		this.setLayout(new BorderLayout());	
		this.add(panel, BorderLayout.CENTER);
		
		//set frame settings
		this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}

	/**
	 * for player name and game difficulty
	 */
	public void setFrame2() {		
		 //Display the window
	     this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.setVisible(true);
		 this.setResizable(false);

		 this.setLayout(new BorderLayout());
		 		
		 //Setup north panel (name)
		 this.nameField = new JTextField(20);
		 this.nameField.setText("Enter a name");
		 
		 this.northPanel = new JPanel();
		 northPanel.add(this.nameField);
		 this.add(northPanel, BorderLayout.NORTH);
		 
		 //Setup center panel (difficulty)
		 this.buttonGroup = new ButtonGroup();
		 this.difficultyEasy = new JRadioButton("Easy");
		 this.difficultyMed = new JRadioButton("Medium");
		 this.difficultyHard = new JRadioButton("Hard");
		 buttonGroup.add(difficultyEasy);
		 buttonGroup.add(difficultyMed);
		 buttonGroup.add(difficultyHard);
		 
		 difficultyEasy.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            setDifficulty("Easy");
		        }
		    });
		 
		 difficultyMed.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            setDifficulty("Med");
		        }
		    });
		 
		 difficultyHard.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            setDifficulty("Hard");
		        }
		    });
		 
		 this.centerPanel = new JPanel();
		 centerPanel.add(difficultyEasy);
		 centerPanel.add(difficultyMed);
		 centerPanel.add(difficultyHard);
		 this.add(centerPanel, BorderLayout.CENTER);
		 
		 
		 //Setup south panel (next)
		 this.nextPage = new JButton("Next");
		 nextPage.addActionListener(new ActionListener() {
		        @Override
			    public void actionPerformed(ActionEvent ae) {
			    	setVisible(false);
			    	dispose();
			    	try {
			    		player.setName(nameField.getText().toString());
			    		remove(centerPanel);
			    		remove(northPanel);
			    		remove(southPanel);
			    		setFrame3();
			    	} catch (Exception e) {
			    		e.printStackTrace();
			    	}
			    }
		    });
		 
		 this.southPanel = new JPanel();
		 southPanel.add(nextPage);
		 this.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * for picking skill points
	 */
	public void setFrame3() {
		if (difficulty == null) {
			MainMenu.difficulty = "Easy";
		}
		
		if (player.getName() == null) {
			player.setName("name");
		} 
		
		if (MainMenu.difficulty == "Easy") {
			this.totalPoints = 16;
		} else if (MainMenu.difficulty == "Med") {
			this.totalPoints = 12;
		} else if (MainMenu.difficulty == "Hard") {
			this.totalPoints = 8;
		}
		
		//Display the window
	    this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);

		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		 
		 //North Panel (name)
		 this.displayName = new JLabel(player.getName());
		 //this.northPanel = new JPanel();
		 this.add(displayName);
		 
		 //Center Panel (skillpoints)
		 this.totalSkillPoints = new JLabel("Total number of skillpoints you can assign: " + String.valueOf(totalPoints));
		 totalSkillPoints.setHorizontalAlignment(JLabel.CENTER);
		
		 this.pilotLabel = new JLabel("Enter the number of skillpoints for being a pilot: ");
		 pilotLabel.setHorizontalAlignment(JLabel.CENTER);
		 pilotLabel.setLabelFor(pilotText);
		 
		 this.fighterLabel = new JLabel("Enter the number of skillpoints for being a fighter: ");
		 fighterLabel.setHorizontalAlignment(JLabel.CENTER);
		 fighterLabel.setLabelFor(fighterText);
		 
		 this.engineerLabel = new JLabel("Enter the number of skillpoints for being an engineer: ");
		 engineerLabel.setHorizontalAlignment(JLabel.CENTER);
		 engineerLabel.setLabelFor(engineerText);
		 
		 this.merchantLabel = new JLabel("Enter the number of skillpoints for being a merchant: ");
		 merchantLabel.setHorizontalAlignment(JLabel.CENTER);
		 merchantLabel.setLabelFor(merchantText);
		 
		 this.add(totalSkillPoints);
		 this.add(pilotLabel);
		 this.add(pilotText);
		 this.add(fighterLabel);
		 this.add(fighterText);
		 this.add(engineerLabel);
		 this.add(engineerText);
		 this.add(merchantLabel);
		 this.add(merchantText);
		 
		 //South Panel (next)
		 this.next = new JButton("Next");
		 next.addActionListener(new ActionListener() {
		        @Override
			    public void actionPerformed(ActionEvent ae) {
			    	setVisible(false);
			    	dispose();
			    	try {
			    		player.setPilot(Integer.parseInt(pilotText.getText()));
			    		player.setFighter(Integer.parseInt(fighterText.getText()));
			    		player.setEngineer(Integer.parseInt(engineerText.getText()));
			    		player.setMerchant(Integer.parseInt(merchantText.getText()));

			    		if (player.getPilot() + player.getFighter() 
			    			+ player.getEngineer() + player.getMerchant() != getMaxPoints()) {
			    			throw new IllegalArgumentException();
			    		} else if (player.getPilot() < 0 || player.getFighter() < 0 || player.getEngineer() < 0 ||
			    				player.getMerchant() < 0) {
			    			throw new IllegalArgumentException();
			    		} else {
					    	setVisible(false);
					    	dispose();
					    	remove(displayName);
					    	remove(totalSkillPoints);
					    	remove(pilotLabel);
					    	remove(pilotText);
					    	remove(fighterLabel);
					    	remove(fighterText);
					    	remove(engineerLabel);
					    	remove(engineerText);
					    	remove(merchantLabel);
					    	remove(merchantText);
					    	remove(next);
			    			setConfirmation();
			    		}
			    	} catch (Exception e) {
			    		JFrame frame = new JFrame();
			    		JOptionPane.showMessageDialog(frame, "Please enter a valid number of skillpoints.");
			    		setFrame3();
			    	}
			    }
		    });

		 this.add(next);
	}

	public void setConfirmation() {
		//Display the window
	     this.setSize(Constants.MENU_WIDTH, Constants.MENU_HEIGHT);
	     this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     this.setResizable(false);
		 this.setVisible(true);

		 this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		 
		 //Confirmation message
		 titleLabel = new JLabel("Please confirm your settings.");
		 
		 this.add(titleLabel);
		 
		 
		 //Difficulty and Points
		 this.difficultyConfirmation = new JLabel("Difficulty: " + MainMenu.difficulty);
	     this.characterNameConfirmation = new JLabel("Character name: " + player.getName());
		 this.engineerPointConfirmation = new JLabel("Engineer Points: " + player.getEngineer());
	     this.pilotPointConfirmation = new JLabel("Pilot Points: " + player.getPilot());
		 this.fighterPointConfirmation = new JLabel("Fighter Points: " + player.getFighter());
		 this.merchantPointConfirmation = new JLabel("Merchant Points: " + player.getMerchant());

		 this.add(difficultyConfirmation);
		 this.add(characterNameConfirmation);
		 this.add(engineerPointConfirmation);
		 this.add(pilotPointConfirmation);
		 this.add(fighterPointConfirmation);
		 this.add(merchantPointConfirmation);
		 
		 if (MainMenu.difficulty == "Easy") {
			 player.setCredits(1000);
			 player.setMoney(1000);
		 } else if (MainMenu.difficulty == "Med") {
			 player.setCredits(500);
			 player.setMoney(500);
		 } else {
			 player.setCredits(100);
			 player.setMoney(100);
		 }
		 this.startingCreditsText = new JLabel("Your starting credits: " + player.getCredits());
		 this.startingMoneyText = new JLabel("Your starting money: " + player.getMoney());

		 this.add(startingCreditsText);
		 this.add(startingMoneyText);

		 
		 //Confirmation Button
		 this.confirmationButton = new JButton("Click here to confirm");
		 confirmationButton.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent event) {
			  	new Universe();
			  	Ship ship = player.getShip();
			  	new Game(difficulty, player, ship, Universe.randomStartingRegion());
			  	setVisible(false);
			  	dispose();
			  }
		 });
		 
		 this.add(confirmationButton);
	}
	
	private int getMaxPoints() {
		return this.totalPoints;
	}

	private void setDifficulty(String difficulty) {
		MainMenu.difficulty = difficulty;
	}

	public static String getDifficulty() {
		return difficulty;
	}

	public MainMenu() {
		super("Welcome to Space Traders!");
		this.setFrame();	
	}
}
