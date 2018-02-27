package game;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

public class Projectiles extends Obj {
	
	private boolean hostile;
	private int dmg;
	private final String shot = "img/shot.png";
	
	public Projectiles(double d, double e, double dx, double dy, String img, boolean fientligt) {
		super(d,e,dx,dy);
		//setVisible(false);
		initShot(d,e,img);
		hostile = fientligt;
	}
	
	public void initShot(double d, double e, String img) {
		ImageIcon imgI = new ImageIcon(img);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setImage(imgI.getImage());
		setxPos(d + 120);
		setyPos(e + 55);
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
