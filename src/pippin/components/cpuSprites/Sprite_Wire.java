package pippin.components.cpuSprites;
import java.awt.*;
import java.util.Vector;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//PHYSICAL WIRE

import pippin.a_newSimLauncher.New_Sim;
import pippin.components.utility.ColorTools;
import pippin.exceptions.AbortedException;
import pippin.genericTypes.Sprite;
import pippin.stage.Stage;
import pippin.test.Simulation;

@SuppressWarnings("serial")
public class Sprite_Wire extends Sprite {
	
	Sprite_Node start;
	Sprite_Node end;
    int[] xList;
    int[] yList;
    long[] timeList;
    int count;
    boolean horizontal;
    static final int SEG_LENGTH = 15;
    static final int COLOR_COUNT = 10;
    static final int COLOR_TIME = 100;
    static final int SEG_TIME = 100;
    Color[] colors;
    Simulation newcpuSim;
    
    


    public Sprite_Wire() {
		super();
	}

	public Sprite_Wire(Stage stage, Sprite_Node start, Sprite_Node end, Simulation ncs, String busType) {
        super(stage, new Point(0, 0));
        this.newcpuSim = ncs;
        this.start = start;
        start.addWire(this);
        this.end = end;
        end.addWire(this);
        setColor(busType);
        Point startPoint = start.location();
        Point endPoint = end.location();
        float distX = endPoint.x - startPoint.x;
        float distY = endPoint.y - startPoint.y;
        float dist = (float)Math.sqrt(distX * distX + distY * distY);
        count = (int)(dist / 15F);
        if (count == 0) {
            count = 1;
        }
        horizontal = distX != 0.0F;
        float x = startPoint.x;
        float y = startPoint.y;
        float diffX = distX / (float)count;
        float diffY = distY / (float)count;
        xList = new int[count + 1];
        yList = new int[count + 1];
        timeList = new long[count];
        int i;
        for (i = 0; i < count; i++) {
            xList[i] = (int)x;
            yList[i] = (int)y;
            timeList[i] = 0L;
            x += diffX;
            y += diffY;
        }

        xList[i] = (int)x;
        yList[i] = (int)y;
    }

    public Sprite_Wire(Stage stage, Sprite_Node start, Sprite_Node end, New_Sim newcpuSim) {
        this(stage, start, end, newcpuSim, "");
    }

    public void setColor(String busType) {
        Color color;
        switch(busType) {
            case "address" -> color = new Color(255, 0, 0);   //rgb(255, 0, 0)
            case "controlIn" -> color = new Color(230, 230, 0); //rgb(230, 230, 0)
            case "controlOut" -> color = new Color(255, 175, 0);//rgb(255, 175, 0)
            case "dataIn" -> color = new Color(31, 233, 255);   //rgb(31, 233, 255)
            case "dataOut" -> color = new Color(0, 0, 255);     //rgb(0, 0, 255)
            default -> color = Color.BLACK;                     //rgb(0, 0, 0)
        }
        colors = ColorTools.interpArrayHSB(Color.green, color, 10);
    }

    public void addStage(int theChannel) {
        super.addStage(theChannel);
        super.stage.addNode(start);
        super.stage.addNode(end);
    }

    public boolean inside(int x, int y) {
        return false;
    }

    public void mouseHandle(@SuppressWarnings("deprecation") Event event1) {
    }

    public boolean animateFrom(Sprite_Node goal) throws AbortedException {
    	Sprite_Node node = start;
        boolean wasStart = true;
        for (int i = 0; i < 2; i++) {
            if (node.animateFrom(goal)) {
                long nextTime = System.currentTimeMillis();
                for (int j = 0; j < count; j++) {
                    if (super.stage.aborting()) {
                        throw new AbortedException();
                    }
                    timeList[wasStart ? j : count - 1 - j] = nextTime;
                    markDirty();
                    //nextTime += 300L; //REGOLATORE VELOCITA ANIMAZIONE
                    nextTime += newcpuSim.getNextTime();
                    long waitTime = nextTime - System.currentTimeMillis();
                    if (waitTime > 0L) {
                        try {
                            Thread.sleep(waitTime);
                        } catch (InterruptedException _ex) {
                            //NO-OP
                        }
                    }
                }
                return true;
            }
            node = end;
            wasStart = false;
        }

        return false;
    }

    public boolean drawAll(Graphics g) {
        boolean wasDirty = super.dirty;
        markClean();
        g.setPaintMode();
        if(wasDirty) {
            int startX = xList[0];
            int startY = yList[0];
            System.currentTimeMillis();
            for(int i = 0; i < timeList.length; i++) {
                int endX = xList[i + 1];
                int endY = yList[i + 1];
                super.dirty |= ColorTools.setColor(g, colors, timeList[i]);
                if(horizontal) {
                    g.drawLine(startX, startY - 1, endX, endY - 1);
                    g.drawLine(startX, startY, endX, endY);
                    g.drawLine(startX, startY + 1, endX, endY + 1);
                } else {
                    g.drawLine(startX - 1, startY, endX - 1, endY);
                    g.drawLine(startX, startY, endX, endY);
                    g.drawLine(startX + 1, startY, endX + 1, endY);
                }
                startX = endX;
                startY = endY;
            }
        } else {
            g.setColor(colors[colors.length - 1]);
            int startX = xList[0];
            int startY = yList[0];
            int endX = xList[timeList.length - 1];
            int endY = yList[timeList.length - 1];
            if(horizontal) {
                g.drawLine(startX, startY - 1, endX, endY - 1);
                g.drawLine(startX, startY, endX, endY);
                g.drawLine(startX, startY + 1, endX, endY + 1);
            } else {
                g.drawLine(startX - 1, startY, endX - 1, endY);
                g.drawLine(startX, startY, endX, endY);
                g.drawLine(startX + 1, startY, endX + 1, endY);
            }
        }
        if(super.getDirtyPartner() != null)
            super.getDirtyPartner().drawAll(g);
        return super.dirty;
    }

    public synchronized void dispose() {
        if(start != null) {
            start = null;
            end = null;
        }
    }

    public void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    public String toString() {
        return getClass().getName() + "[start=" + start + ",end=" + end + "]";
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
		return horizontal;
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
