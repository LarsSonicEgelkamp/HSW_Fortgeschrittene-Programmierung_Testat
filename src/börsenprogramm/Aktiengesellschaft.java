package börsenprogramm;

import java.util.ArrayList;

public class Aktiengesellschaft {

	private int id;
	private ArrayList<String> unsereAktien = new ArrayList<String>();

	public Aktiengesellschaft(int id) {
		this.id = id;
	}

	public ArrayList<String> getUnsereAktienIDs() {
		return unsereAktien;
	}

	public void neueAktie(String id) {
		this.unsereAktien.add(id);
	}

}
