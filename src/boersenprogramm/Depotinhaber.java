package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

public class Depotinhaber {

	private int id;
	private String name;
	private static ArrayList<Integer> meineDepots = new ArrayList<Integer>();
	private Statement stat;

	public Depotinhaber(int id, String name, Statement stat) {
		this.id = id;
		this.name = name;
		this.stat = stat;
	}

	public Depotinhaber(int id, Statement stat) {
		this.id = id;
		this.stat = stat;
	}

	public int getId() {
		return id;
	}

	public String getName(int inhaberID) throws SQLException {
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Name FROM Depotinhaber WHERE ID = " + inhaberID + ";");
		if (rs.next()) {
			return rs.getString(1);
		}
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	private void neuesDepot(int depotID) {
		this.meineDepots.add(depotID);
	}

	public static ArrayList<Integer> getMeineDepots() {
		return meineDepots;
	}
	
	public void verkaufsVerlauf() {
		
	}
	
	
}
