import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * An implementation of binary trees. Modified by Eric Dunbar for assignment 2.
 * Source: BinaryTree.java by Pat Morin ODS from sample code.
 * 
 * @author Pat Morin
 * @author Eric Dunbar
 *
 * @param <Node>
 */
public class BinaryTreeEric<Node extends BinaryTreeEric.BTNode<Node>> {

	public static class BTNode<Node extends BTNode<Node>> {
		public Node left;
		public Node right;
		public Node parent;
	}
	/*
	 * START START START START
	 * ========================================================================
	 * ========================================================================
	 * ========================================================================
	 * ========================================================================
	 * ========================================================================
	 */

	/*
	 * A pre-order traversal of a binary tree is a traversal that visits each
	 * node, u, before any of its children. An in-order traversal visits u after
	 * visiting all the nodes in u’s left subtree but before visiting any of the
	 * nodes in u’s right subtree. A post-order traversal visits u only after
	 * visiting all other nodes in u’s subtree. The pre/in/post-order numbering
	 * of a tree labels the nodes of a tree with the integers 0, . . . ,n - 1 in
	 * the order that they are encountered by a pre/in/post-order traversal. See
	 * Figure 6.10 for an example. (pg 148)
	 */

	/*
	 * Design an algorithm for the following operations for a binary tree BT,
	 * and show the worst-case running times for each implementation:
	 * 
	 * 1 Binary Tree
	 * 
	 * a. preorderNext(x): return the node visited after node x in a pre-order
	 * traversal of BT.
	 * 
	 * b. postorderNext(x): return the node visited after node x in a post-order
	 * traversal of BT.
	 * 
	 * c. inorderNext(x): return the node visited after node x in an in-order
	 * traversal of BT.
	 */

	/**
	 * Created by Eric from Treap.java with assistance from comments in COMP272
	 * forum. Provides the Node class required for BinaryTree to act as a source
	 * of objects.
	 * 
	 * @author Eric Dunbar
	 */
	protected static class Node extends BinaryTreeEric.BTNode<Node> {
		/**
		 * A class variable called p. Does nothing. Eric.
		 */
		int p;

		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * Eric.
		 */
		int preOrder = -99;

		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * Eric.
		 */
		int inOrder = -99;

		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * Eric.
		 */
		int postOrder = -99;
	}

	/**
	 * Add a node to the lowest in-order empty (nil) left node. ERIC
	 * 
	 * @author Eric Dunbar
	 * 
	 * @return the added node
	 */
	public Node addLowest(Node u) {
		if (r == nil) {
			r = u;
		} else {
			Node w = r;
			while (w.left != nil) {
				w = w.left;
			}
			w.left = u;
			u.parent = w;
		}
		return u;
	}

	/**
	 * Add a node to the left child of the given node. Will overwrite existing
	 * left child node. ERIC.
	 * 
	 * @author Eric Dunbar
	 * 
	 * @return the newly added node
	 */
	public Node addLeft(Node parent, Node child) {
		if (parent == nil) {
			throw new NullPointerException();
		}
		parent.left = child;
		child.parent = parent;
		return child;
	}

	/**
	 * Add a node to the right child of the given node. Will overwrite existing
	 * right child node. ERIC.
	 * 
	 * @author Eric Dunbar
	 * 
	 * @return the newly added node
	 */
	public Node addRight(Node parent, Node child) {
		if (parent == nil) {
			throw new NullPointerException();
		}
		parent.right = child;
		child.parent = parent;
		return child;
	}

	/**
	 * Crude attempt at printing the tree. Causes stack over flow with large
	 * trees.
	 * 
	 * @param u root node for the tree to be printed
	 * @param rank what is the starting rank of the root node (0 for root)
	 * @return
	 */
	protected String printTree(Node u, int rank) {
		if (u == nil)
			return "END";
		else {
			System.out.printf("(rank)(order)Left, (rank)(order)Right %4s, %4s %n",
					printTree(u.left, rank + 1), printTree(u.right, rank + 1));
			return "R" + String.format("%d", rank) + " ("
					+ String.format("pre %2d, in %2d", u.preOrder, u.inOrder) + ") " + "*";
		}
	}

	/**
	 * Performs a recursive pre-order traversal. Modification of traverse() by
	 * Pat Morin. Causes a StackOverFlow error.
	 * 
	 * @param u starting node for the tree rooted at u
	 * @param rank current pre-order rank, should be 0 if starting with the root
	 *            node
	 */
	public int preOrderNumberRecursive(Node u, int rank) {
		if (u == nil)
			return rank;
		u.preOrder = rank++;
		rank = preOrderNumberRecursive(u.left, rank);
		rank = preOrderNumberRecursive(u.right, rank);
		return rank;
	}

	public enum Order {
		PREORDER, INORDER, POSTORDER
	};

	/**
	 * Order visit numbers using iterative traversal. Based off traverse2() by
	 * Pat Morin.
	 * 
	 * @author Eric Dunbar
	 */
	public void orderNumberIteractive(Node u, Order o) {
		// System.out.println(" nil? " + nil + ", .left? " + nil.left + ",
		// .right? " + nil.right);
		// System.out.println(" r " + r + " u: " + u + " parent: " + r.parent);
		// System.out.println(" rl " + r.left + " parent: " + r.left.parent);
		// System.out.println(" rll " + r.left.left + " parent: " +
		// r.left.left.parent);
		// System.out.println(" rlll " + r.left.left.left + " parent: " +
		// r.left.left.left.parent);

		Node prev = nil;
		Node next;
		int preOrderRank = 0;
		int inOrderRank = 0;
		int postOrderRank = 0;
		while (u != nil) {
			if (prev == u.parent) {
				// System.out.print(u +" ");
				// FIRST VISIT. Arrived at a new node since prev == u.parent
				// Assign PREORDER number here since this is the first visit
				if (o == Order.PREORDER) {
					u.preOrder = preOrderRank++;
					// System.out.println("u: " + u + " u.left: " + u.left + "
					// u.right: " + u.right + " u.parent: " + u.parent);
				}
				if (u.left != nil)
					// go left
					next = u.left;
				else if (u.right != nil)
				// go right because left is nil
				// IN-ORDER number ASSIGNED HERE because no left child
				{
					if (o == Order.INORDER) {
						u.inOrder = inOrderRank++;
					}
					next = u.right;
				} else
				// LEAF: go back up the tree
				// INORDER number assigned here because no left child
				// POSTORDER number assigned here because this is leaf
				{
					if (o == Order.POSTORDER)
						u.postOrder = postOrderRank++;
					else if (o == Order.INORDER)
						u.inOrder = inOrderRank++;
					next = u.parent;
				}
			} else if (prev == u.left) {

				// came from the left child, now what?
				// INORDER number assigned here because done with left child
				if (o == Order.INORDER)
					u.inOrder = inOrderRank++;
				if (u.right != nil)
					// go right because it's not empty
					next = u.right;
				else
				// go back up because right is empty
				{
					// THERE'S A LEFT CHILD BUT NO RIGHT CHILD.
					// POSTORDER update
					if (o == Order.POSTORDER)
						u.postOrder = postOrderRank++;
					next = u.parent;
				}
			} else {

				// came from right child so up is the only way to go
				// POSTORDER assigned here since done with all children
				if (o == Order.POSTORDER) {
					u.postOrder = postOrderRank++;
				}
				next = u.parent;
			}
			prev = u; // the current node now assigned to previous node
			u = next; // next node assigned to current node
		}
	}

	static boolean iterative = false;

	/**
	 * Assign pre-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation.
	 * 
	 * Answer to assignment (2), question (5) from textbook question 6.7.
	 */
	public void preOrderNumber() {
		if (iterative)
			orderNumberIteractive(r, Order.PREORDER);
		else
			preOrderNumberRecursive(r, 0);
	}

	/**
	 * Assign post-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation.
	 * 
	 * Answer to assignment (2), question (5) from textbook question 6.7.
	 */
	public void postOrderNumbers() {
		if (iterative)
			orderNumberIteractive(r, Order.POSTORDER);
		else
			postOrderNumberRecursive(r, 0);
	}

	/**
	 * Assign in-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation.
	 * 
	 * Answer to assignment (2), question (5) from textbook question 6.7.
	 */
	public void inOrderNumber() {
		if (iterative)
			orderNumberIteractive(r, Order.INORDER);
		else
			inOrderNumberRecursive(r, 0);
	}

	/**
	 * Performs a recursive post-order traversal. Modification of traverse() by
	 * Pat Morin and preOrderNumberRecursive by Eric Dunbar. Causes a
	 * StackOverFlow error.
	 * 
	 * @param u starting node for the tree rooted at u
	 * @param rank current post-order rank, should be 0 if starting with the
	 *            root node
	 */
	public int postOrderNumberRecursive(Node u, int rank) {
		if (u == nil)
			return rank;
		rank = postOrderNumberRecursive(u.left, rank);
		rank = postOrderNumberRecursive(u.right, rank);
		u.postOrder = rank++;
		return rank;
	}

	/**
	 * Performs a recursive in-order traversal. Modification of traverse() by
	 * Pat Morin and preOrderNumberRecursive by Eric Dunbar. Causes a
	 * StackOverFlow error.
	 * 
	 * @param u starting node for the tree rooted at u
	 * @param rank current in-order rank, should be 0 if starting with the root
	 *            node
	 */
	public int inOrderNumberRecursive(Node u, int rank) {
		if (u == nil)
			return rank;
		rank = inOrderNumberRecursive(u.left, rank);
		u.inOrder = rank++;
		rank = inOrderNumberRecursive(u.right, rank);
		return rank;
	}

	private static void printNumber(int number) {
		System.out.printf("%4d ", number);
	}

	public static void printNumbers(Node[] pg148, int numNodes) {
		int lineWrap = 15;
		int lineEnd = Math.min(lineWrap, numNodes);
		int lineStart = 0;
		// show the pre-order numbers

		System.out.println();
		System.out.println("NUMBERS ASSIGNED TO NODES IN A BINARY TREE");
		System.out.println();
		while (lineStart < numNodes) {
			System.out.print(" Pre: ");
			for (int j = lineStart; j < lineEnd; j++) {
				printNumber(pg148[j].preOrder);
			}
			System.out.println(); // show the post-order numbers
			System.out.print("Post: ");
			for (int j = lineStart; j < lineEnd; j++) {
				printNumber(pg148[j].postOrder);
			}
			System.out.println(); // show the in-order numbers
			System.out.print("  In: ");
			for (int j = lineStart; j < lineEnd; j++) {
				printNumber(pg148[j].inOrder);
			}
			System.out.println();
			System.out.println();
			lineStart += lineWrap;
			lineEnd = Math.min(lineEnd + lineWrap, numNodes);
		}
	}

	private static BinaryTreeEric<Node> runSimulations(int repeats, int factor) {
		if (factor < 1 || repeats < 1) {
			factor = repeats = 1; // default
			System.out.println("An error occurred.");
		}
		// eliminate null pointer exceptions & ensure arrays align
		repeats = repeats / factor * factor;

		BinaryTreeEric<Node> b = null; // the binary tree
		Node[] pg148 = null; // the nodes added to the binary tree, in order

		// regression analysis variables
		double[] regressionSizeInd = new double[factor];
		double[][] regressionTimeDep = new double[3][factor];
		int regressionIterationTracker = 0;

		// constants for array indices
		final int pre = 0;
		final int in = 1;
		final int post = 2;

		// build a BinaryTree with increasing numbers of nodes using pg. 148
		for (int idx = 0; idx < repeats; idx += repeats / factor) {
			b = new BinaryTreeEric<Node>(new Node(), new Node());
			pg148 = new Node[1 + 11 * repeats]; // track nodes as they're
												// added,
			// pg. 148
			int i = 0; // index for node tracking
			// specify root
			pg148[i++] = b.addLowest(b.newNode());
			Node second, tert;

			// multiply the number of nodes
			for (int j = 0; j < idx + 1; j++) {
				// Add nodes in pre-order order (with one exception)
				// Build left half
				pg148[i++] = second = b.addLowest(b.newNode()); // first left
																// child
																// pre1
				pg148[i++] = b.addLowest(b.newNode()); // second left child pre2
														// (out of
														// order)
				pg148[i++] = tert = b.addRight(second, b.newNode()); // pre3
				pg148[i++] = b.addLeft(tert, b.newNode()); // pre4
				pg148[i++] = b.addRight(tert, b.newNode()); // pre5

				// Build right half
				// start at root node, or, i-7 for subsequent additions
				pg148[i++] = second = b.addRight(pg148[i - 7], b.newNode());
				pg148[i++] = tert = b.addLeft(second, b.newNode());
				pg148[i++] = b.addLeft(tert, b.newNode());
				pg148[i++] = tert = b.addRight(second, b.newNode());
				pg148[i++] = b.addLeft(tert, b.newNode());
				pg148[i++] = b.addRight(tert, b.newNode());
			}

			regressionSizeInd[regressionIterationTracker] = b.size();

			System.out.println(".size() = " + regressionSizeInd[regressionIterationTracker]
					+ ", iteration = " + idx);

			int timeRepeats = 1000;
			// determine how long it takes to (re)build the numbers
			CommonSuite.StopWatch.start();
			for (int j = 0; j < timeRepeats; j++) {
				// repeat 1000 times to make time measurable on a fast computer
				// stack over flow is a problem
				b.preOrderNumber();
				// b.orderNumberIteractive(b.r, Order.PREORDER); // assign
				// pre-order
				// numbers
				// b.preOrderNumberRecursive(b.r, 0); // assign pre-order
				// numbers
			}
			regressionTimeDep[pre][regressionIterationTracker] = CommonSuite.StopWatch.stop();

			CommonSuite.StopWatch.start();
			for (int j = 0; j < timeRepeats; j++) {
				// repeat 1000 times to make time measurable on a fast computer
				// stack over flow is a problem
				b.postOrderNumbers();
				// b.orderNumberIteractive(b.r, Order.POSTORDER); // assign
				// post-order
				// numbers
				// b.postOrderNumberRecursive(b.r, 0); // assign post-order
				// numbers
			}
			regressionTimeDep[post][regressionIterationTracker] = CommonSuite.StopWatch.stop();

			CommonSuite.StopWatch.start();
			for (int j = 0; j < timeRepeats; j++) {
				// repeat 1000 times to make time measurable on a fast computer
				// stack over flow is a problem
				b.inOrderNumber();
				// b.orderNumberIteractive(b.r, Order.INORDER); // assign
				// in-order
				// numbers
				// b.inOrderNumberRecursive(b.r, 0); // assign post-order
				// numbers
			}
			regressionTimeDep[in][regressionIterationTracker++] = CommonSuite.StopWatch.stop();
		}

		System.out.println();

		// show the tree with all its warts
		// b.printTree(b.r, 1);

		printNumbers(pg148, b.size());

		System.out.println();
		System.out.println("||========================================||");
		System.out.println("||  Do ordering methods run in O(n) time? ||");
		System.out.println("||========================================||");
		System.out.println();

		System.out.println("||======================================||");
		System.out.println("||  PRE-ORDER NUMBERS LINEAR REGRESSION ||");
		System.out.println("||======================================||");
		LinearRegression.doLinearRegression(
				Arrays.copyOfRange(regressionSizeInd, 1, regressionSizeInd.length),
				Arrays.copyOfRange(regressionTimeDep[pre], 1, regressionTimeDep[pre].length));

		System.out.println("||======================================||");
		System.out.println("|| POST-ORDER NUMBERS LINEAR REGRESSION ||");
		System.out.println("||======================================||");
		LinearRegression.doLinearRegression(
				Arrays.copyOfRange(regressionSizeInd, 1, regressionSizeInd.length),
				Arrays.copyOfRange(regressionTimeDep[post], 1, regressionTimeDep[post].length));

		System.out.println("||======================================||");
		System.out.println("||   IN-ORDER NUMBERS LINEAR REGRESSION ||");
		System.out.println("||======================================||");
		LinearRegression.doLinearRegression(
				Arrays.copyOfRange(regressionSizeInd, 1, regressionSizeInd.length),
				Arrays.copyOfRange(regressionTimeDep[in], 1, regressionTimeDep[in].length));

		return b;
	}

	public static void main(String[] args) {
		iterative = true;
		BinaryTreeEric<Node> b = runSimulations(2000, 20);

		// print some trouble-shooting code
		System.out.println("nil? " + b.nil + ", .left? " + b.nil.left + ", .right? " + b.nil.right);
		System.out.println("r " + b.r);
		System.out.println("rl " + b.r.left);
		System.out.println("rll " + b.r.left.left);
		System.out.println("rlll " + b.r.left.left.left);
		// System.out.println(".size() = " + b.size() + ", .height() = " +
		// b.height());
	}

	/*
	 * END END END END
	 * ========================================================================
	 * ========================================================================
	 * ========================================================================
	 * ========================================================================
	 * ========================================================================
	 */

	/**
	 * Used to make a mini-factory
	 */
	protected Node sampleNode;

	/**
	 * The root of this tree
	 */
	protected Node r;

	/**
	 * This tree's "null" node
	 */
	protected Node nil;

	/**
	 * Create a new instance of this class. Original.
	 * 
	 * @param sampleNode - a sample of a node that can be used to create a new
	 *            node in newNode()
	 * @param nil - a node that will be used in place of null
	 */
	public BinaryTreeEric(Node sampleNode, Node nil) {
		this.sampleNode = sampleNode;
		this.nil = nil;
		r = nil;
	}

	/**
	 * Create a new instance of this class. Original.
	 * 
	 * @param sampleNode - a sample of a node that can be used to create a new
	 *            node in newNode()
	 */
	public BinaryTreeEric(Node sampleNode) {
		this.sampleNode = sampleNode;
	}

	/**
	 * Allocate a new node for use in this tree. Original.
	 * 
	 * @return
	 */
	protected Node newNode() {
		try {
			Node u = (Node) sampleNode.getClass().newInstance();
			u.parent = u.left = u.right = nil;
			return u;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Compute the depth (distance to the root) of u. Original.
	 * 
	 * @param u
	 * @return the distanct between u and the root, r
	 */
	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}

	/**
	 * Compute the size (number of nodes) of this tree. Original.
	 * 
	 * @warning uses recursion so could cause a stack overflow
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}

	/**
	 * Original.
	 * 
	 * @return the size of the subtree rooted at u
	 */
	protected int size(Node u) {
		if (u == nil)
			return 0;
		return 1 + size(u.left) + size(u.right);
	}

	/**
	 * Compute the number of nodes in this tree without recursion. Original.
	 * 
	 * @return
	 */
	public int size2() {
		Node u = r, prev = nil, next;
		int n = 0;
		while (u != nil) {
			if (prev == u.parent) {
				n++;
				if (u.left != nil)
					next = u.left;
				else if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else if (prev == u.left) {
				if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
		return n;
	}

	/**
	 * Compute the maximum depth of any node in this tree. Original.
	 * 
	 * @return the maximum depth of any node in this tree
	 */
	public int height() {
		return height(r);
	}

	/**
	 * Original.
	 * 
	 * @return the size of the subtree rooted at u
	 */
	protected int height(Node u) {
		if (u == nil)
			return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	/**
	 * Original.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return r == nil;
	}

	/**
	 * Make this tree into the empty tree. Original.
	 */
	public void clear() {
		r = nil;
	}

	/**
	 * Demonstration of a recursive traversal. Original.
	 * 
	 * @param u
	 */
	public void traverse(Node u) {
		if (u == nil)
			return;
		traverse(u.left);
		traverse(u.right);
	}

	/**
	 * Demonstration of a non-recursive traversal. Original.
	 */
	public void traverse2() {
		Node u = r, prev = nil, next;
		while (u != nil) {
			if (prev == u.parent) {
				if (u.left != nil)
					next = u.left;
				else if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else if (prev == u.left) {
				if (u.right != nil)
					next = u.right;
				else
					next = u.parent;
			} else {
				next = u.parent;
			}
			prev = u;
			u = next;
		}
	}

	/**
	 * Demonstration of a breadth-first traversal. Original.
	 */
	public void bfTraverse() {
		Queue<Node> q = new LinkedList<Node>();
		if (r != nil)
			q.add(r);
		while (!q.isEmpty()) {
			Node u = q.remove();
			if (u.left != nil)
				q.add(u.left);
			if (u.right != nil)
				q.add(u.right);
		}
	}

	/**
	 * Find the first node in an in-order traversal. Original.
	 * 
	 * @return the first node reported in an in-order traversal
	 */
	public Node firstNode() {
		Node w = r;
		if (w == nil)
			return nil;
		while (w.left != nil)
			w = w.left;
		return w;
	}

	/**
	 * Find the node that follows w in an in-order traversal. Original.
	 * 
	 * @param w
	 * @return the node that follows w in an in-order traversal
	 */
	public Node nextNode(Node w) {
		if (w.right != nil) {
			w = w.right;
			while (w.left != nil)
				w = w.left;
		} else {
			while (w.parent != nil && w.parent.left != w)
				w = w.parent;
			w = w.parent;
		}
		return w;
	}
}
