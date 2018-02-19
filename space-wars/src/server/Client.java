package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
 
public class Client {
 
//    public static void main(String args[]) {
//        String host = "127.0.0.1";
//        int port = 8081;
//        new Client(host, port);
//    }
	
	private String host;
	private int port;
 
    @SuppressWarnings("unchecked")
	public Client(String host, int port) {
    		this.host = host;
    		this.port = port;
    }
    
    public void addHighScore(String player, int score) {
    	try {
            String serverHostname = new String("127.0.0.1");
 
            System.out.println("Connecting to host " + serverHostname + " on port " + port + ".");
 
            Socket socket = null;
            PrintWriter out = null;
            ObjectInputStream mapInputStream = null;
            Map<String, Integer> map = null;
 
            try {
                socket = new Socket(serverHostname, 8081);
                out = new PrintWriter(socket.getOutputStream(), true);
                mapInputStream = new ObjectInputStream(socket.getInputStream());
                
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + serverHostname);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }

            out.println("add " + player + " " + score);
            
            /** Closing all the resources */
            out.close();
            mapInputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }    	
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Integer> getHighScore(){   
    	
    Map<String, Integer> map = null;
    	
    	try {
            String serverHostname = new String("127.0.0.1");
 
            Socket socket = null;
            PrintWriter out = null;
            ObjectInputStream mapInputStream = null;
 
            try {
                socket = new Socket(serverHostname, 8081);
                out = new PrintWriter(socket.getOutputStream(), true);
                mapInputStream = new ObjectInputStream(socket.getInputStream());
                
            } catch (UnknownHostException e) {
                System.err.println("Unknown host: " + serverHostname);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Unable to get streams from server");
                System.exit(1);
            }
 
                
                out.println("get");
                map = (Map<String, Integer>) mapInputStream.readObject();
                
 
            /** Closing all the resources */
            out.close();
            mapInputStream.close();
            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    	
    		return map;
    }
}