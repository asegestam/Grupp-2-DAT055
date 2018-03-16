import java.awt.EventQueue;
import gui.GUI;

/**
 * 	Starts the program with the initial Start menu
 * @author Albin Segestam
 * @version 2018-02-27
 */
public class Main {

	public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
			 GUI gui = new GUI();
	        });
    }
}
