package game;

public class Projectiles extends Obj {
	
	private boolean hostile;
	private int dmg;
	
	public Projectiles(int x, int y, int dx, int dy) {
		super(x,y,dx,dy);
	}

	public boolean isHostile() {
		return hostile;
	}

	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
	
	
	
}
