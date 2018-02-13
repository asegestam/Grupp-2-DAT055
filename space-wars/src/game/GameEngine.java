package game;
import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import controller.ActionHandler;
import gui.Background;

public class GameEngine extends JPanel implements Runnable{
	private Background backgroundOne;
    private Background backgroundTwo;
    private BufferedImage back;
	private ArrayList<Ship> activeObjects;
	private Player player;
	private Ship enemy;
	private Projectiles projectile;
	private Thread gameloop;
	public ArrayList<Projectiles> projectiles;
	private int score = 0;
	private int playerX;
	private int playerY;
	
	
	public GameEngine() {
		backgroundOne = new Background();
        backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
        setVisible(true);
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
        setFocusable(true);
        gameInit();
        addKeyListener(new ActionHandler(player, this));
        setDoubleBuffered(true);
	}
    @Override
    public void addNotify() {
        super.addNotify();
    }
    //Creates the player, enemies and starts the thread
	public void gameInit() {
	        player = new Player(0,0,0,0,"test");
	        	for(int j = 0; j < 6; j++) {
	        		Ship ship = new Ship(1000, (50 + 114 *j), 0 , 0);
		        	activeObjects.add(ship);
	        	}
	        if(gameloop == null) {
	        	gameloop = new Thread(this);
	        	gameloop.start();
	        }
	 }
	public void paintBackground(Graphics g) {
        Graphics2D twoD = (Graphics2D)g;
 
        if (back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));
 
        // Create a buffer to draw to
        Graphics buffer = back.createGraphics();
 
        // Put the two copies of the background image onto the buffer
        backgroundOne.draw(buffer);
        backgroundTwo.draw(buffer);
 
        // Draw the image onto the window
        twoD.drawImage(back, null, 0, 0);
 
    }
	 //Draws the player
	 public void drawPlayer(Graphics g) {
			g.drawImage(player.getImage(), player.getxPos(), player.getyPos(), this);
		 }
	//Draws the enemies
	 public void drawEnemies(Graphics g) {
		 for(Ship s : activeObjects) {
			 Ship s2 = s.getShip();
			 g.drawImage(s2.getImage(), s2.getxPos(),s2.getyPos(), this);
		 }
	 }
	 //Draws the projectiles
	 public void drawShot(Graphics g) {
		 for(Projectiles p : projectiles) {
			 Projectiles shot = p.getProjectile();
			 g.drawImage(shot.getImage(), shot.getxPos(), shot.getyPos(), this);
		 }
	 }
	 //Paints the player,enemies, and the projectiles
	 @Override
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        paintBackground(g);
	        drawPlayer(g);
	        drawEnemies(g);
	        drawShot(g);  
	        Toolkit.getDefaultToolkit().sync();
	        g.dispose();
	 }
	 //Used to add the projectiles to the array
	 public void addProjectile() {
		 projectile = new Projectiles(playerX,playerY,5,0);
		 projectiles.add(projectile);	
		}
	 

	//Updates and repaints the panel on a delay
	@Override
    public void run() {
		int x = player.getxPos();
		int y = player.getyPos();
        long beforeTime, timeDiff, sleep;
        System.out.println("x: " + x + "y: " + y);
        beforeTime = System.currentTimeMillis();
        while (true) {
            repaint();
            update();
            collisionDetection();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 5 - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }
            beforeTime = System.currentTimeMillis();
            
        }
    }
	public void update() {
		player.move();
		//Update the x and y position of the player for the projectiles
		playerX = player.getxPos();
		playerY = player.getyPos();
		//For each projectile in the array, move it
		for(Projectiles p : projectiles) {
			 Projectiles shot = p.getProjectile();
			 shot.move();
		}
		//For each enemy ship, move it
		for(Ship s : activeObjects) {
			 Ship s2 = s.getShip();
			 s2.bounce();
			 s2.moveEnemy();
			 s2.move();
		}
	}

	public void collisionDetection() {
		if(!activeObjects.isEmpty() && !projectiles.isEmpty()) {
		for(int j = 0; j < projectiles.size(); j ++) {
			Projectiles p = projectiles.get(j);

			for(int i = 0; i < activeObjects.size(); i++) {
				Ship s = activeObjects.get(i);

				
				if((p.getyPos()+(p.getLenght()/2) >= s.getyPos() && p.getyPos()+(p.getLenght()/2) <= s.getyPos() + s.getLenght())
						&& (p.getxPos()+p.getWidth() >= s.getxPos() && p.getxPos()+p.getWidth() <= s.getxPos()+ s.getWidth())) {
					
					activeObjects.remove(s);
					projectiles.remove(p);
					}
				}
				
				
			}
		}
	}

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}

}
