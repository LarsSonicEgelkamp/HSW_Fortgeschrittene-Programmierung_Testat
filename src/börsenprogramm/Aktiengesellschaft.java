package börsenprogramm;

import java.util.ArrayList;

public class Aktiengesellschaft {

	private ArrayList<String> unsereAktien = new ArrayList<String>();

	public ArrayList<String> getUnsereAktienIDs() {
		return unsereAktien;
	}

	public void neueAktie(String id) {
		this.unsereAktien.add(id);
	}

}
