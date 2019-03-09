import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Devan Craig 
 *         This program is design to parse files in that have
 *         integer values in order to create a grid of arrays. The program will
 *         also tell you whether the file you are parsing in is valid or
 *         invalid, along with given you information for the invalid file.
 */
public class FormatChecker {

	public static double[][] grid;

	private static final String DELIMITERS = "\\s+";

	public static void main(String args[]) {

		if (args.length < 1) {
			System.err.println("Usage:$java FormatChecker file1 [file2 ..fileN]");
			System.exit(1);
		}

		for (int i = 0; i < args.length; i++) {

			String file = args[i];

			FormatChecker fCheck = new FormatChecker(file);

		}

	}

	/**
	 * @param fileName This method is designed to parse in a file, run through it
	 *                 and figure out whether or not the file is valid or invalid.
	 *                 After the method tries for a valid file it will then catch
	 *                 anything that is wrong and print the problem to the console.
	 */
	public FormatChecker(String fileName) {

		File file = new File(fileName);
		int row = 0;
		int column = 0;
		String current = "";
		boolean firstRow = true;

		try {

			Scanner fileScan = new Scanner(file);

			if (fileScan.hasNextLine()) {
				String newLine = fileScan.nextLine();
				Scanner numbers = new Scanner(newLine);

				numbers.useDelimiter(DELIMITERS);

				if (firstRow == true) {
					firstRow = false;

					int tokenCounter = 0;

					while (numbers.hasNext()) {

						if (tokenCounter == 0) {
							row = Integer.parseInt(numbers.next());

						}

						if (tokenCounter == 1) {
							column = Integer.parseInt(numbers.next());
						}

						if (tokenCounter == 2) {
							throw new MaxException();
						}

						tokenCounter++;
					}
				}
			}
			grid = new double[row][column];
			int i = 0;
			while (fileScan.hasNextLine()) {
				String newLine = fileScan.nextLine();
				Scanner numbers = new Scanner(newLine);
				numbers.useDelimiter(DELIMITERS);
				int j = 0;

				while (numbers.hasNext()) {
					current = numbers.next();
					grid[i][j] = Double.parseDouble(current);
//						System.out.println(grid[i][j]);
					j++;

				}
				i++;

			}
			System.out.println(fileName + "\n" + "VALID");

		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.out.println("Invalid");
		} catch (InputMismatchException d) {
			System.out.println(fileName + "");
			System.err.println(d + " For input string: \"" + current + "\"");
			System.out.println("Invalid");
		} catch (NumberFormatException df) {
			System.out.println(fileName + "");
			System.err.println(df);
			System.out.println("Invalid");
		} catch (ArrayIndexOutOfBoundsException z) {
			System.out.println(fileName + "");
			System.err.println(z);
			System.out.println("Invalid");
		} catch (MaxException e) {
			System.out.println(fileName + "");
			System.err.println("java.lang.MaxException:");
			System.out.println("Invalid");
		}

	}

}
