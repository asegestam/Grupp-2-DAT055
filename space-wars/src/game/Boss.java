package game;

import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
/**
 * Used to create a Boss object with HP,X and Y positions
 * @author Albin Segestam,Åke Svensson
 * @version 2018-03-02
 */
public class Boss extends Enemy {
	private URL bossImg = getClass().getResource("/boss.png");
	private double min = -3;
	private double max = 3;
	private boolean bossAlive = false;
	private ImageIcon imgI;
	/**
	 * Initiates position and hp on boss creation.
	 * @param x positon x-axis
	 * @param y position y-axis
	 * @param dx speed x-axis
	 * @param dy speed y-axis
	 * @param hp health points for the boss
	 */
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
	 * @param x position x-axis
	 * @param y position x-axis
	 */
	public void initBoss(double x, double y) {
		imgI = new ImageIcon(bossImg);
		setWidth(imgI.getImage().getWidth(null));
		setHeight(imgI.getImage().getHeight(null));
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
	/**
	 * Returns boolean indicating if the boss is alive
	 * @return boolean if the boss is alive or not
	 */
	public boolean getBossAlive() {
		return bossAlive;
	}
	/**
	 * Sets the alive boolean
	 * @param b true if it is alive, false if not
	 */
	public void setBossAlive(boolean b) {
		bossAlive = b;
	}

}
