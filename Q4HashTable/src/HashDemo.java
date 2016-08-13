import java.util.HashSet;
import java.util.Random;
import java.util.Set;

//public class LinearHashTable<T> implements USet<T> {

public class HashDemo<T> extends LLinearHashTable<T> implements USet<T> {

	private static boolean testing = false;
	static TestSuite theTester;

	public static void printTheory() {
		String title = "HASH HASH HASH";
		String[] details = { "Hash Hash Hash", "Hash Hash Hash" };
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

	/*@formatter:off
	 * Since the HASH is K mod 13 redesign the resize() method.
	 * 
	 * Tasks:
	 * 
	 * 1. hash(T x) needs to:
	 * (a) take the value, perform % 13 on it (perhaps using Math.mod?)
	 * (b) multiply the result by an instance variable factor, factor
	 * (c) return the modified value
	 * 
	 * 2. resize() needs to:
	 * (a) ensure that the array is larger than a multiple of 13
	 * (b) update the multiple
	 * 
	 * 3. WHAT METHOD INITIALIZES MULTIPLE? Constructor! And resize() updates.
	 * 
	 * 4. INSTANCE variables:
	 * (a) final int divisor = 13; // initialized by constructor
	 * (b) private int multiple; // initialized by?, updated by resize()
	 * 
	 * 5. resize() calls by add(T x) and remove(T x) need to:
	 * (a) be rewritten to change the size check to handle the current factor 
	 * of 13 rather than what's current in use
	 * add(T x) is OK. resizes() when > 50% occupancy
	 * remove(T x) is OK with caveats. Unnecessary resize() calls when
	 *   n < 2 and remove is called.
	 * 
	 * 6. rewrite clear() to correctly size the backing array
	 * 
	 * @formatter:on
	 */

	// INSTANCE FIELDS

	private int factor;// by how much does hash need to be multiplied
	private final int divisor; // the divisor for the HASH table

	// CONSTRUCTORS

	public HashDemo(T nil) {
		super(nil);
		System.out.println("   I'm the new constructor");
		factor = 1;
		divisor = 13;

		// set the initial array size
		clear();
	}

	// INSTANCE METHODS

	/**
	 * Resize the backing array to have size 2^d. Warning: 2^d should be much
	 * bigger than n. Modified by Eric Dunbar from resize() from ODS by Pat
	 * Morin.
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
	 * @return return ((x.hashCode() mod 13) mod 2^w) div 2^(w-d)
	 */
	protected int xhash(T x) {
		System.out.println("hashing it out");
		int h = x.hashCode() % divisor;

		//@formatter:off
		int r1 = (tab[0][h & 0xff]
				^ tab[1][(h >>> 8) & 0xff] 
				^ tab[2][(h >>> 16) & 0xff]
				^ tab[3][(h >>> 24) & 0xff]);
		int r = r1
				>>> (w - d); // unsigned right shift limits index values to 2^d
		//@formatter:on

		System.out.println("     hash(" + x + ") = " + r + "; x % 13 = " + h
				+ "; computation: r1 = " + r1 + "; (w-d) = (" + w + " - " + d + ") = " + (w - d));
		return r;
	}

	@Override
	/**
	 * Generates a hash value using a multiple of mod 13.
	 * 
	 * @author Eric Dunbar
	 * @param x data value for which hash is to be generated
	 * @return hash value
	 */
	public int hash(T x) {
		int v = x.hashCode();
		int r = ((v % divisor + divisor) % divisor) * factor;
		System.out.println(".............. hash(" + x + ") = " + r);
		for (int i = 0; i < t.length; i++) {
			System.out.print(t[i] + ", ");
			;
		}
		System.out.println();
		return r;
	}

	@Override
	public void clear() {
		n = 0;
		q = 0;
		factor = 1;
		t = f.newArray(factor * divisor);
	}

	// PROGRAM CODE

	/**
	 * 
	 */
	public static void question4() {
		Integer q4[] = { 39, 20, 1, 5, 21, 26, 39, 14, 15, 16, 17, 18, 19, 20, 111, 145, 146 };
		USet<Integer> h = new HashDemo<Integer>(-99);

		for (int i = 0; i < q4.length; i++) {
			System.out.print("add   ");
			h.add(q4[i]);
			int x = LLinearHashTable.tab[1][1];
		}

		for (int i = 0; i < q4.length; i++) {
			System.out.println();
		}
	}

	public static void odsDemo() {
		Random rand = new Random(1);
		USet<Integer> lht = new HashDemo<Integer>(-1);
		Set<Integer> s = new HashSet<Integer>();
		int n = 1000000;
		System.out.println("Adding");
		for (int i = 0; i < n; i++) {
			Integer x = rand.nextInt(n);
			boolean rs = s.add(x);
			boolean rlht = lht.add(x);
			if (rs != rlht)
				throw new RuntimeException("Aaaaaaaaaaaaaaaah!");
			if (s.size() != lht.size()) {
				System.out.println(s.size());
				System.out.println(lht.size());
				throw new RuntimeException("Bwaaaaaaaaaaaaaaah!");
			}
		}
		System.out.println("Searching");
		for (int i = 0; i < n; i++) {
			Integer x = rand.nextInt(n);
			boolean rs = s.contains(x);
			boolean rlht = lht.find(x) != null;
			if (rs != rlht)
				throw new RuntimeException("Aaaaaaaaaaaaaaaah!");
			if (s.size() != lht.size())
				throw new RuntimeException("Bwaaaaaaaaaaaaaaah!");
		}

		System.out.println("Removing");
		for (int i = 0; i < n; i++) {
			Integer x = rand.nextInt(n);
			boolean rs = s.remove(x);
			boolean rlht = lht.remove(x) != null;
			if (rs != rlht)
				throw new RuntimeException("Aaaaaaaaaaaaaaaah!");
			if (s.size() != lht.size())
				throw new RuntimeException("Bwaaaaaaaaaaaaaaah!");
		}
		s.clear();
		lht.clear();
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
