package pippin.exceptions;
@SuppressWarnings("serial")
public class AbortedException extends Exception {

    public AbortedException() {}

    public AbortedException(String s) {
        super(s);
    }
}
