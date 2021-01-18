package pippin.controlBar;
import java.awt.*;

import pippin.components.utility.MultiLineLabel;

@SuppressWarnings("serial")
public class ButtonDialog extends Dialog {

    static final Font font = new Font("Dialog", 0, 12);
    static final String[] okList = {"OK"};
    int result;
    Button[] buttons;
    MultiLineLabel label;
    Component owner;

    public static Frame getParent(Component component) {
        for(; component != null && !(component instanceof Frame); component = component.getParent());
        return (Frame)component;
    }

    public static void showOKDialog(Component owner, String title, String message) {
        Frame frame = getParent(owner);
        ButtonDialog dialog = new ButtonDialog(frame, owner, title, message, okList);
        dialog.show();
    }

    public static void showOKDialog(Component frame, Component owner, String title, String message) {
        Frame realFrame = getParent(frame);
        ButtonDialog dialog = new ButtonDialog(realFrame, owner, title, message, okList);
        dialog.show();
    }

    public ButtonDialog(Frame frame, Component owner, String title, String message, String[] buttonLabels) {
        super(frame, title, true);
        result = -2;
        this.owner = owner;
        setFont(font);
        setResizable(false);
        setLayout(new BorderLayout(25, 25));
        label = new MultiLineLabel(message, 20, 20);
        add("Center", label);
        Panel p = new Panel();
        p.setLayout(new FlowLayout(buttonLabels.length != 1 ? 1 : 2, 15, 15));
        buttons = new Button[buttonLabels.length];
        for(int i = 0; i < buttonLabels.length; i++) {
            p.add("South", buttons[i] = new Button(buttonLabels[i]));
        }
        add("South", p);
        pack();
    }

    @SuppressWarnings("deprecation")
	public synchronized void show() {
        Dimension dimScreen = getToolkit().getScreenSize();
        move((dimScreen.width - size().width) / 2, (dimScreen.height - size().height) / 3);
        super.show();
    }

    @SuppressWarnings("deprecation")
	public boolean handleEvent(Event evt) {
        if(evt.id == 201) {
            postResult(-1);
            return true;
        } else {
            return super.handleEvent(evt);
        }
    }

    @SuppressWarnings("deprecation")
	public boolean keyDown(Event evt, int key) {
        if((key == 10 || key == 13) && buttons.length == 1) {
            postResult(0);
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
	public boolean action(Event e, Object arg) {
        if(e.target instanceof Button) {
            for(int i = 0; i < buttons.length; i++) {
                if (e.target == buttons[i]) {
                    postResult(i);
                    return true;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public int result() {
        return result;
    }

    public synchronized void postResult(int result) {
        this.result = result;
        setVisible(false);
        dispose();
        if  (owner != null) {
            owner.requestFocus();
        }
        notify();
    }
}
