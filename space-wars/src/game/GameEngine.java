package game;

import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

public class GameEngine extends JPanel{

	private ArrayList<Obj> activeObjects;
	private int score = 0;
	private Player player;
	
	public GameEngine() {
		activeObjects = new ArrayList<Obj>();
		setBackground(Color.black);
        gameInit();
	}
	
	 public void gameInit() {
	        player = new Player(100,100,0,0,"test");
	        System.out.println(player.getyPos());
	 }
	 
	 public void drawPlayer(Graphics g) {
		g.drawImage(player.getImage(), player.getxPos(), player.getyPos(), this);
	 }
	 
	 @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        drawPlayer(g);
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
