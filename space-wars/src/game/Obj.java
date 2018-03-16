package game;
import java.awt.*;
import java.io.Serializable;

/** 
 * Used as a abstract class for the game objects
 * @author Markus Saarijärvi, Theo Haugen
 * @version 2018-03-02
 */

public abstract class Obj implements Serializable {
	private static final long serialVersionUID = -4645154821514581456L;
	private double xPos;
	private double yPos;
	private double xSpeed;
	private double ySpeed;
	private int hitPoints;
	private int width;
	private int height;
	private  transient Image image;
	private boolean visible;
	/**
	 * Sets postions and speed
	 * @param x x position
	 * @param y y position
	 * @param dx speed in x-axis
	 * @param dy speed in y-axis
	 */
	public Obj(double x, double y, double dx, double dy) {
		this.yPos = y;
		this.xPos = x;
		this.xSpeed = dx;
		this.ySpeed = dy;
		visible = true;

	}
	public Obj getInstance() {
		return this;
	}
	/**
	 * Returns x position
	 * @return x postion
	 */
	public double getxPos() {
		return xPos;
	}
	/**
	 * Sets the x position
	 * @param x value being set
	 */
	public void setxPos(double x) {
		this.xPos = x;
	}
	/**
	 * Returns the y position
	 * @return y position
	 */
	public double getyPos() {
		return yPos;
	}
	/**
	 * Sets the y position
	 * @param y value being set
	 */
	public void setyPos(double y) {
		this.yPos = y;
	}
	/**
	 * Returns the x speed
	 * @return x-axis speed
	 */
	public double getxSpeed() {
		return xSpeed;
	}
	/**
	 * Sets the x-axis speed
	 * @param xSpeed value being set
	 */
	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	/**
	 * Returns the y speed
	 * @return y-axis speed
	 */
	public double getySpeed() {
		return ySpeed;
	}
	/**
	 * Sets the y speed
	 * @param ySpeed value being set
	 */
	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	/**
	 * Returns health points
	 * @return health points
	 */

	public int getHitPoints() {
		return hitPoints;
	}
	/**
	 * Sets the health points
	 * @param hitPoints value being set
	 */
	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	/**
	 * Returns width of object
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Sets the width
	 * @param width value being set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * Returns the height of object
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Sets the height
	 * @param height value being set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * Sets an image of the object
	 * @param image image being used
	 */
    public void setImage(Image image) {
        this.image = image;
    }
    /**
     * Returns an image of the object
     * @return image of the object
     */
    public Image getImage() {
        return image;
    }
    /**
     * Returns if the object is visible or not
     * @return true if visible, false if not
     */
    public boolean isVisible() {
    
        return visible;
    }
    /**
     * Sets the visibility of the object
     * @param visible true if visiable, false if not
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    /**
     * Moves the object bases on the x and y speed.
     */
	public void move() {
		this.xPos += xSpeed;
		this.yPos += ySpeed;
		
	}

}
