package JUnit_Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;

import java.io.File;
import java.io.IOException;
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

import Filemanager.CSV_Manager;
import Ordermanager.Orderliste;
import boersenprogramm.Aktie;
import boersenprogramm.Aktiengesellschaft;
import boersenprogramm.AktuellerUser;
import boersenprogramm.Boerse;
import boersenprogramm.Datenbankersteller;
import boersenprogramm.Order;
import user_Interface.ConnectionManager;
import user_Interface.GUI;


public class JUnit_Tests {

	
	Boerse b;
	Aktie ak;
	Aktiengesellschaft akg;
	GUI g;
	AktuellerUser au;
	Statement stat;
	Orderliste ol;
	CSV_Manager csv;
	DefaultListModel<String> testListModel;
	ArrayList<Order> orderTestList;
	
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


	public void initDatenbank() throws SQLException {
		String databaseURL = "jdbc:mysql://localhost/boersendatenbank?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		try (Connection con = DriverManager.getConnection(databaseURL, "user", "user")) {
			new Datenbankersteller(con);
			this.stat = con.createStatement();
		}
	}

	public void initKlassen() {
		this.b = new Boerse(stat);
		this.ak = new Aktie(00, stat);
		this.akg = new Aktiengesellschaft(00, stat);
		this.g = new GUI(stat);
		this.au = new AktuellerUser("depotinhaber", 10);
		this.ol = new Orderliste();
		this.csv = new CSV_Manager();
		this.orderTestList = new ArrayList<Order>();
	}

	@Test
	public void testGetAktiengesellschaft() throws SQLException {
		Assert.assertTrue(2 == ak.getAktiengesellschaft(1));
	}

	@Test
	public void testGetDepot() throws SQLException {
		Assert.assertTrue(21 == ak.getDepot(1));
	}


	@Test
	public void tesCSVreader() throws IOException {
		File testFile = new File( System.getProperty("user.home")+"\\Service\\Documents\\Boerse\\Orders\\zuBearbeiten\\" +"Order1.csv");
		assertEquals("21;1;-5;55", csv.readCSVDatei(testFile).get(1));
	}

	@Test
	public void testOrderListe() throws IOException {
		orderTestList = csv.getKorrekteOrders();
		csv.getKorrekteOrders();
		System.out.println(csv.getKorrekteOrders());
		ArrayList<String> testarray = new ArrayList<String>();
		System.out.println(orderTestList);
		System.out.println("123");
		for (Order order : orderTestList) {
			testarray.add(Integer.toString(order.getDepotID()));
			System.out.println(order.getDepotID());
		}
		assertThat(testarray.get(1), hasItem("21"));
	}
	
	@Test
	public void testNeueOrderDatei () throws IOException {
		File file = new File("JunitTest.csv");
		File testFile = new File(System.getProperty("user.home")+"\\Service\\Documents\\Boerse\\Orders\\zuBearbeiten\\" +"JunitTest");
		csv.neueOrderDatei(file);
		assertTrue(testFile.exists());
		testFile.delete();
	}
	
	

}

