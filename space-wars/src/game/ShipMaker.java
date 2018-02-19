package game;

class ShipMaker implements Runnable{
	private GameEngine game;
	public ShipMaker(GameEngine game) {
		this.game = game;
	}

	@Override
	public void run() {
		for(int j = 0; j < 6; j++) {
    		Ship ship = new Ship(1000, (50 + 114 *j), 0 , 0);
        	game.activeObjects.add(ship);
		}
		System.out.println(game.activeObjects.size());
	}
}
