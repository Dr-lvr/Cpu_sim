package pippin.stage;
import java.awt.*;
import java.util.Vector;

import pippin.components.cpuSprites.Sprite_Node;
import pippin.genericTypes.Sprite;
import pippin.genericTypes.SpriteType;

@SuppressWarnings("serial")
public class Stage extends FFCanvas implements Runnable {

    private final Vector<Vector<Sprite>> sprites;
    private final Vector<Sprite_Node> nodes;
    private final Color bgColor;
    public GlobalMode mode;
    int lastMode;
    boolean aborting;
    boolean running;
    boolean dirty;

    public Stage(Color bg, Dimension stageSize, GlobalMode mode) {
        sprites = new Vector<>();
        lastMode = -1;
        aborting = false;
        nodes = new Vector<>(30);
        running = true;
        dirty = false;
        this.mode = mode;
        setBackground(bg);
        bgColor = bg;
        setSize(stageSize);
        new Thread(this, "StageRepainter").start();
    }

    public void run() {
        while(running) {
            if(dirty)
                update(null);
            try {
                Thread.sleep(10L);
            } catch(InterruptedException _ex) {
                // NO-OP
            }
        }
    }

    public void startRepaint() {
        dirty = true;
    }

    public int getMode() {
        return mode.getInt();
    }

    public void setMode(int mode) {
        this.mode.setInt(mode);
    }

    public void aborting(boolean aborting) {
        this.aborting = aborting;
    }

    public boolean aborting() {
        return aborting;
    }

    public Dimension minimumSize() {
        return getSize();
    }

    public Dimension preferredSize() {
        return getSize();
    }

    public synchronized void addSprite(Sprite theSprite, int theChannel) {
        if(theSprite.stage == null) {
            System.out.println("Stage.addSprite with sprite.stage == null!");
        }
        if(sprites.size() <= theChannel) {
            sprites.setSize(theChannel + 1);
        }
        Vector<Sprite> vector;
        if(sprites.elementAt(theChannel) == null) {
            sprites.setElementAt(vector = new Vector<>(), theChannel);
        } else {
            vector = sprites.elementAt(theChannel);
        }
        if(vector.contains(theSprite)) {
            System.out.println("Stage.addSprite: the sprite is already there!");
        }
        vector.addElement(theSprite);
        startRepaint();
    }
///////////////////////////////////////////////////////////////////////------> removes sprites (!!! riga 294 CPU)
    public void removeSprite(SpriteType newGenericSprite) {
        for(Vector<Sprite> v : sprites) {
            if(v != null && v.removeElement(newGenericSprite)) {
                markDirty();
                return;
            }
        }
        System.err.println("Stage.removeSprite: couldn't find sprite " + newGenericSprite);
    }

    public void removeSprites(int theChannel) {
        if(theChannel < sprites.size()) {
            sprites.setElementAt(null, theChannel);
            markDirty();
        }
    }

    public void removeAllSprites() {
        for(int theChannel = 0; theChannel < sprites.size(); theChannel++) {
            removeSprites(theChannel);
        }
    }
///////////////////////////////////////////////////////////////////////------> nodes
    public synchronized void addNode(Sprite_Node node) {
        nodes.addElement(node);
    }

    public Sprite_Node nodeByName(String name) {
        for(Sprite_Node node : nodes) {
            if(node.name().equals(name)) {
                return node;
            }
        }
        System.err.println("Node.getNode: couldn't find node \"" + name + "\"");
        for(Sprite_Node node : nodes) {
            System.err.println(" (item in list: " + node + ")");
        }
        return null;
    }
///////////////////////////////////////////////////////////////////////------> locate sprites
    public Sprite locateSprite(int x, int y) {
        for(Vector<Sprite> v : sprites) {
            if(v != null) {
                for(Sprite s : v) {
                    if(s.inside(x, y)) {
                        return s;
                    }
                }
            }
        }
        return null;
    }
///////////////////////////////////////////////////////////////////////------> dirty
    public void dirtySprite(Sprite sprite) {
        startRepaint();
    }

    public void markDirty() {
        lastMode = -1;
        startRepaint();
    }
///////////////////////////////////////////////////////////////////////------> paint
    public void paintAll(Graphics g) {
        boolean redraw = false;
        System.currentTimeMillis();
        g.setColor(bgColor);
        g.setPaintMode();
        Dimension d = getSize();
        g.fillRect(0, 0, d.width, d.height);
        synchronized(this) {
            for(Vector<Sprite> v : sprites) {
                if (v != null) {
                    for (Sprite s : v) {
                        redraw |= s.drawAll(g);
                    }
                }
            }
        }
        if(!redraw) {
            dirty = false;
        }
    }

    public void paintNew(Graphics g) {
        boolean redraw = false;
        boolean newMode = getMode() != lastMode;
        lastMode = getMode();
        System.currentTimeMillis();
        synchronized(this) {
            for(Vector<Sprite> v : sprites) {
                if (v != null) {
                    for (Sprite s : v) {
                        if (newMode) {
                            redraw |= s.drawAll(g);
                        } else {
                            boolean thisRedraw = s.drawNew(g);
                            redraw |= thisRedraw;
                        }
                    }
                }
            }
        }
        if(!redraw) {
            dirty = false;
        }
    }

}
