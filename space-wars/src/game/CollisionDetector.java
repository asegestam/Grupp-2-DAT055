package game;

import java.util.ArrayList;

/**
 * Creates the Game GUI
 * Used to check for collision between the game objects
 * @author Åke Svensson, Theo Haugen
 * @version 2018-03-16
 */

public class CollisionDetector {
	private GameEngine game;
	private Player player;
	private ArrayList<Ship> activeShips;
	private ArrayList<Projectiles> projectiles;
	private ArrayList<Meteor> meteors;
	private ArrayList<Boss> bosses;
	private int score;
	/**
	 * Initiates the game engine used to check objects and the player object
	 * @param game game engine being used
	 * @param player player being checked
	 */
	public CollisionDetector(GameEngine game, Player player) {
		this.game = game;
		this.player = player;
		score = 0;
	}
	
	/**
	 * Checks for collision between player,projectiles,ships and bosses
	 */
	public void collisionDetection() {
		activeShips = game.getActiveShips();
		projectiles = game.getProjectiles();
		meteors = game.getMeteors();
		bosses = game.getBosses();
		player = game.getPlayer();
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
							activeShips.remove(s);
							projectiles.remove(p);
							break;
						    }
					    }
					}
					if(!bosses.isEmpty()) {
						for(Boss b1 : bosses) {
						    Boss b = (Boss) b1.getInstance();
						    if(bossHit(p,b)) {
							score += 10;
							projectiles.remove(p);
							int hp = b.getHitPoints();
							hp--;
							b.setHitPoints(hp);
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
				
				for(int i = 0; i < activeShips.size(); i++) {
					Ship s = activeShips.get(i);
					if(shipCollision(player, s)) {
						player.setHitPoints(player.getHitPoints()-1);
						activeShips.remove(i);
						break;
					}
				}
				for(int i = 0; i < bosses.size(); i++) {
					Boss b = bosses.get(i);
					if(bossCollision(player, b)) {
						player.setHitPoints(player.getHitPoints()-1);
						break;
					}
				}
				for(int i = 0; i < meteors.size(); i++) {
					Meteor m = meteors.get(i);
					if(meteorCollision(player, m)) {
						player.setHitPoints(player.getHitPoints()-1);
						meteors.remove(m);
					}
				}
			}
		}
	}
	/**
	 * Checks for collision between player and enemy projectiles
	 * @param p the projectile being checked
	 * @param player player being checked
	 * @return if collision has occurred or not
	 */
	public boolean playerHit(Projectiles p, Player player) {
		if((p.getyPos()+(p.getHeight()/2) >= player.getyPos() && p.getyPos()+(p.getHeight()/2) <= player.getyPos() + player.getHeight())
				&& (p.getxPos()+(p.getWidth()*0.5) >= player.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= player.getxPos()+ player.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks for collision between enemy ships and player projectiles
	 * @param p the projectile being checked
	 * @param s the ship being checked
	 * @return if collision has occurred or not
	 */
	private boolean enemyHit(Projectiles p, Ship s) {
		if((p.getyPos()+(p.getHeight()/2) >= s.getyPos() && p.getyPos()+(p.getHeight()/2) <= s.getyPos() + s.getHeight())
				&& (p.getxPos()+(p.getWidth()*0.5) >= s.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= s.getxPos()+ s.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks for collision between boss and player projectiles
	 * @param p the projectile being checked
	 * @param b the boss being checked
	 * @return if collision has occurred or not
	 */
	private boolean bossHit(Projectiles p, Boss b) {
		if((p.getyPos()+(p.getHeight()/2) >= b.getyPos() && p.getyPos()+(p.getHeight()/2) <= b.getyPos() + b.getHeight())
				&& (p.getxPos()+(p.getWidth()*0.5) >= b.getxPos() && p.getxPos()+(p.getWidth()*0.5) <= b.getxPos()+ b.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks for collision between player and boss
	 * @param player
	 * @param boss
	 * @return if collision has occurred or not
	 */
	private boolean bossCollision(Player player, Boss boss) {
		if((player.getyPos()+(player.getHeight()/2) >= boss.getyPos() && player.getyPos()+(player.getHeight()/2) <= boss.getyPos() + boss.getHeight())
				&& (player.getxPos()+(player.getWidth()*0.5) >= boss.getxPos() && player.getxPos()+(player.getWidth()*0.5) <= boss.getxPos()+ boss.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks for collision between player and enemy ship
	 * @param player player being checked
	 * @param s ship being checked
	 * @return if collision has occurred or not
	 */
	private boolean shipCollision(Player player, Ship s) {
		if((player.getyPos()+(player.getHeight()/2) >= s.getyPos() && player.getyPos()+(player.getHeight()/2) <= s.getyPos() + s.getHeight())
				&& (player.getxPos()+(player.getWidth()*0.5) >= s.getxPos() && player.getxPos()+(player.getWidth()*0.5) <= s.getxPos()+ s.getWidth())) {
			return true;
		}
		return false;
	}
	/**
	 * Checks collision between meteor and player
	 * @param player player being checked
	 * @param m meteor being checked
	 * @return if collision has occurred or not
	 */
	private boolean meteorCollision(Player player, Meteor m) {
		if((m.getyPos()+(m.getHeight()/2) >= player.getyPos() && m.getyPos()+(m.getHeight()/2) <= player.getyPos() + player.getHeight())
				&& (m.getxPos()+(m.getWidth()*0.5) >= player.getxPos() && m.getxPos()+(m.getWidth()*0.5) <= player.getxPos()+ player.getWidth())) {
			
			return true;
		}
		return false;
	}
	/**
	 * Deletes projectiles and ships that are beyond the game frame
	 * Keeps the player inside the game frame
	 */
	public void outOfBound() {
		activeShips = game.getActiveShips();
		projectiles = game.getProjectiles();
		meteors = game.getMeteors();
		bosses = game.getBosses();
		player = game.getPlayer();
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
		else if(player.getyPos() >= 720 - player.getHeight()) {
			player.setyPos(719 - player.getHeight());
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
			else if(s.getyPos() >= 720 - s.getHeight()) {
				double speedy = s.getySpeed();
				speedy = -speedy;
				s.setySpeed(speedy);
			}
		}
		//boss
		if(!bosses.isEmpty()) {
			for(Boss b1 : bosses) {
				 Boss b = (Boss) b1.getInstance();
		
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
		 if(b.getyPos() >= 720 - b.getHeight()) {
			double speedy = b.getySpeed();
			speedy = -speedy;
			b.setySpeed(speedy);
			b.setyPos(720 - b.getHeight());
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
			else if(s.getyPos() >= 720 - s.getHeight()) {
				projectiles.remove(s);
			}
		}
	}
	/**
	 * Returns the score
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Sets the score
	 * @param d score being set
	 */
	public void setScore(int d) {
		this.score = d;
	}
}
