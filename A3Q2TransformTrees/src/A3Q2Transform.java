import java.util.ArrayList;
import java.util.Comparator;

/**
 * Illustrate that via AVL single rotation, any binary search tree T1 can be
 * transformed into another search tree T2 (with the same items) (5 marks). Give
 * an algorithm to perform this transformation using O(N log N) rotation on
 * average
 * 
 * 
 * @author Eric Dunbar
 * @date 20/8/16
 * @assignment 3
 * @question 2
 * @title Binary Search Tree transformation
 *
 * @param <T> The class type of BST
 */
public class A3Q2Transform<T> extends BinarySearchTree<A3Q2Transform.Node<T>, T> {

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
	public A3Q2Transform(Node<T> sampleNode, Node<T> nil, Comparator<T> c) {
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
	}

	/**
	 * Displays the question text for assignment 3, question 2 from COMP272.
	 */
	public static void printQuestion() {
		String title = "Question 2. Transform one binary search tree to any other";
		String[] details = {
				"2. (a) Illustrate that via AVL single rotation, any binary search tree T1 can",
				"be transformed into another search tree T2 (with the same items) (5 marks).",
				"Give an algorithm to perform this transformation using O(N log N) rotation on average" };
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

	// DEMONSTRATION CODE STARTS
	// DEMONSTRATION CODE STARTS
	// DEMONSTRATION CODE STARTS

	/**
	 * Tracks height and data for each node in the tree for printing purposes.
	 */
	private ArrayList<ArrayList<T>> pT;
	private ArrayList<T> elements;

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

	private ArrayList<T> printBSTree() {
		return printBSTree(r);
	}

	private String prefix;

	/**
	 * Print the binary search sub-tree starting at the given Node.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 */
	private ArrayList<T> printBSTree(Node<T> u) {
		pT = new ArrayList<ArrayList<T>>();
		elements = new ArrayList<T>();

		int n = 0;
		prefix = "";
		System.out.print("{");
		constructBSTree(u, n);
		System.out.println("}");

		System.out.println();
		System.out.println("CAUTION: horizontal positions are correct only relative to each other");

		for (int i = 1; i < pT.size(); i++) {
			System.out.printf("d = %3d: ", i - 1);
			int theSize = pT.get(i).size();
			int baseWidth = 90;
			String thePadding = CommonSuite.stringRepeat(" ",
					(int) ((baseWidth - 3 * theSize) / (theSize + 1)));
			for (int j = 0; j < theSize; j++)
				System.out.printf("%s%3s", thePadding, pT.get(i).get(j));
			System.out.println();
		}
		return elements;
	}

	/**
	 * Helper method to collect information to print the binary search tree.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 * @param n starting height (0 = root)
	 */
	private void constructBSTree(Node<T> u, int n) {
		n++;
		addToPT(n, u.x);
		System.out.printf("%s%s", prefix, u.x);
		elements.add(u.x);
		prefix = ", ";
		if (u.left != nil)
			constructBSTree(u.left, n);
		if (u.right != nil)
			constructBSTree(u.right, n);
	}

	/**
	 * Builds a balanced BinarySearchTree<Integer> given the minimum and maximum
	 * bounds.
	 * 
	 * @param ib pointer to BSTProperty that is to be filled
	 * @param min lower limit data elements
	 * @param max upper limit of data elements
	 */
	private static void buildBalanced(A3Q2Transform<Integer> ib, int min, int max) {
		if (min == max) {
			ib.add(min);
			return;
		}
		int mid = (min + max) / 2;
		ib.add(mid);
		if (min < mid)
			buildBalanced(ib, min, mid - 1);
		if (mid < max)
			buildBalanced(ib, mid + 1, max);
	}

	/**
	 * Demonstrate the binary search tree transformation.
	 */
	private static void runTransformDemo() {
		A3Q2Transform<Integer> t1, t2, z1, z2;

		// CONSTRUCT THE BST

		t1 = new A3Q2Transform<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		t2 = new A3Q2Transform<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		z1 = new A3Q2Transform<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		z2 = new A3Q2Transform<Integer>(new Node<Integer>(), new Node<Integer>(),
				new DefaultComparator<Integer>());

		// A balanced tree
		int balMin = 1;
		int balMax = 31;
		buildBalanced(t1, balMin, balMax);

		t2.add((balMin + balMax) * 5 / 6);

		for (int i = balMax; i >= balMin; i--) {
			t2.add(i);
		}

		String[] description = { "A BALANCED TREE",
				"The minimum element starts at " + balMin + " and maxes out at " + balMax + "." };

		t2.clear();
		
		ArrayList<Integer> aL = t1.printBSTree();

		while (aL.size() != 0) {
			t2.add(aL.get((int)(Math.random()*aL.size())));
			
		}
		
		performTransform(t2, t1, description);

		// An inefficient balanced tree
		int base = 2;
		int exp = 4;
		t2.add((int) (Math.pow(base, exp) / 2));
		for (int j = 1; j < Math.pow(base, exp); j++)
			t2.add(j);

		if (true)
			return;

		description = new String[] { "AN INEFFICIENT BALANCED TREE",
				"A balanced tree is subjected to the Binary Search Tree Property test. ", "",
				"The minimum element starts at " + 1 + " and maxes out at "
						+ (int) Math.pow(base, exp) + ".",
				"", "Two data elements are swapped between nodes. This causes the BSTP test ",
				"to fail since one or more of the following conditions is no longer valid: ",
				"1. u.left.x < u.x,; or", "2. u.right.x > u.x.", "" };
		// performTransform(t2, description);

		// A manually built tree
		for (Integer integer : new Integer[] { 4, 2, 3, 1, 6, 5, 7 })
			z1.add(integer);

		description = new String[] { "A MANUALLY BUILT TREE",
				"A binary search tree is subjected to the Binary Search Tree Property test. ", "",
				"Two data elements are swapped between nodes. This causes the BSTP test ",
				"to fail since one or more of the following conditions is no longer valid: ",
				"1. u.left.x < u.x,; or", "2. u.right.x > u.x.", "" };
		// performTransform(z1, description);

		// An unbalanced tree
		base = 2;
		exp = 3;
		for (int j = 1; j < Math.pow(base, exp); j++)
			z2.add(j);

		description = new String[] { "AN UNBALANCED TREE",
				"An unbalanced tree is subjected to the Binary Search Tree Property test. ", "",
				"The minimum element starts at " + 1 + " and maxes out at "
						+ (int) Math.pow(base, exp) + "." };
		// performTransform(z2, description);

		System.out.println();
		System.out.println("DONE");
	}

	// TRANSFORM

	public static void performTransform(A3Q2Transform<Integer> t1, A3Q2Transform<Integer> t2,
			String[] text) {

		CommonSuite.printFullDescription(text);

		if (!t1.transformTree(t2))
			System.out.println("FAILURE: TREES ARE NOT EQUAL");

	}

	/**
	 * Is the current root of the sub-tree that is to have its root changed.
	 * Lazy hack to reduce redundancy in pre-order traversal. Am sure there must
	 * be an even simpler way.
	 */
	private Node<T> root;

	/**
	 * Is the new root for the sub-tree that is to have its root changed. Lazy
	 * hack to reduce redundancy in pre-order traversal. Am sure there must be
	 * an even simpler way.
	 */
	private Node<T> newRoot;

	/**
	 * Is the nil node for the binary search tree that is to be used as a
	 * template for the transformation. Lazy hack to reduce redundancy in
	 * pre-order traversal. Am sure there must be an even simpler way.
	 */
	private Node<T> externalNil;

	/**
	 * How many makeRoots were performed during most recent transformation.
	 */
	private int makeRootCounter; // how many makeRoots performed

	/**
	 * How many traversal steps were performed during most recent
	 * transformation.
	 */
	private int traversalStepCounter;

	/**
	 * How many rotations were performed in all makeRoot operations.
	 */
	private int rotationsCounter;

	/**
	 * Transforms this binary search tree to match the structure of tree
	 * provided as a parameter. Both trees must contain the same elements or the
	 * transformation will fail and false will be returned. Some code borrowed
	 * from BinarySearchTree.traversal by Pat Morin.
	 * 
	 * Source: Templatetypedef. (2012, December 25). Is it always possible to
	 * turn one BST into another using tree rotations? Retrieved August 21,
	 * 2016, from
	 * http://stackoverflow.com/questions/14027726/is-it-always-possible-to-turn-one-bst-into-another-using-tree-rotations.
	 * 
	 * @warning the tree's structure may be changed even with a failure
	 * @author Eric Dunbar
	 * @date Aug 21, 2016
	 * @title Transform trees
	 * @assignment 3
	 *
	 * @param t the tree that is the template
	 * @return true if transformation successful.
	 */
	public boolean transformTree(A3Q2Transform<T> t) {

		CommonSuite.printSuperFancyHeader("Master binary search tree (template)");
		t.printBSTree();
		CommonSuite.printSuperFancyHeader("Binary search tree to be transformed");
		printBSTree();
		System.out.println();

		if (t.size() != this.size()) {
			return false;
		}

		externalNil = t.nil;
		newRoot = findLast(t.r.x);
		root = r;

		Node<T> u = t.r, prev = t.nil, next;

		makeRootCounter = 0; // how many makeRoots performed
		traversalStepCounter = 0; // how many traversal steps performed
		rotationsCounter = 0; // how many rotations were performed in the
								// makeRoot operations

		// Do a pre-order traversal of the tree
		while (u != t.nil) {
			traversalStepCounter++;
			if (newRoot != null) {

				CommonSuite.printFancyHeader(String.format(
						"makeRoot, round %2d: change root of sub-tree rooted at %s to %s.",
						++makeRootCounter, root.x, u.x));

				if (findLast(u.x).x != u.x) {
					return false;
				}
				makeRoot(root, newRoot);
				root = findLast(u.x); // keep the current node of the
										// transformed tree synchronized with
										// the current node of the template tree

				printBSTree();
				System.out.println();
			}
			if (prev == u.parent) {
				if (u.left != t.nil) {
					next = u.left;
					root = root.left;
					newRoot = findLast(next.x);
					System.out.println("Condition 1: has left child, go left");
				} else if (u.right != t.nil) {
					next = u.right;
					root = root.right;
					newRoot = findLast(next.x);
					System.out.println("Condition 2: no left child, go right");
				} else {
					next = u.parent;
					upToParent(next);
					System.out.println("Condition 3: no children, go up");
				}
			} else if (prev == u.left) {
				if (u.right != t.nil) {
					next = u.right;
					root = root.right;
					newRoot = findLast(next.x);
					System.out.println("Condition 4: from left, go right");
				} else {
					next = u.parent;
					upToParent(next);
					System.out.println("Condition 5: from left, no right, go up");
				}
			} else {
				System.out.println("Condition 6: from right, go up");
				next = u.parent;
				upToParent(next);
			}
			prev = u;
			u = next;
		}

		System.out.println();
		CommonSuite.printFancyHeader("Summarise order");
		System.out.println("       makeRootCounter = " + makeRootCounter);
		System.out.println("  traversalStepCounter = " + traversalStepCounter);
		System.out.println("      rotationsCounter = " + rotationsCounter);
		System.out.println("       n = tree.size() = " + this.size());

		return true;
	}

	/**
	 * Lazy hack to handle going up to the parent during a tree transformation.
	 * Part of a pre-order traversal of a binary search tree.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 21, 2016
	 * @title
	 * @assignment 3
	 *
	 * @param next
	 */
	private void upToParent(Node<T> next) {
		root = root.parent;
		if (next != externalNil)
			newRoot = null;
	}

	/**
	 * Use left and right rotations to restructure the binary search sub-tree so
	 * the target node becomes its root. The parameter root is the node that is
	 * the current root of the tree and is the position target will occupy when
	 * the method is done.
	 * 
	 * @formatter:off

		while (target node is not the root) {
		    if (node is a left child) {
		        apply a right rotation to the node and its parent;
		    } else {
		        apply a left rotation to the node and its parent;
		    }
		}
		Source: Templatetypedef (2012).
	 * @formatter:on
	 * 
	 * @author Eric Dunbar
	 * @date Aug 22, 2016
	 * @title Make a given node the root of the subtree.
	 * @assignment 3
	 *
	 * @param root node that is current root of the sub-tree
	 * @param target node that is to become the root of the sub-tree
	 */
	public void makeRoot(Node<T> root, Node<T> target) {
		Node<T> rootParent = root.parent;
		while (target.parent != rootParent) {
			rotationsCounter++; // track number of rotations required
			if (target.parent.left == target) {
				rotateRight(target.parent);
			} else {
				rotateLeft(target.parent);
			}
		}
	}

	public static void main(String[] args) {
		// Display programmer info and create testing object
		CommonSuite.commonProgramStart(3, 2, "Binary Search Tree AVL Rotations", false);

		printTheory();
		System.out.println();
		printQuestion();
		System.out.println();

		runTransformDemo();
	}
}
