import java.awt.EventQueue;
import java.io.IOException;
import java.net.ServerSocket;

import gui.GUI;
import server.Server;
/**
 * 	Starts the server and creates the initial start menu
 * @author Albin Segestam, Markus Saarijärvi
 * @version 2018-02-27
 */
public class Main{
	public static final int PORT_NUMBER = 8081;

	public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
			 GUI gui = new GUI();
	        });
  }

}
