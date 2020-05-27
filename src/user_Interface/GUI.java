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
import b�rsenprogramm.Boersenmanager;
import b�rsenprogramm.Depotinhaber;

public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel label;

	JRadioButton btnB�rsenmanager, btnAktion�r, btnAktiengesellschaft;

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

		this.setTitle("B�rsenanwendung");
		this.setSize(200, 400);
		this.setMinimumSize(new Dimension(200, 200));
		JPanel content = new JPanel();

		btnB�rsenmanager = new JRadioButton("B�rsenmanager", true);
		btnAktiengesellschaft = new JRadioButton("Aktiengesellschaft");
		btnAktion�r = new JRadioButton("Aktion�r");
		ButtonGroup anmeldeAuswahl = new ButtonGroup();
		anmeldeAuswahl.add(btnAktiengesellschaft);
		anmeldeAuswahl.add(btnAktion�r);
		anmeldeAuswahl.add(btnB�rsenmanager);

		txtAnmeldeID = new JTextField("Hier bitte ihre ID eintragen");

		btnAnmelden = new JButton("Anmelden");
		btnRegestrieren = new JButton("Regestrieren");

		content.add(btnB�rsenmanager);
		content.add(btnAktion�r);
		content.add(btnAktiengesellschaft);
		content.add(txtAnmeldeID);
		content.add(btnRegestrieren);
		content.add(btnAnmelden);

		btnAnmelden.addActionListener(this);
		btnRegestrieren.addActionListener(this);

		this.setContentPane(content);

	}

	private boolean anmeldeIDPr�fen(String checkWord)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		boolean exestierendeID = true;
		// TODO Hier muss noch die �berpr�fung stattfinden ob die ID bereits existiert
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
		// der Buttons ist, wird der Text des JLabels entsprechend ge�ndert
		if (ae.getSource() == this.btnAnmelden && btnB�rsenmanager.isSelected() == true) {
			try {
				if (!anmeldeIDPr�fen(txtAnmeldeID.getText())) {
					createB�rsenmanagerFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "B�rsenmanager",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (ae.getSource() == this.btnAnmelden && btnAktion�r.isSelected() == true) {
			try {
				if (!anmeldeIDPr�fen(txtAnmeldeID.getText())) {
					createAktion�rFenster();
				} else {
					JOptionPane.showMessageDialog(null, "Die Anmelde-ID existiert nicht", "Aktion�r",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (HeadlessException | ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (ae.getSource() == this.btnAnmelden && btnAktiengesellschaft.isSelected() == true) {
			try {
				if (!anmeldeIDPr�fen(txtAnmeldeID.getText())) {
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
	 * Description: Hier wird das Interface f�r eine Aktiengesellschaft erstellt
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
	 * Description: Hier wird das Interface f�r einen Aktion�r/Depotinhaber erstellt
	 *
	 */

	JButton btnVerkaufen;

	private void createAktion�rFenster() {
		JPanel meinDepot = new JPanel();
		JComboBox depots = new JComboBox();
		btnVerkaufen = new JButton("Verkaufen");
		JList<String> meineAktienList = new JList<String>();

		// TO DO: Hier muss die Combobox noch bef�llt werden
		for (Integer meineDepot : Depotinhaber.getMeineDepots()) {
			depots.add(meinDepot);
		}

		meinDepot.add(depots);
		meinDepot.add(meineAktienList);
		// TODO: Die Jlist muss noch bef�llt werden

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
	 * Description: Hier wird das Interface f�r den B�rsenmanger erstellt
	 *
	 */
	private void createB�rsenmanagerFenster() {
		JPanel panelB�rsenmanager = new JPanel();

		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		tabpane.addTab("B�rsenmanager", panelB�rsenmanager);
		tabpane.addTab("Aktien", createAktienPanel());

		JLabel lblAktienName = new JLabel("Aktien Name:");
		JLabel lblAktienWert = new JLabel("Aktien Wert:");
		JTextField txtAktienName = new JTextField(5);
		JTextField txtAktienWert = new JTextField(5);
		JButton btnAktieAnlegen = new JButton("Aktie anlegen");

		panelB�rsenmanager.add(lblAktienName);
		panelB�rsenmanager.add(txtAktienName);
		panelB�rsenmanager.add(lblAktienWert);
		panelB�rsenmanager.add(txtAktienWert);
		panelB�rsenmanager.add(btnAktieAnlegen);
		btnAktieAnlegen.addActionListener(this);

		JLabel lblDepotName = new JLabel("Depot Name:");
		JTextField txtDepotName = new JTextField(5);
		JButton btnDepotAnlegen = new JButton("Depot anlegen");

		panelB�rsenmanager.add(lblDepotName);
		panelB�rsenmanager.add(txtDepotName);
		panelB�rsenmanager.add(btnDepotAnlegen);
		btnDepotAnlegen.addActionListener(this);

		JLabel lblOrders = new JLabel("ausstehende Orders:");
		JComboBox cbxOrders = new JComboBox();
		JButton btnOrderAusf�hren = new JButton("Order ausf�hren");
		// TODO Die Combo Box muss noch bef�llt werden und eine Textarea mit allen Aktien oder ein Jlist eingef�gt werden
		panelB�rsenmanager.add(lblOrders);
		panelB�rsenmanager.add(cbxOrders);
		panelB�rsenmanager.add(btnOrderAusf�hren);

		panelB�rsenmanager.add(btnAbmelden);
		btnAbmelden.addActionListener(this);

		this.setContentPane(tabpane);
		this.validate();

	}

	/**
	 * Description: Hier wird das Panel f�r den Aktienmarkt erstellt
	 *
	 */
	private JPanel createAktienPanel() {
		// TODO Auto-generated method stub
		JPanel aktien = new JPanel();
		

		
		return aktien;
	}
}
