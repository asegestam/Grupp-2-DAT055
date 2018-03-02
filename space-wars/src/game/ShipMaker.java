package game;
import java.util.Random;
/**
 * This class is used to add new rocks into the game
 * Is runnable so can be used as a thread
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-03-02
 */
class ShipMaker implements Runnable{
	private GameEngine game;
	private Boss boss1,boss2,boss3;
	/**
	 * Initiates the given game engine object and Boss objects
	 * @param game
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
		if(!game.pause) {
			if(game.getScore() <= 130 && game.getScore() >= 100 && boss1.getBossAlive() == false) {
				game.bosses.add(boss1);
				boss1.setBossAlive(true);
			}
			if(game.getScore() <= 750 && game.getScore() >= 700 && boss2.getBossAlive() == false && boss3.getBossAlive() == false) {
				game.bosses.add(boss2);
				boss2.setBossAlive(true);
				game.bosses.add(boss3);
				boss3.setBossAlive(true);
			}
			if(!boss1.getBossAlive()) {
				Random r = new Random();
				double min = 5;
				double max = 700;
				double random = min + (max - min) * r.nextDouble();
				Ship ship = new Ship(1850, random, 0 , 0);
				game.activeShips.add(ship);
				ship.moveEnemy();
			}
			for(Boss b1 : game.bosses) {
				Boss b = b1.getBoss();
				if(b.getBossAlive()) {
					b.moveBoss();
				}
			}
		}
	}
}
