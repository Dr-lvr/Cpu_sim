package pippin.genericTypes;
import java.awt.*;

import pippin.stage.Stage;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// ABSTRACT CLASS - RAPRESENTS A SPRITE, 

//(NEW!) IS A PANEL SO EVERY SPRITE IS NOW A PANEL ALSO
//(NEW!) CAN HANDLE EVERY SPRITE LIKE A SPRITE TYPE

@SuppressWarnings("serial")
public abstract class Sprite extends Panel implements SpriteType{

    public Stage stage;
    private int x;
    private int y;
    private int width;
    private int height;
    protected boolean dirty;
    private Sprite dirtyPartner;

    public Sprite() {
		super();
	}

	public Sprite(Stage stage, Point loc) {
        dirty = true;
        this.stage = stage;
        setX(loc.x);
        setY(loc.y);
    }

    public Sprite(Stage stage, int x, int y, int width, int height) {
        dirty = true;
        this.stage = stage;
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
    }

    public void setDirtyPartner(Sprite dirtyPartner) {
        this.dirtyPartner = dirtyPartner;
    }

    public void addStage(int theChannel) {
        stage.addSprite(this, theChannel);
    }

    public synchronized boolean inside(int x, int y) {
        return x >= this.getX() && y >= this.getY() && x < this.getX() + getWidth() && y < this.getY() + getHeight();
    }

    public void move(int x, int y) {
        this.setX(x);
        this.setY(y);
        markDirty();
    }

    public void move(Point p) {
        move(p.x, p.y);
    }

    public void reshape(int x, int y, int width, int height) {
        this.setX(x);
        this.setY(y);
        this.setWidth(width);
        this.setHeight(height);
        markDirty();
    }

    public void reshape(Rectangle rect) {
        reshape(rect.x, rect.y, rect.width, rect.height);
    }

    public /*synchronized*/ abstract boolean drawAll(Graphics g);

    public synchronized boolean drawNew(Graphics g) {
        if (dirty) {
            return drawAll(g);
        } else {
            return false;
        }
    }

    public final void markDirty() {
        if (stage == null) {
            System.out.println("Sprite.markDirty: pippin.stage is null with sprite " + this);
        } else {
            stage.dirtySprite(this);
        }
        dirty = true;
    }

    public final boolean isDirty() {
        return dirty;
    }

    public final void markClean() {
        dirty = false;
    }

    public abstract String toString();

    public void dispose() {
    }

    @SuppressWarnings("deprecation")
	public void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Sprite getDirtyPartner() {
		return dirtyPartner;
	}
}
