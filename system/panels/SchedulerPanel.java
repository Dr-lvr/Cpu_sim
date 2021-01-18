package pippin.system.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.*;

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

		JFrame ff= new JFrame();
		ff.setResizable(true);
		JPanel c = new JPanel();
		JLabel text = new JLabel("Scheduler panel for scheduling");
		c.add(text);
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
