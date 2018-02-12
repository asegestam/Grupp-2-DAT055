package game;

public abstract class Enemy extends Obj {

	int fireRate;
	int scoreValue;
	
	void changeFireRate(){}
	public Enemy(int x, int y, int dx, int dy) {
		super(x,y,dx,dy);
	}
}
