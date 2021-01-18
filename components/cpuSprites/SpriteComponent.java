package pippin.components.cpuSprites;

import java.awt.*;
import java.util.Vector;

import pippin.genericTypes.Sprite;
import pippin.stage.Stage;

// Sprite that is part of another sprite
@SuppressWarnings("serial")
public class SpriteComponent extends Sprite {

    String name;
    Vector<Sprite_Node> nodes;
    
    

    public SpriteComponent() {
		super();
	}

	public SpriteComponent(Stage stage, String name, int x, int y, int width, int height) {
        super(stage, new Point(x, y));
        nodes = new Vector<>(4);
        setWidth(width);
        setHeight(height);
        this.name = name;
    }

    public void addNode(String nodeName, int dX, int dY) {
    	Sprite_Node node = new Sprite_Node(name + ":" + nodeName, super.getX() + dX, super.getY() + dY);
        nodes.addElement(node);
    }

    public void addStage(int theChannel) {
        super.addStage(theChannel);
        for (Sprite_Node node : nodes) {
            super.stage.addNode(node);
        }
    }

    public boolean drawAll(Graphics g) {
        markClean();
        if (super.getDirtyPartner() != null) {
            super.getDirtyPartner().markDirty();
        }
        return false;
    }

    public void dispose() {
    }

    public void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    public String toString() {
        return getClass().getName() + "[width=" + super.getWidth() + ",height=" + super.getHeight() + "]";
    }

	@Override
	public void setBgColor(Color color) {
		// TODO Auto-generated method stub
		
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
	public void flash() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
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
