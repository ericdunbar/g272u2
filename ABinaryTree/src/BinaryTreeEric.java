import java.util.Comparator;
import java.util.Iterator;

public class BinaryTreeEric<Node extends BinaryTreeEric.BTENode<Node, T>, T> extends BinaryTree<Node>
		implements SSet<T> {

	public static class BTENode<Node extends BTENode<Node, T>, T> extends BinaryTree.BTNode<Node> {
		T x;
	}

	protected Comparator<T> c;

	public BinaryTreeEric(Node sampleNode, Node nil, Comparator<T> c) {
		super(sampleNode, nil);
		this.c = c; 
	}

	public BinaryTreeEric(Node sampleNode, Comparator<T> c) {
		super(sampleNode);
		this.c = c; 
	}

	public BinaryTreeEric(Node sampleNode) {
		this(sampleNode, new DefaultComparator<T>());
	}

	/**
	 * Eric's addition
	 * 
	 * @author Eric
	 *
	 * @param <T>
	 */
	protected static class Node<T> extends BTNode<Node<T>> {
		byte colour;
	}

	/**
	 * Eric's addition
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BinaryTreeEric success;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparator<? super T> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T find(T x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findGE(T x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T findLT(T x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(T x) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(T x) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<T> iterator(T x) {
		// TODO Auto-generated method stub
		return null;
	}

}
