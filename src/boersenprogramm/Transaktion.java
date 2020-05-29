package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user_Interface.ConnectionManager;

public class Transaktion {

	private int  transaktionsID;
	private int aktienID;
	private int verkaufsDepotID;
	private int ankaufsDepotID;
	private int verkaufswert;
	private Statement stat;

	public Transaktion(int id, Statement stat) {
		this.transaktionsID = id;
		this.stat = stat;
	}
	
	public int getTransaktionsID() {
		return transaktionsID;
	}

	public int getAktienID(int transaktionsID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Aktie_ID FROM Transaktion WHERE ID = " + transaktionsID + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setAktienID(int aktienID) {
		this.aktienID = aktienID;
	}

	public int getVerkaufsDepotID(int transaktionsID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT VerkaufsDepot_ID FROM Transaktion WHERE ID = " + transaktionsID + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setVerkaufsDepotID(int verkaufsDepotID) {
		this.verkaufsDepotID = verkaufsDepotID;
	}

	public int getAnkaufsDepotID(int transaktionsID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT AnkaufsDepot_ID FROM Transaktion WHERE ID = " + transaktionsID + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setAnkaufsDepotID(int ankaufsDepotID) {
		this.ankaufsDepotID = ankaufsDepotID;
	}

	public int getVerkaufswert(int transaktionsID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Verkaufswert FROM Transaktion WHERE ID = " + transaktionsID + ";");
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setVerkaufswert(int verkaufswert) {
		this.verkaufswert = verkaufswert;
	}
}
