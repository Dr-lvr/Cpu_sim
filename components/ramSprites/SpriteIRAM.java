package pippin.components.ramSprites;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import pippin.components.utility.ColorTools;
import pippin.components.utility.TextBox;
import pippin.manager.CpuParser;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteIRAM extends SpriteRAM {

	static final int WIDTH = 295;
	static final int HEIGHT = 258;
	public static final int FIRST_ADDRESS = 0;
	public static final int LAST_ADDRESS = 126;
	public static final int WORD_LENGTH = 2;
	int scroll;
	private boolean isEditingLabel; // used outside the class to understand if the edited TextBox is a datBox or a
									// labelBox
	Scrollbar scrollBar;
	private boolean[] showAsVariable; // determines if the value inside a cell should be displayed as a number or as
										// an instruction
	TextBox[] labelBoxes;
	Color[] addColors;
	Map<String, Integer> labelsMapping;

	Map<Integer, String> actualJob;

	
	public SpriteIRAM() {
		super();
	}

	public SpriteIRAM(Stage stage, int x, int y, Scrollbar scrollBar) {
		super(stage, "IRAM", x, y, WIDTH, HEIGHT);

		addColors = ColorTools.interpArrayRGB(new Color(0, 255, 0), new Color(255, 204, 204), 10);

		this.scrollBar = scrollBar;
		super.data = new int[64];
		super.addBoxes = new TextBox[64];
		super.datBoxes = new TextBox[64];
		labelBoxes = new TextBox[64];
		setShowAsVariable(new boolean[64]);
		labelsMapping = new HashMap<>();
		setEditingLabel(false);

		addNode("DAT", WIDTH - (int) ((float) WIDTH * 0.3333333F), 0);
		addNode("ADD", -5, 50);

		for (int i = 0; i < 64; i++) {
			super.addBoxes[i] = new TextBox(stage, 0, 0, 1, 1, Integer.toString(i * 2, 10),
					CpuParser.intToBinString(i * 2, 8), super.font, true, true);
			super.addBoxes[i].setBGColors(addColors);
			super.datBoxes[i] = new TextBox(stage, 0, 0, 1, 1, "", "", super.font, true, false);
			labelBoxes[i] = new TextBox(stage, 0, 0, 1, 1, "", "", super.font, true, true, Color.lightGray);
			setValue(indexToAddress(i), "NOP");
		}
		
		//TODO:make data processing homogeneous
		actualJob = new HashMap<Integer, String>();
		
		updateScroll();
	}

	//TODO:make data processing homogeneous
	public void scheduleWork(Vector<String> process) {

		for (int i = 0; i < this.lastAddress(); i += 2) {
			this.setValue(i, "NOP");
		}

		for (int i = 0; i < process.size(); i += 2) {
			this.setValue(i, process.get(i));
			this.actualJob.put(i, process.get(i));
		}
	}

	//TODO:make data processing homogeneous
	public Map<Integer, String> getActualJob() {
		
		//returning data when schedule in not FIFO way
		Map<Integer, String> tmp = actualJob;
		actualJob = new HashMap<Integer, String>();
		return tmp;
	}
	
	

	@Override
	public String getStringValue(int address) {
		int index = addressToIndex(address);
		if (super.stage.getMode() == 1) {
			return datBoxes[index].getSymText();
		} else {
			return datBoxes[index].getBinText();
		}
	}

	@Override
	public String checkValue(int address, String value) {
		if (super.stage.getMode() == 1) {
			if (value.matches("\\d+")) { // if è una variabile
				int dataValue = Integer.parseInt(value);
				if (dataValue > 127 || dataValue < -128) {
					return "Data exceed 8-bit range (127/-128)";
				}
			} else {
				String operand = CpuParser.secondPart(value);
				if (labelsMapping.containsKey(operand)) {
					value = CpuParser.firstPart(value) + " " + labelsMapping.get(operand);
				}
				CpuParser.instructionToInt16(value);
			}
		} else {
			String stripValue = stripWhite(value);
			int iValue;
			try {
				iValue = Integer.valueOf(stripValue, 2);
			} catch (NumberFormatException _ex) {
				return "not a legal binary number";
			}
			CpuParser.int16ToInstruction(iValue);
		}
		return CpuParser.error();
	}

	@Override
	public String checkValue(int address, int value) {
		String error = CpuParser.int16ToInstruction(value);
		if (error != null) {
			return null;
		} else {
			return CpuParser.error();
		}
	}

	@Override
	public void setValue(int address, String value) {
		if (super.stage.getMode() == 1) {
			if (value.matches("\\d{1,3}")) { // if it's a variable
				getShowAsVariable()[addressToIndex(address)] = true;
				setValue(address, CpuParser.joinBits(Integer.parseInt(value), 0b00000000));
			} else {
				getShowAsVariable()[addressToIndex(address)] = false;
				if (labelsMapping.containsKey(CpuParser.secondPart(value))) {
					int index = addressToIndex(address);
					String valueWithAddress = CpuParser.firstPart(value) + " "
							+ labelsMapping.get(CpuParser.secondPart(value));
					int int16Value = CpuParser.instructionToInt16(valueWithAddress);
					String binString = int16ToBin(int16Value);
					super.data[index] = int16Value;
					super.datBoxes[index].setText(value, binString);
					markDirty();
				} else {
					setValue(address, CpuParser.instructionToInt16(value));
				}
			}
		} else {
			value = stripWhite(value);
			int iValue;
			try {
				iValue = Integer.valueOf(value, 2);
			} catch (NumberFormatException _ex) {
				return;
			}
			setValue(address, iValue);
		}
	}

	@Override
	public void setValue(int address, int value) {
		String symString;
		String binString;
		int index = addressToIndex(address);
		super.data[index] = value;
		if (getShowAsVariable()[index]) {
			symString = Integer.toString(CpuParser.top8Bits(value));
		} else {
			symString = CpuParser.int16ToInstruction(value);
		}
		binString = int16ToBin(super.data[index]);
		assert symString != null;
		super.datBoxes[index].setText(symString, binString);
		markDirty();
	}

	@Override
	public int firstAddress() {
		return 0;
	}

	@Override
	public int lastAddress() {
		return 126;
	}

	@Override
	public int nextAddress(int address) {
		if (address < lastAddress()) {
			return address + 2;
		} else {
			return firstAddress();
		}
	}

	@Override
	public int prevAddress(int address) {
		if (address > firstAddress()) {
			return address - 2;
		} else {
			return lastAddress();
		}
	}

	@Override
	public void flashAddress(int address) {
		showBooleanAddress(address);
		super.flashAddress(address);
	}

	@Override
	public void flashData(int address) {
		showBooleanAddress(address);
		super.flashData(address);
	}

	public void updateScroll() {
		int addressWidth = 66;
		int dataWidth = 132;
		int labelWidth = 95;
		int labelAddBorder = super.getX() + labelWidth;
		int addDatBorder = labelAddBorder + addressWidth;
		int top = super.getY();
		int left = super.getX();

		for (int i = 0; i < 64; i++) {
			if (i >= scroll && i < scroll + 16) {
				int bottom = super.getY() + (((i + 1) - scroll) * super.getHeight()) / 16;
				labelBoxes[i].reshape(left, top, labelWidth, 17);
				super.addBoxes[i].reshape(labelAddBorder, top, addressWidth, 17);
				super.datBoxes[i].reshape(addDatBorder, top, dataWidth, 17);
				top = bottom - 1;
			} else {
				labelBoxes[i].reshape(-10, -10, 5, 5);
				super.addBoxes[i].reshape(-10, -10, 5, 5);
				super.datBoxes[i].reshape(-10, -10, 5, 5);
			}
		}

		toScroll();
		markDirty();
	}

	@Override
	public boolean showBooleanAddress(int address) {
		int index = addressToIndex(address);
		if (index < scroll) {
			scroll = index;
			updateScroll();
			return true;
		}
		if (index >= scroll + 16) {
			scroll = (index - 16) + 1;
			updateScroll();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int clickToAddress(int x, int y) {
		for (int i = 0; i < 64; i++) {
			if (datBoxes[i].isInside(x, y) || labelBoxes[i].isInside(x, y)) {
				return indexToAddress(i);
			}
		}
		return -1;
	}

	public boolean isEditingLabel(int x, int y) {
		for (int i = 0; i < 64; i++) {
			if (labelBoxes[i].isInside(x, y)) {
				isEditingLabel = true;
				return true;
			}
		}
		isEditingLabel = false;
		return false;
	}

	public String checkLabel(int address, String value) {
		if (value.length() > 9) {
			return "invalid label length (max 9 characters)";
		}
		if (value.matches("(^@@$|^#?[1-9]+$)")) {
			return "the label contains an invalid character";
		}
		if (labelsMapping.containsKey(value) && labelsMapping.get(value) != address) {
			return "this label is already in use";
		}
		return null;
	}

	public Rectangle addressToLabelRect(int address) {
		int index = addressToIndex(address);
		return labelBoxes[index].getRect();
	}

	public String getLabelStringValue(int address) {
		int index = addressToIndex(address);
		return labelBoxes[index].getSymText();
	}

	public void setLabel(int address, String value) {
		int index = addressToIndex(address);
		String previousValue = labelBoxes[index].getSymText();
		System.out.println("previous value \"" + previousValue + "\"");
		if (value.isEmpty() || value.isBlank()) {
			labelsMapping.remove(previousValue);
			System.out.println("removed label \"" + previousValue + "\"");
		} else {
			if (!previousValue.equals("")) {
				labelsMapping.remove(previousValue);
			}
			labelsMapping.put(value, address);
			System.out.println("changed/added mapping: " + address + " \"" + value + "\"");
		}
		labelBoxes[index].setText(value, value);
		markDirty();
		System.out.println("all mappings: " + labelsMapping.toString());
	}

	public void fromScroll() {
		if (scrollBar != null) {
			int newScroll = scrollBar.getValue();
			if (newScroll != scroll) {
				scroll = newScroll;
				updateScroll();
			}
		}
	}

	public void toScroll() {
		if (scrollBar != null) {
			scrollBar.setValues(scroll, 16, 0, 56);
		}
	}

	// called when the shortcut ctrl+s is entered while editing an instruction
	// inside the ram
	public void shiftFromAddress(int addressFrom) {
		if (addressFrom < firstAddress() || addressFrom > lastAddress()) {
			return;
		}
		System.out.println("shifting from address " + addressFrom);
		for (int i = LAST_ADDRESS - 2; i >= addressFrom; i -= WORD_LENGTH) {
			int index = addressToIndex(i);
			getShowAsVariable()[index + 1] = getShowAsVariable()[index];
			data[index + 1] = data[index];
			datBoxes[index + 1].setText(datBoxes[index].getSymText(), datBoxes[index].getBinText());
			String tmpLabel = labelBoxes[index].getSymText();
			setLabel(i, "");
			setLabel(i + 2, tmpLabel);
		}
		setValue(addressFrom, "NOP");
		setLabel(addressFrom, "");
	}

	@Override
	public boolean drawAll(Graphics g) {
		markClean();
		for (int i = scroll; i < scroll + 16; i++) {
			super.dirty |= labelBoxes[i].drawAll(g);
			super.dirty |= super.addBoxes[i].drawAll(g);
			super.dirty |= super.datBoxes[i].drawAll(g);
		}

		if (super.getDirtyPartner() != null) {
			super.getDirtyPartner().markDirty();
		}
		return super.dirty;
	}

	@Override
	public int addressToIndex(int address) {
		return address / 2;
	}

	@Override
	protected int indexToAddress(int index) {
		return index * 2;
	}

	private String int16ToBin(int value) {
		int top8 = CpuParser.top8Bits(value);
		int bottom8 = CpuParser.bottom8Bits(value);
		return CpuParser.intToBinString(top8, 8) + "  " + CpuParser.intToBinString(bottom8, 8);
	}

	// removes all white spaces from a string
	private String stripWhite(String value) {
		return value.replaceAll("\\s", "");
	}

	public boolean isEditingLabel() {
		return isEditingLabel;
	}

	public void setEditingLabel(boolean isEditingLabel) {
		this.isEditingLabel = isEditingLabel;
	}

	public boolean[] getShowAsVariable() {
		return showAsVariable;
	}

	public void setShowAsVariable(boolean[] showAsVariable) {
		this.showAsVariable = showAsVariable;
	}

	public void setActualJob(Map<Integer, String> actualJob) {
		this.actualJob = actualJob;
	}
}
