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

import com.sun.org.apache.xalan.internal.lib.ExsltStrings;

import java.sql.SQLException;
import java.sql.Statement;

import boersenprogramm.Order;

public class CSV_Manager {

	private int fehlerhafteZeilen;
	private ArrayList<String> logEintraege;

	public CSV_Manager() {

	}

	public static void writeCSV(String filename) throws IOException {

		File filepath = new File(System.getProperty("user.home") + "/Börsenprogramm/CSV_Dateien");
		BufferedWriter bw = null;
		FileWriter fw = null;
		File f = new File(filepath + "/" + filename + ".csv");

		if (filepath.mkdirs()) {

			try {

				fw = new FileWriter(f);
				bw = new BufferedWriter(fw);

				for (String orderElement : Order.getOrderListe()) {
					bw.write(orderElement + ",");
				}
				bw.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		} else {
			try {
				fw = new FileWriter(f);
				bw = new BufferedWriter(fw);

				for (String orderElement : Order.getOrderListe()) {
					bw.write(orderElement + ",");
				}
				bw.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
	}

	public ArrayList<String> readCSV(String filename) throws IOException {
		ArrayList<String> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new FileReader(System.getProperty("user.home") + "/Börsenprogramm/CSV_Dateien" + filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				records.addAll(Arrays.asList(values));
			}
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} catch (IOException e) {
			throw new IOException(e);
		}
		return records;
	}

	public void neueOrderDatei(File f) throws IOException {
		File filepath = new File(System.getProperty("C:\\Users\\Service\\Documents\\Boerse\\Orders\\" + f.getName()));
		System.out.println(filepath);
		BufferedWriter bw = null;
		FileWriter fw = null;

		if (filepath.mkdirs()) {

			try {
				ArrayList<String> orderLines = this.readCSV(f.getAbsolutePath());
				filepath.setWritable(true);
				// wird nicht geschrieben ???, angeblich kein Zugriff???
				fw = new FileWriter(filepath);
				bw = new BufferedWriter(fw);
				for (String line : orderLines) {
					bw.write(line);
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
	 * @throws SQLException 
	 */
	


//private void scan(File directory) {
//                File[] currentFiles = directory.listFiles();
//		scanDirectory(currentFiles);
//}
//
//private void scanDirectory(File[] currentFiles) {
//		 if (currentFiles==null || currentFiles.length==0) {
//			 return;
//		 }
//		 
//		 for (File file : currentFiles) {
//			if (file.isDirectory()) {
//				// rekursiver aufruf
//				scanDirectory(file.listFiles());
//				
//				// falls notwendig: aktuelles verzeichnis löschen
//				file.delete();
//				continue;
//			}
//			
//
//		 }
//	}


	
	public void bearbeiteOrders(Statement stat) throws IOException, SQLException {
		File ordersKaufen = new File(System.getProperty("user.home")+"Boersenprogramm/Order/Order_Kaufen");	// da sollten alle Orders liegen
		File ordersVerkaufen = new File(System.getProperty("user.home")+"Boersenprogramm/Order/Order_Verkaufen");
		ArrayList<String> pathsKaufen = this.readCSV(ordersKaufen.getAbsolutePath());
		ArrayList<String> pathsVerkaufen= this.readCSV(ordersVerkaufen.getAbsolutePath());
		ArrayList<String> eineOrderKaufen = null;										// alle anderen Pfade der einzelnen Orders// entnommen.
		
		//depotID;aktieninhaberId;menge;stückpreis
		
	
		
		for (String s : pathsKaufen) { // für jeden Pfad wird ein File erstellt
			
			 eineOrderKaufen.addAll(this.readCSV(f.getAbsolutePath()));
			 ueberpruefeOrder(eineOrderKaufen);// Order wird auf Richtigkeit der Syntax geprueft
			if (this.fehlerhafteZeilen != 0) {
				this.logEintraege
						.add("Die Datei: " + pathsKaufen.getName() + " hat " + this.fehlerhafteZeilen + " fehlerhafte Zeilen");
			}
			
			int verkaufPreis = Integer.parseInt(pathsVerkaufen.get(3));
			int kaufPreis = Integer.parseInt(eineOrderKaufen.get(3));
			ArrayList<Strings> 
			
			if (verkaufPreis <= kaufPreis) {
				for (int i = Integer.parseInt(eineOrderKaufen.get(2));i < ; i++) {				
				stat.executeQuery("UPDATE Aktie SET Depot_ID='"+eineOrderKaufen.get(0)+"' WHERE Aktiengesellschaft_ID = '"+eineOrderKaufen.get(1)+"'");
			}
				
				
				
//				UPDATE kunde SET plz = '59581', ort = 'Warstein' WHERE id = 5;


			}
		}
		
		
		
		
		this.schreibeLogDatei();
		
		System.out.println(eineOrderKaufen);

		
	}

	/**
	 * Überprüft eine Order auf korrekte Syntax.
	 * 
	 * @param eineOrder: Eine Order, wobei jede Zeile der Order in einer AllarList
	 *                   übergeben wird.
	 */
	public void ueberpruefeOrder(ArrayList<String> eineOrder) {
		this.fehlerhafteZeilen = 0;// anzahl der fehlerhaften Zeilen wird bei dem jeder neuen Order wieder auf null
									// gesetzt
		for (String zeile : eineOrder) {// jede Zeile der Order wird untersucht
			String[] elements = zeile.split(";");// Elemente in der CSV-Datei werden durch ; getrennt und Zeilen durch ,
			if (elements.length == 4) {// Syntax der Order: Muss 4 elemente haben
				for (String s : elements) {
					try {// fängt NumberFormatException, wenn die Strings keine Zahlen sind. Dies sollten
							// sie sein, da es ID`s oder Werte sind.
						int i = Integer.parseInt(s);
					} catch (NumberFormatException e) {
						this.fehlerhafteZeilen++;
						break;
					}
				}
			} else {
				this.fehlerhafteZeilen++;
			}
		}
	}

	/**
	 * Schreibt eine Logdatei mit der Anzahl an fehlerhaften Zeilen von jeder Datei
	 * 
	 * @throws IOException
	 */
	public void schreibeLogDatei() throws IOException {
		int i = 1;// individueller Name jeder Logdatei
		File filepath = new File(System.getProperty("C:\\Users\\Service\\Documents\\Boerse\\Logs\\" + i));
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
}
