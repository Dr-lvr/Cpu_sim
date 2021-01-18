package pippin.system.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pippin.system.data.Job;
import pippin.system.mediators.Controller;

/// NUOVO MACRO-COMPONENTE ISTANZIATO DA SPRITE PROVIDER (SUPERCLASSE) E GESTITO IN SPRITE MANAGER (SOTTOCLASSE)

/*
 * init editor panel with controller and mediator
 * when compile&run is pressed aquire&parse the program in the controller with the mediator, and set it in the controller cache
 * 
 */
@SuppressWarnings("serial")
public class TextPanel extends JPanel implements ActionListener{

	private TextArea area;
	private Button b;
	private Dialog d;
	private Dialog c;
	
	Controller myController;
	
	public TextPanel(Controller theController) {
		
		myController = theController;
		area = new TextArea();
		b = new Button("Compile&Run");
		b.addActionListener(this);
		add(b);
		add(area);

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

	public void actionPerformed(ActionEvent e) {
		
		String text = area.getText();
		
		myController.setEdited(true);
		myController.setInSlot(new Job(this.parseText(text)));
		
		JFrame f= new JFrame();
		f.setResizable(true);
        d = new Dialog(f , "Compiler", true);  
        d.setLayout( new FlowLayout() );  
        Button b = new Button ("OK"); 
        b.addActionListener ( new ActionListener()
        {  
            public void actionPerformed( ActionEvent e )  
            {  
                d.setVisible(false);  
            }  
        });
        TextArea t = new TextArea (text);
        t.setBounds(0, 0, 50, 50);
        d.add(t);  
        d.add(b);   
        d.setSize(300,300);    
        d.setVisible(true);
	}
	
	public Vector<String> parseText(String program) {
		
		Vector<String> tmp = new Vector<String>();
		StringBuffer str = new StringBuffer();
		for(int i = 0; i<program.length(); ++i) {
			
			if(program.charAt(i) == ';') {
				tmp.add(str.toString());
				tmp.add("");
				str = new StringBuffer();
				
			} else if(program.charAt(i) == '\n' || program.charAt(i) == '\r'){
				
			} else {
				str.append(program.charAt(i));
			}
		}
		return tmp;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TextArea getArea() {
		return area;
	}

	public void setArea(TextArea area) {
		this.area = area;
	}
}