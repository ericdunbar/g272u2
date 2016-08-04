import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import BinaryTree.Node;

/**
 * An implementation of binary trees. Modified by Eric Dunbar for assignment 2.
 * Source: BinaryTree.java by Pat Morin ODS from sample code.
 * 
 * @author Pat Morin
 * @author Eric Dunbar
 *
 * @param <Node>
 */
public class BinaryTreeEric<Node extends BinaryTreeEric.BTENode<Node>> {

	public static class BTENode<Node extends BTENode<Node>> {
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

	/*
	 * 5. (20 marks) Exercise 6.7. Create a subclass of BinaryTree whose nodes
	 * have fields for storing preorder, post-order, and in-order numbers. Write
	 * methods preOrderNumber(), inOrderNumber(), and postOrderNumbers() that
	 * assign these numbers correctly. These methods should each run in O(n)
	 * time.
	 */

	/**
	 * Node class that tracks pre-order, in-order and post-order numbering for
	 * nodes (pg. 148 Pat Morin ODS) and parent, left-child and right-child node
	 * relationships. Created by Eric with inspiration from Treap.java and
	 * assistance from a comment in COMP272 forum that suggested that a Node
	 * class needed to be created.
	 * 
	 * @author Eric Dunbar
	 */
	protected static class Node extends BinaryTreeEric.BTENode<Node> {
		final int warningNumber = -99;
		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * 
		 * @author Eric Dunbar
		 */
		int preOrder = warningNumber;

		/**
		 * Track the order number of the node if visited by an in-order routine.
		 * 
		 * @author Eric Dunbar
		 */
		int inOrder = warningNumber;

		/**
		 * Track the order number of the node if visited by a post-order
		 * routine.
		 * 
		 * @author Eric Dunbar
		 */
		int postOrder = warningNumber;
	}

	/**
	 * Add the node as the lowest in-order node.
	 * 
	 * @author Eric Dunbar
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
	 * Add or replace the left child node of the given parent node.
	 * 
	 * @warning This will replace an existing left child node.
	 * @author Eric Dunbar
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
	 * Add or replace the right child node of the given parent node.
	 * 
	 * @warning This will replace an existing right child node.
	 * @author Eric Dunbar
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
	 * Crude attempt at printing the tree, recursively. Causes stack over flow
	 * with large trees.
	 * 
	 * @author Eric Dunbar
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
	 * Performs a recursive pre-order traversal of all nodes and records the
	 * pre-order traversal number in each node. Code modified from traverse() by
	 * Pat Morin. Fast but causes a StackOverFlow error with a large enough
	 * number of nodes. Runs in O(n) time.
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
	 * Determine the next node for a pre-order traversal of the tree. Calling
	 * this repeatedly should build the same tree as what is stored by pre-order
	 * traversal. Based off traverse2() by Pat Morin and orderNumberIterative by
	 * Eric Dunbar.
	 * 
	 * Worst case running time is O(2n) since it potentially has to visit each
	 * node and check the left and right children before it finds the next node.
	 * For example, an unbalanced tree with only left-node children on the left
	 * and a single node on the right would result in the maximum running time.
	 * Best case is O(1) if the given node has a left child.
	 * 
	 * @author Eric Dunbar
	 */
	public Node preorderNext(Node u) {
		Node prev = nil;
		Node next;

		if (u.left != nil)
			return u.left; // RETURN left child
		else if (u.right != nil)
			return u.right; // RETURN right child because left is nil

		// LEAF, thus go up the parent chain until a right-child is found
		prev = u;
		u = u.parent;

		while (u != nil) {
			if (u.left == prev) {
				if (u.right != nil)
					return u.right;
				else
					next = u.parent;
			} else // if (u.right == prev)
				next = u.parent;
			prev = u; // the current node now assigned to previous node
			u = next; // next node assigned to current node
		}
		return nil; // if it got this far there was no next node
	}

	/**
	 * Determine the next node for an in-order traversal of the tree. Calling
	 * this repeatedly should build the same tree as what is stored by in-order
	 * traversal. Based off traverse2() & firstNode() by Pat Morin and,
	 * orderNumberIterative() & preorderNext() by Eric Dunbar.
	 * 
	 * Worst case running time is O(n) if given the last node in an unbalanced
	 * right-child-only tree. It has to visit all parent nodes.
	 * 
	 * @author Eric Dunbar
	 */
	public Node inorderNext(Node u) {
		/*
		 * First: test to see if it has a right-child, if so, go down one and
		 * find the "first" (lowest) node
		 * 
		 * Second: go up to parent and if the prev is the parent's left node,
		 * return the parent
		 * 
		 * Third: if it was the right node, keep repeating 2nd and third until
		 * you hit nil
		 */
		Node prev;

		if (u.right != nil) {
			u = u.right;
			while (u.left != nil)
				u = u.left;
			return u;
		}

		while (u != nil) {
			prev = u;
			u = u.parent;
			if (u.left == prev)
				return u; // RETURN parent}
		}
		return nil; // let's move on
	}

	/**
	 * Determine the next node for a post-order traversal of the tree. Calling
	 * this repeatedly should build the same tree as what is stored by
	 * post-order traversal. Based off traverse2() & firstNode() by Pat Morin
	 * and, orderNumberIterative() & preorderNext() & inorderNext() by Eric
	 * Dunbar.
	 * 
	 * Worst case running time is ___ if given the last node in an unbalanced
	 * right-child-only tree. It has to visit all parent nodes.
	 * 
	 * @author Eric Dunbar
	 */
	public Node postorderNext(Node u) {
		/*
		 * 1. Go up.
		 * 
		 * a. If you were the right-child return your parent
		 * 
		 * b. If you were the left-child... return the left or right child of
		 * the firstNode
		 * 
		 * c. if your parent was nil return nil
		 */

		if (u == nil) {
			return nil;
		}
		Node prev = u;
		u = u.parent;
		// GO UP.
		// If u was right-child, return the parent
		if (u.right == prev)
			return u;
		// If u was a left-child, return the firstNode, starting at the parent
		else if (u.left == prev) {
			if (u.right == nil)
				return u;
			u = u.right;

			while (u.left != nil || u.right != nil) {
				if (u.left != nil)
					u = u.left;
				else
					u = u.right;
			}
			return u;
		}
		return nil;
	}

	/**
	 * Identifies the type of number ordering scheme.
	 * 
	 * @author Eric Dunbar
	 */
	public enum Order {
		PREORDER, INORDER, POSTORDER
	};

	/**
	 * Order visit numbers using iterative traversal. Based off traverse2() by
	 * Pat Morin.
	 * 
	 * @author Eric Dunbar
	 */
	public void orderNumberIterative(Node u, Order o) {
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
			orderNumberIterative(r, Order.PREORDER);
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
			orderNumberIterative(r, Order.POSTORDER);
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
			orderNumberIterative(r, Order.INORDER);
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

	/**
	 * Print the traversal order numbers to System.out.
	 * 
	 * @param pg148 array of type Node
	 * @param numNodes how many Nodes in the array
	 */
	public static void printNumbers(Node[] pg148, int numNodes) {
		int lineWrap = 15;
		int lineEnd = Math.min(lineWrap, numNodes);
		int lineStart = 0;
		// show the pre-order numbers

		System.out.println();
		System.out.println("TRAVERSAL ORDER NUMBERS ASSIGNED TO NODES IN A BINARY TREE");
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

	private static BinaryTreeEric<Node> runFullOrderSimulations(int repeats, int factor) {
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

			pg148 = buildBTPg148(b, idx);

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

	private static Node[] buildBTPg148(BinaryTreeEric<Node> b, int copies) {
		return buildBTPg148Proper(b, copies + 1);
	}

	private static Node[] buildBTPg148Proper(BinaryTreeEric<Node> b, int copies) {
		Node[] pg148 = new Node[1 + 11 * (copies)]; // track nodes as
													// they're
		// added,
		// pg. 148
		int i = 0; // index for node tracking
		// specify root
		pg148[i++] = b.addLowest(b.newNode());
		Node second, tert;

		// multiply the number of nodes
		for (int j = 0; j < copies; j++) {
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

		return pg148;
	}

	public static void main(String[] args) {
		iterative = false;
		// BinaryTreeEric<Node> b = runFullOrderSimulations(1111, 11);

		BinaryTreeEric<Node> b = runOrderNextSimulation();

		// print some trouble-shooting code
		System.out.println("nil? " + b.nil + ", .left? " + b.nil.left + ", .right? " + b.nil.right);
		System.out.println("r " + b.r);
		System.out.println("rl " + b.r.left);
		System.out.println("rll " + b.r.left.left);
		System.out.println("rlll " + b.r.left.left.left);
		// System.out.println(".size() = " + b.size() + ", .height() = " +
		// b.height());
	}

	private static void printOrderHeader(String[] traversal, String[] method, int idx) {
		String outS = String.format(
				"|| %2d. %s-ORDER traversal sequence returned by repeated calls to %s. ||", idx + 1,
				traversal[idx], method[idx]);
		System.out.println();
		System.out.println(CommonSuite.stringRepeat("=", outS.length()));
		System.out.println(outS);
		System.out.println(CommonSuite.stringRepeat("=", outS.length()));
		System.out.println();
	}

	private static BinaryTreeEric<BinaryTreeEric.Node> runOrderNextSimulation() {
		// TODO Auto-generated method stub
		BinaryTreeEric<Node> b = new BinaryTreeEric<>(new Node(), new Node());
		Node[] pg148 = buildBTPg148Proper(b, 1);
		String[] method = { "preorderNext()", "inorderNext()", "postorderNext" };
		String[] traversal = { "PRE", "IN", "POST" };
		int methodIdx = 0;

		b.preOrderNumber(); // generate preOrderNumbers
		b.inOrderNumber(); // generate inOrderNumbers
		b.postOrderNumbers(); // generate postOrderNumbers

		System.out.println();
		System.out.println(
				"Traverse order numbers generated by the 'pre'-, 'post'- and 'inOrderNumber()'");

		printNumbers(pg148, pg148.length);

		// **************************************************
		// PREORDER NUMBERS
		// **************************************************

		printOrderHeader(traversal, method, methodIdx++);

		int i = 0;
		Node[] preOrderNextNums = new Node[pg148.length];
		Node orderNumberCall = b.r;

		while (orderNumberCall != b.nil) {
			preOrderNextNums[i++] = orderNumberCall;
			orderNumberCall = b.preorderNext(orderNumberCall);
		}
		printNumbers(preOrderNextNums, preOrderNextNums.length);
		System.out.println();

		// **************************************************
		// INORDER NUMBERS
		// **************************************************

		printOrderHeader(traversal, method, methodIdx++);

		i = 0;
		Node[] inOrderNextNums = new Node[pg148.length];
		orderNumberCall = preOrderNextNums[2]; // should be firstNode pg148

		while (orderNumberCall != b.nil) {
			inOrderNextNums[i++] = orderNumberCall;
			orderNumberCall = b.inorderNext(orderNumberCall);
		}
		printNumbers(inOrderNextNums, inOrderNextNums.length);
		System.out.println();

		// **************************************************
		// POSTORDER NUMBERS
		// **************************************************

		printOrderHeader(traversal, method, methodIdx++);

		i = 0;
		Node[] postOrderNextNums = new Node[pg148.length];
		orderNumberCall = preOrderNextNums[2]; // should be firstNode pg148

		while (orderNumberCall != b.nil) {
			postOrderNextNums[i++] = orderNumberCall;
			orderNumberCall = b.postorderNext(orderNumberCall);
		}
		printNumbers(postOrderNextNums, postOrderNextNums.length);
		System.out.println();
		return b;
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
