package JUnit_Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

import boersenprogramm.Aktie;
import boersenprogramm.AktuellerUser;
import boersenprogramm.Boerse;
import boersenprogramm.Datenbankersteller;
import de.loehrke.testen.junit.uebung06.NegativeZahlException;
import de.loehrke.testen.junit.uebung06.Rechner;
import user_Interface.ConnectionManager;

//import de.loehrke.testen.junit.uebung02.Rechner;
//	public class RechnerAdditionTest {
//
//		private Rechner rechner;
//
//		@Before
//		public void initRechner() {
//			this.rechner = new Rechner();
//		}
//
//		@Test
//		public void testAddition() {
//			int actual = rechner.addiere(1, 2);
//			Assert.assertTrue("Fehler bei der Addition", actual == 3);
//		}
//
//	}

//private Rechner rechner;
//
//@DataPoints
//public static final int[] zahlen = { -100, -20, -10, -5, -2, -1, 0, 1, 2, 5, 10, 20, 100};
//
//@Before
//public void initRechner() {
//	this.rechner = new Rechner();
//}
//
//@Theory
//public void testBerechneFakultaetPositiv(int input) {
//	// Annahme, dass dieser Testfall nur mit positiven Zahlen und 0 aufgerufen wird
//	Assume.assumeTrue("Aufruf mit negativer Zahl: " + input, input >= 0);
//	
//	// Hinweis: Dieser Test basiert auf der Korrektheit der Methode
//	// berechnetFakultaetMitSchleife!
//	int expected = berechneFakultaetMitSchleife(input);
//	try {
//		int actual = rechner.berechneFakultaet(input);
//		Assert.assertTrue(actual == expected);
//	} catch (NegativeZahlException e) {
//		Assert.fail(e.getMessage());
//	}
//}
//
//@Theory
//public void testBerechneFakultaetNegativ(int input) {
//	// Annahme, dass dieser Testfall nur mit negativen Zahlen aufgerufen wird
//	Assume.assumeTrue("Aufruf mit positiver Zahl oder 0: " + input, input < 0);
//	
//	boolean exceptionGeworfen = false;
//	try {
//		rechner.berechneFakultaet(input);
//	} catch (NegativeZahlException e) {
//		exceptionGeworfen = true;
//	}
//	Assert.assertTrue(exceptionGeworfen);
//}

public class JUnit_Tests {


	Boerse b;
	Aktie ak;
	Statement stat;
	private static Datenbankersteller db;

	@Before
	public void initKlassen() throws SQLException {
		
		String databaseURL = "jdbc:mysql://localhost/boersendatenbank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(databaseURL, "user", "user")) {
			db = new Datenbankersteller(con);
			this.stat = con.createStatement();
		}
		
		this.b = new Boerse(stat);
		this.ak = new Aktie(00, stat);
	}

	@Test
	public void testGetAktiengesellschaft() throws SQLException {
//		int actual = rechner.addiere(1, 2);
//		Assert.assertTrue("Fehler bei der Addition", actual == 3);
		int aktiengesellschaft = 99;
		this.stat = ConnectionManager.ueberpruefeConnection(stat);
		ResultSet rs = stat.executeQuery("SELECT Aktiengesellschaft_ID FROM Aktie WHERE ID = " + "1" + ";");
		if (rs.next()) {
			aktiengesellschaft = rs.getInt(1);
		}

		Assert.assertTrue(2 == ak.getAktiengesellschaft(1));

	}

//	public ArrayList<Aktie> getAktienListe(AktuellerUser user) throws SQLException {
//		ArrayList<Integer> aktienIDListe;
//		aktienIDListe = dm.getAktienIDs(user);
//		for (int id : aktienIDListe) {
//			Aktie tempAk = new Aktie(id, stat);
//			tempAk.setWert(tempAk.getWert(tempAk.getId()));
//			tempAk.setAktiengesellschaft(tempAk.getAktiengesellschaft(tempAk.getId()));
//			tempAk.setDepot(tempAk.getDepot(tempAk.getId()));
//			tempAk.setDepotinhaber(tempAk.getDepotinhaber(tempAk.getId()));
//			aktienListe.add(tempAk);
//		}
//		return aktienListe;
//	}

}

//package de.loehrke.testen.junit.uebung07;
//
//import static org.hamcrest.CoreMatchers.both;
//import static org.hamcrest.CoreMatchers.everyItem;
//import static org.hamcrest.CoreMatchers.hasItem;
//import static org.hamcrest.CoreMatchers.not;
//import static org.hamcrest.CoreMatchers.startsWith;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;

/**
 * Implementieren Sie jeweils einen Testfall, der für eine Liste von IBANs
 * testet, ob a. nicht nur deutsche IBANs enthalten sind. b. mindestens eine
 * französische IBAN enthalten ist. c. keine italienische IBAN enthalten ist. d.
 * die Bedingungen aus den Teilaufgaben b und c eingehalten werden.
 * 
 * Hinweis: Zur Vereinfachung reicht es aus, den Ländercode der IBAN zu prüfen.
 * 
 * @author Katharina Löhrke
 */
//public class HamcrestTest {

//	private List<String> ibans;

//	@Before
//	public void initIbanListe() {
//		ibans = new ArrayList<>();
//
//		ibans.add("DE12346788901234567890");
//		ibans.add("DE12346788901234567890");
//		ibans.add("DE12346788901234567890");
//		ibans.add("DE12346788901234567890");
//		ibans.add("DE12346788901234567890");
//		ibans.add("FR12346788901234567890");
//	}

//	@Test
//	public void testTeilaufgabeA() {
//		// Test, ob nicht nur deutsche IBANs enthalten sind
//		Assert.assertThat(this.ibans, not(everyItem(startsWith("DE"))));
//	}
//
//	@Test
//	public void testTeilaufgabeB() {
//		// Test, ob mindestens eine französische IBAN enthalten ist
//		Assert.assertThat(this.ibans, hasItem(startsWith("FR")));
//	}
//
//	@Test
//	public void testTeilaufgabeC() {
//		// Test, ob keine italienische IBAN enthalten ist
//		Assert.assertThat(this.ibans, not(hasItem(startsWith("IT"))));
//	}
//
//	@Test
//	public void testTeilaufgabeD() {
//		// Test, ob mindestens eine französische IBAN
//		// UND keine italienische IBAN enthalten ist
//		Assert.assertThat(this.ibans, both(hasItem(startsWith("FR"))).and(not(hasItem(startsWith("IT")))));
//	}
//
//}