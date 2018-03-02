package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import game.GameEngine;
import game.Player;
/**
 * 	Handles the user input to move and shoot the player object
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-02-27
 */

public class ActionHandler implements ActionListener,KeyListener {
	
	boolean leftKey = false,rightKey = false,upKey = false, downKey = false, spaceKey = false;
	double speed;
	double deltaSpeed = 2;
	private Player player;
	private GameEngine game;
	/**
	 * Initiates the player object being controlled and the game engine
	 * @param player
	 * @param game
	 */
	public ActionHandler(Player player, GameEngine game) {
		this.player = player;
		this.game = game;
	}
	
	
	public void updateHandler(Player player, GameEngine game) {
		this.player = player;
		this.game = game;
	}
    /**
     * Moves the player object  and adds a projectile to the projectile array based on key pressed. Can also pause the game.
     * Keys used: Arrow Keys, Space, Escape
     */
	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_ESCAPE) {
			boolean pause = game.getPause();
			if(pause) {
				game.setPause(false);
			}
			else {
				game.setPause(true);
				}
			}
		
		if(!game.pause) {
			if(c == KeyEvent.VK_LEFT && !leftKey) {
				speed = player.getxSpeed();
				speed -= deltaSpeed;
				player.setxSpeed(speed);
				leftKey = true;
			}
			if(c == KeyEvent.VK_RIGHT && !rightKey) {
				speed = player.getxSpeed();
				speed += deltaSpeed;
				player.setxSpeed(speed);
				rightKey = true;
			}
			if(c == KeyEvent.VK_UP && !upKey) {
				speed = player.getySpeed();
				speed -= deltaSpeed;
				player.setySpeed(speed);
				upKey = true;
			}
			if(c == KeyEvent.VK_DOWN && !downKey) {
				speed = player.getySpeed();
				speed += deltaSpeed;
				player.setySpeed(speed);
				downKey = true;
			}	
			if(c == KeyEvent.VK_SPACE && !spaceKey) {
				game.addProjectile(player.getxPos(),player.getyPos(),5,0,"img/Shot.png",false);
				spaceKey = true;
			}	
	    }	
	}
	/**
	 * Moves the player object based on key released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_LEFT && leftKey) {
			speed = player.getxSpeed();
			speed += deltaSpeed;
			player.setxSpeed(speed);
			leftKey = false;
		}
		if(c == KeyEvent.VK_RIGHT && rightKey) {
			speed = player.getxSpeed();
			speed -= deltaSpeed;
			player.setxSpeed(speed);
			rightKey = false;
		}
		if(c == KeyEvent.VK_UP && upKey) {
			speed = player.getySpeed();
			speed += deltaSpeed;
			player.setySpeed(speed);
			upKey = false;
		}
		if(c == KeyEvent.VK_DOWN && downKey) {
			speed = player.getySpeed();
			speed -= deltaSpeed;
			player.setySpeed(speed);
			downKey = false;
		}
		if(c == KeyEvent.VK_SPACE && spaceKey) {
			spaceKey = false;
		}
		
	}

	/**
	 * Unused
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Unused
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	
}
