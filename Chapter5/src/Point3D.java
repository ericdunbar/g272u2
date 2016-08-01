
/**
 * From ODS. From sample code.
 * 
 * @author Pat Morin
 *
 */
public class Point3D {
	protected Double x0, x1, x2;

	public boolean equals(Object o) {
		return (o instanceof Point3D) && ((Point3D) o).x0.equals(x0) && ((Point3D) o).x1.equals(x1)
				&& ((Point3D) o).x2.equals(x2);
	}

	public int hashCode() {
		// random numbers from rand.org
		long[] z = { 0x2058cc50L, 0xcb19137eL, 0x2cb6b6fdL };
		long zz = 0xbea0107e5067d19dL;

		// http://stackoverflow.com/questions/8691693/bitwise-operator-and-single-ampersand

		// convert (unsigned) hashcodes to long
		long h0 = x0.hashCode() & ((1L << 32) - 1);
		long h1 = x1.hashCode() & ((1L << 32) - 1);
		long h2 = x2.hashCode() & ((1L << 32) - 1);

		return (int) (((z[0] * h0 + z[1] * h1 + z[2] * h2) * zz) >>> 32);
	}

	public static void main(String[] args) {
		Integer two = -3;
		long ltwo = two & (1L << 32) - 1;

		// http://stackoverflow.com/questions/5263187/print-an-integer-in-binary-format-in-java
		System.out.println("                        two = 0b" + Integer.toBinaryString(two));
		System.out.println("ltwo = two & (1L << 32) - 1 = 0b" + Long.toBinaryString(ltwo));
		System.out.println("two.hashCode() = " + two.hashCode() + " ltwo = " + ltwo);
	}
}
