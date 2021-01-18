package pippin.components.cpuSprites;
import java.awt.*;
import java.util.Vector;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//PHYSICAL NODE

import pippin.exceptions.AbortedException;

public class Sprite_Node {

    private Vector<Sprite_Wire> wires;
    private final String name;
    boolean propagating;
    int x;
    private int y;
    
    public Sprite_Node() {
    	
    	super();
		this.name = "";
	}

	public Sprite_Node(String name, int x, int y) {
        this.wires = new Vector<>(5);
        this.propagating = false;
        this.name = name;
        this.x = x;
        this.setY(y);
    }

    public Point location() {
        return new Point(x, getY());
    }

    public String name() {
        return name;
    }

    public void addWire(Sprite_Wire wire) {
        wires.addElement(wire);
    }

    public boolean animateFrom(Sprite_Node node) throws AbortedException {
        try {
            if (propagating) {
                return false;
            }
            if (node == this) {
                return true;
            }
            propagating = true;
            for (Sprite_Wire wire : wires) {
                if(wire.animateFrom(node)) {
                    propagating = false;
                    return true;
                }
            }
            propagating = false;
            return false;
        } catch(AbortedException e) {
            propagating = false;
            throw e;
        }
    }


    public synchronized void dispose() {
        if(wires != null) {
            @SuppressWarnings("unchecked")
			Vector<Sprite_Wire> tempWires = (Vector<Sprite_Wire>)wires.clone();
            for(int i = 0; i < tempWires.size(); i++) {
                tempWires.elementAt(i).dispose();
            }
            wires.removeAllElements();
            wires = null;
        }
    }


    @SuppressWarnings("deprecation")
	public void finalize() throws Throwable {
        dispose();
        super.finalize();
    }


    public String toString() {
        return getClass().getName() + "[name=" + name + ", wires.length=" + wires.size() + ", x=" + x + ", y=" + getY() + "]";
    }


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
}