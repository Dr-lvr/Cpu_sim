package pippin.components.utility;
import java.awt.*;

import pippin.stage.Stage;

public class TextBox {

    int x;
    int y;
    int width;
    int height;
    int symXOffset;
    int binXOffset;
    int yOffset;
    long textTime;
    long bgTime;
    boolean calcOffsets;
    boolean dirty;
    boolean frame;
    boolean centered;
    private String symText;
    private String binText;
    String symTabText;
    String binTabText;
    Color[] textColors;
    Color[] bgColors;
    Font font;
    Stage stage;

    public TextBox(Stage stage, int x, int y, int width, int height, String symText, String binText, Font font, boolean frame, boolean centered) {
        calcOffsets = true;
        dirty = true;
        this.stage = stage;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.font = font;
        this.frame = frame;
        this.centered = centered;
        setText(symText, binText);
        setTextColors(Color.red, Color.black);
        setBGColors(Color.green, new Color(224, 224, 224));
    }

    public TextBox(Stage stage, int x, int y, int width, int height, String symText, String binText, Font font, boolean frame, boolean centered, Color bgColor) {
        this(stage, x, y, width, height, symText, binText, font, frame, centered);
        setBGColors(Color.GREEN, bgColor);
    }

    public void setTextColors(Color activeColor, Color idleColor) {
        textColors = ColorTools.interpArrayRGB(activeColor, idleColor, 10);
    }

    public void setTextColors(Color[] textColors) {
        this.textColors = textColors;
    }

    public void setBGColors(Color activeColor, Color idleColor) {
        bgColors = ColorTools.interpArrayRGB(activeColor, idleColor, 10);
    }

    public void setBGColors(Color[] bgColors) {
        this.bgColors = bgColors;
    }

    public void dumpColors() {
        for(int i = 0; i < bgColors.length; i++)
            System.out.println("Color " + i + " = " + bgColors[i]);

    }

    public void reshape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        calcOffsets = true;
        dirty = true;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isInside(int x, int y) {
        return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
    }

    public void setText(String symText, String binText) {
        int tabPos = symText.indexOf('\t');
        if(tabPos == -1) {
            this.setSymText(symText);
            symTabText = "";
        } else {
            this.setSymText(symText.substring(0, tabPos));
            symTabText = symText.substring(tabPos + 1);
        }
        tabPos = binText.indexOf('\t');
        if(tabPos == -1) {
            this.setBinText(binText);
            binTabText = "";
        } else {
            this.setBinText(binText.substring(0, tabPos));
            binTabText = binText.substring(tabPos + 1);
        }
        calcOffsets = true;
        dirty = true;
    }

    public void flashText() {
        textTime = System.currentTimeMillis();
        dirty = true;
    }

    public void flashBG() {
        bgTime = System.currentTimeMillis();
        dirty = true;
    }

    public boolean drawAll(Graphics g) {
        dirty = false;
        if (calcOffsets) {
            FontMetrics fm = g.getFontMetrics(font);
            if (centered) {
                symXOffset = (width - fm.stringWidth(getSymText())) / 2;
                binXOffset = (width - fm.stringWidth(getBinText())) / 2;
            } else {
                symXOffset = 5;
                binXOffset = 5;
            }
            yOffset = height - (height - (fm.getAscent() - (1 + font.getSize() / 12))) / 2;
            calcOffsets = false;
        }
        dirty |= ColorTools.setColor(g, bgColors, bgTime);
        g.fillRect(x, y, width, height);
        if (frame) {
            g.setColor(Color.black);
            g.drawRect(x, y, width, height);
        }
        dirty |= ColorTools.setColor(g, textColors, textTime);
        g.setFont(font);
        if (stage.getMode() == 1) {
            g.drawString(getSymText(), x + symXOffset, y + yOffset);
            g.drawString(symTabText, x + 40, y + yOffset);
        } else {
            g.drawString(getBinText(), x + binXOffset, y + yOffset);
            g.drawString(binTabText, x + 40, y + yOffset);
        }
        return dirty;
    }

    public boolean drawNew(Graphics g) {
        if (dirty)
            return drawAll(g);
        else
            return false;
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }

	public String getBinText() {
		return binText;
	}

	public void setBinText(String binText) {
		this.binText = binText;
	}

	public String getSymText() {
		return symText;
	}

	public void setSymText(String symText) {
		this.symText = symText;
	}

}
