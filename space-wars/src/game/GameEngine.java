package game;
import java.util.ArrayList;
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
	        player = new Player(0,0,0,0,"test");
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
			 s2.moveEnemy(playerX,playerY);
			 s2.move();
		}
		//For each enemy ship, move it
		for(Rock r : rocks) {
			 Rock r2 = r.getRock();
			 r2.move();
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
					score += 10;
					System.out.println("Score " + score);
					activeObjects.remove(s);
					projectiles.remove(p);
					}
				}
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
