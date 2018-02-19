package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;
import game.GameEngine;
import server.Client;

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
		frame.setSize(1920, 1080);
		frame.setResizable(false);
	    frame.setLocationRelativeTo(null); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	}
	public void makeMenu(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		//Options menu
		JMenu menu = new JMenu("Options");
		menuBar.add(menu);
		JMenuItem menuItem = new JMenuItem("text");
		menu.add(menuItem);
		//Settings menu
		menu = new JMenu("Settings");
		menuBar.add(menu);
		menuItem = new JMenuItem("text");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);
		//HighScore menu
		menu = new JMenu("HighScores");
		menuBar.add(menu);
		menuItem = new JMenuItem("List of highscores");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);
		menuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					writeHighScores();
				}
			});
	}
	
	public void writeHighScores() {
		Client client = new Client("127.0.0.1", 8081);
		Map <String, Integer> map = client.getHighScore();
		String highScoreString = "";
		for (Map.Entry<String, Integer> entry : map.entrySet())
		{
			highScoreString += entry.getKey() + ":" + entry.getValue() + "\n";
		}
		
		JOptionPane.showMessageDialog(this,
			    highScoreString,
			    "HighScores",
			    JOptionPane.PLAIN_MESSAGE);
	}
	
}
