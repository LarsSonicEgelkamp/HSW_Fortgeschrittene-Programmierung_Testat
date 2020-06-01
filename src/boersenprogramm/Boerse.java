package boersenprogramm;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

/**
 * Verwaltet die grundsätzlichen Funktionen der Börsenanwendung mit Außnahme der
 * Orderverarbeitung.
 *
 */
public class Boerse {

	private ArrayList<Aktie> aktienListe = new ArrayList<Aktie>();
	private ArrayList<Depot> depotListe = new ArrayList<Depot>();
	private ArrayList<Transaktion> transaktionsListe = new ArrayList<Transaktion>();
	private Statement stat;
	private Datenbankmanager dm;

	public Boerse(Statement stat) {
		this.stat = stat;
		dm = new Datenbankmanager(stat);
	}

	/**
	 * Sucht alle Aktien, die für den aktuellen User relevant sind.
	 * 
	 * @return: Eine ArrayList mit Aktien gefüllt, mit den Attributen ID, aktueller
	 *          Wert, Aktiengesellschaft ID, Depot ID und Depotinhaber ID.
	 * @throws SQLException
	 */
	public ArrayList<Aktie> getAktienListe(AktuellerUser user) throws SQLException {
		ArrayList<Integer> aktienIDListe;
		aktienIDListe = dm.getAktienIDs(user);
		for (int id : aktienIDListe) {
			Aktie tempAk = new Aktie(id, stat);
			tempAk.setWert(tempAk.getWert(tempAk.getId()));
			tempAk.setAktiengesellschaft(tempAk.getAktiengesellschaft(tempAk.getId()));
			tempAk.setDepot(tempAk.getDepot(tempAk.getId()));
			tempAk.setDepotinhaber(tempAk.getDepotinhaber(tempAk.getId()));
			aktienListe.add(tempAk);
		}
		return aktienListe;
	}

	/**
	 * Sucht alle Depots, die für den aktuellen User relevant sind.
	 * 
	 * @return: Eine ArrayList mit Depots gefüllt, mit den Attributen ID und Inhaber
	 *          ID.
	 * @throws SQLException
	 */
	public ArrayList<Depot> getDepotListe(AktuellerUser user) throws SQLException {

		ArrayList<Integer> depotIDListe;
		depotIDListe = dm.getDepotIDs(user);
		for (int id : depotIDListe) {
			Depot tempDe = new Depot(id, stat);
			tempDe.setInhaber(tempDe.getInhaber(id));
			depotListe.add(tempDe);
		}
		return depotListe;
	}

	/**
	 * Sucht alle Transaktionen, die für den aktuellen User relevant sind.
	 * 
	 * @param user: aktueller User
	 * @return: ArrayList<Transaktion>, eine Liste mit allen Transaktionen, die für
	 *          den aktuellen User relevant sind
	 * @throws SQLException
	 */
	public ArrayList<Transaktion> getTransaktionsListe(AktuellerUser user) throws SQLException {
		ArrayList<Integer> transaktionsIDListe;
		transaktionsIDListe = dm.getTransaktionsListe(user);
		for (int id : transaktionsIDListe) {
			Transaktion tempTa = new Transaktion(id, stat);
			tempTa.setAktienID(tempTa.getAktienID(id));
			tempTa.setVerkaufswert(tempTa.getVerkaufswert(id));
			tempTa.setVerkaufsDepotID(tempTa.getVerkaufsDepotID(id));
			tempTa.setAnkaufsDepotID(tempTa.getAnkaufsDepotID(id));
			transaktionsListe.add(tempTa);
		}
		return transaktionsListe;
	}

	/**
	 * Legt eine neue Aktie an.
	 * 
	 * @param id:   ID der neuen Aktie
	 * @param wert: Initialwert der neuen Aktie
	 * @throws SQLException
	 */
	public void neueAktie(int id, int wert, int aktiengesellschaftID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet aktieVorhanden = stat.executeQuery("SELECT ID FROM Aktien WHERE ID = " + id + ";");
		if (aktieVorhanden.next()) {
			throw new IllegalArgumentException("Diese Aktie existiert schon. Bitte geben sie eine andere an.");
		} else {
			Aktie aktie = new Aktie(stat, id, wert);
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			stat.execute("INSERT INTO Aktie(ID, aktuellerWert) VALUES (" + id + ", " + wert + ");");
			this.aktienListe.add(aktie);
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			stat.execute("UPDATE Aktie SET Aktiengesellschaft_ID = "+aktiengesellschaftID+" WHERE (ID="+id+");");
		}
	}

	public void neuesDepot(int id, int inhaberName) {
		Depot depot = new Depot(id, inhaberName, stat);
		this.depotListe.add(depot);
	}

	/**
	 * Liest alle IDs, der in der Datenbank befindlichen Aktien aus.
	 * 
	 * @param stat
	 * @return: ArrayList<String>, mit allen IDs
	 * @throws SQLException
	 */
	public ArrayList<String> alleAktienLesen(Statement stat) throws SQLException {
		ArrayList<String> alleAktien = new ArrayList<String>();

		try {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet alleAktienSQL = stat.executeQuery("SELECT ID FROM Aktie;");

			for (int j = 0; alleAktienSQL.next(); j++) {
				alleAktien.add(alleAktienSQL.getString(j));
			}

		} catch (SQLException e) {
			throw new SQLException(e);
		}

		return alleAktien;
	}

	/**
	 * Sucht zu der übergebenen AktienID die Wertehistorie heraus.
	 * 
	 * @param stat
	 * @param aktienID
	 * @throws SQLException
	 */
	public void abfragenAktienHistorie(Statement stat, Aktie aktienID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet aktienHistorie = stat
				.executeQuery("SELECT ID FROM Wertehistorie WHERE ID = " + aktienID.getId() + ";");

		if (aktienHistorie.next()) {
			aktienListe.add(aktienID);
		}
	}

}
