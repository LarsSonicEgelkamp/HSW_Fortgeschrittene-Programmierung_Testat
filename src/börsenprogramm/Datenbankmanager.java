package börsenprogramm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Datenbankmanager {

	private Connection co;

	public Datenbankmanager(Connection con) {
		this.co = con;
		try {
			Statement stat = co.createStatement();
//			stat.execute("DROP TABLE Aktiengesellschaft");
//			stat.execute("DROP TABLE Boersenmanager");
//			stat.execute("DROP TABLE Depotinhaber");
//			stat.execute("DROP TABLE Depot");
//			stat.execute("DROP TABLE Aktie");
//			stat.execute("DROP TABLE WerteHistorie");

			erstelleAktiengesellschafttabelle(stat, co);
			erstelleBoersenmanagerTabelle(stat, co);
			erstelleDepotinhaberTabelle(stat, co);
			erstelleDepottabelle(stat, co);
			erstelleAktienTabelle(stat, co);
			erstelleWerteHistorieTabelle(stat, co);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * überprüft, ob die referenzierte Tabelle schon vorhanden ist gibt false
	 * zurück, wenn die Tabelle noch nicht existiert
	 */
	protected boolean pruefeExistenz(Statement stat, Connection con, String tabellenName) throws SQLException {

		DatabaseMetaData dbm;
		try {
			dbm = con.getMetaData();

			ResultSet tables = dbm.getTables(null, null, tabellenName, null);
			if (tables != null && tables.next()) {
				return true;

			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleAktienTabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Aktie") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Aktie ( ID INT, aktuellerWert INT, Aktiengesellschaft_ID INT, Depotinhaber_ID INT, Depot_ID INT, FOREIGN KEY(Aktiengesellschaft_ID) REFERENCES Aktiengesellschaft(ID), FOREIGN KEY(Depotinhaber_ID) REFERENCES Depotinhaber(ID), FOREIGN KEY(Depot_ID) REFERENCES Depot(ID),  CONSTRAINT PK_Aktie_ID PRIMARY KEY (ID));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute(
						"INSERT INTO Aktie(ID, aktuellerWert, Aktiengesellschaft_ID, Depotinhaber_ID, Depot_ID) VALUES \r\n"
								+ "('1', '20', '2', '11', '21'),\r\n" + "('2','10', '3', '12', '23'),\r\n"
								+ "('3','49', '1', '11', '21'),\r\n" + "('4','45', '4', '10', '20'),\r\n"
								+ "('5','56', '4', '12', '23'),\r\n" + "('6','17', '5', '10', '22'),\r\n"
								+ "('7','15', '2', '12', '24');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}


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
