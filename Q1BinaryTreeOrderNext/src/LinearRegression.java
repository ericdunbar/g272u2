
/******************************************************************************
 * Compilation: javac LinearRegression.java StdIn.java Execution: java
 * LinearRegression < data.txt
 * 
 * Reads in a sequence of pairs of real numbers and computes the best fit (least
 * squares) line y = ax + b through the set of points. Also computes the
 * correlation coefficient and the standard error of the regression
 * coefficients.
 *
 * Note: the two-pass formula is preferred for stability.
 *
 * Source:
 * http://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html
 * 
 * http://stackoverflow.com/questions/5684282/weighted-linear-regression-in-java
 *
 ******************************************************************************/

/**
 * Modified by Eric Dunbar. Original found at
 * http://introcs.cs.princeton.edu/java/97data/LinearRegression.java.html.
 * 
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Eric Dunbar
 * @date Aug 11, 2016
 * @title Linear Regression
 * @assignment 2
 *
 */
public class LinearRegression {

	public static void doLinearRegression(double[] ind, double[] dep) {
		int MAXN = ind.length;
		int n = 0;
		double[] x = new double[MAXN];
		double[] y = new double[MAXN];

		// first pass: read in data, compute xbar and ybar
		double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
		for (n = 0; n < ind.length; n++) {
			x[n] = ind[n];
			y[n] = dep[n];
			sumx += x[n];
			sumx2 += x[n] * x[n];
			sumy += y[n];
		}
		double xbar = sumx / n;
		double ybar = sumy / n;

		// second pass: compute summary statistics
		double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
		for (int i = 0; i < n; i++) {
			xxbar += (x[i] - xbar) * (x[i] - xbar);
			yybar += (y[i] - ybar) * (y[i] - ybar);
			xybar += (x[i] - xbar) * (y[i] - ybar);
		}
		double beta1 = xybar / xxbar;
		double beta0 = ybar - beta1 * xbar;

		// print results
		System.out.printf("    y = %6.4f * x + %6.4f%n", beta1, beta0);

		// analyze results
		int df = n - 2;
		double rss = 0.0; // residual sum of squares
		double ssr = 0.0; // regression sum of squares
		for (int i = 0; i < n; i++) {
			double fit = beta1 * x[i] + beta0;
			rss += (fit - y[i]) * (fit - y[i]);
			ssr += (fit - ybar) * (fit - ybar);
		}
		double R2 = ssr / yybar;
		double svar = rss / df;
		double svar1 = svar / xxbar;
		double svar0 = svar / n + xbar * xbar * svar1;
		System.out.println();
		System.out.printf("R^2 = %6.4f%n", R2);
		System.out.printf("  std error of m = %6.4f%n", Math.sqrt(svar1));
		System.out.printf("  std error of b = %6.4f%n", Math.sqrt(svar0));
		svar0 = svar * sumx2 / (n * xxbar);

		System.out.println("  SSTO = " + yybar);
		System.out.println("  SSE  = " + rss);
		System.out.println("  SSR  = " + ssr);

		System.out.println();
		System.out.println("Data");
		for (int i = 0; i < ind.length; i++) {
			System.out.println("    " + i + " (" + x[i] + ", " + y[i] + ") ");

		}
		System.out.println();
	}
}

// Copyright © 2000–2011, Robert Sedgewick and Kevin Wayne.
// Last updated: Tue Jun 28 21:27:28 EDT 2016.