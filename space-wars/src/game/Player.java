package game;

import javax.swing.ImageIcon;

public class Player extends Obj {
	private final String playerImg = "img/ship.png";

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
	}
	public String getName() {
		return name;
	}
	public void createPlayer() {
		
	}

}
