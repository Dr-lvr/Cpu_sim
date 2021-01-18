package pippin.editorPanel.crawler;

import pippin.components.cpuSprites.SpriteALU;
import pippin.components.cpuSprites.SpriteIR;
import pippin.components.cpuSprites.SpriteMUX;
import pippin.components.cpuSprites.SpriteRect;
import pippin.components.ramSprites.SpriteBox;
import pippin.components.ramSprites.SpriteIRAM;

public enum PlugPositions {
	
	ABUS(1030, 30, 5, 259, SpriteRect.class),
	
	INSTRUCTION_REGISTER(35, 30, 140, 20, SpriteIR.class), 
	ARITHMETIC_LOGIC_UNIT(90, 170, 160, 50,  SpriteALU.class),
	MULTIPLEXER(185, 100, 60, 20, SpriteMUX.class), 
	
	INSTRUCTION_IN_RAM(0,0,0,0, SpriteIRAM.class),
	
	DECODER(35, 100, 70, 20, SpriteBox.class), 
	EDITOR(10, 410, 470, 250, SpriteBox.class),
	ACCUMULATOR(135, 250, 70, 20, SpriteBox.class),
	SYSTEM_SERVICES(555, 525, 260, 110, SpriteBox.class), 
	VIRTUAL_SSD(1020, 500, 320, 180, SpriteBox.class),
	VIRTUAL_RAM(1020, 330, 330, 150, SpriteBox.class),
	AUTO_INCREMENT(300, 100, 30, 20, SpriteBox.class),
	PROGRAM_COUNTER(280, 145, 70, 20, SpriteBox.class);

	private int y;
	private int x;
	private int width;
	private int lenght;
	
	Class<?> a;
	
	PlugPositions(int x, int y, int width, int lenght, Class<?> aClass) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.lenght = lenght;
		this.a = aClass;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getWidth() {
		return width;
	}

	public int getLenght() {
		return lenght;
	}
}
