
/**
 * @author Devan Craig Creates a priority queue using methods from the MaxHeap
 *         class
 */
public class PQueue {

	private MaxHeap<Process> pQueue;

	/**
	 * Constructor: Makes a priority queue
	 */
	public PQueue() {
		pQueue = new MaxHeap<>();
	}

	/**
	 * Removes a process from the queue
	 * 
	 * @return the removed queue
	 */
	public Process dePQueue() {
		return pQueue.removeMax();
	}

	/**
	 * Updates the process in the max heap, then rebuild the max heap
	 * 
	 * @param timeToIncrement the time it takes to increment the process
	 * @param maxLevel        the max priority we can take in.
	 */
	public void update(int timeToIncrement, int maxLevel) {
		for (int i = MaxHeap.ROOT; i <= pQueue.size(); i++) {
			Process p = pQueue.get(i);
			p.increaseTimeNotProcessed();
			if (p.getTimeNotProcessed() >= timeToIncrement) {
				if (p.getPriority() < maxLevel) {
					p.increasePriority();
				}
				p.resetTimeNotProcessed();
			}
		}
		pQueue.buildMaxHeap();
	}

	/**
	 * Inserts a new process into the queue
	 * 
	 * @param in places the process to be inserted
	 */
	public void enPQueue(Process in) {
		pQueue.insert(in);
	}

	/**
	 * Checks to see if the queue is empty
	 * 
	 * @return if the queue is empty
	 */
	public boolean isEmpty() {
		return pQueue.size() < 1;
	}

	/**
	 * Clears the queue and all the process
	 */
	public void clearPQueue() {
		pQueue.clearHeap();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return pQueue.toString();
	}

}