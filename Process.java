
/**
 * @author Devan Craig
 * Creates a process that 
 */
public class Process implements Comparable<Process> {
	private int priorityLevel;
	private int arrivalTime;
	private int timeRemaining;
	private int timeNotProcessed;

	/**
	 * Constructor: Creates the process with parameters
	 * @param arrivalTime   the time it takes to arrive
	 * @param processTime   the time it takes to process time
	 * @param priorityLevel the priority level of the process
	 */
	public Process(int arrivalTime, int processTime, int priorityLevel) {
		this.priorityLevel = priorityLevel;
		this.arrivalTime = arrivalTime;
		timeNotProcessed = 0;
		timeRemaining = processTime;
	}

	/**
	 * Gets the priority level of the process
	 * @return priority level
	 */
	public int getPriority() {
		return priorityLevel;
	}

	/**
	 * Increase the priority level
	 */
	public void increasePriority() {
		priorityLevel++;
	}

	/**
	 * Gets the arrival time of the process
	 * @return arrival time
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Gets the time remaining of the process
	 * @return time remaining
	 */
	public int getTimeRemaining() {
		return timeRemaining;
	}

	/**
	 * Gets the time not processed during the process
	 * @return time not processed
	 */
	public int getTimeNotProcessed() {
		return timeNotProcessed;
	}

	/**
	 * reset the time not processed back to 0
	 * @return time not processed
	 */
	public int resetTimeNotProcessed() {
		return timeNotProcessed = 0;
	}

	/**
	 * reduce time remaining and resets the time not processed
	 * @return the new time not processed
	 */
	public int reduceTimeRemaining() {
		timeRemaining--;
		return resetTimeNotProcessed();
	}

	/**
	 * Increase the time remaining
	 */
	public void increaseTimeRemaining() {
		timeRemaining++;
	}

	/**
	 * Checks to see if the process is finish by seeing if it's equal to 0
	 * @return if the time remaining is finished
	 */
	public boolean finish() {
		return getTimeRemaining() == 0;
	}

	/**
	 * Increase time not processed
	 */
	public void increaseTimeNotProcessed() {
		this.timeNotProcessed++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Process o) {
		// TODO Auto-generated method stub
		if (o.priorityLevel < priorityLevel) {
			return -1;
		} else if (o.priorityLevel > priorityLevel) {
			return 1;
		} else if (arrivalTime < o.arrivalTime) {
			return 1;
		} else {
			return 0;
		}
	}

}