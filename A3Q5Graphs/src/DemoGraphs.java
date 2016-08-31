import java.util.Arrays;

import sun.security.provider.certpath.AdjacencyList;

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
	 * @param letter lowercase letter
	 * @return number less the ASCII value of 'a'
	 */
	public static int apToNum(char letter) {
		int base = (int) 'a';
		int num = (int) letter - base;
		return num;
	}

	public static boolean[][] createAdjacencyMatrix() {
		Integer[][] something = { { 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0 },
				{ 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 } };

		boolean adjMatrix[][] = convertIntegerArrToBoolean(something);

		return adjMatrix;
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
		System.out.println("Alpha AdjacencyList");
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
		System.out.println();
	}

	/**
	 * Specify number of rows in Graph
	 */
	static int graphRows = 4;

	/**
	 * Specify number of columns in Graph
	 */
	static int graphCols = 4;

	/**
	 * Determines whether the given relative grid position CAN occur in the
	 * Graph, assuming a 2D space.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 * @title
	 * @assignment 2
	 *
	 * @param i
	 * @param rowOffset
	 * @param colOffset
	 * @return
	 */
	private static boolean validVertex(int i, int rowOffset, int colOffset) {
		int iMod = i % graphCols;

		if (iMod + colOffset < 0 || iMod + colOffset >= graphCols)
			return false; // off left or right edge

		if (i + rowOffset * graphCols < 0) {
			return false; // off top
		}

		if (i + rowOffset * graphCols > graphRows * graphCols - 1) {
			return false; // off bottom
		}

		if (rowOffset == 0 && colOffset == 0)
			return false; // not me

		return true;
	}

	/**
	 * Prints AdjacencyMatrix to an AdjacencyList in CW processing order,
	 * starting with the top-left adjacent vertex.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 * @title
	 * @assignment 2
	 *
	 * @param b
	 */
	public static void printBADAdjacencyList(boolean[][] b) {
		System.out.println("ClockWise AdjacencyList");
		for (int i = 0; i < b.length; i++) {
			System.out.printf("%n%s: ", numToAP(i));
			String prefix = "";

			for (int row = -graphRows + 1; row < graphRows; row++) {
				for (int col = -graphCols + 1; col < graphCols; col++) {
					if (validVertex(i, row, col)) {
						if (b[i][i + row * graphCols + col]) {
							System.out.printf("%s%s", prefix, numToAP(i + row * graphCols + col));
							prefix = ", ";
						}
					}
				}
			}
		}
		System.out.println();
	}

	/**
	 * Generates the clockwise adjacency list, starting at the lowest
	 * alphabetical letter for any vertex and then progressing clockwise around
	 * the outEdges.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 30, 2016
	 * @title
	 * @assignment 3
	 *
	 * @return
	 */
	public static AdjacencyLists buildAdjacencyListCW() {
		// @formatter:off
		char[][] cw = 
			{ 	{ 'b', 'f', 'e' }, 
				{ 'a', 'c' }, 
				{ 'b', 'd', 'f' }, 
				{ 'c', 'g' },
				{ 'a', 'i' }, 
				{ 'a', 'c', 'j' }, 
				{ 'd', 'h', 'k', 'j' },
				{ 'g', 'o' },
				{ 'e', 'j', 'n', 'm' },
				{ 'f', 'g', 'i' },
				{ 'g', 'o' },
				{ 'p' },
				{ 'i' },
				{ 'i', 'o' },
				{ 'h', 'p', 'n', 'k' },
				{ 'l', 'o' }
				};
		// @formatter:on

		AdjacencyLists al = new AdjacencyLists(graphRows * graphCols);

		for (int i = 0; i < cw.length; i++) {
			for (int j = 0; j < cw[i].length; j++) {
				al.addEdge(i, apToNum(cw[i][j]));
			}
		}
		return al;
	}

	/**
	 * @author Eric Dunbar
	 * @date Aug 29, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		CommonSuite.commonProgramStart(3, 5, "Graphs", false);

		// boolean[][] adjMatrixI = createAdjacencyMatrix();

		AdjacencyLists al = buildAdjacencyListCW();

		System.out.println("DFS");
		Searches.dfsZ(al, apToNum('g'));

		System.out.println();
		System.out.println("BFS");
		Searches.bfsZ(al, apToNum('b'));

		char pathStart = 'l';
		System.out.println();
		System.out.println("Path goes through every edge in each direction (doublePath)");
		System.out.printf("Path starts at vertex %s%n%n", pathStart);
		Searches.doublePathZ(al, apToNum(pathStart));
	}
}
