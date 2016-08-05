import java.util.LinkedList;
import java.util.Queue;

/**
 * An implementation of binary trees. Modified by Eric Dunbar for assignment 2,
 * question 1 and question 5. Source: BinaryTree.java by Pat Morin ODS from
 * sample code.
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
	 * START START START START This is the start of additional code added by
	 * Eric Dunbar
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
	 * 1. Design an algorithm for the following operations for a binary tree BT,
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
	 * Binary Tree nodes track pre-order, in-order and post-order numbering for
	 * nodes (pg. 148 Pat Morin ODS) and also track parent, left-child and
	 * right-child node relationships. Created by Eric with inspiration from
	 * Treap.java and assistance from a comment in COMP272 forum that suggested
	 * that a Node class needed to be created.
	 * 
	 * @author Eric Dunbar
	 */
	public static class Node extends BinaryTreeEric.BTENode<Node> {
		final int warning = -99;
		/**
		 * Track the order number of the node if visited by a pre-order routine.
		 * 
		 * @author Eric Dunbar
		 */
		int preOrder = warning;

		/**
		 * Track the order number of the node if visited by an in-order routine.
		 * 
		 * @author Eric Dunbar
		 */
		int inOrder = warning;

		/**
		 * Track the order number of the node if visited by a post-order
		 * routine.
		 * 
		 * @author Eric Dunbar
		 */
		int postOrder = warning;
	}

	/**
	 * Add the given node as the lowest in-order node.
	 * 
	 * @author Eric Dunbar
	 * @param u node to be added
	 * @return a pointer to the added node
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
	 * @param parent Node to which the Node is to be added
	 * @param child the Node that is to be added
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
	 * @param parent Node to which the Node is to be added
	 * @param child the Node that is to be added
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
	public String printTree(Node u, int rank) {
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
	 * pre-order traversal number in each node. Code based on traverse() by Pat
	 * Morin. Fast but causes a StackOverFlow error with a large enough number
	 * of nodes. Runs in O(n) time.
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
	 * traversal. Based off traverse2() by Pat Morin and orderNumberIterative()
	 * by Eric Dunbar.
	 * 
	 * Worst case running time is O(2n) since it potentially has to visit each
	 * node and check the left and right children before it finds the next node.
	 * For example, an unbalanced tree with only left-node children on the left
	 * and a single node on the right would result in the maximum running time.
	 * Best case is O(1) if the given node has a left child.
	 * 
	 * @author Eric Dunbar
	 * @param the current node in the traversal order
	 */
	public Node preorderNext(Node u) {
		Node prev = nil;
		Node next;

		if (u.left != nil)
			return u.left; // RETURN left child
		else if (u.right != nil)
			return u.right; // RETURN right child

		// THIS IS LEAF, go up parent chain until a right-child is found
		prev = u;
		u = u.parent;

		while (u != nil) {
			if (u.left == prev) {
				if (u.right != nil)
					return u.right;
				else
					next = u.parent;
			} else
				next = u.parent;
			prev = u;
			u = next;
		}
		return nil; // if it got this far there was no right child
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
	 * @param the current node in the traversal order
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
		// Note: code could be more efficient. See nextNode().
	}

	/**
	 * Determine the next node for a post-order traversal of the tree. Calling
	 * this repeatedly should build the same tree as what is stored by
	 * post-order traversal. Based off traverse2() & firstNode() by Pat Morin
	 * and, orderNumberIterative() & preorderNext() & inorderNext() by Eric
	 * Dunbar.
	 * 
	 * Worst case running time is O(n) if given the first node in an unbalanced
	 * one left-child, vs. many left-children on the other side tree. It has to
	 * visit all nodes before finding the next lowest node.
	 * 
	 * @author Eric Dunbar
	 * @param u current Node in the traversal order
	 */
	public Node postorderNext(Node u) {
		/*
		 * 1. Go up. a. If you were the right-child return your parent b. If you
		 * were the left-child... return the firstNode, starting at the parent
		 * c. if your parent was nil return nil
		 */

		if (u == nil) {
			return nil;
		}
		Node prev = u;
		u = u.parent;

		// GO UP. If u was right-child, return the parent
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
	 * Identifies the type of number ordering scheme: PREORDER, INORDER or
	 * POSTORDER.
	 * 
	 * @author Eric Dunbar
	 */
	public enum Order {
		PREORDER, INORDER, POSTORDER
	};

	/**
	 * Assign pre-, in- and post-order visit numbers using iterative traversal.
	 * Modified from traverse2() by Pat Morin.
	 * 
	 * Efficiency improvement: there's no reason this method couldn't build all
	 * traversal order variables in one execution of this method.
	 * 
	 * @author Eric Dunbar
	 * @param u starting Node for traversal ordering
	 * @param the type of traversal ordering requested, can be Order.PREORDER,
	 *            Order.INORDER, and Order.POSTORDER
	 */
	public void orderNumberIterative(Node u, Order o) {
		Node prev = nil;
		Node next;
		int preOrderRank = 0;
		int inOrderRank = 0;
		int postOrderRank = 0;
		while (u != nil) {
			if (prev == u.parent) {
				// FIRST VISIT. Arrived at a new node since prev == u.parent
				// Assign PREORDER number here since this is the first visit
				if (o == Order.PREORDER) {
					u.preOrder = preOrderRank++;
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
				// Came from the left child, now what?
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

	/**
	 * Determines whether recursive or iterative code will be used, if
	 * available. Default is set to recursive code. Larger BinaryTrees should be
	 * set to use iterative code, if available.
	 */
	static boolean iterative = false;

	/**
	 * Assign pre-order numbers to nodes in the binary tree. Causes a
	 * StackOverFlow error if used with a recursive implementation and a larger
	 * BinaryTree.
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
	private int postOrderNumberRecursive(Node u, int rank) {
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
	private int inOrderNumberRecursive(Node u, int rank) {
		if (u == nil)
			return rank;
		rank = inOrderNumberRecursive(u.left, rank);
		u.inOrder = rank++;
		rank = inOrderNumberRecursive(u.right, rank);
		return rank;
	}

	/*
	 * END END END END
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
	 * Find the first node in an in-order traversal. Original by Pat Morin.
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
	 * Find the node that follows w in an in-order traversal. Original by Pat
	 * Morin.
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
