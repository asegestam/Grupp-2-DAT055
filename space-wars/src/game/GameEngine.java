package game;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.awt.*; 
import java.awt.image.BufferedImage;
import javax.swing.*;
import static java.util.concurrent.TimeUnit.*;
import controller.ActionHandler;
import gui.Background;


public class GameEngine extends JPanel implements Runnable{
	
	private Background backgroundOne;
	private Background backgroundTwo;
	private BufferedImage back;
	public ArrayList<Ship> activeObjects;
	public ArrayList<Projectiles> projectiles;
	public ArrayList<Rock> rocks;
	private Player player;
	private Ship enemy;
	public Semaphore sem;
	private Projectiles projectile;
	private Thread gameloop;
	private int score = 0;
	private int playerX;
	private int playerY;
	
	
	public GameEngine() {
		backgroundOne = new Background();
		backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		rocks = new ArrayList<Rock>();
		Semaphore sem = new Semaphore();
        setVisible(true);
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
	        player = new Player(0,0,0,0,3,"test");
	        addThreads();
	        if(gameloop == null) {
	        	gameloop = new Thread(this);
	        	gameloop.start();
	        }
	 }
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
	 //Draws the projectiles
	 public void drawRocks(Graphics g) {
		 for(Rock r : rocks) {
			 Rock rock = r.getRock();
			 g.drawImage(rock.getImage(), rock.getxPos(), rock.getyPos(), this);
		 }
	 }
	 //Paints the player,enemies, and the projectiles
	 @Override
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        drawBackground(g);
	        drawPlayer(g);
	        drawEnemies(g);
	        drawShot(g);  
	        drawRocks(g);
	        Toolkit.getDefaultToolkit().sync();
	        g.dispose();
	 }
	 //Used to add the projectiles to the array
	 public synchronized void addProjectile(int x,int y,int dx, int dy,String img, boolean hostile) {
		 projectile = new Projectiles(x,y,dx,dy,img,hostile);
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
	public void update() {
		
		Iterator<Projectiles> shots = projectiles.iterator();
		Iterator<Ship> enemies = activeObjects.iterator();
		Iterator<Rock> obst = rocks.iterator();
		player.move();
		//Update the x and y position of the player for the projectiles
		playerX = player.getxPos();
		playerY = player.getyPos();
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
	}

	
	
	//fiende skott
	ActionListener fiende_skott = new ActionListener() {
		 public void actionPerformed(ActionEvent evt) {
             Iterator<Ship> enemy = activeObjects.iterator();
             
             while(enemy.hasNext()) {
            	 Ship s = enemy.next();
            	 
            	 addProjectile(s.getxPos(),s.getyPos(),-2,0,"img/shot2.png",true);
             }
         }
	};
	
	
	Timer fiendeSottTimer = new Timer(1200,fiende_skott);

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
					System.out.println("ouch!");
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
		}
		
		for(Rock obst: rocks) {
			Rectangle r4 = obst.getBounds();
			
			if(r4.intersects(r1)) {
				obst.setVisible(false);
			}
		}
	}

	
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
			
			if(s.getxPos() < 0) {
				s.setxPos(1);
			}
			else if(s.getxPos() >= 1280 - s.getWidth()) {
				s.setxPos(1279 - s.getWidth());
			}
			else if(s.getyPos() < 0) {
				s.setyPos(1);
			}
			else if(s.getyPos() >= 720 - s.getLenght()) {
				s.setyPos(719 - s.getLenght());
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
			}
			else if(s.getyPos() >= 720 - s.getLenght()) {
				p.remove();
			}
		}
	}
	
	//Used to add threads to a scheduled pool
	private void addThreads() {
		 ScheduledThreadPoolExecutor eventPool = new ScheduledThreadPoolExecutor(5);
		 //Spawns enemy ships every x seconds
		 eventPool.scheduleAtFixedRate(new ShipMaker(this), 0, 10, SECONDS);
		 eventPool.scheduleAtFixedRate(new RockMaker(this), 0, 5, SECONDS);

	}

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}

}

