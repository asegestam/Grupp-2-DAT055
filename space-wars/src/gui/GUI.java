package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.*;
import java.util.*;
import game.GameEngine;
import server.Client;

/**
 * Creates the Game GUI
 * Used to create start menu, game frame and game-over screen
 * @author Albin Segestam,Markus Saarijärvi, Erik Tallbacka
 * @version 2018-03-02
 */

public class GUI extends JFrame {
	
	private final static String WINDOW_TITLE = "Space-Wars";
	JFrame gameFrame;
	private Client client;
	private GameEngine game;
	/**
	 * Constructor creates the start menu and starts a connection to the client
	 */
	public GUI() {
		super(WINDOW_TITLE);
		makeStartMenu();
		client = new Client("127.0.0.1", 8081);
	}
	/**
	 * Creates a frame with buttons to start, load and exit the game
	 * Also a highscore button to view the highscore list
	 */
	public void makeStartMenu() {
		JFrame frame =  new JFrame("Space-Wars");
		GridLayout gl = new GridLayout(3,1);
		frame.setSize(1280, 720);
		frame.setLayout(gl);
		frame.setResizable(false);
		JButton startButton = new JButton("Start Game");
		JButton exitButton = new JButton("Exit Game");
		JButton hsButton = new JButton("HighScores");
		
		startButton.setPreferredSize(new Dimension(250,50));
		exitButton.setPreferredSize(new Dimension(250,50));
		hsButton.setPreferredSize(new Dimension(250,50));
		
		frame.add(startButton);
		frame.add(exitButton);
		frame.add(hsButton);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null); 
		//Calls for the game frame to be created, removes this frame
        startButton.addActionListener(
        (ActionEvent e)->{makeGameFrame(); frame.dispose();;});
        //Exits the game
        exitButton.addActionListener(
        (ActionEvent e)->{System.exit(0);;});
        //Prints highscores
        hsButton.addActionListener(
        (ActionEvent e)->{printHighScores();});
        exitButton.addActionListener(
                (ActionEvent e)->{System.exit(0);;});
	}
	/**
	 * Creates the game frame and in turn starts the game
	 */
	public void makeGameFrame() {
		gameFrame =  new JFrame("Space-Wars");
		makeMenu(gameFrame);
		gameFrame.add(game = new GameEngine(this));
		gameFrame.pack();
		gameFrame.setSize(1280, 720);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null); 
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true); 
	}
	/**
	 * Draws the game-over screen with buttons.
	 * Buttons to start new game,load game, exit game, submit score and view scores.
	 * @param score the player's final score, used to submit to the server
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
		gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(64.0f));
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(48.0f));
		frame.add(gameOverLabel,gbc);
		frame.add(scoreLabel,gbc);

		JButton startButton = new JButton("New Game");
		JButton exitButton = new JButton("Exit Game");
		JButton hsButton = new JButton("HighScores");
		JButton scoreButton = new JButton("Submit Score");

		startButton.setPreferredSize(new Dimension(250,50));
		exitButton.setPreferredSize(new Dimension(250,50));
		hsButton.setPreferredSize(new Dimension(250,50));
		scoreButton.setPreferredSize(new Dimension(250,50));
		
		frame.add(startButton,gbc);
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
		        (ActionEvent e)->{printHighScores();});
		//Submits score
		scoreButton.addActionListener(
		        (ActionEvent e)->{String inputName = JOptionPane.showInputDialog("Please Enter a Name");client.addHighScore(inputName, score);});
	}
	/**
	 * Creates a menubar
	 * @param frame the frame being used
	 */
	public void makeMenu(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		//HighScore menu
		JMenu menu = new JMenu("HighScores");
		JMenuItem menuItem = new JMenuItem("List of highscores");
		frame.setJMenuBar(menuBar);
		menuBar.add(menu);
		menu.add(menuItem);
		menuItem.addActionListener((ActionEvent e)->{printHighScores();});
		//Load/Save 
		menu = new JMenu("Game Options");
		menuBar.add(menu);
		menuItem = new JMenuItem("Save Game");
		menu.add(menuItem);
		menuItem.addActionListener((ActionEvent e)->{String input = JOptionPane.showInputDialog("Provide file name"); game.saveGame(input);});
		
		
		menuItem = new JMenuItem("Load Game");
		menu.add(menuItem);
		menuItem.addActionListener((ActionEvent e)->{String input = JOptionPane.showInputDialog("Provide file name"); game.loadGame(input);});
		menuItem = new JMenuItem("Exit Game");
		menu.add(menuItem);
		menuItem.addActionListener((ActionEvent e)->{System.exit(0);});
	}
	/**
	 * Sorts the given collection of values
	 * @param highScores the collection which to sort
	 * @return the sorted collection
	 */
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
        //Loop the sorted list and put it into a new insertion order Map LinkedHashMapss
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
	/**
	 * Prints the sorted highscore list in a OptionPane
	 */
	public void printHighScores() {
		Map <String, Integer> map = sortByValue(client.getHighScore());
		String highScoreString = "";
		for (Map.Entry<String, Integer> entry : map.entrySet())
		{
			highScoreString += entry.getKey() + ":" + entry.getValue() + "\n";
		}
		JOptionPane.showMessageDialog(this,highScoreString,"HighScores",JOptionPane.PLAIN_MESSAGE);
	}
	/**
	 * Returns the frame
	 * @return JFrame the active frame
	 */
	public JFrame getFrame() {
		
		return gameFrame;
	}
}
