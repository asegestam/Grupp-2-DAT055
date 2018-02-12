package game;

import java.util.ArrayList;
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*;

import controller.MoveAction;

public class GameEngine extends JPanel implements Runnable{

	private ArrayList<Ship> activeObjects;
	private Player player;
	private Ship enemy;
	private Thread gameloop;
	public ArrayList<Projectiles> projectiles;
	private int score = 0;
	private int playerX;
	private int playerY;
	
	public GameEngine() {
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		setBackground(Color.black);
		this.setLayout(null);
		setFocusable(true);
        gameInit();
        createActions();
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
	        drawPlayer(g);
	        drawEnemies(g);
	        drawShot(g);  
	        Toolkit.getDefaultToolkit().sync();
	        g.dispose();
	 }
	 //Used to add the projectiles to the array
	 public void addProjectile(Projectiles projectile) {
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
	//Creates all the key actions for player movement and shooting
	public void createActions() {
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        //pressed key
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,false), "leftP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0,false), "rightP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,false), "upP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,false), "downP");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0,false), "shootP");
        //released key
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0,true), "leftR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0,true), "rightR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0,true), "upR");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0,true), "downR");
        //Actions for pressed key
        am.put("leftP", new MoveAction(player, -1, 0));
        am.put("rightP", new MoveAction(player, 1, 0));
        am.put("upP", new MoveAction(player, 0, -1));
        am.put("downP", new MoveAction(player, 0, 1));
        am.put("shootP",new ShootAction(5,0));
        //Actions for released key
        am.put("leftR", new MoveAction(player, 0, 0));
        am.put("rightR", new MoveAction(player, 0, 0));
        am.put("upR", new MoveAction(player, 0, 0));
        am.put("downR", new MoveAction(player, 0, 0));
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

	public boolean collisionDetection() {
		return false;
	}

	public void newGame(String name) {

	}

	public void loadGame(String file) {

	}

	public void generateScore() {

	}
	//When called: creates a projectile object and adds it to the array
	private class ShootAction extends AbstractAction{

		Projectiles projectile;
		private int dx;
		private int dy;
		
		public ShootAction(int dx, int dy) {
			this.dx = dx;
			this.dy = dy;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			projectile = new Projectiles(playerX,playerY,dx,dy);
			projectile.setVisible(true);
			addProjectile(projectile);
			System.out.println("Shooting");
		}
	}
}
