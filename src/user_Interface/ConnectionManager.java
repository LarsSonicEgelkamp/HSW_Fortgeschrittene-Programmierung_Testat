package user_Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

	/**
	 * 
	 * @param stat
	 * @return boolean: Es wird true zurückgegeben, wenn noch eine Connection zur
	 *         Datenbank besteht.
	 * @throws SQLException
	 */
	public static Statement ueberpruefeConnection(Statement stat) throws SQLException {

		try {
			if (stat.isClosed()) {
				stat = connectionWiederherstellen(stat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Datenbankverbindung verloren: " + e);
		}

		return stat;
	}

	/**
	 * Versucht die Verbindung zur Datenbank wiederherzustellen.
	 * 
	 * @param stat
	 * @throws SQLException
	 */
	public static Statement connectionWiederherstellen(Statement stat) throws SQLException {
		String databaseURL = "jdbc:mysql://localhost/boersendatenbank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(databaseURL, "user", "user");
		stat = con.createStatement();
		return stat;
	}
}
