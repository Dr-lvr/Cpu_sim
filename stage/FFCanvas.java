package pippin.stage;
import java.awt.*;

@SuppressWarnings("serial")
public class FFCanvas extends Canvas {

    Image buf;
    Graphics bufGraphics;
    Dimension bufSize;
    boolean page;
    boolean zeroRangeBug;
    int pageX;
    int pageY;
    int pageWidth;
    int pageHeight;
    Scrollbar scrollX;
    Scrollbar scrollY;
    FFCanvas partner;

    public FFCanvas() {
        page = false;
        zeroRangeBug = false;
    }

    public void setScrolls(Scrollbar scrollX, Scrollbar scrollY) {
        setScrolls(scrollX, scrollY, false);
    }

    public void setScrolls(Scrollbar scrollX, Scrollbar scrollY, boolean zeroRangeBug) {
        this.scrollX = scrollX;
        this.scrollY = scrollY;
        this.zeroRangeBug = zeroRangeBug;
        toScrolls();
    }

    public void setPartner(FFCanvas partner) {
        this.partner = partner;
    }

    @SuppressWarnings("deprecation")
	public void reshape(int x, int y, int width, int height) {
        super.reshape(x, y, width, height);
        repaint();
    }

    public void pageResize(int pageWidth, int pageHeight) {
        page = true;
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;
        toScrolls();
        repaint();
    }

    public void pageReshape(int pageX, int pageY, int pageWidth, int pageHeight) {
        page = true;
        this.pageX = pageX;
        this.pageY = pageY;
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;
        toScrolls();
        repaint();
    }

    @SuppressWarnings("deprecation")
	public Dimension pageSize() {
        if(page)
            return new Dimension(pageWidth, pageHeight);
        else
            return size();
    }

    @SuppressWarnings("deprecation")
	public Rectangle pageBounds() {
        if(page)
            return new Rectangle(pageX, pageY, pageWidth, pageHeight);
        else
            return new Rectangle(0, 0, size().width, size().height);
    }

    @SuppressWarnings("deprecation")
	public void fromScrolls() {
        if(scrollX != null) {
            if(scrollX.getValue() < 0)
                pageX = 0;
            else
                pageX = -scrollX.getValue();
            if(scrollX.getMaximum() > pageWidth - size().width)
                pageWidth = scrollX.getMaximum() + size().width;
        }
        if(scrollY != null) {
            if(scrollY.getValue() < 0)
                pageY = 0;
            else
                pageY = -scrollY.getValue();
            if(scrollY.getMaximum() > pageHeight - size().height)
                pageHeight = scrollY.getMaximum() + size().height;
        }
        repaint();
    }

    @SuppressWarnings("deprecation")
	public void toScrolls() {
        int minimum = 0;
        if(zeroRangeBug)
            minimum = -1;
        if(scrollX != null)
            scrollX.setValues(pageX != 0 ? -pageX : -1, size().width, minimum, pageWidth - size().width);
        if(scrollY != null)
            scrollY.setValues(pageY != 0 ? -pageY : -1, size().height, minimum, pageHeight - size().height);
        if(partner != null)
            partner.fromScrolls();
    }

    public synchronized void update(Graphics g) {
        if(g == null)
            g = getGraphics();
        if(g != null)
            paint(g);
    }

    public synchronized void paint(Graphics g) {
        if(isVisible()) {
            boolean oldBuffer = true;
            @SuppressWarnings("deprecation")
			int width = page ? pageWidth : size().width;
            @SuppressWarnings("deprecation")
			int height = page ? pageHeight : size().height;
            if(bufSize == null || bufGraphics == null || buf == null || bufSize.width != width || bufSize.height != height) {
                oldBuffer = false;
                if(bufGraphics != null)
                    bufGraphics.dispose();
                bufSize = new Dimension(width, height);
                buf = createImage(width, height);
                if(buf != null)
                    bufGraphics = buf.getGraphics();
            }
            Graphics drawG;
            if(buf == null) {
                drawG = g.create();
                drawG.translate(pageX, pageY);
            } else {
                drawG = bufGraphics;
            }
            if(drawG == null)
                System.out.println("FFCanvas.paint: ERROR! drawG is null!!!!");
            if(oldBuffer)
                paintNew(drawG);
            else
                paintAll(drawG);
            if(buf != null) {
                if(page) {
                    g.drawImage(buf, pageX, pageY, this);
                    return;
                }
                g.drawImage(buf, 0, 0, this);
            }
        }
    }

    public void paintNew(Graphics g) {
        paintAll(g);
    }

    public void paintAll(Graphics g1) {
    }

    public void dispose() {
        if(buf != null) {
            buf.flush();
            buf = null;
        }
        if(bufGraphics != null) {
            bufGraphics.dispose();
            bufGraphics = null;
        }
        bufSize = null;
    }

    @SuppressWarnings("deprecation")
	public void finalize() throws Throwable {
        dispose();
        super.finalize();
    }
}
