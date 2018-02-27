package game;

import javax.swing.ImageIcon;

public class Player extends Obj {
	private final String playerImg = "img/ship.png";

	private String name;

	public Player(int x, int y, int dx, int dy,int hp, String name) {
		super(x,y,dx,dy);
		setHitPoints(hp);
		this.name = name;
		initPlayer();
	}
	public void initPlayer() {
		ImageIcon imgI = new ImageIcon(playerImg);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setImage(imgI.getImage());
		setxPos(100);
		setyPos(250);
	}
	public String getName() {
		return name;
	}
}
