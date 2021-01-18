package pippin.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.util.HashMap;
import java.util.Map;

import pippin.components.cpuSprites.SpriteALU;
import pippin.components.cpuSprites.SpriteEditBox;
import pippin.components.cpuSprites.SpriteIR;
import pippin.components.cpuSprites.SpriteLabel;
import pippin.components.cpuSprites.SpriteMUX;
import pippin.components.cpuSprites.SpriteRect;
import pippin.components.cpuSprites.Sprite_Node;
import pippin.components.cpuSprites.Sprite_Wire;
import pippin.components.ramSprites.SpriteBox;
import pippin.components.ramSprites.SpriteIRAM;
import pippin.components.ramSprites.SpriteRAM;
import pippin.genericTypes.SpriteType;
import pippin.genericTypes.SpritesEnum;
import pippin.stage.Stage;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// MANAGER BRANCH /////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
import pippin.test.Simulation;

//PHYSICALLY WIRE THE COMPOSITE CPU STAGE MADE BY SPRITES OF SEVERAL TYPES MENAGED BY A GENERIC TYPE SPRITE
// IT HAS A MAP OF SPRITES

// IT WIRES (ALSO GRAPHICALLY) VARIOUS TYPES OF SPRITES THROUGH GENERIC NODES (COMPONENTS OF SPRITE) AND WIRES
// (SEE SPRITE NODES, SPRITE WIRE, SPRITE COMPONENT)

// i metodi cardine sono automatizzabili tramite cicli per risparmiare almeno 2/3 del codice attuale

//---> GESTISCE ANCHE GLI SPRITE SOTTO LE NUOVE COMPONENTI

////////---------------> todo: ottimizzare la factory

@SuppressWarnings("serial")
public class StageFactory extends Panel {
	
	private static SpriteRAM editRAM;
	private static SpriteEditBox editBox;
	
	private static Map<SpritesEnum, SpriteType> spritesStage = new HashMap<SpritesEnum, SpriteType>();
	
	public static Map<SpritesEnum, SpriteType> mapSprites(Stage stage, Simulation ncs, Scrollbar scrollBar) {
		
		//automatizzabile
		spritesStage.put(SpritesEnum.STATUS_WORD, new SpriteRect(stage, 218, 250, 130, 20));

		spritesStage.put(SpritesEnum.BOX_EDITOR, editBox);
		spritesStage.put(SpritesEnum.ABUS, new SpriteRect(stage, 358, 30, 5, 259));
		//spritesStage.put(SpritesEnum.RAM_CONTAINER, new SpriteRect(pippin.stage, 0, 3, ncs.getWidth() - 390, 292));
		spritesStage.put(SpritesEnum.INSTRUCTION_REGISTER, new SpriteIR(stage, 35, 30, 140, 20));
		spritesStage.put(SpritesEnum.MULTIPLEXER, new SpriteMUX(stage, 185, 100, 60, 20));
		spritesStage.put(SpritesEnum.ARITHMETIC_LOGIC_UNIT, new SpriteALU(stage, 90, 170, 160, 50));
		spritesStage.put(SpritesEnum.RAM_EDITOR, editRAM);
		spritesStage.put(SpritesEnum.ACCUMULATOR, new SpriteBox(stage, "ACC", 135, 250, 70, 20, 0));
		spritesStage.put(SpritesEnum.AUTO_INCREMENT, new SpriteBox(stage, "INC", 300, 100, 30, 20, "+2", "+10"));
		spritesStage.put(SpritesEnum.PROGRAM_COUNTER, new SpriteBox(stage, "PC", 280, 145, 70, 20, 0));
		spritesStage.put(SpritesEnum.DECODER,
				new SpriteBox(stage, "DEC", 35, 100, 70, 20, "Decoder", "Decoder"));
		spritesStage.put(SpritesEnum.INSTRUCTION_IN_RAM, new SpriteIRAM(stage,
				410, 30, scrollBar));
		spritesStage.put(SpritesEnum.EDITOR,
				new SpriteBox(stage, "EDIT", 10, 410, 470, 250, 0));
		spritesStage.put(SpritesEnum.SYSTEM_SERVICES,
				new SpriteBox(stage, "SYSTEM", 10, 293, 260, 110, 0));
		//spritesStage.put(SpritesEnum.VIRTUAL_SSD,
			//	new SpriteBox(pippin.stage, "SSD", (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 460, 490, 295, 150, 0));
		spritesStage.put(SpritesEnum.VIRTUAL_RAM,
				new SpriteBox(stage, "VRAM", 500, 410, 100, 250, 0));
		return spritesStage;
	}
	
	public static void setSpritesOnStage(Stage stage) {
		//automatizzabile
		
		//problema logico - il programma setta le etichette sullo pippin.stage dalle etichette stesse il che piega un po'
		//la logica costruttiva generale -> frame add/canvas add/panel
		final int labelOffsetX = 45;
		final int labelOffsetY = 25;
		for (Map.Entry<SpritesEnum, SpriteType> entry : spritesStage.entrySet()) {
			switch (entry.getKey()) {
				case STATUS_WORD:
					makeLabel(stage, "STATUS_WORD", 238, 250, 100, 20, 2);
					entry.getValue().addStage(2);
					break;
			case BOX_EDITOR:
				break;
			case ABUS:
				entry.getValue().addStage(2);
				break;
			case CPU_CONTAINER:
				entry.getValue().setBgColor(new Color(0, 0, 0, 0));
				entry.getValue().addStage(0);
				makeContainerLabel(stage, "CPU", 370 - labelOffsetX, 3 + labelOffsetY, 5, 5, 1);
				break;
			case RAM_CONTAINER:
				entry.getValue().setBgColor(new Color(0, 0, 0, 0));
				entry.getValue().addStage(0);
				// makeContainerLabel("RAM", 300 - labelOffsetX, 3 + labelOffsetY, 5, 5, 1);
				break;
			case MULTIPLEXER:
				entry.getValue().addStage(2);
				break;
			case ARITHMETIC_LOGIC_UNIT:
				entry.getValue().addStage(2);
				makeLabel(stage, "ALU", 90, 170, 160, 50, 2);
				break;
			case INSTRUCTION_REGISTER:
				entry.getValue().addStage(2);
				makeLabel(stage, "IR", 35, 30, 140, 20, 1);
				break;
			case RAM_EDITOR:
				break;
			case DECODER:
				entry.getValue().addNode("IR", 35, 0);
				entry.getValue().addNode("ALU", 35, 20);
				entry.getValue().addNode("MUX", 70, 10);
				entry.getValue().addStage(2);
				break;
			case ACCUMULATOR:
				entry.getValue().addNode("ALU", 35, 0);
				entry.getValue().addNode("DAT", 35, 20);
				entry.getValue().addStage(2);
				makeLabel(stage, "ACC", 135, 250, 70, 20, 1);
				break;
			case AUTO_INCREMENT:
				entry.getValue().addNode("ADD", 15, 0);
				entry.getValue().addNode("PC", 15, 20);
				entry.getValue().addStage(2);
				break;
			case PROGRAM_COUNTER:
				entry.getValue().addNode("INC", 35, 0);
				entry.getValue().addNode("ADD", 35, 20);
				entry.getValue().setPC(true);
				entry.getValue().addStage(2);
				makeLabel(stage, "PC", 280, 145, 70, 20, 1);
				break;
			case INSTRUCTION_IN_RAM:
				entry.getValue().addStage(2);
				break;
			case EDITOR:
				entry.getValue().addNode("EDI", 315, 602);
				entry.getValue().addStage(2);
				break;
			case SYSTEM_SERVICES:
				entry.getValue().addNode("SYS", 566, 602);
				entry.getValue().addNode("SYS2", 670, 558);
				entry.getValue().addNode("SYS3", 670, 412);
				entry.getValue().addStage(2);
				break;
			case VIRTUAL_SSD:
				entry.getValue().addStage(2);
				break;
			case VIRTUAL_RAM:
				entry.getValue().addNode("VRAM", 600, 412);
				entry.getValue().addNode("VRAM2", 1187, 366);
				entry.getValue().addNode("VRAM3", 1150, 366);
				entry.getValue().addStage(2);
				break;
			default:
				break;
			}
		}
	}
	static SpriteLabel makeLabel(Stage stage, String text, int x, int y, int width, int height, int horPos) {
		int vertSpace = 4;
		SpriteLabel label = new SpriteLabel(stage, text, x, y - vertSpace, width, 0, horPos, 5);
		label.addStage(1);
		return label;
	}
	static SpriteLabel makeContainerLabel(Stage stage, String text, int x, int y, int width, int height, int horPos) {
		int vertSpace = 4;
		SpriteLabel label = new SpriteLabel(stage, text, x, y - vertSpace, width, 0, horPos, 5);
		label.addStage(1);
		label.setFont(new Font("Helvetica", Font.BOLD, 18));
		return label;
	}
	public static void wireUnit(Stage stage, Simulation ncs, Color bgColor) {
		
		makeNode(stage, "ADD4", 265, 180);
		makeNode(stage, "DAT3", 15, 140);
		makeNode(stage, "DAT1", "ACC:DAT", 285);
		makeNode(stage, "DAT6", "IR:DAT", 10);
		makeNode(stage, "DAT2", "DAT3", "DAT1");
		makeNode(stage, "DAT4", "ALU:DAT", "DAT3");
		makeNode(stage, "DAT5", "DAT3", "DAT6");
		makeNode(stage, "DAT7", "MUX:DAT", "DAT6");
		makeNode(stage, "DAT8", "IRAM:DAT", "DAT6");
		makeNode(stage, "ADD1", "IR:ADD", "IRAM:ADD");
		makeNode(stage, "ADD2", "MUX:ADD", "IRAM:ADD");
		makeNode(stage, "ADD3", "ADD4", "IRAM:ADD");
		makeNode(stage, "ADD5", "PC:ADD", "ADD4");
		makeNode(stage, "ADD6", "INC:ADD", "IRAM:ADD");
		makeNode(stage, "DEC1", "DEC:ALU", "ALU:DEC");
		
		int a;
		a=38;
		for(int i = 0; i<16; ++i) {
			
			makeNode(stage, Integer.toString(a), 360, a);
			makeNode(stage, Integer.toString(a) + "1", 410, a);
			makeWire(stage, Integer.toString(a), Integer.toString(a) + "1", "controlIn", ncs);
			
			a+=16;
		}
		
		//wire pippin.system
		makeNode(stage, "EDI", 276, 390);
		makeNode(stage, "SYS", 393, 390);
		makeNode(stage, "OTH", 393, 410);
		
		makeNode(stage, "SYS2", 276, 350);
		makeNode(stage, "SYS3", 548, 350);
		makeNode(stage, "VRAM", 548, 500);
		
		makeNode(stage, "SYS0", 575, 450);
		makeNode(stage, "SYS4", 575, 330);
		makeNode(stage, "SYS5", 636, 330);
		makeNode(stage, "VRAM2",636, 290);
		
		makeWire(stage, "EDI", "SYS", "dataOut", ncs);
		makeWire(stage, "SYS", "OTH", "dataOut", ncs);
		//makeWire(pippin.stage, "EDI", "SYS", "dataOut", ncs);
		
		makeWire(stage, "SYS2", "SYS3", "dataOut", ncs);
		makeWire(stage, "SYS3", "VRAM", "dataOut", ncs);
		
		makeWire(stage, "SYS0", "SYS4", "dataOut", ncs);
		makeWire(stage, "SYS4", "SYS5", "dataOut", ncs);
		makeWire(stage, "SYS5", "VRAM2", "dataOut", ncs);
		
		//wire vram & pippin.system
		/*
		makeNode(pippin.stage, "VRAM3", 1130, 366);
		makeNode(pippin.stage, "RAMSTACK1", 1130, 280);
		*/
		//makeWire(pippin.stage, "VRAM3", "RAMSTACK1", "dataOut", ncs);
		/////////////////////////////////////////////////////
		
		int decoderNodesX = spritesStage.get(SpritesEnum.DECODER).getX()
				+ spritesStage.get(SpritesEnum.DECODER).getWidth() / 4;
		int decToRamTurnY = (int) ((spritesStage.get(SpritesEnum.ACCUMULATOR).getX()
				+ spritesStage.get(SpritesEnum.ACCUMULATOR).getHeight()) * 1.5f
				- spritesStage.get(SpritesEnum.ARITHMETIC_LOGIC_UNIT).getX()) - 5;
		makeNode(stage, "DEC2", decoderNodesX, spritesStage.get(SpritesEnum.DECODER).getX());
		makeNode(stage, "DEC3", decoderNodesX, stage.nodeByName("DAT3").getY() - 5);
		makeNode(stage, "DEC4", decoderNodesX, stage.nodeByName("DAT3").getY() + 5);
		makeNode(stage, "DEC5", decoderNodesX, decToRamTurnY);
		makeNode(stage, "DEC6", spritesStage.get(SpritesEnum.ACCUMULATOR).getX()
				+ spritesStage.get(SpritesEnum.ACCUMULATOR).getWidth() / 2 - 5, decToRamTurnY);
		makeNode(stage, "DEC7", spritesStage.get(SpritesEnum.ACCUMULATOR).getX()
				+ spritesStage.get(SpritesEnum.ACCUMULATOR).getWidth() / 2 + 5, decToRamTurnY);
		makeNode(stage, "DEC8", spritesStage.get(SpritesEnum.ABUS).getX(), decToRamTurnY);
		
		makeWire(stage, "DEC2", "DEC3", "controlOut", ncs);
		makeWire(stage, "DEC5", "DEC6", "controlOut", ncs);
		makeWire(stage, "DEC7", "DEC8", "controlOut", ncs);
		makeWire(stage, "ACC:DAT", "DAT1", "dataOut", ncs);
		makeWire(stage, "DAT1", "DAT2", "dataOut", ncs);
		makeWire(stage, "DAT2", "DAT3", "dataOut", ncs);
		Sprite_Wire tempA = makeWire(stage, "DAT3", "DAT4", "dataOut", ncs);
		makeWire(stage, "DAT4", "ALU:DAT", "dataOut", ncs);
		makeWire(stage, "DAT3", "DAT5", "dataOut", ncs);
		makeWire(stage, "DAT5", "DAT6", "dataOut", ncs);
		makeWire(stage, "DAT6", "IR:DAT", "dataOut", ncs);
		makeWire(stage, "DAT6", "DAT7", "dataOut", ncs);
		Sprite_Wire tempC = makeWire(stage, "DAT7", "MUX:DAT", "dataOut", ncs);
		makeWire(stage, "DAT7", "DAT8", "dataOut", ncs);
		makeWire(stage, "DAT8", "IRAM:DAT", "dataOut", ncs);
		makeWire(stage, "IR:ADD", "ADD1", "dataIn", ncs);
		makeWire(stage, "ADD1", "ADD2", "dataIn", ncs);
		makeWire(stage, "ADD2", "MUX:ADD", "dataIn", ncs);
		SpriteRect temp2 = makeRect(stage, bgColor, "DAT7", "ADD3");
		Sprite_Wire tempD = makeWire(stage, "ADD2", "ADD3", "address", ncs);
		tempC.setDirtyPartner(temp2);
		temp2.setDirtyPartner(tempD);
		makeWire(stage, "ADD3", "ADD4", "address", ncs);
		makeWire(stage, "ADD4", "ADD5", "address", ncs);
		makeWire(stage, "ADD5", "PC:ADD", "address", ncs);
		makeWire(stage, "ADD3", "ADD6", "address", ncs);
		makeWire(stage, "ADD6", "INC:ADD", "address", ncs);
		makeWire(stage, "ADD6", "IRAM:ADD", "address", ncs);
		makeWire(stage, "IR:DEC", "DEC:IR", "dataIn", ncs);
		SpriteRect temp1 = makeRect(stage, bgColor, "DEC1", "DAT3");
		Sprite_Wire tempB = makeWire(stage, "DEC:ALU", "DEC1", "controlIn", ncs);
		tempA.setDirtyPartner(temp1);
		temp1.setDirtyPartner(tempB);
		makeWire(stage, "DEC1", "ALU:DEC", "controlIn", ncs);
		makeWire(stage, "ALU:ACC", "ACC:ALU", "dataIn", ncs);
		makeWire(stage, "DEC:MUX", "MUX:DEC", "controlIn", ncs);
		makeWire(stage, "MUX:ALU", "ALU:MUX", "dataIn", ncs);
		makeWire(stage, "INC:PC", "PC:INC", "address", ncs);
	}
	private static Sprite_Node makeNode(Stage stage, String name, int x, int y) {
		Sprite_Node node = new Sprite_Node(name, x, y);
		stage.addNode(node);
		return node;
	}
	private static Sprite_Node makeNode(Stage stage, String name, String xName, int y) {
		Sprite_Node xNode = stage.nodeByName(xName);
		return makeNode(stage, name, xNode.location().x, y);
	}
	private static Sprite_Node makeNode(Stage stage, String name, String xName, String yName) {
		Sprite_Node xNode = stage.nodeByName(xName);
		Sprite_Node yNode = stage.nodeByName(yName);
		return makeNode(stage, name, xNode.location().x, yNode.location().y);
	}
	private static SpriteRect makeRect(Stage stage, Color color, String xName, String yName) {
		Sprite_Node xNode = stage.nodeByName(xName);
		Sprite_Node yNode = stage.nodeByName(yName);
		SpriteRect rect = new SpriteRect(stage, color, xNode, yNode);
		rect.addStage(1);
		return rect;
	}
	private static Sprite_Wire makeWire(Stage stage, String start, String end, String busType, Simulation ncs) {
		Sprite_Node startNode = stage.nodeByName(start);
		Sprite_Node endNode = stage.nodeByName(end);
		Sprite_Wire wire = new Sprite_Wire(stage, startNode, endNode, ncs, busType);
		wire.addStage(1);
		return wire;
	}
}
