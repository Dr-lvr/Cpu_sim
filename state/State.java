package pippin.state;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Hashtable;

import pippin.controlBar.ButtonDialog;

public class State {

    Frame applet;
    Frame frame;
    String defaultName;
    String stateFileName;
    File stateFile;
    static boolean error = true;
    static final String HEADER = "This is a legal state file";
    private static Hashtable<String, Boolean> privTable = new Hashtable<>();

    public State(Frame applet, String defaultName) {
        this.applet = applet;
        this.defaultName = defaultName;
        try {
            Component component = applet;
            while (!(component instanceof Frame)) {
                component = component.getParent();
                assert component != null : "State.constructor.null component";
            }
            frame = (Frame)component;
            error = false;
        } catch(NullPointerException _ex) {
            System.err.println("Internal error in State.<init>: got null pointer while looking for frame");
        }
    }

    /*public Statable load()
    {
        if(error || stateFileName == null)
            return null;
        try
        {
            URL dataURL = new URL(applet.getDocumentBase(), stateFileName);

            return loadFromURL(dataURL, false);
        }
        catch(MalformedURLException _ex)
        {
            System.err.println("Bad stateFile parameter in applet tag: \"" + stateFileName + "\"");
        }
        error = true;
        return null;
    }*/

    @SuppressWarnings("deprecation")
	public Statable loadFromDialog() {
        Statable stateObj = null;
        error = false;
        try {
            FileDialog fd = new FileDialog(frame, "Pick a data file:", 0);
            if(stateFile != null)
                fd.setDirectory(stateFile.getParent());
            fd.show();
            if(fd.getFile() != null) {
                File dataFile = new File(fd.getDirectory(), fd.getFile());
                stateObj = loadFromFile(dataFile, true);
                if(stateObj != null)
                    stateFile = dataFile;
            }
        } catch(AWTError _ex) {
            ButtonDialog.showOKDialog(applet, null, "Error opening file dialog", "Error opening file dialog:\n(your browser can't do it)");
            error = true;
        } catch(SecurityException _ex) {
            ButtonDialog.showOKDialog(applet, null, "Error opening file dialog", "Error opening file dialog:\n(your browser won't allow it)");
            error = true;
        }
        return stateObj;
    }

    private Statable loadFromFile(File data, boolean errorDialogs) {
        Statable stateObj = null;
        try {
            // if(grantedPrivilege("UniversalFileAccess"))
            //     PrivilegeManager.enablePrivilege("UniversalFileAccess");
            FileInputStream fis = new FileInputStream(data);
            stateObj = loadFromStream(fis, errorDialogs);
            fis.close();
            //if(grantedPrivilege("UniversalFileAccess"))
            //   PrivilegeManager.revertPrivilege("UniversalFileAccess");
            if(stateObj != null)
                stateFile = data;
        } catch(FileNotFoundException _ex) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: file not found");
            else
                System.err.println("Error loading state: file not found");
        } catch(IOException e) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: couldn't read file");
            System.err.println("Error loading state: exception " + e);
            error = true;
        } catch(SecurityException _ex) {
            ButtonDialog.showOKDialog(applet, null, "Error opening file dialog", "Error opening file dialog:\n(your browser won't allow it)");
            error = true;
        }
        return stateObj;
    }

    @SuppressWarnings("unused")
	private Statable loadFromURL(URL data, boolean errorDialogs) {
        Statable stateObj = null;
        System.out.println("loadFromURL: data=" + data);
        try {
            //if(grantedPrivilege("UniversalFileAccess"))
            //    PrivilegeManager.enablePrivilege("UniversalFileAccess");
            InputStream is = data.openStream();
            stateObj = loadFromStream(is, errorDialogs);
            is.close();
            // if(grantedPrivilege("UniversalFileAccess"))
            //    PrivilegeManager.revertPrivilege("UniversalFileAccess");
            if(!error && data.getProtocol().equals("file"))
                stateFile = new File(data.getFile());
            else
                stateFile = null;
        } catch(FileNotFoundException _ex) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: file not found");
            else
                System.err.println("Error loading state: file not found");
        } catch(IOException e) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: couldn't read file");
            System.err.println("Error loading state: exception " + e);
            error = true;
        } catch(SecurityException e) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: couldn't read file\n(your browser won't allow it)");
            else
                System.err.println("Error loading state: exception " + e);
            error = true;
        }
        return stateObj;
    }

    private Statable loadFromStream(InputStream is, boolean errorDialogs) {
        Statable stateObj = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            if(dis.readUTF().equals("This is a legal state file"))
                stateObj = stateToObj(dis);
            else
                error = true;
            dis.close();
            bis.close();
            // if(grantedPrivilege("UniversalFileAccess"))
            //    PrivilegeManager.revertPrivilege("UniversalFileAccess");
            if(error) {
                stateObj = null;
                if(errorDialogs)
                    ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: bad file format");
                else
                    System.err.println("Internal error, State.loadFromURL: couldn't restore object");
            }
        } catch(FileNotFoundException _ex) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: file not found");
            else
                System.err.println("Error loading state: file not found");
        } catch(IOException e) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: couldn't read file");
            System.err.println("Error loading state: exception " + e);
            error = true;
        } catch(SecurityException e) {
            if(errorDialogs)
                ButtonDialog.showOKDialog(applet, null, "Error loading state", "Error loading state: couldn't read file\n(your browser won't allow it)");
            else
                System.err.println("Error loading state: exception " + e);
            error = true;
        }
        return stateObj;
    }

    public boolean canSave() {
        return !error && stateFile != null;
    }

    public void clearSave() {
        stateFile = null;
    }

    public void save(Statable stateObj) {
        if(canSave())
            saveToFile(stateObj, stateFile);
    }

    public void saveAs(Statable stateObj) {
        File newFile = saveToDialog(stateObj, stateFile, defaultName);
        if(newFile != null)
            stateFile = newFile;
    }

    public void saveAsOther(Statable stateObj, String fileName) {
        saveToDialog(stateObj, stateFile, fileName);
    }

    @SuppressWarnings("deprecation")
	private File saveToDialog(Statable stateObj, File oldFile, String fileName) {
        File newFile = null;
        error = false;
        try {
            FileDialog fd = new FileDialog(frame, "Save data as:", 1);
            if(!canSave())
                fd.setFile(fileName);
            else if(oldFile != null) {
                fd.setDirectory(oldFile.getParent());
                fd.setFile(oldFile.getName());
            }
            fd.show();
            if(fd.getFile() != null) {
                File dataFile = new File(fd.getDirectory(), fd.getFile());
                if(saveToFile(stateObj, dataFile))
                    newFile = dataFile;
            }
        } catch(AWTError _ex) {
            ButtonDialog.showOKDialog(applet, null, "Error opening file dialog", "Error opening file dialog:\n(your browser can't do it)");
            error = true;
        } catch(SecurityException _ex) {
            ButtonDialog.showOKDialog(applet, null, "Error opening file dialog", "Error opening file dialog:\n(your browser won't allow it)");
            error = true;
        }
        return newFile;
    }

    private boolean saveToFile(Statable stateObj, File dataFile) {
        try {
            // if(grantedPrivilege("UniversalFileAccess"))
            //     PrivilegeManager.enablePrivilege("UniversalFileAccess");
            FileOutputStream fos = new FileOutputStream(dataFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeUTF("This is a legal state file");
            objToState(stateObj, dos);
            dos.close();
            bos.close();
            fos.close();
            stateObj.markClean();
            // if(grantedPrivilege("UniversalFileAccess"))
            //     PrivilegeManager.revertPrivilege("UniversalFileAccess");
        } catch(IOException e) {
            ButtonDialog.showOKDialog(applet, null, "Error saving state", "Error writing state file:\ncouldn't write to file.");
            System.err.println("Error loading state: exception " + e);
            error = true;
        } catch(SecurityException _ex) {
            ButtonDialog.showOKDialog(applet, null, "Error saving state", "Error writing state file:\n(your browser won't allow it).");
            error = true;
        }
        return !error;
    }

    public void dispose() {
        applet = null;
        frame = null;
        stateFile = null;
    }

    public static void objToState(Statable obj, DataOutputStream dos) {
        try {
            dos.writeUTF(obj.getClass().getName());
            if(!obj.objIntoState(dos)) {
                error = true;
            }
        } catch(IOException _ex) {
            error = true;
        }
    }

    public static Statable stateToObj(DataInputStream dis) {
        try {
            String name = dis.readUTF();
            Class<?> theClass = Class.forName(name);
            @SuppressWarnings("deprecation")
			Statable obj = (Statable)theClass.newInstance();
            if(obj.stateIntoObj(dis)) {
                return obj;
            } else {
                error = true;
                return null;
            }
        } catch(IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodError _ex) {
            error = true;
        }
        return null;
    }

    public static void postError() {
        error = true;
    }

    @SuppressWarnings("unused")
	private String getSuffix(String string) {
        int pos = string.lastIndexOf('.');
        if(pos == -1)
            return null;
        else
            return string.substring(pos);
    }

    @SuppressWarnings("unused")
	private String swapSuffix(String string, String suffix) {
        int pos = string.lastIndexOf('.');
        if(pos == -1)
            return null;
        else
            return string.substring(0, pos) + suffix;
    }

    @SuppressWarnings("unused")
	private boolean grantedPrivilege(String target) {
        Boolean granted = privTable.get(target);
        if(granted == null) {
            try {
                //    PrivilegeManager.enablePrivilege(target);
                granted = true;
            } catch(NoClassDefFoundError | Exception _ex) {
                granted = false;
            }
            privTable.put(target, granted);
        }
        return granted;
    }

}
