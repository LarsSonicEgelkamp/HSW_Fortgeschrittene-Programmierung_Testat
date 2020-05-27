package user_Interface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import Filemanager.Serialisierung;
import börsenprogramm.Boersenmanager;
import börsenprogramm.Depotinhaber;

public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel label;

	JRadioButton btnBörsenmanager, btnAktionär, btnAktiengesellschaft;

	JButton btnAnmelden, btnRegestrieren;
	JButton btnAbmelden = new JButton("Abmelden");

	JTextField txtAnmeldeID;

	Serialisierung s = new Serialisierung();

	/**
	 * 
	 * Hier wird die das User-Interface erzeugt
	 * 
	 */
	public GUI() {

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
		btnRegestrieren = new JButton("Regestrieren");

		content.add(btnBörsenmanager);
		content.add(btnAktionär);
		content.add(btnAktiengesellschaft);
		content.add(txtAnmeldeID);
		content.add(btnRegestrieren);
		content.add(btnAnmelden);

		btnAnmelden.addActionListener(this);
		btnRegestrieren.addActionListener(this);

		this.setContentPane(content);

	}

	private boolean anmeldeIDPrüfen(String checkWord)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		boolean exestierendeID = true;
		// TODO Hier muss noch die überprüfung stattfinden ob die ID bereits existiert
		// oder nicht
		exestierendeID = !s.deserilize(checkWord).equals(checkWord);

		return exestierendeID;
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
				if (!anmeldeIDPrüfen(txtAnmeldeID.getText())) {
					createBörsenmanagerFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Börsenmanager",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (ae.getSource() == this.btnAnmelden && btnAktionär.isSelected() == true) {
			try {
				if (!anmeldeIDPrüfen(txtAnmeldeID.getText())) {
					createAktionärFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Aktionär",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getSource() == this.btnAnmelden && btnAktiengesellschaft.isSelected() == true) {
			try {
				if (!anmeldeIDPrüfen(txtAnmeldeID.getText())) {
					createAktiengesellschaftFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Aktiengesellschaft",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getSource() == this.btnRegestrieren) {
			try {
				s.serialize(txtAnmeldeID.getText(), txtAnmeldeID.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getSource() == this.btnAbmelden) {
			this.createStartseite(this);
			this.validate();
		} else if (ae.getSource() == this.btnVerkaufen) {
			System.out.println("verkaufen");
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

	private void createAktionärFenster() {
		JPanel meinDepot = new JPanel();
		JComboBox depots = new JComboBox();
		btnVerkaufen = new JButton("Verkaufen");
		JList<String> meineAktienList = new JList<String>();

		// TO DO: Hier muss die Combobox noch befüllt werden
		for (Integer meineDepot : Depotinhaber.getMeineDepots()) {
			depots.add(meinDepot);
		}

		meinDepot.add(depots);
		meinDepot.add(meineAktienList);
		// TODO: Die Jlist muss noch befüllt werden

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tabpane.addTab("mein Depot", meinDepot);
		tabpane.addTab("Aktien", createAktienPanel());
		
		meinDepot.add(btnVerkaufen);
		meinDepot.add(btnAbmelden);
		
		JComboBox cbxAlleAktien = new JComboBox();
		JLabel lblAktienKaufen=new JLabel("Anzahl der Aktien:");
		JTextField txtAktienKaufen = new JTextField(5);
		JButton btnAktienKaufen = new JButton("kaufen");
		
		meinDepot.add(cbxAlleAktien);
		meinDepot.add(lblAktienKaufen);
		meinDepot.add(txtAktienKaufen);
		meinDepot.add(btnAktienKaufen);

		btnVerkaufen.addActionListener(this);

		this.setContentPane(tabpane);
		this.validate();
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

		JLabel lblAktienName = new JLabel("Aktien Name:");
		JLabel lblAktienWert = new JLabel("Aktien Wert:");
		JTextField txtAktienName = new JTextField(5);
		JTextField txtAktienWert = new JTextField(5);
		JButton btnAktieAnlegen = new JButton("Aktie anlegen");

		panelBörsenmanager.add(lblAktienName);
		panelBörsenmanager.add(txtAktienName);
		panelBörsenmanager.add(lblAktienWert);
		panelBörsenmanager.add(txtAktienWert);
		panelBörsenmanager.add(btnAktieAnlegen);
		btnAktieAnlegen.addActionListener(this);

		JLabel lblDepotName = new JLabel("Depot Name:");
		JTextField txtDepotName = new JTextField(5);
		JButton btnDepotAnlegen = new JButton("Depot anlegen");

		panelBörsenmanager.add(lblDepotName);
		panelBörsenmanager.add(txtDepotName);
		panelBörsenmanager.add(btnDepotAnlegen);
		btnDepotAnlegen.addActionListener(this);

		JLabel lblOrders = new JLabel("ausstehende Orders:");
		JComboBox cbxOrders = new JComboBox();
		JButton btnOrderAusführen = new JButton("Order ausführen");
		// TODO Die Combo Box muss noch befüllt werden und eine Textarea mit allen Aktien oder ein Jlist eingefügt werden
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
		// TODO Auto-generated method stub
		JPanel aktien = new JPanel();
		

		
		return aktien;
	}
}
