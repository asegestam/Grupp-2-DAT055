package game;
import java.util.Random;
import java.util.concurrent.TimeUnit;
class ShipMaker implements Runnable{
	private GameEngine game;
	private Boss boss1,boss2,boss3;
	public ShipMaker(GameEngine game) {
		this.game = game;
		boss1 = new Boss(1400, 150,-0.5,0,50);
		boss2 = new Boss(1400, 150,-0.5,0,25);
		boss3 = new Boss(1400, 150,-0.5,0,25);
	}
	
	@Override
	public void run() {
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
        	game.activeObjects.add(ship);
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
