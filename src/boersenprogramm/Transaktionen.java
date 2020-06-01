package boersenprogramm;

public class Transaktionen {
	

	private int verkaufsDepotID;
	private int ankaufsDepotID;
	private int verkaufswert;
	private int aktiengesellschaftsID;
	private double transaktionskosten;
	private int menge;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	public int getVerkaufsDepotID() {
		return verkaufsDepotID;
	}
	public void setVerkaufsDepotID(int verkaufsDepotID) {
		this.verkaufsDepotID = verkaufsDepotID;
	}
	public int getAnkaufsDepotID() {
		return ankaufsDepotID;
	}
	public void setAnkaufsDepotID(int ankaufsDepotID) {
		this.ankaufsDepotID = ankaufsDepotID;
	}
	public int getVerkaufswert() {
		return verkaufswert;
	}
	public void setVerkaufswert(int verkaufswert) {
		this.verkaufswert = verkaufswert;
	}
	public int getAktiengesellschaftsID() {
		return aktiengesellschaftsID;
	}
	public void setAktiengesellschaftsID(int aktiengesellschaftsID) {
		this.aktiengesellschaftsID = aktiengesellschaftsID;
	}
	public double getTransaktionskosten() {
		return transaktionskosten;
	}
	public void setTransaktionskosten(int verkaufswert) {
		this.transaktionskosten = verkaufswert*0.001;
	}
}
