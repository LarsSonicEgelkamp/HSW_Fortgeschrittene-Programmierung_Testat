package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public Aktie(int id, Statement stat) {
		this.id = id;
		this.stat = stat;
	}

	public int getId() {
		return id;
	}

	public int getWert(int id) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT aktuellerWert FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	/*
	 * Bevor der Wert der Aktie geaendert wird, wird der aktuelle Wert zuerst in der
	 * Werte-Historie gespeichert.
	 */
	public void setWert(int wert) throws SQLException {
		this.aktWert = wert;
	}

	public int getDepotinhaber(int id) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Depotinhaber_ID FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setDepotinhaber(int depotinhaber) {
		this.depotinhaber = depotinhaber;
	}

	public int getAktiengesellschaft(int id) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Aktiengesellschaft_ID FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setAktiengesellschaft(int aktiengesellschaft) {
		this.aktiengesellschaft = aktiengesellschaft;
	}

	public int getDepot(int id) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Depot_ID FROM Aktie WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setDepot(int depot) {
		this.depot = depot;
	}
}
