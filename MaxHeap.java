import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Devan Craig 
 *  	   The MaxHeap class makes a heap where each parent is
 *         greater then each of there kids (Left and Right)
 * @param <T>
 */
public class MaxHeap<T extends Comparable<T>> {

	public static final int ROOT = 1;
	private int size;
	private ArrayList<T> heap;

	/**
	 * Constructor: Creates the max heap with a starting index of 1
	 */
	public MaxHeap() {
		heap = new ArrayList<>();
		heap.add(null);
	}

	/**
	 * maxHeapify looks at the left and right subtrees using the startInd to build a
	 * max heap down
	 * 
	 * @param startInd the index of the node used by left and right subtrees
	 */
	public void maxHeapify(int startInd) {

		if (startInd <= size) {
			int left = (startInd * 2);
			int right = (startInd * 2) + 1;
			int max;

			if (left <= size && heap.get(left).compareTo(heap.get(startInd)) == 1) {
				max = left;
			} else {
				max = startInd;
			}

			if (right <= size && heap.get(right).compareTo(heap.get(max)) == 1) {
				max = right;
			}

			if (max != startInd) {
				Collections.swap(heap, startInd, max);
				maxHeapify(max);
			}
		}
	}

	/**
	 * 
	 * @param i
	 * @param element
	 */
	public void HeapifyUp(int i, T element) {
		heap.set(i, element);
		int Up = ((i / 2) + 1);
		while (i > ROOT && heap.get(Up).compareTo(heap.get(i)) == -1) {
			Collections.swap(heap, i, Up);
			i = Up;
		}
		maxHeapify(ROOT);
	}

//	// swaps the index and the max
//	private void swap(int t, int t2) {
//		// TODO Auto-generated method stub
//		T holder = heap.get(t);
//		heap.set(t, heap.get(t2));
//		heap.set(t2, holder);
//	}

	/**
	 * Builds a max heap from the bottom up
	 */
	public void buildMaxHeap() {

		size = heap.size() - 1;
		for (int i = size / 2; i > 0; i--) {
			maxHeapify(i);
		}
	}

	/**
	 * Removes the max from the the heap
	 * 
	 * @return the new max node
	 */
	public T removeMax() {
		T max = heap.remove(ROOT);
		size--;
		buildMaxHeap();
		return max;
	}

	/**
	 * Gets the max node from the heap
	 * 
	 * @return max node
	 */
	public T max() {
		return heap.get(ROOT);
	}

	/**
	 * Gets the node based on index
	 * 
	 * @param index of the node selected
	 * @return the index of the node selected
	 */
	public T get(int index) {
		return heap.get(index);
	}

	/**
	 * Inserts a node into the heap based on i
	 * 
	 * @param i node that gets inserted
	 */
	public void insert(T i) {
		heap.add(ROOT, i);
		size = heap.size();
		buildMaxHeap();
	}

	/**
	 * @return the number of nodes in the heap
	 */
	public int size() {
		return size;
	}

	/**
	 * Clears the heap and sets the new starting index back to 1
	 */
	public void clearHeap() {
		heap.clear();
		heap.add(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return heap.toString();

	}

}