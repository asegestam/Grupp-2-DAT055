package game;

public class Player extends Obj {

	private String name;

	public Player(int x, int y, int dx, int dy, String name) {
		super(x,y,dx,dy);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void createPlayer() {
		
	}

}
