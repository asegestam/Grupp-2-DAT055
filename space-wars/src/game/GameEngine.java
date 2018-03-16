package game;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import static java.util.concurrent.TimeUnit.*;
import controller.ActionHandler;
import gui.Background;
import gui.GUI;
import server.Client;
/**
 * Generates the game
 * 
 * @author Albin Segestam,Åke Svensson, Erik Tallbacka, Theo Haugen
 * @version 2018-03-16
 */

public class GameEngine extends JPanel implements Runnable {
	private static final long serialVersionUID = 8589348462449768141L;
	private GUI gui;
	private Player player;
	private Projectiles projectile;
	private Thread gameloop;
	private ActionHandler playerHandler;
	public CollisionDetector cd;
	public StateSaver sv;
	private ArrayList<Ship> activeShips;
	private ArrayList<Projectiles> projectiles;
	private ArrayList<Meteor> meteors;
	private ArrayList<Boss> bosses;
	private Background backgroundOne;
	private Background backgroundTwo;
  	private BufferedImage back;
	public boolean running;
	private boolean backGroundvisible;
	@SuppressWarnings("unused")
	private Client client;
	ScheduledThreadPoolExecutor eventPool;
	public boolean pause;
	/**
	 * Initiate all necessary fields
	 * Initate the game
	 * @param gui instance of the underlying GUI/Frame
	 */
	public GameEngine(GUI gui) {
		this.gui = gui;
		client = new Client("127.0.0.1", 8081);
		eventPool = new ScheduledThreadPoolExecutor(5);
		activeShips = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		meteors = new ArrayList<Meteor>();
		bosses = new ArrayList<Boss>();
		backgroundOne = new Background();
		backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		backGroundvisible = true;
        setVisible(true);
        setFocusable(true);
        setDoubleBuffered(true);
        pause = false;
        running = true;
		gameInit();
		addPlayerKeyListener(player, this);
	}
	//Creates the player and adds the threads for enemies and meteors and starts the game thread
	private void gameInit() {
	    player = new Player(100,250,0,0,5);
	    cd = new CollisionDetector(this,player);
	    sv = new StateSaver(this);
	    addThreads();
	    if(gameloop == null) {
	    	gameloop = new Thread(this);
	        gameloop.start();
	    }
	}
	//Draws the scrolling background
	private void drawBackground(Graphics g) {
		Graphics2D twoD = (Graphics2D)g;
		if (back == null) {
			back = (BufferedImage)(createImage(getWidth(), getHeight()));
		}
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
	//Draws the player
	private void drawPlayer(Graphics g) {
		 if(player.isVisible()) {
			 g.drawImage(player.getImage(),(int) player.getxPos(),(int) player.getyPos(), this); 
			 }
		 }
	//Draws all the enemy ships in the array
    private void drawEnemies(Graphics g) {
		 Iterator<Ship> iter = activeShips.iterator();
		 while(iter.hasNext()) {
			 Ship ship = (Ship) iter.next().getInstance();
			 if(ship.isVisible()) {
				 g.drawImage(ship.getImage(), (int) ship.getxPos(), (int) ship.getyPos(), this);
			 }
		 }
	 }
	//Draws the boss objects in the array
    private void drawBoss(Graphics g) {
        Iterator<Boss> iter = bosses.iterator();
		while(iter.hasNext()) {
		    Boss boss = (Boss) iter.next().getInstance();
			if(boss.isVisible()) {
			    g.drawImage(boss.getImage(), (int) boss.getxPos(), (int) boss.getyPos(), this);
			}
		}
	}
    //Draws all the projectiles in the array
    private void drawShot(Graphics g) {
		 Iterator<Projectiles> iter = projectiles.iterator();
		 while(iter.hasNext()) {
			 Projectiles shot = (Projectiles) iter.next().getInstance();
			 if(shot.isVisible()) {
				 g.drawImage(shot.getImage(), (int) shot.getxPos(), (int) shot.getyPos(), this);
			 }
		 }
	 }
	//Draws all the rocks in the array
    private void drawRocks(Graphics g) {
	    Iterator<Meteor> iter = meteors.iterator();
		while(iter.hasNext()) {
			Meteor meteor = (Meteor) iter.next().getInstance();
			if(meteor.isVisible()) {
				g.drawImage(meteor.getImage(), (int) meteor.getxPos(), (int) meteor.getyPos(), this);
			}
		}
	}
	/**
	 * Paints all the game objects onto the panel
	 * @param g what object to draw on, in this case the panel
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
	         drawBoss(g);
	     }
	     Toolkit.getDefaultToolkit().sync();
	     g.dispose();
	 }
	 /**
	  * Adds a projectile with given x,y,speed and image to the projectile array
	  * @param x postion in x-axis
	  * @param y position in y-axis
	  * @param dx speed in x-axis
	  * @param dy speed in y-axis
	  * @param img image for the design
	  * @param hostile if the projectile is harmful for the player
	  */
	 public void addProjectile(double x,double y,double dx, double dy,URL img, boolean hostile) {
		 projectile = new Projectiles(x,y,dx,dy,img,hostile);
		 projectiles.add(projectile);	
		}
	//Adds a KeyListener to the player object
    private void addPlayerKeyListener(Player player, GameEngine game) {
	    if(playerHandler == null) {
	        playerHandler = new ActionHandler(player, this);
		    addKeyListener(playerHandler);
		}
	    else {
		    playerHandler.updateHandler(player, this);
		}
    }

	/**
	 * Updates the game based on timer sleep
	 */
	@Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (running) {
            repaint();
            update();
            cd.collisionDetection();
            cd.outOfBound();
            enemyShootTimer.start();
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
	//Updates the game state.
	private void update() {
		if(!pause) {
			if(player.getHitPoints() == 0) {
				running = false;
				gameOver();
			}
			player.move();
			//For each projectile in the array, move it
			Iterator<Projectiles> projIter = projectiles.iterator();
			while(projIter.hasNext()) {
				Projectiles shot = (Projectiles) projIter.next().getInstance();
				shot.move();
			}
			//For each enemy ship, move it
			Iterator<Ship> shipIter = activeShips.iterator();
			while(shipIter.hasNext()) {
				Ship ship = (Ship) shipIter.next().getInstance();
				ship.move();
			}
			
			Iterator<Meteor> metIter = meteors.iterator();
			while(metIter.hasNext()) {
				Meteor meteor = (Meteor) metIter.next().getInstance();
				meteor.move();
			}
			
			Iterator<Boss> bossIter = bosses.iterator();
			while(bossIter.hasNext()) {
				Boss boss = (Boss) bossIter.next().getInstance();
				boss.move();
			}
		}
	}
	//Stops the background threads and calls for a game over screen
	private void gameOver() {
		//Shutdowns the threadpool
		eventPool.shutdownNow();
		gui.makeGameOverScreen(cd.getScore());
	}
	/**
	 * Enemies shoot at random directions on a timer
	 */
	ActionListener enemy_shoot = new ActionListener() {
		 public void actionPerformed(ActionEvent evt) {
			 if(!pause) {
				 if(!activeShips.isEmpty()) {
					 int min = 0;
					 int max = activeShips.size()-1;
					 int random = new Random().nextInt(max + 1 - min) + min;
					 Ship s = activeShips.get(random);
					 
					 Random rX = new Random();
					 double rangeMinX = -2.5;
					 double rangeMaxX = -1.5;
					 double dx = rangeMinX + (rangeMaxX - rangeMinX) * rX.nextDouble();
					 
					 Random rY = new Random();
					 double rangeMinY = -0.1;
					 double rangeMaxY = 0.1;
					 double dy = rangeMinY + (rangeMaxY - rangeMinY) * rY.nextDouble();
					 URL shot = getClass().getResource("/shot2.png");
					 ImageIcon imgI = new ImageIcon(shot);
					 addProjectile(s.getxPos() - s.getWidth() - imgI.getImage().getWidth(null),s.getyPos()-(s.getHeight()/2)+20,dx,dy,shot,true);
					 
				 } 
				 if(!bosses.isEmpty()) {
					 for(Boss b1 : bosses) {
						 Boss s = (Boss) b1.getInstance();
						 Random rX = new Random();
						 double rangeMinX = -2.5;
						 double rangeMaxX = -1.5;
						 double dx = rangeMinX + (rangeMaxX - rangeMinX) * rX.nextDouble();
						 
						 Random rY = new Random();
						 double rangeMinY = -1;
						 double rangeMaxY = 1;
						 double dy = rangeMinY + (rangeMaxY - rangeMinY) * rY.nextDouble();
						 URL shot = getClass().getResource("/shot2.png");
						 ImageIcon imgI = new ImageIcon(shot);
						 
						 addProjectile(s.getxPos()- imgI.getImage().getWidth(null),s.getyPos()+(s.getHeight()/2),dx,dy,shot,true);
					 }
				 }
			 }
	    }
	};
	/**
	 * Enemies moves around on a timer
	 */
	ActionListener enemy_bounce = new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		    for(int i = 0; i < activeShips.size(); i++) {
			    Ship s = activeShips.get(i);
				s.moveEnemy();
			}
	    }
	};
	Timer enemyBounce = new Timer (1500,enemy_bounce);
	Timer enemyShootTimer = new Timer(275,enemy_shoot);
	//Adds the ShipMaker and RockMaker threads to a eventpool
	private void addThreads() {
		 //Spawns enemy ships every x seconds
		 eventPool.scheduleAtFixedRate(new ShipMaker(this), 0, 700, MILLISECONDS);
		 eventPool.scheduleAtFixedRate(new MeteorMaker(this), 0, 5, SECONDS);
	}
	/**
	 * Streams the game objects to a file
	 * @param fileName file name of the file being written to
	 */
	public void saveGame(String fileName) {
		sv.saveGame(fileName);
	}
	/**
	 * Loads objects from file into the game
	 * @param fileName file name of the file being read
	 */
	public void loadGame(String fileName) {
		sv.loadGame(fileName);
		player.setxPos(sv.getSavedXPos());
		player.setyPos(sv.getSavedYPos());
		player.setPlayerImage();
		cd.setScore(sv.getSavedScore());
		for(Ship s : activeShips) {
			s.setShipImage();
		}
		for(Projectiles p : projectiles) {
			p.setProjectilesImage();
		}
		for(Meteor m : meteors) {
			m.setMeteorImage();
		}
		setVisible(true);
		update();
		addPlayerKeyListener(player, this);
	}
	/**
	 * Returns the ArrayList containing the ships
	 * @return ArrayList containing active enemy ships
	 */
	public ArrayList<Ship> getActiveShips() {
		return activeShips;
	}
	/**
	 * Returns the ArrayList containing the projectiles
	 * @return ArrayList containing active projectiles
	 */
	public ArrayList<Projectiles> getProjectiles() {
		return projectiles;
	}
	/**
	 * Returns the ArrayList containing the meteors
	 * @return ArrayList containing active meteors
	 */
	public ArrayList<Meteor> getMeteors() {
		return meteors;
	}
	/**
	 * Returns the ArrayList containing bosses
	 * @return ArrayList containing active bosses
	 */
	public ArrayList<Boss> getBosses() {
		return bosses;
	}
	/**
	 * Returns the player object
	 * @return the Player object
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * Returns pause state
	 * @return the pause state
	 */
	public boolean getPause() {
		return pause;
	}
	/**
	 * Returns the score, used when saving score
	 * @return the player's score
	 */
	public int getScore() {
		return cd.getScore();
	}
	/**
	 * Set the pause state
	 * @param paused paused state, if true the game is paused
	 */
	public void setPause(boolean paused) {
		pause = paused;
	}
	/**
	 * Sets the ArrayList to the given ArrayList.
	 * Used when loading a saved game
	 * @param activeShips the ArrayList being loaded
	 */
	public void setActiveShips(ArrayList<Ship> activeShips) {
		this.activeShips = activeShips;
	}
	/**
	 * Sets the player object to the given player object
	 * Used when loading a saved game
	 * @param player player being loaded
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * Sets the ArrayList to the given ArrayList.
	 * Used when loading a saved game
	 * @param projectiles the ArrayList being loaded
	 */
	public void setProjectiles(ArrayList<Projectiles> projectiles) {
		this.projectiles = projectiles;
	}
	/**
	 * Sets the ArrayList to the given ArrayList.
	 * Used when loading a saved game
	 * @param meteors the ArrayList being loaded
	 */
	public void setMeteors(ArrayList<Meteor> meteors) {
		this.meteors = meteors;
	}
	/**
	 * Sets the ArrayList to the given ArrayList.
	 * Used when loading a saved game
	 * @param bosses the ArrayList being loaded
	 */
	public void setBosses(ArrayList<Boss> bosses) {
		this.bosses = bosses;
	}
}

