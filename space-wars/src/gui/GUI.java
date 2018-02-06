package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	
	
	public GUI() {
		super(WINDOW_TITLE);
		makeFrame();
	}
	
	public static void main(String[] args) {
		
		new GUI();
		
	}
	
	public void makeFrame() {
		
		JFrame frame =  new JFrame();
		Container contentPane = frame.getContentPane();
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
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2); 
	    
	    frame.setVisible(true);
	}
	
}
