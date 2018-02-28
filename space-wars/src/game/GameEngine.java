package game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-02-28
 */

public class GameEngine extends JPanel implements Runnable{

	private Background backgroundOne;
	private Background backgroundTwo;
  	private BufferedImage back;
	public ArrayList<Ship> activeShips;
	public ArrayList<Projectiles> projectiles;
	public ArrayList<Meteor> meteors;
	public ArrayList<Boss> bosses;
	private Player player;
	private Projectiles projectile;
	private Thread gameloop;
	private int score = 0;
	public boolean running;
	private boolean backGroundvisible;
	private GUI gui;
	@SuppressWarnings("unused")
	private Client client;
	ScheduledThreadPoolExecutor eventPool;
	
	public GameEngine(GUI gui) {
		backgroundOne = new Background();
		backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		activeShips = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		meteors = new ArrayList<Meteor>();
		bosses = new ArrayList<Boss>();
		backGroundvisible = true;
		client = new Client("127.0.0.1", 8081);
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
	        player = new Player(100,250,0,0,5);
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
	  * Draws all the enemy ships in the array
	  * @param g
	  */
	 public void drawEnemies(Graphics g) {
		 Iterator<Ship> iter = activeShips.iterator();
		 while(iter.hasNext()) {
			 Ship ship = iter.next().getShip();
			 if(ship.isVisible()) {
				 g.drawImage(ship.getImage(), (int) ship.getxPos(), (int) ship.getyPos(), this);
			 }
		 }
	 }
	 /**
	  * Draws the boss objects in the array
	  * @param g
	  */
	 public void drawBoss(Graphics g) {
		 Iterator<Boss> iter = bosses.iterator();
		 while(iter.hasNext()) {
			 Boss boss = iter.next().getBoss();
			 if(boss.isVisible()) {
				 g.drawImage(boss.getImage(), (int) boss.getxPos(), (int) boss.getyPos(), this);
			 }
		 }
	 }
	 
	 /**
	  * Draws all the projectiles in the array
	  * @param g
	  */
	 public void drawShot(Graphics g) {
		 Iterator<Projectiles> iter = projectiles.iterator();
		 while(iter.hasNext()) {
			 Projectiles shot = iter.next().getProjectile();
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
		 Iterator<Meteor> iter = meteors.iterator();
		 while(iter.hasNext()) {
			 Meteor meteor = iter.next().getMeteor();
			 if(meteor.isVisible()) {
				 g.drawImage(meteor.getImage(), (int) meteor.getxPos(), (int) meteor.getyPos(), this);
			 }
		 }
	 }
	
	/**
	 * Paints all the game objects onto the panel
	 * @param g
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
	 public void addProjectile(double x,double y,double dx, double dy,String img, boolean hostile) {
		 projectile = new Projectiles(x,y,dx,dy,img,hostile);
		 projectiles.add(projectile);	
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
            collisionDetection();
            outOfBound();
            fiendeSottTimer.start();
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
	/**
	 * Stops the background threads and calls for a game over screen
	 * @see GUI
	 */
	public void gameOver() {
		//Shutdowns the threadpool
		eventPool.shutdownNow();
		gui.makeGameOverScreen(score);
	}
	/**
	 * Updates the game state.
	 */
	public void update() {
		if(player.getHitPoints() == 0) {
			running = false;
			gameOver();
		}
		player.move();
		//For each projectile in the array, move it
		Iterator<Projectiles> projIter = projectiles.iterator();
		while(projIter.hasNext()) {
			Projectiles shot = projIter.next().getProjectile();
			shot.move();
		}
		//For each enemy ship, move it
		Iterator<Ship> shipIter = activeShips.iterator();
		 while(shipIter.hasNext()) {
			 Ship ship = shipIter.next().getShip();
			 ship.move();
		 }
		 
		 Iterator<Meteor> metIter = meteors.iterator();
		 while(metIter.hasNext()) {
			 Meteor meteor = metIter.next().getMeteor();
			 meteor.move();
		 }
		 
		 Iterator<Boss> bossIter = bosses.iterator();
		 while(bossIter.hasNext()) {
			Boss boss = bossIter.next().getBoss();
			boss.move();
		 }
	}

	

	//fiende skott
	ActionListener fiende_skott = new ActionListener() {
		 public void actionPerformed(ActionEvent evt) {
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
					
				ImageIcon imgI = new ImageIcon("img/shot2.png");
				addProjectile(s.getxPos()-s.getWidth()- imgI.getImage().getWidth(null),s.getyPos()-(s.getLenght()/2),dx,dy,"img/shot2.png",true);
			 
			 } 
			 if(!bosses.isEmpty()) {
				 for(Boss b1 : bosses) {
					 Boss s = b1.getBoss();
					 Random rX = new Random();
					 double rangeMinX = -2.5;
					 double rangeMaxX = -1.5;
					 double dx = rangeMinX + (rangeMaxX - rangeMinX) * rX.nextDouble();
					
					 Random rY = new Random();
					 double rangeMinY = -1;
					 double rangeMaxY = 1;
					 double dy = rangeMinY + (rangeMaxY - rangeMinY) * rY.nextDouble();
				     ImageIcon imgI = new ImageIcon("img/shot2.png");
					
				     addProjectile(s.getxPos()- imgI.getImage().getWidth(null),s.getyPos()+(s.getLenght()/2),dx,dy,"img/shot2.png",true);
				 
			     }
			 }
         }
	};
	
	ActionListener enemy_bounce = new ActionListener() {
	    public void actionPerformed(ActionEvent evt) {
		    for(int i = 0; i < activeShips.size(); i++) {
			    Ship s = activeShips.get(i);
				s.moveEnemy();
			}
	    }
	};
	
	Timer fiendeStuts =new Timer (1500,enemy_bounce);
	Timer fiendeSottTimer = new Timer(350,fiende_skott);
	/**
	 * Checks for collision between player,projectiles,ships and bosses
	 */
	public void collisionDetection() {
		if(!projectiles.isEmpty()) {
			for(int j = 0; j < projectiles.size(); j ++) {
				Projectiles p = projectiles.get(j);
				if (p.isHostile() == false) {
					if(!activeShips.isEmpty()) {
					    for(int i = 0; i < activeShips.size(); i++) {
						    Ship s = activeShips.get(i);
						    //enemy and projectile
						    if(enemyHit(p,s)) {
							score += 10;
							System.out.println("Score " + score);
							activeShips.remove(s);
							projectiles.remove(p);
							break;
						    }
					    }
					}
					if(!bosses.isEmpty()) {
						for(Boss b1 : bosses) {
						    Boss b = b1.getBoss();
						    if(bossHit(p,b)) {
							score += 10;
							System.out.println("Score " + score);
							projectiles.remove(p);
							int hp = b.getHitPoints();
							hp--;
							b.setHitPoints(hp);
							System.out.println(hp);
								if(b.getHitPoints() == 0) {
								b.setBossAlive(false);
								b.setVisible(false);
								bosses.remove(b);
								}
							break;
						   }
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
	public int getScore() {
		return score;
	}
	public boolean playerHit(Projectiles p, Player player) {
		if((p.getyPos()+(p.getLenght()/2) >= player.getyPos() && p.getyPos()+(p.getLenght()/2) <= player.getyPos() + player.getLenght())
				&& (p.getxPos()+(p.getWidth()*0.5) >= player.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= player.getxPos()+ player.getWidth())) {
			return true;
		}
		return false;
	}
	
	private boolean enemyHit(Projectiles p, Ship s) {
		if((p.getyPos()+(p.getLenght()/2) >= s.getyPos() && p.getyPos()+(p.getLenght()/2) <= s.getyPos() + s.getLenght())
				&& (p.getxPos()+(p.getWidth()*0.5) >= s.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= s.getxPos()+ s.getWidth())) {
			return true;
		}
		return false;
	}
	
	private boolean bossHit(Projectiles p, Boss s) {
		if((p.getyPos()+(p.getLenght()/2) >= s.getyPos() && p.getyPos()+(p.getLenght()/2) <= s.getyPos() + s.getLenght())
				&& (p.getxPos()+(p.getWidth()*0.5) >= s.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= s.getxPos()+ s.getWidth())) {
			System.out.println("true");
			return true;
		}
		return false;
	}
	/**
	 * Deletes projectiles and ships that are beyond the game frame
	 * Keeps the player inside the game frame
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
		for(int i = 0; i < activeShips.size(); i++) {
			Ship s = activeShips.get(i);
			
			if(s.getxPos() < -s.getWidth()) {
				activeShips.remove(s);
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
		//boss
		if(!bosses.isEmpty()) {
			for(Boss b1 : bosses) {
				 Boss b = b1.getBoss();
		
		if(b.getxPos() <= 800) {
			b.setxPos(800);
		}
		 if(b.getxPos() >= 1501 + b.getWidth()) {
			b.setxPos(1500 + b.getWidth());
		}
		 if(b.getyPos() <= 0) {
			double speedy = b.getySpeed();
			speedy = -speedy;
			b.setySpeed(speedy);
			b.setyPos(0);
		}
		 if(b.getyPos() >= 720 - b.getLenght()) {
			double speedy = b.getySpeed();
			speedy = -speedy;
			b.setySpeed(speedy);
			b.setyPos(720 - b.getLenght());
		}
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
	 * Adds the ShipMaker and RockMaker threads to a eventpool
	 */
	private void addThreads() {
		 
		 //Spawns enemy ships every x seconds
		 eventPool.scheduleAtFixedRate(new ShipMaker(this), 0, 700, MILLISECONDS);
		 eventPool.scheduleAtFixedRate(new MeteorMaker(this), 0, 5, SECONDS);

	}

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}

}

