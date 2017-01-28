
// Created by Austin Patel on 7/5/16 at 8:02 PM

import java.util.ArrayList;

// Includes binary functions
public class Binary {

	public static String decimalToBinary(int decimal) {
		// First get the powers of 2 that fit into the number
		ArrayList<Integer> spots = new ArrayList<>();
		int val = decimal;

		while (val != 0) {
			int curPow = 2;

			while (curPow < val) {
				curPow = curPow << 1;

				if (curPow > val) {
					curPow = curPow >> 1;
					val -= curPow;
					spots.add(curPow);
					curPow = 2;
				} else if (curPow == val) {
					val -= curPow;
					spots.add(curPow);
					break;
				}
			}

			if (val == 1) {
				spots.add(1);
				val -= 1;
			} else if (val == 2) {
				spots.add(2);
				val -= 2;
			}

			if (val == 0) {
				String binary = "";
				// Conversion from spots to binary
				for (int i = spots.get(0); i >= 1; i /= 2) {
					if (spots.contains(i))
						binary += "1";
					else
						binary += "0";
				}

				while (binary.length() < 15) // Make sure it is 14 characters long
					binary = "0" + binary;

				return binary;
			}
		}
		return "000000000000000"; // Handles if input is 0
	}
}
