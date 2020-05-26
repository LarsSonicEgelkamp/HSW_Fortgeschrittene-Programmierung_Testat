package platzZumTesten;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Datenbankanbindung {

	public static void main(String[] args) {

		String databaseURL = "jdbc:mysql://localhost/fortgeschrittene_programmierung";
		try (Connection con = DriverManager.getConnection(databaseURL, "fpuser", "fpuser")) {

			Statement statement = con.createStatement();

//			statement.execute("CREATE DATABASE");
			System.out.println("Hallo hallo");
		} catch (SQLException e) {
			System.out.println("Fehler: " + e.getMessage());
		}

	}

}
