package pippin.editorPanel;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import java.awt.Button;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
import javax.swing.JPanel;

import pippin.system.mediators.Controller;

@SuppressWarnings("serial")
public class CPU_EditorPanel extends JPanel implements ActionListener {

	private static CPU_EditorPanel theController;
	Vector<JButton> panelButtons;

	//EditorMenu m;
	Dialog d;

	public CPU_EditorPanel() throws IOException {

		final String[] imageList = { "CPU_icona", "RAM_icona", "editor", "system", "queue", "setting"};
/*
		m = new EditorMenu();
		this.add(m.menu);
		this.add(m.submenu);
*/
		panelButtons = new Vector<JButton>();

		Icon icon;
		JButton button8;
		for (String image : imageList) {
			icon = new ImageIcon("../Sim_cpu/src/pippin/z__images2/" + image + ".png"); // ../Pippin_V003/src/pippin/z__images2/ir.png
			button8 = new JButton(icon);
			//button8.setBounds(0, 10, 200, 10);
			button8.setBackground(Color.lightGray);

			button8.addActionListener(this);
			
			this.add(button8);
			panelButtons.add(button8);
		}
		setLayout (new BoxLayout (this, BoxLayout.Y_AXIS)); 
	}
	// singleton pattern at lazy time
	public static CPU_EditorPanel getEditorInstanceP() throws IOException {
		if (theController == null) {
			synchronized (Controller.class) {
				if (theController == null) {
					theController = new CPU_EditorPanel();
				}
			}
		}
		return theController;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		Frame f = new Frame();
		d = new Dialog(f, "CPU settings", true);
		d.setLayout(new GridLayout(3,3));
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setVisible(false);
			}
		});
		d.add(b);
		for(int i = 0; i<8; ++i) {
			b = new JButton("-");
			d.add(b);
		}
		d.setBounds(370, 38, 360, 335);
		d.setVisible(true);
	}
}
