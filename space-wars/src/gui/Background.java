package gui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Used to add a scrolling background to the game to mimic forward movement
 * @author Erik Tallbacka
 * @version 2018-03-02
 */
public class Background {
	
	private BufferedImage img;
	java.net.URL backgroundImg =  getClass().getResource("/space.jpg");
	private int x,y;
	/**
	 * Initiates starting position of the background
	 */
	public Background() {
        this(0,0);
    }
	
	public Background(int x, int y) {
	    this.x = x;
	    this.y = y;
	    // Try to open the image file background
	    try {
	        img = ImageIO.read(backgroundImg);
	    }
	    catch (Exception e) { System.out.println(e); }
	}
	/**
	 * Draws the background
	 * @param g what object to draw on, in this case the panel
	 */
	public void draw(Graphics g) {
		// Draw the image onto the Graphics reference
	    g.drawImage(img, getX(), getY(), img.getWidth(), img.getHeight(), null);
	    // Move the x position left for next time
	    this.x -= 1;
	    // Check to see if the image has gone off stage left
	    if (this.x <= -1 * img.getWidth()) {
	    	/*
	         * If it has, line it back up so that its left edge is
	         *  lined up to the right side of the other background image
	         */ 
	    	this.x = this.x + img.getWidth() * 2;
	    }
	}
	/**
	 * Sets the x position
	 * @param x value being set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Returns the x position
	 * @return x position
	 */
	public int getX() {
	   return this.x;
	}
	/**
	 * Returns y position
	 * @return y position
	 */
	public int getY() {
		return this.y;
	}
	/**
	 * Returns the image width
	 * @return the image width
	 */
	public int getImageWidth() {
	    return img.getWidth();
	}
}
