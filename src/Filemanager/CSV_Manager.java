package Filemanager;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import boersenprogramm.Order;

public class CSV_Manager {

	public CSV_Manager() {

	}

	public static void writeCSV(String filename) {

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

				e.printStackTrace();

			}

		}

		else {
			try {

				fw = new FileWriter(f);
				bw = new BufferedWriter(fw);

				for (String orderElement : Order.getOrderListe()) {
					bw.write(orderElement + ",");
				}
				bw.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

	}

	public ArrayList<String> readCSV(String filename)  {
		ArrayList<String> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Börsenprogramm/CSV_Dateien"+filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records.addAll(Arrays.asList(values));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}
}
