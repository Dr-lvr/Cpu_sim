package pippin.components.utility;
import java.awt.*;

public class Letter {

    static {
        textColors = ColorTools.interpArrayHSB(Color.red, Color.black, 10);
        bgColors = ColorTools.interpArrayHSB(Color.green, new Color(224, 224, 224), 10);
    }

    int x;
    int y;
    int width;
    int height;
    char c;
    long textTime;
    long bgTime;
    static final int COLOR_COUNT = 10;
    static Color[] textColors;
    static Color[] bgColors;
    boolean dirty;

    public Letter(int x, int y, int width, int height) {
        c = ' ';
        dirty = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void reshape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dirty = true;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public boolean inside(int x, int y) {
        return x >= this.x && x < this.x + width && y >= this.y && y < this.y + height;
    }

    public void set(char c) {
        this.c = c;
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
        dirty |= ColorTools.setColor(g, bgColors, bgTime);
        g.fillRect(x, y, width + 1, height + 1);
        dirty |= ColorTools.setColor(g, textColors, textTime);
        switch(c) {
            case 43: // '+'
                drawLine(g, new Point(x, y + height / 2), new Point(x + width, y + height / 2), 3, Color.black, Color.black);
                drawLine(g, new Point(x, y + height / 2 + 1), new Point(x + width, y + height / 2 + 1), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2, y), new Point(x + width / 2, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y), new Point(x + width / 2 + 1, y + height), 3, Color.black, Color.black);
                break;

            case 45: // '-'
                drawLine(g, new Point(x, y + height / 2), new Point(x + width, y + height / 2), 3, Color.black, Color.black);
                drawLine(g, new Point(x, y + height / 2 + 1), new Point(x + width, y + height / 2 + 1), 3, Color.black, Color.black);
                break;

            case 42: // '*'
                drawLine(g, new Point(x, y + height / 2), new Point((x + width) - 1, y + height / 2), 3, Color.black, Color.black);
                drawLine(g, new Point(x, (y + height / 2) - 1), new Point((x + width) - 1, (y + height / 2) - 1), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 4, y + height / 6), new Point(x + (3 * width) / 4 + 1, (y + (5 * height) / 6) - 1), 3, Color.black, Color.black);
                drawLine(g, new Point((x + width / 4) - 1, y + height / 6), new Point(x + (3 * width) / 4, (y + (5 * height) / 6) - 1), 3, Color.black, Color.black);
                drawLine(g, new Point(x + (3 * width) / 4, y + height / 6), new Point((x + width / 4) - 1, (y + (5 * height) / 6) - 1), 3, Color.black, Color.black);
                drawLine(g, new Point(x + (3 * width) / 4 + 1, y + height / 6), new Point(x + width / 4, (y + (5 * height) / 6) - 1), 3, Color.black, Color.black);
                break;

            case 47: // '/'
                drawLine(g, new Point(x + (3 * width) / 4, y), new Point(x + width / 4, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x + (3 * width) / 4 + 1, y), new Point(x + width / 4 + 1, y + height), 3, Color.black, Color.black);
                break;

            case 61: // '='
                drawLine(g, new Point(x, y + (2 * height) / 6), new Point(x + width, y + (2 * height) / 6), 3, Color.black, Color.black);
                drawLine(g, new Point(x, y + (2 * height) / 6 + 1), new Point(x + width, y + (2 * height) / 6 + 1), 3, Color.black, Color.black);
                drawLine(g, new Point(x, y + (4 * height) / 6), new Point(x + width, y + (4 * height) / 6), 3, Color.black, Color.black);
                drawLine(g, new Point(x, y + (4 * height) / 6 + 1), new Point(x + width, y + (4 * height) / 6 + 1), 3, Color.black, Color.black);
                break;

            case 58: // ':'
                drawLine(g, new Point(x + width / 2, y + height / 3), new Point(x + width / 2, y + (height / 3 + 3)), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y + height / 3), new Point(x + width / 2 + 1, y + (height / 3 + 3)), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2, y + (2 * height) / 3), new Point(x + width / 2, y + (2 * height) / 3 + 3), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y + (2 * height) / 3), new Point(x + width / 2 + 1, y + (2 * height) / 3 + 3), 3, Color.black, Color.black);
                break;

            case 94: // '^'
                drawLine(g, new Point(x + width / 2, y), new Point(x, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2, y), new Point(x + width, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y), new Point(x + 1, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y), new Point(x + width, (y + height) - 1), 3, Color.black, Color.black);
                break;

            case 33: // '!'
                drawLine(g, new Point(x + width / 2, y), new Point(x + width / 2, y + (7 * height) / 10), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y), new Point(x + width / 2 + 1, y + (7 * height) / 10), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2, y + (8 * height) / 10), new Point(x + width / 2, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x + width / 2 + 1, y + (8 * height) / 10), new Point(x + width / 2 + 1, y + height), 3, Color.black, Color.black);
                break;

            case 60: // '<'
                drawLine(g, new Point(x + width, y), new Point(x, y + height / 2), 3, Color.black, Color.black);
                drawLine(g, new Point((x + width) - 1, y), new Point(x, (y + height / 2) - 1), 3, Color.black, Color.black);
                drawLine(g, new Point(x, y + height / 2), new Point(x + width, y + height), 3, Color.black, Color.black);
                drawLine(g, new Point(x, (y + height / 2) - 1), new Point(x + width, (y + height) - 1), 3, Color.black, Color.black);
                break;
        }
        return dirty;
    }

    public boolean drawNew(Graphics g) {
        if(dirty)
            return drawAll(g);
        else
            return false;
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }

    private void drawLine(Graphics g, Point start, Point end, int width, Color fill, Color border) {
        g.setColor(fill);
        g.drawLine(start.x, start.y, end.x, end.y);
    }

}
