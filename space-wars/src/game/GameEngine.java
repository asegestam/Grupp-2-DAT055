package game;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
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
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-02-27
 */

public class GameEngine extends JPanel implements Runnable{
	
  private Background backgroundOne;
	private Background backgroundTwo;
  private BufferedImage back;
	public ArrayList<Ship> activeObjects;
	public ArrayList<Projectiles> projectiles;
	public ArrayList<Rock> rocks;
	public ArrayList<Boss> bosses;
	private Player player;
	private Ship enemy;
	private Projectiles projectile;
	private Thread gameloop;
	private int score = 0;
	public boolean running;
	private boolean backGroundvisible;
	private GUI gui;
	ScheduledThreadPoolExecutor eventPool;
  
	public GameEngine(GUI gui) {
    backgroundOne = new Background();
		backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		rocks = new ArrayList<Rock>();
		bosses = new ArrayList<Boss>();
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
	 //Draws Boss
	 public void drawBoss(Graphics g) {
		 for(Boss b1 : bosses) {
			 Boss b = b1.getBoss();
			 if(b.isVisible()) {
				 g.drawImage(b.getImage(), (int)b.getxPos(), (int)b.getyPos(), this);
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
	        	drawBoss(g);
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
  public synchronized void addProjectile(int x,int y,int dx, int dy,String img, boolean hostile) {
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
            sleep = 10 - timeDiff;
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
		Iterator<Projectiles> shots = projectiles.iterator();
		Iterator<Ship> enemies = activeObjects.iterator();
		Iterator<Rock> obst = rocks.iterator();
		if(player.getHitPoints() == 0) {
			running = false;
			gameOver();
		}
		player.move();
		//For each projectile in the array, move it
		while(shots.hasNext()) {
			Projectiles shot = shots.next();
			
			if(shot.isVisible()) {
			 shot.move();
			}else {
				shots.remove();
			}
		}
		//For each enemy ship, move it
		while(enemies.hasNext()) {
			 Ship s = enemies.next();
			 if(s.isVisible()) {
				 s.moveEnemy();
				 s.move();
			 }else {
				 enemies.remove();
			 }
		}
		
		while(obst.hasNext()) {
			Rock r = obst.next();
			if(r.isVisible()) {
				r.move();
			}else {
				obst.remove();
			}
		}
		
		for(Boss b : bosses) {
			 Boss b2 = b.getBoss();
			 b2.move();
		}
	}
	//fiende skott
	ActionListener fiende_skott = new ActionListener() {
		 public void actionPerformed(ActionEvent evt) {
			 if(!activeObjects.isEmpty()) {
          int min = 0;
          int max = activeObjects.size()-1;
          int random = new Random().nextInt(max + 1 - min) + min;
          Ship s = activeObjects.get(random);
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
					ImageIcon imgI = new ImageIcon("space-wars/img/shot2.png");
					
			    addProjectile(s.getxPos()-s.getWidth()- imgI.getImage().getWidth(null),s.getyPos()-(s.getLenght()/2),dx,dy,"space-wars/img/shot2.png",true);
			 
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
				 ImageIcon imgI = new ImageIcon("space-wars/img/shot2.png");
					
				 addProjectile(s.getxPos()- imgI.getImage().getWidth(null),s.getyPos()+(s.getLenght()/2),dx,dy,"space-wars/img/shot2.png",true);
				 
			       }
			   }
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
		
		Rectangle r1 = player.getBounds();
		
		for(Ship enemy: activeObjects) {
			Rectangle r2 = enemy.getBounds();
			
			if(r1.intersects(r2)) {
				System.out.println("crash");
				enemy.setVisible(false);
			}
		}
		
		for(Projectiles p: projectiles) {
			Rectangle r3 = p.getBounds();
			
			if(p.isHostile()) {
				if(r1.intersects(r3)) {
					p.setVisible(false);
					System.out.println("ouch");
				}
			}
				for(Ship enemy: activeObjects) {
					Rectangle r2 = enemy.getBounds();
					if(!p.isHostile()) {
						if(r2.intersects(r3)) {
							enemy.setVisible(false);
							p.setVisible(false);
					}
				}
			}
      for(Boss b1 : bosses) {
              Rectangle r5 = b1.get.getBounds();
              		if(r5.intersects(r3)) {
                    b1.setVisible(false);
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
for(Rock obst: rocks) {
			Rectangle r4 = obst.getBounds();
			if(r4.intersects(r1)) {
				obst.setVisible(false);
			}
    }

	public int getScore() {
		return score;
	}
	/**
	 * Checks if the game objects have gone past the game frame
	 */
  public void outOfBound() {
	  Iterator<Projectiles> p = projectiles.iterator();
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
		while(p.hasNext()) {
			Projectiles s = p.next();
			if(s.getxPos() < 0) {
				p.remove();
			}
			else if(s.getxPos() >= 1280 - s.getWidth()) {
				p.remove();
			}
			else if(s.getyPos() < 0) {
				p.remove();
			else if(s.getyPos() >= 720 - s.getLenght()) {
				p.remove();
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

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}

}

