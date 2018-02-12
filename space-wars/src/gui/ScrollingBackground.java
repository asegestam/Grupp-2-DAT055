package gui;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
	 
public class ScrollingBackground extends Canvas implements Runnable {
	 
	    // Two copies of the background image to scroll
	    private Background backgroundOne;
	    private Background backgroundTwo;
	 
	    private BufferedImage back;
	 
	    public ScrollingBackground() {
	        backgroundOne = new Background();
	        backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
	 
	        new Thread(this).start();
	        setVisible(true);
	    }
	 
	    @Override
	    public void run() {
	        try {
	        	
	            while (true) {
	                Thread.currentThread();
	                // Set how slow background moves
					Thread.sleep(10);
	                repaint();
	            }
	        }
	        catch (Exception e) {}
	    }
	 
	    @Override
	    public void update(Graphics g) {
	        paint(g);
	    }
	 
	    public void paint(Graphics g) {
	        Graphics2D twoD = (Graphics2D)g;
	 
	        if (back == null)
	            back = (BufferedImage)(createImage(getWidth(), getHeight()));
	 
	        // Create a buffer to draw to
	        Graphics buffer = back.createGraphics();
	 
	        // Put the two copies of the background image onto the buffer
	        backgroundOne.draw(buffer);
	        backgroundTwo.draw(buffer);
	 
	        // Draw the image onto the window
	        twoD.drawImage(back, null, 0, 0);
	 
	    }

}
