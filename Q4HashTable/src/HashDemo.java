import java.util.HashSet;
import java.util.Random;
import java.util.Set;

//public class LinearHashTable<T> implements USet<T> {

public class HashDemo<T> extends LinearHashTable<T> implements USet<T>  {

	public HashDemo(T nil) {
		super(nil);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Random rand = new Random(1);
		USet<Integer> lht = new HashDemo<Integer>(-1);
		Set<Integer> s = new HashSet<Integer>();
		int n = 1000000;
		System.out.println("Adding");
		for (int i = 0; i < n; i++) {
			Integer x = rand.nextInt(n);
			boolean rs = s.add(x);
			boolean rlht = lht.add(x);
			if (rs != rlht) 
				throw new RuntimeException("Aaaaaaaaaaaaaaaah!");
			if (s.size() != lht.size()) {
				System.out.println(s.size());
				System.out.println(lht.size());
				throw new RuntimeException("Bwaaaaaaaaaaaaaaah!");
			}
		}
		System.out.println("Searching");
		for (int i = 0; i < n; i++) {
			Integer x = rand.nextInt(n);
			boolean rs = s.contains(x);
			boolean rlht = lht.find(x) != null;
			if (rs != rlht) 
				throw new RuntimeException("Aaaaaaaaaaaaaaaah!");
			if (s.size() != lht.size()) 
				throw new RuntimeException("Bwaaaaaaaaaaaaaaah!");
		}

		System.out.println("Removing");
		for (int i = 0; i < n; i++) {
			Integer x = rand.nextInt(n);
			boolean rs = s.remove(x);
			boolean rlht = lht.remove(x) != null;
			if (rs != rlht) 
				throw new RuntimeException("Aaaaaaaaaaaaaaaah!");
			if (s.size() != lht.size()) 
				throw new RuntimeException("Bwaaaaaaaaaaaaaaah!");
		}
		s.clear();
		lht.clear();	}
}
