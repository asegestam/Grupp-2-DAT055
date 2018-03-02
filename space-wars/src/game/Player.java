package game;

import javax.swing.ImageIcon;
/**
 * Used to iniate a player object with HP,X and Y positions
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 *
 */
public class Player extends Obj {
	private final String playerImg = "img/ship.png";
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
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setPlayerImage();
	}
	/**
	 * Sets a image as the player graphics
	 */
	public void setPlayerImage() {
		setImage(imgI.getImage());
	}
}
