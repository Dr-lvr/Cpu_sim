package pippin.system.panels;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;

import pippin.system.mediators.Controller;

/// NUOVO MACRO-COMPONENTE ISTANZIATO DA SPRITE PROVIDER (SUPERCLASSE) E GESTITO IN SPRITE MANAGER (SOTTOCLASSE)

@SuppressWarnings("serial")
public class SchedulerPanel extends JPanel implements ActionListener {

	Vector<JButton> buttons;
	Dialog d;

	Controller myController;

	public SchedulerPanel(Controller theController) {

		myController = theController;

		buttons = new Vector<JButton>();

		this.setBounds(1030, 350, 300, 120);
		this.setBackground(Color.gray);
		int x = 1030, y = 410;
		for (int i = 0; i < 21; ++i) {
			for (int j = 0; j < 4; ++j) {

				buttons.add(new JButton());
				buttons.get(j).setBounds(x, y, 10, 10);
				this.add(buttons.get(i));
				x += 5;
			}
			x = 1030;
			y += 5;
		}
	}

	public synchronized void refresh() {
		/*
		 * map the service used get a job by the scheduler generate a dialog
		 * 
		 */
		if (myController.queryService()) {
			int i = 0;
			while (i < buttons.size()) {

				buttons.get(i).setBackground(Color.LIGHT_GRAY);
				++i;
			}
			i = 0;
			while (i < myController.getMyScheduler().getQueue().size()) {

				buttons.get(i).setBackground(Color.RED);
				++i;
			}
			myController.getMyScheduler().setControl(true);
		}
	}
	
	public Vector<String> getNextSchedule() throws Exception {

		return myController.getMyScheduler().nextJob();
	}
	
	public int getStackPointer() {
		
		return myController.getMyScheduler().getStackPointer();
	}

	public int getProcessLife() {

		return myController.getMyScheduler().getProcessLife();
	}

	public void decLife() {

		myController.getMyScheduler().decLife();
	}

	public boolean getControl() {
		
		return myController.getMyScheduler().queryControl();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
