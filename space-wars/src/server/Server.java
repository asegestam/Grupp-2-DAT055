package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 	Server to store highscores
 * @author Markus Saarijärvi
 * @version 2018-02-27
 */
public class Server extends Thread{
	
    public static final int PORT_NUMBER = 8081;
    private static HashMap<String, Integer> highScores  = new HashMap<String, Integer>();
    protected Socket socket;
    
    public Server(Socket socket) {
	        this.socket = socket;
	        addCrapToTheMap();
	        start();
	    }
    /**
	 * Responds to the query from the client
	 * Such as AddHighscore and getHighScores
	 */
    public void run() {
	    InputStream in = null;
	    OutputStream out = null;
	        try {
	            in = socket.getInputStream();
	            out = socket.getOutputStream();
	            ObjectOutputStream mapOutputStream = new ObjectOutputStream(out);
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            String request;
	            while ((request = br.readLine()) != null) {
	                if(request.startsWith("add")) {
	                    String highScoreInfo[] = request.split(" ");
	                    add(highScoreInfo[1], Integer.parseInt(highScoreInfo[2]));
	                }
	                else if(request.startsWith("get")) {
	                    mapOutputStream.writeObject(getHighScores());
	                }
	                request += '\n';
	            }
	 
	        } catch (IOException ex) {
	            System.out.println("Unable to get streams from client");
	        } finally {
	            try {
	                in.close();
	                out.close();
	                socket.close();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
    /**
	 * Establish a connection to the server
	 * @param args
	 */
    public static void main(String[] args) {
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
    /**
	 * Adds a name and a score to the HashMap
	 * @param user
	 * @param score
	 */
    private static void add(String user, int score) {
    	if(!highScores.containsKey(user) && score > 0) {
    		highScores.put(user, score);
    		}
    	if(highScores.containsKey(user) && highScores.get(user) < score) {
    		highScores.put(user, score);
	    	}
    	}
    /**
	 * Returns the HashMap
	 * @return
	 */
    private static HashMap<String, Integer> getHighScores(){
    	return highScores;
	}
    /**
	 * Adds dummy values to the list
	 */
    public void addCrapToTheMap() {
    	add("Markus", 1337);
	    add("Theo", 100);
	    add("Albin", 111);
	    add("Ake", 1);
	    add("Tony", 1336);
	    add("Svante", 1335);
	    }	
    }
