package game;

import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

public class GameEngine {

	private ArrayList<Obj> activeObjects;
	private int score = 0;
	private Player player;
	private JFrame frame;

	public GameEngine(String name) {
		activeObjects = new ArrayList<Obj>();
		player = new Player(name, 40, 40);
		makeFrame();
	}

	public static void main(String[] args) {
		
		JFrame frame =  new JFrame();
		Container contentPane = frame.getContentPane();
		frame.pack();
		frame.setSize(300, 300);
		frame.setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2); 
		
	    contentPane.setLayout(new GridLayout(4,4,3,3));
	    
	    for(int i = 0; i < 16; i++)
	       {   
	           JButton button = new JButton(Integer.toString(i));
	           
	           button.addActionListener(
	            (ActionEvent a)->{actionHandler(a);}
	           );
	           
	           contentPane.add(button);
	           
	       }
	    
	    frame.setVisible(true);
		
	}
	
	private static void actionHandler(ActionEvent a){
        
           Toolkit.getDefaultToolkit().beep();
        
    }


	public void makeFrame() {
		frame =  new JFrame();
		Container contentPane = frame.getContentPane();
		frame.pack();
		frame.setSize(300, 300);
		frame.setResizable(false);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2); 
		
	    contentPane.setLayout(new GridLayout(4,4,3,3));
	    
	    frame.setVisible(true);
		
	}

	public void update() {

	}

	public boolean collisionDetection() {
		return false;
	}

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}
}
