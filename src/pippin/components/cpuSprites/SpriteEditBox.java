package pippin.components.cpuSprites;
import java.awt.*;
import java.util.Vector;

import pippin.genericTypes.Sprite;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteEditBox extends Sprite {

    Font font;
    FontMetrics fontMetrics;
    StringBuffer text;
    String textToString;
    boolean selected;
    int value;
    boolean calcOffsets;
    boolean centered;
    int xOffset;
    int yOffset;
    Color unselText;
    Color unselBG;
    Color selText;
    Color selBG;
    static final int MARGIN = 4;

    public SpriteEditBox(Stage stage, int x, int y, int width, int height, String text, boolean centered) {
        super(stage, x, y, width, height);
        font = new Font("Helvetica", 0, 12);
        selected = true;
        calcOffsets = true;
        unselText = Color.black;
        unselBG = Color.white;
        selText = Color.white;
        selBG = Color.black;
        this.text = new StringBuffer(text);
        textToString = text;
        this.centered = centered;
    }

    public void setValue(String text) {
        this.text = new StringBuffer(text);
        textToString = text;
        selected = true;
        calcOffsets = true;
        markDirty();
    }

    public String getValue1() {
        return text.toString();
    }

    public void addKey(char key) {
        if(selected) {
            selected = false;
            text.setLength(0);
        }
        if(key == '\b') {
            if(text.length() > 0)
                text.setLength(text.length() - 1);
        } else if(key >= ' ' && key <= '~')
            text.append(Character.toUpperCase(key));
        textToString = text.toString();
        if(fontMetrics != null && fontMetrics.stringWidth(textToString) > super.getWidth() - 8) {
            text.setLength(text.length() - 1);
            textToString = text.toString();
        }
        if(centered)
            calcOffsets = true;
        markDirty();
    }

    public boolean drawAll(Graphics g) {
        markClean();
        if(calcOffsets) {
            if(fontMetrics == null)
                fontMetrics = g.getFontMetrics(font);
            yOffset = super.getHeight() - (super.getHeight() - (fontMetrics.getAscent() - (1 + font.getSize() / 12))) / 2;
            if(centered)
                xOffset = (super.getWidth() - fontMetrics.stringWidth(textToString)) / 2;
            else
                xOffset = 4;
            calcOffsets = false;
        }
        g.setColor(selected ? selBG : unselBG);
        g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        g.setColor(selected ? selText : unselText);
        g.setFont(font);
        g.drawString(textToString, super.getX() + xOffset, super.getY() + yOffset);
        return super.dirty;
    }

    public String toString() {
        return getClass().getName() + "[" + textToString + "]";
    }

	@Override
	public void setBgColor(Color color) {
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
	public void scheduleWork(Vector<String> vector) {
		// TODO Auto-generated method stub
		
	}
}
