package game;
import javax.swing.ImageIcon;
/**
 * Used to create moving projectiles
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-03-02
 */
public class Projectiles extends Obj {
	
	private boolean hostile;
	private int dmg;
	private final String shot = "img/shot.png";
	private ImageIcon imgI;
	/**
	 * Initiates position, speed, graphics and if its a enemy projectile or player
	 * @param x position x-axis
	 * @param y position y-axis
	 * @param dx speed x-axis
	 * @param dy speed y-axis
	 * @param img
	 * @param hostileShot if the projectile is hostile to player or not
	 */
	public Projectiles(double x, double y, double dx, double dy, String img, boolean hostileShot) {
		super(x,y,dx,dy);
		initShot(x,y,img);
		hostile = hostileShot;
	}
	/**
	 * Sets given image as the graphics
	 * Aligns the projectile
	 * @param x
	 * @param y
	 * @param img
	 */
	public void initShot(double x, double y, String img) {
		imgI = new ImageIcon(img);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setProjectilesImage();
		setxPos(x + 120);
		setyPos(y + 55);
	}
	/**
	 * Sets image
	 */
	public void setProjectilesImage() {
		setImage(imgI.getImage());
	}
	/**
	 * Return image of projectile
	 * @return
	 */
	public String getProjectilesImage() {
				return shot;
	}
	/**
	 * Returns hostile state
	 * @return
	 */
	public boolean isHostile() {
		return hostile;
	}
	/**
	 * Sets hostile state
	 * @param hostile
	 */
	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}
	/**
	 * Returns this object
	 * @return
	 */
	public Projectiles getProjectile() {
		return this;
	}
	
	
}
