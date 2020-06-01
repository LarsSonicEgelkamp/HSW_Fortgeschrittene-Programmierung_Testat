package Ordermanager;

import java.util.ArrayList;

import boersenprogramm.Order;

public class Orderliste extends ArrayList<Order> {

	public Orderliste(Orderliste orderliste) {
		this.addAll(orderliste);
	}

	public Orderliste() {

	}

	/**
	 * Sucht aus der bestehenden Orderliste alle Ankaufsanfragen heraus.
	 * 
	 * @return: eine Orderliste nur mit den Ankaufsanfragen
	 */
	public Orderliste getAnkaufsliste() {
		Orderliste ankaufsliste = new Orderliste();

		for (Order o : this) {
			int menge = o.getMenge();
			if (menge > 0) {
				ankaufsliste.add(o);
			}
		}
		return ankaufsliste;
	}

	/**
	 * Sucht aus der bestehenden Orderliste alle Verkaufsanfragen heraus.
	 * 
	 * @return: eine Orderliste nur mit den Verkaufsanfragen
	 */
	public Orderliste getVerkaufsliste() {
		Orderliste verkaufsliste = new Orderliste();

		for (Order o : this) {
			int menge = o.getMenge();
			if (menge < 0) {
				o.setMenge(menge * -1);
				verkaufsliste.add(o);
			}
		}
		return verkaufsliste;
	}

	/**
	 * Sucht zu der übergebenen Order-Ankaufsanfrage eine passende
	 * Order-Verkaufsanfrage heraus.
	 * 
	 * @param ankaufsanfrage: Order mit einer Ankaufsanfrage
	 * @return: Order mit einer zu der Ankaufsanfrage passenden Verkaufsanfrage
	 */
	public Order getpassendeOrder(Order ankaufsanfrage) {

		for (Order verkaufsanfrage : this) {
			if (verkaufsanfrage.getAktienGruppenID() == ankaufsanfrage.getAktienGruppenID()) {
				if (verkaufsanfrage.getStueckpreis() <= ankaufsanfrage.getStueckpreis()) {
					return verkaufsanfrage;
				}
			}
		}
		return null;
	}

}
