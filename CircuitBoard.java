import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 * 
 * @author mvail & Devan Craig
 * 
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	// constants you may find useful
	private final int ROWS; // initialized in constructor
	private final int COLS; // initialized in constructor
	private final char OPEN = 'O'; // capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/**
	 * Construct a CircuitBoard from a given board input file, where the first line
	 * contains the number of rows and columns as ints and each subsequent line is
	 * one row of characters representing the contents of that position. Valid
	 * characters are as follows: 'O' an open position 'X' an occupied, unavailable
	 * position '1' first of two components needing to be connected '2' second of
	 * two components needing to be connected 'T' is not expected in input files -
	 * represents part of the trace connecting components 1 and 2 in the solution
	 * 
	 * @param filename file containing a grid of characters
	 * @throws FileNotFoundException      if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that
	 *                                    prevents reading a valid input file
	 */

	@SuppressWarnings("resource")
	public CircuitBoard(String filename) throws FileNotFoundException {

		Scanner fileScan = new Scanner(new File(filename));
		// Checking to see if there is a next int for rows
		if (!fileScan.hasNextInt()) {
			throw new InvalidFileFormatException("Row Grid Deminsion Invaild " + filename);
		}
		ROWS = fileScan.nextInt();
		// Checking to see if there is a next int for columns
		if (!fileScan.hasNextInt()) {
			throw new InvalidFileFormatException("Coloumn Grid Deminsion Invaild " + filename);
		}
		COLS = fileScan.nextInt();

		fileScan.nextLine();
		boolean startError = false;
		boolean endError = false;
		int startErr = 0;
		int endErr = 0;
		// Creating Board
		board = new char[ROWS][COLS];

		int i = 0;
		int j;
		char character;
		// Both while loops work together to create the grid
		while (fileScan.hasNextLine()) {
			String newLine = fileScan.nextLine();
			Scanner numbers = new Scanner(newLine);
			j = 0;
			// Runs the grid as long as it has a next value
			while (numbers.hasNext()) {
				if (i + 1 > ROWS) {
					throw new InvalidFileFormatException("Invalid: Grid Deminsions " + filename);
				}
				character = numbers.next().charAt(0);
				if (j >= COLS) {
					throw new InvalidFileFormatException("Invalid: Index out of Bounds " + filename);
				}
				board[i][j] = character;

				if (ALLOWED_CHARS.indexOf(character) == -1) {
					throw new InvalidFileFormatException("Invalid Characters " + filename);
				}
				if (board[i][j] == START) {
					startingPoint = new Point(i, j);
					startErr++;
					startError = true;
					if (startErr > 1) {
						startError = false;
					}

				}
				if (board[i][j] == END) {
					endingPoint = new Point(i, j);
					endErr++;
					endError = true;
					if (endErr > 1) {
						endError = false;
					}
				}

				j++;

			}
			if (j != COLS) {
				throw new InvalidFileFormatException("Invalid: Grid Deminsions " + filename);
			}

			i++;
			numbers.close();

		}
		if (i != ROWS) {
			throw new InvalidFileFormatException("Invalid: Grid Deminsions " + filename);
		}

		if (startError == false || endError == false) {
			throw new InvalidFileFormatException("Invalid: Start or End Points " + filename);
		}

	}

	/**
	 * Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/**
	 * utility method for copy constructor
	 * 
	 * @return copy of board array
	 */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}

	/**
	 * Return the char at board position x,y
	 * 
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Return whether given board position is open
	 * 
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}

	/**
	 * Set given position to be a 'T'
	 * 
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}

	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}

	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}

	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}

	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}
	/**
	 * 
	 * @param x represents the rows on the grid
	 * @param y represents the columns on the grid
	 * Creates a board that looks at all the adjacent points
	 * where you can make your next move and return a paths to
	 * the end.
	 * @return
	 */
	public ArrayList<Point> adjacentPoints(int x, int y) {
		ArrayList<Point> retVal = new ArrayList<>();
		Point point1 = new Point(x, y);
		int i = 0;
		int j;
		while (i < board.length) {
			j = 0;
			while (j < board[i].length) {
				Point point2 = new Point(i, j);
				if (TraceState.adjacent(point1, point2) && isOpen(i, j)) {
					retVal.add(point2);
				}
				j++;
			}
			i++;
		}
		return retVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}

}// class CircuitBoard
