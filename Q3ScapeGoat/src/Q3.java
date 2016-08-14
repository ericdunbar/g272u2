/**
 * 
 */

/*
 * public class ScapegoatTree<T> 
		extends BinarySearchTree<ScapegoatTree.Node<T>,T> {
	/**
	 * An overestimate of n
	 */
//	int q;
	
//	protected static class Node<T> extends BinarySearchTree.BSTNode<Node<T>,T> {	}

/**
 * @author Eric Dunbar
 * @date Aug 14, 2016
 * @title
 * @assignment 2
 *
 */
public class Q3 {

	/**
	 * @author Eric Dunbar
	 * @date Aug 14, 2016
	 * @title
	 * @assignment 2
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		ScapegoatTree<Integer> sg;
		
		sg = new ScapegoatTree<>();
		
		Integer a[] = { 1, 5, 2, 4, 3};
		
		for (int i = 0; i < a.length; i++) {
			sg.add(a[i]);
		}
		
		sg.printBSTPTree();
	}

}
