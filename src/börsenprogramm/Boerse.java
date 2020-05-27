package börsenprogramm;

import java.util.ArrayList;

public class Boerse {

	private ArrayList<Aktie> aktienListe = new ArrayList<Aktie>();
	private ArrayList<Depot> depotListe = new ArrayList<Depot>();

	public ArrayList<Aktie> getAktienListe() {
		return aktienListe;
	}

	public ArrayList<Depot> getDepotListe() {
		return depotListe;
	}

	public void neueAktie(int id, int wert) {
		Aktie aktie = new Aktie(id, wert);
		this.aktienListe.add(aktie);
	}

	public void neuesDepot(int id, String inhaberName) {
		Depot depot = new Depot(id, inhaberName);
		this.depotListe.add(depot);
	}
}
