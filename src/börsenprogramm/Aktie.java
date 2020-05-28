package börsenprogramm;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Aktie {

	private final int id;
	private int aktWert;
	private ArrayList<Integer> werteHistorie = new ArrayList<Integer>();
	Statement stat;

	public Aktie(Statement stat, int id, int initialWert) {
		this.id = id;
		this.aktWert = initialWert;
		this.stat = stat;
	}

	public Integer getId() {
		return id;
	}

	public int getWert() {
		return aktWert;
	}

	/*
	 * Bevor der Wert der Aktie geändert wird, wird der aktuelle Wert zuerst in der
	 * Werte-Historie gespeichert.
	 */
	public void setWert(int wert, Date aktDatum) throws SQLException {
		this.werteHistorie.add(this.getWert());
		stat.execute("INSERT INTO Wertehistorie (Aktie_ID, Wert, Datum) VALUES (" + this.id + ", " + this.aktWert + ", "
				+ aktDatum+");");

		this.aktWert = wert;
	}
}
