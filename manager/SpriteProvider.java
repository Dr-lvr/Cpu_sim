package pippin.manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.util.Map;

import pippin.New_Sim;
import pippin.components.cpuSprites.SpriteEditBox;
import pippin.components.cpuSprites.Sprite_Node;
import pippin.editorPanel.CPU_EditorPanel;
import pippin.exceptions.AbortedException;
import pippin.genericTypes.SpriteType;
import pippin.genericTypes.SpritesEnum;
import pippin.stage.GlobalMode;
import pippin.stage.Stage;
import pippin.system.mediators.Controller;
import pippin.system.panels.ServicePanel;
import pippin.system.panels.TextPanel;
import pippin.system.panels.SchedulerPanel;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// MANAGER BRANCH /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import pippin.test.Simulation;
import pippin.test.StoragePanel;

//IT IS A PANEL - THE PANEL, IT WIRES SEVERAL TYPES OF COMPONENTS IN A STAGE

@SuppressWarnings("serial")
public class SpriteProvider extends Panel {

	private Color bgColor;
	private Scrollbar scrollBar;
	private Stage stage;
	private GlobalMode mode;
	private Simulation ncs;
	private Frame frame;

	private int barWidth;

	private Map<SpritesEnum, SpriteType> component_Map;
	private ServicePanel services;
	private SchedulerPanel scheduler;
	private StoragePanel ssd;
	private TextPanel editor;
	private CPU_EditorPanel editorPanel;

	public SpriteProvider(Simulation test_Sim, Frame frame, CPU_EditorPanel editorPanel) {

		mode = new GlobalMode("mode", 1);
		bgColor = Color.lightGray;

		this.editorPanel = editorPanel;
		editorPanel.setBounds(705, 0, 200, 1000);
		editorPanel.setBackground(Color.gray);
		editorPanel.setVisible(true);
		this.add(editorPanel);
		
		this.buildStage(frame);

	}

	private void buildStage(Frame frame) {

		this.frame = frame;
		ncs = (Simulation) frame;
		setLayout(null);
		setBackground(bgColor);
		// setBounds(0, 0, 725, 300);
		setBounds(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()-530,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100);
		scrollBar = new Scrollbar(Scrollbar.VERTICAL);
		frame.add(scrollBar);
		barWidth = scrollBar.getPreferredSize().width;
		frame.remove(scrollBar);
		scrollBar = new Scrollbar(Scrollbar.VERTICAL);
		add(scrollBar);
		scrollBar.setBounds(getSize().width - 27, 30, barWidth, 258);
		this.stage = new Stage(bgColor, new Dimension(725, 400), mode); // 665 400
		// this.stage.reshape(0, 0, 725, 400); // 565 295
		this.stage.reshape(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 100); // 565 295
		
		Controller controller = Controller.getControllerInstance();

		editor = new TextPanel(controller);
		editor.setVisible(true);
		//editor.setBounds(20, 340, 315, 310);
		editor.setBounds(20, 420, 450, 220);
		editor.getArea().setSize(50, 50);
		this.add(editor);
		
		services = new ServicePanel(controller);
		services.setBounds(20, 303, 240, 90);
		services.setVisible(true);
		this.add(services);
		
		scheduler = new SchedulerPanel(controller);
		scheduler.setBounds(505, 415, 90, 240);
		scheduler.setVisible(true);
		this.add(scheduler);
		/*
		ssd = new StoragePanel(controller);
		ssd.setBounds(920, 505, 270, 120);
		ssd.setVisible(true);
		this.add(ssd);
		*/
		this.component_Map = StageFactory.mapSprites(stage, ncs, scrollBar);
		StageFactory.setSpritesOnStage(stage);
		StageFactory.wireUnit(stage, ncs, bgColor);
		this.add(stage);
		
		//the manage of layers is a stack so what I want to see in the foreground I have to put it first in line
	}

	public void flashWire(String start, String end) throws AbortedException {
		Sprite_Node startNode = stage.nodeByName(start);
		Sprite_Node endNode = stage.nodeByName(end);
		endNode.animateFrom(startNode);
	}
	
	public CPU_EditorPanel getEditorPanel() {
		return editorPanel;
	}

	public ServicePanel getServices() {
		return services;
	}

	public SchedulerPanel getSchedulerPanel() {
		return scheduler;
	}

	public StoragePanel getSsd() {
		return ssd;
	}

	public TextPanel getTextEditor() {
		return editor;
	}

	private SpriteType get(SpritesEnum sp) {
		return this.component_Map.get(sp);
	}

	public SpriteType getInstructionRam() {
		return get(SpritesEnum.INSTRUCTION_IN_RAM);
	}

	public SpriteType getRamEditor() {
		return get(SpritesEnum.RAM_EDITOR);
	}

	public SpriteType getBoxEditor() {
		return get(SpritesEnum.BOX_EDITOR);
	}

	public SpriteType getAccumulator() {
		return get(SpritesEnum.ACCUMULATOR);
	}

	public SpriteType getProgramCounter() {
		return get(SpritesEnum.PROGRAM_COUNTER);
	}

	public SpriteType getDecoder() {
		return get(SpritesEnum.DECODER);
	}

	public SpriteType getInstructionRegister() {
		return get(SpritesEnum.INSTRUCTION_REGISTER);
	}

	public SpriteType getArithmeticLogicUnit() {
		return get(SpritesEnum.ARITHMETIC_LOGIC_UNIT);
	}

	public SpriteType getAutoIncrement() {
		return get(SpritesEnum.AUTO_INCREMENT);
	}

	public SpriteType getMultiplexer() {
		return get(SpritesEnum.MULTIPLEXER);
	}

	public SpriteType getABus() {
		return get(SpritesEnum.ABUS);
	}

	public SpriteType getSprite_EDITOR() {
		return get(SpritesEnum.EDITOR);
	}

	public SpriteType getSprite_VRAM() {
		return get(SpritesEnum.VIRTUAL_RAM);
	}

	public SpriteType getSprite_SSD() {
		return get(SpritesEnum.VIRTUAL_SSD);
	}

	public SpriteType getSprite_SYSTEM() {
		return get(SpritesEnum.SYSTEM_SERVICES);
	}

	public void setEditRAM(SpriteType editRAM) {
		component_Map.put(SpritesEnum.RAM_EDITOR, editRAM);
	}

	public void setEditBox(SpriteEditBox editBox) {
		component_Map.put(SpritesEnum.BOX_EDITOR, editBox);
	}

	public void setACC(int value) {
		get(SpritesEnum.ACCUMULATOR).setValue(value);
	}

	public void setPC(int value) {
		get(SpritesEnum.PROGRAM_COUNTER).setValue(value);
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Scrollbar getScrollBar() {
		return scrollBar;
	}

	public void setScrollBar(Scrollbar scrollBar) {
		this.scrollBar = scrollBar;
	}

	public Stage getStage() {
		return this.stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public GlobalMode getMode() {
		return mode;
	}

	public void setMode(GlobalMode mode) {
		this.mode = mode;
	}

	public Simulation getNcs() {
		return ncs;
	}

	public void setNcs(New_Sim ncs) {
		this.ncs = ncs;
	}

	public Frame getFrame() {
		return frame;
	}

	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	public int getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(int barWidth) {
		this.barWidth = barWidth;
	}
}
