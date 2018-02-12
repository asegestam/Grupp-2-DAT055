package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import game.Player;

public class MoveAction extends AbstractAction {
	
	private Player player;
	private double dx;
	private double dy;
	
	public MoveAction(Player player, double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(player.getxSpeed() != dx || player.getySpeed() != dy) {
			player.setxSpeed(player.getxSpeed());
			player.setySpeed(player.getySpeed());
		}
		player.setxSpeed(dx);
		player.setySpeed(dy);
		player.move();
	}

}
