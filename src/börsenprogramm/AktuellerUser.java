package börsenprogramm;

public class AktuellerUser {

	private String userArt;
	private String id;

	/**
	 * Legt den aktueller Benutzer fest, mit Benutzerart und der ID. Die Parameter
	 * sind dabei final.
	 * 
	 * @param userArt
	 * @param id
	 * @throws Exception, wenn die Benutzerart nicht existiert.
	 */
	public AktuellerUser(String userArt, String id) throws Exception {
		if (userArt.contentEquals("bosersenmanager") || userArt.contentEquals("aktionaer")
				|| userArt.contentEquals("aktiengesellschaft")) {
			this.userArt = userArt;
			this.id = id;
		} else {
			throw new Exception("Den User, " + userArt + ", gibt es nicht.");
		}
	}

	public String getUserArt() {
		return userArt;
	}

	public String getId() {
		return id;
	}
}
