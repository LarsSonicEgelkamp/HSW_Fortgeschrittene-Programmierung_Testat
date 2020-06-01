package boersenprogramm;

/**
 * Legt den aktueller Benutzer fest, mit Benutzerart und der ID.
 * 
 * @param userArt: aktuelle Userart, muss "bosersenmanager", "aktionaer" oder
 *                 "aktiengesellschaft" entsprechen.
 * @param id:      aktuelle UserID
 * @throws Exception, wenn die Benutzerart nicht existiert.
 */
public class AktuellerUser {

	private String userArt;
	private int id;

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
