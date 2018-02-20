package game;

class RockMaker implements Runnable{
	private GameEngine game;
	public RockMaker(GameEngine game) {
		this.game = game;
	}

	@Override
	public void run() {
		for(int j = 0; j < 5; j++) {
    		Rock rock = new Rock(1200, 400, 0,0);
        	game.rocks.add(rock);
		}
		System.out.println(game.rocks.size());
		
		
		
	}
}
