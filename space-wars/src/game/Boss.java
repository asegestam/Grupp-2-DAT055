package game;

import java.util.Random;
import javax.swing.ImageIcon;
/**
 * Used to create a Boss object with HP,X and Y positions
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 *
 */
public class Boss extends Enemy {
	private final String bossImg = "img/boss.png";
	double min = -3;
	double max = 3;
	boolean bossAlive = false;
	private ImageIcon imgI;
	
	public Boss(double  x, double y, double dx, double dy, int hp) {
		super(x,y,dx,dy);
		this.setHitPoints(hp);
		initBoss(x,y);
	}
	/**
	 * Moves the boss object randomly
	 */
	public void moveBoss() {
		Random random = new Random();
		double dy = min + (max - min) * random.nextDouble();
		this.setySpeed(dy);
	}
	/**
	 * Initiate the boss
	 * @param x
	 * @param y
	 */
	public void initBoss(double x, double y) {
		imgI = new ImageIcon(bossImg);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setBossImage();
		setxPos(x);
		setyPos(y);
	}
	/**
	 * Sets a image as the boss graphics
	 */
	public void setBossImage() {
		setImage(imgI.getImage());
	}
	public Boss getBoss() {
		return this;
	}
	public boolean getBossAlive() {
		return bossAlive;
	}
	public void setBossAlive(boolean b) {
		bossAlive = b;
	}

}
