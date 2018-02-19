package game;
import java.awt.*;
import javax.imageio.*;

public class Obj {
	
	//Instansvariabler
	private int xPos;
	private int yPos;
	private double xSpeed;
	private double ySpeed;
	private int hitPoints;
	private int width;
	private int lenght;
	private Image image;
	private boolean visible;

	public Obj(int x, int y, int dx, int dy) {
		this.xPos = x;
		this.yPos = y;
		this.xSpeed = dx;
		this.yPos = dy;
		visible = true;

	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
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

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
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
