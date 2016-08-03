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
		int preOrder;

		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * Eric.
		 */
		int inOrder;

		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * Eric.
		 */
		int postOrder;
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
			u.parent = w.left;
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
	 * Crude attempt at printing the tree
	 * 
	 * @param u
	 * @param rank
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
	 * Demonstration of a recursive pre-order traversal. Modification of
	 * traverse() by Pat Morin. Causes a StackOverFlow error.
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

	/**
	 * Assign pre-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation.
	 * 
	 * Answer to assignment (2), question (5) from textbook question 6.7.
	 */
	public void preOrderNumber() {
		preOrderNumberRecursive(r, 0);
	}

	/**
	 * Assign post-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation.
	 * 
	 * Answer to assignment (2), question (5) from textbook question 6.7.
	 */
	public void postOrderNumbers() {
		postOrderNumberRecursive(r, 0);
	}

	/**
	 * Assign in-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation.
	 * 
	 * Answer to assignment (2), question (5) from textbook question 6.7.
	 */
	public void inOrderNumber() {
		inOrderNumberRecursive(r, 0);
	}

	/**
	 * Demonstration of a recursive post-order traversal. Modification of
	 * traverse() by Pat Morin and preOrderNumberRecursive by Eric Dunbar.
	 * Causes a StackOverFlow error.
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
	 * Demonstration of a recursive in-order traversal. Modification of
	 * traverse() by Pat Morin and preOrderNumberRecursive by Eric Dunbar.
	 * Causes a StackOverFlow error.
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

	/**
	 * Demonstration of a non-recursive traversal. Original.
	 */
	public void xtraverse2() {
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

	private static BinaryTreeEric<Node> runSimulations(int repeats, int factor) {
		BinaryTreeEric<Node> b = null;
		double[] size = new double[factor];
		double[][] time = new double[3][factor];
		final int pre = 0;
		final int in = 1;
		final int post = 2;
		int tracker = 0;
		
		// build a BinaryTree with increasing numbers of nodes
		for (int idx = 0; idx < repeats; idx += repeats/factor) {
			b = new BinaryTreeEric<Node>(new Node(), new Node());
			Node[] pg148 = new Node[12 * repeats]; // track nodes as they're
													// added,
			// pg. 148
			int i = 0; // index for node tracking
			// specify root
			pg148[i++] = b.addLowest(b.newNode());
			Node second, tert;
			
			//multiply the number of nodes
			for (int j = 0; j < idx; j++) {
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
				pg148[i++] = second = b.addRight(pg148[i - 7], b.newNode()); // starts
																				// at
																				// root
																				// node
				pg148[i++] = tert = b.addLeft(second, b.newNode());
				pg148[i++] = b.addLeft(tert, b.newNode());
				pg148[i++] = tert = b.addRight(second, b.newNode());
				pg148[i++] = b.addLeft(tert, b.newNode());
				pg148[i++] = b.addRight(tert, b.newNode());
			}

			System.out.println("Size() = " + b.size() + " idx = " + idx);

			// determine how long it takes to (re)build the numbers
			size[tracker] = b.size();
			CommonSuite.StopWatch.start();
			for (int j = 0; j < 1000; j++) {
				// repeat 1000 times to make time measurabe on a fast computer
				// stack over flow is a problem
				b.preOrderNumberRecursive(b.r, 0); // assign pre-order numbers
			}
			time[pre][tracker] = CommonSuite.StopWatch.stop();

			CommonSuite.StopWatch.start();
			for (int j = 0; j < 1000; j++) {
				// repeat 1000 times to make time measurabe on a fast computer
				// stack over flow is a problem
				b.postOrderNumberRecursive(b.r, 0); // assign post-order numbers
			}
			time[post][tracker] = CommonSuite.StopWatch.stop();

			CommonSuite.StopWatch.start();
			for (int j = 0; j < 1000; j++) {
				// repeat 1000 times to make time measurabe on a fast computer
				// stack over flow is a problem
				b.inOrderNumberRecursive(b.r, 0); // assign post-order numbers
			}
			time[in][tracker++] = CommonSuite.StopWatch.stop();
			/*
			 * // show the tree with all its warts b.printTree(b.r, 1); // show
			 * the pre-order numbers int numNodes = b.size();
			 * System.out.print("Pre:  "); for (int j = 0; j < numNodes; j++) {
			 * System.out.printf("%2d ", pg148[j].preOrder); }
			 * System.out.println(); // show the post-order numbers
			 * System.out.print("Post: "); for (int j = 0; j < numNodes; j++) {
			 * System.out.printf("%2d ", pg148[j].postOrder); }
			 * System.out.println(); // show the in-order numbers
			 * System.out.print("In:   "); for (int j = 0; j < numNodes; j++) {
			 * System.out.printf("%2d ", pg148[j].inOrder); }
			 * System.out.println();
			 */
		}

		for (int i = 0; i < tracker; i++) {
			System.out.print("" + i + " (" + size[i] + ", " + time[pre][i] + ") ");
		}
		
		System.out.println();


		System.out.println("/==========================\\");
		System.out.println("||  PRE LINEAR REGRESSION ||");
		System.out.println("\\==========================/");
		LinearRegression.doLinearRegression(size, time[pre]);

		System.out.println("/==========================\\");
		System.out.println("|| POST LINEAR REGRESSION ||");
		System.out.println("\\==========================/");
		LinearRegression.doLinearRegression(size, time[post]);

		System.out.println("/==========================\\");
		System.out.println("||   IN LINEAR REGRESSION ||");
		System.out.println("\\==========================/");
		LinearRegression.doLinearRegression(size, time[in]);

		return b;
	}

	public static void main(String[] args) {

		BinaryTreeEric<Node> b = runSimulations(4000, 20);

		// print some trouble-shooting code
		System.out.println("nil? " + b.nil + ", .left? " + b.nil.left + ", .right? " + b.nil.right);
		System.out.println("r " + b.r);
		System.out.println("rl " + b.r.left);
		System.out.println("rll " + b.r.left.left);
		System.out.println("rlll " + b.r.left.left.left);
		//System.out.println(".size() = " + b.size() + ", .height() = " + b.height());
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
