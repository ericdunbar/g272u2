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

	private static <T> boolean testSearchTreeProperty(Node<T> n) {
		
		return false;

	}

	private static void demoSearchTree() {

	}

	public static void main(String[] args) {
		printTheory();
		System.out.println();
		printTheory2();
		System.out.println();
		printQuestion();
	}

}
