package game;
/**
 * Creates the Game GUI
 * Abstract class
 * @author Albin Segestam
 * @version 2018-03-02
 */

public abstract class Enemy extends Obj {
	/**
	 * Initates postion and speed
	 * @param x position x-axis
	 * @param y position y-axis
	 * @param dx speed x-axis
	 * @param dy speed y-axis
	 */
	public Enemy(double x, double y, double dx, double dy) {
		super(x,y,dx,dy);
	}
}
