
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * ScapegoatTree modified by Eric Dunbar from Open Data Structures by Pat Morin.
 * 
 * @author Eric Dunbar
 * @date Aug 16, 2016
 * @title ScapegoatTree
 * @assignment 2
 *
 * @param <T>
 */
public class ScapegoatTree<T> extends BinarySearchTree<ScapegoatTree.Node<T>, T> {
	/**
	 * An overestimate of n
	 */
	int q;
	int credits = 0;

	protected static class Node<T> extends BinarySearchTree.BSTNode<Node<T>, T> {
		int credit = 0;
	}

	public ScapegoatTree(Comparator<T> c) {
		super(new Node<T>(), c);
		credits = 0;
	}

	public ScapegoatTree() {
		this(new DefaultComparator<T>());
	}

	public boolean remove(T x) {
		System.out.println("note: does not implement credit scheme");
		System.out.println("Remove(" + x + ")");
		if (super.remove(x)) {
			if (2 * n < q) {
				rebuild(r);
				q = n;
			}
			return true;
		}
		return false;
	}

	/**
	 * Compute the ceiling of log_{3/2}(q)
	 * 
	 * @param q
	 * @return the ceiling of log_{3/2}(q)
	 */
	// protected static final int log32(int q) {
	protected static final double log32(int q) {
		final double log23 = 2.4663034623764317;
		double vD = log23 * Math.log(q);
		int v = (int) Math.ceil(vD);
		// return v;
		return vD;
	}

	/***
	 * Do a normal BinarySearchTree insertion, but return the depth of the newly
	 * inserted node.
	 * 
	 * @param u - the new node to insert
	 * @return the depth of the newly inserted node, or -1 if the node was not
	 *         inserted
	 */
	int addWithDepth(Node<T> u) {
		System.out.println("AWD:   node pointer(" + u + ")");
		Node<T> w = r;
		if (w == nil) {
			r = u;
			n++;
			q++;
			System.out.printf(
					"AWD:   (root created) Increment q to %d; increment n to %d; add %s%n", q, n,
					u.x.toString());
			return 0;
		}
		boolean done = false;
		int d = 0;
		do {
			int res = c.compare(u.x, w.x);
			if (res < 0) {
				if (w.left == nil) {
					w.credit++;
					credits++;

					w.left = u;
					u.parent = w;
					done = true;
				} else {
					w.credit++;
					credits++;

					w = w.left;
				}
			} else if (res > 0) {
				if (w.right == nil) {
					w.right = u;
					u.parent = w;
					done = true;
				}
				w.credit++;
				credits++;
				w = w.right;
			} else {
				return -1;
			}
			d++;
		} while (!done);
		n++;
		q++;
		System.out.printf("AWD:   Increment q to %d; increment n to %d; add %s%n", q, n,
				u.x.toString());
		return d;
	}

	private void printRebuildData(Node<T> w, int sizeW, int sizeWParent) {
		System.out.printf(
				"w.x = %d, w.parent.x = %d; size(w)/size(w.parent) = %d/%d; %d/%d > 2/3 = %s%n",
				w.x, w.parent.x, sizeW, sizeWParent, sizeW, sizeWParent,
				(((double) sizeW / sizeWParent) > (2.0 / 3)));

	}

	public boolean add(T x) {
		// first do basic insertion keeping track of depth
		Node<T> u = newNode(x);
		int d = addWithDepth(u);
		System.out.printf("add:   add(%d) = %s; d = %d; %s = %d; %s(%s) = %f%n", x, d >= 0, d, "q",
				q, "log32", "q", log32(q));
		if (d > log32(q)) {
			System.out.println();
			System.out.println("Rebuild operation required:");
			System.out.println("    searching for scapegoat where size(w)/size(w.parent) > 2/3");
			System.out.println();

			// depth exceeded, find scapegoat
			Node<T> w = u.parent;
			int sizeW = size(w);
			int sizeWParent = size(w.parent);

			printRebuildData(w, sizeW, sizeWParent);
			while (3 * sizeW <= 2 * sizeWParent) {
				w = w.parent;
				sizeW = size(w);
				sizeWParent = size(w.parent);
				printRebuildData(w, sizeW, sizeWParent);
			}
			printRebuildData(w, sizeW, sizeWParent);
			System.out.println();
			System.out.println();

			rebuild(w.parent);
		}
		System.out.println();
		return d >= 0;
	}

	@SuppressWarnings("unchecked")
	protected void rebuild(Node<T> u) {
		System.out.println("Starting rebuild at node (" + u + ")");
		System.out.println();
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
		System.out.println("packIntoArray(" + u + "," + i + ")");
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
		System.out.print("buildBalanced(" + i + ", " + ns + ")");
		if (ns == 0) {
			System.out.println("    nil");
			return nil;
		}
		int m = ns / 2;
		System.out.printf("    element = %s%n", a[i + m].x.toString());
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
	private ArrayList<ArrayList<String>> pS;
	private ArrayList<ArrayList<Integer>> pCredits;

	/**
	 * Add data to a data structure that tracks elements and ranks (height) of
	 * the Nodes in the BinarySearchTree. This can be used to display the tree.
	 * 
	 * @param n height with 0 being root
	 * @param x data element
	 */
	private void addToPT(int n, T x, String s, int creditt) {
		while (pT.size() < n + 1) {
			pT.add(new ArrayList<T>());
			pS.add(new ArrayList<String>());
			pCredits.add(new ArrayList<Integer>());
		}
		pT.get(n).add(x);
		pS.get(n).add(s);
		pCredits.get(n).add(creditt);
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
		pS = new ArrayList<ArrayList<String>>();
		pCredits = new ArrayList<ArrayList<Integer>>();

		int n = 0;
		constructBSTPTree(u, n, "ROOT");

		System.out.println();
		System.out.println("CAUTION: horizontal positions are correct only relative to each other");

		for (int i = 0; i < pT.size(); i++) {
			int theSize = pT.get(i).size();
			int baseWidth = 90;
			String thePadding = CommonSuite.stringRepeat(" ",
					(int) ((baseWidth - 3 * theSize) / (theSize + 1)));
			for (int j = 0; j < theSize; j++)
				System.out.printf("%s%s: %3s (credit %d)", thePadding, pS.get(i).get(j),
						pT.get(i).get(j), pCredits.get(i).get(j));
			System.out.println();
		}
	}

	/**
	 * Helper method to collect information to print the binary search tree.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 * @param num starting height (0 = root)
	 */
	private void constructBSTPTree(Node<T> u, int num, String s) {
		num++;
		addToPT(num, u.x, s, u.credit);
		System.out.printf("%d: %s ", num, u.x);
		if (u.left != nil)
			constructBSTPTree(u.left, num, "LEFT");
		if (u.right != nil)
			constructBSTPTree(u.right, num, "RIGHT");
	}

}
