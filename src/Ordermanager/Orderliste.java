package Ordermanager;

import java.util.ArrayList;

import boersenprogramm.Order;

public class Orderliste {

	private ArrayList<Order> orderListe = new ArrayList<Order>();

	public ArrayList<Order> getOrderListe() {
		return orderListe;
	}
/**
 * Fügt der Orderliste die übergebenen Orders hinzu.
 * @param orderListe: ArrayList<Order> mit den hinzuzufügenden Orders
 */
	public void setOrderListe(ArrayList<Order> orderListe) {
		if (orderListe.size() <= 0) {
			this.orderListe = orderListe;
		} else {
			for(Order order:orderListe) {
				this.neueOrder(order);
			}
		}
	}

	public void neueOrder(Order order) {
		this.orderListe.add(order);
	}
}
