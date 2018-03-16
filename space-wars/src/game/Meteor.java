package game;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
/**
 * Used to create a moving rock object that moves across the screen
 * @author Albin Segestam
 * @version 2018-03-02
 */
public class Meteor extends Obj{
	java.net.URL meteor = getClass().getResource("/meteor.png");
	private static int min = 5;
	private static int max = 710;
	private ImageIcon imgI;
	/**
	 * Sets the X and Y position and speed
	 * @param x position x-axis
	 * @param y position y-axis
	 * @param dx speed x-axis
	 * @param dy speed y-axis
	 */
	public Meteor(int x, int y, int dx, int dy) {
		super(x,y,dx,dy);
		initMeteor();
	}
	/**
	 * Sets a image as the rock graphics
	 */
	public void initMeteor() {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		imgI = new ImageIcon(meteor);
		setWidth(imgI.getImage().getWidth(null));
		setHeight(imgI.getImage().getHeight(null));
		setMeteorImage();
		setyPos(randomNum);
	}
	/**
	 * Sets an image for the object
	 */
	public void setMeteorImage() {
		setImage(imgI.getImage());
	}
}
