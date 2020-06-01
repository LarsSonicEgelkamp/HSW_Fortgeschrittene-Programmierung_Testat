package JUnit_Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;

import boersenprogramm.Aktie;
import boersenprogramm.Aktiengesellschaft;
import boersenprogramm.AktuellerUser;
import boersenprogramm.Boerse;
import boersenprogramm.Datenbankersteller;
import de.loehrke.testen.junit.uebung06.NegativeZahlException;
import de.loehrke.testen.junit.uebung06.Rechner;
import user_Interface.ConnectionManager;
import user_Interface.GUI;

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
	Aktiengesellschaft akg;
	GUI g;
	AktuellerUser au;
	Statement stat;
	DefaultListModel<String> testListModel;
	private static Datenbankersteller db;
	
	

	@Before
	public void init() throws SQLException {
		initDatenbank();
		initKlassen();
		initListModel();
		
	}

	public void initListModel() {
		testListModel = new DefaultListModel<String>();
		testListModel.add(0, "ID: 4 Wert: 45");
	}
	
//	public void makeAktienList(DefaultListModel<String> listModel) throws SQLException {
//	Boerse b = new Boerse(stat);
//	ArrayList<Aktie> akList = b.getAktienListe(aUser);
//	for (Aktie a : akList) {
//		int aktuellesDepot = a.getDepot(a.getId());
//		int gewaehltesDe = (Integer) depots.getSelectedItem();
//		if (gewaehltesDe == aktuellesDepot) {
//			listModel.addElement("ID: " + a.getId() + " Wert:" + a.getWert(a.getId()));
//		}
//	}
//}

	public void initDatenbank() throws SQLException {
		String databaseURL = "jdbc:mysql://localhost/boersendatenbank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(databaseURL, "user", "user")) {
			db = new Datenbankersteller(con);
			this.stat = con.createStatement();
		}
	}

	public void initKlassen() {
		this.b = new Boerse(stat);
		this.ak = new Aktie(00, stat);
		this.akg = new Aktiengesellschaft(00, stat);
		this.g = new GUI(stat);
		this.au = new AktuellerUser("depotinhaber", 10);
	}

	@Test
	public void testGetAktiengesellschaft() throws SQLException {
		Assert.assertTrue(2 == ak.getAktiengesellschaft(1));
	}
	
	@Test
	public void testGetDepot () throws SQLException {
		Assert.assertTrue(21 == ak.getDepot(1));
	}
	
	@Test
	public void testMakeAktienList() {
		
	}
	
	@Test
	public void testListModel() throws SQLException {
		System.out.println(b.getAktienListe(au));
	}
	
	@Test
	public void tesCSVManager () {
		
	}



}

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