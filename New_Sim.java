package pippin;
import javax.swing.*;

import pippin.controlBar.ControlBar;
import pippin.editorPanel.CPU_EditorPanel;
import pippin.manager.AnimationManager;
import pippin.state.CPUState;
import pippin.state.State;
import pippin.test.Simulation;

import java.awt.*;
import java.io.IOException;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// THE FRAME /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// IS A FRAME - THE FRAME, USE -> SPRITE MANAGER, CONTROLBAR. HAS A STATE

@SuppressWarnings("serial")
public class New_Sim extends JFrame implements Simulation{

	private final CPU_EditorPanel editorPanel;
	private final ControlBar controlBar;
	private final AnimationManager cpu;
	private final State state;
	
	public New_Sim() throws IOException {
		
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(102, 102, 102));
		setMinimumSize(new Dimension(0, 0));
		setLayout(new FlowLayout());
		
		editorPanel = CPU_EditorPanel.getEditorInstanceP();
		
		cpu = new AnimationManager(this, this, this.editorPanel);
		add(cpu);
		
		state = new State(this, "CPUSim.dat");
		
		CPUState.spriteIRAM = cpu.getInstructionRam();
		
		controlBar = new ControlBar(this);
		add(controlBar);
	}

	public boolean isAnimating() {
		return controlBar.isAnimating();
	}


	public int delayValue() {
		return 500 - (300 - controlBar.getNextTime());
	}


	public long getNextTime() {
		return controlBar.getNextTime();
	}


	public AnimationManager getCpu() {
		return cpu;
	}
	
	// it's not called getState because getState is already a superclass method
	public State getProgramState() {
		return state;
	}

}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// THE FRAME - END /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
