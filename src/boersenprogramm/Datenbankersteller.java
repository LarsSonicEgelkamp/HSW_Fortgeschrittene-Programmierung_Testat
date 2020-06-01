package boersenprogramm;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenbankersteller {

	private Connection co;

	public Datenbankersteller(Connection con) {
		this.co = con;
		try {
			Statement stat = co.createStatement();
//			stat.execute("SET FOREIGN_KEY_CHECKS = 0");
//			stat.execute("DROP TABLE Boersenmanager");
//			stat.execute("DROP TABLE WerteHistorie");
//			stat.execute("DROP TABLE Transaktion");
//			stat.execute("DROP TABLE Aktie");
//			stat.execute("DROP TABLE Aktiengesellschaft");
//			stat.execute("DROP TABLE Depotinhaber");
//			stat.execute("DROP TABLE Depot");
//			stat.execute("SET FOREIGN_KEY_CHECKS = 1");

			erstelleAktiengesellschafttabelle(stat, co);
			erstelleBoersenmanagerTabelle(stat, co);
			erstelleDepotinhaberTabelle(stat, co);
			erstelleDepottabelle(stat, co);
			erstelleAktienTabelle(stat, co);
			erstelleWerteHistorieTabelle(stat, co);
			erstelleTransaktionTabelle(stat, co);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * überprüft, ob die referenzierte Tabelle schon vorhanden ist gibt false
	 * zurück, wenn die Tabelle noch nicht existiert
	 */
	private boolean pruefeExistenz(Statement stat, Connection con, String tabellenName) throws SQLException {

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
						"INSERT INTO Aktie(ID, aktuellerWert, Aktiengesellschaft_ID, Depotinhaber_ID, Depot_ID) VALUES\r\n"
								+ "('1', '20', '2', '11', '21'),\r\n" + "('12', '22', '2', '11', '21'),\r\n"
								+ "('13', '23', '2', '11', '21'),\r\n" + "('14', '40', '2', '11', '21'),\r\n"
								+ "('15', '20', '2', '11', '21'),\r\n" + "('16', '15', '2', '11', '21'),\r\n"
								+ "('2','10', '3', '12', '23'),\r\n" + "('3','49', '1', '11', '21'),\r\n"
								+ "('8','49', '1', '11', '21'),\r\n" + "('9','55', '1', '11', '21'),\r\n"
								+ "('10','30', '1', '11', '21'),\r\n" + "('11','23', '1', '11', '21'),\r\n"
								+ "('4','45', '4', '10', '20'),\r\n" + "('5','56', '4', '12', '23'),\r\n"
								+ "('19','60', '4', '12', '23'),\r\n" + "('6','17', '5', '10', '22'),\r\n"
								+ "('17','17', '5', '10', '22'),\r\n" + "('18','20', '5', '10', '22'),\r\n"
								+ "('7','15', '2', '12', '24');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleWerteHistorieTabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Wertehistorie") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Wertehistorie ( Aktie_ID INT, Wert INT, Datum DATE, FOREIGN KEY(Aktie_ID) REFERENCES Aktie(ID), CONSTRAINT PK_Wertehistorie_Aktie_ID_Datum PRIMARY KEY (Aktie_ID, Datum));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute("INSERT INTO Wertehistorie(Aktie_ID, Wert, Datum) VALUES\r\n"
						+ "('1', '5', '2020-01-31'),\r\n" + "('1', '10', '2020-02-29'),\r\n"
						+ "('1', '15', '2020-03-31'),\r\n" + "('1', '10', '2020-04-30'),\r\n"
						+ "('2', '20', '2020-02-29'),\r\n" + "('2', '30', '2020-03-31'),\r\n"
						+ "('2', '10', '2020-04-30'),\r\n" + "('3', '50', '2020-03-31'),\r\n"
						+ "('3', '33', '2020-04-30'),\r\n" + "('4', '45', '2020-04-30'),\r\n"
						+ "('5', '56', '2020-03-31'),\r\n" + "('5', '23', '2020-04-30'),\r\n"
						+ "('6', '45', '2020-04-30'),\r\n" + "('7', '65', '2020-04-30');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleAktiengesellschafttabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Aktiengesellschaft") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Aktiengesellschaft ( ID INT, CONSTRAINT PK_Aktiengesellschaft_ID PRIMARY KEY (ID));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute("INSERT INTO Aktiengesellschaft(ID) VALUES ('1'),('2'),('3'),('4'),('5');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleBoersenmanagerTabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Boersenmanager") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Boersenmanager ( ID INT, CONSTRAINT PK_Boersenmanager_ID PRIMARY KEY (ID));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute("INSERT INTO Boersenmanager(ID) VALUES ('30'),('31');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleDepottabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Depot") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Depot ( ID INT, Depotinhaber_ID INT, FOREIGN KEY (Depotinhaber_ID) REFERENCES Depotinhaber(ID), CONSTRAINT PK_Depot_ID PRIMARY KEY (ID));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute("INSERT INTO Depot(ID, Depotinhaber_ID) VALUES \r\n" + "('20', '10'),\r\n"
						+ "('21', '11'),\r\n" + "('22', '10'),\r\n" + "('23', '12'),\r\n" + "('24', '12');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleDepotinhaberTabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Depotinhaber") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Depotinhaber ( ID INT, Name VARCHAR(255),Iban VARCHAR(255), CONSTRAINT PK_Depotinhaber_ID PRIMARY KEY (ID));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute("INSERT INTO Depotinhaber(ID, Name, Iban) VALUES \r\n"
						+ "('10', 'Klaus Heinrich', 'DE88500105173812539961'),\r\n"
						+ "('11', 'Maria Engel','DE90500105178797648121'),\r\n"
						+ "('12', 'Barbara Giesberg','DE81500105175916719571');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

	public void erstelleTransaktionTabelle(Statement stat, Connection con) throws SQLException {
		try {
			if (this.pruefeExistenz(stat, con, "Transaktion") == false) {
				String sqlBefehlTabelleErstellen = "CREATE TABLE "
						+ "Transaktion ( ID INT, Aktie_ID INT, Verkaufswert INT, VerkaufsDepot_ID INT, AnkaufsDepot_ID INT, FOREIGN KEY (Aktie_ID) REFERENCES Aktie(ID), FOREIGN KEY (AnkaufsDepot_ID) REFERENCES Depot(ID), FOREIGN KEY (VerkaufsDepot_ID) REFERENCES Depot(ID)  , CONSTRAINT PK_Transaktion_ID PRIMARY KEY (ID));";
				stat.execute(sqlBefehlTabelleErstellen);
				stat.execute(
						"INSERT INTO Transaktion(ID, Aktie_ID, Verkaufswert, VerkaufsDepot_ID, AnkaufsDepot_ID) VALUES\r\n"
								+ "('50', '1', '5', '20', '21'),\r\n" + "('51', '1', '10', '21', '22'),\r\n"
								+ "('52', '1', '15', '22', '20'),\r\n"
								+ "('53', '1', '10', '20', '21'),						\r\n"
								+ "('54', '2', '20', '23', '20'),\r\n" + "('55', '2', '30', '20', '24'),\r\n"
								+ "('56', '2', '10', '24', '22'),\r\n" + "('57', '3', '50', '24', '22'),\r\n"
								+ "('58', '3', '33', '22', '21'),\r\n" + "('59', '4', '45', '21', '20'),\r\n"
								+ "('60', '5', '56', '20', '24'),\r\n" + "('61', '5', '23', '24', '23'),\r\n"
								+ "('62', '6', '45', '21', '22'),\r\n" + "('63', '7', '65', '23', '24');");

			}
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
	}

}
