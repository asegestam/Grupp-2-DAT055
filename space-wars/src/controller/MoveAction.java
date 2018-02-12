package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import game.Player;

public class MoveAction extends AbstractAction {
	
	private Player player;
	private int dx;
	private int dy;
	
	public MoveAction(Player player, int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.setxSpeed(dx);
		player.setySpeed(dy);
		player.move();
		
	}

}
