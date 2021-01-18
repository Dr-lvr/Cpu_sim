package pippin.components.cpuSprites;
import java.awt.*;

import pippin.components.utility.TextBox;
import pippin.manager.CpuParser;
import pippin.stage.Stage;

@SuppressWarnings("serial")
public class SpriteIR extends SpriteComponent {

    TextBox operatorBox;
    TextBox operandBox;
    Font font;

    
    public SpriteIR() {
		super();
	}

	public SpriteIR(Stage stage, int x, int y, int width, int height) {
        super(stage, "IR", x, y, width, height);
        font = new Font("Helvetica", 0, 12);
        addNode("DAT", width / 2, 0);
        addNode("DEC", width / 4, height);
        addNode("ADD", (3 * width) / 4, height);
        operatorBox = new TextBox(stage, x, y, width / 2, height, "", "", font, false, true);
        operandBox = new TextBox(stage, x + width / 2, y, width / 2, height, "", "", font, false, true);
    }

    public void setValue(int instruction) {
        int opcodeInt = CpuParser.top8Bits(instruction);
        int operandInt = CpuParser.bottom8Bits(instruction);
        String opcodeString = CpuParser.int16ToOpcode(instruction);
        String operandString = CpuParser.int16ToOperand(instruction);
        operatorBox.setText(opcodeString, CpuParser.intToBinString(opcodeInt, 8));
        operandBox.setText(operandString, CpuParser.intToBinString(operandInt, 8));
        markDirty();
    }

    public void flashOperator() {
        operatorBox.flashBG();
        markDirty();
    }

    public void flashOperand() {
        operandBox.flashBG();
        markDirty();
    }

    public void clear() {
        operatorBox.setText("", "");
        operandBox.setText("", "");
        markDirty();
    }

    public boolean drawAll(Graphics g) {
        markClean();
        super.dirty |= operandBox.drawAll(g);
        super.dirty |= operatorBox.drawAll(g);
        g.setColor(Color.black);
        g.drawRect(super.getX(), super.getY(), super.getWidth(), super.getHeight());
        if(super.getDirtyPartner() != null)
            super.getDirtyPartner().markDirty();
        return super.dirty;
    }

}
