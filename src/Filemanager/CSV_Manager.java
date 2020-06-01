package Filemanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Ordermanager.Orderliste;
import boersenprogramm.Order;
import boersenprogramm.Umsaetze;

public class CSV_Manager {

	private int fehlerhafteZeilen;
	private ArrayList<String> logEintraege;

	private File aktuelleOrder;

	public CSV_Manager() {

	}

	/**
	 * Liest eine Order zeilenweise aus.
	 * 
	 * @param order: die zu lesende Order
	 * @return: ArrayList<String>, wobei jeder String eine Zeile einer Order ist
	 * @throws IOException
	 */
	private ArrayList<String> readCSVDatei(File order) throws IOException {
		ArrayList<String> zeilen = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(order))) {
			String line;
			while ((line = br.readLine()) != null) {
				zeilen.add(line);
			}
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} catch (IOException e) {
			throw new IOException(e);
		}
		return zeilen;
	}

	/**
	 * Fügt die übergebene Orderdatei in das Empfangsverzeichnis für neue Ordes
	 * hinzu.
	 * 
	 * @param neueOrder: im CSV-Format
	 * @throws IOException
	 */
	public void neueOrderDatei(File neueOrder) throws IOException {
		String filename = neueOrder.getName();
		File filepath = new File("C:\\Users\\Service\\Documents\\Boerse\\Orders\\zuBearbeiten\\" + filename);
		System.out.println(filepath);
		BufferedWriter bw = null;
		FileWriter fw = null;

		if (filepath.createNewFile()) {
			boolean write = filepath.canWrite();
			System.out.println(write);

			try {
				ArrayList<String> orderLines = this.readCSVDatei(neueOrder);
				// wird nicht geschrieben ???, angeblich kein Zugriff???
				System.out.println("isFile:" + filepath.isFile());
				fw = new FileWriter(filepath);
				bw = new BufferedWriter(fw);
				for (String line : orderLines) {
					bw.write(line);
					bw.newLine();
				}

				bw.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		} else {
			throw new IOException("Fehler beim Einreichen der Order.");
		}
	}

	/**
	 * Arbeitet alle Orders in dem angegebenen File ab.
	 * 
	 * @throws IOException
	 */
	public Orderliste getKorrekteOrders() throws IOException {
		Orderliste orderliste = new Orderliste();
		File orderVerzeichnis = new File("C:\\Users\\Service\\Documents\\Boerse\\Orders\\zuBearbeiten");
																										
		if (orderVerzeichnis.mkdirs()) {
			File[] orders = orderVerzeichnis.listFiles();
															
			for (File f : orders) {
				this.aktuelleOrder = f;
				ArrayList<String> orderAlsList = this.readCSVDatei(f);
				orderAlsList = this.getKorrekteOrders(orderAlsList);
				orderliste = this.getOrderliste(orderAlsList);

			}
		} else {
			throw new IOException("Speicherverzeichnis der Orders existiert nich und konnte nicht angelegt werden.");
		}
		this.schreibeLogDatei();
		return orderliste;
	}

	/**
	 * Wandelt die übergebenen Orders vom String-Format zu Orders um.
	 * 
	 * @param orderAlsList: ArrayList<String>, die eine Orderdatei repräsentiert,
	 *                      wobei jeder String eine Orderzeile enthält
	 * @return: ArrayList<Order>, welche alle Orders der übergebenen Orderdatei
	 *          enthält
	 */
	private Orderliste getOrderliste(ArrayList<String> orderAlsList) {
		Orderliste orderliste = new Orderliste();
		for (String zeile : orderAlsList) {
			String[] orderElemente = zeile.split(";");
			Order tempOr = new Order();
			tempOr.setDepotID(Integer.parseInt(orderElemente[0]));
			tempOr.setAktienGruppenID(Integer.parseInt(orderElemente[1]));
			tempOr.setMenge(Integer.parseInt(orderElemente[2]));
			tempOr.setStueckpreis(Integer.parseInt(orderElemente[3]));
			orderliste.add(tempOr);
		}
		return orderliste;
	}

	/**
	 * Überprüft die übergebene Order auf korrekte und fehlerhafte Zeilen.
	 * 
	 * @param eineOrder: eine ArrayList<String>, wobei jeder String einer Zeile der
	 *                   Order entspricht
	 * @return: ArrayList<String> mit alle fehlerfreien Zeilen der Order
	 * @throws IOException
	 */
	private ArrayList<String> getKorrekteOrders(ArrayList<String> eineOrderDateiAlsListe) throws IOException {
		this.fehlerhafteZeilen = 0;
		ArrayList<String> korrekteOrders = new ArrayList<String>();
		ArrayList<String> fehlerhafteOrders = new ArrayList<String>();
		boolean fehlerVorhanden = false;

		for (String zeile : eineOrderDateiAlsListe) {
			boolean fehlerfrei = this.ueberpruefeZeile(zeile);
			if (fehlerfrei) {
				korrekteOrders.add(zeile);
			} else {
				this.fehlerhafteZeilen++;
				fehlerVorhanden = true;
				fehlerhafteOrders.add(zeile);

			}
		}
		if (fehlerVorhanden) {
			this.schreibeFehlerhafteDatei(korrekteOrders, fehlerhafteOrders);
			this.logEintraege.add("Die Datei: " + this.aktuelleOrder.getName() + " hat " + this.fehlerhafteZeilen
					+ " fehlerhafte Zeilen");

		} else {
			this.schreibeVerarbeiteteOrderDatei(korrekteOrders);
		}
		return korrekteOrders;
	}

	/**
	 * Überprüft eine Zeile einer Orderdatei auf Korrektheit der Syntax.
	 * 
	 * @param eineOrder: Eine Order, wobei jede Zeile der Order in einer AllarList
	 *                   übergeben wird.
	 */
	private boolean ueberpruefeZeile(String zeileEinerOrder) {
		String[] elements = zeileEinerOrder.split(";");
		if (elements.length == 4) {
			for (String s : elements) {
				try {
					int i = Integer.parseInt(s);
				} catch (NumberFormatException e) {
					this.fehlerhafteZeilen++;
					return false;
				}
			}
		} else {
			this.fehlerhafteZeilen++;
			return false;
		}
		return true;
	}

	/**
	 * Schreibt die gesamte Orderdatei in das Verzeichnis fehlerhafter Orders. Die
	 * korrekten und fehlerhaften Zeilen werden dabei voneinander getrennt.
	 * 
	 * @param korrekteOrders:    als ArrayList<String>
	 * @param fehlerhafteOrders: als ArrayList<String>
	 */
	private void schreibeFehlerhafteDatei(ArrayList<String> korrekteOrders, ArrayList<String> fehlerhafteOrders)
			throws IOException {
		File f = new File("C:\\Users\\Service\\Documents\\Boerse\\Orders\\fehlerhaft\\" + this.aktuelleOrder.getName());

		if (f.createNewFile()) {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);

			for (String zeile : korrekteOrders) {
				bw.write(zeile);
				bw.newLine();
			}

			bw.write("Fehlerhafte Orders:");
			bw.newLine();

			for (String zeile : fehlerhafteOrders) {
				bw.write(zeile);
				bw.newLine();
			}
			bw.close();
		} else {
			throw new IOException(
					"Fehlerhafte Orderdatei, " + this.aktuelleOrder.getName() + ", konnte nicht angelegt werden.");
		}
	}

	private void schreibeVerarbeiteteOrderDatei(ArrayList<String> korrekteOrders) throws IOException {
		File f = new File(
				"C:\\Users\\Service\\Documents\\Boerse\\Orders\\verarbeitet\\" + this.aktuelleOrder.getName());

		if (f.createNewFile()) {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);

			for (String zeile : korrekteOrders) {
				bw.write(zeile);
				bw.newLine();
			}
			bw.close();
		} else {
			throw new IOException("Die vollständig korrekte Orderdatei, " + this.aktuelleOrder.getName()
					+ ", konnte im Verzeichnis \"verarbeitet\" nicht angelegt werden.");
		}
	}

	/**
	 * Schreibt eine Logdatei mit der Anzahl an fehlerhaften Zeilen von jeder Datei
	 * 
	 * @throws IOException
	 */
	private void schreibeLogDatei() throws IOException {
		String datum = null;// TODO automatisches Datum erzeugen
		File filepath = new File("C:\\Users\\Service\\Documents\\Boerse\\Logs\\" + datum);
		BufferedWriter bw = null;
		FileWriter fw = null;
		if (filepath.mkdirs()) {

			try {

				fw = new FileWriter(filepath);
				bw = new BufferedWriter(fw);
				for (String zeile : this.logEintraege) {
					bw.write(zeile);
				}
				bw.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		} else {
			throw new IOException("Fehler beim Schreiben der Logdatei.");
		}
	}
/**
 * Legt anhande der uebergebenen Umsaetze eine neue Umsaetzedatei an
 * @param umsaetze: ArrayList<Umsaetze>, enthält alle umsaetze
 * @throws IOException
 */
	public void schreibeUmsaetze(ArrayList<Umsaetze> umsaetze) throws IOException {
		String datum = null;// TODO automatisches Datum erzeugen
		File filepath = new File(System.getProperty("C:\\Users\\Service\\Documents\\Boerse\\UmsaetzDateien" + datum));
		BufferedWriter bw = null;
		FileWriter fw = null;
		if (filepath.mkdirs()) {
			try {
				double transaktionskosten = 0;
				fw = new FileWriter(filepath);
				bw = new BufferedWriter(fw);
				for (Umsaetze um : umsaetze) {
					bw.write(um.getZahlerIban() + "; " + um.getZahlungsempfaengerIban() + "; " + um.getBetrag() + "; "
							+ um.getVerwendungszweck() + "; " + um.getAusfuehrdatum()+", ");
					bw.newLine();
					transaktionskosten = transaktionskosten + um.getTransaktionskosten();
				}
				bw.write("Die gesamten Transaktionskosten betragen:" + transaktionskosten);
				bw.close();
			} catch (IOException e) {
				throw new IOException(e);
			}

		} else {
			throw new IOException("Fehler beim Schreiben der Umsaetzedatei.");
		}
	}
}
