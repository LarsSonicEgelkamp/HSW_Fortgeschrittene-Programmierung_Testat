package Filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serialisierung {

	public Serialisierung() {

	}

	File filepath = new File(System.getProperty("user.home") + "/Börsenprogramm");

	public void serialize(Object o, String dateiname) throws IOException {

		if (filepath.mkdir()) {
			try (FileOutputStream fos = new FileOutputStream(filepath + "/" + dateiname + ".id");
					ObjectOutputStream oos = new ObjectOutputStream(fos)) {
				oos.writeObject(o);
			}
		}
	}

	public String deserilize(String dateiname) throws FileNotFoundException, IOException, ClassNotFoundException {
		final String s;
		try (FileInputStream fis = new FileInputStream(filepath + "/" + dateiname + ".id");
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			s = (String) ois.readObject();
		}
		return s;
	}

}
