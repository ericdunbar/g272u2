import java.util.Arrays;

/**
 * 
 */

/**
 * @author Eric Dunbar
 * @date Aug 29, 2016
 * @title
 * @assignment 3
 *
 */
public class DemoGraphs {

	/**
	 * Converts a number from 1 to 26 to a corresponding lowercase letter from
	 * a-z. Does not throw an error if an integer is provided that can be
	 * converted less (int)'a' to a valid character.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 *
	 * @param num
	 * @return lowercase letter with a = 0 .. z = 25
	 */
	public static char numToAP(int num) {
		int base = (int) 'a';
		char let = (char) (num + base);
		return let;
	}

	/**
	 * Converts a character from a-z to a corresponding number from 0 to 25.
	 * Does not throw an error if a valid char is passed to it.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 *
	 * @param let lowercase letter
	 * @return number less the ASCII value of 'a'
	 */
	public static int apToNum(char let) {
		int base = (int) 'a';
		int num = (int) let - base;
		System.out.println(num);
		return num;
	}

	public static Integer[][] createAdjacencyMatrix() {
		Integer[][] something = { { 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 } };

		return something;
	}

	public static boolean[][] convertIntegerArrToBoolean(Integer[][] s) {
		boolean[][] b = new boolean[s.length][s.length];
		for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
				b[i][j] = (s[i][j] == 1);
			}
		}
		return b;
	}

	/**
	 * Processes AdjacencyMatrix alphabetically.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 * @title
	 * @assignment 2
	 *
	 * @param b
	 */
	public static void printAlphaAdjacencyList(boolean[][] b) {
		for (int i = 0; i < b.length; i++) {
			System.out.printf("%n%s: ", numToAP(i));
			String prefix = "";

			for (int j = 0; j < b[i].length; j++) {
				if (b[i][j]) {
					System.out.printf("%s%s", prefix, numToAP(j));
					prefix = ", ";
				}
			}

		}
	}

	
	/**
	 * Processes AdjacencyMatrix alphabetically.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 * @title
	 * @assignment 2
	 *
	 * @param b
	 */
	public static void printCWAdjacencyList(boolean[][] b) {

		for (int i = 0; i < b.length; i++) {
			System.out.printf("%n%s: ", numToAP(i));
			String prefix = "";

			if ((i % 4) == 0) {
				
			}
			for (int j = 0; j < b[i].length; j++) {
				if (b[i][j]) {
					System.out.printf("%s%s", prefix, numToAP(j));
					prefix = ", ";
				}
			}

		}
	}

	
	
	/**
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 * @title
	 * @assignment 2
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		Integer[][] s = createAdjacencyMatrix();

		boolean b[][] = convertIntegerArrToBoolean(s);

		printAlphaAdjacencyList(b);
	}
}
