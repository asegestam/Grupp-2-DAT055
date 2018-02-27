package game;
import java.util.ArrayList;
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


public class GameEngine extends JPanel implements Runnable{
	
	private Background backgroundOne;
	private Background backgroundTwo;
  	private BufferedImage back;
	public ArrayList<Ship> activeObjects;
	public ArrayList<Projectiles> projectiles;
	public ArrayList<Rock> rocks;
	public ArrayList<Boss> bosses;
	private Player player;
	private Projectiles projectile;
	private Thread gameloop;
	private int score = 0;
	public boolean running;
	private boolean backGroundvisible;
	private GUI gui;
	private Client client;
	ScheduledThreadPoolExecutor eventPool;
	
	public GameEngine(GUI gui) {
		backgroundOne = new Background();
		backgroundTwo = new Background(backgroundOne.getImageWidth(), 0);
		activeObjects = new ArrayList<Ship>();
		projectiles = new ArrayList<Projectiles>();
		rocks = new ArrayList<Rock>();
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
        if(backGroundvisible) {
        	twoD.drawImage(back, null, 0, 0);	
        }
 
    }
	 //Draws the player
	 public void drawPlayer(Graphics g) {
		 if(player.isVisible()) {
			 g.drawImage(player.getImage(),(int) player.getxPos(),(int) player.getyPos(), this); 
			 }
		 }
	//Draws the enemies
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

			 Boss b = bosses.get(0);
			 if(b.isVisible()) {
				 g.drawImage(b.getImage(), (int)b.getxPos(), (int)b.getyPos(), this);
			 
		 }
	 }
	 
	 //Draws the projectiles
	 public void drawShot(Graphics g) {
		 for(Projectiles p : projectiles) {
			 Projectiles shot = p.getProjectile();
			 if(shot.isVisible()) {
				 g.drawImage(shot.getImage(), (int) shot.getxPos(), (int) shot.getyPos(), this);
			 }
		 }
	 }
	 //Draws the projectiles
	 public void drawRocks(Graphics g) {
		 for(Rock r : rocks) {
			 Rock rock = r.getRock();
			 if(rock.isVisible()) {
				 g.drawImage(rock.getImage(),(int) rock.getxPos(),(int) rock.getyPos(), this); 
			 }
		 }
	 }
	
	 //Paints the player,enemies, and the projectiles
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
	 //Used to add the projectiles to the array
	 public void addProjectile(double d,double e,double dx, double dy,String img, boolean hostile) {
		 projectile = new Projectiles(d,e,dx,dy,img,hostile);
		 projectiles.add(projectile);	
		}

	//Updates and repaints the panel on a delay
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
	//Removes all objects and creates a game over screen
	public void gameOver() {
		//Shutdowns the threadpool
		eventPool.shutdownNow();
		
		JFrame frame = gui.getFrame();
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.setLayout(new GridBagLayout()); 
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
		JLabel gameOverLabel = new JLabel("Game Over");
		JLabel scoreLabel = new JLabel("Your Score: " + Integer.toString(score));
		gameOverLabel.setForeground(Color.red);
		gameOverLabel.setFont(gameOverLabel.getFont().deriveFont(64.0f));
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(48.0f));
		frame.add(gameOverLabel,gbc);
		frame.add(scoreLabel,gbc);

		JButton startButton = new JButton("New Game");
		JButton loadButton = new JButton("Load Game");
		JButton exitButton = new JButton("Exit Game");
		JButton hsButton = new JButton("HighScores");
		JButton scoreButton = new JButton("Submit Score");

		startButton.setPreferredSize(new Dimension(250,50));
		loadButton.setPreferredSize(new Dimension(250,50));
		exitButton.setPreferredSize(new Dimension(250,50));
		hsButton.setPreferredSize(new Dimension(250,50));
		scoreButton.setPreferredSize(new Dimension(250,50));
		
		frame.add(startButton,gbc);
		frame.add(loadButton,gbc);
		frame.add(exitButton,gbc);
	    frame.add(hsButton,gbc);
	    frame.add(scoreButton,gbc);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
		//Creates a new game
        startButton.addActionListener(
        (ActionEvent e)->{gui.makeGameFrame(); frame.dispose();;});
        //Exits the game
		 exitButton.addActionListener(
			        (ActionEvent e)->{System.exit(0);;});
		//Prints highscores
		hsButton.addActionListener(
		        (ActionEvent e)->{gui.writeHighScores();});
		scoreButton.addActionListener(
		        (ActionEvent e)->{String inputName = JOptionPane.showInputDialog("Please Enter a Name");client.addHighScore(inputName, score);});
	}
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
		
		for(Boss b : bosses) {
			 Boss b2 = b.getBoss();
			 b2.move();
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
					
					ImageIcon imgI = new ImageIcon("space-wars/img/shot2.png");
					
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
						if(!bosses.isEmpty()) {
						Boss b = bosses.get(0);
						//boss and projectile
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
		//boss
		if(!bosses.isEmpty()) {
		Boss b = bosses.get(0);
		
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

