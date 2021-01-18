package pippin.state;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import pippin.genericTypes.SpriteType;

public class CPUState implements Statable {

    public static SpriteType spriteIRAM;
    private boolean isDirty;

    public CPUState() {
        isDirty = false;
    }

    // Read the CPUState object from the data source and write it into the ram
    public boolean stateIntoObj(DataInputStream inputStream) {
        if (spriteIRAM == null) {
            return false;
        }

        Vector<Integer> state = new Vector<>();

        for (int i = spriteIRAM.firstAddress(); i <= spriteIRAM.lastAddress(); i = spriteIRAM.nextAddress(i)) {
            state.addElement(spriteIRAM.getValue(i));
        }

        try {
            for (int i = spriteIRAM.firstAddress(); i <= spriteIRAM.lastAddress(); i = spriteIRAM.nextAddress(i)) {
                int value = inputStream.readInt();
                if (spriteIRAM.checkValue(i, value) != null) {
                    throw new IOException();
                }
                spriteIRAM.setValue(i, value);
            }
            return true;
        } catch (IOException _ex) {
            //NO-OP
        }

        for (int i = spriteIRAM.firstAddress(), j = 0; i <= spriteIRAM.lastAddress(); i = spriteIRAM.nextAddress(i), j++) {
            spriteIRAM.setValue(i, state.elementAt(j));
        }
        return false;
    }

    // Write the CPUState object into the output stream
    public boolean objIntoState(DataOutputStream outputStream) {
        if (spriteIRAM == null) {
            return false;
        }

        try {
            for (int i = spriteIRAM.firstAddress(); i <= spriteIRAM.lastAddress(); i = spriteIRAM.nextAddress(i)) {
                outputStream.writeInt(spriteIRAM.getValue(i));
            }
            return true;
        }
        catch(IOException _ex) {
            return false;
        }
    }

    public void markDirty() {
        isDirty = true;
    }

    public void markClean() {
        isDirty = false;
    }

    public boolean isDirty() {
        return isDirty;
    }
}
