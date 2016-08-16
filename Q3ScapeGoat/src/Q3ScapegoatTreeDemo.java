/*
Exercise 8.2. Illustrate what happens when the sequence 1,5,2,4,3 is added to an empty ScapegoatTree, and show where the credits described in the proof of Lemma 8.3 go, and how they are used during this sequence of additions.

A. A rebuild is not required because of the condition that q/2 <= n <= q. q = n. After each addition q = n because no remove operations have been performed.

B. Condition: log3/2 q <= log3/2 2n < log3/2 n+2. If depth exceeds log 3/2 q a rebuild is required.

Since for q =5, (log 3/2 q = 3.97) < (d = 4) a rebuild is required when element 5 is added. 9 credits are paid to reorganize, starting with u.x = 5 since that's where [size(u.x=5)/size(u.x=5.left) > 2/3]

C. Nine of the ten credits are used in the rebuild.

Step 1:
Set 1 as root
Credits earned:
None
n = 1
d = 0
log 3/2 (q) = 0.0

Step 2:
Add 5 as right-child.
Credits earned:
Root 0->1
n = 2
d = 1
log 3/2 (q) = 2; 1.7

Step 3:
Add 2 as left-child to 5
Credits earned:
Root 1->2
5 0->1
n = 3
d = 2
log 3/2 (q) = 3; 2.7

Step 4:
Add 4 as right-child to 2
Credits earned:
Root 2->3
5 1->2
2 0->1
n = 4
d = 3
log 3/2 (q) = 4; 3.4

Step 5:
Add 3 as left-child to 4
Credits earned:
Root 3-> 4
5 2->3
2 1->2
4 0->1
n = 5
d = 4
log 3/2 (q) = 4; 3.97

Step 6:
Rebuild sub-tree starting with u.x=5 as the root node of the sub-tree. 9 of the 10 credits are used since the tree's root, r does not have to be moved to bring the whole tree into compliance with the limiting conditions.
 */

/**
 * Program used to confirm what happens when a sequence of numbers is added to
 * an empty ScapegoatTree. Added for completeness. The actual answer did not
 * specify that code was required so little effort has been made to make this
 * meaningful for anyone else.
 * 
 * @author Eric Dunbar
 * @date Aug 14, 2016
 * @title ScapegoatTree
 * @assignment 2
 *
 */
public class Q3ScapegoatTreeDemo {

	public static void printQuestion() {
		String title = "Scapegoat Tree (Q3)";
		String[] details = {
				"Illustrate what happens when the sequence 1, 5, 2, 4, 3 is added to an",
				"empty ScapegoatTree, and show where the credits described in the proof of",
				"Lemma 8.3 go, and how they are used during this sequence of additions", "",
				"Program used to confirm what happens when a sequence of numbers is added to",
				"an empty ScapegoatTree. Added for completeness. The actual answer did not",
				"specify that code was required so little effort has been made to make this",
				"meaningful for anyone else." };
		CommonSuite.printDescription(title, details);

		System.out.println();
	}

	public static void main(String[] args) {
		// no testing here folks
		CommonSuite.commonProgramStart(2, 3, "ScapegoatTree", false);

		printQuestion();

		ScapegoatTree<Integer> sg;

		sg = new ScapegoatTree<>();

		Integer a[] = { 1, 5, 2, 4, 3 };

		for (int i = 0; i < a.length; i++) {
			sg.add(a[i]);
		}

		sg.printBSTPTree();
	}

}
