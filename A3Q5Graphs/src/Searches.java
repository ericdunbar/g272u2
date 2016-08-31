import java.util.Queue;
import java.util.Stack;

/**
 * @author Pat Morin (original)
 * @author Eric Dunbar (modified0
 * @date Aug 30, 2016
 * @title
 * @assignment 3
 *
 */
public class Searches {
	/**
	 * Perform a bread-first search of g starting at vertex i
	 * 
	 * @param g
	 * @param i
	 */
	public static void bfs(Graph g, int r) {
		boolean[] seen = new boolean[g.nVertices()];
		Queue<Integer> q = new SLList<Integer>();
		q.add(r);
		seen[r] = true;
		while (!q.isEmpty()) {
			int i = q.remove();
			for (Integer j : g.outEdges(i)) {
				if (!seen[j]) {
					q.add(j);
					seen[j] = true;
				}
			}
		}
	}

	public static void bfsZ(Graph g, int r) {
		boolean[] seen = new boolean[g.nVertices()];
		Queue<Integer> q = new SLList<Integer>();
		edge = 0;
		q.add(r);
		seen[r] = true;
		while (!q.isEmpty()) {
			int i = q.remove();
			for (Integer j : g.outEdges(i)) {
				if (!seen[j]) {
					System.out.printf("%2d. %s => %s%n", ++edge, DemoGraphs.numToAP(i),
							DemoGraphs.numToAP(j));
					q.add(j);
					seen[j] = true;
				}
			}
		}
	}

	protected static byte white = 0, grey = 1, black = 2;

	/**
	 * Recursive implementation of DFS
	 * 
	 * @param g
	 * @param i
	 */
	public static void dfs(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		dfs(g, r, c);
	}

	public static void dfs(Graph g, int i, byte[] c) {
		c[i] = grey; // currently visiting i
		for (Integer j : g.outEdges(i)) {
			if (c[j] == white) {
				c[j] = grey;
				dfs(g, j, c);
			}
		}
		c[i] = black; // done visiting i
	}

	static int edge = 0;

	public static void dfsZ(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		edge = 0;
		dfsZ(g, r, c);
	}

	public static void dfsZ(Graph g, int i, byte[] c) {
		c[i] = grey; // currently visiting i
		for (Integer j : g.outEdges(i)) {
			if (c[j] == white) {
				System.out.printf("%2d. %s => %s%n", ++edge, DemoGraphs.numToAP(i),
						DemoGraphs.numToAP(j));
				c[j] = grey;
				dfsZ(g, j, c);
			}
		}
		c[i] = black; // done visiting i
	}

	/**
	 * Generates a path using a depth-first traversal order that traverses every
	 * undirected edge once in an undirected graph.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 30, 2016
	 * @title create path
	 * @assignment 3
	 *
	 * @param g graph to be traversed
	 * @param r starting vertex
	 */
	public static void doublePathZ(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		edge = 0;
		doublePathZ(g, r, c);
	}

	/**
	 * Generates a path using a depth-first traversal order that traverses every
	 * undirected edge once in an undirected graph. Caution: the class variable edge
	 * is not reset when this method is called.
	 * 
	 * @author Eric Dunbar
	 * @date Aug 30, 2016
	 * @title create path
	 * @assignment 3
	 *
	 * @param g graph to be traversed
	 * @param r starting vertex
	 * @param c array of bytes tracking colour for each vertex (use doublePathZ(g, r))
	 */
	public static void doublePathZ(Graph g, int i, byte[] c) {
		c[i] = grey; // currently visiting i
		for (Integer j : g.outEdges(i)) {
			if (c[j] == white) {
				System.out.printf("%2d. %s => %s%n", ++edge, DemoGraphs.numToAP(i),
						DemoGraphs.numToAP(j));
				c[j] = grey;
				doublePathZ(g, j, c);
			} else {
				System.out.printf("%2d. %s => %s (reverse edge)%n", ++edge, DemoGraphs.numToAP(i),
						DemoGraphs.numToAP(j));
			}
		}
		c[i] = black; // done visiting i
	}

	/**
	 * A non-recursive implementation of dfs Note, this doesn't give exactly the
	 * same traversal as dfs(g,r)
	 * 
	 * @param g
	 * @param r
	 */
	public static void dfs2(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		Stack<Integer> s = new Stack<Integer>();
		s.push(r);
		while (!s.isEmpty()) {
			int i = s.pop();
			if (c[i] == white) {
				c[i] = grey;
				for (int j : g.outEdges(i))
					s.push(j);
			}
		}
	}

	public static void dfs2Z(Graph g, int r) {
		byte[] c = new byte[g.nVertices()];
		Stack<Integer> s = new Stack<Integer>();
		s.push(r);
		while (!s.isEmpty()) {
			int i = s.pop();
			if (c[i] == white) {
				c[i] = grey;
				System.out.println(i);
				for (int j : g.outEdges(i))
					s.push(j);
			}
		}
	}

}
