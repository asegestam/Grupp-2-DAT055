package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import game.GameEngine;
import server.Client;

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	JFrame gameFrame;
	public GUI() {
		super(WINDOW_TITLE);
		makeStartMenu();
	}
	//Creates a frame with buttons to start, load and exit the game
	public void makeStartMenu() {
		JFrame frame =  new JFrame("Space-Wars");
		GridLayout gl = new GridLayout(2,2);
		frame.setSize(1280, 720);
		frame.setLayout(gl);
		frame.setResizable(false);
		JButton startButton = new JButton("Start Game");
		JButton loadButton = new JButton("Load Game");
		JButton exitButton = new JButton("Exit Game");
		JButton hsButton = new JButton("HighScores");
		
		startButton.setPreferredSize(new Dimension(250,50));
		loadButton.setPreferredSize(new Dimension(250,50));
		exitButton.setPreferredSize(new Dimension(250,50));
		hsButton.setPreferredSize(new Dimension(250,50));
		
		frame.add(startButton);
		frame.add(loadButton);
		frame.add(exitButton);
		frame.add(hsButton);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); 
		
        startButton.addActionListener(
        (ActionEvent e)->{makeGameFrame(); frame.dispose();;});
        exitButton.addActionListener(
        (ActionEvent e)->{System.exit(0);;});
        hsButton.addActionListener(
        (ActionEvent e)->{writeHighScores();});
	}
	//Creates the game frame
	public void makeGameFrame() {
		gameFrame =  new JFrame("Space-Wars");
		makeMenu(gameFrame);
		gameFrame.add(new GameEngine(this));
		gameFrame.pack();
		gameFrame.setSize(1280, 720);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null); 
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true); 
	}
	//Creates the menubar
	public void makeMenu(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		//Options menu
		JMenu menu = new JMenu("Options");
		JMenuItem menuItem = new JMenuItem("text");
		menuBar.add(menu);
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
	public JFrame getFrame() {
		
			return gameFrame;
		}
	
	private static Map<String, Integer> sortByValue(Map<String, Integer> highScores) {

        //Convert Map to List of Map
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(highScores.entrySet());

        //Sort list with Collections.sort and create a custom Comparator
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        //Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
	
	public void writeHighScores() {
		Client client = new Client("127.0.0.1", 8081);
		Map <String, Integer> map = sortByValue(client.getHighScore());
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
