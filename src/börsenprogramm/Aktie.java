package börsenprogramm;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

public class Aktie {

	private final int id;
	private int aktWert;
	private ArrayList<Integer> werteHistorie = new ArrayList<Integer>();

	private int depotinhaber;
	private int aktiengesellschaft;
	private int depot;

	Statement stat;

	public Aktie(Statement stat, int id, int initialWert) {
		this.id = id;
		this.aktWert = initialWert;
		this.stat = stat;
	}

	public Aktie(Statement stat, int id, int initialWert, int aktiengesellschaft) {
		this.id = id;
		this.aktWert = initialWert;
		this.stat = stat;
		this.aktiengesellschaft = aktiengesellschaft;
	}

	public Aktie(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public int getWert(int id, Statement stat) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT aktuellerWert FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(rs.getRow());
		}
		return 000;// Errorcode
	}

	/*
	 * Bevor der Wert der Aktie geändert wird, wird der aktuelle Wert zuerst in der
	 * Werte-Historie gespeichert.
	 */
	public void setWert(int wert) throws SQLException {
//		this.werteHistorie.add(this.getWert(id));
//		this.stat = ConnectionManager.ueberpruefeConnection(stat);
//		stat.execute("INSERT INTO Wertehistorie (Aktie_ID, Wert, Datum) VALUES (" + this.id + ", " + this.aktWert + ", "
//				+ aktDatum + ");");

		this.aktWert = wert;
	}

	public int getDepotinhaber(int id, Statement stat) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Depotinhaber_ID FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(rs.getRow());
		}
		return 000;// Errorcode
	}

	public void setDepotinhaber(int depotinhaber) {
		this.depotinhaber = depotinhaber;
	}

	public int getAktiengesellschaft(int id, Statement stat) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Aktiengesellschaft_ID FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(rs.getRow());
		}
		return 000;// Errorcode
	}

	public void setAktiengesellschaft(int aktiengesellschaft) {
		this.aktiengesellschaft = aktiengesellschaft;
	}

	public int getDepot(int id, Statement stat) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Depot_ID FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(rs.getRow());
		}
		return 000;// Errorcode
	}

	public void setDepot(int depot) {
		this.depot = depot;
	}

//	public Aktie getAktie(int id) throws SQLException {
//		Aktie tempAk = new Aktie(id);
//		tempAk.setWert(tempAk.getWert(id));
//		tempAk.setAktiengesellschaft(tempAk.getAktiengesellschaft(id));
//		tempAk.setDepot(tempAk.getDepot(id));
//		tempAk.setDepotinhaber(tempAk.getDepotinhaber(id));
//		return tempAk;
//	}

}
