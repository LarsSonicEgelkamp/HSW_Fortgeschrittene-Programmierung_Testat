package Ordermanager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;


import Filemanager.CSV_Manager;
import boersenprogramm.Aktie;
import boersenprogramm.Depot;
import boersenprogramm.Order;
import boersenprogramm.Transaktionen;
import boersenprogramm.Umsaetze;
import user_Interface.ConnectionManager;

public class Orderverarbeitung {

	private CSV_Manager csvmag = new CSV_Manager();

	private Orderliste orderListe = new Orderliste();
	private Orderliste verkaufsListe = new Orderliste();
	private Orderliste ankaufsListe = new Orderliste();

	private ArrayList<Transaktionen> transaktionsListe = new ArrayList<Transaktionen>();
	private ArrayList<Transaktionen> umsaetzeAlsTransaktionen = new ArrayList<Transaktionen>();
	ArrayList<Umsaetze> umsaetze = new ArrayList<Umsaetze>();

	private String gegenkonto = "1234";
	private Date datum;

	private Statement stat;

	public Orderverarbeitung(Statement stat, Date datum) throws IOException, SQLException {
		this.stat = stat;
		this.datum = datum;
		this.orderListe = csvmag.getKorrekteOrders();
		if (orderListe.size() > 0) {
			this.ermittleTransaktionen();
			this.transaktionenDurchfuehren();
			this.neueAktienWerteBerechnen();
			this.umsaetzeAnlegen(datum);
			this.umsaetzeSchreiben();
		}
	}

	/**
	 * Ruft den CSV-Manager auf, womit die Umsaetzedatei geschrieben wird.
	 * 
	 * @throws IOException
	 */
	private void umsaetzeSchreiben() throws IOException {
		csvmag.schreibeUmsaetze(umsaetze);

	}

	/**
	 * Sammelt alle Daten, die für die Umsaetzedatei in eine ArrayList<Umsaetze>
	 * benötigt werden.
	 * 
	 * @param datum, das aktuelle Datum
	 * @throws SQLException
	 */
	private void umsaetzeAnlegen(Date datum) throws SQLException {
		umsaetze = new ArrayList<Umsaetze>();
		for (Transaktionen t : umsaetzeAlsTransaktionen) {
			int zaehler = 0;
			while (zaehler < 2) {
				Umsaetze um = new Umsaetze();
				um.setVerwendungszweck(t.getId());
				um.setBetrag(t.getVerkaufswert());
				um.setAusfuehrdatum(datum);
				um.setTransaktionskosten(t.getTransaktionskosten());
				if (zaehler == 0) {

					this.stat = ConnectionManager.ueberpruefeConnection(stat);
					ResultSet rs = stat.executeQuery(
							"SELECT Depotinhaber_ID FROM Depot WHERE ID = " + t.getAnkaufsDepotID() + ";");
					if (rs.next()) {
						String zahlerIban = rs.getString(1);
						um.setZahlerIban(zahlerIban);
					}
					um.setZahlungsempfaengerIban(gegenkonto);
					umsaetze.add(um);
					zaehler++;
				} else {
					this.stat = ConnectionManager.ueberpruefeConnection(stat);
					ResultSet rs = stat.executeQuery(
							"SELECT Depotinhaber_ID FROM Depot WHERE ID = " + t.getVerkaufsDepotID() + ";");
					if (rs.next()) {
						String zahlungsempfaengerIban = rs.getString(1);
						um.setZahlungsempfaengerIban(zahlungsempfaengerIban);
					}
					um.setZahlerIban(gegenkonto);
					umsaetze.add(um);
					zaehler++;
				}
			}
		}

	}

	/**
	 * Ermittelt den neuen Wert der Aktien einer Aktiengesellschaft anhand des
	 * Preises zudem am meisten Transaktionen stattfanden.
	 * 
	 * @throws SQLException
	 */
	private void neueAktienWerteBerechnen() throws SQLException {
		ArrayList<Integer> aktiengruppenIDs = new ArrayList<Integer>();
		for (Transaktionen t : transaktionsListe) {
			int id = t.getAktiengesellschaftsID();
			if (!aktiengruppenIDs.contains(id)) {
				aktiengruppenIDs.add(id);
			}
		}

		for (int id : aktiengruppenIDs) {
			int anzahlTransaktionen = 0;
			Transaktionen trn = new Transaktionen();

			for (Transaktionen t : transaktionsListe) {
				if (t.getAktiengesellschaftsID() == id) {
					if (anzahlTransaktionen < t.getMenge()) {
						anzahlTransaktionen = t.getMenge();
						trn = t;
					}

					this.updateWertehistorie(id);

					this.stat = ConnectionManager.ueberpruefeConnection(stat);
					stat.execute("UPDATE Aktie SET aktuellerWert = " + trn.getVerkaufswert()
							+ " WHERE Aktiegesellschaft_ID = " + trn.getAktiengesellschaftsID() + ";");
				}
			}
		}

	}

	/**
	 * Aktualisiert die Wertehistorie bevor der aktuelle Wert verändert wird.
	 * 
	 * @param aktiengesellschaftsID: alle Aktien einer Aktiengesellschaft sind in
	 *                               einer Gruppe und haben denselben Wert.
	 * @throws SQLException
	 */
	private void updateWertehistorie(int aktiengesellschaftsID) throws SQLException {

		ArrayList<Integer> alleAktienIDs = new ArrayList<Integer>();
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat
				.executeQuery("SELECT ID FROM Aktie WHERE Aktiengesellschaft_ID = " + aktiengesellschaftsID + ";");
		while (rs.next()) {
			alleAktienIDs.add(rs.getInt(1));
		}
		for (int id : alleAktienIDs) {
			Aktie tempAk = new Aktie(id, stat);
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			stat.execute("INSERT INTO Wertehistorie(Aktie_ID, Wert, Datum) VALUES\r\n" + "('" + id + "', '"
					+ tempAk.getWert(id) + "', '" + datum + "');");
		}

	}

	/**
	 * Trägt die übergebene Transaktion als neue Transaktion in die Datenbank ein
	 * und weißt ihr dabei eine einmalige ID zu. Dazu wird die ID immer weiter
	 * hochgezählt.
	 * 
	 * @param transaktion: eine Transaktion
	 * @param aktienID
	 * @throws SQLException
	 */
	private void transaktionenEintragen(Transaktionen transaktion, int aktienID) throws SQLException {

		for (Transaktionen t : transaktionsListe) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat.executeQuery("SELECT MAX(ID) FROM Transaktion;");
			if (rs.next()) {
				int maxID = rs.getInt(1);
				t.setId(maxID + 1);
				this.umsaetzeAlsTransaktionen.add(t);
				this.stat = ConnectionManager.ueberpruefeConnection(stat);
				stat.execute(
						"INSERT INTO Transaktion(ID, Aktie_ID, Verkaufswert, VerkaufsDepot_ID, AnkaufsDepot_ID) VALUES\r\n"
								+ "('" + maxID + 1 + "', '" + aktienID + "', '" + transaktion.getVerkaufswert() + "', '"
								+ transaktion.getVerkaufsDepotID() + "', '" + transaktion.getAnkaufsDepotID() + "');");
			}

		}

	}

	/**
	 * Die Daten der von den Transaktionen betroffenen Aktien werden geändert. Es
	 * werden die Depot_ID und die Depotinhaber_ID geändert.
	 * 
	 * @throws SQLException
	 */
	private void transaktionenDurchfuehren() throws SQLException {
		for (Transaktionen t : transaktionsListe) {
			ArrayList<Integer> idsMoeglicherAktien = new ArrayList<Integer>();
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ResultSet rs = stat.executeQuery("SELECT ID FROM Aktie WHERE Depot_ID = " + t.getVerkaufsDepotID()
					+ " AND Aktiengesellschaft_ID = " + t.getAktiengesellschaftsID() + ";");
			while (rs.next() && idsMoeglicherAktien.size() - 1 < t.getMenge()) {
				idsMoeglicherAktien.add(rs.getInt(1));
			}
			int zaehler = 0;
			Depot tempDe = new Depot(t.getAnkaufsDepotID(), stat);
			int depotinhaberID = tempDe.getInhaber(t.getAnkaufsDepotID());
			while (zaehler <= t.getMenge() && zaehler < idsMoeglicherAktien.size() - 1) {
				this.stat = ConnectionManager.ueberpruefeConnection(stat);
				stat.execute("UPDATE Aktie SET Depot_ID = " + t.getAnkaufsDepotID() + " WHERE ID = "
						+ idsMoeglicherAktien.get(zaehler) + ";");

				stat.execute("UPDATE Aktie SET Depotinhaber_ID = " + depotinhaberID + " WHERE ID = "
						+ idsMoeglicherAktien.get(zaehler) + ";");
				this.transaktionenEintragen(t, idsMoeglicherAktien.get(zaehler));
				zaehler++;
			}
		}

	}

	/**
	 * Es werden die Ankaufsanfragen und Verkaufsanfragen abgeglichen, wodurch die
	 * möglichen Transaktionen ermittelt werden.
	 */
	private void ermittleTransaktionen() {
		this.verkaufsListe = this.orderListe.getVerkaufsliste();
		this.ankaufsListe = this.orderListe.getAnkaufsliste();
		int index = 0;

		while (index < this.ankaufsListe.size() && !this.verkaufsListe.isEmpty() && !this.ankaufsListe.isEmpty()) {
			Order tempOr = this.ankaufsListe.get(index);
			Order passenderVerkauf = this.verkaufsListe.getpassendeOrder(tempOr);
			if (passenderVerkauf != null) {
				int verkaufIndex = this.verkaufsListe.indexOf(passenderVerkauf);

				if (tempOr.getMenge() > passenderVerkauf.getMenge()) {
					int diffMenge = tempOr.getMenge() - passenderVerkauf.getMenge();
					Transaktionen trn = new Transaktionen();
					trn.setAktiengesellschaftsID(tempOr.getAktienGruppenID());
					trn.setAnkaufsDepotID(tempOr.getDepotID());
					trn.setVerkaufsDepotID(passenderVerkauf.getDepotID());
					trn.setVerkaufswert(this.berechnePreis(tempOr.getStueckpreis(), passenderVerkauf.getStueckpreis()));
					trn.setTransaktionskosten(trn.getVerkaufswert());
					trn.setMenge(passenderVerkauf.getMenge());
					this.transaktionsListe.add(trn);
					tempOr.setMenge(diffMenge);
					this.ankaufsListe.add(tempOr);
					this.verkaufsListe.remove(verkaufIndex);

				} else if (tempOr.getMenge() < passenderVerkauf.getMenge()) {
					int diffMenge = passenderVerkauf.getMenge() - tempOr.getMenge();
					Transaktionen trn = new Transaktionen();
					trn.setAktiengesellschaftsID(tempOr.getAktienGruppenID());
					trn.setAnkaufsDepotID(tempOr.getDepotID());
					trn.setVerkaufsDepotID(passenderVerkauf.getDepotID());
					trn.setVerkaufswert(this.berechnePreis(tempOr.getStueckpreis(), passenderVerkauf.getStueckpreis()));
					trn.setTransaktionskosten(trn.getVerkaufswert());
					trn.setMenge(tempOr.getMenge());
					this.transaktionsListe.add(trn);
					passenderVerkauf.setMenge(diffMenge);
					this.verkaufsListe.add(passenderVerkauf);
					this.verkaufsListe.remove(verkaufIndex);

				} else if (tempOr.getMenge() == passenderVerkauf.getMenge()) {
					Transaktionen trn = new Transaktionen();
					trn.setAktiengesellschaftsID(tempOr.getAktienGruppenID());
					trn.setAnkaufsDepotID(tempOr.getDepotID());
					trn.setVerkaufsDepotID(passenderVerkauf.getDepotID());
					trn.setVerkaufswert(this.berechnePreis(tempOr.getStueckpreis(), passenderVerkauf.getStueckpreis()));
					trn.setTransaktionskosten(trn.getVerkaufswert());
					trn.setMenge(tempOr.getMenge());
					this.transaktionsListe.add(trn);
					this.verkaufsListe.remove(verkaufIndex);
				}

			} else {
				index++;
			}
		}
	}

	/**
	 * als Verkaufspreis wird der Mittelwert zwischen Ankaufs- und Verkaufspreis
	 * berechnet.
	 * 
	 * @param ankaufsP:  Maximalpreis des Ankaufs
	 * @param verkaufsP: Mindestpreis des Verkaufs
	 * @return: den Verkaufspreis
	 */
	public int berechnePreis(int ankaufsP, int verkaufsP) {
		int differenz = ankaufsP - verkaufsP;
		if (differenz > 0) {
			differenz = differenz / 2;
		}
		int preis = ankaufsP - differenz;
		return preis;
	}

}
