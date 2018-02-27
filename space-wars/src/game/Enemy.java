package game;

public abstract class Enemy extends Obj {

	int fireRate;
	int scoreValue;
	
	void changeFireRate(){}
	public Enemy(double x, double y, double dx, double dy) {
		super(x,y,dx,dy);
	}
}
