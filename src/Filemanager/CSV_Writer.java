package Filemanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import börsenprogramm.Order;

public class CSV_Writer {

	public CSV_Writer() {

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
}
