
// Created by Austin Patel on 7/5/16 at 5:46 PM

import java.util.HashMap;

// Holds the symbol table that contains the predefined symbols, variables and labels
public class SymbolTable {

	private HashMap<String, Integer> symbols = new HashMap<String, Integer>() {
		/**
		* 
		*/
		private static final long serialVersionUID = 1L;

		{
			put("R0", 0);
			put("R1", 1);
			put("R2", 2);
			put("R3", 3);
			put("R4", 4);
			put("R5", 5);
			put("R6", 6);
			put("R7", 7);
			put("R8", 8);
			put("R9", 9);
			put("R10", 10);
			put("R11", 11);
			put("R12", 12);
			put("R13", 13);
			put("R14", 14);
			put("R15", 15);
			put("SCREEN", 16384);
			put("KBD", 24576);
			put("SP", 0);
			put("LCL", 1);
			put("ARG", 2);
			put("THIS", 3);
			put("THAT", 4);
		}
	};

	public void add(String key, int value) {
		symbols.put(key, value);
	}

	public int get(String key) {
		return symbols.get(key);
	}
	
	public boolean contains (String key) {
		return symbols.containsKey(key);
	}
}
