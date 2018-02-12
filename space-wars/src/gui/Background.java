package gui;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Background {
	
	private BufferedImage img;
	private final String backgroundImg = "space-wars/img/space.jpg";
	private int x,y;
	
	public Background() {
        this(0,0);
    }
	
	 public Background(int x, int y) {
	        this.x = x;
	        this.y = y;
	        
	        // Try to open the image file background
	        try {
	            img = ImageIO.read(new File(backgroundImg));
	        }
	        catch (Exception e) { System.out.println(e); }
	 
	    }
	 public void draw(Graphics g) {
		 
	        // Draw the image onto the Graphics reference
	        g.drawImage(img, getX(), getY(), img.getWidth(), img.getHeight(), null);
	 
	        // Move the x position left for next time
	        this.x -= 1;
	 
	        // Check to see if the image has gone off stage left
	        if (this.x <= -1 * img.getWidth()) {
	 
	            /*
	             *  If it has, line it back up so that its left edge is
	             *  lined up to the right side of the other background image
	             */ 
	            this.x = this.x + img.getWidth() * 2;
	        }
	 
	    }
	 
	    public void setX(int x) {
	        this.x = x;
	    }
	    public int getX() {
	        return this.x;
	    }
	    public int getY() {
	        return this.y;
	    }
	    public int getImageWidth() {
	        return img.getWidth();
	    }
	
	
}
