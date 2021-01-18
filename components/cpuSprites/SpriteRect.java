package pippin.components.cpuSprites;
import java.awt.*;
import java.util.Vector;

import pippin.components.utility.ColorTools;
import pippin.genericTypes.Sprite;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteRect extends Sprite {

    static {
        bgColors = ColorTools.interpArrayHSB(Color.green, new Color(224, 224, 224), 10);
    }

    private Color bgColor;
    static Color[] bgColors;
    boolean framed;
    long time;


    public SpriteRect(Stage stage, int x, int y, int width, int height) {
        super(stage, new Point(0, 0));
        framed = false;
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        framed = true;
    }

    public SpriteRect(Stage stage, Color bgColor, Sprite_Node horiz, Sprite_Node vert) {
        super(stage, new Point(0, 0));
        framed = false;
        this.setBgColor(bgColor);
        int xCenter = horiz.location().x;
        int yCenter = vert.location().y;
        setX(xCenter - 3);
        setY(yCenter - 3);
        setWidth(7);
        setHeight(7);
        markDirty();
    }

    public SpriteRect() {
		// TODO Auto-generated constructor stub
	}

	public boolean inside(int x, int y) {
        return false;
    }

    public void mouseHandle(@SuppressWarnings("deprecation") Event event1) {
    }

    public void flash() {
        time = System.currentTimeMillis();
        markDirty();
    }

    public boolean drawAll(Graphics g) {
        markClean();
        g.setPaintMode();
        if (getBgColor() != null) {
            g.setColor(getBgColor());
        } else {
            super.dirty |= ColorTools.setColor(g, bgColors, time);
        }
        g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        if(framed) {
            g.setColor(Color.black);
            g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        }
        if(super.getDirtyPartner() != null) {
            super.getDirtyPartner().drawAll(g);
        }
        return super.dirty;
    }

    public String toString() {
        return getClass().getName() + "[]";
    }

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	@Override
	public void setValue(String stringValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addKey(char key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addNode(String string, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPC(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(int i, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(int i, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fromScroll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEditingLabel(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEditingLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shiftFromAddress(int editAddress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String checkLabel(int editAddress, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabelStringValue(int editAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addressToIndex(int operand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void flashBG() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flashData(int operand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean[] getShowAsVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flashAddress(int operand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flashOperand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flashOperator() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle addressToLabelRect(int editAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLeftArg(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRightArg(int argument) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOperation(char operation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flashLine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(boolean direct) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getValue(int address) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clickToAddress(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Rectangle addressToRect(int editAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStringValue(int editAddress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nextAddress(int oldAddress) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int prevAddress(int oldAddress) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean showBooleanAddress(int editAddress) {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String checkValue(int editAddress, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(int operand, int joinBits) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int firstAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastAddress() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object checkValue(int i, int value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void scheduleWork(Vector<String> vector) {
		// TODO Auto-generated method stub
		
	}
}
