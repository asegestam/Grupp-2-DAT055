package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.GameEngine;
import game.Player;
import game.Projectiles;

public class ActionHandler implements ActionListener,KeyListener{
	boolean leftKey = false,rightKey = false,upKey = false, downKey = false, spaceKey = false;
	double speed;
	double deltaSpeed = 2;
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
			speed -= deltaSpeed;
			player.setxSpeed(speed);
			leftKey = true;
		}
		if(c == KeyEvent.VK_RIGHT && !rightKey)
		{
			speed = player.getxSpeed();
			speed += deltaSpeed;
			player.setxSpeed(speed);
			rightKey = true;
		}
		if(c == KeyEvent.VK_UP && !upKey)
		{
			speed = player.getySpeed();
			speed -= deltaSpeed;
			player.setySpeed(speed);
			upKey = true;
		}
		if(c == KeyEvent.VK_DOWN && !downKey)
		{
			speed = player.getySpeed();
			speed += deltaSpeed;
			player.setySpeed(speed);
			downKey = true;
		}	
		if(c == KeyEvent.VK_SPACE && !spaceKey) {
			game.addProjectile(player.getxPos(),player.getyPos(),5,0,"space-wars/img/Shot.png",false);
			spaceKey = true;
		}	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int c = e.getKeyCode();
		if(c == KeyEvent.VK_LEFT && leftKey)
		{
			speed = player.getxSpeed();
			speed += deltaSpeed;
			player.setxSpeed(speed);
			leftKey = false;
		}
		if(c == KeyEvent.VK_RIGHT && rightKey)
		{
			speed = player.getxSpeed();
			speed -= deltaSpeed;
			player.setxSpeed(speed);
			rightKey = false;
		}
		if(c == KeyEvent.VK_UP && upKey)
		{
			speed = player.getySpeed();
			speed += deltaSpeed;
			player.setySpeed(speed);
			upKey = false;
		}
		if(c == KeyEvent.VK_DOWN && downKey)
		{
			speed = player.getySpeed();
			speed -= deltaSpeed;
			player.setySpeed(speed);
			downKey = false;
		}
		if(c == KeyEvent.VK_SPACE && spaceKey) {
			spaceKey = false;
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
