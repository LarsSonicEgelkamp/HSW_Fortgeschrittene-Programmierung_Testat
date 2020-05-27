package börsenprogramm;

import java.util.ArrayList;

public class Boersenmanager {
	

	public Boersenmanager() {
		
	}

	static ArrayList<String> aktienListe = new ArrayList<String>();

	public static ArrayList<String> getAktienListe() {
		return aktienListe;
	}

	public void setAktienListe(ArrayList<String> aktienListe) {
		this.aktienListe = aktienListe;
	}
	
}
