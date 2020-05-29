package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Aktiengesellschaft {

	private int id;
	private ArrayList<Aktie> unsereAktien = new ArrayList<Aktie>();
	Statement stat;

	public Aktiengesellschaft(int id, Statement stat) {
		this.id = id;
		this.stat = stat;
	}

	public ArrayList<Aktie> getUnsereAktienIDs() {
		return unsereAktien;
	}

	public void neueAktie(Aktie ak) throws SQLException {
		ResultSet aktie = stat.executeQuery("SELECT ID FROM Aktien WHERE ID = " + ak.getId()+";");
		if (aktie.next()) {
			this.unsereAktien.add(ak);
		} else {
			System.out.println("Die hinzuzufügende Aktie existiert nicht. Bitte legen sie erst ihre Aktie an"); // Design
		}
	}
}
