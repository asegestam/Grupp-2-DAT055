package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Server extends Thread{

	    public static final int PORT_NUMBER = 8081;
	    
	    private static HashMap<String, Integer> highScores  = new HashMap<String, Integer>();
	 
	    protected Socket socket;

	    private Server(Socket socket) {
	        this.socket = socket;
	        System.out.println("New client connected from " + socket.getInetAddress().getHostAddress());
	        start();
	    }
	 
	    public void run() {
	        InputStream in = null;
	        OutputStream out = null;
	        try {
	            in = socket.getInputStream();
	            out = socket.getOutputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(in));
	            String request;
	            while ((request = br.readLine()) != null) {
	                System.out.println("Message received:" + request);
	                
	                if(request.equals("add"))
	                		System.out.println("add(String user, int score)");
	                else if(request.equals("get"))
	                		System.out.println("get HashMap<String, Integer> highScores");
	                
	                request += '\n';
	                out.write(request.getBytes());
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
	    
	    private static void add(String user, int score) {
	    	
	    		if(!highScores.containsKey(user) && score > 0) {
	    			highScores.put(user, score);
	    			return;
	    		}
	    			
	    		if(highScores.containsKey(user) && highScores.get(user) < score) {
	    			highScores.put(user, score);
	    		}
	    		
	    }
	    
	    private static HashMap<String, Integer> getHighScores(){
	    	
	    		return highScores;
	    	
	    }
	
}
