package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

public class Datenbankmanager {

	private Statement stat;

	public Datenbankmanager(Statement stat) {
		this.stat = stat;
	}

	/**
	 * Sucht die zu dem User zugehörigen Aktien_IDs heraus. Bei einem Zugriff durch
	 * den Börsenmanager werden alle Aktien_IDs zurückgegeben.
	 * 
	 * @param user: aktueller User
	 * @return: ArrayList<Integer>, alle dem User zugeordneten Aktien_IDs
	 * @throws SQLException
	 */
	public ArrayList<Integer> getAktienIDs(AktuellerUser user) throws SQLException {

		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (user.getUserArt().contentEquals("boersenmanager")) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat.executeQuery("SELECT ID FROM Aktie ;");
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
		} else {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat
					.executeQuery("SELECT ID FROM Aktie WHERE " + user.getUserArt() + "_ID = " + user.getId() + ";");
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
		}
		return ids;
	}

	/**
	 * Sucht die zu dem User zugehörigen Depot_IDs heraus. Bei einem Zugriff durch
	 * den Börsenmanager werden alle Depot_IDs zurückgegeben.
	 * 
	 * @param user: aktueller User
	 * @return: ArrayList<Integer>, alle dem User zugeordneten Depot_IDs
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Integer> getDepotIDs(AktuellerUser user) throws SQLException, IllegalArgumentException {

		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (user.getUserArt().contentEquals("aktiengesellschaft")) {
			throw new IllegalArgumentException("Der User Aktiengesellschaft hat keinen Zugriff auf die Depots");
		} else if (user.getUserArt().contentEquals("boersenmanager")) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat.executeQuery("SELECT ID FROM Depot;");
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
		} else {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat
					.executeQuery("SELECT ID FROM Depot WHERE " + user.getUserArt() + "_ID = " + user.getId() + ";");
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
		}
		return ids;
	}

	/**
	 * Sucht die zu dem User zugehörigen Transaktions_IDs heraus. Bei einem Zugriff
	 * durch den Börsenmanager werden alle Transaktions_IDs zurückgegeben.
	 * 
	 * @param user: aktueller User
	 * @return: ArrayList<Integer>, alle dem User zugeordneten Transaktion_IDs
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Integer> getTransaktionsListe(AktuellerUser user) throws SQLException, IllegalArgumentException {

		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (user.getUserArt().contentEquals("aktiengesellschaft")) {
			ArrayList<Integer> aktienIDs = this.getAktienIDs(user);
			for (int id : aktienIDs) {
				this.stat = ConnectionManager.ueberpruefeConnection(stat);
				String sql = "SELECT ID FROM Transaktion WHERE Aktien_ID = " + Integer.toString(id) + ";";
				System.out.println(sql);
				ResultSet rs = stat.executeQuery("SELECT ID FROM Transaktion WHERE Aktie_ID = " + Integer.toString(id) + ";");
				while (rs.next()) {
					ids.add(rs.getInt(1));
				}
			}
		} else if (user.getUserArt().contentEquals("boersenmanager")) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat.executeQuery("SELECT ID FROM Depot;");
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
		} else {
			ArrayList<Integer> depotIDs = this.getDepotIDs(user);
			for (int id : depotIDs) {
				this.stat = ConnectionManager.ueberpruefeConnection(stat);
				ResultSet rs = stat.executeQuery("SELECT ID FROM Transaktion WHERE VerkaufsDepot_ID = " + id
						+ " XOR AnkaufsDepot_ID =" + id + ";");
				while (rs.next()) {
					ids.add(rs.getInt(1));
				}
			}
		}
		return ids;
	}
}
