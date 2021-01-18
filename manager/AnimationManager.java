package pippin.manager;

import java.awt.*;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// MANAGER BRANCH /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// IS THE SPRITE MANAGER, IS RUNNABLE, IS SPRITE PROVIDER
// MANAGE THE SYNC OF ANIMATION AND CONTROL BAR

// OVER - ENGENERED? USES A THREAD FOR MANAGE INTERNAL STEP (332) USED IN SYNCONIZED RUN() METHOD (112)

import pippin.components.cpuSprites.SpriteEditBox;
import pippin.components.ramSprites.SpriteIRAM;
import pippin.controlBar.ButtonDialog;
import pippin.editorPanel.CPU_EditorPanel;
import pippin.exceptions.AbortedException;
import pippin.genericTypes.SpriteType;
import pippin.test.Manager;
import pippin.test.Simulation;

@SuppressWarnings("serial")
public class AnimationManager extends SpriteProvider implements Runnable, Manager {

	boolean isReset;
	boolean isHalted;
	boolean gotFocus;
	boolean isStepping;
	boolean isRunning;
	int steps;
	int editAddress;

	Thread thread;

	public AnimationManager(Simulation test_Sim, Frame frame, CPU_EditorPanel editorPanel) {

		super(test_Sim, frame, editorPanel);

		this.isRunning = true;
		this.isReset = false;
		this.isHalted = false;
		this.gotFocus = false;
		this.isStepping = false;

		this.internalReset();

		this.thread = new Thread(this, "EventHandler");
		this.thread.start();
	}

	public synchronized void clear() {
		reset();
		for (int i = SpriteIRAM.FIRST_ADDRESS; i <= SpriteIRAM.LAST_ADDRESS; i += SpriteIRAM.WORD_LENGTH) {

			getInstructionRam().setValue(i, "NOP");
			getInstructionRam().setLabel(i, "");
		}
	}

	public synchronized void reset() {
		if (editWrite()) {
			steps = 0;
			isReset = true;
			while (isStepping) {
				getStage().aborting(true);
				try {
					wait();
				} catch (InterruptedException _ex) {
					// NO-OP
				}
			}
			getStage().aborting(false);
			notifyAll();
		}
	}

	public synchronized void stop() {
		if (editWrite()) {
			steps = 0;
			while (isStepping) {
				getStage().aborting(true);
				try {
					wait();
				} catch (InterruptedException _ex) {
					// NO-OP
				}
			}
			getStage().aborting(false);
			notifyAll();
		}
	}

	public synchronized void step() {
		isHalted = false;
		if (editWrite()) {
			steps++;
			notifyAll();
		}
	}

	public synchronized void play() {
		isHalted = false;
		if (editWrite()) {
			steps = 1000;
			notifyAll();
		}
	}

	public synchronized void changeMode(int mode) {
		editWrite();
		getMode().setInt(mode);
		if (getBoxEditor() != null) {
			getBoxEditor().setValue(getRamEditor().getStringValue(editAddress));
		}
		repaint();
	}

//////////////////////////////////////////////////////////// RUN
	@Override
	public void run() {
		boolean step;
		while (isRunning) {
			synchronized (this) {
				if (!isReset && steps == 0) {
					isStepping = false;
					notifyAll();
					try {
						wait();
					} catch (InterruptedException _ex) {
						// NO-OP
					}
					isStepping = true;
				}
				if (steps > 0) {
					step = true;
					steps--;
				} else {
					step = false;
				}
			}
			if (isReset) {
				steps = 0;
				internalReset();
				// write the system reset
				isReset = false;
			} else if (!internalDone()) {
				if (step) {
					step = false;
					try {
						internalStep();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				steps = 0;
			}
		}
	}

//////////////////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public boolean handleEvent(Event evt) {
		if (evt.id == Event.SCROLL_ABSOLUTE || evt.id == Event.SCROLL_LINE_DOWN || evt.id == Event.SCROLL_LINE_UP
				|| evt.id == Event.SCROLL_PAGE_DOWN || evt.id == Event.SCROLL_PAGE_UP) {
			getInstructionRam().fromScroll();
			if (getRamEditor() == getInstructionRam()) {
				Rectangle rect = getRamEditor().addressToRect(editAddress);
				rect.grow(-1, -1);
				getBoxEditor().reshape(rect);
			}
			return true;
		} else {
			return super.handleEvent(evt);
		}
	}

	@SuppressWarnings("deprecation")
	public boolean gotFocus(Event evt, Object what) {
		gotFocus = true;
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean lostFocus(Event evt, Object what) {
		gotFocus = false;
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean mouseDown(Event evt, int x, int y) {
		if (isStepping) {
			return false;
		}
		if (!gotFocus) {
			requestFocus();
		}
		boolean isEditingLabel = false;
		if (editWrite()) {
			if (getInstructionRam().inside(x, y)) {

				setEditRAM(getInstructionRam());
				isEditingLabel = getInstructionRam().isEditingLabel(x, y);

			} else if (getProgramCounter().inside(x, y)) {
				setEditRAM(getProgramCounter());

			} else if (getAccumulator().inside(x, y)) {
				setEditRAM(getAccumulator());

			} else {
				return true;
			}
			editAddress = getRamEditor().clickToAddress(x, y);
			if (editAddress == -1) {
				setEditRAM(null);
				return true;
			}
			Rectangle rect = isEditingLabel ? getInstructionRam().addressToLabelRect(editAddress)
					: getRamEditor().addressToRect(editAddress);
			rect.grow(-1, -1);
			String text = isEditingLabel ? getInstructionRam().getLabelStringValue(editAddress)
					: getRamEditor().getStringValue(editAddress);
			setEditBox(new SpriteEditBox(getStage(), rect.x, rect.y, rect.width, rect.height, text,
					getRamEditor() != getInstructionRam()));
			getBoxEditor().addStage(3);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean keyDown(Event evt, int key) {
		if (getRamEditor() == null) {
			return false;
		}
		if (key == 9 || key == 13 || key == 10 || key == 1004 || key == 1005) {
			if (getInstructionRam().isEditingLabel()) {
				return false;
			}
			int oldAddress = editAddress;
			SpriteType oldEditRAM = getRamEditor();
			if (!editWrite()) {
				return true;
			}
			setEditRAM(oldEditRAM);
			if (evt.shiftDown() && key == 1004) {
				editAddress = getRamEditor().nextAddress(oldAddress);
			} else if (evt.shiftDown() || key == 1004) {
				editAddress = getRamEditor().prevAddress(oldAddress);
			} else {
				editAddress = getRamEditor().nextAddress(oldAddress);
			}
			getRamEditor().showBooleanAddress(editAddress);
			Rectangle rect = getRamEditor().addressToRect(editAddress);
			rect.grow(-1, -1);
			String text = getRamEditor().getStringValue(editAddress);

			setEditBox(new SpriteEditBox(getStage(), rect.x, rect.y, rect.width, rect.height, text,
					getRamEditor() != getInstructionRam()));
			getBoxEditor().addStage(3);
			return true;
		}

		if (getRamEditor().showBooleanAddress(editAddress)) {
			Rectangle rect = getRamEditor().addressToRect(editAddress);
			rect.grow(-1, -1);
			getBoxEditor().reshape(rect);
		}
		getBoxEditor().addKey((char) key);
		if (getRamEditor() == getInstructionRam() && evt.controlDown() && evt.key == 19) { // s
			getInstructionRam().shiftFromAddress(editAddress);
			return true;
		}
		return true;
	}

	public boolean editWrite() {
		if (getRamEditor() == null) {
			return true;
		}
		String value = getBoxEditor().getValue1();
		if (value == null) {
			System.err.println("Internal error, CPU.editWrite: null value");
		}
		String error = getInstructionRam().isEditingLabel() ? getInstructionRam().checkLabel(editAddress, value)
				: getRamEditor().checkValue(editAddress, value);
		if (error != null) {
			String title;
			if (getRamEditor() == getInstructionRam()) {
				if (getInstructionRam().isEditingLabel()) {
					title = "Error writing label";
				} else {
					title = "Error writing instruction";
				}
			} else if (getRamEditor() == getAccumulator()) {
				title = "Error writing Program Counter";
			} else if (getRamEditor() == getAccumulator()) {
				title = "Error writing Accumulator";
			} else {
				title = "Error writing value";
			}
			String message = "Error setting the value to \"" + value + "\":\n" + error + ".";
			ButtonDialog.showOKDialog(this, title, message);
			if (getInstructionRam().isEditingLabel()) {
				getBoxEditor().setValue(getInstructionRam().getLabelStringValue(editAddress));
			} else {
				getBoxEditor().setValue(getRamEditor().getStringValue(editAddress));
			}
			return false;
		} else {
			getStage().removeSprite(getBoxEditor());
			getBoxEditor().dispose();
			if (getInstructionRam().isEditingLabel()) {
				getInstructionRam().setLabel(editAddress, value);
			} else {
				getRamEditor().setValue(editAddress, value);
			}
			setEditRAM(null);
			setEditBox(null);
			return true;
		}
	}

	public void repaint() {
		getStage().repaint();
		super.repaint();
	}

	public void updateStage() {
		getStage().update(null);
	}

//////////////////////////////////- INTERNAL STEP - /////////////////////////////////////////////////////////
	
	private int a;
	private void internalStep() throws Exception {
		boolean jumping = false; // if true PC isn't increased by 2
		boolean divideByZero = false;
		boolean overflow = false;
		boolean invalidOpcode = false;

		////////////////////////////////////// System management

		this.getSprite_EDITOR().flashBG();
		this.flashWire("EDI", "SYS");
		this.flashWire("SYS", "OTH");
		this.getSprite_SYSTEM().flashBG();
		this.flashWire("SYS2", "SYS3");
		this.getSprite_VRAM().flashBG();
		this.flashWire("SYS3", "VRAM");
		this.getServices().refresh();
		this.getSchedulerPanel().refresh();
		
		/*
		this.flashWire("SYS3", "VRAM");
		this.getSprite_SSD().flashBG();
		this.flashWire("VRAM3", "RAMSTACK1");
		delay(getNcs().delayValue());
		*/
		/*
		a=38;
		for(int i = 0; i<16; ++i) {
			
			this.flashWire(Integer.toString(a), Integer.toString(a)+"1");
			a+=16;
		}*/

		if (this.getSchedulerPanel().getControl()) {
			
			this.getInstructionRam().scheduleWork(this.getSchedulerPanel().getNextSchedule());
			this.setPC(0);
		} else {
			this.getSchedulerPanel().decLife();
		}
		//////////////////////////////////////////////////////////////////

		try {

			int opcode = CpuParser.top8Bits(getInstructionRam().getValue(getProgramCounter().getValue()));
			int operand = CpuParser.bottom8Bits(getInstructionRam().getValue(getProgramCounter().getValue()));
			boolean direct = opcode < 16;
			if (!direct) {
				opcode -= 16;
			}

			getInstructionRegister().clear();
			getArithmeticLogicUnit().clear();
			getMultiplexer().clear();
			getProgramCounter().flashBG();
			delay(getNcs().delayValue());

			if (getNcs().isAnimating()) {
				flashWire("PC:ADD", "IRAM:ADD");
			}
			getABus().flash();
			delay(getNcs().delayValue() / 2);
			a=38;
			for(int i = 0; i<16; ++i) {
				
				this.flashWire(Integer.toString(a), Integer.toString(a)+"1");
				a+=16;
			}
			delay(getNcs().delayValue() / 2);
			getInstructionRam().showBooleanAddress(getProgramCounter().getValue());
			getInstructionRam().flashAddress(getProgramCounter().getValue());
			delay(getNcs().delayValue());
			getInstructionRam().flashData(getProgramCounter().getValue());
			delay(getNcs().delayValue());

			if (getNcs().isAnimating()) {
				flashWire("IRAM:DAT", "IR:DAT");
			}
			getInstructionRegister().setValue(getInstructionRam().getValue(getProgramCounter().getValue()));
			getInstructionRegister().flashOperator();
			getInstructionRegister().flashOperand();
			delay(getNcs().delayValue());
			getInstructionRegister().flashOperator();

			switch (opcode) {
			case 0, 1, 2, 3, 4, 8, 10, 11 -> { // ADD, SUB, MUL, DIV, LOD, AND, CPZ, CPL
				char operation = CpuParser.opcodeToOperation(opcode);
				int argument = 0;
				int result = 0;
				if (direct) {
					argument = CpuParser.top8Bits(getInstructionRam().getValue(operand));
				} else {
					argument = CpuParser.intToSEx8(operand);
				}
				switch (operation) {
				case '+' -> result = getAccumulator().getValue() + argument;
				case '-' -> result = getAccumulator().getValue() - argument;
				case '*' -> result = getAccumulator().getValue() * argument;
				case '=' -> result = argument;
				case '&' -> result = getAccumulator().getValue() & argument;
				case ':' -> result = (argument == 0) ? 1 : 0;
				case '<' -> result = (argument < 0) ? 1 : 0;
				case '/' -> {
					if (argument == 0) {
						divideByZero = true;
					} else {
						result = getAccumulator().getValue() / argument;
					}
				}
				}
				if (result != CpuParser.intToSEx8(result)) {
					overflow = true;
				}
				result = CpuParser.intToSEx8(result);

				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("DEC:MUX", "MUX:DEC");
				}
				getMultiplexer().set(direct);
				getMultiplexer().flashBG();
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("DEC:ALU", "ALU:DEC");
				}
				getArithmeticLogicUnit().setOperation(operation);
				delay(getNcs().delayValue());
				if (operation != '=' && operation != ':' && operation != '<') {
					getAccumulator().flashBG();
					delay(getNcs().delayValue());
					if (getNcs().isAnimating()) {
						flashWire("ACC:DAT", "ALU:DAT");
					}
					getArithmeticLogicUnit().setLeftArg(getAccumulator().getValue());
					delay(getNcs().delayValue());
				}
				if (operation == ':' || operation == '<') {
					getArithmeticLogicUnit().setLeftArg(0);
					delay(getNcs().delayValue());
				}
				getInstructionRegister().flashOperand();
				delay(getNcs().delayValue());
				if (direct) {
					if (getNcs().isAnimating()) {
						flashWire("IR:ADD", "IRAM:ADD");
					}
					getABus().flash();
					delay(getNcs().delayValue() / 2);
					getInstructionRam().flashAddress(operand);
					delay(getNcs().delayValue());
					getInstructionRam().flashData(operand);
					delay(getNcs().delayValue());
					if (getNcs().isAnimating()) {
						flashWire("IRAM:DAT", "MUX:DAT");
					}
				} else {
					getInstructionRegister().flashOperand();
					if (getNcs().isAnimating()) {
						flashWire("IR:ADD", "MUX:ADD");
					}
				}
				getMultiplexer().flashLine();
				if (getNcs().isAnimating()) {
					flashWire("MUX:ALU", "ALU:MUX");
				}
				getArithmeticLogicUnit().setRightArg(argument);
				delay(getNcs().delayValue());
				getArithmeticLogicUnit().setOperation(operation);
				delay(getNcs().delayValue());
				if (divideByZero) {
					throw new AbortedException();
				} else {
					if (overflow) {
						throw new AbortedException();
					}
					if (getNcs().isAnimating()) {
						flashWire("ALU:ACC", "ACC:ALU");
					}
					setACC(result);
					getAccumulator().flashBG();
					delay(getNcs().delayValue());
				}
			}
			case 5 -> { // STO
				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("IR:ADD", "IRAM:ADD");
				}
				getABus().flash();
				delay(getNcs().delayValue() / 2);
				getInstructionRam().flashAddress(operand);
				delay(getNcs().delayValue());
				getAccumulator().flashBG();
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("ACC:DAT", "IRAM:DAT");
				}
				getInstructionRam().getShowAsVariable()[getInstructionRam().addressToIndex(operand)] = true;
				getInstructionRam().setValue(operand, CpuParser.joinBits(getAccumulator().getValue(), 0b00000000));
				getInstructionRam().flashData(operand);
				delay(getNcs().delayValue());
			}
			case 9 -> { // NOT
				char noperation = CpuParser.opcodeToOperation(opcode);
				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("DEC:ALU", "ALU:DEC");
				}
				getArithmeticLogicUnit().setOperation(noperation);
				delay(getNcs().delayValue());
				getAccumulator().flashBG();
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("ACC:DAT", "ALU:DAT");
				}
				getArithmeticLogicUnit().setLeftArg(getAccumulator().getValue());
				delay(getNcs().delayValue());
				getArithmeticLogicUnit().setOperation(noperation);
				delay(getNcs().delayValue());
				if (getNcs().isAnimating()) {
					flashWire("ALU:ACC", "ACC:ALU");
				}
				if (getAccumulator().getValue() == 0) {
					setACC(1);
				} else {
					setACC(0);
				}
				delay(getNcs().delayValue());
			}
			case 12 -> { // JMP
				jumping = true;
				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
				getInstructionRegister().flashOperand();
				if (getNcs().isAnimating()) {
					flashWire("IR:ADD", "PC:ADD");
				}
				setPC(operand);
				getProgramCounter().flashBG();
				delay(getNcs().delayValue());
			}
			case 13 -> { // JMZ
				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
				getAccumulator().flashBG();
				delay(getNcs().delayValue());
				if (getAccumulator().getValue() == 0) {
					jumping = true;
					getInstructionRegister().flashOperand();
					if (getNcs().isAnimating()) {
						flashWire("IR:ADD", "PC:ADD");
					}
					setPC(operand);
					getProgramCounter().flashBG();
					delay(getNcs().delayValue());
				}
			}
			case 14 -> { // NOP
				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
			}
			case 15 -> { // HLT
				if (getNcs().isAnimating()) {
					flashWire("IR:DEC", "DEC:IR");
				}
				getDecoder().flashBG();
				delay(getNcs().delayValue());
				isHalted = true;
			}
			default -> {
				invalidOpcode = true;
				throw new AbortedException();
			}
			}
			if (getProgramCounter().getValue() < 126) {
				if (!jumping) {
					if (getNcs().isAnimating()) {
						flashWire("PC:ADD", "INC:ADD");
					}
					getAutoIncrement().flashBG();
					delay(getNcs().delayValue());
					if (getNcs().isAnimating()) {
						flashWire("INC:PC", "PC:INC");
					}
					setPC(getProgramCounter().getValue() + 2);
					getProgramCounter().flashBG();
					delay(getNcs().delayValue());
				}
			} else {
				isHalted = true;
			}
		} catch (AbortedException _ex) {
			isHalted = true;
			String title;
			String message;
			if (divideByZero) {
				title = "Divide By Zero";
				message = "Error: ALU tried to divide by zero";
			} else if (overflow) {
				title = "Arithmetic Overflow";
				message = "Error: ALU overflowed";
			} else if (invalidOpcode) {
				title = "Invalid Opcode";
				message = "Error: invalid opcode found inside IR";
			} else {
				return;
			}
			ButtonDialog.showOKDialog(this, title, message);
		}
	}

	private void internalReset() {
		isHalted = false;
		setPC(0);
		setACC(0);
		getArithmeticLogicUnit().clear();
		getInstructionRegister().clear();
		getStage().update(null);
		getProgramCounter().flashBG();
		getAccumulator().flashBG();
	}

	private boolean internalDone() {
		return getProgramCounter().getValue() == 128 || isHalted;
	}

	private void delay(int ms) throws AbortedException {
		if (getStage().aborting()) {
			throw new AbortedException();
		}
		try {
			Thread.sleep(ms);
		} catch (InterruptedException _ex) {
			// NO-OP
		}
	}
}
