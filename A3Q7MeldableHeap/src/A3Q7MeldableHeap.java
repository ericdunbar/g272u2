import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

/**
 * A program to demonstrate a remove(Node<T> u) method for MeldableHeap. The
 * remove(Node<T> u) method removes a given node from the MeldableHeap.
 * 
 * TODO: Should implement Queue interface
 * 
 * @author Eric Dunbar (remove(Node<T> u) method and a few others)
 * @author morin (most of the MeldableHeap)
 * @date Aug 26, 2016
 * @title remove(u) from MeldableHeap
 * @assignment 3
 *
 * @param <T>
 */
public class A3Q7MeldableHeap<T> extends BinaryTree<A3Q7MeldableHeap.Node<T>> implements Queue<T> {

	protected Random rand;

	protected int n;

	Comparator<T> c;

	protected static class Node<T> extends BinaryTree.BTNode<Node<T>> {
		T x;
	}

	public A3Q7MeldableHeap() {
		this(new DefaultComparator<T>());
	}

	public A3Q7MeldableHeap(Comparator<T> c0) {
		super(new Node<T>());
		c = c0;
		rand = new Random();
		sampleNode = newNode();
	}

	public boolean add(T x) {
		Node<T> u = newNode();
		u.x = x;
		r = merge(u, r);
		r.parent = nil;
		n++;
		return true;
	}

	public T findMin() {
		return r.x;
	}

	public T remove() {
		T x = r.x;
		r = merge(r.left, r.right);
		if (r != nil)
			r.parent = nil;
		n--;
		return x;
	}

	public Node<T> merge(Node<T> h1, Node<T> h2) {
		if (h1 == nil)
			return h2;
		if (h2 == nil)
			return h1;
		if (c.compare(h2.x, h1.x) < 0)
			return merge(h2, h1);
		// now we know h1.x <= h2.x
		if (rand.nextBoolean()) {
			h1.left = merge(h1.left, h2);
			h1.left.parent = h1;
		} else {
			h1.right = merge(h1.right, h2);
			h1.right.parent = h1;
		}
		return h1;
	}

	public T element() {
		if (r == nil)
			throw new NoSuchElementException();
		return r.x;
	}

	public boolean offer(T x) {
		return add(x);
	}

	public T peek() {
		return r == nil ? null : r.x;
	}

	public T poll() {
		return r == nil ? null : remove();
	}

	public boolean addAll(Collection<? extends T> c) {
		for (T x : c)
			add(x);
		return !c.isEmpty();
	}

	public boolean contains(Object x) {
		for (T y : this) {
			if (y.equals(x))
				return true;
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object x : c) {
			if (!contains(x))
				return false;
		}
		return true;
	}

	public Iterator<T> iterator() {
		class MHI implements Iterator<T> {
			protected Node<T> w, prev;

			public MHI(Node<T> iw) {
				w = iw;
			}

			public boolean hasNext() {
				return w != nil;
			}

			public T next() {
				T x = w.x;
				prev = w;
				w = nextNode(w);
				return x;
			}

			public void remove() {
				A3Q7MeldableHeap.this.remove(prev);
			}
		}
		Node<T> w = r;
		while (w.left != nil)
			w = w.left;
		return new MHI(w);
	}

	public boolean remove(Object x) {
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			if (it.next().equals(x)) {
				remove();
				return true;
			}
		}
		return false;
	}

	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object x : c) {
			modified = modified || remove(x);
		}
		return modified;
	}

	public boolean retainAll(Collection<?> c) {
		boolean modified = false;
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	public int size() {
		return n;
	}

	public void clear() {
		r = nil;
		n = 0;
	}

	public Object[] toArray() {
		Object[] a = new Object[n];
		int i = 0;
		for (T x : this) {
			a[i++] = x;
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	public <T2> T2[] toArray(T2[] a) {
		if (a.length < n) {
			a = (T2[]) Array.newInstance(a.getClass().getComponentType(), n);
		}
		int i = 0;
		for (T x : this) {
			a[i++] = (T2) x;
		}
		return a;
	}

	// THE PROGRAM
	// THE PROGRAM
	// THE PROGRAM

	public static void main(String[] args) {

		// display programmer info
		CommonSuite.commonProgramStart(3, 7, "Meldable Heap Remove", false);

		// re-create the heaps from ODS
		Integer meldableHeapPage218_1[] = new Integer[] { 4, 9, 8, 17, 26, 50, 16, 19, 55 };
		Integer meldableHeapPage218_2[] = new Integer[] { 19, 25, 20, 28, 89, 32, 93, 99 };

		A3Q7MeldableHeap<Integer> h, j;

		h = new A3Q7MeldableHeap<Integer>();
		j = new A3Q7MeldableHeap<Integer>();

		for (int i = 0; i < meldableHeapPage218_1.length; i++)
			h.add(meldableHeapPage218_1[i]);

		for (int i = 0; i < meldableHeapPage218_2.length; i++)
			j.add(meldableHeapPage218_2[i]);

		// show the heaps before and after they are merged
		CommonSuite.printFancyHeader("MeldableHeap h");
		h.printBSTree();
		CommonSuite.printFancyHeader("MeldableHeap j");
		j.printBSTree();
		h.merge(h.r, j.r);
		CommonSuite.printFancyHeader("MERGE of MeldableHeap h and j: h.merge(h.r, j.r)");
		h.printBSTree();
		CommonSuite.printFancyHeader("Pointer & element originally at j.r.right.right");
		try {
			System.out.printf("j.r.R.R %s = %s%n", j.r.right.right, j.r.right.right.x);
		} catch (Exception e) {
			System.out.println("Node empty. Run simulation again. No nodes removed.");
			System.out.println(e.toString());
		}

		// demonstrate the remove(u) method
		CommonSuite.printFancyHeader("REMOVE j.r.right.right: h.remove(j.right.right)");
		h.remove(j.r.right.right);
		h.printBSTree();
	}

	// END OF THE PROGRAM
	// END OF THE PROGRAM
	// END OF THE PROGRAM

	// THE ACTUAL QUESTION
	// THE ACTUAL QUESTION
	// THE ACTUAL QUESTION

	/**
	 * Removes the node from the Meldable Heap. Null is returned if the node is
	 * nil so null values cannot be stored in this meldable heap.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 25, 2016
	 * @title Remove node from meldable heap
	 * @assignment 3
	 *
	 * @param u node to remove
	 * @return element contained in node that was removed, null if node is nil
	 */
	public T remove(Node<T> u) {
		if (u == nil || u == null)
			return null;

		T element = u.x;

		// merge children of removed node
		Node<T> subroot = merge(u.left, u.right);
		if (subroot != nil)
			subroot.parent = u.parent;

		if (u == r) // root was removed
			u = subroot;
		else if (u.parent.left == u)
			u.parent.left = subroot;
		else
			u.parent.right = subroot;
		n--;
		return element;
	}

	/**
	 * Note by Eric Dunbar: method is included here for comparison.
	 * 
	 * @author Pat Morin
	 * @date Unknown
	 * @title n/a
	 * @assignment n/a
	 *
	 * @param u
	 */
	protected void xremove(Node<T> u) {
		if (u == r) {
			remove();
		} else {
			if (u == u.parent.left) {
				u.parent.left = nil;
			} else {
				u.parent.right = nil;
			}
			u.parent = nil;
			r = merge(r, u.left);
			r = merge(r, u.right);
			r.parent = nil;
			n--;
		}
	}

	// END OF THE ACTUAL QUESTION
	// END OF THE ACTUAL QUESTION
	// END OF THE ACTUAL QUESTION

	// DISPLAY BINARY TREE
	// DISPLAY BINARY TREE
	// DISPLAY BINARY TREE

	/**
	 * Tracks height and data for each node in the tree for printing purposes.
	 */
	private ArrayList<ArrayList<String>> pT;

	/**
	 * Tracks elements in the tree as a list.
	 */
	private ArrayList<T> elements;

	/**
	 * Add data to a data structure that tracks elements and ranks (height) of
	 * the Nodes in the BinarySearchTree. This can be used to display the tree.
	 * 
	 * @param n height with 0 being root
	 * @param x data element
	 */
	private void addToPT(int n, String x) {
		while (pT.size() < n + 1)
			pT.add(new ArrayList<String>());
		pT.get(n).add(x);
	}

	public ArrayList<T> printBSTree() {
		return printBSTree(r);
	}

	/**
	 * Print the binary search sub-tree starting at the given Node.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 */
	private ArrayList<T> printBSTree(Node<T> u) {

		ArrayList<T> breadth = constructTree(u); // very wasteful
		constructBSTree(u); // very wasteful
		System.out.println(breadth);

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
		return breadth;
	}

	public ArrayList<T> constructBSTree() {
		return constructBSTree(r);
	}

	public ArrayList<T> constructBSTree(Node<T> u) {
		pT = new ArrayList<ArrayList<String>>();
		elements = new ArrayList<T>();

		int n = 0;
		constructBSTree(u, n, "X");
		return elements;
	}

	/**
	 * Creates an ArrayList<T> containing the data elements, in a breadth-first
	 * traversal order from a binary tree. Source code for bfTraversal from Pat
	 * Morin's ODS. Use this code as the basis for a rewrite of
	 * constructBSTree()
	 * 
	 * @author Eric Dunbar
	 * @return
	 * @date Aug 23, 2016
	 * @title
	 * @assignment 3
	 *
	 * @return
	 */
	public ArrayList<T> constructTree() {
		return constructTree(r);
	}

	/**
	 * Creates an ArrayList<T> containing the data elements, in a breadth-first
	 * traversal order from a binary tree. Source code for bfTraversal from Pat
	 * Morin's ODS. Use this code as the basis for a rewrite of
	 * constructBSTree()
	 * 
	 * @author Eric Dunbar
	 * @return
	 * @date Aug 23, 2016
	 * @title
	 * @assignment 3
	 *
	 * @return
	 */
	private ArrayList<T> constructTree(Node<T> z) {
		elements = new ArrayList<T>();
		Queue<Node<T>> q = new LinkedList<Node<T>>();

		if (z != nil)
			q.add(z);
		while (!q.isEmpty()) {
			Node<T> u = q.remove();
			elements.add(u.x);
			if (u.left != nil)
				q.add(u.left);
			if (u.right != nil)
				q.add(u.right);
		}
		return elements;
	}

	/**
	 * Recursive helper method to collect information to print the binary search
	 * tree. Does a pre-order traversal.
	 * 
	 * @author Eric Dunbar
	 * @param u the root Node for the sub-tree
	 * @param n starting height (0 = root)
	 */
	private void constructBSTree(Node<T> u, int n, String lr) {
		n++;
		addToPT(n, lr + " " + u.x.toString());
		elements.add(u.x);
		if (u.left != nil)
			constructBSTree(u.left, n, lr + "L");
		if (u.right != nil)
			constructBSTree(u.right, n, lr + "R");
	}

	// DISPLAY BINARY TREE
	// DISPLAY BINARY TREE
	// DISPLAY BINARY TREE
}
