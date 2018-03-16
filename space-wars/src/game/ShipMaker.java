package game;
import java.util.ArrayList;
import java.util.Random;
/**
 * This class is used to add new rocks into the game
 * Is runnable so can be used as a thread
 * @author Erik Tallbacka, Åke Svensson
 * @version 2018-03-02
 */
class ShipMaker implements Runnable{
	private GameEngine game;
	private Boss boss1,boss2,boss3;
	private ArrayList<Ship> activeShips;
	private ArrayList<Boss> bosses;
	private Player player;
	/**
	 * Initiates the given game engine object and Boss objects
	 * @param game a instance of game engine
	 */
	public ShipMaker(GameEngine game) {
		this.game = game;
		boss1 = new Boss(1400, 150,-0.5,0,50);
		boss2 = new Boss(1400, 150,-0.5,0,25);
		boss3 = new Boss(1400, 150,-0.5,0,25);
	}
	/**
	 * Adds bosses to the game on given score values
	 * Adds enemy ships when boss is not active
	 */
	@Override
	public void run() {
		if(!game.getPause()) {
			bosses = game.getBosses();
			player = game.getPlayer();
			activeShips = game.getActiveShips();
			if(game.cd.getScore() <= 130 && game.cd.getScore() >= 100 && boss1.getBossAlive() == false) {
				bosses.add(boss1);
				boss1.setBossAlive(true);
			}
			if(game.cd.getScore() <= 750 && game.cd.getScore() >= 700 && boss2.getBossAlive() == false && boss3.getBossAlive() == false) {
				bosses.add(boss2);
				boss2.setBossAlive(true);
				bosses.add(boss3);
				boss3.setBossAlive(true);
			}
			if(!boss1.getBossAlive()) {
				Random r = new Random();
				double min = 5;
				double max = 700;
				double random = min + (max - min) * r.nextDouble();
				Ship ship = new Ship(1850, random, 0 , 0);
				activeShips.add(ship);
				ship.moveEnemy();
			}
			for(Boss b1 : bosses) {
				Boss b = (Boss) b1.getInstance();
				if(b.getBossAlive()) {
					b.moveBoss();
				}
			}
		}
	}
}
