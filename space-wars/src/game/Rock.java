package game;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;

public class Rock extends Obstacles{
	private final String shot = "space-wars/img/meteor.png";
	private static int min = 5;
	private static int max = 710;
	private ImageIcon imgI;
	
	public Rock(int x, int y, int dx, int dy) {
		super(x,y,dx,dy);
		initRock();
	}
	public void initRock() {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		imgI = new ImageIcon(shot);
		setWidth(imgI.getImage().getWidth(null));
		setLenght(imgI.getImage().getHeight(null));
		setRockImage();
		setxPos(1290);
		setyPos(randomNum);
		setySpeed(0);
		setxSpeed(-4);
	}
	
	public Rock getRock() {
		return this;
	}
	public void setRockImage() {
		setImage(imgI.getImage());
	}

}
