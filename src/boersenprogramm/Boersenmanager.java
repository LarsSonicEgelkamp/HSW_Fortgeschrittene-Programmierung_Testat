package boersenprogramm;

import java.awt.Component;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import Filemanager.CSV_Manager;

public class Boersenmanager {

	public Boersenmanager() {

	}

	static ArrayList<String> aktienListe = new ArrayList<String>();

	public static ArrayList<String> getAktienListe() {
		return aktienListe;
	}

	public void setAktienListe(ArrayList<String> aktienListe) {
		this.aktienListe = aktienListe;
	}

	Connection con;
//	Datenbankmanager datenbank = new Datenbankmanager(con);

	public void createDepot(Statement stat, int depotID) throws SQLException {
		String sqlBefehlTabelleErstellen = "CREATE TABLE Depot(" + depotID
				+ " INT, AktieID, CONSTRAINT PK_Depot_ID PRIMARY KEY (ID))";
		stat.execute(sqlBefehlTabelleErstellen);
	}

	public void aktieAnlegen(Statement stat, int aktienID, int aktuellerWert, int AktiengesellschaftsID,
			int DepotinhaberID, int depotID) throws SQLException {
		try {
			stat.execute(
					"INSERT INTO Aktie(ID, aktuellerWert, Aktiengesellschaft_ID, Depotinhaber_ID, Depot_ID) VALUES \r\n"
							+ "('" + aktienID + "', '" + aktuellerWert + "', '" + AktiengesellschaftsID + "', '"
							+ DepotinhaberID + "', '" + depotID + "'),\r\n");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void ordersAusführen(Statement stat) throws IOException, SQLException {
		CSV_Manager readCSV = new CSV_Manager();

		ArrayList<String> records = new ArrayList<String>();
		JFileChooser jfc = new JFileChooser();

		int rueckgabeWert = jfc.showOpenDialog(null);

		if (rueckgabeWert == JFileChooser.APPROVE_OPTION) {
			// Ausgabe der ausgewaehlten Datei
			records = readCSV.readCSV(jfc.getSelectedFile().getName());
		}

		// TODO hier muss dann noch geguckt werden welche Pfad da rein muss

		// AktienID, DepotID
		String aktienID, depotID;

		depotID = records.get(0);
		aktienID = records.get(1);

		// TODO hier muss noch der SQL Befehl vernünfitg gemacht werden
		stat.execute("INSERT INTO depot(ID,AktienID) VALUES \r\n" + "('");

	}

}
