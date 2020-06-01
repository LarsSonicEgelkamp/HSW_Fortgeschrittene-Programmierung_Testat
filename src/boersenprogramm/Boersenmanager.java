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
/**
 * 
 * @param stat
 * @param depotID
 * @throws SQLException
 */
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
}
