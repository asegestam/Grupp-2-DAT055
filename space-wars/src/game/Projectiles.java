package game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class Projectiles extends Obj {
	
	private boolean hostile;
	private int dmg;
	private final String shot = "space-wars/img/shot.png";
	
	public Projectiles(int x, int y, double dx, double dy, String img, boolean fientligt) {
		super(x,y,dx,dy);
		//setVisible(false);
		initShot(x,y,img);
		hostile = fientligt;
	}
	
	public void initShot(int x, int y, String img) {
		ImageIcon imgI = new ImageIcon(img);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setImage(imgI.getImage());
		setxPos(x + 120);
		setyPos(y + 55);
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
	public Projectiles getProjectile() {
		return this;
	}
	
	
}
