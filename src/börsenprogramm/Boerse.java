package börsenprogramm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import Filemanager.CSV_Manager;

public class Boerse {

	private ArrayList<Aktie> aktienListe = new ArrayList<Aktie>();
	private ArrayList<Depot> depotListe = new ArrayList<Depot>();

	public ArrayList<Aktie> getAktienListe() {
		return aktienListe;
	}

	public ArrayList<Depot> getDepotListe() {
		return depotListe;
	}

	public void neueAktie(int id, int wert) {
		Aktie aktie = new Aktie(null, id, wert);
		this.aktienListe.add(aktie);
	}

	public void neuesDepot(int id, String inhaberName) {
		Depot depot = new Depot(id, inhaberName);
		this.depotListe.add(depot);
	}
	
	public ArrayList<String> alleAktienLesen (Statement stat, Connection con) {
		ArrayList<String> alleAktien = new ArrayList<String>();
		
		try {
			alleAktien.addAll( stat.executeQuery("SELECT ID FROM Aktie;"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return alleAktien;
	}
	
	public void abfragenAktienHistorie (Statement stat, Connection con, Aktie aktienID) throws SQLException {
		
		ResultSet aktienHistorie = stat.executeQuery("SELECT ID FROM Wertehistorie WHERE ID = " + aktienID.getId()+";");
		
		if (aktienHistorie.next()) {
			aktienListe.add(aktienID);
		}
	}
	

}
