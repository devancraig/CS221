import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Devan Craig
 *
 * Single-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * @param <T>
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;
	private int modCount;

	/**
	 * Initializes are variables
	 */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	/**
	 * Adds an element to the front of the list
	 */
	public void addToFront(T element) {
		// have to have a node
		Node<T> newNode = new Node<T>(element);
		newNode.setNext(head);
		head = newNode;
		// need to add to tail if size is 0
		if (tail == null) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	/**
	 * Adds an element to the end of the list
	 */
	public void addToRear(T element) {
		// Need a new node
		Node<T> newNode = new Node<T>(element);
		if (isEmpty()) {
			head = newNode;
		} else {
			tail.setNext(newNode);
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	/**
	 * Adds an element to the end of the list
	 */
	public void add(T element) {
		addToRear(element);

	}

	@Override
	/**
	 * Adds an element after the specific target
	 */
	public void addAfter(T element, T target) {
		// TODO Find the target node and place the element after
		Node<T> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(target)) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			throw new NoSuchElementException();
		}
		Node<T> newNode = new Node<T>(element);

		newNode.setNext(targetNode.getNext());
		targetNode.setNext(newNode);
		if (targetNode == tail) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	/**
	 * Adds an element to the given index
	 */
	public void add(int index, T element) {
		// TODO Auto-generated method stub
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> newNode = new Node<T>(element);
		if (index == 0) {
			newNode.setNext(head);
			head = newNode;
		} else { // somewhere after head
			Node<T> previousNode = head;
			for (int i = 0; i < index - 1; i++) {
				previousNode = previousNode.getNext();
			}

			newNode.setNext(previousNode.getNext());
			previousNode.setNext(newNode);
		}
		if (newNode.getNext() == null) {
			tail = newNode;
		}
		size++;
		modCount++;

	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();

		str.append("[");
		// have to put stuff in between the brackets
		for (T element : this) {
			str.append(element.toString());
			str.append(", ");
		}
		if (!isEmpty()) {
			str.delete(str.length() - 2, str.length());
		}
		str.append("]");
		return str.toString();
	}

	@Override
	/**
	 * Removes and returns the first element from this list. 
	 */
	public T removeFirst() {
		// Check if its empty first
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		if (size == 1) {
			tail = null;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	/**
	 * Removes and returns the last element from this list. 
	 */
	public T removeLast() {
		// Check if its empty
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		// Keep the element that is tail right now
		T retVal = tail.getElement();
		if (size > 1) {
			Node<T> current = head;
			while (current.getNext() != tail) {
				current = current.getNext();
			}
			// replace the current tail with the new current element
			tail = current;
			tail.setNext(null);
		} else {
			head = tail = null;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	/**
	 * Removes and returns the first element from the list matching the specified element.
	 */
	public T remove(T element) {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		int index = indexOf(element);
		if (index < 0 || index >= size) {
			throw new NoSuchElementException();
		}
		return remove(index);
	}

	@Override
	/**
	 * Removes  and returns the element at the specified index.
	 */
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		T retVal;
		if (index == 0) { // remove head
			retVal = head.getElement();
			head = head.getNext();
			if (head == null) {
				tail = null;
			}
		} else {
			Node<T> previous = head;

			for (int i = 0; i < index - 1; i++) {
				previous = previous.getNext();
			}
			if (previous == tail) {
				throw new NoSuchElementException();
			}
			retVal = previous.getNext().getElement();

			previous.setNext(previous.getNext().getNext());

			if (previous.getNext() == null) {
				tail = previous;
			}
		}

		size--;
		modCount++;
		return retVal;

	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> current = head;

		if (index == 0) {
			head.setElement(element);
		} else {
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}

			if (current == tail) {
				tail.setElement(element);
			}
			current.setElement(element);
		}
		modCount++;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		T retVal;
		Node<T> current = head;
		if (index == 0) {
			retVal = head.getElement();
		} else {
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}

			if (current == tail) {
				retVal = tail.getElement();
			}

			retVal = current.getElement();
		}

		return retVal;
	}

	@Override
	public int indexOf(T element) {
		// go from the start and go through the whole list
		int index = 0;
		Node<T> current = head;
		// current can't be null or current can't equal the same thing that we are on
		while (current != null && !element.equals(current.getElement())) {
			current = current.getNext();
			index++;
		}
		if (current == null) {
			index = -1;
		}
		return index;
	}

	@Override
	public T first() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.getElement();

	}

	@Override
	public T last() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		boolean b = true;
		Node<T> current = head;
		// current can't be null or current can't equal the same thing that we are on
		while (current != null && !target.equals(current.getElement())) {
			current = current.getNext();
		}

		if (current == null) {
			b = false;
		}
		return b;
	}

	@Override
	public boolean isEmpty() {

		return (size == 0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	private class SLLIterator implements Iterator<T> {

		private Node<T> nextNode;
		private int iterModCount;
		private boolean canRemove;

		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			canRemove = false;
		}

		public boolean hasNext() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();
			canRemove = true;
			return retVal;
		}

		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!canRemove) {
				throw new IllegalStateException();
			}
			canRemove = false;

			if (head.getNext() == nextNode) { // removing head
				head = nextNode;
				if (size == 1) { // removed tail, also
					tail = null;
				}
			} else {

				Node<T> prevPrev = head;
				while (prevPrev.getNext().getNext() != nextNode) {
					prevPrev = prevPrev.getNext();

				}
				prevPrev.setNext(nextNode);
				if (nextNode == null) { // or pP.getNext() == null - removed tail
					tail = prevPrev;
				}

			}

			modCount++;
			iterModCount++;
			size--;
		}
	}
}
