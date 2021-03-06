import java.util.ArrayList;
import java.util.Comparator;

public class A3Q6RedBlackTree<T> extends BinarySearchTree<A3Q6RedBlackTree.Node<T>, T>
		implements SSet<T> {

	protected static class Node<T> extends BinarySearchTree.BSTNode<Node<T>, T> {
		byte colour;
	}

	static byte red = 0;
	static byte black = 1;

	public A3Q6RedBlackTree(Comparator<T> c) {
		super(new Node<T>(), new Node<T>(), c);
		nil.colour = black;
	}

	public A3Q6RedBlackTree() {
		this(new DefaultComparator<T>());
	}

	/**
	 * Make u lighter and its children darker
	 * 
	 * @param u
	 */
	protected void pushBlack(Node<T> u) {
		u.colour--;
		u.left.colour++;
		u.right.colour++;
	}

	/**
	 * Make u darker and its children lighter
	 * 
	 * @param u
	 */
	protected void pullBlack(Node<T> u) {
		u.colour++;
		u.left.colour--;
		u.right.colour--;
	}

	protected void flipLeft(Node<T> u) {
		swapColors(u, u.right);
		rotateLeft(u);
	}

	protected void flipRight(Node<T> u) {
		swapColors(u, u.left);
		rotateRight(u);
	}

	/**
	 * Swap the color of u and w
	 * 
	 * @param u
	 * @param w
	 */
	protected void swapColors(Node<T> u, Node<T> w) {
		byte tmp = u.colour;
		u.colour = w.colour;
		w.colour = tmp;
	}

	public boolean add(T x) {
		Node<T> u = newNode(x);
		System.out.printf("newNode(%s) = %s %n", x.toString(), u);
		u.colour = red;
		boolean added = add(u);
		if (added)
			addFixup(u);
		return added;
	}

	/**
	 * Fixup the newly added node u. u is a red node. Each iteration ensures
	 * that (1) u is red, (2) the only red-red edge [if any] is between u and
	 * u.parent (3) the only right-leaning node [if any] is u.parent.
	 * 
	 * @param u
	 */
	protected void addFixup(Node<T> u) {
		while (u.colour == red) {
			if (u == r) { // u is the root - done
				u.colour = black;
				return;
			}
			Node<T> w = u.parent;
			if (w.left.colour == black) { // ensure left-leaning
				flipLeft(w);
				u = w;
				w = u.parent;
			}
			if (w.colour == black)
				return; // no red-red edge = done
			Node<T> g = w.parent; // grandparent of u
			if (g.right.colour == black) {
				flipRight(g);
				return;
			} else {
				pushBlack(g);
				u = g;
			}
		}
	}

	/**
	 * Removes given element and displays removal information.
	 * 
	 * @author Eric Dunbar
	 */
	public boolean remove(T x) {
		Node<T> u = findLast(x);
		if (u == nil || c.compare(u.x, x) != 0)
			return false;
		Node<T> w = u.right;
		if (w == nil) {
			w = u;
			u = w.left;
		} else {
			while (w.left != nil)
				w = w.left;
			u.x = w.x;
			u = w.right;
		}
		splice(w);
		u.colour += w.colour;
		System.out.printf("Remove(%s): u.parent = w.parent;%n", x.toString());
		System.out.printf("BEFORE:    u = %s,   u.parent = %s,        w = %s,  w.parent = %s%n", u,
				u.parent, w, w.parent);
		System.out.printf("BEFORE:  nil = %s, nil.parent = %s, nil.left = %s, nil.right = %s%n",
				nil, nil.parent, nil.left, nil.right);
		u.parent = w.parent;
		System.out.printf(" AFTER:    u = %s,   u.parent = %s,        w = %s,  w.parent = %s%n", u,
				u.parent, w, w.parent);
		System.out.printf(" AFTER:  nil = %s, nil.parent = %s, nil.left = %s, nil.right = %s%n",
				nil, nil.parent, nil.left, nil.right);
		removeFixup(u);
		return true;
	}

	/**
	 * Fixup u after the removal of u's parent. u is a node whose color is
	 * 1(black) or 2(double-black). In the latter case we do work to get rid of
	 * the double-black node.
	 * 
	 * @param u
	 */
	protected void removeFixup(Node<T> u) {
		while (u.colour > black) {
			if (u == r) {
				u.colour = black;
			} else if (u.parent.left.colour == red) {
				u = removeFixupCase1(u);
			} else if (u == u.parent.left) {
				u = removeFixupCase2(u);
			} else {
				u = removeFixupCase3(u);
			}
		}
		if (u != r) { // restore left-leaning property if needed
			Node<T> w = u.parent;
			if (w.right.colour == red && w.left.colour == black) {
				flipLeft(w);
			}
		}
	}

	/**
	 * This case gets applied when the tree looks like this 1 / \ 0 2(u)
	 * 
	 * @param u
	 * @return the next node to fix up
	 */
	protected Node<T> removeFixupCase1(Node<T> u) {
		flipRight(u.parent);
		return u;
	}

	/**
	 * This case gets applied when the tree looks like this ? / \ (u)2 1
	 * 
	 * @param u
	 * @return the next node to fix up
	 */
	protected Node<T> removeFixupCase2(Node<T> u) {
		Node<T> w = u.parent;
		Node<T> v = w.right;
		pullBlack(w); // w.left
		flipLeft(w); // w is now red
		Node<T> q = w.right;
		if (q.colour == red) { // q-w is red-red
			rotateLeft(w);
			flipRight(v);
			pushBlack(q);
			if (v.right.colour == red)
				flipLeft(v);
			return q;
		} else {
			return v;
		}
	}

	/**
	 * This case gets applied when the tree looks like this ? / \ 1 2(u)
	 * 
	 * @param u
	 * @return the next node to fix up
	 */
	protected Node<T> removeFixupCase3(Node<T> u) {
		Node<T> w = u.parent;
		Node<T> v = w.left;
		pullBlack(w);
		flipRight(w); // w is now red
		Node<T> q = w.left;
		if (q.colour == red) { // q-w is red-red
			rotateRight(w);
			flipLeft(v);
			pushBlack(q);
			return q;
		} else {
			if (v.left.colour == red) {
				pushBlack(v); // both v's children are red
				return v;
			} else { // ensure left-leaning
				flipLeft(v);
				return w;
			}
		}
	}

	/**
	 * Debugging function that verifies the red-black tree properties
	 */
	protected void verify() {
		if (size(r) != n)
			throw new IllegalArgumentException("size is incorrect");
		verify(r);
	}

	/**
	 * Debugging function that verifies the red-black tree properties for the
	 * subtree rooted at u
	 * 
	 * @param u
	 * @return the black height of the node u
	 */
	protected int verify(Node<T> u) {
		if (u == nil)
			return u.colour;
		if (u.colour < red || u.colour > black)
			throw new AssertionError("Invalid color: " + u.colour);
		if (u.colour == red)
			if (u.left.colour == red || u.right.colour == red)
				throw new AssertionError("red-red edge found");
		if (u.right.colour == red && u.left.colour != red)
			throw new AssertionError("non-left-leaning node found");
		int dl = verify(u.left);
		int dr = verify(u.right);
		if (dl != dr)
			throw new AssertionError("black-height property violated");
		return dl + u.colour;
	}

	// PRINT THE TREE

	/**
	 * Tracks height and data for each node in the tree for printing purposes.
	 */
	private ArrayList<ArrayList<String>> pT;

	/**
	 * Add data to a data structure that tracks elements and ranks (height) of
	 * the Nodes in the BinarySearchTree. This can be used to display the tree.
	 * 
	 * @param n height with 0 being root
	 * @param x data element
	 */
	private void addToPT(int n, T x) {
		while (pT.size() < n + 1)
			pT.add(new ArrayList<String>());
		pT.get(n).add(x.toString() + " (" + findNode(x).colour + ")");
	}

	public void printBSTree() {
		printBSTree(r);
	}

	/**
	 * Print the binary search sub-tree starting at the given Node.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 */
	public void printBSTree(Node<T> u) {
		pT = new ArrayList<ArrayList<String>>();

		int n = 0;
		constructBSTPTree(u, n);

		System.out.println();
		System.out.println("NOTE: positions are only correct for (a) depth or (b) horizontal position,");
		System.out.println("      but not both at the same time.");
		System.out.println();

		for (int i = 1; i < pT.size(); i++) {
			System.out.printf("d %3d: ", i-1);
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
	// END PRINT

	private static void demoRedblack() {
		A3Q6RedBlackTree<Integer> s = new A3Q6RedBlackTree<Integer>();

		Integer n[] = {  50, 44, 62,32, 88, 48, 17, 78 };
		// sorted sequence
		for (int i = 0; i < n.length; i++) {
			s.add(n[i]);
			s.printBSTree();
		}

		System.out.printf("root = %s; colour = %s%n", s.r.x.toString(), s.r.colour);
		System.out.println();

		for (int i = 0; i < n.length; i++) {
			Node<Integer> u = s.findNode(n[i]);
			System.out.printf("x = %s; colour = %s%n", u.x.toString(), u.colour);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommonSuite.commonProgramStart(3, 6, "RedBlack Tree Demo", false);

		demoRedblack();
	}

}
