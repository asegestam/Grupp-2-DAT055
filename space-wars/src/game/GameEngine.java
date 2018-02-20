package game;
import java.util.ArrayList;
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
import javax.swing.ImageIcon;


public class GameEngine extends JPanel implements Runnable{
	
	private Background backgroundOne;
  private Background backgroundTwo;
  private BufferedImage back;
	public ArrayList<Ship> activeObjects;
	public ArrayList<Projectiles> projectiles;
	public ArrayList<Rock> rocks;
	private Player player;
	private Ship enemy;
	private Projectiles projectile;
	private Thread gameloop;
	private int score = 0;
	private double playerX;
	private double playerY;
	
	
	public GameEngine() {
		backgroundOne = new Background();
    backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		rocks = new ArrayList<Rock>();
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
	        player = new Player(0,0,0,0,5,"test");
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
			g.drawImage(player.getImage(),(int) player.getxPos(),(int) player.getyPos(), this);
		 }
	//Draws the enemies
	 public void drawEnemies(Graphics g) {
		 for(Ship s : activeObjects) {
			 Ship s2 = s.getShip();
			 g.drawImage(s2.getImage(), (int)s2.getxPos(),(int)s2.getyPos(), this);
		 }
	 }
	 //Draws the projectiles
	 public void drawShot(Graphics g) {
		 for(Projectiles p : projectiles) {
			 Projectiles shot = p.getProjectile();
			 g.drawImage(shot.getImage(), (int) shot.getxPos(), (int) shot.getyPos(), this);
		 }
	 }
	 //Draws the projectiles
	 public void drawRocks(Graphics g) {
		 for(Rock r : rocks) {
			 Rock rock = r.getRock();
			 g.drawImage(rock.getImage(),(int) rock.getxPos(),(int) rock.getyPos(), this);
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
	 public void addProjectile(double d,double e,double dx, double dy,String img, boolean hostile) {
		 projectile = new Projectiles(d,e,dx,dy,img,hostile);
		 projectiles.add(projectile);	
		}

	//Updates and repaints the panel on a delay
	@Override
    public void run() {
		double x = player.getxPos();
		double y = player.getyPos();
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
					
					ImageIcon imgI = new ImageIcon("space-wars/img/shot.png");
					
			 addProjectile(s.getxPos()-s.getWidth()- imgI.getImage().getWidth(null),s.getyPos()-(s.getLenght()/2),dx,dy,"space-wars/img/shot2.png",true);
			 
			 
         }
	};
	
	//fiende skott
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
					if(player.getHitPoints() == 0) {
						System.out.println("Game over");
					}
				}
			}
		}
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
	
	//Used to add threads to a scheduled pool
	private void addThreads() {
		 ScheduledThreadPoolExecutor eventPool = new ScheduledThreadPoolExecutor(5);
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

