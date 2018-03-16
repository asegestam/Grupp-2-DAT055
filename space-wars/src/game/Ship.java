package game;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
/**
 * Used to create enemy ships with initial positions and graphics
 * @author Åke Svensson
 * @version 2018-03-02
 */
public class Ship extends Enemy {
	private URL ship = getClass().getResource("/enemyShip.png");
	private double max = 0.3;
	private double min = -0.3;
	private ImageIcon imgI;
	/**
	 * Sets X and Y positions and X and Y-axis speeds
	 * @param x position x-axis
	 * @param y position y-axis
	 * @param dx speed x-axis
	 * @param dy speed y-axis
	 */
	public Ship (double x, double y, double dx, double dy) {
		super(x,y,dx,dy);
		initEnemy();
	}
	/**
	 * Moves the ship randomly across the screen
	 */
	public void moveEnemy() {
		Random random = new Random();
		double dy = min + (max - min) * random.nextDouble();
		this.setxSpeed(-1);
		this.setySpeed(dy);
	}
	/**
	 * Sets an image as the ship graphics
	 */
	public void initEnemy() {
		imgI = new ImageIcon(ship);
		setWidth(imgI.getImage().getWidth(null));
		setHeight(imgI.getImage().getHeight(null));
		setShipImage();
	}
	/**
	 * Sets an image for the object
	 */
	public void setShipImage() {
		setImage(imgI.getImage());
	}
}
