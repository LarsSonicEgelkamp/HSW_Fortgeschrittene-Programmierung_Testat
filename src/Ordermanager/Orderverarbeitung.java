package Ordermanager;

import java.io.IOException;
import java.util.ArrayList;

import Filemanager.CSV_Manager;
import boersenprogramm.Order;

public class Orderverarbeitung {

	private CSV_Manager csvmag = new CSV_Manager();

	private Orderliste orderListe;

	public Orderverarbeitung() throws IOException {
		this.orderListe = this.getOrderliste();
		if (orderListe.getOrderListe().size() > 0) {
			this.verarbeiteOrders();
		}
	}
/**
 * 
 * @return: eine Orderliste mit allen anfallenden Orders.
 * @throws IOException
 */
	private Orderliste getOrderliste() throws IOException {
		Orderliste orders = csvmag.getKorrekteOrders();
		return orders;
	}

	private void verarbeiteOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();
		orders = this.orderListe.getOrderListe();
	}

}
