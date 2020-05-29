package börsenprogramm;

import java.io.IOException;

public class AktuellerUser {

	private String userArt;
	private int id;

	/**
	 * Legt den aktueller Benutzer fest, mit Benutzerart und der ID. Die Parameter
	 * sind dabei final.
	 * 
	 * @param userArt: aktuelle Userart, muss "bosersenmanager", "aktionaer" oder
	 *                 "aktiengesellschaft" entsprechen.
	 * @param id:      aktuelle UserID
	 * @throws Exception, wenn die Benutzerart nicht existiert.
	 */
	public AktuellerUser(String userArt, int id) throws IllegalArgumentException {
		if (userArt.contentEquals("boersenmanager") || userArt.contentEquals("depotinhaber")
				|| userArt.contentEquals("aktiengesellschaft")) {
			this.userArt = userArt;
			this.id = id;
		} else {
			throw new IllegalArgumentException("Den User, " + userArt + ", gibt es nicht.");
		}
	}

	public String getUserArt() {
		return userArt;
	}

	public int getId() {
		return id;
	}
}
