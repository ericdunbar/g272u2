import java.util.Arrays;

/**
 * Compare two sequences with the same total order relation to determine if they
 * contain the same set of elements.
 * 
 * @author Eric Dunbar
 * @date Aug 31, 2016
 * @title Assignment 3, Question 3 Comparison of two sequences with possible
 *        duplicates
 * @assignment 3
 *
 */
public class A3Q3Comparison {

	public static void main(String[] args) {
		CommonSuite.commonProgramStart(3, 3, "Same set?", false);

		demoSequenceCompare(new Integer[] { 3, 34, 334, 4, 3 },
				new Integer[] { 3, 34, 334, 4, 4, 4, 4, 3 });

		demoSequenceCompare(new Integer[] { 3, 34, 334, 4, 3 },
				new Integer[] { 3, 34, 334, 4, 4, 4, 4, 3, 4 });
	}

	/**
	 * Demonstrate the two different comparison routines for sequences upon
	 * which the same total order relation has been defined.
	 * 
	 * @author Eric Dunbar
	 * @date Sep 1, 2016
	 * @title Demo
	 * @assignment 2
	 *
	 * @param s1 first sequence of elements
	 * @param s2 second sequence of elements
	 */
	public static <T> void demoSequenceCompare(T[] s1, T[] s2) {
		CommonSuite.printFancyHeader("O(n) minimum time set comparison");

		System.out.println("Sequence 1: " + Arrays.toString(s1));
		System.out.println("Sequence 2: " + Arrays.toString(s2));
		System.out.println("The sequences are " + (compareSequences_o_n(s1, s2) ? "" : "not ")
				+ "the same set.");

		CommonSuite.printFancyHeader("O(2n) minimum time set comparison");

		System.out.println("Sequence 1: " + Arrays.toString(s1));
		System.out.println("Sequence 2: " + Arrays.toString(s2));
		System.out.println("The sequences are " + (compareSequences_o_2_n(s1, s2) ? "" : "not ")
				+ "the same set.");
	}

	/**
	 * Compare two sequences to determine if they contain the same set of
	 * elements. The same total order relation has been defined on it. Runs in
	 * O(2n) best case with no duplicates and O(3n) in worst case if there are
	 * many duplicates.
	 * 
	 * @author Eric Dunbar
	 * @date Sep 1, 2016
	 * @title Compare two sequences, O(2n) best time
	 * @assignment 2
	 *
	 * @param s1 first sequence
	 * @param s2 second sequence
	 * @return true if the sequences contain the same set
	 */
	public static <T> boolean compareSequences_o_2_n(T[] s1, T[] s2) {
		int c1 = 0, c2 = 0;
		boolean sameSet = true;

		// PRIME THE LOOP
		// test for inequality between first elements
		if (!s1[c1].equals(s2[c2]))
			sameSet = false;
		else
			do {
				// scan for next new element in S1
				while (c1 < s1.length - 1)
					if (!s1[c1].equals(s1[++c1]))
						break;

				// scan for next new element in S2
				while (c2 < s2.length - 1)
					if (!s2[c2].equals(s2[++c2]))
						break;

				// test for inequality between next elements
				if (!s1[c1].equals(s2[c2])) {
					sameSet = false;
					break;
				}
			} while (c1 < s1.length - 1 || c2 < s2.length - 1);
		return sameSet;
	}

	/**
	 * Compare two sequences to determine if they contain the same set of
	 * elements. The same total order relation has been defined on it. Runs in
	 * O(n) best case with no duplicates and O(4n) in worst case if there are
	 * exactly 1 and 2 copies, respectively of each element in each sequence.
	 * 
	 * @author Eric Dunbar
	 * @date Sep 1, 2016
	 * @title compare sequences, O(n) best time
	 * @assignment 2
	 *
	 * @param s1 first sequence
	 * @param s2 second sequence
	 * @return true if the sequences contain the same set
	 */
	public static <T> boolean compareSequences_o_n(T[] s1, T[] s2) {
		int c1 = 0, c2 = 0;
		boolean sameSet = true;

		// PRIME THE LOOP
		// test for inequality between first elements
		if (!s1[c1].equals(s2[c2]))
			sameSet = false;
		else
			while (c1 < s1.length - 1 || c2 < s2.length - 1) {
				if (c1 < s1.length - 1)
					c1++;
				if (c2 < s2.length - 1)
					c2++;

				// test for inequality between next elements
				if (!s1[c1].equals(s2[c2])) {

					// scan for next new element in S1
					while (c1 < s1.length - 1)
						if (s1[c1 - 1].equals(s1[c1]))
							c1++;
						else
							break;

					// scan for next new element in S2
					while (c2 < s2.length - 1)
						if (s2[c2 - 1].equals(s2[c2]))
							c2++;
						else
							break;

					if (!s1[c1].equals(s2[c2])) {
						sameSet = false;
						break;
					}
				}
			}
		return sameSet;
	}
}
