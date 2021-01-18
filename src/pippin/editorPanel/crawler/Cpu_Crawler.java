package pippin.editorPanel.crawler;

import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

import pippin.components.cpuSprites.SpriteALU;
import pippin.components.cpuSprites.SpriteEditBox;
import pippin.components.cpuSprites.SpriteIR;
import pippin.components.cpuSprites.SpriteMUX;
import pippin.components.cpuSprites.SpriteRect;
import pippin.components.ramSprites.SpriteBox;
import pippin.components.ramSprites.SpriteIRAM;
import pippin.components.ramSprites.SpriteRAM;
import pippin.genericTypes.SpriteType;
import pippin.genericTypes.SpritesEnum;
import pippin.stage.Stage;
import pippin.system.mediators.Controller;
import pippin.test.Simulation;

@SuppressWarnings("serial")
public class Cpu_Crawler extends Panel {

	private static Cpu_Crawler theCpu_Crawler;
	
	private static SpriteRAM editRAM;
	private static SpriteEditBox editBox;
	private static Map<SpritesEnum, SpriteType> spritesStage = new HashMap<SpritesEnum, SpriteType>();

	// private constructor
	private Cpu_Crawler() {

		this.add(new Panel());
	}

	// singleton pattern at lazy time
	public static Cpu_Crawler getControllerInstance() {
		if (theCpu_Crawler == null) {
			synchronized (Controller.class) {
				if (theCpu_Crawler == null) {
					theCpu_Crawler = new Cpu_Crawler();
				}
			}
		}
		return theCpu_Crawler;
	}

	public static Map<SpritesEnum, SpriteType> mapSprites(Stage stage, Simulation ncs, Scrollbar scrollBar) {
		
		spritesStage.put(SpritesEnum.BOX_EDITOR, editBox);
		spritesStage.put(SpritesEnum.ABUS, new SpriteRect(stage, 1030, 30, 5, 259));
		spritesStage.put(SpritesEnum.RAM_CONTAINER, new SpriteRect(stage, 365, 3, ncs.getWidth() - 390, 292));
		spritesStage.put(SpritesEnum.INSTRUCTION_REGISTER, new SpriteIR(stage, 35, 30, 140, 20));
		spritesStage.put(SpritesEnum.MULTIPLEXER, new SpriteMUX(stage, 185, 100, 60, 20));
		spritesStage.put(SpritesEnum.ARITHMETIC_LOGIC_UNIT, new SpriteALU(stage, 90, 170, 160, 50));
		spritesStage.put(SpritesEnum.RAM_EDITOR, editRAM);
		spritesStage.put(SpritesEnum.ACCUMULATOR, new SpriteBox(stage, "ACC", 135, 250, 70, 20, 0));
		spritesStage.put(SpritesEnum.AUTO_INCREMENT, new SpriteBox(stage, "INC", 300, 100, 30, 20, "+2", "+10"));
		spritesStage.put(SpritesEnum.PROGRAM_COUNTER, new SpriteBox(stage, "PC", 280, 145, 70, 20, 0));
		spritesStage.put(SpritesEnum.DECODER, new SpriteBox(stage, "DEC", 35, 100, 70, 20, "Decoder", "Decoder"));
		spritesStage.put(SpritesEnum.INSTRUCTION_IN_RAM, new SpriteIRAM(stage,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 330, 30, scrollBar));
		spritesStage.put(SpritesEnum.EDITOR, new SpriteBox(stage, "EDIT", 10, 410, 470, 250, 0));
		spritesStage.put(SpritesEnum.SYSTEM_SERVICES, new SpriteBox(stage, "SYSTEM", 555, 525, 260, 110, 0));
		spritesStage.put(SpritesEnum.VIRTUAL_SSD, new SpriteBox(stage, "SSD", 1020, 500, 320, 180, 0));
		spritesStage.put(SpritesEnum.VIRTUAL_RAM, new SpriteBox(stage, "VRAM", 1020, 330, 330, 150, 0));
		return spritesStage;
	}
}
