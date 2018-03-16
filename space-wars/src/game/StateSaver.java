package game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * 	Used to save objects in class state and load them into the game engine
 * @author Markus Saarijärvi
 * @version 2018-03-16
 */
public class StateSaver {
	
	private ArrayList<Ship> activeShips;
	private ArrayList<Projectiles> projectiles;
	private ArrayList<Meteor> meteors;
	private ArrayList<Boss> bosses;
	private Player player;
	private GameEngine game;
	private State state = new State();
	private int score;
	public StateSaver(GameEngine game) {
		this.game = game;
	}
	
	/**
	 * Streams the game objects to a file
	 * @param fileName file name of the file being written to
	 */
    public void saveGame(String fileName) {
		activeShips = game.getActiveShips();
		projectiles = game.getProjectiles();
		meteors = game.getMeteors();
		bosses = game.getBosses();
		player = game.getPlayer();
		state.activeShips = activeShips;
		state.projectiles = projectiles;
		state.bosses = bosses;
		state.meteors = meteors;
		state.player = player;
		state.xPos = player.getxPos();
		state.yPos = player.getyPos();
		state.score = game.getScore();
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(state);
			out.close();
			return;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Loads objects from file into the game
	 * @param fileName file name of the file being read
	 */
	public void loadGame(String fileName) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
				state = (State)in.readObject();
				in.close();
				game.setActiveShips(state.activeShips);
				game.setProjectiles(state.projectiles);
				game.setBosses(state.bosses);
				game.setMeteors(state.meteors);
				game.setPlayer(state.player);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the saved x position for the player
	 * @return x position for player
	 */
	public double getSavedXPos() {
		return state.xPos;
	}
	/**
	 * Returns the saved y position for the player
	 * @return y position for player
	 */
	public double getSavedYPos() {
		return state.yPos;
	}
	/**
	 * Returns the saved player score
	 * @return player score
	 */
	public int getSavedScore() {
		return state.score;
	}
	/**
	 * A inner class used for saving the state of all game objects
	 * @author Markus Saarijärvi
	 *
	 */
	private static class State implements Serializable {
		/**
		 * Empty Contstuctor
		 */
		public State() {
		}
		private static final long serialVersionUID = -6636539941992971082L;
		public ArrayList<Ship> activeShips;
		public ArrayList<Projectiles> projectiles;
		public ArrayList<Meteor> meteors;
		public ArrayList<Boss> bosses;
		private Player player;
		private double xPos;
		private double yPos;
		private int score;
	}

}
