package boersenprogramm;

import java.sql.Date;

/**
 * Beschreibt das Objekt Umsaetze der Börsenanwendung für die
 * Orderabwicklung.
 */
public class Umsaetze {
	
	private String zahlerIban;
	private String zahlungsempfaengerIban;
	private int betrag;
	private int verwendungszweck;
	private Date ausfuehrdatum;
	private double transaktionskosten;
	
	public double getTransaktionskosten() {
		return transaktionskosten;
	}

	public void setTransaktionskosten(double transaktionskosten) {
		this.transaktionskosten = transaktionskosten;
	}

	public String getZahlerIban() {
		return zahlerIban;
	}

	public void setZahlerIban(String zahlerIban) {
		this.zahlerIban = zahlerIban;
	}

	public String getZahlungsempfaengerIban() {
		return zahlungsempfaengerIban;
	}

	public void setZahlungsempfaengerIban(String zahlungsempfaengerIban) {
		this.zahlungsempfaengerIban = zahlungsempfaengerIban;
	}

	public int getBetrag() {
		return betrag;
	}

	public void setBetrag(int betrag) {
		this.betrag = betrag;
	}

	public int getVerwendungszweck() {
		return verwendungszweck;
	}

	public void setVerwendungszweck(int verwendungszweck) {
		this.verwendungszweck = verwendungszweck;
	}

	public Date getAusfuehrdatum() {
		return ausfuehrdatum;
	}

	public void setAusfuehrdatum(Date ausfuehrdatum) {
		this.ausfuehrdatum = ausfuehrdatum;
	}
}
