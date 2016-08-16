import java.util.ArrayList;
import java.util.Comparator;

/**
 * A a test to confirm that a binary search tree correctly implements the binary
 * search tree property as described in ODS by Pat Morin on page 140. A
 * BinarySearchTree is a type of binary tree in which each node, u, stores a
 * data value, u.x. The data values in a binary search tree obey the binary
 * search tree property: for a node, u, every data value stored in the subtree
 * rooted at u.left is less than u.x and every data value stored in the subtree
 * rooted at u.right is greater than u.x.
 * 
 * @author Eric Dunbar
 * @date 11/8/16
 * @assignment 2
 * @question 2
 * @title Binary Search Tree property demonstration
 *
 * @param <T> The class type of BST
 */
public class Q2BSTPropertyDemo<T> extends BinarySearchTree<Q2BSTPropertyDemo.Node<T>, T> {

	/**
	 * Creates instance of class given a sample node used to create new nodes, a
	 * nil node to define the undefined condition (distinct from null) and a
	 * comparator object (of type DefaultComparator by Pat Morin) to perform
	 * comparisons. Based off code by Pat Morin in ODS.
	 * 
	 * @param sampleNode
	 * @param nil
	 * @param c
	 */
	public Q2BSTPropertyDemo(Node<T> sampleNode, Node<T> nil, Comparator<T> c) {
		super(sampleNode, nil, c);
	}

	/**
	 * Displays a description of the binary search tree property from Open Data
	 * Structures by Pat Morin.
	 */
	public static void printTheory() {
		String title = "BinarySearchTree: An Unbalanced Binary Search Tree (ODS 6.2)";
		String[] details = {
				"A BinarySearchTree is a special kind of binary tree in which each node,",
				"u, also stores a data value, u.x, from some total order. The data values",
				"in a binary search tree obey the binary search tree property: For a node,",
				"u, every data value stored in the subtree rooted at u.left is less than",
				"u.x and every data value stored in the subtree rooted at u.right is",
				"greater than u.x." };
		CommonSuite.printDescription(title, details);

		System.out.println();
		title = "Searching (ODS 6.2.1)";
		details = new String[] {
				"The binary search tree property allows us to quickly locate a value, x, ",
				"in a binary search tree. To do this we start searching for x at the root, ",
				"r. When examining a node, u, there are three cases:", "",
				"1. If x < u.x, then the search proceeds to u.left;",
				"2. If x > u.x, then the search proceeds to u.right;",
				"3. If x = u.x, then we have found the node u containing x.", "",
				"The search terminates when Case 3 occurs or when u = nil. In the former",
				"case, we found x. In the latter case, we conclude that x is not in the",
				"binary search tree." };
		CommonSuite.printDescription(title, details);
	}

	/**
	 * Displays the question text for assignment 2, question 2 from COMP272.
	 */
	public static void printQuestion() {
		String title = "Question 2. Confirm search tree order property";
		String[] details = {
				"2. (25 marks) Design a recursive linear-time algorithm that tests whether",
				"a binary tree satisfies the search tree order property at every node." };
		CommonSuite.printDescription(title, details);
	}

	/*
	 * Traversing through all nodes of a binary tree in Java. (2013, March 9).
	 * Retrieved August 05, 2016, from
	 * http://stackoverflow.com/questions/15306452/traversing-through-all-nodes-
	 * of-a-binary-tree-in-java
	 */

	/**
	 * Class required to implement the binary search tree from ODS by Pat Morin.
	 * 
	 * @author Eric Dunbar
	 *
	 * @param <T>
	 */
	protected static class Node<T> extends BinarySearchTree.BSTNode<Node<T>, T> {
	}

	/**
	 * Tests whether a binary tree satisfies the "search tree order property"
	 * (ODS by Pat Morin, binary search tree property, pg. 140) at every node.
	 * For each node u, every data value stored in the subtree rooted at u.left
	 * is less than u.x and every data value stored in the subtree rooted at
	 * u.right is greater than u.x. ANSWER TO ASSIGNMENT 2, QUESTION 2.
	 * 
	 * @author Eric Dunbar
	 * @param n root Node for sub-tree to be tested.
	 * @return whether the binary search tree property is implemented properly
	 */
	public boolean isValidSearchTreeOrderProperty(Node<T> n) {
		boolean testB = true; // a leaf complies with the property
		if (n.left != nil) {
			testB = testB && (c.compare(n.x, n.left.x) > 0);
			testB = testB && isValidSearchTreeOrderProperty(n.left);
		}
		if (n.right != nil) {
			testB = testB && (c.compare(n.x, n.right.x) < 0);
			testB = testB && isValidSearchTreeOrderProperty(n.right);
		}
		return testB;
	}

	// DEMONSTRATION CODE STARTS
	// DEMONSTRATION CODE STARTS
	// DEMONSTRATION CODE STARTS
	// DEMONSTRATION CODE STARTS
	// DEMONSTRATION CODE STARTS

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
	 * Print the binary search sub-tree starting at the given Node.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 */
	private void printBSTPTree(Node<T> u) {
		pT = new ArrayList<ArrayList<T>>();

		int n = 0;
		constructBSTPTree(u, n);

		System.out.println();
		System.out.println("CAUTION: horizontal positions are correct only relative to each other");

		for (int i = 0; i < pT.size(); i++) {
			int theSize = pT.get(i).size();
			int baseWidth = 90;
			String thePadding = CommonSuite.stringRepeat(" ",
					(int) ((baseWidth - 3 * theSize) / (theSize + 1)));
			for (int j = 0; j < theSize; j++)
				System.out.printf("%s%3s", thePadding, pT.get(i).get(j));
			System.out.println();
		}
	}

	/**
	 * Helper method to collect information to print the binary search tree.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 * @param n starting height (0 = root)
	 */
	private void constructBSTPTree(Node<T> u, int n) {
		n++;
		addToPT(n, u.x);
		System.out.printf("%d: %s ", n, u.x);
		if (u.left != nil)
			constructBSTPTree(u.left, n);
		if (u.right != nil)
			constructBSTPTree(u.right, n);
	}

	/**
	 * Builds a balanced BinarySearchTree<Integer> given the minimum and maximum
	 * bounds.
	 * 
	 * @param ib pointer to BSTProperty that is to be filled
	 * @param min lower limit data elements
	 * @param max upper limit of data elements
	 */
	private static void buildBalanced(Q2BSTPropertyDemo<Integer> ib, int min, int max) {
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
	public static boolean printIsValidBST(Q2BSTPropertyDemo<Integer> ib) {
		Node<Integer> my = ib.r;
		boolean validBST = ib.isValidSearchTreeOrderProperty(ib.r);

		System.out.printf("This tree %s obey the binary search tree property.%n",
				validBST ? "does" : "does NOT");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();
		return validBST;
	}

	public static void performThreeTests(Q2BSTPropertyDemo<Integer> ib, String[] text) {
		CommonSuite.printSuperFancyHeader("NEW TEST: " + text[0]);
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
		Q2BSTPropertyDemo<Integer> bt, ibt, mt, ut;

		// CONSTRUCT THE BST

		bt = new Q2BSTPropertyDemo<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		ibt = new Q2BSTPropertyDemo<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		mt = new Q2BSTPropertyDemo<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		ut = new Q2BSTPropertyDemo<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		// A balanced tree
		int balMin = 1;
		int balMax = 14;
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

		// An unbalanced tree
		base = 2;
		exp = 3;
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

	private static boolean testing = false;
	static TestSuite theTester;

	public static void main(String[] args) {
		// Display programmer info and create testing object
		theTester = CommonSuite.commonProgramStart(2, 2, "Binary Search Tree Property Demo",
				testing);

		printTheory();
		System.out.println();
		printQuestion();
		runBinarySearchTreePropertyTest();

		CommonSuite.commonProgramEnd(theTester);
	}
}
