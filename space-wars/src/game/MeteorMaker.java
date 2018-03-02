package game;
/**
 * This class is used to add new rocks into the game
 * Is runnable so can be used as a thread
 * 
 * @author Albin Segestam,Åke Svensson, Markus Saarijärvi, Erik Tallbacka, Theo Haugen
 * @version 2018-03-02
 */
public class MeteorMaker implements Runnable{
	private GameEngine game;
	/**
	 * Initiates the given game engine object
	 * @param game
	 */
	public MeteorMaker(GameEngine game) {
		this.game = game;
	}
	/**
	 * When active: adds 5 rock objects to the array
	 */
	@Override
	public void run() {
		if(!game.pause) {
			for(int j = 0; j < 5; j++) {
				Meteor rock = new Meteor(1200, 400, -4,0);
				game.meteors.add(rock);
			}
		}
	}
}
