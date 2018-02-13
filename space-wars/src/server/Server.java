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
import java.util.Map;


public class Server extends Thread{

	    public static final int PORT_NUMBER = 8081;
	    
	    private HashMap<String, Integer> highScores  = new HashMap<String, Integer>();
	 
	    protected Socket socket;

	    private Server(Socket socket) {
	        this.socket = socket;
	        addCrapToTheMap();
	        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
	        start();
	    }
	 
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
	                System.out.println("Message received:" + request);
	                
	                if(request.startsWith("add")) {
	                		String highScoreInfo[] = request.split(" ");
	                		add(highScoreInfo[1], Integer.parseInt(highScoreInfo[2]));
	                		for (Map.Entry<String, Integer> entry : highScores.entrySet())
	                		{
	                			System.out.println(entry.getKey() + ":" + entry.getValue());
	                		}
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
	 
	    public static void main(String[] args) {
	        System.out.println("Space-Wars Server");
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
	    
	    private void add(String user, int score) {
	    	
	    		if(!highScores.containsKey(user) && score > 0) {
	    			highScores.put(user, score);
	    		}
	    			
	    		if(highScores.containsKey(user) && highScores.get(user) < score) {
	    			highScores.put(user, score);
	    		}
	    		
	    }
	    
	    private HashMap<String, Integer> getHighScores(){
	    	
	    		return highScores;
	    		
	    }
	    
	    public void addCrapToTheMap() {
	    		add("Markus", 1337);
	    		add("Theo", 100);
	    		add("Albin", 111);
	    		add("Åke", 1);
	    		add("Tony", 1336);
	    		add("Svante", 1335);
	    }
	
}
