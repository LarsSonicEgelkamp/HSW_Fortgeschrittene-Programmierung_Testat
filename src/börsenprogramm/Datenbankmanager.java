package börsenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import user_Interface.ConnectionManager;

public class Datenbankmanager {

	private Statement stat;

	public Datenbankmanager(Statement stat) {
		this.stat = stat;
	}

	public ArrayList<Integer> getAktienIDs() throws SQLException {

		ArrayList<Integer> ids = new ArrayList<Integer>();
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT ID FROM Aktie;");
		while (rs.next()) {
			ids.add(rs.getInt(rs.getRow()));
		}
		return ids;
	}
}
