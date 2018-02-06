package game;

import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

public class GameEngine {

	private ArrayList<Obj> activeObjects;
	private int score = 0;
	private Player player;
	private JFrame frame;

	public GameEngine(String name) {
		activeObjects = new ArrayList<Obj>();
		player = new Player(name);
	}

	public void update() {

	}

	public boolean collisionDetection() {
		return false;
	}

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}
}
