package b�rsenprogramm;

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

	public ArrayList<Integer> getTransaktionsListe(AktuellerUser user) throws SQLException, IllegalArgumentException {

		ArrayList<Integer> ids = new ArrayList<Integer>();
		if (user.getUserArt().contentEquals("aktiengesellschaft")) {
			ArrayList<Integer> aktienIDs = this.getAktienIDs(user);
			for (int id : aktienIDs) {
				this.stat = ConnectionManager.ueberpruefeConnection(stat);
				ResultSet rs = stat.executeQuery("SELECT ID FROM Transaktion WHERE Aktien_ID = " + id + ";");
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
