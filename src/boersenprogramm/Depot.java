package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

/**
 * Beschreibt das Objekt Depot der Börsenanwendung. Durch die Konstruktoren und
 * die set-Methoden kann ein neues Depot erstellt werden. Die get-Methoden
 * suchen anhand der übergebenen DepotID die gefragten Werte aus der Datenbank.
 */
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
			return rs.getInt(1);
		}
		return 000;// Errorcode
	}

	public void setInhaber(int inhaberID) {
		this.inhaberID = inhaberID;
	}

	/**
	 * Sucht alle Aktien_IDs heraus, die in dem Depot mit der übergebenen Depot_ID
	 * liegen.
	 * 
	 * @param depotID
	 * @return: ArrayList<Integer>, mit allen Aktien_IDs, die in diesem Depot
	 *          liegen.
	 * @throws SQLException
	 */
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
