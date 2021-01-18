package pippin.manager;
// Contains utility method for working with instructions (pretty much extracting data from raw values)

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

///---> PARSER USED IN SPRITE MANAGER -> INTERNAL STEP

public class CpuParser {

    static String error;
    public static final int OPCODE_ADD = 0;
    public static final int OPCODE_SUB = 1;
    public static final int OPCODE_MUL = 2;
    public static final int OPCODE_DIV = 3;
    public static final int OPCODE_LOD = 4;
    public static final int OPCODE_STO = 5;
    
    ////////////////////////////////new 
   // public static final int OPCODE_CALL = 6;
    
    public static final int OPCODE_AND = 8;
    public static final int OPCODE_NOT = 9;
    public static final int OPCODE_CPZ = 10;
    public static final int OPCODE_CPL = 11;
    public static final int OPCODE_JMP = 12;
    public static final int OPCODE_JMZ = 13;
    public static final int OPCODE_NOP = 14;
    public static final int OPCODE_HLT = 15;
    public static final int IMMEDIATE_FLAG = 16;
    public static final String[] VARIABLES = {"W", "X", "Y", "Z", "T1", "T2", "T3", "T4"};
    public static final int VARIABLE_BASE = 128;
    static final String SEPARATOR = "\t";

    private CpuParser() { }

    public static boolean isImmediate(String operand) {
        return operand.charAt(0) == '#';
    }

    public static int instructionToInt16(String instruction) {
        error = null;
        String opcodeString = firstPart(instruction);
        String operandString = secondPart(instruction);
        int opcode = opcodeToInt8(opcodeString);
        if (error != null) {
            return -1;
        }
        int operand = 0;
        if (opcodeTakesNoOperand(opcode) && operandString != null) {
            error = "opcode does not take an operand";
            return -1;
        }
        if (!opcodeTakesNoOperand(opcode) && operandString == null) {
            error = "opcode requires an operand";
            return -1;
        }
        if (opcodeTakesNoOperand(opcode)) {
            return joinBits(opcode, operand);
        }
        if (isImmediate(operandString)) {
            operand = operandImmediateToInt(operandString);
            if (error != null) {
                return -1;
            }
            opcode += 16;
        } else if (opcode == 12 || opcode == 13) {
            try {
                operand = Integer.parseInt(operandString, 10);
                if (operand < -128 || operand > 127) {
                    error = "operand out of range";
                    System.err.println("CPU.operandImmediate: value out of range " + operand);
                }
            } catch (NumberFormatException _ex) {
                if (operandString.matches("[A-Z]{1,9}")) {
                    error = "label not found";
                } else {
                    error = "operand not a legal integer";
                }
                return -1;
            }
        } else {
            operand = operandDirectToInt(operandString);
            if(error != null) {
                return -1;
            }
        }
        return joinBits(opcode, operand);
    }

    public static String int16ToInstruction(int instruction) {
        boolean imm = false;
        error = null;
        int opcode = top8Bits(instruction);
        int operand = bottom8Bits(instruction);
        if (opcode >= 16) {
            imm = true;
            opcode -= 16;
        }
        String opString = opcodeToString(opcode);
        if (opcodeTakesNoOperand(opcode)) {
            return opString;
        }
        String operString;
        if (imm) {
            operString = new String(operandImmediateToString(operand));
        } else if (opcode == 12 || opcode == 13) {
            operString = Integer.toString(operand);
        } else {
            operString = operandDirectToString(operand);
        }
        if (opString.length() > 0) {
            return opString + "\t" + operString;
        } else {
            error = "unknown opcode";
            return null;
        }
    }

    public static String int16ToOpcode(int instruction) {
        int top = top8Bits(instruction);
        if (top >= 16) {
            top -= 16;
        }
        return opcodeToString(top);
    }

    public static String int16ToOperand(int instruction) {
        int opcode = top8Bits(instruction);
        int operand = bottom8Bits(instruction);
        if (opcode >= 16) {
            return operandImmediateToString(operand);
        }
        if (opcode == 12 || opcode == 13) {
            return Integer.toString(operand);
        }
        if (operand == 0) {
            return "";
        } else {
            return operandDirectToString(operand);
        }
    }

    public static boolean opcodeTakesNoOperand(int opcode) {
        return opcode == 9 || opcode == 14 || opcode == 15;
    }

    public static char opcodeToOperation(int opcode) {
        switch (opcode) {
            case 0: // '\0'
                return '+';
            case 1: // '\001'
                return '-';
            case 2: // '\002'
                return '*';
            case 3: // '\003'
                return '/';
            case 4: // '\004'
                return '=';
            case 8: // '\b'
                return '^';
            case 10: // '\n'
                return ':';
            case 11: // '\013'
                return '<';
            case 9: // '\t'
                return '!';
            case 5: // '\005'
            case 6: // '\006'
            case 7: // '\007'
            default:
                return ' ';
        }
    }

    public static String intToBinString(int value, int digits) {
        String binString = Integer.toString(value, 2);
        return "0000000000000000".substring(binString.length(), digits) + binString;
    }

    public static String sEx8ToBinString(int value) {
        return intToBinString(value & 0xff, 8);
    }

    public static String sEx8ToDecString(int value) {
        return Integer.toString(intToSEx8(value), 10);
    }

    public static int intToSEx8(int value) {
        if ((value & 0x80) == 128) {
            return value | 0xffffff00;
        } else {
            return value & 0xff;
        }
    }

    public static int stringToSEx8(String value, int base) throws NumberFormatException {
        int i = Integer.valueOf(value, base);
        if (base == 2 && i >= 128 && i <= 255) {
            i |= 0xffffff00;
        }
        return i;
    }

    public static int top8Bits(int value) {
        return value / 256;
    }

    public static int bottom8Bits(int value) {
        return value % 256;
    }

    public static String error() {
        return error;
    }

    private static int opcodeToInt8(String opcode) {
        opcode = opcode.toUpperCase();
        error = null;
        switch (opcode) {
            case "ADD":
                return 0;
            case "SUB":
                return 1;
            case "MUL":
                return 2;
            case "DIV":
                return 3;
            case "LOD":
                return 4;
            case "STO":
                return 5;
            ////////////////////////////////new 
                /*
            case "CALL":
            	return 6;
            	*/
            case "AND":
                return 8;
            case "NOT":
                return 9;
            case "CPZ":
                return 10;
            case "CPL":
                return 11;
            case "JMP":
                return 12;
            case "JMZ":
                return 13;
            case "NOP":
                return 14;
            case "HLT":
                return 15;
            default:
                error = "unknown instruction";
                return -1;
        }
    }

    private static String opcodeToString(int opcode) {
        switch (opcode) {
            case 0: // '\0'
                return "ADD";
            case 1: // '\001'
                return "SUB";
            case 2: // '\002'
                return "MUL";
            case 3: // '\003'
                return "DIV";
            case 4: // '\004'
                return "LOD";
            case 5: // '\005'
                return "STO";
            case 8: // '\b'
                return "AND";
            case 9: // '\t'
                return "NOT";
            case 10: // '\n'
                return "CPZ";
            case 11: // '\013'
                return "CPL";
            case 12: // '\f'
                return "JMP";
            case 13: // '\r'
                return "JMZ";
            case 14: // '\016'
                return "NOP";
            case 15: // '\017'
                return "HLT";
            ////////////////////////////////new 
                /*  
            case 6: // '\006'
            	return "CALL";*/
            	
            case 7: // '\007'
            default:
                return "";
        }
    }

    private static int operandDirectToInt(String operand) {
        if (operand == null) {
            error = "opcode requires an operand";
            return 0;
        }
        if (operand.charAt(0) == '#') {
            error = "opcode requires a direct reference";
            return 0;
        }
        /*for (int i = 0; i < VARIABLES.length; i++) {
            if (VARIABLES[i].equals(operand)) {
                return i + 128;
            }
        }
        error = "unknown variable";*/
        if (operand.matches("[A-Z]{1,9}")) {
            error = "label not found";
            return 0;
        }
        if(!operand.matches("\\d{1,3}")) {
        	error = "invalid sintax";
        	return 0;
        	
        }
        int value = Integer.parseInt(operand);
        if (value % 2 != 0 || value < 0 || value > 126) {
            error = "invalid address";
            return 0;
        }
        return value; // 0
    }

    private static String operandDirectToString(int operand) {
       /* if (operand >= 128 && operand - 128 < VARIABLES.length) {
            return VARIABLES[operand - 128];
        } else {
            error = "unknown variable";
            return null;
        }*/
        return Integer.toString(operand);
    }

    private static int operandImmediateToInt(String operand) {
        if (operand == null) {
            error = "opcode requires an operand";
            return 0;
        }
        if (operand.charAt(0) != '#') {
            error = "opcode requires an immediate value";
            return 0;
        }
        String value = operand.substring(1);
        try {
            int integer = Integer.parseInt(value, 10);
            if (integer < -128 || integer > 127) {
                error = "operand out of range";
                System.err.println("CPU.operandImmediate: value out of range " + integer);
                return 0;
            } else {
                return integer;
            }
        } catch (NumberFormatException _ex) {
            error = "operand not a legal integer";
        }
        return 0;
    }

    private static String operandImmediateToString(int operand) {
        return "#" + intToSEx8(operand);
    }

    public static String firstPart(String string) {
        int startPos;
        for(startPos = 0; startPos < string.length() && (string.charAt(startPos) == ' ' || string.charAt(startPos) == '\t'); startPos++);
        int endPos;
        for(endPos = startPos; endPos < string.length() && string.charAt(endPos) != ' ' && string.charAt(endPos) != '\t'; endPos++);
        String result = string.substring(startPos, endPos);
        return result;
    }

    public static String secondPart(String string) {
        int startPos;
        for(startPos = 0; startPos < string.length() && (string.charAt(startPos) == ' ' || string.charAt(startPos) == '\t'); startPos++);
        for(; startPos < string.length() && string.charAt(startPos) != ' ' && string.charAt(startPos) != '\t'; startPos++);
        for(; startPos < string.length() && (string.charAt(startPos) == ' ' || string.charAt(startPos) == '\t'); startPos++);
        int endPos;
        for(endPos = startPos; endPos < string.length() && string.charAt(endPos) != ' ' && string.charAt(endPos) != '\t'; endPos++);
        if(startPos == endPos) {
            return null;
        } else {
            String result = string.substring(startPos, endPos);
            return result;
        }
    }
    //fantastico
    public static int joinBits(int top8Bits, int bottom8Bits) {
        return (top8Bits << 8 & 0xff00) + (bottom8Bits & 0xff);
    }

}
