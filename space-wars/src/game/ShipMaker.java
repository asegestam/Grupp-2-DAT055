package game;
import java.util.Random;
import java.util.concurrent.TimeUnit;
class ShipMaker implements Runnable{
	private GameEngine game;
	private Boss boss;
	public ShipMaker(GameEngine game) {
		this.game = game;
		
		boss = new Boss(2000, 150,-0.5,0,10);
		boss.setVisible(false);
		game.bosses.add(boss);
		
		
	}
	
	
	
	@Override
	public void run() {
	
		if(game.getScore() >= 30 && game.getScore() <= 50 ) {
			boss.setVisible(true);
			boss.setBossAlive(true);
		}
		if(!boss.getBossAlive()) {
		Random r = new Random();
		double min = 5;
		double max = 700;
		double random = min + (max - min) * r.nextDouble();
    		Ship ship = new Ship(1850, random, 0 , 0);
        	game.activeObjects.add(ship);
        	ship.moveEnemy();
		}
		if(boss.getBossAlive()) {
			
			boss.moveBoss();
		}
	}
}
