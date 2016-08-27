import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Eric Dunbar
 * @date Aug 26, 2016
 * @title
 * @assignment 3
 *
 */
public class A3Q4a_MergeSort {

	/**
	 * Sequence to be sorted for question 4 in assignment 3
	 */
	public static final Integer sequence[] = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5 };

	private static void displayQuestion() {
		String title = "Sorting";
		String description[] = {
				"4. (5 + 8 = 13 marks) Given sequence 3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5,",
				"   sort the sequence using the following algorithms, and illustrate the",
				"   details of the execution of the algorithms:", "",
				"    a. (5 marks) merge-sort algorithm.", "",

				"    b. (8 marks) quick-sort algorithm. Choose a partitioning strategy you",
				"       like to pick a pivot element from the sequence. Analyze how different",
				"       portioning strategies may impact on the performance of the sorting",
				"       algorithm." };
		CommonSuite.printDescription(title, description);
	}

	public static String arrayToString(Integer[] a) {
		String string = "{ ";
		String prefix = "";
		for (int i = 0; i < a.length; i++) {
			string += prefix + a[i].toString();
			prefix = ", ";
		}

		string += " }";
		return string;
	}

	/**
	 * 
	 * @author Pat Morin
	 * @author Eric Dunbar (modified by)
	 * @date Aug 26, 2016 (modified date)
	 * @title Merge Sort
	 * @assignment 3
	 *
	 * @param array array to be sorted
	 */
	public static void mergeSort(Integer[] array, int depth) {
		if (array.length <= 1) {
			System.out.printf("        RETURN because array = %s has length = %d         (d = %d)%n", arrayToString(array),
					array.length, depth);
			return;
		}
		depth++;
		Integer[] a0 = Arrays.copyOfRange(array, 0, array.length / 2);
		Integer[] a1 = Arrays.copyOfRange(array, array.length / 2, array.length);

		System.out.printf("Subdivide lower: a0 = %-13s; a[0..%d]; a0.length = %s (d = %d)%n",
				arrayToString(a0), array.length / 2 - 1, a0.length, depth); 
		
		mergeSort(a0, depth);

		System.out.printf("Subdivide UPPER: a1 = %-13s; a[%d..%d]; a1.length = %s (d = %d)%n",
				arrayToString(a1), array.length / 2, array.length - 1, a1.length, depth); 

		mergeSort(a1, depth);
		merge(a0, a1, array, depth);
		System.out.printf("        RETURN to less deep recursion of mergeSort because mergeSort is done (d = %d)%n", depth);
	}

	protected static void merge(Integer[] a0, Integer[] a1, Integer[] a, int depth) {
		int i0 = 0, i1 = 0;
		for (int i = 0; i < a.length; i++) {
			if (i0 == a0.length)
				a[i] = a1[i1++];
			else if (i1 == a1.length)
				a[i] = a0[i0++];
			else if (Integer.compare(a0[i0], a1[i1]) < 0)
				a[i] = a0[i0++];
			else
				a[i] = a1[i1++];
		}
		System.out.printf("    MERGE and sort a0 = %s and a1 = %s to a = %s  (d = %d)%n", arrayToString(a0),
				arrayToString(a1), arrayToString(a), depth);
	}

	/**
	 * Illustrate steps in MergeSort
	 * 
	 * @author Eric Dunbar
	 * @date Aug 26, 2016
	 * @title
	 * @assignment 2
	 *
	 */
	private static void runMergeSort() {
		Integer sequence[] = A3Q4a_MergeSort.sequence;
		System.out.println();
		System.out.println("Unsorted sequence: " + Arrays.toString(sequence));
		System.out.println();

		System.out.printf(
				"%d) mergeSort:                      (array, 0, array.length) = (array, 0, %d)%n",
				0, sequence.length);

		mergeSort(sequence, 0);

		System.out.println();
		System.out.println("  Sorted sequence: " + Arrays.toString(sequence));
		System.out.println();
	}

	/**
	 * @author Eric Dunbar
	 * @date Aug 26, 2016
	 * @title Illustrate sorting
	 * @assignment 3
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		CommonSuite.commonProgramStart(3, 4, "Sorting Illustration", false);

		displayQuestion();

		runMergeSort();
	}

}
