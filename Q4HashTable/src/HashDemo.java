/**
 * A hash table that handles collision using linear probing, using (K mod 13) as
 * the hash function. Modified from LinearHashTable by Pat Morin.
 * 
 * @author Eric Dunbar
 * @date Aug 15, 2016
 * @assignment 2
 *
 * @param <T> data type
 */
public class HashDemo<T> extends LinearHashTable<T> implements USet<T> {

	private static boolean testing = false;
	static TestSuite theTester;

	public static void printTheory() {
		String title = "LinearHashTable: Linear Probing (5.2)";
		String[] details = { "Open addressing stores elements directly in an array, t, with each",
				"array location in t storing at most one value.", "",
				"Ideally store the element x with hash value i = hash(x) in table",
				"location t[i]. If that location is occupied then try to store it",
				"at location t[(i + 1) mod t.length]; if that’s not possible, then",
				"try t[(i+2) mod t.length], until an unused location is found.", "",
				"Three types of entries are stored in t:",
				"1. data values: actual values in the USet that we are representing;",
				"2. null values: at array locations where no data has ever been stored; and",
				"3. del values: at array locations where data has been deleted", "",
				"The counter, n, keeps track of the number of elements in the linear",
				"hash table. q keeps track of the number of elements of Types 1 and 3." };
		CommonSuite.printDescription(title, details);

		System.out.println();

		title = "Question 4. Hash Table with linear probing.";
		details = new String[] {
				"4. (20 marks) Implement a commonly used hash table in a program that",
				"handles collision using linear probing. Using (K mod 13) as the hash",
				"function, store the following elements in the table: {1, 5, 21, 26, 39,",
				"14, 15, 16, 17, 18, 19, 20, 111, 145, 146}." };
		CommonSuite.printDescription(title, details);

		System.out.println();
	}

	/*
	 * Tasks:
	 * 
	 * 1. hash(T x) needs to: (a) take the value, perform % 13 on it (perhaps
	 * using Math.mod?) (b) multiply the result by an instance variable factor,
	 * factor (c) return the modified value
	 * 
	 * 2. resize() needs to: (a) ensure that the array is larger than a multiple
	 * of 13 (b) update the multiple
	 * 
	 * 3. WHAT METHOD INITIALIZES MULTIPLE? Constructor! And resize() updates.
	 * 
	 * 4. INSTANCE variables: (a) final int divisor = 13; // initialized by
	 * constructor (b) private int multiple; // initialized by? updated by
	 * resize()
	 * 
	 * 5. resize() calls by add(T x) and remove(T x) need to: (a) be rewritten
	 * to change the size check to handle the current factor of 13 rather than
	 * what's current in use add(T x) is OK. resizes() when > 50% occupancy
	 * remove(T x) is OK with caveats. Unnecessary resize() calls when n < 2 and
	 * remove is called.
	 * 
	 * 6. rewrite clear() to correctly size the backing array
	 */

	// INSTANCE FIELDS

	/**
	 * By how much does the hash need to be multiplied if many elements are to
	 * be stored in the hash table.
	 */
	private int factor;

	/**
	 * The divisor for the hash table. Default is initialized to 13 by
	 * constructor but this can be changed.
	 */
	private final int divisor;

	// CONSTRUCTORS

	/**
	 * Create an instance of a hash table that use K Mod 13 as the hash
	 * function.
	 * 
	 * @param nil an element that is used as the nil value. Programmer
	 *            responsible for making sure it is not be used.
	 */
	public HashDemo(T nil) {
		super(nil);
		factor = 1;
		divisor = 13;

		// override the initial array size
		clear();
	}

	// INSTANCE METHODS

	/**
	 * Resize the backing array to have size that is a multiple of 13 that is >=
	 * 3 * n. Modified by Eric Dunbar from resize() from ODS by Pat Morin.
	 */
	@Override
	protected void resize() {
		// determine correct size for new array
		// modified by Eric Dunbar
		factor = 1;
		int minimum = Math.max(13, 3 * n); // minimum array size
		while ((factor * divisor) < minimum)
			factor++;

		T[] told = t;
		t = f.newArray(divisor * factor);
		q = n;
		// insert everything from told
		for (int k = 0; k < told.length; k++) {
			if (told[k] != null && told[k] != del) {
				int i = hash(told[k]);
				while (t[i] != null)
					i = (i == t.length - 1) ? 0 : i + 1;
				t[i] = told[k];
			}
		}

	}

	/**
	 * Experimental. Not for use.
	 * 
	 * @return ((x.hashCode() mod 13) mod 2^w) div 2^(w-d)
	 */
	protected int xhash(T x) {
		System.out.println("hashing it out");
		int h = x.hashCode() % divisor;

		int r1 = (tab[0][h & 0xff] ^ tab[1][(h >>> 8) & 0xff] ^ tab[2][(h >>> 16) & 0xff]
				^ tab[3][(h >>> 24) & 0xff]);
		int r = r1 >>> (w - d); // unsigned right shift limits index values to
								// 2^d

		System.out.println("     hash(" + x + ") = " + r + "; x % 13 = " + h
				+ "; computation: r1 = " + r1 + "; (w-d) = (" + w + " - " + d + ") = " + (w - d));
		return r;
	}

	@Override
	/**
	 * Generates a hash value using x mod 13. If more elements are to be stored
	 * than half the array size, then a scaling factor is applied to the result
	 * of the mod 13 calculation.
	 * 
	 * @author Eric Dunbar
	 * @param x data value for which hash is to be generated
	 * @return hash value
	 */
	public int hash(T x) {
		int v = x.hashCode();
		int r = ((v % divisor + divisor) % divisor) * factor;
		return r;
	}

	/**
	 * Clears a hash table and resets its size and other parameters to the
	 * starting condition.
	 */
	@Override
	public void clear() {
		n = 0;
		q = 0;
		factor = 1;
		t = f.newArray(factor * divisor);
	}

	// PROGRAM CODE

	/**
	 * Demonstration of question 4, hash table with a hash(x) = K Mod 13
	 * 
	 * @author Eric Dunbar
	 * @date Aug 15, 2016
	 * @assignment 2
	 *
	 */
	public static void question4() {
		Integer q4[] = { 1, 5, 21, 26, 39, 14, 15, 16, 17, 18, 19, 20, 111, 145, 146 };

		// why USet instead of HashDemo? Or LinearHashTable?
		USet<Integer> h = new HashDemo<Integer>(-99);

		for (int i = 0; i < q4.length; i++) {
			h.add(q4[i]);
		}
		printHashTable((HashDemo<Integer>) h);

		int largeV = 234252;

		System.out.println();

		String title = "Add a single value to the hash table.";
		String details[] = new String[] {
				"Add " + largeV + " to the hash table. Notice that (" + largeV + " mod 13) * 3 = ",
				(largeV % 13 + 13) % 13 * 3
						+ " and should appear immediately after two elements with the same ",
				"modulo 13 result (5*3) in index 17." };
		CommonSuite.printDescription(title, details);

		System.out.println();

		for (int i = 88; i < 200_000; i += 2083) {
			h.add(largeV);
		}
		printHashTable((HashDemo<Integer>) h);

		System.out.println();

		title = "Add a series to hash table increasing by 13^3.";
		details = new String[] {
				"Add a series to the hash table, starting at 88 and increasing by 13^3.",
				"All elements in the series have 10 as the modulo (~remainder) which is why",
				"the elements have all been added to the table starting at index 180 and",
				"wrapping around to 0." };
		CommonSuite.printDescription(title, details);

		System.out.println();

		for (int i = 88; i < 200_000; i += Math.pow(13, 3)) {
			h.add(i);
		}
		printHashTable((HashDemo<Integer>) h);
	}

	/**
	 * Prints Integers stored in a K Mod 13 hash table. Requires a terminal with
	 * a width of at least 120. Can comfortably handle values up to 999_999.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 15, 2016
	 *
	 * @param h hash table to display
	 */
	public static void printHashTable(HashDemo<Integer> h) {
		System.out.println();
		System.out.println(CommonSuite.stringRepeat("_", 12) + "Thirteen position"
				+ CommonSuite.stringRepeat("_", 97));
		System.out.print("_Index_||");
		for (int i = 0; i < 13; i++) {
			System.out.printf("___%2d___|", i);
		}

		for (int i = 0; i < h.t.length; i++) {
			if (i % 13 == 0) {
				System.out.println();
				System.out.printf("%6d ||", i);
			}
			Integer valueE = h.t[i];
			String value = (valueE == h.del || valueE == null ? "" : valueE.toString());
			System.out.printf(" %6s |", value);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		// Display programmer info and create testing object
		theTester = CommonSuite.commonProgramStart(2, 4, "Linear hash table with collision",
				testing);

		printTheory();

		question4();

		CommonSuite.commonProgramEnd(theTester);
	}

}
