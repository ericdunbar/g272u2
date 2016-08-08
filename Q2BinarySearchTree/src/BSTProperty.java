import java.util.ArrayList;
import java.util.Comparator;

/**
 * A binary search tree that provides a test to confirm that the binary search
 * tree correctly implements the binary search tree property as described in ODS
 * by Pat Morin on page 140. A BinarySearchTree is a type of binary tree in
 * which each node, u, stores a data value, u.x. The data values in a binary
 * search tree obey the binary search tree property: for a node, u, every data
 * value stored in the subtree rooted at u.left is less than u.x and every data
 * value stored in the subtree rooted at u.right is greater than u.x.
 * 
 * @author Eric Dunbar
 *
 * @param <T> The class type of BST
 */
public class BSTProperty<T> extends BinarySearchTree<BSTProperty.Node<T>, T> {

	public BSTProperty(Node<T> sampleNode, Node<T> nil, Comparator<T> c) {
		super(sampleNode, nil, c);
	}

	/**
	 * Displays a description of the binary search tree property from Open Data
	 * Structures by Pat Morin.
	 */
	public static void printTheory() {
		String title = "6.2 BinarySearchTree: An Unbalanced Binary Search Tree";
		String[] details = {
				"A BinarySearchTree is a special kind of binary tree in which each node,",
				"u, also stores a data value, u.x, from some total order. The data values",
				"in a binary search tree obey the binary search tree property: For a node,",
				"u, every data value stored in the subtree rooted at u.left is less than",
				"u.x and every data value stored in the subtree rooted at u.right is",
				"greater than u.x." };
		CommonSuite.printDescription(title, details);

		System.out.println();
		title = "6.2.1 Searching";
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
	public boolean testBSTProperty(Node<T> n) {
		boolean testB = true; // a leaf complies with the property
		if (n.left != nil) {
			testB = testB && (c.compare(n.x, n.left.x) > 0);
			testB = testB && testBSTProperty(n.left);
		}
		if (n.right != nil) {
			testB = testB && (c.compare(n.x, n.right.x) < 0);
			testB = testB && testBSTProperty(n.right);
		}
		return testB;
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
		System.out.println(
				"CAUTION: slightly valid relative horizontal positions displayed only for a balanced tree");
		for (int i = 0; i < pT.size(); i++) {
			int theSize = pT.get(i).size();
			int baseWidth = 100;
			String thePadding = CommonSuite.stringRepeat(" ",
					(int) ((baseWidth - 3 * theSize) / (theSize + 1)));
			System.out.printf("%2d", theSize);
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

	private static void buildBalanced(BSTProperty<Integer> ib, int min, int max) {
		int mid = (min + max) / 2;
		ib.add(mid);
		if (min < max) {
			buildBalanced(ib, min, mid - 1);
			buildBalanced(ib, mid + 1, max);
		}
	}

	/**
	 * Demonstrate the binary search tree property test.
	 */
	private static void runBinarySearchTreePropertyTest() {
		BSTProperty<Integer> ib, jb, kb;

		// CONSTRUCT THE BST

		CommonSuite.printFancyHeader("Construct a BinarySearchTree");

		ib = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		jb = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		kb = new BSTProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		// A balanced tree
		buildBalanced(ib, 1, 31);

		// An extremely unbalanced tree
		jb.add((int) (Math.pow(2, 4) / 2));
		for (int j = 1; j < Math.pow(2, 4); j++)
			jb.add(j);

		// A manually built tree
		for (Integer integer : new Integer[] { 4, 2, 3, 1, 6, 5, 7 })
			kb.add(integer);

		Node<Integer> my = ib.r;

		// THE ACTUAL QUESTION 2 TEST
		System.out.printf("This tree %s obey the binary search tree property.%n",
				ib.testBSTProperty(ib.r) ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();

		// SWAP DATA AND DON'T BOTHER CHECK FOR VALID NODES :)
		CommonSuite.printFancyHeader("Modify the BinarySearchTree by switching elements");

		Node<Integer> n = ib.r.left;
		while (n.x == null) {
			if (n.left == ib.nil)
				n = ib.r.right;
			n = n.left;
		}

		Integer rootSwap;
		rootSwap = ib.r.x;
		ib.r.x = n.x;
		n.x = rootSwap;

		// THE ACTUAL QUESTION 2 TEST
		System.out.printf("This tree %s obey the binary search tree property.%n",
				ib.testBSTProperty(ib.r) ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();

		// SWAP DATA BACK AND DON'T BOTHER CHECK FOR VALID NODES :)
		CommonSuite.printFancyHeader("Reverse changes to BinarySearchTree");
		n.x = ib.r.x;
		ib.r.x = rootSwap;

		// THE ACTUAL QUESTION 2 TEST
		System.out.printf("This tree %s obey the binary search tree property.%n",
				ib.testBSTProperty(ib.r) ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();
	}

	public static void main(String[] args) {
		printTheory();
		System.out.println();
		printQuestion();
		runBinarySearchTreePropertyTest();
	}

}
