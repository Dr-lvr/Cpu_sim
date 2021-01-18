package pippin.state;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface Statable {

    boolean stateIntoObj(DataInputStream datainputstream);

    boolean objIntoState(DataOutputStream dataoutputstream);

    void markDirty();

    void markClean();

    boolean isDirty();
}
