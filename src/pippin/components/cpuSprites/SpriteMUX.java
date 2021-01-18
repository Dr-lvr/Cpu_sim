package pippin.components.cpuSprites;
import java.awt.*;

import pippin.components.utility.ColorTools;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteMUX extends SpriteComponent {

    static {
        lineColors = ColorTools.interpArrayHSB(Color.green, new Color(128, 0, 0), 10);
        bgColors = ColorTools.interpArrayHSB(Color.green, new Color(224, 224, 224), 10);
    }

    public SpriteMUX() {
		super();
	}

	long lineTime;
    long bgTime;
    static final int COLOR_COUNT = 10;
    static final int COLOR_TIME = 100;
    static Color[] lineColors;
    static Color[] bgColors;
    boolean calcOffsets;
    static final String label = "MUX";
    int xOffset;
    int yOffset;
    Font font;
    Polygon border;
    static final float SLOPE = 2F;
    static final int INPUT_DIST = 15;
    boolean showLine;
    boolean rightNode;

    public SpriteMUX(Stage stage, int x, int y, int width, int height) {
        super(stage, "MUX", x, y, width, height);
        calcOffsets = true;
        font = new Font("Helvetica", 0, 12);
        showLine = false;
        addNode("ADD", 15, 0);
        addNode("DAT", width - 15, 0);
        addNode("ALU", width / 2, height);
        addNode("DEC", (int)((float)height / 2.0F / 2.0F), height / 2);
        border = new Polygon();
        border.addPoint(x, y);
        border.addPoint(x + width, y);
        border.addPoint((x + width) - (int)((float)height / 2.0F), y + height);
        border.addPoint(x + (int)((float)height / 2.0F), y + height);
        border.addPoint(x, y);
    }

    public void set(boolean rightNode) {
        this.rightNode = rightNode;
        showLine = true;
        markDirty();
    }

    public void clear() {
        showLine = false;
        markDirty();
    }

    public void flashLine() {
        lineTime = System.currentTimeMillis();
        markDirty();
    }

    public void flashBG() {
        bgTime = System.currentTimeMillis();
        markDirty();
    }

    public boolean drawAll(Graphics g) {
        markClean();
        if(calcOffsets) {
            FontMetrics fm = g.getFontMetrics(font);
            xOffset = (super.getWidth() - fm.stringWidth("MUX")) / 2;
            yOffset = super.getHeight() - (super.getHeight() - (fm.getAscent() - (1 + font.getSize() / 12))) / 2;
            calcOffsets = false;
        }
        super.dirty |= ColorTools.setColor(g, bgColors, bgTime);
        g.fillPolygon(border);
        g.setColor(Color.black);
        g.drawPolygon(border);
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("MUX", super.getX() + xOffset, super.getY() + yOffset);
        if(super.getDirtyPartner() != null)
            super.getDirtyPartner().markDirty();
        return super.dirty;
    }

}
