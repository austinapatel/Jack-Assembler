
// Created by Austin Patel on 7/5/16 at 6:35 PM

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Manages the asm file and all of its functions

public class AsmFileManager {

	private final int STARTING_MEM_LOCATION = 16;

	private String fileLocation;
	private List<String> fileContent = new ArrayList<>();

	// Gets the location of the *.asm file and loads it
	// "filePath" is an optional variable that if is passed in then the argument
	// will be used as the path otherwise the program will ask for a path to use
	public void getContent(String filePath) {
		if (filePath.equals("")) {
			System.out.println("Enter the file location of a *.asm file in the hack "
					+ "assembly language to be converted to a *.hack binary file:");

			Scanner scanner = new Scanner(System.in);
			File asmFile;

			while (true) {
				fileLocation = scanner.nextLine();

				asmFile = new File(fileLocation);

				if (asmFile.exists()) {
					// Make sure the file ending is *.asm
					if (!fileLocation.substring(fileLocation.length() - 4).equals(".asm")) {
						System.out.println("The file does not end with *.asm");
						continue;
					}
					break;
				}

				System.out.println("The file at the location specified does not exist.");
			}
			scanner.close();
		} else {
			fileLocation = filePath;
		}
		
		try {
			fileContent = Files.readAllLines(Paths.get(fileLocation), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("File could not be read.");
			e.printStackTrace();
		}
	}

	public void removeWhiteSpace() {
		for (int lineNum = 0; lineNum < fileContent.size(); lineNum++) {
			String line = fileContent.get(lineNum);

			// Remove comments
			if (line.contains("//"))
				fileContent.set(lineNum, line.substring(0, line.indexOf("//")));
			line = fileContent.get(lineNum);

			// Trim the beginning and the end of the line
			fileContent.set(lineNum, line.trim());
		}

		// Remove blank lines
		boolean foundBlank = true;
		while (foundBlank) {
			foundBlank = false;
			for (String line : fileContent)
				if (line.equals(""))
					foundBlank = true;

			fileContent.remove("");
		}
	}

	// Converts symbols into their binary equivalent
	public void convertSymbols() {
		SymbolTable symbolTable = new SymbolTable();

		// Add labels to symbol table
		int lineNum = 0;
		ArrayList<String> removalList = new ArrayList<String>();
		for (String line : fileContent) {
			if (line.contains("(")) {
				symbolTable.add(line.substring(1, line.length() - 1), lineNum);
				removalList.add(line);
			} else
				lineNum++;
		}

		for (String item : removalList)
			fileContent.remove(item);

		int curMemLocation = STARTING_MEM_LOCATION;

		// Add variable symbols to table
		for (int i = 0; i < fileContent.size(); i++) {
			String line = fileContent.get(i);
			if (line.charAt(0) == '@') {
				String address = line.substring(1);
				try {
					Integer.parseInt(address);
				} catch (NumberFormatException e) { // Address must be a String
													// (variable)
					if (!symbolTable.contains(address)) {
						symbolTable.add(address, curMemLocation);
						curMemLocation++;
					}
					fileContent.set(i, "@" + String.valueOf(symbolTable.get(address)));
				}
			}
		}
	}

	// Converts the assembly instructions into binary ones
	public void assemble() {
		for (int i = 0; i < fileContent.size(); i++) {
			String line = fileContent.get(i);
			if (line.charAt(0) == '@') // A instruction
				fileContent.set(i, Code.aInstruction(line));
			else // C instruction
				fileContent.set(i, Code.cInstruction(line));
		}
	}

	// Creates a *.hack file writing the binary contents into it
	public void generateHack() {
		try {
			String writePath = fileLocation.subSequence(0, fileLocation.length() - 4) + ".hack";
			Files.write(Paths.get(writePath), fileContent);
			System.out.println("*.asm to *.hack conversion complete.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printContent() {
		for (String line : fileContent)
			System.out.println(line);
	}
}
