package börsenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

public class Depot {

	private int id;
	private int inhaberID;
	private ArrayList<Integer> alleAktien = new ArrayList<Integer>();
	private ArrayList<Order> meineTransaktionen = new ArrayList<Order>();
	Statement stat;

	public Depot(int id, int inhaber, Statement stat) {
		this.id = id;
		this.inhaberID = inhaber;
		this.stat = stat;
	}

	public Depot(int id, Statement stat) {
		this.id = id;

		this.stat = stat;
	}

	public int getId() {
		return id;
	}

	public int getInhaber(int id) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Depotinhaber_ID FROM Depot WHERE ID = " + id + ";");
		if (rs.next()) {
			return rs.getInt(rs.getRow());
		}
		return 000;// Errorcode
	}

	public void setInhaber(int inhaberID) {
		this.inhaberID = inhaberID;
	}

	public ArrayList<Integer> getAlleAktienIDs(int depotID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT ID FROM Aktie WHERE Depot_ID = " + depotID + ";");
		while (rs.next()) {
			int id = rs.getInt(1);
			alleAktien.add(id);
		}
		return alleAktien;
	}

	public void neueAktie(int aktienID) {
		this.alleAktien.add(aktienID);
	}

	public ArrayList<Order> getMeineTransaktionen() {
		return meineTransaktionen;
	}

	public void neueTransaktion(Order order) {
		this.meineTransaktionen.add(order);
	}
}
