package space_trader_game;
/**
 * The driver for the application.
 * @author CS 2340 Group 81
 *
 */
import javax.swing.*;

import space_trader_game.Menus.MainMenu;

public class Driver {


	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		/**
		 * The main method for the driver
		 * @author CS Group 81 Fall 2019
		 */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new MainMenu();
				} catch (Exception e) {				
					e.printStackTrace();
				}
			}
		});
	}
}