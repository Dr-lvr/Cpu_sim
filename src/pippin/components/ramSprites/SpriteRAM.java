package pippin.components.ramSprites;
import java.awt.*;

import pippin.components.cpuSprites.SpriteComponent;
import pippin.components.utility.TextBox;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public abstract class SpriteRAM extends SpriteComponent {

    protected int[] data;
    protected TextBox[] addBoxes;
    protected TextBox[] datBoxes;
    protected Font font;


    
    public SpriteRAM() {
		super();
	}

	public SpriteRAM(Stage stage, String name, int x, int y, int width, int height) {
        super(stage, name, x, y, width, height);
        font = new Font("Helvetica", 0, 12);
    }

    public int getValue(int address) {
        int index = addressToIndex(address);
        return data[index];
    }

    public abstract String getStringValue(int i);

    public abstract String checkValue(int i, String s);

    public abstract String checkValue(int i, int j);

    public abstract void setValue(int i, String s);

    public abstract void setValue(int i, int j);

    public abstract int firstAddress();

    public abstract int lastAddress();

    public abstract int nextAddress(int i);

    public abstract int prevAddress(int i);

    public abstract boolean showBooleanAddress(int i);

    public void flashAddress(int address) {
        int index = addressToIndex(address);
        addBoxes[index].flashBG();
        markDirty();
    }

    public void flashData(int address) {
        int index = addressToIndex(address);
        datBoxes[index].flashBG();
        markDirty();
    }

    public int clickToAddress(int x, int y) {
        for (int i = 0; i < datBoxes.length; i++) {
            if (datBoxes[i].isInside(x, y)) {
                return indexToAddress(i);
            }
        }
        return -1;
    }

    public Rectangle addressToRect(int address) {
        int index = addressToIndex(address);
        return datBoxes[index].getRect();
    }

    public abstract int addressToIndex(int i);

    protected abstract int indexToAddress(int i);

}
