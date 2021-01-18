package pippin;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// BOOTSTRAP /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import javax.swing.JPanel;

///----> TODO: COLLEGARE SPRITES E COMPONENTI NUOVE
///----> TODO: SCRIVERE DOCUMENTAZIONE
///----> TODO: APPUNTARE IL CODICE PER MODIFICHE

public class Main {
	public static void main(String[] args) {

			JFrame frame = new JFrame("CPU_Sim_Menu");
	        JPanel panel = new JPanel();  
	        panel.setLayout(new FlowLayout());  
	        JLabel label = new JLabel("---->>>");
	        JButton button = new JButton();  
	        button.setText("GOTO_CPU_Sim_Menu");
	        button.addActionListener ( new ActionListener()
	        {  
	            public void actionPerformed( ActionEvent e )  
	            {  
	            	frame.setVisible(false);
	            	try {
	    				New_Sim newSim = new New_Sim();
	    				newSim.setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 500,
	    						(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	    				newSim.setTitle("New_Tuned_Simulator");
	    				newSim.setVisible(true);
	    			} catch (Exception c) {
	    				c.printStackTrace();
	    				System.exit(0);
	    			}  
	            }  
	        });
	        panel.add(label);  
	        panel.add(button);  
	        frame.add(panel);  
	        frame.setBounds(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 500,
					(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	        //frame.setLocationRelativeTo(null);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	        frame.setVisible(true);
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// BOOTSTRAP-END /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////