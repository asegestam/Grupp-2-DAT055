package Game;

public class Projectiles extends Obj {
	
	private boolean hostile;
	private int dmg;
	
	public Projectiles() {
		
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
