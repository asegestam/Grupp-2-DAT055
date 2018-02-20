package game;
import java.util.Random;
import java.util.concurrent.TimeUnit;
class ShipMaker implements Runnable{
	private GameEngine game;
	public ShipMaker(GameEngine game) {
		this.game = game;
	}

	@Override
	public void run() {
		Random r = new Random();
		double min = 5;
		double max = 700;
		double random = min + (max - min) * r.nextDouble();
    		Ship ship = new Ship(1850, random, 0 , 0);
        	game.activeObjects.add(ship);
        	ship.moveEnemy();
		
		System.out.println(game.activeObjects.size());
	}
}
