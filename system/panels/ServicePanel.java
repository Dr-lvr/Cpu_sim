package pippin.system.panels;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.*;

import pippin.system.data.Job;
import pippin.system.mediators.Controller;

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/// NUOVO MACRO-COMPONENTE ISTANZIATO DA SPRITE PROVIDER (SUPERCLASSE) E GESTITO IN ANIMATION MANAGER (SOTTOCLASSE)

/*
 * query the editor by the controller, get the first available service, and set it no more available, 
 * then sets a dialog with the service code
 * 
 */

@SuppressWarnings("serial")
public class ServicePanel extends JPanel implements ActionListener {

	Vector<JButton> buttons;
	Dialog d;
	Controller myController;
	
	public ServicePanel(Controller theController) {

		myController = theController;
		
		buttons = new Vector<JButton>();
		this.setBounds(560, 530, 250, 100);
		this.setBackground(Color.gray);
		for (int i = 0; i < 8; ++i) {

			JButton b = new JButton("S"+i);
			buttons.add(b);
			this.add(buttons.get(i));
		}

		JFrame ff= new JFrame();
		ff.setResizable(true);
		JPanel c = new JPanel();
		c.setLayout( new FlowLayout() );
		c.setSize(300,300);
		c.setVisible(true);
		ff.add(c);
		ff.setBounds(400, 230, 200, 200);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				//c.setVisible(true);
				ff.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent me) {
				ff.setVisible(false);
			}
		});
	}

	public synchronized void refresh() throws Exception {
		
		if(myController.queryEditor()) {
			
			int added = myController.getLastAdded();
			
			buttons.get(added).setText("Nw"+myController.getLastAdded());
			buttons.get(added).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Job myJob = myController.getSlot(added);
					
					Frame f = new Frame();
					d = new Dialog(f, "Program", true);
					d.setLayout(new FlowLayout());
					JButton b = new JButton("OK");
					b.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							d.setVisible(false);
						}
					});
					Label t = new Label();
					t.setText(myJob.toString());
					
					myController.getMyScheduler().addJob(myJob);
					myController.setServiceRequest(true);
					
					d.add(t);
					d.add(b);
					d.setSize(300, 300);
					d.setVisible(true);
				}
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
