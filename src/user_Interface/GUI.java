package user_Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Filemanager.CSV_Manager;
import Filemanager.Serialisierung;
import Ordermanager.Orderverarbeitung;
import boersenprogramm.AktuellerUser;
import boersenprogramm.Boerse;
import boersenprogramm.Boersenmanager;
import boersenprogramm.Depot;
import boersenprogramm.Depotinhaber;
import boersenprogramm.Transaktion;
import boersenprogramm.Aktie;
import boersenprogramm.AktuellerUser;
import boersenprogramm.Boerse;
import boersenprogramm.Boersenmanager;
import boersenprogramm.Depot;
import boersenprogramm.Depotinhaber;
import boersenprogramm.Transaktion;

public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel label;

	JRadioButton btnBoersenmanager, btnAktionaer, btnAktiengesellschaft;

	JPanel meinDepot;

	JButton btnAnmelden, btnRegestrieren;

	JButton btnAbmelden = new JButton("Abmelden");

	JTextField txtAnmeldeID, txtDepotinhaberID, txtAktienID, txtAktienWert;

	JButton btnDepotAnlegen, btnAktieAnlegen;

	Serialisierung s = new Serialisierung();

	Statement stat;

	AktuellerUser aUser;

	Boersenmanager bm;

	/**
	 * 
	 * Hier wird die das User-Interface erzeugt
	 * 
	 */
	public GUI(Statement stat) {
		this.stat = stat;
		createStartseite(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * @param gui
	 */
	private void createStartseite(GUI gui) {

		this.setTitle("Boersenanwendung");
		this.setSize(200, 400);
		this.setMinimumSize(new Dimension(200, 200));
		JPanel content = new JPanel();

		btnBoersenmanager = new JRadioButton("Boersenmanager", true);
		btnAktiengesellschaft = new JRadioButton("Aktiengesellschaft");
		btnAktionaer = new JRadioButton("Aktionär");
		ButtonGroup anmeldeAuswahl = new ButtonGroup();
		anmeldeAuswahl.add(btnAktiengesellschaft);
		anmeldeAuswahl.add(btnAktionaer);
		anmeldeAuswahl.add(btnBoersenmanager);

		txtAnmeldeID = new JTextField("Hier bitte ihre ID eintragen");

		btnAnmelden = new JButton("Anmelden");

		content.add(btnBoersenmanager);
		content.add(btnAktionaer);
		content.add(btnAktiengesellschaft);
		content.add(txtAnmeldeID);
		content.add(btnAnmelden);

		btnAnmelden.addActionListener(this);

		this.setContentPane(content);

	}

	/**
	 * 
	 * @param checkWord
	 * @param anmeldeSubjekt
	 * @return boolean: Gibt true zurück, wenn die ID valide ist.
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	private boolean anmeldeIDPruefen(String checkWord, String anmeldeSubjekt)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
//		boolean exestierendeID = true;

		ResultSet ids;
		if (anmeldeSubjekt.contentEquals("boersenmanager")) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ids = stat.executeQuery("SELECT ID FROM Boersenmanager WHERE ID=" + checkWord + ";");
			if (ids.next()) {
				return true;
			}
		} else if (anmeldeSubjekt.contentEquals("aktiengesellschaft")) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ids = stat.executeQuery("SELECT ID FROM Aktiengesellschaft WHERE ID=" + checkWord + ";");
			if (ids.next()) {
				return true;
			}
		} else if (anmeldeSubjekt.contentEquals("aktionaer")) {
			this.stat = ConnectionManager.ueberpruefeConnection(stat);
			ids = stat.executeQuery("SELECT ID FROM Depotinhaber WHERE ID=" + checkWord + ";");
			if (ids.next()) {
				return true;
			}
		}
		// TODO Hier muss noch die überprüfung stattfinden ob die ID bereits existiert
		// oder nicht
//		exestierendeID = !s.deserilize(checkWord).equals(checkWord);

//		return exestierendeID;
		return false;
	}

	/**
	 * Description:
	 */
	public void actionPerformed(ActionEvent ae) {
		// Die Quelle wird mit getSource() abgefragt und mit den
		// Buttons abgeglichen. Wenn die Quelle des ActionEvents einer
		// der Buttons ist, wird der Text des JLabels entsprechend geändert
		if (ae.getSource() == this.btnAnmelden && btnBoersenmanager.isSelected() == true) {
			try {
				if (anmeldeIDPruefen(txtAnmeldeID.getText(), "boersenmanager")) {
					aUser = new AktuellerUser("boersenmanager", Integer.parseInt(txtAnmeldeID.getText()));
					createBoersenmanagerFenster();
					bm = new Boersenmanager();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Börsenmanager",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException | SQLException e) {
				this.setErrorMessage(e);
			}

		} else if (ae.getSource() == this.btnAnmelden && btnAktionaer.isSelected() == true) {
			try {
				if (anmeldeIDPruefen(txtAnmeldeID.getText(), "aktionaer")) {
					aUser = new AktuellerUser("depotinhaber", Integer.parseInt(txtAnmeldeID.getText()));
					createAktionaerFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Aktionär",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException | SQLException e) {
				this.setErrorMessage(e);
			}
		} else if (ae.getSource() == this.btnAnmelden && btnAktiengesellschaft.isSelected() == true) {
			try {
				if (anmeldeIDPruefen(txtAnmeldeID.getText(), "aktiengesellschaft")) {
					aUser = new AktuellerUser("aktiengesellschaft", Integer.parseInt(txtAnmeldeID.getText()));
					createAktiengesellschaftFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Aktiengesellschaft",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException | SQLException e) {
				this.setErrorMessage(e);
			}
		} else if (ae.getSource() == this.btnAbmelden) {
			this.createStartseite(this);
			this.validate();
		} else if (ae.getSource() == this.btnVerkaufen) {
			System.out.println("verkaufen");
		} else if (ae.getSource() == this.depots) {

			try {
				DefaultListModel<String> listModel = new DefaultListModel<String>();

				int index = this.getComponentIndex(meineAktienList);
				this.meinDepot.remove(meineAktienList);
				Boerse b = new Boerse(stat);
				ArrayList<Aktie> akList = b.getAktienListe(aUser);
				for (Aktie a : akList) {
					int aktuellesDepot = a.getDepot(a.getId());
					int gewaehltesDe = (Integer) depots.getSelectedItem();
					if (gewaehltesDe == aktuellesDepot) {
						listModel.addElement("ID: " + a.getId() + " Wert:" + a.getWert(a.getId()));
					}
				}
				meineAktienList = new JList(listModel);

				this.meinDepot.add(meineAktienList, index);

				this.revalidate();
				this.repaint();

			} catch (Exception e) {// SQLException |
				this.setErrorMessage(e);
			}
		} else if (ae.getSource() == this.btnDepotAnlegen) {
			int depotinhaberID = Integer.parseInt(txtDepotinhaberID.getText());
			try {
				bm.createDepot(stat, depotinhaberID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getSource() == btnAktieAnlegen) {
//			bm.aktieAnlegen(stat,  txtAktienID.getText(), txtAktienWert.getText(), AktiengesellschaftsID, DepotinhaberID, depotID);
		} else if (ae.getSource() == cbxAktienID) {

		} else if (ae.getSource() == this.orderEinreichen) {
			try {
				JFileChooser chooserOrderEinreichen = new JFileChooser();
				chooserOrderEinreichen.setDialogTitle("Bitte w�hlen sie ihre Order aus.");
				chooserOrderEinreichen.showOpenDialog(null);
				File f = chooserOrderEinreichen.getSelectedFile();
				this.orderEinreichen(f);
			} catch (IOException e) {
				this.setErrorMessage(e);
			}
		} else if (ae.getSource() == this.btnOrdersAusfuehren) {
			try {
				this.ordersAusfuehren();
			} catch (IOException | SQLException e) {
				this.setErrorMessage(e);
			}
		}
	}

	public int getComponentIndex(Component component) throws Exception {
		if (component != null && component.getParent() != null) {
			Container c = component.getParent();
			for (int i = 0; i < c.getComponentCount(); i++) {
				if (c.getComponent(i) == component)
					return i;
			}
		}

		throw new Exception("Index der gesuchten Componente kann nicht gefunden werden");
	}

	/**
	 * Description: Hier wird das Interface für eine Aktiengesellschaft erstellt
	 *
	 */
	private void createAktiengesellschaftFenster() {
		JPanel aktiengesellschaftPanel = new JPanel();

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		tabpane.addTab("Aktien Gesellschaft", aktiengesellschaftPanel);
		tabpane.addTab("Aktien", createAktienPanel());
		tabpane.addTab("Alle Transaktionen unserer Aktien", createTransaktionPanel());

		aktiengesellschaftPanel.add(btnAbmelden);

		this.setContentPane(tabpane);
		this.validate();
	}

	/**
	 * Description: Hier wird das Interface für einen Aktionär/Depotinhaber
	 * erstellt
	 *
	 */
	JComboBox<Integer> depots;
	JList<String> meineAktienList;
	JButton btnVerkaufen;
	JButton orderEinreichen;

	private void createAktionaerFenster() throws SQLException {
		meinDepot = new JPanel();
		btnVerkaufen = new JButton("Verkaufen");
		DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel<Integer>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		orderEinreichen = new JButton("Order einreichen");

		Boerse b = new Boerse(stat);

		for (Depot de : b.getDepotListe(aUser)) {
			comboBoxModel.addElement(de.getId());
		}
		depots = new JComboBox<Integer>(comboBoxModel);
		depots.addActionListener(this);

		ArrayList<Aktie> akList = b.getAktienListe(aUser);
		for (Aktie a : akList) {
			int aktuellesDepot = a.getDepot(a.getId());
			int gewaehltesDe = (Integer) depots.getSelectedItem();
			if (gewaehltesDe == aktuellesDepot) {
				listModel.addElement("ID: " + a.getId() + " Wert:" + a.getWert(a.getId()));
			}
		}
		meineAktienList = new JList<String>(listModel);

		meinDepot.add(depots);
		meinDepot.add(meineAktienList);

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabpane.addTab("mein Depot", meinDepot);
		tabpane.addTab("Aktien", createAktienPanel());
		tabpane.addTab("Depots", createDepotPanel());

		tabpane.addTab("Meine Transaktionen", createTransaktionPanel());

//		tabpane.add("Aktien Historie", createWertHistoriePanel());

		meinDepot.add(btnVerkaufen);

		JComboBox cbxAlleAktien = new JComboBox();
		JLabel lblAktienKaufen = new JLabel("Anzahl der Aktien:");
		JTextField txtAktienKaufen = new JTextField(5);
		JButton btnAktienKaufen = new JButton("kaufen");

		meinDepot.add(cbxAlleAktien);
		meinDepot.add(lblAktienKaufen);
		meinDepot.add(txtAktienKaufen);
		meinDepot.add(btnAktienKaufen);

		orderEinreichen.addActionListener(this);
		btnVerkaufen.addActionListener(this);
		meinDepot.add(orderEinreichen);
		meinDepot.add(btnAbmelden);
		btnAbmelden.addActionListener(this);

		this.setContentPane(tabpane);
		this.validate();
	}

	Boerse b = new Boerse(stat);

	JComboBox cbxAktienID;

	private void createWertHistoriePanel(Boerse b) {
		JPanel werteHistoriePanel = new JPanel();
		cbxAktienID = new JComboBox();
		JList aktienHistorie = new JList();

		werteHistoriePanel.add(cbxAktienID);
		werteHistoriePanel.add(aktienHistorie);
		try {
			for (String aktie : b.alleAktienLesen(stat)) {
				cbxAktienID.addItem(aktie);
			}
		} catch (SQLException e) {
			this.setErrorMessage(e);
		}

		cbxAktienID.addActionListener(this);
	}

	/**
	 * Description: Hier wird das Interface fuer den Boersenmanger erstellt
	 *
	 */
	JButton btnOrdersAusfuehren;

	private void createBoersenmanagerFenster() {
		JPanel panelBoersenmanager = new JPanel();
		btnOrdersAusfuehren = new JButton("Alle Orders ausfuehren");
		btnOrdersAusfuehren.addActionListener(this);

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		tabpane.addTab("Boersenmanager", panelBoersenmanager);
		tabpane.addTab("Aktien", createAktienPanel());
		tabpane.addTab("Depots", createDepotPanel());
		tabpane.addTab("Alle Transaktionen", createTransaktionPanel());

		JLabel lblAktienID = new JLabel("Aktien ID:");
		JLabel lblAktienWert = new JLabel("Aktien Wert:");
		txtAktienID = new JTextField(5);
		txtAktienWert = new JTextField(5);
		btnAktieAnlegen = new JButton("Aktie anlegen");
		JLabel lblDepotinhaberID = new JLabel("Depotinhaber ID:");
		txtDepotinhaberID = new JTextField(5);

		panelBoersenmanager.add(lblAktienID);
		panelBoersenmanager.add(txtAktienID);
		panelBoersenmanager.add(lblAktienWert);
		panelBoersenmanager.add(txtAktienWert);
		panelBoersenmanager.add(btnAktieAnlegen);
		btnAktieAnlegen.addActionListener(this);

		JLabel lblDepotID = new JLabel("Depot ID:");
		JTextField txtDepotID = new JTextField(5);
		btnDepotAnlegen = new JButton("Depot anlegen");

		panelBoersenmanager.add(lblDepotID);
		panelBoersenmanager.add(txtDepotID);
		panelBoersenmanager.add(lblDepotinhaberID);
		panelBoersenmanager.add(txtDepotinhaberID);
		panelBoersenmanager.add(btnDepotAnlegen);
		btnDepotAnlegen.addActionListener(this);

		JLabel lblOrders = new JLabel("ausstehende Orders:");
		JComboBox cbxOrders = new JComboBox();
		JButton btnOrderAusfuehren = new JButton("Order ausführen");
		// TODO Die Combo Box muss noch befüllt werden und eine Textarea mit allen
		// Aktien oder ein Jlist eingefügt werden
		panelBoersenmanager.add(lblOrders);
		panelBoersenmanager.add(cbxOrders);
		panelBoersenmanager.add(btnOrderAusfuehren);
		panelBoersenmanager.add(btnOrdersAusfuehren);

		panelBoersenmanager.add(btnAbmelden);
		btnAbmelden.addActionListener(this);

		this.setContentPane(tabpane);
		this.validate();

	}

	/**
	 * Description: Hier wird das Panel für den Aktienmarkt erstellt
	 *
	 */
	private JPanel createAktienPanel() {
		JPanel aktien = new JPanel();
		Boerse b = new Boerse(stat);
		DefaultTableModel tamodel = new DefaultTableModel();
		try {
			ArrayList<Aktie> liste = b.getAktienListe(aUser);

			String[] newIdentifiers = { "ID", "Aktueller Wert", "Von Aktiengesellschaft", "Aktuell in Besitz von",
					"zu finden in Depot" };
			tamodel.setColumnIdentifiers(newIdentifiers);
			for (Aktie ak : liste) {
				Integer[] rowData = { ak.getId(), ak.getWert(ak.getId()), (ak).getAktiengesellschaft(ak.getId()),
						ak.getDepotinhaber(ak.getId()), ak.getDepot(ak.getId()) };
				tamodel.addRow(rowData);

			}
			JTable aktienListe = new JTable(tamodel);
			JScrollPane sp = new JScrollPane(aktienListe);
			aktien.add(sp);
		} catch (SQLException e1) {
			this.setErrorMessage(e1);
		}
		this.validate();
		return aktien;
	}

	private JPanel createDepotPanel() {
		JPanel depots = new JPanel();
		Boerse b = new Boerse(stat);
		DefaultTableModel talmodel = new DefaultTableModel();
		try {
			ArrayList<Depot> liste = b.getDepotListe(aUser);

			String[] newIdentifiers = { "ID", "Aktuell in Besitz von" };
			talmodel.setColumnIdentifiers(newIdentifiers);
			for (Depot de : liste) {
				Depotinhaber di = new Depotinhaber(de.getInhaber(de.getId()), stat);
				String inhaberName = di.getName(di.getId());
				String[] rowData = { "" + de.getId(), "" + de.getInhaber(de.getId()) + " [ " + inhaberName + "]" };
				talmodel.addRow(rowData);

			}
			JTable depotListe = new JTable(talmodel);
			JScrollPane sp = new JScrollPane(depotListe);
			depots.add(sp);
		} catch (SQLException e1) {
			this.setErrorMessage(e1);
		}
		this.validate();
		return depots;
	}

	public void setErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
	}

	public void setErrorMessage(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, e.getMessage(), "Fehlermeldung", JOptionPane.ERROR_MESSAGE);
	}

//	private DefaultListCellRenderer createAktienListRenderer() {
//		return new DefaultListCellRenderer() {
//			private Color background = new Color(0, 100, 255, 15);
//			private Color defaultBackground = (Color) UIManager.get("List.background");
//
//			@Override
//			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
//					boolean cellHasFocus) {
//				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//				if (c instanceof JLabel) {
//					JLabel label = (JLabel) c;
//					
//					Aktie ak = (Aktie) value;
//					try {
//						label.setText(String.format(" %s            %s            %s            %s            %s",
//								ak.getId(), ak.getWert(ak.getId()), ak.getAktiengesellschaft(ak.getId()),
//								ak.getDepotinhaber(ak.getId()), ak.getDepot(ak.getId())));
//					} catch (SQLException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if (!isSelected) {
//						label.setBackground(index % 2 == 0 ? background : defaultBackground);
//					}
//				}
//				return c;
//			}
//		};
//	}

	JPanel transaktionsP;

	private JPanel createTransaktionPanel() {
		transaktionsP = new JPanel();
		Boerse b = new Boerse(stat);
		DefaultTableModel tamodel = new DefaultTableModel();
		try {
			ArrayList<Transaktion> liste = b.getTransaktionsListe(aUser);
			if (aUser.getUserArt().contentEquals("aktiengesellschaft")) {
				String[] newIdentifiers = { " Transaktions ID", "Aktien ID", "Verkaufswert" };
				tamodel.setColumnIdentifiers(newIdentifiers);
				for (Transaktion ta : liste) {
					Integer[] rowData = { ta.getTransaktionsID(), ta.getAktienID(ta.getTransaktionsID()),
							ta.getVerkaufswert(ta.getTransaktionsID()) };
					tamodel.addRow(rowData);

				}
			} else {
				String[] newIdentifiers = { " Transaktions ID", "Aktien ID", "Verkaufswert", "Verkaufsdepot",
						"Ankaufsdepot" };
				tamodel.setColumnIdentifiers(newIdentifiers);
				for (Transaktion ta : liste) {
					Integer[] rowData = { ta.getTransaktionsID(), ta.getAktienID(ta.getTransaktionsID()),
							ta.getVerkaufswert(ta.getTransaktionsID()), ta.getVerkaufsDepotID(ta.getTransaktionsID()),
							ta.getAnkaufsDepotID(ta.getTransaktionsID()) };
					tamodel.addRow(rowData);

				}
			}
			JTable aktienListe = new JTable(tamodel);
			JScrollPane sp = new JScrollPane(aktienListe);
			transaktionsP.add(sp);
		} catch (SQLException e1) {
			this.setErrorMessage(e1);
		}
		this.validate();
		return transaktionsP;

	}

	private void orderEinreichen(File order) throws IOException {
		if (!this.orderPruefen(order)) {
			throw new IOException("Falsches Dateiformat. Die Datei muss eine CSV-Datei sein.");
		}
		CSV_Manager csvmag = new CSV_Manager();
		csvmag.neueOrderDatei(order);
	}

	/**
	 * �berpr�ft, ob die eingereichte Datei das richtige Format hat.
	 * 
	 * @param order: Die eingereichte Datei.
	 * @return: true, wenn das Dateiformat korrekt ist; false, wenn das DAteiformat
	 *          falsch ist
	 */
	private boolean orderPruefen(File order) {
		String[] splitOrder = order.getName().split("\\.");
		int index = splitOrder.length - 1;
		if (splitOrder[index].contentEquals("csv")) {
			return true;
		}
		return false;
	}

	private void ordersAusfuehren() throws IOException, SQLException {
		Date datum = null;// TODO aktuelles Datum initialisieren oder User nach Datum fragen und dann
							// auslesen
		Orderverarbeitung ord = new Orderverarbeitung(stat, datum);
	}
}
