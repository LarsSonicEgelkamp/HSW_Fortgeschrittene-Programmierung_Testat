package Filemanager;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import börsenprogramm.Order;

public class CSV_Writer {

	public CSV_Writer() {

	}

	public void CSV_Writer(String filename) {

		final String filepath = System.getProperty("user.home" + "/Börsenprogramm/CSV_Dateien/" + filename + ".csv");

		BufferedWriter bw = null;
		FileWriter fw = null;
		File f = new File(filepath);

		if (f.mkdir()) {
			try {

				fw = new FileWriter(filepath);
				bw = new BufferedWriter(fw);

				for (String orderElement : Order.getOrderListe()) {
					bw.write(orderElement + ",");
				}

			} catch (IOException e) {

				e.printStackTrace();

			} finally {

				try {

					if (bw != null)
						bw.close();

					if (fw != null)
						fw.close();

				} catch (IOException ex) {

					ex.printStackTrace();

				}

			}

		}
	}
}
