import java.util.Random;

/**
 * @author Devan Craig Creates a new random process using a random variable and
 *         a probability
 */
public class ProcessGenerator {

	private Random rand;
	private Double prob;

	/**
	 * Constructor: Creates a probability and a random variable
	 * 
	 * @param prob which is used for the probability
	 */
	public ProcessGenerator(Double prob) {
		this.prob = prob;
		rand = new Random();
	}

	/**
	 * Creates a random variable based on the seed
	 * 
	 * @param prob probability that it will happen
	 * @param seed used for the random variable
	 */
	public ProcessGenerator(Double prob, long seed) {
		this.prob = prob;
		rand = new Random(seed);
	}

	/**
	 * Gets the new process with some of the variables being randomized
	 * @param arrivalTime   the time it takes to arrive
	 * @param processTime   the time it takes to process time
	 * @param priorityLevel the priority level of the process
	 * @return a new process with a randomized processTime and priorityLevel
	 */
	public Process getNewProcess(int arrivalTime, int processTime, int priorityLevel) {
		return new Process(arrivalTime, rand.nextInt(processTime) + 1, rand.nextInt(priorityLevel) + 1);
	}

	/**
	 * Used to see if there was a process generated
	 * @return true or false if there is a process
	 */
	public boolean query() {
		if (prob <= rand.nextDouble()) {
			return true;
		} else {
			return false;
		}
	}

}