package game;
import java.util.Random;
import javax.swing.ImageIcon;
/**
 * Used to create enemy ships with initial positions and graphics
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-03-02
 */
public class Ship extends Enemy {
	private final String enemyImg = "img/enemy.png";
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
		imgI = new ImageIcon(enemyImg);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setShipImage();
	}
	
	public void setShipImage() {
		setImage(imgI.getImage());
	}
	/**
	 * Returns a enemy ship object for use in other classes
	 * @return Ship
	 */
	public Ship getShip() {
		return this;
	}
}
