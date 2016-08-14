

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;


public class ScapegoatTree<T> 
		extends BinarySearchTree<ScapegoatTree.Node<T>,T> {
	/**
	 * An overestimate of n
	 */
	int q;
	
	protected static class Node<T> extends BinarySearchTree.BSTNode<Node<T>,T> {	}
	
	public ScapegoatTree(Comparator<T> c) {
		super(new Node<T>(), c);
	}
	
	public ScapegoatTree() {
		this(new DefaultComparator<T>());
	}
	
	public boolean remove(T x) {
		if (super.remove(x)) {
			if (2*n < q) {
				rebuild(r);
				q = n;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Compute the ceiling of log_{3/2}(q)
	 * @param q
	 * @return the ceiling of log_{3/2}(q)
	 */
	protected static final int log32(int q) {
		final double log23 = 2.4663034623764317;
		return (int)Math.ceil(log23*Math.log(q));
	}

	/***
	 * Do a normal BinarySearchTree insertion, but return the depth
	 * of the newly inserted node. 
	 * @param u - the new node to insert
	 * @return the depth of the newly inserted node, or -1 if the node
	 * was not inserted
	 */
	int addWithDepth(Node<T> u) {
		Node<T> w = r;
		if (w == nil) {
			r = u;
			n++; q++;
			return 0;
		}
		boolean done = false;
		int d = 0;
		do {
			int res = c.compare(u.x, w.x);
			if (res < 0) {
				if (w.left == nil) {
					w.left = u;
					u.parent = w;
					done = true;
				} else {
					w = w.left;
				}
			} else if (res > 0) {
				if (w.right == nil) {
					w.right = u;
					u.parent = w;
					done = true;
				}
				w = w.right;
			} else {
				return -1;
			}
			d++;
		} while (!done);
		n++; q++;
		return d;
	}

	public boolean add(T x) {
		// first do basic insertion keeping track of depth
		Node<T> u = newNode(x);
		int d = addWithDepth(u);
		if (d > log32(q)) {
			// depth exceeded, find scapegoat
			Node<T> w = u.parent;
			while (3*size(w) <= 2*size(w.parent))
				w = w.parent;
			rebuild(w.parent);
		}
		return d >= 0;
	}

	@SuppressWarnings("unchecked")
	protected void rebuild(Node<T> u) {
		int ns = size(u);
		Node<T> p = u.parent;
		Node<T>[] a = (Node<T>[]) Array.newInstance(Node.class, ns);
		packIntoArray(u, a, 0);
		if (p == nil) {
			r = buildBalanced(a, 0, ns);
			r.parent = nil;
		} else if (p.right == u) {
			p.right = buildBalanced(a, 0, ns);
			p.right.parent = p;
		} else {
			p.left = buildBalanced(a, 0, ns);
			p.left.parent = p;
		}
	}

	/**
	 * A recursive helper that packs the subtree rooted at u into
	 * a[i],...,a[i+size(u)-1]
	 * 
	 * @param u
	 * @param a
	 * @param i
	 * @return size(u)
	 */
	protected int packIntoArray(Node<T> u, Node<T>[] a, int i) {
		if (u == nil) {
			return i;
		}
		i = packIntoArray(u.left, a, i);
		a[i++] = u;
		return packIntoArray(u.right, a, i);
	}

	/**
	 * A recursive helper that builds a perfectly balanced subtree out of
	 * a[i],...,a[i+ns-1]
	 * 
	 * @param a
	 * @param i
	 * @param ns
	 * @return the rooted of the newly created subtree
	 */
	protected Node<T> buildBalanced(Node<T>[] a, int i, int ns) {
		if (ns == 0)
			return nil;
		int m = ns / 2;
		a[i + m].left = buildBalanced(a, i, m);
		if (a[i + m].left != nil)
			a[i + m].left.parent = a[i + m];
		a[i + m].right = buildBalanced(a, i + m + 1, ns - m - 1);
		if (a[i + m].right != nil)
			a[i + m].right.parent = a[i + m];
		return a[i + m];
	}
	
	// PRINTING CODE FROM BSTProperty. Eric's addition
	
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

	public void printBSTPTree() {
		printBSTPTree(r);
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


}
