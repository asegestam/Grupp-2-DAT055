package game;

import java.util.Random;

import javax.swing.ImageIcon;

public class Ship extends Enemy {
	private final String enemyImg = "space-wars/img/enemy.png";
	private int max = 220;
	private int min = -230;

	public Ship (int x, int y, int dx, int dy) {
		super(x,y,dx,dy);
		initEnemy(x,y);
	}
	public void moveEnemy() {
		int random = new Random().nextInt(max + 1 - min) + min;
		this.setxSpeed(random/100);
		this.setySpeed(random/100);
	}
	public void bounce() {
		if(this.getxPos() < 0 || this.getyPos()> 840)
		{
		    this.setxSpeed(-this.getxSpeed());

		if (this.getxPos() < 0)  
		{
		    this.setxPos(0);
		} 
		else if (this.getxPos() > 840)  
		{
		    this.setxPos(840);
		} 
	}
	}
	public void initEnemy(int x, int y) {
		ImageIcon imgI = new ImageIcon(enemyImg);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setImage(imgI.getImage());
		setxPos(x);
		setyPos(y);
	}
	public Ship getShip() {
		return this;
	}
}
