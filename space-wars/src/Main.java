import java.awt.EventQueue;

import gui.GUI;

public class Main{

	public static void main(String[] args) {
		 EventQueue.invokeLater(() -> {
			 GUI gui = new GUI();
	        });
	}

}
