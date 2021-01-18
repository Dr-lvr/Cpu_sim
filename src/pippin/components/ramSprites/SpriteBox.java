package pippin.components.ramSprites;
import java.awt.*;

import pippin.components.utility.TextBox;
import pippin.manager.CpuParser;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteBox extends SpriteRAM {
    TextBox box;
    Font font;
    String symText;
    String binText;
    int value;
    private boolean isPC;

    
	public SpriteBox() {
		super();
	}

	public SpriteBox(Stage stage, String name, int x, int y, int width, int height, String symText, String binText) {
        super(stage, name, x, y, width, height);
        font = new Font("Helvetica", 0, 12);
        setPC(false);
        box = new TextBox(stage, x, y, width, height, symText, binText, font, true, true);
    }

    public SpriteBox(Stage stage, String name, int x, int y, int width, int height, int value) {
        super(stage, name, x, y, width, height);
        font = new Font("Helvetica", 0, 12);
        setPC(false);
        symText = Integer.toString(value, 10);
        binText = CpuParser.intToBinString(value, 8);
        box = new TextBox(stage, x, y, width, height, symText, binText, font, true, true);
    }

    public void setText(String symText, String binText) {
        this.symText = symText;
        this.binText = binText;
        box.setText(symText, binText);
        markDirty();
    }

    public String checkValue(int address, String value) {
        int base = super.stage.getMode() != 1 ? 2 : 10;
        try {
            int result = CpuParser.stringToSEx8(value, base);
            return checkValue(address, result);
        } catch(NumberFormatException _ex) {
            return "not a legal " + (base != 10 ? "binary" : "decimal") + " number";
        }
    }

    public String checkValue(int address, int value) {
        int base = super.stage.getMode() != 1 ? 2 : 10;
        if(value < (isPC() ? '\0' : -128) || value > 127)
            return "out of range: must be from " + (base != 10 ? isPC() ? "00000000" : "10000000" : isPC() ? "0" : "-128") + " to " + (base != 10 ? isPC() ? "01111110" : "01111111" : isPC() ? "126" : "127");
        if(isPC() && value % 2 == 1)
            return "value must be even";
        else
            return null;
    }

    public void setValue(int value) {
        this.value = CpuParser.intToSEx8(value);
        String symText = CpuParser.sEx8ToDecString(this.value);
        String binText = CpuParser.sEx8ToBinString(this.value);
        setText(symText, binText);
        markDirty();
    }

    public void setValue(int address, int value) {
        setValue(value);
    }

    public void setValue(int address, String value) {
        try {
            int base = super.stage.getMode() != 1 ? 2 : 10;
            int result = CpuParser.stringToSEx8(value, base);
            setValue(address, result);
        } catch(NumberFormatException _ex) {
            //NO-OP
        }
    }

    public int getValue() {
        return value;
    }

    public int getValue(int address) {
        return getValue();
    }

    public String getStringValue(int address) {
        String result;
        if(super.stage.getMode() == 1)
            result = CpuParser.sEx8ToDecString(value);
        else
            result = CpuParser.sEx8ToBinString(value);
        return result;
    }

    public int firstAddress() {
        return 0;
    }

    public int lastAddress() {
        return 0;
    }

    public int nextAddress(int address) {
        return 0;
    }

    public int prevAddress(int address) {
        return 0;
    }

    public boolean showBooleanAddress(int addres) {
        return false;
    }

    public void flashAddress(int i) {
    }

    public void flashData(int i) {
    }

    public int clickToAddress(int x, int y) {
        return 0;
    }

    public Rectangle addressToRect(int address) {
        return new Rectangle(super.getX(), super.getY(), super.getWidth(), super.getHeight());
    }

    public void flashText() {
        box.flashText();
        markDirty();
    }

    public void flashBG() {
        box.flashBG();
        markDirty();
    }

    public boolean drawAll(Graphics g) {
        markClean();
        super.dirty |= box.drawAll(g);
        if(super.getDirtyPartner() != null)
            super.getDirtyPartner().markDirty();
        return super.dirty;
    }

    public int addressToIndex(int address) {
        return 0;
    }

    protected int indexToAddress(int index) {
        return 0;
    }

	public boolean isPC() {
		return isPC;
	}

	public void setPC(boolean isPC) {
		this.isPC = isPC;
	}
}
