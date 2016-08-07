import java.util.ArrayList;
import java.util.Comparator;

/*
 * 6.2 BinarySearchTree: An Unbalanced Binary Search Tree
 * 
 * A BinarySearchTree is a special kind of binary tree in which each node,
 * u, also stores a data value, u.x, from some total order. The data values
 * in a binary search tree obey the binary search tree property: For a node,
 * u, every data value stored in the subtree rooted at u.left is less than
 * u.x and every data value stored in the subtree rooted at u.right is
 * greater than u.x.
 */

public class BinarySearchTreeProperty<T>
		extends BinarySearchTree<BinarySearchTreeProperty.Node<T>, T> {

	public BinarySearchTreeProperty(Node<T> sampleNode, Node<T> nil, Comparator<T> c) {
		super(sampleNode, nil, c);
		// TODO Auto-generated constructor stub
	}

	private static void printTheory() {
		String title = "6.2 BinarySearchTree: An Unbalanced Binary Search Tree";
		String[] details = {
				"A BinarySearchTree is a special kind of binary tree in which each node,",
				"u, also stores a data value, u.x, from some total order. The data values",
				"in a binary search tree obey the binary search tree property: For a node,",
				"u, every data value stored in the subtree rooted at u.left is less than",
				"u.x and every data value stored in the subtree rooted at u.right is",
				"greater than u.x." };
		CommonSuite.printDescription(title, details);
	}

	private static void printTheory2() {
		String title = "6.2.1 Searching";
		String[] details = {
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

	private static void printQuestion() {
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

	protected static class Node<T> extends BinarySearchTree.BSTNode<Node<T>, T> {
	}

	/**
	 * Tests whether a binary tree satisfies the "search tree order property"
	 * (binary search tree property, pg. 140???) at every node. For each node u,
	 * every data value stored in the subtree rooted at u.left is less than u.x
	 * and every data value stored in the subtree rooted at u.right is greater
	 * than u.x.
	 * 
	 * Answer to question 2, assignment 2.
	 * 
	 * @author Eric Dunbar
	 * @param n root Node for sub-tree to be tested.
	 * @return whether the binary search tree property is implemented properly
	 */
	public boolean testBSTP(Node<T> n) {
		boolean testB = true;
		if (n.left != nil) {
			testB = testB && (c.compare(n.x, n.left.x) > 0);
			testB = testB && testBSTP(n.left);
		}
		if (n.right != nil) {
			testB = testB && (c.compare(n.x, n.right.x) < 0);
			testB = testB && testBSTP(n.right);
		}
		return testB;
	}

	/**
	 * Tracks rank and data for each node in the tree for printing purposes.
	 */
	ArrayList<ArrayList<T>> pT;

	/**
	 * Add data to a data structure that tracks elements and ranks (height) of
	 * the Nodes in the BinarySearchTree. This can be used to show the tree.
	 * 
	 * @param n height with 0 being root
	 * @param x data element
	 */
	private void addToPT(int n, T x) {
		while (pT.size() < n + 1) {
			pT.add(new ArrayList<T>());
		}
		pT.get(n).add(x);
	}

	/**
	 * Print the binary search sub-tree starting at the given Node.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 */
	public void printBSTPTree(Node<T> u) {
		pT = new ArrayList<ArrayList<T>>();

		int n = 0;
		constructBSTPTree(u, n);

		System.out.println(
				"Caution: this only produces accurate relative horizontal positions for a balanced tree");
		for (int i = 0; i < pT.size(); i++) {
			int theSize = pT.get(i).size();
			String thePadding = CommonSuite.stringRepeat(" ", (60 - 4 * theSize) / (theSize + 1));
			for (int j = 0; j < theSize; j++) {
				System.out.printf("%s%4s", thePadding, pT.get(i).get(j));
			}
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
		System.out.printf("%d, %s   ", n, u.x);
		if (u.left != nil) {
			constructBSTPTree(u.left, n);
		}
		if (u.right != nil) {
			constructBSTPTree(u.right, n);
		}
	}

	/**
	 * Demonstrate the binary search tree property test.
	 */
	private static void runBinarySearchTreePropertyTest() {
		BinarySearchTreeProperty<Integer> ib;
		ib = new BinarySearchTreeProperty<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());
		ib.add(10);
		Integer[] iArray = { 10, 5, 3, 7, 4, 6, 2, 11, 12, 8, 1, 9, 15, 16, 17, 18, 13, 14, 19,
				20 };
		for (Integer integer : iArray) {
			ib.add(integer);
		}
		Node<Integer> my = ib.r;

		// THE ACTUAL QUESTION 2 TEST
		System.out.printf("This tree %s obey the binary search tree property.%n",
				ib.testBSTP(ib.r) ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();
		System.out.println();

		// SWAP DATA AND DON'T BOTHER CHECK FOR VALID NODES :)
		System.out.println("Modify the BinarySearchTree by manually switching elements");

		Integer fastAndLooseTest;
		fastAndLooseTest = ib.r.left.x;
		ib.r.left.x = ib.r.right.x;
		ib.r.right.x = fastAndLooseTest;

		// THE ACTUAL QUESTION 2 TEST
		System.out.printf("This tree %s obey the binary search tree property.%n",
				ib.testBSTP(ib.r) ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();
		System.out.println();

		// SWAP DATA AND DON'T BOTHER CHECK FOR VALID NODES :)
		System.out
				.println("Reverse the changes to BinarySearchTree by manually switching elements");
		fastAndLooseTest = ib.r.left.x;
		ib.r.left.x = ib.r.right.x;
		ib.r.right.x = fastAndLooseTest;

		// THE ACTUAL QUESTION 2 TEST
		System.out.printf("This tree %s obey the binary search tree property.%n",
				ib.testBSTP(ib.r) ? "does" : "does not");
		System.out.println();
		ib.printBSTPTree(my);
		System.out.println();
		System.out.println();
	}

	public static void main(String[] args) {
		printTheory();
		System.out.println();
		printTheory2();
		System.out.println();
		printQuestion();
		runBinarySearchTreePropertyTest();
	}

}
