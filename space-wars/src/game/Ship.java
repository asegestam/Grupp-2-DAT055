package game;

import java.util.Random;

import javax.swing.ImageIcon;

public class Ship extends Enemy {
	private final String enemyImg = "space-wars/img/enemy.png";
	private double max = 0.3;
	private double min = -0.3;
	private ImageIcon imgI;

	public Ship (double x, double y, double dx, double dy) {
		super(x,y,dx,dy);
		imgI = new ImageIcon(enemyImg);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setShipImage();
		initEnemy(x,y);
	}
	public void moveEnemy() {
		Random random = new Random();
		
		double dy = min + (max - min) * random.nextDouble();
		this.setxSpeed(-1);
		this.setySpeed(dy);
	}
	public void initEnemy(double x, double y) {
		setxPos(x);
		setyPos(y);
	}
	public Ship getShip() {
		return this;
	}
	public void setShipImage() {
		setImage(imgI.getImage());
	}
}
