package game;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import static java.util.concurrent.TimeUnit.*;
import controller.ActionHandler;
import gui.Background;
import gui.GUI;

/**
 * Generates the game
 * 
 * @author Albin Segestam,�ke Svensson, Markus Saarij�rvi, Erik Tallbacka, Theo Haugen
 * @version 2018-02-27
 */

public class GameEngine extends JPanel implements Runnable {
	
	private static class State implements Serializable {
		public ArrayList<Ship> activeObjects;
		public ArrayList<Projectiles> projectiles;
		public ArrayList<Rock> rocks;
		private Player player;
		private double xPos;
		private double yPos;
		private int score;
	}
	
    private Background backgroundOne;
	private Background backgroundTwo;
  	private BufferedImage back;
	public ArrayList<Ship> activeObjects;
	public ArrayList<Projectiles> projectiles;
	public ArrayList<Rock> rocks;
	private Player player;
	private Projectiles projectile;
	private Thread gameloop;
	private int score = 0;
	public boolean running;
	private boolean backGroundvisible;
	private GUI gui;
	ScheduledThreadPoolExecutor eventPool;
	private State state = new State();
	
	public GameEngine(GUI gui) {
        backgroundOne = new Background();
		backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		rocks = new ArrayList<Rock>();
		backGroundvisible = true;
		this.gui = gui;
		eventPool = new ScheduledThreadPoolExecutor(5);
		running = true;
        setVisible(true);
        setFocusable(true);
        gameInit();
        addKeyListener(new ActionHandler(player, this));
        setDoubleBuffered(true);
	}
	/**
	 * Creates the player and adds the threads for enemies and meteors
	 * Starts the game thread
	 */
	public void gameInit() {
	        player = new Player(0,0,0,0,5,"test");
	        addThreads();
	        if(gameloop == null) {
	        	gameloop = new Thread(this);
	        	gameloop.start();
	        }
	 }
	/**
	 * Draws the scrolling background
	 * @param g
	 */
	//Draw the background
	public void drawBackground(Graphics g) {
        Graphics2D twoD = (Graphics2D)g;
        if (back == null)
            back = (BufferedImage)(createImage(getWidth(), getHeight()));
        // Create a buffer to draw to
        Graphics buffer = back.createGraphics();
        // Put the two copies of the background image onto the buffer
        backgroundOne.draw(buffer);
        backgroundTwo.draw(buffer);
        // Draw the image onto the window
        if(backGroundvisible) {
        	twoD.drawImage(back, null, 0, 0);	
        }
 
    }
	 /**
	  * Draws the player
	  * @param g
	  */
	 public void drawPlayer(Graphics g) {
		 if(player.isVisible()) {
			 g.drawImage(player.getImage(),(int) player.getxPos(),(int) player.getyPos(), this); 
			 }
		 }
	/**
	 * Draws all enemy objects in the array
	 * @param g
	 */
	 public void drawEnemies(Graphics g) {
		 for(Ship s : activeObjects) {
			 Ship shot = s.getShip();
			 if(shot.isVisible()) {
				 g.drawImage(shot.getImage(), (int)shot.getxPos(),(int)shot.getyPos(), this);	 
			 }
		 }
	 }
	 /**
	  * Draws all the projectiles in the array
	  * @param g
	  */
	 public void drawShot(Graphics g) {
		 for(Projectiles p : projectiles) {
			 Projectiles shot = p.getProjectile();
			 if(shot.isVisible()) {
				 g.drawImage(shot.getImage(), (int) shot.getxPos(), (int) shot.getyPos(), this);
			 }
		 }
	 }
	 /**
	  * Draws all the rocks in the array
	  * @param g
	  */
	 public void drawRocks(Graphics g) {
		 for(Rock r : rocks) {
			 Rock rock = r.getRock();
			 if(rock.isVisible()) {
				 g.drawImage(rock.getImage(),(int) rock.getxPos(),(int) rock.getyPos(), this); 
			 }
		 }
	 }
	/**
	 * Paints all the game objects if the game is running
	 */
	 @Override
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if(running) {
	        	drawBackground(g);
	        	drawPlayer(g);
	        	drawEnemies(g);
	        	drawShot(g);  
	        	drawRocks(g);
	        }
	        Toolkit.getDefaultToolkit().sync();
	        g.dispose();
	 }
	 /**
	  * Creates a projectile object and adds it so a array
	  * @param x position in x-axis
	  * @param y position in y-axis
	  * @param dx directional speed x-axis
	  * @param dy directional speed y-axis
	  * @param img the visual appearance
	  * @param hostile if the projectile is a enemy projectile or player
	  */
	 	 public void addProjectile(double x,double y,double dx, double dy,String img, boolean hostile) {
		 projectile = new Projectiles(x,y,dx,dy,img,hostile);
		 projectiles.add(projectile);	
		}

	/**
	 * Updates the game on a short delay
	 */
	@Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (running) {
            repaint();
            update();
            collisionDetection();
            outOfBound();
            fiendeSottTimer.start();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = 15 - timeDiff;
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
	/**
	 * Paints the gameover screen with buttons
	 */
	public void gameOver() {
		//Shutdowns the threadpool
		eventPool.shutdownNow();
		gui.makeGameOverScreen(score);
	}
	/**
	 * Updates the position of each object in the game
	 */
	public void update() {
		if(player.getHitPoints() == 0) {
			running = false;
			gameOver();
		}
		player.move();
		//For each projectile in the array, move it
		for(Projectiles p : projectiles) {
			 Projectiles shot = p.getProjectile();
			 shot.move();
		}
		//For each enemy ship, move it
		for(Ship s : activeObjects) {
			 Ship s2 = s.getShip();
			 s2.move();
			 
		}
		//For each enemy ship, move it
		for(Rock r : rocks) {
			 Rock r2 = r.getRock();
			 r2.move();
		}
	}
	//fiende skott
	ActionListener fiende_skott = new ActionListener() {
		 public void actionPerformed(ActionEvent evt) {
             int min = 0;
             	int max = activeObjects.size()-1;
             	int random = new Random().nextInt(max + 1 - min) + min;
             	Ship s = activeObjects.get(random);
             	
					Random rX = new Random();
					double rangeMinX = -2.5;
					double rangeMaxX = -1.5;
					double dx = rangeMinX + (rangeMaxX - rangeMinX) * rX.nextDouble();
					
					Random rY = new Random();
					double rangeMinY = -0.1;
					double rangeMaxY = 0.1;
					double dy = rangeMinY + (rangeMaxY - rangeMinY) * rY.nextDouble();
					
					ImageIcon imgI = new ImageIcon("img/shot.png");
					
			 addProjectile(s.getxPos()-s.getWidth()- imgI.getImage().getWidth(null),s.getyPos()-(s.getLenght()/2),dx,dy,"space-wars/img/shot2.png",true);
			 
			 
         }
	};
	
		ActionListener fiende_Stuts = new ActionListener() {
			 public void actionPerformed(ActionEvent evt) {
	             
				 for(int i = 0; i < activeObjects.size(); i++) {
						Ship s = activeObjects.get(i);
						s.moveEnemy();
				 }
	         }
		};
	
	Timer fiendeStuts =new Timer (1500,fiende_Stuts);
	Timer fiendeSottTimer = new Timer(250,fiende_skott);
	/**
	 * Checks all objects for collision and removes them if true
	 */
	public void collisionDetection() {
		if(!activeObjects.isEmpty() && !projectiles.isEmpty()) {
			for(int j = 0; j < projectiles.size(); j ++) {
				Projectiles p = projectiles.get(j);
				if (p.isHostile() == false) {
					for(int i = 0; i < activeObjects.size(); i++) {
						Ship s = activeObjects.get(i);
						//enemy and projectile
						if(enemyHit(p,s)) {
							score += 10;
							System.out.println("Score " + score);
							activeObjects.remove(s);
							projectiles.remove(p);
							break;
						}
					}
				}
				//player and projectile
				else if(playerHit(p,player)) {
					projectiles.remove(p);
					player.setHitPoints(player.getHitPoints()-1);
				}
			}
		}
	}
	/**
	 * Checks if player and projectile have collide
	 * @param p the projectile
	 * @param player the player
	 * @return if collision has occured
	 */
	public boolean playerHit(Projectiles p, Player player) {
		if((p.getyPos()+(p.getLenght()/2) >= player.getyPos() && p.getyPos()+(p.getLenght()/2) <= player.getyPos() + player.getLenght())
				&& (p.getxPos()+(p.getWidth()*0.5) >= player.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= player.getxPos()+ player.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks if the enemy ship and projectile have collided
	 * @param p projectile to be checked
	 * @param s ship to be checked
	 * @return if collision has occured
	 */
	private boolean enemyHit(Projectiles p, Ship s) {
		if((p.getyPos()+(p.getLenght()/2) >= s.getyPos() && p.getyPos()+(p.getLenght()/2) <= s.getyPos() + s.getLenght())
				&& (p.getxPos()+(p.getWidth()*0.5) >= s.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= s.getxPos()+ s.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks if the game objects have gone past the game frame
	 */
	public void outOfBound() {
		//player
		if(player.getxPos() < 0) {
			player.setxPos(1);
		}
		else if(player.getxPos() >= 1280 - player.getWidth()) {
			player.setxPos(1279 - player.getWidth());
		}
		else if(player.getyPos() < 0) {
			player.setyPos(1);
		}
		else if(player.getyPos() >= 720 - player.getLenght()) {
			player.setyPos(719 - player.getLenght());
		}
		
		//skepp
		for(int i = 0; i < activeObjects.size(); i++) {
			Ship s = activeObjects.get(i);
			
			if(s.getxPos() < -s.getWidth()) {
				activeObjects.remove(s);
			}
			else if(s.getxPos() >= 1280) {
				s.setxPos(1279);
			}
			else if(s.getyPos() < 0) {
				double speedy = s.getySpeed();
				speedy = -speedy;
				s.setySpeed(speedy);
			}
			else if(s.getyPos() >= 720 - s.getLenght()) {
				double speedy = s.getySpeed();
				speedy = -speedy;
				s.setySpeed(speedy);
			}
		}
		
		//projectiles
		for(int i = 0; i < projectiles.size(); i++) {
			Projectiles s = projectiles.get(i);
			
			if(s.getxPos() < -2) {
				projectiles.remove(s);
			}
			else if(s.getxPos() >= 1280 - s.getWidth()) {
				projectiles.remove(s);
			}
			else if(s.getyPos() <= 0) {
				projectiles.remove(s);
			}
			else if(s.getyPos() >= 720 - s.getLenght()) {
				projectiles.remove(s);
			}
		}
	}
	
	/**
	 * Adds threads to a eventpool
	 */
	private void addThreads() {
		 
		 //Spawns enemy ships every x seconds
		 eventPool.scheduleAtFixedRate(new ShipMaker(this), 0, 700, MILLISECONDS);
		 eventPool.scheduleAtFixedRate(new RockMaker(this), 0, 5, SECONDS);

	}
	
	public void save(String fileName) {
		
		state.activeObjects = activeObjects;
		state.projectiles = projectiles;
		state.rocks = rocks;
		state.player = player;
		state.xPos = player.getxPos();
		state.yPos = player.getyPos();
		state.score = score;
		
		try {
			ObjectOutputStream out = 
					new ObjectOutputStream(
							new FileOutputStream(fileName));
			
			out.writeObject(state);
			out.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadGame(String fileName) {
		try {
			ObjectInputStream in = 
					new ObjectInputStream(
							new FileInputStream(fileName));
			
			state = (State) in.readObject();
			in.close();
			
			activeObjects = state.activeObjects;
			projectiles = state.projectiles;
			rocks = state.rocks;
			player = state.player;
			player.setxPos(state.xPos);
			player.setyPos(state.yPos);
			score = state.score;
			
			player.setPlayerImage();
			addKeyListener(new ActionHandler(player, this));
			
			for(Ship s : activeObjects) {
				s.setShipImage();
			}
			for(Projectiles p : projectiles) {
				p.setProjectilesImage();
			}
			for(Rock r : rocks) {
				r.setRockImage();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void newGame(String name) {

	}

	public void generateScore() {

	}

}

