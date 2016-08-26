
import java.util.ArrayList;
import java.util.Scanner;

//@formatter:off
/**
 * Assignment 1 Common Tools (not assigned), COMP272 Class: TestSuite.java
 * Purpose: Provide a testing suite for use with COMP272 programs.
 *
 * @author: Eric Dunbar Student ID: 3243514 Date: October 3, 2015 May 30, 2016
 *          (revision) Version: 1.1
 *
 *          Based on: Eck, David J. (2015). Introduction to Programming Using
 *          Java, Seventh Edition. Web access http://math.hws.edu/javanotes/
 */
// @formatter:on
public class TestSuite {

	private ArrayList<TestTracker> recordsTestInfo = new ArrayList<TestTracker>();
	private Scanner myInput = new Scanner(System.in);
	private boolean testing = true; // this is for the instance to let the
	// program know whether it should be showing
	// testing code, if desired
	private boolean silentRecording = true; // display info immediately?

	// @formatter:off
	/**
	 * Assignment 1 Common Tools, COMP268 Class: TestSuite.java Purpose:
	 * Provides a private object Type that allows instances to track testing
	 * parameters.
	 *
	 * @author: Eric Dunbar Student ID: 3243514 Date: October 3, 2015 Version:
	 *          1.0
	 *
	 *          Based on: Eck, David J. (2015). Introduction to Programming
	 *          Using Java, Seventh Edition. Web access
	 *          http://math.hws.edu/javanotes/
	 */
	// @formatter:on
	private class TestTracker {
		String comment;
		String programInput;
		String expectedOutput;
		String observedOutput;
		TestStage testStage;
		boolean isValid;
		String toStringS;

		/**
		 * Creates an instance of TestTracker, a type that tracks the parameters
		 * passed to the super-instance for testing purposes.
		 * 
		 * @param comment the comment, to be printed, as String
		 * @param programInput the code fragment, as String
		 * @param expectedOutput the expected output, as String
		 * @param observedOutput the observed output, as String
		 * @param testStage the test stage this test is part of (1, 2, 3, 4), as
		 *            TestStage
		 * @param isValid is expected == observed?
		 */
		public TestTracker(String comment, String programInput, String expectedOutput,
				String observedOutput, TestStage testStage, boolean isValid) {
			this.comment = comment;
			this.programInput = programInput;
			this.expectedOutput = expectedOutput;
			this.observedOutput = observedOutput;
			this.testStage = testStage;
			this.isValid = isValid;
		}

		private void addToString(String addS) {
			toStringS += addS;
		}

		private void addToStringln(String addS) {
			toStringS += "\n" + addS;
		}

		private String formatTitle(String header) {
			return String.format("| %13s: ", header);
		}

		/**
		 * Generates a multi-line String representation of the test results,
		 * with indent at the beginning, if desired.
		 * 
		 * @param string text to prepend to each line
		 * @return toString with prepended text
		 */
		private String createToString(String string) {
			toStringS = "";
			String[] titlesS = { "DESCRIPTION", "INPUT", "EXPECTED", "OBSERVED", "RESULT" };
			String[] detailsS = { comment, programInput, expectedOutput, observedOutput,
					isValid ? "Pass" : "Fail" };

			addToString(string + CommonSuite.stringRepeat("__", 30));

			for (int i = 0; i < 5; i++) {
				addToStringln(string + (formatTitle(titlesS[i])) + detailsS[i]);
			}
			addToStringln(string + (CommonSuite.stringRepeat("--", 30)) + "\n");

			return toStringS;
		}

		/**
		 * Generates a multi-line String representation of the test results
		 * which is indented.
		 * 
		 * @return multi-line representation of the test results
		 */
		public String toIndentedString() {
			return createToString(CommonSuite.indentString(""));
		}

		/**
		 * Generates a multi-line String representation of the test results.
		 * 
		 * @return multi-line representation of the test results
		 */
		public String toString() {
			return createToString("");
		}
	} // end of TestTracker class

	/**
	 * Ask user whether to turn testing on or off.
	 */
	public void setTesting() {
		System.out.println();
		System.out.println("Enable test recording? y or n?");
		System.out.print("> ");
		String enabled = "";

		while (!enabled.equalsIgnoreCase("y") && !enabled.equalsIgnoreCase("n"))
			enabled = myInput.nextLine();

		setTesting(enabled.equalsIgnoreCase("y"));
		// TODO cleanup if this code works
		// ) {
		// setTesting(true);
		// } else if (enabled.equalsIgnoreCase("n")) {
		// setTesting(false);
		// }
	}

	/**
	 * Should the calling program be displaying test code?
	 * 
	 * @return whether program should be testing
	 */
	public boolean isTesting() {
		return testing;
	}

	/**
	 * Sets whether program should display test code
	 * 
	 * @param testing true if testing, as boolean
	 */
	public void setTesting(boolean testing) {
		this.testing = testing;
	}

	/**
	 * @return the silentRecording
	 */
	public boolean isSilentRecording() {
		return silentRecording;
	}

	/**
	 * @param silentRecording the silentRecording to set
	 */
	public void setSilentRecording(boolean silentRecording) {
		this.silentRecording = silentRecording;
	}

	// To what testing stage does the test belong?
	public enum TestStage {
		Stage1, Stage2, Stage3, Stage4
	};

	// Self-explanatory tracking variables for this instance
	private int numberOfTestsRun = 0;
	private int testsPassed = 0;
	private int testsFailed = 0;

	/**
	 * Sub-class to obtain input for TestSuite.java
	 * 
	 * @author Eric Dunbar
	 *
	 */
	public static class ObtainTestInput {
		static Scanner theInput = new Scanner(System.in); // Used to get input

		/**
		 * Display a message and obtain a single line worth of String input
		 * 
		 * @param message message to be displayed
		 * @return String containing user response
		 */
		private static String obtainString(String message) {
			// Let user know what input is required
			System.out.println(message);

			// Obtain input and display responses
			System.out.print("> "); // Show > to indicate input required
			return theInput.nextLine(); // Obtain menu choice
		}

		/**
		 * Complete a fully user-controlled isValidTest. The user provides all
		 * input and decides whether the test results are the same.
		 * 
		 * @param myTestSuite an instance of a test tracking object of type
		 *            TestSuite
		 */
		public static void doFullManualValidTest(TestSuite myTestSuite) {
			System.out.println();
			System.out.println("Please provide all the test data for a single test:");
			System.out.println();
			String comment = TestSuite.ObtainTestInput.obtainComment();
			System.out.println();
			String programInput = TestSuite.ObtainTestInput.obtainCode();
			System.out.println();
			String expectedOutput = TestSuite.ObtainTestInput.obtainExpectedOutput();
			System.out.println();
			String observedOutput = TestSuite.ObtainTestInput.obtainObservedOutput();
			System.out.println();
			TestSuite.TestStage whichStage = TestSuite.ObtainTestInput.obtainStage();

			boolean automatedComparison = false; // Force a manual comparison
			myTestSuite.recordTest(comment, programInput, expectedOutput, observedOutput,
					automatedComparison, whichStage);

			System.out.println();
			System.out.println("This particular test run has been recorded by the TestSuite.");
			System.out.println("Execute .printTestsPerformed() to see a summary of all test runs.");
			System.out.println();
		}

		/**
		 * Obtain the expected output of a test routine
		 * 
		 * @return String containing expected output
		 */
		public static String obtainExpectedOutput() {
			return obtainString("Please enter the EXPECTED OUTPUT on a single line");
		}

		/**
		 * Obtain the observed output of a test routine
		 * 
		 * @return String containing observed output
		 */
		public static String obtainObservedOutput() {
			return obtainString("Please enter the OBSERVED OUTPUT on a single line");
		}

		/**
		 * Obtain the comment describing a test routine
		 * 
		 * @return String containing comment
		 */
		public static String obtainComment() {
			return obtainString("Please enter a COMMENT describing the test on a single line");
		}

		/**
		 * Obtain the comment describing a test routine
		 * 
		 * @return String containing comment
		 */
		public static String obtainCode() {
			return obtainString(
					"Please enter the CODE that was run or the INPUT provided on a single line");
		}

		/**
		 * Obtains the current TestStage (Stage1, Stage2, Stage3, Stage4) from
		 * the user
		 * 
		 * @return the current test stage, as type TestStage
		 */
		public static TestStage obtainStage() {
			while (true) {
				String sMenuChoice; // Numeric variable used to provide choice
									// feedback

				System.out.println("Please choose your test stage.");
				printGlossaryTestStages();
				// Control structure to get input and display responses
				System.out.print("> "); // Show > to indicate input required
				sMenuChoice = theInput.nextLine(); // Obtain menu choice

				// Determine what happens.
				switch (sMenuChoice) {

				case "1": {
					return TestStage.Stage1;
				}
				case "2": {
					return TestStage.Stage2;
				}
				case "3": {
					return TestStage.Stage3;
				}
				case "4": {
					return TestStage.Stage4;
				}
				default:
					System.out.println("Try again. Enter 1, 2, 3 or 4");
					break;
				}
			}
		} // end obtainStage()
	} // end class ObtainTestInput

	/**
	 * Create a TestSuite instance to conduct observed output vs. expected
	 * output tests and track number of passes, number of failures.
	 */
	public TestSuite() {
	}

	/**
	 * How many comparison test have been conducted?
	 * 
	 * @return Number of tests conducted
	 */
	public int getNumberOfTestsRun() {
		return numberOfTestsRun;
	}

	/**
	 * How many comparison tests passed?
	 * 
	 * @return Number of tests that passed
	 */
	public int getTestsPassed() {
		return testsPassed;
	}

	/**
	 * How many comparison tests have failed?
	 * 
	 * @return Number of tests that failed
	 */
	public int getTestsFailed() {
		return testsFailed;
	}

	/**
	 * Print a glossary of what each of the four TestStages means.
	 */
	public static void printGlossaryTestStages() {
		String indentS = CommonSuite.stringRepeat(" ", CommonSuite.indentAmount);

		System.out.println("GLOSSARY OF STAGES:");
		System.out.println(TestStage.Stage1);
		System.out.println(indentS + "Program compiles successfully?");
		System.out.println(TestStage.Stage2);
		System.out.println(indentS + "Normal data entered for test runs.");
		System.out.println(TestStage.Stage3);
		System.out.println(indentS + "Abnormal data entered for test runs.");
		System.out.println(TestStage.Stage4);
		System.out.println(indentS + "Limiting conditions violated for test runs.");
	}

	/**
	 * Prints a concise summary of the tests performed.
	 */
	public void printShortTestSummary() {
		String indentS = CommonSuite.stringRepeat(" ", CommonSuite.indentAmount);

		System.out.println("TEST SUMMARY:");
		System.out.println(indentS + "Number of tests performed: " + getNumberOfTestsRun());
		System.out.println(indentS + "Number of tests passed:    " + getTestsPassed());
		System.out.println(indentS + "Number of tests failed:    " + getTestsFailed());
	}

	/**
	 * Prints test results for one of the TestStage stages.
	 * 
	 * @param currentStage the stage for which results must be printed
	 */
	private void printTestStageResults(TestStage currentStage) {
		// Print the test information for this particular stage of testing
		String indentS = CommonSuite.stringRepeat(" ", CommonSuite.indentAmount);
		int numberOfFailures = 0; // how many failures occurred in this stage
		int numberOfTests = 0; // track how many tests were performed in this
								// stage
		for (TestTracker testTracker : recordsTestInfo) {
			if (testTracker.testStage == currentStage) {
				numberOfTests++; // we've got a live one. Increment the number
									// of tests
				System.out.println("Test Run " + numberOfTests + ": " + testTracker.comment);
				System.out.println("Input:");
				System.out.println(indentS + testTracker.programInput);
				System.out.println();
				System.out.println("Expected Output:");
				System.out.println(indentS + testTracker.expectedOutput);
				System.out.println();
				System.out.println("Observed Output:");
				System.out.print(indentS);
				if (testTracker.isValid) {
					System.out.print("Pass. ");
				} else {
					System.out.print("Fail. ");
					numberOfFailures++;
				}
				System.out.println(testTracker.observedOutput);
				System.out.println();
				System.out.println();

			} // end if
		} // end for

		String theStage;

		switch (currentStage) {
		case Stage1:
			theStage = "Stage 1";
			break;
		case Stage2:
			theStage = "Stage 2";
			break;
		case Stage3:
			theStage = "Stage 3";
			break;
		case Stage4:
			theStage = "Stage 4";
			break;
		default:
			theStage = "";
			break;
		}

		if (numberOfTests == 0) {
			System.out.println("Not applicable. No tests were performed for " + theStage);
		} else if (numberOfFailures > 0) {
			System.out.println(theStage + " - end of test -- stage fails -- " + numberOfFailures
					+ " out of " + numberOfTests + " failed");
		} else {
			System.out.println(
					theStage + " - end of test -- stage passes -- test performed as expected.");
		}
		System.out.println();

	}

	/**
	 * Prints test results for one of the TestStage stages.
	 * 
	 * @param currentStage the stage for which results must be printed
	 */
	private void printCompactTestStageResults(TestStage currentStage) {
		int numberOfFailures = 0; // how many failures occurred in this stage
		int numberOfTests = 0; // how many tests performed in this stage
		for (TestTracker testTracker : recordsTestInfo) {
			if (testTracker.testStage == currentStage) {
				numberOfTests++; // we've got a live one. Increment the number
									// of tests
				CommonSuite.printlnIndent("Test Run " + numberOfTests);
				CommonSuite.printlnIndent("Description: " + testTracker.comment);
				CommonSuite.printlnIndent("Program Input: " + testTracker.programInput);
				CommonSuite.printlnIndent("Expected Output: " + testTracker.expectedOutput);
				CommonSuite.printlnIndent("Observed Output: " + testTracker.observedOutput);
				CommonSuite.printIndent(("Result: "));
				if (testTracker.isValid) {
					System.out.print("Pass. ");
				} else {
					System.out.print("Fail. ");
					numberOfFailures++;
				}
				System.out.println();
				System.out.println();

			} // end if
		} // end for

		String theStage;

		switch (currentStage) {
		case Stage1:
			theStage = "Stage 1";
			break;
		case Stage2:
			theStage = "Stage 2";
			break;
		case Stage3:
			theStage = "Stage 3";
			break;
		case Stage4:
			theStage = "Stage 4";
			break;
		default:
			theStage = "";
			break;
		}

		if (numberOfTests == 0) {
			System.out.println("Not applicable. No tests were performed for " + theStage);
		} else if (numberOfFailures > 0) {
			System.out.println(theStage + " - end of test -- stage fails -- " + numberOfFailures
					+ " out of " + numberOfTests + " failed");
		} else {
			System.out.println(
					theStage + " - end of test -- stage passes -- test performed as expected.");
		}
		System.out.println();

	}

	/**
	 * Prints a wide variety of testing information.
	 * 
	 * @param theTester an instance of type TestSuite containing test data to be
	 *            printed
	 */
	public void printFullTestData() {
		// Note: since this code is reused so much it was thought best to spin
		// it off into its own method

		// Let's recap...

		System.out.println();
		System.out.println("Let's summarize the results...");
		System.out.println();

		// Display programmer info
		CommonSuite.printProgrammerInfo();

		// Display test results
		System.out.println();
		printShortTestSummary();
		printTestsPerformed();
	}

	public void printTestsPerformed() {
		System.out.println(CommonSuite.stringRepeat("*", 80));
		System.out.println("Tests Performed:");
		System.out.println();
		System.out.println("Stage 1 - Program compiles and runs correctly using Eclipse");
		System.out.println();
		System.out.println("Stage 2 - Normal Data:");
		System.out.println();

		TestStage currentStage = TestStage.Stage2;
		printCompactTestStageResults(currentStage);

		System.out.println("Stage 3 - Abnormal Data:");
		System.out.println();

		currentStage = TestStage.Stage3;
		printCompactTestStageResults(currentStage);

		System.out.println("Stage 4 - Limiting Conditions:");
		System.out.println();

		currentStage = TestStage.Stage4;
		printCompactTestStageResults(currentStage);
		System.out.println(CommonSuite.stringRepeat("*", 80));
	}

	/**
	 * Automatically compares two String fragments for equality and records
	 * results of the comparison. Defaults to stage 2, normal input.
	 * 
	 * @param comment comment describing the test
	 * @param programInput code executed/input
	 * @param expectedOutput output that is expected
	 * @param observedOutput output that is observed
	 * @return true if expected and observed output equivalent
	 */
	public boolean recordTest(String comment, String programInput, String expectedOutput,
			String observedOutput) {
		boolean automatedComparison = true; // default to automated comparison
		TestStage whichStage = TestStage.Stage2; // default to stage 2, normal
		return recordTest(comment, programInput, expectedOutput, observedOutput,
				automatedComparison, whichStage);
	}

	/**
	 * Compare two String fragments for equality and records results of the
	 * comparison. Optionally do a user-mediated comparison.
	 * 
	 * @param comment comment describing the test
	 * @param programInput code executed/input
	 * @param expectedOutput output that is expected
	 * @param observedOutput output that is observed
	 * @param automatedComparison true if comparison done automatically,
	 *            otherwise comparison is done manually through visual
	 *            inspection
	 * @param whichStage which of the four stages is this
	 * @return true if expected and observed output equivalent
	 */
	public boolean recordTest(String comment, String programInput, String expectedOutput,
			String observedOutput, boolean automatedComparison, TestStage whichStage) {
		return backendRecordTest(comment, programInput, expectedOutput, observedOutput,
				automatedComparison, whichStage, false, false);
	}

	/**
	 * Records results of a test when the result is already known.
	 * 
	 * @param comment
	 * @param programInput
	 * @param expectedOutput
	 * @param observedOutput
	 * @param result true if O==E
	 * @param whichStage
	 * @return
	 */
	public boolean recordTestKnownResult(String comment, String programInput, String expectedOutput,
			String observedOutput, boolean result) {
		boolean automatedComparison = true;
		return backendRecordTest(comment, programInput, expectedOutput, observedOutput,
				automatedComparison, TestStage.Stage2, true, result);
	}
	/**
	 * Records results of a test when the result is already known.
	 * 
	 * @param comment
	 * @param programInput
	 * @param expectedOutput
	 * @param observedOutput
	 * @param result true if O==E
	 * @param whichStage
	 * @return
	 */
	public boolean recordTestKnownResult(String comment, String programInput, String expectedOutput,
			String observedOutput, boolean result, TestStage whichStage) {
		boolean automatedComparison = true;
		return backendRecordTest(comment, programInput, expectedOutput, observedOutput,
				automatedComparison, whichStage, false, false);
	}

	/**
	 * Compare two String fragments for similarities. Use boolean
	 * automatedComparison to determine whether the routine should do the
	 * comparison or whether the end user should have to manually compare the
	 * results.
	 * 
	 * @param comment comment describing the test
	 * @param programInput code executed
	 * @param expectedOutput output that is expected
	 * @param observedOutput output that is observed
	 * @param automatedComparison true if method conducts comparison
	 *            automatically, otherwise comparison is done manually through
	 *            visual inspection
	 * @param whichStage which of the four stages is this, type TestStage
	 * @param resultKnown true if result is known
	 * @param result of whether O~E, ignored if !resultKnown
	 * @return whether the expected and observed output were the same
	 */
	private boolean backendRecordTest(String comment, String programInput, String expectedOutput,
			String observedOutput, boolean automatedComparison, TestStage whichStage,
			boolean resultKnown, boolean result) {

		boolean isValid = false; // initialize the test variable as false

		if (!automatedComparison) {
			// user-mediated comparison
			System.out.println(CommonSuite.stringRepeat("-", 40));
			System.out.println("Please review the recorded information.");
			System.out.println(CommonSuite.stringRepeat("-", 40));
			System.out.println();
			System.out.println("The comment:");
			System.out.println(comment);
			System.out.println();
			System.out.println("The code:");
			System.out.println(programInput);
			System.out.println();
			System.out.println("Expected output:");
			System.out.println(expectedOutput);
			System.out.println();
			System.out.println("Observed output:");
			System.out.println(observedOutput);
			System.out.println(CommonSuite.stringRepeat("-", 40));
			System.out.println();

			String userResponse;
			while (true) {
				System.out.print("Is observed output equal to expected output? y or n? ");
				userResponse = myInput.nextLine();
				if (userResponse.equalsIgnoreCase("y")) {
					isValid = true;
					break;
				} else if (userResponse.equalsIgnoreCase("n")) {
					isValid = false;
					break;
				}
			}
		} else if (observedOutput == null || expectedOutput == null) {
			isValid = (observedOutput == expectedOutput);
		} else {
			// automated comparison
			isValid = resultKnown ? result : observedOutput.equals(expectedOutput);
		}

		// Housekeeping. Update instance pass/fail variables
		numberOfTestsRun++;
		if (isValid) {
			testsPassed++;
		} else {
			testsFailed++;
		}

		recordsTestInfo.add(new TestTracker(comment, programInput, expectedOutput, observedOutput,
				whichStage, isValid));
		if (!isSilentRecording()) {
			System.out.print(recordsTestInfo.get(recordsTestInfo.size() - 1).toIndentedString());
		}

		return isValid;
	}
}
