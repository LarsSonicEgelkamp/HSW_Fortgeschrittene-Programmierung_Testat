package Ordermanager;

import java.util.ArrayList;

import boersenprogramm.Order;

public class Orderliste {

	private ArrayList<Order> orderListe = new ArrayList<Order>();

	public ArrayList<Order> getOrderListe() {
		return orderListe;
	}
/**
 * F�gt der Orderliste die �bergebenen Orders hinzu.
 * @param orderListe: ArrayList<Order> mit den hinzuzuf�genden Orders
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
