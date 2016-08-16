import java.util.Arrays;

/**
 * Demonstrates the use of traversal order methods for questions 1 and 5 of
 * assignment 2 in COMP 272.
 * 
 * @author Eric Dunbar
 * @date Aug 11, 2016
 * @title Demo of traversal order for questions 1 and 5
 * @assignment 2
 *
 */
public class Q1TraversalMethodsDemo {

	/**
	 * Trivial, private method used to format and print numbers for
	 * printNumbers().
	 * 
	 * @author Eric Dunbar
	 * 
	 * @param number
	 */
	private static void supportPrintNumber(int number) {
		System.out.printf("%4d ", number);
	}

	private static void printOrderHeader(String[] traversal, String[] method, int idx) {
		String outS = String.format(
				"%2d. %s-ORDER traversal sequence returned by repeated calls to %s.", idx + 1,
				traversal[idx], method[idx]);
		CommonSuite.printFancyHeader(outS);
	}

	/**
	 * Print the pre-, in- and post-order traversal numbers to System.out.
	 * 
	 * @param pg148 array of type Node
	 * @param numNodes how many Nodes in the array
	 */
	private static void printNumbers(EricBinaryTree.Node[] pg148, int numNodes) {
		int lineWrap = 15;
		int lineEndIdx = Math.min(lineWrap, numNodes);
		int lineStartIdx = 0;

		System.out.println();
		System.out.println("TRAVERSAL ORDER NUMBERS ASSIGNED TO NODES IN A BINARY TREE");
		System.out.println();
		while (lineStartIdx < numNodes) {
			System.out.print("  Pre: ");
			for (int j = lineStartIdx; j < lineEndIdx; j++) {
				supportPrintNumber(pg148[j].preOrder);
			}
			System.out.println(); // show the post-order numbers
			System.out.print(" Post: ");
			for (int j = lineStartIdx; j < lineEndIdx; j++) {
				supportPrintNumber(pg148[j].postOrder);
			}
			System.out.println(); // show the in-order numbers
			System.out.print("   In: ");
			for (int j = lineStartIdx; j < lineEndIdx; j++) {
				supportPrintNumber(pg148[j].inOrder);
			}
			System.out.println();
			System.out.println();
			lineStartIdx += lineWrap;
			lineEndIdx = Math.min(lineEndIdx + lineWrap, numNodes);
		}
	}

	public static EricBinaryTree.Node[] buildBTPg148(EricBinaryTree<EricBinaryTree.Node> b,
			int copies) {
		return buildBTPg148Proper(b, copies + 1);
	}

	/**
	 * Create the binary tree as shown on page 148 of ODS by Pat Morin.
	 * Recreates the pre-order, in-order and post-order traversal order.
	 * 
	 * @param b binary tree to be constructed
	 * @param copies number of copies of the sub-tree to be used
	 * @return array of Nodes in pre-order traversal order
	 */
	public static EricBinaryTree.Node[] buildBTPg148Proper(EricBinaryTree<EricBinaryTree.Node> b,
			int copies) {
		// track nodes as they're added, pg. 148
		EricBinaryTree.Node[] pg148 = new EricBinaryTree.Node[1 + 11 * (copies)];

		int nodeIdx = 0;
		pg148[nodeIdx++] = b.addLowest(b.newNode()); // create root
		EricBinaryTree.Node second, tert;

		// make multiple copies of the original binary tree
		for (int j = 0; j < copies; j++) {
			// Add nodes in pre-order traversal

			// Build left half
			pg148[nodeIdx++] = second = b.addLowest(b.newNode()); // 1st LC pre1
			pg148[nodeIdx++] = b.addLowest(b.newNode()); // 2nd LC pre2
			pg148[nodeIdx++] = tert = b.addRight(second, b.newNode()); // pre3
			pg148[nodeIdx++] = b.addLeft(tert, b.newNode()); // pre4
			pg148[nodeIdx++] = b.addRight(tert, b.newNode()); // pre5

			// Build right half
			// start at root node, or, nodeIdx-7 for subsequent additions
			pg148[nodeIdx++] = second = b.addRight(pg148[nodeIdx - 7], b.newNode());
			pg148[nodeIdx++] = tert = b.addLeft(second, b.newNode());
			pg148[nodeIdx++] = b.addLeft(tert, b.newNode());
			pg148[nodeIdx++] = tert = b.addRight(second, b.newNode());
			pg148[nodeIdx++] = b.addLeft(tert, b.newNode());
			pg148[nodeIdx++] = b.addRight(tert, b.newNode());
		}
		return pg148;
	}

	private static EricBinaryTree<EricBinaryTree.Node> question1NextOrderSimulation() {
		String[] details = {
				"Design an algorithm for the following operations for a binary tree BT,",
				"and show the worst-case running times for each implementation:", "",
				"1 Binary Tree", "",
				"a. preorderNext(x): return the node visited after node x in a pre-order",
				"traversal of BT.", "",
				"b. postorderNext(x): return the node visited after node x in a post-order",
				"traversal of BT.", "",
				"c. inorderNext(x): return the node visited after node x in an in-order",
				"traversal of BT." };

		CommonSuite.printDescription("Question 1: Determine next node in a BinaryTree traversal",
				details);
		System.out.println();

		EricBinaryTree<EricBinaryTree.Node> b = new EricBinaryTree<>(new EricBinaryTree.Node(),
				new EricBinaryTree.Node());
		EricBinaryTree.Node[] pg148 = buildBTPg148Proper(b, 1);
		String[] method = { "preorderNext()", "inorderNext()", "postorderNext()" };
		String[] traversal = { "PRE", "IN", "POST" };
		int methodIdx = 0;

		b.preOrderNumber(); // generate preOrderNumbers
		b.inOrderNumber(); // generate inOrderNumbers
		b.postOrderNumbers(); // generate postOrderNumbers

		System.out.println();
		CommonSuite.printFancyHeader(
				"Traverse order numbers generated by the 'pre'-, 'post'- and 'inOrderNumber()'");

		printNumbers(pg148, pg148.length);

		// **************************************************
		// PREORDER NUMBERS
		// **************************************************

		printOrderHeader(traversal, method, methodIdx++);

		int i = 0;
		EricBinaryTree.Node[] preOrderNextNums = new EricBinaryTree.Node[pg148.length];
		EricBinaryTree.Node orderNumberCall = b.r;

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
		EricBinaryTree.Node[] inOrderNextNums = new EricBinaryTree.Node[pg148.length];
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
		EricBinaryTree.Node[] postOrderNextNums = new EricBinaryTree.Node[pg148.length];
		orderNumberCall = preOrderNextNums[2]; // should be firstNode pg148

		while (orderNumberCall != b.nil) {
			postOrderNextNums[i++] = orderNumberCall;
			orderNumberCall = b.postorderNext(orderNumberCall);
		}
		printNumbers(postOrderNextNums, postOrderNextNums.length);
		System.out.println();
		return b;
	}

	private static EricBinaryTree<EricBinaryTree.Node> question5CreateOrderNumbersSimulations(
			int repeats, int factor) {
		String[] details = {
				"5. (20 marks) Exercise 6.7. Create a subclass of BinaryTree whose nodes",
				"have fields for storing preorder, post-order, and in-order numbers. Write",
				"methods preOrderNumber(), inOrderNumber(), and postOrderNumbers() that",
				"assign these numbers correctly. These methods should each run in O(n)", "time." };

		CommonSuite.printDescription("Question 5: Create order numbers for a BinaryTree traversal",
				details);
		System.out.println();

		if (factor < 1 || repeats < 1)
			factor = repeats = 1; // default

		// eliminate null pointer exceptions & ensure arrays align
		repeats = repeats / factor * factor;

		EricBinaryTree<EricBinaryTree.Node> b = null; // the binary tree
		EricBinaryTree.Node[] pg148 = null; // the nodes added to the binary
											// tree, in order

		// regression analysis variables
		double[] regrSizeInd = new double[factor];
		double[][] regrTimeDep = new double[3][factor];
		int regrIterationCounter = 0;

		// constants for array indices
		final int pre = 0;
		final int in = 1;
		final int post = 2;

		// build a BinaryTree with increasing numbers of nodes using pg. 148
		for (int idx = 0; idx < repeats; idx += repeats / factor) {
			b = new EricBinaryTree<EricBinaryTree.Node>(new EricBinaryTree.Node(),
					new EricBinaryTree.Node());

			pg148 = buildBTPg148(b, idx);

			regrSizeInd[regrIterationCounter] = b.size();

			System.out.println(
					".size() = " + regrSizeInd[regrIterationCounter] + ", iteration = " + idx);

			int timeRepeats = 1000;
			// determine how long it takes to (re)build the numbers
			CommonSuite.StopWatch.start();
			for (int j = 0; j < timeRepeats; j++) {
				// repeat 1000 times to make time measurable on a fast computer
				// stack over flow is a problem
				b.preOrderNumber();
			}
			regrTimeDep[pre][regrIterationCounter] = CommonSuite.StopWatch.stop();

			CommonSuite.StopWatch.start();
			for (int j = 0; j < timeRepeats; j++) {
				// repeat 1000 times to make time measurable on a fast computer
				// stack over flow is a problem
				b.postOrderNumbers();
			}
			regrTimeDep[post][regrIterationCounter] = CommonSuite.StopWatch.stop();

			CommonSuite.StopWatch.start();
			for (int j = 0; j < timeRepeats; j++) {
				// repeat 1000 times to make time measurable on a fast computer
				// stack over flow is a problem
				b.inOrderNumber();
			}
			regrTimeDep[in][regrIterationCounter++] = CommonSuite.StopWatch.stop();
		}

		System.out.println();

		printNumbers(pg148, Math.min(b.size(), 200));

		CommonSuite.printFancyHeader("Do ordering methods run in O(n) time?");

		CommonSuite.printFancyHeader("PRE-ORDER NUMBERS LINEAR REGRESSION");
		LinearRegression.doLinearRegression(Arrays.copyOfRange(regrSizeInd, 1, regrSizeInd.length),
				Arrays.copyOfRange(regrTimeDep[pre], 1, regrTimeDep[pre].length));

		CommonSuite.printFancyHeader("POST-ORDER NUMBERS LINEAR REGRESSION");
		LinearRegression.doLinearRegression(Arrays.copyOfRange(regrSizeInd, 1, regrSizeInd.length),
				Arrays.copyOfRange(regrTimeDep[post], 1, regrTimeDep[post].length));

		CommonSuite.printFancyHeader("IN-ORDER NUMBERS LINEAR REGRESSION");
		LinearRegression.doLinearRegression(Arrays.copyOfRange(regrSizeInd, 1, regrSizeInd.length),
				Arrays.copyOfRange(regrTimeDep[in], 1, regrTimeDep[in].length));

		return b;
	}

	private static boolean testing = false;
	static TestSuite theTester;

	public static void main(String[] args) {

		String[] details = {
				"A pre-order traversal of a binary tree is a traversal that visits each",
				"node, u, before any of its children. An in-order traversal visits u after",
				"visiting all the nodes in u�s left subtree but before visiting any of the",
				"nodes in u�s right subtree. A post-order traversal visits u only after",
				"visiting all other nodes in u�s subtree. The pre/in/post-order numbering",
				"of a tree labels the nodes of a tree with the integers 0, . . . ,n - 1 in",
				"the order that they are encountered by a pre/in/post-order traversal. See",
				"Figure 6.10 for an example. (pg 148)" };

		CommonSuite.printDescription("BinaryTree Traversal", details);
		System.out.println();

		EricBinaryTree.iterative = false;

		// Display programmer info and create testing object
		theTester = CommonSuite.commonProgramStart(2, 1,
				"Determine next node in a BinaryTree traversal", testing);

		question1NextOrderSimulation();

		CommonSuite.commonProgramEnd(theTester);

		// Display programmer info and create testing object
		theTester = CommonSuite.commonProgramStart(2, 5,
				"Create order numbers for a BinaryTree traversal", testing);

		question5CreateOrderNumbersSimulations(2000, 11);

		CommonSuite.commonProgramEnd(theTester);
	}

}
