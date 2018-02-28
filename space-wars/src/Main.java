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
			 startServer();
	        });
  }
	private static void startServer() {
        System.out.println("Space-Wars Server is now active");
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT_NUMBER);
            while (true) {
                /**
                 * create a new {@link SocketServer} object for each connection
                 * this will allow multiple client connections
                 */
                new Server(server.accept());
            }
        } catch (IOException ex) {
            System.out.println("Unable to start server.");
        } finally {
            try {
                if (server != null)
                    server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}

}
