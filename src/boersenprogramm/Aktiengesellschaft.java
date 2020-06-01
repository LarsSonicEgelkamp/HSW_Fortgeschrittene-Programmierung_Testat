package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Beschreibt das Objekt Aktiengesellschaft der Börsenanwendung. Durch den
 * Konstruktor kann eine neue Aktiengesellschaft erstellt werden. 
 */
public class Aktiengesellschaft {

	private int id;
	private ArrayList<Aktie> unsereAktien = new ArrayList<Aktie>();
	Statement stat;

	public Aktiengesellschaft(int id, Statement stat) {
		this.id = id;
		this.stat = stat;
	}

	public int getAktiengesellschaftID() {
		return this.id;
	}

	public ArrayList<Aktie> getUnsereAktienIDs() {
		return unsereAktien;
	}

	public void neueAktie(Aktie ak) throws SQLException {
		ResultSet aktie = stat.executeQuery("SELECT ID FROM Aktien WHERE ID = " + ak.getId() + ";");
		if (aktie.next()) {
			this.unsereAktien.add(ak);
		} else {
			throw new IllegalArgumentException(
					"Die hinzuzufügende Aktie existiert nicht. Bitte legen sie erst ihre Aktie an");
		}
	}
}
