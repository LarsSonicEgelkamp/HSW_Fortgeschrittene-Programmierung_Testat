package user_Interface;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;



import Filemanager.Serialisierung;
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

	}

	/**
	 * @param gui
	 */
	private void createStartseite(GUI gui) {

		this.setTitle("Börsenanwendung");
		this.setSize(400, 200);
		this.setMinimumSize(new Dimension(200, 200));
		JPanel content = new JPanel();

		btnBörsenmanager = new JRadioButton("Börsenmanager",true);
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
	

	private boolean anmeldeIDPrüfen(String checkWord) throws FileNotFoundException, ClassNotFoundException, IOException {
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
		} else if (ae.getSource()==this.btnAbmelden) {
			this.createStartseite(this);
			this.validate();
		}
	}

	/**
	 * Description: Hier wird das Interface für eine Aktiengesellschaft erstellt
	 *
	 */
	private void createAktiengesellschaftFenster() {
		JPanel aktiengesellschaftPanel = new JPanel();
		
		
		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
		
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
	
	JButton meinDepot;
	private void createAktionärFenster() {
		JPanel meinDepot = new JPanel();
		JComboBox depots = new JComboBox();
		
		
		//TO DO: Hier muss die Combobox noch befüllt werden
		for (Integer meineDepot : Depotinhaber.getMeineDepots()) {
			depots.add(meinDepot);
		}		
		
		meinDepot.add(depots);
		
		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
		tabpane.addTab("mein Depot", meinDepot);
		tabpane.addTab("Aktien", createAktienPanel());
		
		meinDepot.add(btnAbmelden);
		
		this.setContentPane(tabpane);
		this.validate();
	}



	/**
	 * Description: Hier wird das Interface für den Börsenmanger erstellt
	 *
	 */
	private void createBörsenmanagerFenster() {
		JPanel panelBörsenmanager = new JPanel();
		
		
		JTabbedPane tabpane = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT );
		
		tabpane.addTab("Börsenmanager", panelBörsenmanager);
		tabpane.addTab("Aktien", createAktienPanel());
		
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
