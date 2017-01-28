
// Created by Austin Patel on 7/5/16 at 5:46 PM

// This program has the user input the file location of a hack program is a *.asm file and
// converts it into the hack machine language binary code

public class Main {
	
	// args[0] = path of the *.asm file to convert to *.hack
	public static void main(String[] args) {
		AsmFileManager manager = new AsmFileManager();
		
		if (args.length == 0) // If run from Eclipse
			manager.getContent("");
		else // If run from the command line
			manager.getContent(args[0]);
		
		manager.removeWhiteSpace();
		manager.convertSymbols();
		manager.assemble();
		manager.generateHack();
	}
}