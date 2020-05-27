package user_Interface;

import java.sql.Statement;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;


import börsenprogramm.Datenbankmanager;


public class Start {

	private static Datenbankmanager db;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String databaseURL = "jdbc:mysql://localhost/boersendatenbank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(databaseURL, "user", "user")) {
			db = new Datenbankmanager(con);
			Statement stat = con.createStatement();

		} catch (SQLException e) {
			System.out.println("Fehler: " + e.getMessage());
		}

		GUI start = new GUI();

        start.setVisible(true);
	}

}
