package börsenprogramm;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

public class Boerse {

	private ArrayList<Aktie> aktienListe = new ArrayList<Aktie>();
	private ArrayList<Depot> depotListe = new ArrayList<Depot>();
	Statement stat;

	public Boerse(Statement stat) {
		this.stat = stat;
	}

	public ArrayList<Aktie> getAktienListe() throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet alleAktienIDs = stat.executeQuery("SELECT ID FROM Aktie;");
		Aktie tempAk;
		while (alleAktienIDs.next()) {
			int id = alleAktienIDs.getInt(1);
			tempAk = new Aktie(id);
			aktienListe.add(tempAk);
		}
		for (Aktie ak : aktienListe) {
			ak.setWert(ak.getWert(ak.getId(), stat));
			ak.setAktiengesellschaft(ak.getAktiengesellschaft(ak.getId(), stat));
			ak.setDepot(ak.getDepot(ak.getId(), stat));
			ak.setDepotinhaber(ak.getDepotinhaber(ak.getId(), stat));
		}
		return aktienListe;
	}

	public ArrayList<Depot> getDepotListe() {
		return depotListe;
	}

	public void neueAktie(int id, int wert) throws SQLException {
		ResultSet aktieVorhanden = stat.executeQuery("SELECT ID FROM Aktien WHERE ID = " + id + ";");
		if (aktieVorhanden.next()) {
			System.out.println("Diese Aktie existiert schon. Bitte wählen sie eine andere ID.");
		} else {
			Aktie aktie = new Aktie(stat, id, wert);
			stat.execute("INSERT INTO Aktie(ID, aktuellerWert) VALUES (" + id + ", " + wert + ");");
			this.aktienListe.add(aktie);
			// Aktiengesellschaft_ID zu Aktie hinzufügen, prüfen, ob Aktie schon
			// Aktiengesellschaft_ID hat
//			stat.execute("UPDATE Aktie SET Aktiengesellschaft_ID = "+Aktiengesellschaft_ID+" WHERE (ID="+id+");"); // wie bekomme ich die ID
		}
	}

	public void neuesDepot(int id, String inhaberName) {
		Depot depot = new Depot(id, inhaberName);
		this.depotListe.add(depot);
	}
}
