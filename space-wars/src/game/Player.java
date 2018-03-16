package game;

import java.net.URL;

import javax.swing.ImageIcon;
/**
 * Used to iniate a player object with HP,X and Y positions
 * @author Albin Segestam,Erik Tallbacka
 * @version 2018-03-02
 *
 */
public class Player extends Obj {
	private URL playerImg = getClass().getResource("/playerShip.png");
	private ImageIcon imgI;
    /**
     * Sets initial X and Y positions, speed, and hp
     * @param x position x-axis
     * @param y position y-axis
     * @param dx speed x-axis
     * @param dy speed y-axis
     * @param hp health points
     */
	public Player(int x, int y, int dx, int dy,int hp) {
		super(x,y,dx,dy);
		setHitPoints(hp);
		initPlayer();
	}
	/**
	 * Initiates the player object
	 */
	public void initPlayer() {
		imgI = new ImageIcon(playerImg);
		setPlayerImage();
		setWidth(imgI.getImage().getWidth(null));
		setHeight(imgI.getImage().getHeight(null));
	}
	/**
	 * Sets a image as the player graphics
	 */
	public void setPlayerImage() {
		setImage(imgI.getImage());
	}
}
