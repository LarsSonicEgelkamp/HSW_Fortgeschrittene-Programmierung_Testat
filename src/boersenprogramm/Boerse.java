package boersenprogramm;



import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Filemanager.CSV_Manager;

import user_Interface.ConnectionManager;

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
 * 
 * @return: Eine ArrayList mit Depots gefüllt, mit den Attributen ID und Inhaber ID.
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

	public void neueAktie(int id, int wert) throws SQLException {
		ResultSet aktieVorhanden = stat.executeQuery("SELECT ID FROM Aktien WHERE ID = " + id + ";");
		if (aktieVorhanden.next()) {
			System.out.println("Diese Aktie existiert schon. Bitte wÃ¤hlen sie eine andere ID.");
		} else {
			Aktie aktie = new Aktie(stat, id, wert);
			stat.execute("INSERT INTO Aktie(ID, aktuellerWert) VALUES (" + id + ", " + wert + ");");
			this.aktienListe.add(aktie);
		}
	}

	public void neuesDepot(int id, int inhaberName) {
		Depot depot = new Depot(id, inhaberName, stat);
		this.depotListe.add(depot);
	}

	public ArrayList<String> alleAktienLesen(Statement stat) {
		ArrayList<String> alleAktien = new ArrayList<String>();

		try {
			ResultSet alleAktienSQL = stat.executeQuery("SELECT ID FROM Aktie;");

			for (int j = 0; alleAktienSQL.next(); j++) {
				alleAktien.add(alleAktienSQL.getString(j));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return alleAktien;
	}

	public void abfragenAktienHistorie(Statement stat, Aktie aktienID) throws SQLException {

		ResultSet aktienHistorie = stat
				.executeQuery("SELECT ID FROM Wertehistorie WHERE ID = " + aktienID.getId() + ";");

		if (aktienHistorie.next()) {
			aktienListe.add(aktienID);
		}
	}

}
