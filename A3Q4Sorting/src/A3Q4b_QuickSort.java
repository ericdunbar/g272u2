import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * @author Eric Dunbar
 * @date Aug 26, 2016
 * @title
 * @assignment 3
 *
 */
public class A3Q4b_QuickSort {

	protected static Random rand = new Random();

	/**
	 * 
	 * @author Pat Morin (original)
	 * @author Eric Dunbar (modified)
	 * @param <T>
	 * @date Aug 27, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param a
	 * @param i
	 * @param n
	 */
	public static <T> void quickSort(T[] a, int i, int n, DefaultComparator<T> c) {
		if (n <= 1)
			return;
		T x = a[i + rand.nextInt(n)];
		int p = i - 1, j = i, q = i + n;

		while (j < q) {
			int comp = c.compare(a[j], x);
			if (comp < 0) { // move to beginning of array
				swap(a, j++, ++p);
			} else if (comp > 0) {
				swap(a, j, --q); // move to end of array
			} else {
				j++; // keep in the middle
			}
		}
		quickSort(a, i, p - i + 1, c);
		quickSort(a, q, n - (q - i), c);
	}

	/**
	 * Integer version of quickSort. Simplifies code slightly and simplifies
	 * illustration code for assignment.
	 * 
	 * @author Pat Morin (original)
	 * @author Eric Dunbar (modified)
	 * @date Aug 27, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param array array to sort
	 * @param start
	 * @param length
	 */
	public static void quickSort(Integer array[], int start, int length, int depth) {
		depth++;
		System.out.println();
		System.out.printf("Quick sort, recursion %d%n", depth);
		String sIndent = "    ";
		String dIndent = sIndent + sIndent;
		String triIndent = sIndent + sIndent + sIndent;

		if (length <= 1) {
			System.out.printf("%sstart = %2d, end = %2d%n", dIndent, start, start + length);
			return;
		}
		Integer pivot = array[start + rand.nextInt(length)];
		Integer belowPivotIdx = start - 1;

		System.out.printf("%sRandomly choose %d as the pivot for indices [%s..%s] in %s.%n", sIndent,
				pivot, start, start + length - 1,
				threeRangesArrayToString(array, start, start + length - 1));

		Integer j = start;
		Integer abovePivotIdx = start + length;

		while (j < abovePivotIdx) {
			int comp = Integer.compare(array[j], pivot);
			if (comp < 0) { // move to beginning of array
				{
					swap(array, j++, ++belowPivotIdx);
					System.out.printf(
							"%sMove %d to belowPivotIndex from [%d] to [%2d] because %d < pivot, belowPivotIndex++ to %s%n",
							triIndent, array[belowPivotIdx], j - 1, belowPivotIdx,
							array[belowPivotIdx], belowPivotIdx);
				}
			} else if (comp > 0) {
				swap(array, j, --abovePivotIdx); // move to end of array
				System.out.printf(
						"%sMove %d to abovePivotIndex from [%d] to [%2d] because %d > pivot, abovePivotIndex-- to %s%n",
						triIndent, array[abovePivotIdx], j, abovePivotIdx, array[abovePivotIdx],
						abovePivotIdx);
			} else {
				j++; // keep in the middle
				System.out.printf("%sKeep %d at current index [%d], j++%n", triIndent, array[j - 1],
						j - 1);
			}
		}

		System.out.println();
		System.out.printf("%sNow indices [%d..%d] are sorted to %s%n", 
				sIndent,
				start,
				start + length - 1,
				threeRangesArrayToString(array, start, start + length - 1));

		System.out.printf("%saround pivot %d: %s%n", sIndent,
				pivot,
				threeRangesArrayToString(array, belowPivotIdx+1, abovePivotIdx-1));
		System.out.println();
		
		System.out.printf("%sCall quickSort(array, start=%d, length=%d) and quickSort(array, start=%d, length=%d)%n",sIndent,
				start, belowPivotIdx - start + 1, abovePivotIdx, length - (abovePivotIdx - start));
		quickSort(array, start, belowPivotIdx - start + 1, depth);
		quickSort(array, abovePivotIdx, length - (abovePivotIdx - start), depth);
	}

	/**
	 * 
	 * @author Pat Morin
	 * @date n/a
	 * @title swaps two elements
	 *
	 * @param a
	 * @param i
	 * @param j
	 */
	protected final static <T> void swap(T[] a, int i, int j) {
		T t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

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

				"    b. (8 marks) quick-sort algorithm. Choose a partitioning strategy you",
				"       like to pick a pivot element from the sequence. Analyze how different",
				"       portioning strategies may impact on the performance of the sorting",
				"       algorithm." };
		CommonSuite.printDescription(title, description);
	}

	/**
	 * Converts an array of Integers to a string using braces at the ends.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 27, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param a array to convert
	 * @return
	 */
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
	 * Converts an array of Integers to a string using braces at the ends.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 27, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param a array to convert
	 * @return
	 */
	public static String pivotArrayToString(Integer[] a, Integer pivotIdx) {
		String string = "{ ";
		String prefix = "";
		for (int i = 0; i < a.length; i++) {
			if (i != pivotIdx) {
				string += prefix + a[i].toString();
				prefix = ", ";
			} else {
				string += prefix + a[i].toString();
				prefix = ",        ";
			}
		}

		string += " }";
		return string;
	}

	/**
	 * Converts an array of Integers to a string using braces at the ends.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 27, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param a array to convert
	 * @return
	 */
	public static String threeRangesArrayToString(Integer[] a, Integer lowerIdx, Integer upperIdx) {
		String string = "{ ";
		String prefix = "";
		for (int i = 0; i < a.length; i++) {
			if (i != lowerIdx - 1 && i != upperIdx) {
				string += prefix + a[i].toString();
				prefix = ", ";
			} else {
				string += prefix + a[i].toString();
				prefix = ",    ";
			}
		}

		string += " }";
		return string;
	}

	/**
	 * Illustrate steps in MergeSort
	 * 
	 * @author Eric Dunbar
	 * @date Aug 26, 2016
	 * @title
	 * @assignment 3
	 *
	 */
	private static void runQuickSort() {
		Integer sequence[] = A3Q4b_QuickSort.sequence;
		System.out.println();
		System.out.println("Unsorted sequence: " + Arrays.toString(sequence));
		System.out.println();

		System.out.printf("%d) quickSort(array, 0, array.length) = (array, 0, %d)%n", 0,
				sequence.length);

		quickSort(sequence, 0, sequence.length, 0);

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

		runQuickSort();
	}

}
