package pippin.controlBar;

import java.awt.*;
import java.util.Hashtable;

import javax.swing.*;

import pippin.manager.AnimationManager;
import pippin.state.CPUState;
import pippin.state.State;
import pippin.test.Simulation;

@SuppressWarnings("serial")
public class ControlBar extends Panel {

	private final ImageButton resetButton;
	private final ImageButton stopButton;
	private final ImageButton stepButton;
	private final ImageButton playButton;
	private final JSlider slider;
	private final ImageButton symbolicBinaryButton;
	private final ImageButton animateButton;
	private final ImageButton clearAllButton;
	private final ImageButton openButton;
	private final ImageButton saveButton;
	private final ImageButton saveAsButton;
	private final Simulation ncs;

	private int nextTime;
	private boolean animate;

	public ControlBar(Simulation test_Sim){
		this.ncs = test_Sim;
		
        setBounds(10,670,(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 60);
        
		ImageLoader imageLoader = new ImageLoader();

		setLayout(new FlowLayout());
		setBackground(new Color(100, 100, 100));

		resetButton = new ImageButton("reset", imageLoader.getImage("NEWrrOn"), imageLoader.getImage("NEWrr"), null);
		resetButton.reshape(10, 670, 50, 50);
		add(resetButton);

		stopButton = new ImageButton("stop", imageLoader.getImage("NEWstopOn"), imageLoader.getImage("NEWstop"), null);
		stopButton.reshape(60, 670, 50, 50);
		add(stopButton);

		stepButton = new ImageButton("step", imageLoader.getImage("NEWffOn"), imageLoader.getImage("NEWff"), null);
		stepButton.reshape(110, 670, 50, 50);
		add(stepButton);

		playButton = new ImageButton("play", imageLoader.getImage("NEWplayOn"), imageLoader.getImage("NEWplay"), null);
		playButton.reshape(160, 670, 50, 50);
		add(playButton);

		slider = new JSlider(JSlider.HORIZONTAL, 10, 300, 155);
		slider.setPreferredSize(new Dimension(80, 50));
		slider.setBackground(new Color(100, 100, 100));
		slider.addChangeListener(event -> nextTime = 150 - slider.getValue() );
		Hashtable<Integer, JLabel> labels = new Hashtable<>();
		JLabel slow = new JLabel("Slow");
		slow.setForeground(Color.white);
		JLabel fast = new JLabel("Fast");
		fast.setForeground(Color.white);
		labels.put(10, slow);
		labels.put(600, fast);
		slider.setLabelTable(labels);
		slider.setPaintLabels(true);
		add(slider);

		nextTime = 150;

		JPanel jPanel2 = new JPanel();
		jPanel2.setLayout(new GridLayout(2, 1));

		symbolicBinaryButton = new ImageButton("symbolic", "binary", imageLoader.getImage("binary"),
				imageLoader.getImage("symbolic"), null);
		symbolicBinaryButton.reshape(210, 670, 80, 25);
		add(symbolicBinaryButton);

		animateButton = new ImageButton("animateOn", "animateOff", imageLoader.getImage("animateOff"),
				imageLoader.getImage("animateOn"), null);
		animateButton.reshape(210, 695, 80, 25);
		add(animateButton);
		animate = true;

		add(jPanel2);

		clearAllButton = new ImageButton("clearAll", imageLoader.getImage("clearAllDown"), imageLoader.getImage("clearAll"),
				null);
		clearAllButton.reshape(290, 670, 80, 50);
		add(clearAllButton);

		openButton = new ImageButton("open", imageLoader.getImage("openDown"), imageLoader.getImage("open"), null);
		openButton.reshape(370, 670, 60, 50);
		add(openButton);

		saveButton = new ImageButton("save", imageLoader.getImage("saveDown"), imageLoader.getImage("save"),
				imageLoader.getImage("saveDisabled"));
		saveButton.reshape(430, 670, 60, 50);
		saveButton.enable(test_Sim.getProgramState().canSave());
		add(saveButton);

		saveAsButton = new ImageButton("saveAs", imageLoader.getImage("saveAsDown"), imageLoader.getImage("saveAs"), null);
		saveAsButton.reshape(490, 670, 80, 50);
		add(saveAsButton);

		//setVisible(true);
		
		//setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50);
		//setBounds(0, 300, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 50);
	}
	
	public boolean isAnimating() {
		return animate;
	}

	public int getNextTime() {
		return nextTime;
	}

	@SuppressWarnings("deprecation")
	public boolean action(Event evt, Object what) {
		AnimationManager cpu = ncs.getCpu();
		State state = ncs.getProgramState();

		if (evt.target == resetButton) {
			cpu.reset();
			return true;
		} else if (evt.target == stopButton) {
			cpu.stop();
			return true;
		} else if (evt.target == stepButton) {
			cpu.step();
			return true;
		} else if (evt.target == playButton) {
			cpu.play();
			return true;
		} else if (evt.target == symbolicBinaryButton) {
			cpu.changeMode(symbolicBinaryButton.getState() ? 2 : 1);
			return true;
		} else if (evt.target == animateButton) {
			animate = !animate;
			return true;
		} else if (evt.target == clearAllButton) {
			cpu.clear();
			state.clearSave();
			return true;
		} else if (evt.target == openButton) {
			cpu.reset();
			if (cpu.editWrite()) {
				CPUState.spriteIRAM = cpu.getInstructionRam();
				state.loadFromDialog();
				cpu.updateStage();
			}
			saveButton.enable(state.canSave());
			return true;
		} else if (evt.target == saveButton) {
			cpu.reset();
			if (cpu.editWrite()) {
				CPUState.spriteIRAM = cpu.getInstructionRam();
				state.save(new CPUState());
				cpu.updateStage();
			}
			saveButton.enable(state.canSave());
			return true;
		} else if (evt.target == saveAsButton) {
			cpu.reset();
			if (cpu.editWrite()) {
				CPUState.spriteIRAM = cpu.getInstructionRam();
				state.saveAs(new CPUState());
				cpu.updateStage();
			}
			saveButton.enable(state.canSave());
			return true;
		} else {
			return false;
		}

	}

}
