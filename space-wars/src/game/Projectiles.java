package game;
import java.net.URL;

import javax.swing.ImageIcon;
/**
 * Used to create moving projectiles
 * @author Albin Segestam, Theo Haugen
 * @version 2018-03-02
 */
public class Projectiles extends Obj {
	private boolean hostile;
	private URL shot = getClass().getResource("/shot.png");
	private ImageIcon imgI;
	/**
	 * Initiates position, speed, graphics and if its a enemy projectile or player
	 * @param x position x-axis
	 * @param y position y-axis
	 * @param dx speed x-axis
	 * @param dy speed y-axis
	 * @param img image used by projectile
	 * @param hostileShot if the projectile is hostile to player or not
	 */
	public Projectiles(double x, double y, double dx, double dy, URL img, boolean hostileShot) {
		super(x,y,dx,dy);
		initShot(x,y,img);
		hostile = hostileShot;
	}
	/**
	 * Sets given image as the graphics
	 * Aligns the projectile
	 * @param x x position
	 * @param y y position
	 * @param img image used by projectile
	 */
	public void initShot(double x, double y, URL img) {
		imgI = new ImageIcon(img);
		setWidth(imgI.getImage().getWidth(null));
		setHeight(imgI.getImage().getHeight(null));
		setProjectilesImage();
		setxPos(x + 65);
		setyPos(y + 30);
	}
	/**
	 * Sets image
	 */
	public void setProjectilesImage() {
		setImage(imgI.getImage());
	}
	/**
	 * Returns hostile state
	 * @return if the projectile is hostile or not
	 */
	public boolean isHostile() {
		return hostile;
	}
	/**
	 * Sets hostile state
	 * @param hostile true if enemy projectile, false if player projectile
	 */
	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}
}
