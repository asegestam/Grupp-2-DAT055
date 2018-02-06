package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	
	
	public GUI() {
		super(WINDOW_TITLE);
		makeFrame();
	}
	public void makeFrame() {
		
		JFrame frame =  new JFrame();
		
		frame.pack();
		frame.setSize(1920, 800);
		frame.setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("text");
		menu.add(menuItem);
		menu = new JMenu("Settings");
		menuBar.add(menu);
		menuItem = new JMenuItem("text");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);
		/* F�ljande kod tar fram en bild
		 * 
		 */
		JLabel jl = new JLabel();
	    Image image = new ImageIcon(getClass().getResource("image.jpg")).getImage();
	    jl.setIcon(new ImageIcon(image));
	    frame.add(jl);
	    
		

	    frame.setLocationRelativeTo(null); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);

	}
	
}
