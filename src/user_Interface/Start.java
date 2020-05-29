package user_Interface;

import java.sql.Statement;

import boersenprogramm.Datenbankersteller;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;


public class Start {

	private static Datenbankersteller db;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String databaseURL = "jdbc:mysql://localhost/boersendatenbank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(databaseURL, "user", "user")) {
			db = new Datenbankersteller(con);
			Statement stat = con.createStatement();

			GUI start = new GUI(stat);

			start.setVisible(true);

		} catch (SQLException e) {
			System.out.println("Fehler: " + e.getMessage());
		}
	}

}
