package user_Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import user_Interface.ConnectionManager;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import Filemanager.Serialisierung;
import börsenprogramm.Aktie;
import börsenprogramm.AktuellerUser;
import börsenprogramm.Boerse;
import börsenprogramm.Boersenmanager;
import börsenprogramm.Depot;
import börsenprogramm.Depotinhaber;

public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel label;

	JRadioButton btnBörsenmanager, btnAktionär, btnAktiengesellschaft;

	JButton btnAnmelden;
	JButton btnAbmelden = new JButton("Abmelden");

	JTextField txtAnmeldeID, txtDepotinhaberID, txtAktienID, txtAktienWert;

	JButton btnDepotAnlegen, btnAktieAnlegen;

	JComboBox<Integer> depots;

	Serialisierung s = new Serialisierung();

	Statement stat;

	AktuellerUser aUser;
	JPanel aktuellesPanel;

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
		System.out.println("Hallo");

	}

	/**
	 * @param gui
	 */
	private void createStartseite(GUI gui) {

		this.setTitle("Börsenanwendung");
		this.setSize(200, 400);
		this.setMinimumSize(new Dimension(200, 200));
		JPanel content = new JPanel();

		btnBörsenmanager = new JRadioButton("Börsenmanager", true);
		btnAktiengesellschaft = new JRadioButton("Aktiengesellschaft");
		btnAktionär = new JRadioButton("Aktionär");
		ButtonGroup anmeldeAuswahl = new ButtonGroup();
		anmeldeAuswahl.add(btnAktiengesellschaft);
		anmeldeAuswahl.add(btnAktionär);
		anmeldeAuswahl.add(btnBörsenmanager);

		txtAnmeldeID = new JTextField("Hier bitte ihre ID eintragen");

		btnAnmelden = new JButton("Anmelden");

		content.add(btnBörsenmanager);
		content.add(btnAktionär);
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
	private boolean anmeldeIDPrüfen(String checkWord, String anmeldeSubjekt)
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
		if (ae.getSource() == this.btnAnmelden && btnBörsenmanager.isSelected() == true) {
			try {
				if (anmeldeIDPrüfen(txtAnmeldeID.getText(), "boersenmanager")) {
					aUser = new AktuellerUser("boersenmanager", Integer.parseInt(txtAnmeldeID.getText()));
					createBörsenmanagerFenster();
					bm = new Boersenmanager();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Börsenmanager",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException | SQLException e) {
				this.setErrorMessage(e);
			}

		} else if (ae.getSource() == this.btnAnmelden && btnAktionär.isSelected() == true) {
			try {
				if (anmeldeIDPrüfen(txtAnmeldeID.getText(), "aktionaer")) {
					aUser = new AktuellerUser("depotinhaber", Integer.parseInt(txtAnmeldeID.getText()));
					createAktionärFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Aktionär",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException | SQLException e) {
				this.setErrorMessage(e);
			}
		} else if (ae.getSource() == this.btnAnmelden && btnAktiengesellschaft.isSelected() == true) {
			try {
				if (anmeldeIDPrüfen(txtAnmeldeID.getText(), "aktiengesellschaft")) {
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
			JPanel tempPanel = this.getAktuellesPanel();
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
		}
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

		aktiengesellschaftPanel.add(btnAbmelden);

		this.setContentPane(tabpane);
		this.validate();
	}

	/**
	 * Description: Hier wird das Interface für einen Aktionär/Depotinhaber erstellt
	 *
	 */

	JButton btnVerkaufen;

	private void createAktionärFenster() throws SQLException {
		JPanel meinDepot = new JPanel();
		btnVerkaufen = new JButton("Verkaufen");
		DefaultComboBoxModel<Integer> comboBoxModel = new DefaultComboBoxModel<Integer>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();

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
		JList<String> meineAktienList = new JList<String>(listModel);

		meinDepot.add(depots);
		meinDepot.add(meineAktienList);

		// TODO: Die Jlist muss noch befüllt werden

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabpane.addTab("mein Depot", meinDepot);
		tabpane.addTab("Aktien", createAktienPanel());
		tabpane.addTab("Depots", createDepotPanel());

		meinDepot.add(btnVerkaufen);

		JComboBox cbxAlleAktien = new JComboBox();
		JLabel lblAktienKaufen = new JLabel("Anzahl der Aktien:");
		JTextField txtAktienKaufen = new JTextField(5);
		JButton btnAktienKaufen = new JButton("kaufen");

		meinDepot.add(cbxAlleAktien);
		meinDepot.add(lblAktienKaufen);
		meinDepot.add(txtAktienKaufen);
		meinDepot.add(btnAktienKaufen);

		btnVerkaufen.addActionListener(this);

		meinDepot.add(btnAbmelden);
		btnAbmelden.addActionListener(this);

		this.setContentPane(tabpane);
		this.aktuellesPanel = meinDepot;
		this.validate();
	}

	public JPanel getAktuellesPanel() {
		return this.aktuellesPanel;
	}

	/**
	 * Description: Hier wird das Interface für den Börsenmanger erstellt
	 *
	 */
	private void createBörsenmanagerFenster() {
		JPanel panelBörsenmanager = new JPanel();

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		tabpane.addTab("Börsenmanager", panelBörsenmanager);
		tabpane.addTab("Aktien", createAktienPanel());
		tabpane.addTab("Depots", createDepotPanel());

		JLabel lblAktienID = new JLabel("Aktien ID:");
		JLabel lblAktienWert = new JLabel("Aktien Wert:");
		txtAktienID = new JTextField(5);
		txtAktienWert = new JTextField(5);
		btnAktieAnlegen = new JButton("Aktie anlegen");
		JLabel lblDepotinhaberID = new JLabel("Depotinhaber ID:");
		txtDepotinhaberID = new JTextField(5);

		panelBörsenmanager.add(lblAktienID);
		panelBörsenmanager.add(txtAktienID);
		panelBörsenmanager.add(lblAktienWert);
		panelBörsenmanager.add(txtAktienWert);
		panelBörsenmanager.add(btnAktieAnlegen);
		btnAktieAnlegen.addActionListener(this);

		JLabel lblDepotID = new JLabel("Depot ID:");
		JTextField txtDepotID = new JTextField(5);
		btnDepotAnlegen = new JButton("Depot anlegen");

		panelBörsenmanager.add(lblDepotID);
		panelBörsenmanager.add(txtDepotID);
		panelBörsenmanager.add(lblDepotinhaberID);
		panelBörsenmanager.add(txtDepotinhaberID);
		panelBörsenmanager.add(btnDepotAnlegen);
		btnDepotAnlegen.addActionListener(this);

		JLabel lblOrders = new JLabel("ausstehende Orders:");
		JComboBox cbxOrders = new JComboBox();
		JButton btnOrderAusführen = new JButton("Order ausführen");
		// TODO Die Combo Box muss noch befüllt werden und eine Textarea mit allen
		// Aktien oder ein Jlist eingefügt werden
		panelBörsenmanager.add(lblOrders);
		panelBörsenmanager.add(cbxOrders);
		panelBörsenmanager.add(btnOrderAusführen);

		panelBörsenmanager.add(btnAbmelden);
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
			this.setErrorMessage("Fehler beim Erstellen der Aktienliste" + e1);
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
			this.setErrorMessage("Fehler beim Erstellen der Aktienliste" + e1);
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

	private DefaultListCellRenderer createAktienListRenderer() {
		return new DefaultListCellRenderer() {
			private Color background = new Color(0, 100, 255, 15);
			private Color defaultBackground = (Color) UIManager.get("List.background");

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (c instanceof JLabel) {
					JLabel label = (JLabel) c;
					Aktie ak = (Aktie) value;
					try {
						label.setText(String.format(" %s            %s            %s            %s            %s",
								ak.getId(), ak.getWert(ak.getId()), ak.getAktiengesellschaft(ak.getId()),
								ak.getDepotinhaber(ak.getId()), ak.getDepot(ak.getId())));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!isSelected) {
						label.setBackground(index % 2 == 0 ? background : defaultBackground);
					}
				}
				return c;
			}
		};
	}
}
