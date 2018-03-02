package game;
import java.awt.*;
import java.io.Serializable;

/** 
 * Used as a abstract class for the game objects
 * @author Albin Segestam,�ke Svensson, Markus Saarij�rvi, Erik Tallbacka, Theo Haugen
 * @version 2018-03-02
 */

public abstract class Obj implements Serializable {
	/**
	 * 
	 */
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

	public Obj(double x, double y, double dx, double dy) {
		this.yPos = y;
		this.xPos = x;
		this.xSpeed = dx;
		this.ySpeed = dy;
		visible = true;

	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double x) {
		this.xPos = x;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double y) {
		this.yPos = y;
	}

	public double getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}

	public double getySpeed() {
		return ySpeed;
	}

	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int lenght) {
		this.height = lenght;
	}
    public void setImage(Image image) {
        
        this.image = image;
    }

    public Image getImage() {
    
        return image;
    }
    public boolean isVisible() {
    
        return visible;
    }

    public void setVisible(boolean visible) {
    
        this.visible = visible;
    }
	public void move() {
		this.xPos += xSpeed;
		this.yPos += ySpeed;
		
	}

}
