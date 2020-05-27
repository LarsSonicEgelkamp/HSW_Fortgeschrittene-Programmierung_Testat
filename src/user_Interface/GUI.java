package user_Interface;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Filemanager.Serialisierung;

public class GUI extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JLabel label;

	JRadioButton btnB�rsenmanager, btnAktion�r, btnAktiengesellschaft;

	JButton btnAnmelden, btnRegestrieren;

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
		this.setSize(400, 200);
		this.setMinimumSize(new Dimension(200, 200));
		JPanel content = new JPanel();

		btnB�rsenmanager = new JRadioButton("B�rsenmanager",true);
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
	

	private boolean anmeldeIDPr�fen(String checkWord) throws FileNotFoundException, ClassNotFoundException, IOException {
		boolean exestierendeID = true;
		// TODO Hier muss noch die �berpr�fung stattfinden ob die ID bereits existiert
		// oder nicht
//		exestierendeID = s.deserilize("test");
		
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
		}
	}

	/**
	 * Description: Hier wird das Interface f�r eine Aktiengesellschaft erstellt
	 *
	 */
	private void createAktiengesellschaftFenster() {
		JPanel aktiengesellschaftPanel = new JPanel();

		this.setContentPane(aktiengesellschaftPanel);
		this.validate();
	}

	/**
	 * Description: Hier wird das Interface f�r einen Aktion�r/Depotinhaber erstellt
	 *
	 */
	
	JButton meinDepot;
	private void createAktion�rFenster() {
		JPanel aktion�rPanel = new JPanel();
		
		meinDepot = new JButton("mein Depot");
		
		this.setContentPane(aktion�rPanel);
		this.validate();
	}

	/**
	 * Description: Hier wird das Interface f�r den B�rsenmanger erstellt
	 *
	 */
	private void createB�rsenmanagerFenster() {
		JPanel panelB�rsenmanager = new JPanel();

		this.setContentPane(panelB�rsenmanager);
		this.validate();

	}

}
