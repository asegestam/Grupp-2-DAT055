package game;
import java.util.ArrayList;

public class GameEngine {
	
	private ArrayList<Obj> activeObjects;
	private int score = 0;
	private Player player;
	
	public GameEngine(String name) {
		activeObjects = new ArrayList<Obj>();
		player = new Player(name);
	}
	
	public static void main(String [ ] args)
	{
	      
	}
	
	public void update() {
		
	}
	
	public boolean collisionDetection() {
		return false;
	}
	
	public void newGame(String name) {
		
	}
	
	public void loadGame(String file) {
		
	}
	
	public void generateScore() {
		
	}
}
