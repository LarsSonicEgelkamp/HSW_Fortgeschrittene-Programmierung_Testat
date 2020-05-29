package börsenprogramm;

import java.util.ArrayList;

public class Depotinhaber {

	private int id;
	private String name;
	private static ArrayList<Integer> meineDepots = new ArrayList<Integer>();

	private Depotinhaber(int id, String name) {
		this.id = id;
		this.name = name;
	}

	private void neuesDepot(int depotID) {
		this.meineDepots.add(depotID);
	}
	
	public static ArrayList<Integer> getMeineDepots() {
		return meineDepots;
	}
	
	public void verkaufsVerlauf() {
		
	}
}
