package pippin.stage;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class GlobalMode extends Observable {

    Object value;
    String name;

    public GlobalMode(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public GlobalMode(String name, int value) {
        this(name, Integer.valueOf(value));
    }

    public Object getObject() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setString(String value) {
        setObject(value);
    }

    public void setInt(int value) {
        if(!(this.value instanceof Integer) || value != (Integer) this.value) {
            this.value = value;
            setChanged();
            notifyObservers();
        }
    }

    public void setObject(Object value) {
        if(!value.equals(this.value)) {
            this.value = value;
            setChanged();
            notifyObservers();
        }
    }

    public String getString() {
        if(value instanceof String)
            return (String)value;
        else
            return null;
    }

    public int getInt() {
        if(value instanceof Integer)
            return (Integer) value;
        else
            return 0;
    }

}
