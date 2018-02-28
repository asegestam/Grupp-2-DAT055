package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import game.GameEngine;
import server.Client;
/**
 * Generates the game
 * 
 * @author Albin Segestam,�ke Svensson, Markus Saarij�rvi, Erik Tallbacka, Theo Haugen
 * @version 2018-02-27
 */

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	JFrame gameFrame;
	private Client client;
	private GameEngine game;
	
	public GUI() {
		super(WINDOW_TITLE);
		client = new Client("127.0.0.1", 8081);
		game = new GameEngine(this);
		makeStartMenu();
	}
	/**
	 * Creates a start menu with start button, highscore button, exit button and load button
	 */
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
	/**
	 * Creates the game
	 */
	public void makeGameFrame() {
		gameFrame =  new JFrame("Space-Wars");
		makeMenu(gameFrame);
		gameFrame.add(game);
		gameFrame.pack();
		gameFrame.setSize(1280, 720);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null); 
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true); 
	}
	/**
	 * Creates a menubar
	 * @param frame
	 */
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
		//Game menu
		menu = new JMenu("Save/Load");
		menuBar.add(menu);
		menuItem = new JMenuItem("Save Game");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);
		menuItem.addActionListener((ActionEvent e)->{String input = JOptionPane.showInputDialog("Provide file name"); game.save(input);});
		menuItem = new JMenuItem("Load Game");
		menu.add(menuItem);
		frame.setJMenuBar(menuBar);
		menuItem.addActionListener((ActionEvent e)->{String input = JOptionPane.showInputDialog("Provide file name"); game.loadGame(input);});
	}
	/**
	 * Draws the game over screen with buttons and score
	 * @param score the players score
	 */
	public void makeGameOverScreen(int score) {
		JFrame frame = getFrame();
		frame.setLayout(new GridBagLayout()); 
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
		JLabel gameOverLabel = new JLabel("Game Over");
		JLabel scoreLabel = new JLabel("Your Score: " + Integer.toString(score));
		gameOverLabel.setForeground(Color.red);
		gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(72.0f));
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(48.0f));
		frame.add(gameOverLabel,gbc);
		frame.add(scoreLabel,gbc);

		JButton startButton = new JButton("New Game");
		JButton loadButton = new JButton("Load Game");
		JButton exitButton = new JButton("Exit Game");
		JButton hsButton = new JButton("HighScores");
		JButton scoreButton = new JButton("Submit Score");

		startButton.setPreferredSize(new Dimension(250,50));
		loadButton.setPreferredSize(new Dimension(250,50));
		exitButton.setPreferredSize(new Dimension(250,50));
		hsButton.setPreferredSize(new Dimension(250,50));
		scoreButton.setPreferredSize(new Dimension(250,50));
		
		frame.add(startButton,gbc);
		frame.add(loadButton,gbc);
		frame.add(exitButton,gbc);
	    frame.add(hsButton,gbc);
	    frame.add(scoreButton,gbc);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
		//Creates a new game
        startButton.addActionListener(
        (ActionEvent e)->{makeGameFrame(); frame.dispose();;});
        //Exits the game
		 exitButton.addActionListener(
			        (ActionEvent e)->{System.exit(0);;});
		//Prints highscores
		hsButton.addActionListener(
		        (ActionEvent e)->{writeHighScores();});
		scoreButton.addActionListener(
		        (ActionEvent e)->{String inputName = JOptionPane.showInputDialog("Please Enter a Name");client.addHighScore(inputName, score);});
	}
	
	public JFrame getFrame() {
		
			return gameFrame;
		}
	
	/**
	 * Prints the score list
	 */
	public void writeHighScores() {
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
