import java.awt.EventQueue;
import gui.GUI;

/**
 * 	Starts the server and creates the initial start menu
 * @author Albin Segestam, Markus Saarijärvi
 * @version 2018-02-27
 */
public class Main {

	public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
			 GUI gui = new GUI();
	        });
    }
}
