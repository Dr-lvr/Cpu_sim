package pippin.test;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import javax.swing.JPanel;

import pippin.system.mediators.Controller;

/// NUOVO MACRO-COMPONENTE ISTANZIATO DA SPRITE PROVIDER (SUPERCLASSE) E GESTITO IN SPRITE MANAGER (SOTTOCLASSE)


@SuppressWarnings("serial")
public class StoragePanel extends JPanel implements ActionListener{

	Vector<Button> buttons;
	Dialog d;
	
	Controller myController;
	
	public StoragePanel(Controller theController) {

		myController = theController;
		
		buttons = new Vector<Button>();
		this.setBounds(1030, 510, 300, 150);
		this.setBackground(Color.gray);
		int x = 1030, y = 410;
		for (int i = 0; i < 64; ++i) {

			buttons.add(new Button());
			buttons.get(i).setBounds(x, y, 10, 10);
			buttons.get(i).addActionListener(this);
			this.add(buttons.get(i));
			x += 5;
		}
	}

	public void actionPerformed(ActionEvent e) {

		Frame f = new Frame();
		d = new Dialog(f, "Memoria SSD", true);
		d.setLayout(new FlowLayout());
		Button b = new Button("OK");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
			}
		});
		Label t = new Label("Slot numero");
		d.add(t);
		d.add(b);
		d.setSize(300, 300);
		d.setVisible(true);
	}
}