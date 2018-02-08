package gui;

import javax.swing.*;

import game.GameEngine;

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	
	
	public GUI() {
		super(WINDOW_TITLE);
		makeFrame();
	}
	public void makeFrame() {
		JFrame frame =  new JFrame();
		makeMenu(frame);
		frame.add(new GameEngine());
		frame.pack();
		frame.setSize(800, 800);
		frame.setResizable(false);
	    frame.setLocationRelativeTo(null); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
	public void makeMenu(JFrame frame) {
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
	}
	
}
