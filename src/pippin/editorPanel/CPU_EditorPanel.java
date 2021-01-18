	package pippin.editorPanel;

	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.MouseAdapter;
	import java.awt.event.MouseEvent;
	import java.io.IOException;
	//import java.awt.Button;
	import java.util.Vector;

	import javax.swing.*;
	//import javax.swing.ImageIcon;
	//import javax.swing.JButton;

	import pippin.system.mediators.Controller;

	@SuppressWarnings("serial")
	public class CPU_EditorPanel extends JPanel implements ActionListener {

		private static CPU_EditorPanel theController;
		Vector<JButton> panelButtons;
		Vector<JButton> panelButtons2;

		//EditorMenu m;
		Dialog d;

		private String actualImage;

		final String[] imageList = { "CPU_icona", "RAM_icona", "editor", "pippin/system", "queue", "setting"};
		final String[] imageList2 = { "exit", "acc", "alu", "counter", "decoder", "instruction", "MUX", "status"};
		//final String[] imageList3 = { "exit", "fifo", "fixed_priority","manual","multyLevel","priority","rr","shortest_remaining","workConservice"};
		public CPU_EditorPanel() throws IOException {
	/*
			m = new EditorMenu();
			this.add(m.menu);
			this.add(m.submenu);
	*/
			panelButtons = new Vector<JButton>();

			Icon icon;
			JButton button8;
			for (String image : imageList) {
				icon = new ImageIcon("../Sim_cpu/src/pippin/z__images2/mainMenuIcons/" + image + ".png"); // ../Pippin_V003/src/pippin/pippin.z__images2/ir.png
				actualImage = image;
				button8 = new JButton(icon);
				//button8.setBounds(0, 10, 200, 10);
				button8.setBackground(Color.lightGray);

				if(image == "CPU_icona") {
					button8.addActionListener(this);
				}
				JFrame ff= new JFrame();
				ff.setResizable(true);
				JPanel c = new JPanel();
				JLabel text = new JLabel(image);
				c.add(text);
				c.setLayout( new FlowLayout() );
				c.setSize(300,300);
				c.setVisible(true);
				ff.add(c);
				ff.setBounds(400, 230, 200, 200);
				JButton finalButton = button8;
				button8.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent me) {
						ff.setVisible(true);
						finalButton.setBackground(Color.orange);
					}

					@Override
					public void mouseExited(MouseEvent me) {
						ff.setVisible(false);
						finalButton.setBackground(Color.lightGray);
					}
				});

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
			d.setBackground(Color.lightGray);
			panelButtons2 = new Vector<JButton>();

			Icon icon;
			JButton button8;
					for (String image : imageList2) {
						icon = new ImageIcon("../Sim_cpu/src/pippin/z__images2/cpuIcons/" + image + ".png"); // ../Pippin_V003/src/pippin/pippin.z__images2/ir.png
						button8 = new JButton(icon);
						//button8.setBounds(0, 10, 200, 10);
						button8.setBackground(Color.lightGray);
						JButton finalButton = button8;
						if(image == "exit") {
							button8.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									f.setVisible(false);
								}
							});
							button8.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent me) {
									finalButton.setBackground(Color.red);
								}
								@Override
								public void mouseExited(MouseEvent me) {
									finalButton.setBackground(Color.lightGray);
								}
							});
						} else {
							button8.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent me) {
									finalButton.setBackground(Color.cyan);
								}
								@Override
								public void mouseExited(MouseEvent me) {
									finalButton.setBackground(Color.lightGray);
								}
							});
						}
						d.add(button8);
						panelButtons2.add(button8);
					}

			d.setBounds(370, 38, 360, 335);
			d.setVisible(true);
		}
	}
