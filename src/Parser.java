
// Created by Austin Patel on 7/5/16 at 5:46 PM

// Parses an A or C instruction into its different parts
public class Parser {

	// Gets the destination of a C instruction
	public static String getDest(String instruction) {
		if (instruction.contains("="))
			return instruction.substring(0, instruction.indexOf("="));
		
		return "";
	}

	// Gets the computation of a C instruction
	public static String getComp(String instruction) {
		int startComp = 0;
		if (instruction.contains("="))
			startComp = instruction.indexOf("=") + 1;

		int endComp;
		if (instruction.contains(";"))
			endComp = instruction.indexOf(";");
		else
			endComp = instruction.length();

		return instruction.substring(startComp, endComp);
	}

	// Gets the jump of a C instruction
	public static String getJump(String instruction) {
		if (!instruction.contains(";"))
			return "";
		return instruction.substring(instruction.indexOf(";") + 1, instruction.length());
	}

	// Gets the address in a A instruction
	public static String getAddress(String command) {
		return command.substring(1);
	}
}
