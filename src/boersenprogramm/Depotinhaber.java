package boersenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import user_Interface.ConnectionManager;

/**
 * Beschreibt das Objekt Depotinhaber der Börsenanwendung. Durch die
 * Konstruktoren und die set-Methoden kann ein neueer Depotinhaber erstellt
 * werden.
 */
public class Depotinhaber {

	private int id;
	private String name;
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

	/**
	 * Sucht zu einer Depotinhaber_ID den entsprechenden Namen aus der Datenbank.
	 * 
	 * @param inhaberID
	 * @return: den Namen des Depotinhaber mit der übergebenen ID
	 * @throws SQLException
	 */
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
}
