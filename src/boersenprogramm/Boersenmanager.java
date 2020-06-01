package boersenprogramm;

import java.awt.Component;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import Filemanager.CSV_Manager;
import user_Interface.ConnectionManager;

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
	public void createDepot(Statement stat, String depotID,String depotinhaberID) throws SQLException {
		String sqlBefehlTabelleErstellen = "INSERT INTO Depot (ID, Depotinhaber_ID) Values ('"+depotID+"' ,'"+ depotinhaberID+"');";
		stat.execute(sqlBefehlTabelleErstellen);
	}

	public void aktieAnlegen(Statement stat, String aktienID, String aktuellerWert, String AktiengesellschaftsID,
			String DepotinhaberID, String depotID) throws SQLException {
		try {
			stat = ConnectionManager.ueberpruefeConnection(stat);
			stat.execute(
					"INSERT INTO Aktie(ID, aktuellerWert, Aktiengesellschaft_ID, Depotinhaber_ID, Depot_ID) VALUES "
							+ "('" + aktienID + "', '" + aktuellerWert + "', '" + AktiengesellschaftsID + "', '"
							+ DepotinhaberID + "', '" + depotID + "');");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}


}
