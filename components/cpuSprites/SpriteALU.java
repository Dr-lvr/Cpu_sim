package pippin.components.cpuSprites;
import java.awt.*;

import pippin.components.utility.Letter;
import pippin.components.utility.TextBox;
import pippin.manager.CpuParser;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteALU extends SpriteComponent {

    TextBox leftArgBox;
    TextBox rightArgBox;
    Letter operator;
    Font argFont;
    Polygon border;
    Color bgColor;
    static final int ARG_HEIGHT = 16;
    static final int ARG_VSPACE = 5;
    static final int OP_WIDTH = 15;
    static final int OP_HEIGHT = 15;
    static final float SLOPE = 3F;
    static final int V_WIDTH = 16;
    static final int INPUT_DIST = 35;

    public SpriteALU() {
		super();
	}

	public SpriteALU(Stage stage, int x, int y, int width, int height) {
        super(stage, "ALU", x, y, width, height);
        argFont = new Font("Helvetica", 0, 12);
        bgColor = new Color(224, 224, 224);
        addNode("DAT", 35, 0);
        addNode("MUX", width - 35, 0);
        addNode("DEC", (int)((float)(height / 2) / 3F), height / 2);
        addNode("ACC", width / 2, height);
        int veeHeight = 24;
        border = new Polygon();
        border.addPoint(x, y);
        border.addPoint(x + (width - 16) / 2, y);
        border.addPoint(x + width / 2, y + veeHeight);
        border.addPoint(x + (width + 16) / 2, y);
        border.addPoint(x + width, y);
        border.addPoint((x + width) - (int)((float)height / 3F), y + height);
        border.addPoint(x + (int)((float)height / 3F), y + height);
        border.addPoint(x, y);
        int argWidth = (width - 16) / 2 - 5;
        int argHSpace = 7;
        leftArgBox = new TextBox(stage, x + argHSpace, y + 5, argWidth, 16, "", "", argFont, false, true);
        rightArgBox = new TextBox(stage, (x + width) - (argWidth + argHSpace), y + 5, argWidth, 16, "", "", argFont, false, true);
        operator = new Letter(x + (width - 15) / 2, y + ((veeHeight + height) - 15) / 2, 15, 15);
    }

    ////set left arg
    public void setLeftArg(int value) {
        value &= 0xff;
        int extendedValue;
        if ((value & 0x80) != 0) {
            extendedValue = value | 0xffffff00;
        } else {
            extendedValue = value;
        }
        leftArgBox.setText(Integer.toString(extendedValue, 10), CpuParser.intToBinString(value, 8));
        leftArgBox.flashBG();
        markDirty();
    }

    public void setRightArg(int value) {
        value &= 0xff;
        int extendedValue;
        if((value & 0x80) != 0)
            extendedValue = value | 0xffffff00;
        else
            extendedValue = value;
        rightArgBox.setText(Integer.toString(extendedValue, 10), CpuParser.intToBinString(value, 8));
        rightArgBox.flashBG();
        markDirty();
    }

    public void setOperation(char op) {
        operator.set(op);
        operator.flashBG();
        markDirty();
    }

    public void clear() {
        leftArgBox.setText("", "");
        rightArgBox.setText("", "");
        operator.set(' ');
        markDirty();
    }

    public boolean drawAll(Graphics g) {
        markClean();
        g.setColor(bgColor);
        g.fillPolygon(border);
        super.dirty |= leftArgBox.drawAll(g);
        super.dirty |= rightArgBox.drawAll(g);
        super.dirty |= operator.drawAll(g);
        g.setColor(Color.black);
        g.drawPolygon(border);
        if (super.getDirtyPartner() != null) {
            super.getDirtyPartner().markDirty();
        }
        return super.dirty;
    }

    public boolean drawNew(Graphics g) {
        markClean();
        super.dirty |= leftArgBox.drawNew(g);
        super.dirty |= rightArgBox.drawNew(g);
        super.dirty |= operator.drawNew(g);
        g.setColor(Color.black);
        g.drawPolygon(border);
        if (super.getDirtyPartner() != null) {
            super.getDirtyPartner().markDirty();
        }
        return super.dirty;
    }

}
