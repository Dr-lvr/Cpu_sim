package pippin.genericTypes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// MIRROR OF A GENUIN GENERIC SPRITE

public interface SpriteType {
	
	public void scheduleWork(Vector<String> vector);

    public void setDirtyPartner(Sprite dirtyPartner);

    public void addStage(int theChannel);

    public boolean inside(int x, int y);

    public void move(int x, int y);

    public void move(Point p);

    public void reshape(int x, int y, int width, int height);

    public void reshape(Rectangle rect);

    public /*synchronized*/ abstract boolean drawAll(Graphics g);

    public boolean drawNew(Graphics g);

    public void markDirty();

    public boolean isDirty();

    public void markClean();

    public abstract String toString();

    public void dispose();
    
	public void finalize() throws Throwable;

	public int getWidth();

	public void setWidth(int width);

	public int getX();

	public void setX(int x);

	public int getY();

	public void setY(int y);

	public int getHeight();

	public void setHeight(int height);

	public void setBgColor(Color color);

	public void setValue(String stringValue);

	public void addKey(char key);

	public void flash();

	public int getValue();

	public void addNode(String string, int i, int j);

	public void setPC(boolean b);

	public void setValue(int i, String string);

	public void setLabel(int i, String string);

	public void fromScroll();

	public boolean isEditingLabel(int x, int y);

	public boolean isEditingLabel();

	public void shiftFromAddress(int editAddress);

	public String checkLabel(int editAddress, String value);

	public String getLabelStringValue(int editAddress);

	public int addressToIndex(int operand);

	public void flashBG();

	public void flashData(int operand);

	public boolean[] getShowAsVariable();

	public void flashAddress(int operand);

	public void clear();

	public void flashOperand();

	public void flashOperator();

	public Rectangle addressToLabelRect(int editAddress);

	public void setLeftArg(int i);

	public void setRightArg(int argument);

	public void setOperation(char operation);

	public void flashLine();

	public void set(boolean direct);

	public int getValue(int address);

	public int clickToAddress(int x, int y);

	public Rectangle addressToRect(int editAddress);

	public String getStringValue(int editAddress);

	public int nextAddress(int oldAddress);

	public int prevAddress(int oldAddress);

	public boolean showBooleanAddress(int editAddress);
	
	//public boolean showBooleanAddress(int editAddress);

	public String checkValue(int editAddress, String value);

	public void setValue(int value);

	public void setValue(int operand, int joinBits);

	public int firstAddress();

	public int lastAddress();

	public Object checkValue(int i, int value);

	public String getValue1();

}
