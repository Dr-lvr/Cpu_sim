package pippin.editorPanel;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class EditorMenu {

	JMenu menu, submenu;
	JMenuItem i1, i2, i3, i4, i5;

	public EditorMenu() {
		
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("Menu");
		submenu = new JMenu("Sub Menu");
		i1 = new JMenuItem("Item 1");
		i2 = new JMenuItem("Item 2");
		i3 = new JMenuItem("Item 3");
		i4 = new JMenuItem("Item 4");
		i5 = new JMenuItem("Item 5");
		menu.add(i1);
		menu.add(i2);
		menu.add(i3);
		submenu.add(i4);
		submenu.add(i5);
		menu.add(submenu);
		mb.add(menu);
	}

	public JMenu getMenu() {
		return menu;
	}

	public void setMenu(JMenu menu) {
		this.menu = menu;
	}

	public JMenu getSubmenu() {
		return submenu;
	}

	public void setSubmenu(JMenu submenu) {
		this.submenu = submenu;
	}

	public JMenuItem getI1() {
		return i1;
	}

	public void setI1(JMenuItem i1) {
		this.i1 = i1;
	}

	public JMenuItem getI2() {
		return i2;
	}

	public void setI2(JMenuItem i2) {
		this.i2 = i2;
	}

	public JMenuItem getI3() {
		return i3;
	}

	public void setI3(JMenuItem i3) {
		this.i3 = i3;
	}

	public JMenuItem getI4() {
		return i4;
	}

	public void setI4(JMenuItem i4) {
		this.i4 = i4;
	}

	public JMenuItem getI5() {
		return i5;
	}

	public void setI5(JMenuItem i5) {
		this.i5 = i5;
	}
}
