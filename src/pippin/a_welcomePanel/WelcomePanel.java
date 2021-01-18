    package pippin.a_welcomePanel;

    import pippin.a_newSimLauncher.New_Sim;

    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.awt.event.MouseAdapter;
    import java.awt.event.MouseEvent;
    import java.io.IOException;

    public class WelcomePanel extends JFrame {

        public WelcomePanel() throws IOException {

            super();
            JPanel panel = new JPanel();
            panel.setLayout(new CardLayout(350,300));
            Icon icon = new ImageIcon("../Sim_cpu/src/pippin/z__images2/mainMenuIcons/CPU_icona.png"); // ../Pippin_V003/src/pippin/pippin.z__images2/ir.png
            JButton button = new JButton(icon);
            button.setBackground(Color.orange);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBackground(new Color(102, 102, 102));
            setMinimumSize(new Dimension(0, 0));
            setLayout(new FlowLayout());

            JFrame frame = this;
            button.addActionListener ( new ActionListener()
            {
                public void actionPerformed( ActionEvent e )
                {
                    frame.setVisible(false);
                    try {
                        WelcomePanel pippin = new WelcomePanel();
                    } catch (IOException c) {
                        c.printStackTrace();
                    }
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
