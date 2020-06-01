package boersenprogramm;

/**
 * Beschreibt das Objekt Order der Börsenanwendung. 
 */
public class Order {

	private int depotID;
	private int aktienGruppenID;
	private int menge;
	private int stueckpreis;

	public Order(int depotID, int aktienGruppenID, int menge, int stueckpreis) {
		this.depotID = depotID;
		this.aktienGruppenID = aktienGruppenID;
		this.menge = menge;
		this.stueckpreis = stueckpreis;
	}

	public Order() {
		
	}

	public int getDepotID() {
		return depotID;
	}

	public void setDepotID(int depotID) {
		this.depotID = depotID;
	}

	public int getAktienGruppenID() {
		return aktienGruppenID;
	}

	public void setAktienGruppenID(int aktienGruppenID) {
		this.aktienGruppenID = aktienGruppenID;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public int getStueckpreis() {
		return stueckpreis;
	}

	public void setStueckpreis(int stueckpreis) {
		this.stueckpreis = stueckpreis;
	}

}
