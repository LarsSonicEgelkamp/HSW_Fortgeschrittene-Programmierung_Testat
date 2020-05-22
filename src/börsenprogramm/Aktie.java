package börsenprogramm;

import java.util.ArrayList;

public class Aktie {

	private String id;
	private int aktWert;
	private ArrayList<Integer> werteHistorie = new ArrayList<Integer>();

	public Aktie(String id, int initialWert) {
		this.id = id;
		this.aktWert = initialWert;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getWert() {
		return aktWert;
	}

	/*
	 * Bevor der Wert der Aktie geändert wird, wird der aktuelle Wert zuerst in der
	 * Werte-Historie gespeichert.
	 */
	public void setWert(int wert) {
		this.werteHistorie.add(this.getWert());
		this.aktWert = wert;
	}
}
