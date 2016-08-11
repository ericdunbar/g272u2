import java.util.ArrayList;
import java.util.Comparator;

import BSTProperty.Node;

public class BSTPropertyDemo<T> extends BSTProperty<T> {

	public BSTPropertyDemo(BSTProperty.Node<T> sampleNode, BSTProperty.Node<T> nil,
			Comparator<T> c) {
		super(sampleNode, nil, c);
	}

	/**
	 * Tracks height and data for each node in the tree for printing purposes.
	 */
	private ArrayList<ArrayList<T>> pT;

	/**
	 * Add data to a data structure that tracks elements and ranks (height) of
	 * the Nodes in the BinarySearchTree. This can be used to display the tree.
	 * 
	 * @param n height with 0 being root
	 * @param x data element
	 */
	private void addToPT(int n, T x) {
		while (pT.size() < n + 1)
			pT.add(new ArrayList<T>());
		pT.get(n).add(x);
	}

	/**
	 * Helper method to collect information to print the binary search tree.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 * @param n starting height (0 = root)
	 */
	private void constructBSTPTree(BSTProperty.Node<T> u, int n) {
		n++;
		addToPT(n, u.x);
		System.out.printf("%d: %s ", n, u.x);
		if (u.left != nil)
			constructBSTPTree(u.left, n);
		if (u.right != nil)
			constructBSTPTree(u.right, n);
	}

	
	/**
	 * Print the binary search sub-tree starting at the given Node.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 */
	private void printBSTPTree(BSTProperty.Node<T> u) {
		pT = new ArrayList<ArrayList<T>>();

		int n = 0;
		constructBSTPTree(u, n);

		System.out.println();
		System.out.println(
				"CAUTION: slightly valid relative horizontal positions displayed only for a balanced tree");

		for (int i = 0; i < pT.size(); i++) {
			int theSize = pT.get(i).size();
			int baseWidth = 100;
			String thePadding = CommonSuite.stringRepeat(" ",
					(int) ((baseWidth - 3 * theSize) / (theSize + 1)));
			// System.out.printf("%2d", theSize); // display number of elements
			// at this height
			for (int j = 0; j < theSize; j++)
				System.out.printf("%s%3s", thePadding, pT.get(i).get(j));
			System.out.println();
		}
	}

	/**
	 * Builds a balanced BinarySearchTree<Integer> given the minimum and maximum
	 * bounds.
	 * 
	 * @param ib pointer to BSTProperty that is to be filled
	 * @param min lower limit data elements
	 * @param max upper limit of data elements
	 */
	private static void buildBalanced(BSTProperty<Integer> ib, int min, int max) {
		int mid = (min + max) / 2;
		ib.add(mid);
		if (min < max) {
			buildBalanced(ib, min, mid - 1);
			buildBalanced(ib, mid + 1, max);
		}
	}

	/**
	 * Test whether a binary search tree conforms to the 'binary search tree
	 * property' as described in ODS by Pat Morin on page 140. The results of
	 * the test are printed and a boolean is returned.
	 * 
	 * @param ib binary search tree
	 * @return whether the binary search tree conforms to the binary search tree
	 *         property
	 */
	public static boolean printIsValidBST(BSTProperty<Integer> ib) {
		@SuppressWarnings("unchecked")
		BSTProperty.Node<Integer> my = (BSTProperty.Node<Integer>) ib.r;
		boolean validBST = ib.isValidSearchTreeOrderProperty(ib.r);

		System.out.printf("This tree %s obey the binary search tree property.%n",
				validBST ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();
		return validBST;
	}

	public static void performThreeTests(BSTProperty<Integer> ib, String[] text) {

		CommonSuite.printFancyHeader("NEW TEST: " + text[0]);
		System.out.println();
		CommonSuite.printFullDescription(text);

		CommonSuite.printFancyHeader("Construct a BinarySearchTree");

		// THE ACTUAL QUESTION 2 TEST
		printIsValidBST(ib);

		// SWAP DATA AND DON'T BOTHER CHECK FOR VALID NODES :)
		CommonSuite.printFancyHeader("Modify the BinarySearchTree by switching elements");

		Node<Integer> n = ib.r.left;

		if (ib.r.left != ib.nil)
			n = ib.r.left;
		else
			n = ib.r.right;

		Integer rootSwap;
		rootSwap = ib.r.x;
		ib.r.x = n.x;
		n.x = rootSwap;

		// THE ACTUAL QUESTION 2 TEST
		printIsValidBST(ib);

		// SWAP DATA BACK AND DON'T BOTHER CHECK FOR VALID NODES :)
		CommonSuite.printFancyHeader("Reverse changes to BinarySearchTree");
		n.x = ib.r.x;
		ib.r.x = rootSwap;

		// THE ACTUAL QUESTION 2 TEST
		printIsValidBST(ib);
	}

	/**
	 * Demonstrate the binary search tree property test.
	 */
	private static void runBinarySearchTreePropertyTest() {
		BSTProperty<Integer> bt, ibt, mt, ut;

		// CONSTRUCT THE BST

		bt = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		ibt = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		mt = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		ut = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		// A balanced tree
		int balMin = 1;
		int balMax = 31;
		buildBalanced(bt, balMin, balMax);

		String[] description = { "AN EFFICIENT BALANCED TREE",
				"A balanced tree is subjected to the Binary Search Tree Property test. ", "",
				"The minimum element starts at " + balMin + " and maxes out at " + balMax + ".", "",
				"Two data elements are swapped between nodes. This causes the BSTP test ",
				"to fail since one or more of the following conditions is no longer valid: ",
				"1. u.left.x < u.x,; or", "2. u.right.x > u.x.", "" };
		performThreeTests(bt, description);

		// An inefficient balanced tree
		int base = 2;
		int exp = 4;
		ibt.add((int) (Math.pow(base, exp) / 2));
		for (int j = 1; j < Math.pow(base, exp); j++)
			ibt.add(j);

		description = new String[] { "AN INEFFICIENT BALANCED TREE",
				"A balanced tree is subjected to the Binary Search Tree Property test. ", "",
				"The minimum element starts at " + 1 + " and maxes out at "
						+ (int) Math.pow(base, exp) + ".",
				"", "Two data elements are swapped between nodes. This causes the BSTP test ",
				"to fail since one or more of the following conditions is no longer valid: ",
				"1. u.left.x < u.x,; or", "2. u.right.x > u.x.", "" };
		performThreeTests(ibt, description);

		// A manually built tree
		for (Integer integer : new Integer[] { 4, 2, 3, 1, 6, 5, 7 })
			mt.add(integer);

		description = new String[] { "A MANUALLY BUILT TREE",
				"A binary search tree is subjected to the Binary Search Tree Property test. ", "",
				"Two data elements are swapped between nodes. This causes the BSTP test ",
				"to fail since one or more of the following conditions is no longer valid: ",
				"1. u.left.x < u.x,; or", "2. u.right.x > u.x.", "" };
		performThreeTests(mt, description);

		// An inefficient balanced tree
		base = 2;
		exp = 6;
		for (int j = 1; j < Math.pow(base, exp); j++)
			ut.add(j);

		description = new String[] { "AN UNBALANCED TREE",
				"An unbalanced tree is subjected to the Binary Search Tree Property test. ", "",
				"The minimum element starts at " + 1 + " and maxes out at "
						+ (int) Math.pow(base, exp) + ".",
				"", "Two data elements are swapped between nodes. This causes the BSTP test ",
				"to fail since one or more of the following conditions is no longer valid: ",
				"1. u.left.x < u.x,; or", "2. u.right.x > u.x.", "" };
		performThreeTests(ut, description);

		System.out.println();
		System.out.println("DONE");
	}

	public static void main(String[] args) {
		printTheory();
		System.out.println();
		printQuestion();
		runBinarySearchTreePropertyTest();
	}
}
