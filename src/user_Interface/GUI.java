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
    
	JButton btnBörsenmanager;
    JButton btnAktionär;
    JButton btnAktiengesellschaft;
    
	public GUI () {
		
		this.setTitle("Börsenanwendung");
		this.setSize(400,200);
        panel = new JPanel();
        
        
	    
	    //Drei Buttons werden erstellt
        btnBörsenmanager = new JButton("Börsenmanager");
        btnAktionär = new JButton ("Aktionär");
        btnAktiengesellschaft = new JButton ("Aktiengesellschaft");
        
        btnBörsenmanager.addActionListener(this);
        btnAktionär.addActionListener(this);
        btnAktiengesellschaft.addActionListener(this);
        
        panel.add(btnBörsenmanager);
        panel.add(btnAktionär);
        panel.add(btnAktiengesellschaft);
        
        this.add(panel);
//        this.setVisible(true);
        
	}
	
	 public void actionPerformed (ActionEvent ae){
	        // Die Quelle wird mit getSource() abgefragt und mit den
	        // Buttons abgeglichen. Wenn die Quelle des ActionEvents einer
	        // der Buttons ist, wird der Text des JLabels entsprechend geändert
	        if(ae.getSource() == this.btnBörsenmanager){
	        	JPanel panelBörsenmanager = new JPanel();
	        	this.add(panelBörsenmanager);
//	        	panel.setVisible(true);
	        	
	        }
	        else if(ae.getSource() == this.btnAktionär){

	        }
	        else if (ae.getSource() == this.btnAktiengesellschaft){

	        }
	    }
	
	

}
