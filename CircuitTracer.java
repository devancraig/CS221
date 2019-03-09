import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Search for shortest paths between start and end points on a circuit board as
 * read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to a
 * GUI according to options specified via command-line arguments.
 * 
 * @author mvail & Devan Craig
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;

	/**
	 * launch the program
	 * 
	 * @param args three required arguments: first arg: -s for stack or -q for queue
	 *             second arg: -c for console output or -g for GUI output third arg:
	 *             input file name
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); // create this with args
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		// TODO: print out clear usage instructions when there are problems with
		// any command line args
		System.out.println("Options Allowed:");
		System.err.println("-s for stack");
		System.err.println("-q for queue");
		System.err.println("-c to run program in console");
		System.err.println("-g for GUI");
	}

	/**
	 * Set up the CircuitBoard and all other components based on command line
	 * arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws FileNotFoundException
	 */
	private CircuitTracer(String[] args) throws FileNotFoundException {
		// TODO: parse command line args
		String sOrQ = args[0];
		String cOrG = args[1];
		String fileName = args[2];
		try {

			if (sOrQ.equals("-q")) {
				stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
			} else if (sOrQ.equals("-s")) {
				stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
			} else {
				System.out.println("Program must have a storage type of -q or -s");
				printUsage();
				System.exit(1);
			}

			if (cOrG.equals("-c")) {

			} else if (cOrG.equals("-g")) {

			} else {
				System.out.println("Program must have a display type of -c or -g");
				printUsage();
				System.exit(1);

			}

			board = new CircuitBoard(fileName);

			bestPaths = new ArrayList<>();

		} catch (FileNotFoundException e) {
			System.out.print(e.toString());
		}

		ArrayList<Point> sPoint = board.adjacentPoints(board.getStartingPoint().x, board.getStartingPoint().y);

		// finding a starting point based off of open positions
		for (Point p : sPoint) {
			stateStore.store(new TraceState(board, p.x, p.y));
		}

		while (!stateStore.isEmpty()) {

			int shortestP = Integer.MAX_VALUE;
			// Creating a for each loop to run through and find the shortest path using the
			// pathLength() method
			for (TraceState tState : bestPaths) {
				if (tState.pathLength() < shortestP) {
					shortestP = tState.pathLength();
				}
			}
			// retrieving a possible valid state
			TraceState state = stateStore.retrieve();
			if (state.isComplete()) {
				if (state.pathLength() == shortestP) {
					bestPaths.add(state);
				} else if (state.pathLength() < shortestP) {
					bestPaths.clear();
					bestPaths.add(state);
				}
			} else {
				ArrayList<Point> adjacent = state.getBoard().adjacentPoints(state.getRow(), state.getCol());
				for (Point point : adjacent) {
					stateStore.store(new TraceState(state, point.x, point.y));
				}
			}
		}

		if (cOrG.equals("-c")) {
			for (TraceState cState : bestPaths) {
				System.out.println(cState);
			}
		} else if (cOrG.equals("-g")) {
			System.out.print("GUI Not Supported");
		}

	}

} // class CircuitTracer
