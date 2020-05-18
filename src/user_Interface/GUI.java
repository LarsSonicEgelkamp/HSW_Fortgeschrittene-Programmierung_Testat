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
    
	JButton button1;
    JButton button2;
    JButton button3;
    
	public GUI () {
		
		this.setTitle("Börsenanwendung");
		this.setSize(400,200);
        panel = new JPanel();
       

	    
	    //Drei Buttons werden erstellt
        button1 = new JButton("Button 1");
        button2 = new JButton ("Button 2");
        button3 = new JButton ("Button 3");
        
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        
        this.add(panel);
        		
	}
	
	 public void actionPerformed (ActionEvent ae){
	        // Die Quelle wird mit getSource() abgefragt und mit den
	        // Buttons abgeglichen. Wenn die Quelle des ActionEvents einer
	        // der Buttons ist, wird der Text des JLabels entsprechend geändert
	        if(ae.getSource() == this.button1){

	        }
	        else if(ae.getSource() == this.button2){

	        }
	        else if (ae.getSource() == this.button3){

	        }
	    }
	
	

}
