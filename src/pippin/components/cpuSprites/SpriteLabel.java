package pippin.components.cpuSprites;
import java.awt.*;
import java.util.Vector;

import pippin.genericTypes.Sprite;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteLabel extends Sprite {

    Color textColor;
    Color bgColor;
    boolean calcOffsets;
    int xOffset;
    int yOffset;
    static final int LEFT = 1;
    static final int CENTER = 2;
    static final int RIGHT = 3;
    static final int TOP = 4;
    static final int BOTTOM = 5;
    int horPos;
    int vertPos;
    String text;
    private Font font;

    public SpriteLabel(Stage stage, String text, int x, int y, int width, int height, int horPos, int vertPos) {
        super(stage, x, y, width, height);
        textColor = Color.black;
        bgColor = Color.lightGray;
        calcOffsets = true;
        setFont(new Font("Helvetica", 0, 12));
        this.text = text;
        this.horPos = horPos;
        this.vertPos = vertPos;
    }

    public boolean drawAll(Graphics g) {
        markClean();
        g.setPaintMode();
        if(calcOffsets) {
            FontMetrics fm = g.getFontMetrics(getFont());
            if(horPos == 1)
                xOffset = 0;
            else if(horPos == 3)
                xOffset = super.getWidth() - fm.stringWidth(text);
            else
                xOffset = (super.getWidth() - fm.stringWidth(text)) / 2;
            if(vertPos == 4)
                yOffset = fm.getAscent() - (1 + getFont().getSize() / 12);
            else if(vertPos == 5)
                yOffset = super.getHeight();
            else
                yOffset = super.getHeight() - (super.getHeight() - (fm.getAscent() - (1 + getFont().getSize() / 12))) / 2;
            calcOffsets = false;
        }
        g.setColor(bgColor);
        g.fillRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        g.setColor(textColor);
        g.setFont(getFont());
        g.drawString(text, super.getX() + xOffset, super.getY() + yOffset);
        return super.dirty;
    }

    public String toString() {
        return getClass().getName() + "[]";
    }

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
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
