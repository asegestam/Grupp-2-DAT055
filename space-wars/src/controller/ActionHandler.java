package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.GameEngine;
import game.Player;
import game.Projectiles;

public class ActionHandler implements ActionListener,KeyListener{
	boolean leftKey = false,rightKey = false,upKey = false, downKey = false;
	double speed;
	private Player player;
	private Projectiles projectile;
	private GameEngine game;
	
	public ActionHandler(Player player, GameEngine game) {
		this.player = player;
		this.game = game;
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_LEFT && !leftKey)
		{
			speed = player.getxSpeed();
			speed --;
			player.setxSpeed(speed);
			leftKey = true;
		}
		if(c == KeyEvent.VK_RIGHT && !rightKey)
		{
			speed = player.getxSpeed();
			speed ++;
			player.setxSpeed(speed);
			rightKey = true;
		}
		if(c == KeyEvent.VK_UP && !upKey)
		{
			speed = player.getySpeed();
			speed --;
			player.setySpeed(speed);
			upKey = true;
		}
		if(c == KeyEvent.VK_DOWN && !downKey)
		{
			speed = player.getySpeed();
			speed ++;
			player.setySpeed(speed);
			downKey = true;
		}	
		if(c == KeyEvent.VK_SPACE) {
			game.addProjectile();
		}	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		if(c == KeyEvent.VK_LEFT && leftKey)
		{
			speed = player.getxSpeed();
			speed ++;
			player.setxSpeed(speed);
			leftKey = false;
		}
		if(c == KeyEvent.VK_RIGHT && rightKey)
		{
			speed = player.getxSpeed();
			speed --;
			player.setxSpeed(speed);
			rightKey = false;
		}
		if(c == KeyEvent.VK_UP && upKey)
		{
			speed = player.getySpeed();
			speed ++;
			player.setySpeed(speed);
			upKey = false;
		}
		if(c == KeyEvent.VK_DOWN && downKey)
		{
			speed = player.getySpeed();
			speed --;
			player.setySpeed(speed);
			downKey = false;
} 
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	
}
