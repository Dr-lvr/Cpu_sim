		package pippin;

		import java.awt.*;
		import java.awt.event.ActionEvent;
		import java.awt.event.ActionListener;
		import java.awt.event.MouseAdapter;
		import java.awt.event.MouseEvent;

		import javax.swing.*;
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////////// BOOTSTRAP /////////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		///----> TODO: COLLEGARE SPRITES E COMPONENTI NUOVE
		///----> TODO: SCRIVERE DOCUMENTAZIONE
		///----> TODO: APPUNTARE IL CODICE PER MODIFICHE

		public class Main {
			public static void main(String[] args) {


					JFrame frame = new JFrame("CPU_Sim_Menu");
					JPanel panel = new JPanel();
					panel.setLayout(new CardLayout(350,300));
					//panel.setBackground(Color.lightGray);
					//JLabel label = new JLabel();
					//label.setText("CPU_sim.V003");
					//label.setBounds(0, 0, 100, 100);
					Icon icon = new ImageIcon("../Sim_cpu/src/pippin/z__images2/mainMenuIcons/CPU_icona.png"); // ../Pippin_V003/src/pippin/z__images2/ir.png
					JButton button = new JButton(icon);
					button.setBackground(Color.orange);

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
					button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseEntered(MouseEvent me) {
							button.setBackground(Color.green);
						}

						@Override
						public void mouseExited(MouseEvent me) {
							button.setBackground(Color.orange);
						}
					});
					panel.add(button);
					frame.add(panel);
					//frame.add(label);
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