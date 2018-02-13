package game;

import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

public class GameEngine extends JPanel implements ActionListener, KeyListener{

	private ArrayList<Obj> activeObjects;
	private int score = 0;
	private Player player;
	Timer t = new Timer(1, this);
	
	public GameEngine() {
		activeObjects = new ArrayList<Obj>();
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
        gameInit();
        t.start();
	}
	
	 public void gameInit() {
	        player = new Player(100,100,0,0,"test");
	        System.out.println(player.getyPos());
	 }
	 
	 
	 @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        drawPlayer(g);
	 }
	 
	 public void drawPlayer(Graphics g) {
			g.drawImage(player.getImage(), player.getxPos(), player.getyPos(), this);
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
	
	

	@Override
	public void keyPressed(KeyEvent e) {
		

		int c = e.getKeyCode();
		
		if(c == KeyEvent.VK_LEFT)
		{
			player.setxSpeed(-5);
			player.setySpeed(0);
		}
		if(c == KeyEvent.VK_RIGHT)
		{
			player.setxSpeed(5);
			player.setySpeed(0);
		}
		if(c == KeyEvent.VK_UP)
		{
			player.setxSpeed(0);
			player.setySpeed(-5);
		}
		if(c == KeyEvent.VK_DOWN)
		{
			player.setxSpeed(0);
			player.setySpeed(5);
		}	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		player.setxSpeed(0);
		player.setySpeed(0);
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		player.move();
		repaint();
		
	}
	
	
}
