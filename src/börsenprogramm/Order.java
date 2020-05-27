package börsenprogramm;

import java.util.ArrayList;

public class Order {

	private int orderID;
	private int aktienID;
	private int menge;
	private int stueckpreis;
	private static ArrayList<String> OrderListe;

	public static ArrayList<String> getOrderListe() {
		return OrderListe;
	}

	public void setOrderListe(ArrayList<String> orderListe) {
		OrderListe = orderListe;
	}

	public Order(int id) {
		this.orderID = id;
	}
}
