package game;

import javax.swing.ImageIcon;

public class Player extends Obj {
	private final String playerImg = "space-wars/img/ship.png";

	private String name;

	public Player(int x, int y, int dx, int dy, String name) {
		super(x,y,dx,dy);
		this.name = name;
		initPlayer();
	}
	public void initPlayer() {
		ImageIcon imgI = new ImageIcon(playerImg);
		setWidth(imgI.getImage().getWidth(null));
		setImage(imgI.getImage());
		setxPos(100);
		setyPos(250);
	}
	public String getName() {
		return name;
	}
}
