package gui;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.*;
import game.GameEngine;

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	
	public GUI() {
		super(WINDOW_TITLE);
		makeStartMenu();
	}
	//Creates a frame with buttons to start, load and exit the game
	public void makeStartMenu() {
		JFrame frame =  new JFrame("Space-Wars");
		GridLayout gl = new GridLayout(3,1);
		frame.setSize(1280, 720);
		frame.setLayout(gl);
		frame.setResizable(false);
		JButton startButton = new JButton("Start Game");
		JButton loadButton = new JButton("Load Game");
		JButton exitButton = new JButton("Exit Game");
		
		startButton.setPreferredSize(new Dimension(250,50));
		loadButton.setPreferredSize(new Dimension(250,50));
		exitButton.setPreferredSize(new Dimension(250,50));
		
		frame.add(startButton);
		frame.add(loadButton);
		frame.add(exitButton);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); 
		
        startButton.addActionListener(
        (ActionEvent e)->{makeGameFrame(); frame.dispose();;});
        exitButton.addActionListener(
        (ActionEvent e)->{System.exit(0);;});
	}
	//Creates the game frame
	public void makeGameFrame() {
		JFrame frame =  new JFrame("Space-Wars");
		makeMenu(frame);
		frame.add(new GameEngine());
		frame.pack();
		frame.setSize(1280, 720);
		frame.setResizable(false);
	    frame.setLocationRelativeTo(null); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
	//Creates the menubar
	public void makeMenu(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Options");
		JMenuItem menuItem = new JMenuItem("text");
		menuBar.add(menu);
		menu.add(menuItem);
		menu = new JMenu("Settings");
		menuBar.add(menu);
		menuItem = new JMenuItem("text");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);
	}
}
