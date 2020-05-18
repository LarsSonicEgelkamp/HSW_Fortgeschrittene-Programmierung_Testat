package user_Interface;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUI {

	public static void main(String[] args) {
		
		// Erzeugung eines neuen Frames mit dem Titel "Beispiel JFrame "        
		JFrame meinFrame = new JFrame("Börsenanwendung");
		// größe setzen        
		meinFrame.setSize(200,200);
		// Wir lassen unseren Frame anzeigen
		meinFrame.setVisible(true);
		//Passwort eingabe
		JOptionPane.showInputDialog("Bitte User-ID angeben");
		
	}

}
