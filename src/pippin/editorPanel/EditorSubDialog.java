package pippin.editorPanel;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
//import javax.swing.JFrame;
import javax.swing.JLabel;
///import javax.swing.JPopupMenu.Separator;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EditorSubDialog extends Frame implements ActionListener {
	JTextField tf;
	JLabel l;
	JButton b;
	// Separator s;

	EditorSubDialog() {
		// s = new Separator();
		// s.setBounds(this.getX()+50, this.getY(), this.getWidth(), this.getHeight());
		Frame f = this;
		tf = new JTextField();
		tf.setBounds(50, 50, 150, 20);
		l = new JLabel();
		l.setBounds(50, 100, 250, 20);
		b = new JButton("Find IP");
		b.setBounds(50, 150, 95, 30);
		b.addActionListener(this);
		JButton c = new JButton("OK");

		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setVisible(false);
			}
		});

		c.setBounds(280, 150, 95, 30);
		// add(s);
		add(c);
		add(b);
		add(tf);
		add(l);
		// setSize(400, 400);
		// setLayout(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			String host = tf.getText();
			String ip = java.net.InetAddress.getByName(host).getHostAddress();
			l.setText("IP of " + host + " is: " + ip);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
