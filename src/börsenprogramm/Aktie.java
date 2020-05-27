package börsenprogramm;

import java.util.ArrayList;

public class Aktie {

	private int id;
	private int aktWert;
	private ArrayList<Integer> werteHistorie = new ArrayList<Integer>();

	public Aktie(int id, int initialWert) {
		this.id = id;
		this.aktWert = initialWert;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
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
