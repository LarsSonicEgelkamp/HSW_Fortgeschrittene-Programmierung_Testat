package user_Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener{

	JLabel label;
    JPanel panel;
    
	JButton btnB�rsenmanager;
    JButton btnAktion�r;
    JButton btnAktiengesellschaft;
    
	public GUI () {
		
		this.setTitle("B�rsenanwendung");
		this.setSize(400,200);
        panel = new JPanel();
        
        
	    
	    //Drei Buttons werden erstellt
        btnB�rsenmanager = new JButton("B�rsenmanager");
        btnAktion�r = new JButton ("Aktion�r");
        btnAktiengesellschaft = new JButton ("Aktiengesellschaft");
        
        btnB�rsenmanager.addActionListener(this);
        btnAktion�r.addActionListener(this);
        btnAktiengesellschaft.addActionListener(this);
        
        panel.add(btnB�rsenmanager);
        panel.add(btnAktion�r);
        panel.add(btnAktiengesellschaft);
        
        this.add(panel);
//        this.setVisible(true);
        
	}
	
	 public void actionPerformed (ActionEvent ae){
	        // Die Quelle wird mit getSource() abgefragt und mit den
	        // Buttons abgeglichen. Wenn die Quelle des ActionEvents einer
	        // der Buttons ist, wird der Text des JLabels entsprechend ge�ndert
	        if(ae.getSource() == this.btnB�rsenmanager){
	        	JPanel panelB�rsenmanager = new JPanel();
	        	this.add(panelB�rsenmanager);
//	        	panel.setVisible(true);
	        	
	        }
	        else if(ae.getSource() == this.btnAktion�r){

	        }
	        else if (ae.getSource() == this.btnAktiengesellschaft){

	        }
	    }
	
	

}
