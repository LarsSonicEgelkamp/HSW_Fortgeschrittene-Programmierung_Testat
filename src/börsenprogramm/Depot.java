package börsenprogramm;

import java.util.ArrayList;

public class Depot {

	private int id;
	private String inhaber;
	private ArrayList<String> meineAktien = new ArrayList<String>();
	private ArrayList<Order> meineTransaktionen = new ArrayList<Order>();

	public Depot(int id, String inhaber) {
		this.id = id;
		this.inhaber = inhaber;
	}

	public int getId() {
		return id;
	}

	public String getInhaber() {
		return inhaber;
	}

	public ArrayList<String> getMeineAktien() {
		return meineAktien;
	}

	public void neueAktie(String aktienID) {
		this.meineAktien.add(aktienID);
	}

	public ArrayList<Order> getMeineTransaktionen() {
		return meineTransaktionen;
	}

	public void neueTransaktion(Order order) {
		this.meineTransaktionen.add(order);
	}
}
