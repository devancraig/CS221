import java.net.ConnectException;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * @author Devan Craig
 *
 * Double-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented and a
 * ListIterator that is supported and uses working methods set(), 
 * add() nextIndex(), and previousIndex(). 
 * @param <T>
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {

	private Node<T> head;
	private Node<T> tail;
	private int size;
	private int modCount;

	public IUDoubleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;

	}

	@Override
	public void addToFront(T element) {
		// TODO Auto-generated method stub
		Node<T> newNode = new Node<T>(element);
		newNode.setNext(head);
		if (head != null) {
			head.setPrev(newNode);
		} else {
			tail = newNode;
		}
		head = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		// TODO Auto-generated method stub
		Node<T> newNode = new Node<T>(element);
		newNode.setPrev(tail);
		if (tail != null) {
			tail.setNext(newNode);
		} else {
			head = newNode;
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO Find the target node and place the element after
		ListIterator<T> lit = listIterator();
		boolean foundIt = false;
		while (!foundIt && lit.hasNext()) {
			T value = lit.next();
			if (value.equals(target)) {
				foundIt = true;
			}
		}
		if (!foundIt) {
			throw new NoSuchElementException();
		}
		lit.add(element);
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> newNode = new Node<T>(element);
		Node<T> current = head;

		for (int i = 0; i < index; i++) {
			current = current.getNext();
		}
		newNode.setNext(current);
		if (current != null) {
			newNode.setPrev(current.getPrev());
			current.setPrev(newNode);
		} else {
			newNode.setPrev(tail);
			tail = newNode;
		}
		if (current != head) {
			newNode.getPrev().setNext(newNode);
		} else {

			head = newNode;
		}

		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		if (size > 1) {
			head.getNext().setPrev(null);
		} else {
			tail = null;
		}

		head = head.getNext();
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		if (size > 1) {
			tail.getPrev().setNext(null);
		} else {
			head = null;
		}
		tail = tail.getPrev();
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		// TODO Auto-generated method stub
		ListIterator<T> lit = listIterator();
		boolean foundIt = false;
		while(lit.hasNext() && !foundIt) {
			if(lit.next().equals(element)) {
				foundIt = true;
			}
		}
		if(!foundIt) {
			throw new NoSuchElementException();
		}
		T retVal = lit.previous();
		lit.remove();
		return retVal;
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		ListIterator<T> lit = listIterator(index);
		T retVal = lit.next();
		lit.remove();

		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
			
			ListIterator<T> lit = listIterator(index);
			lit.next();
			lit.set(element);
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

		ListIterator<T> lit = listIterator();
		boolean foundIt = false;
		while(lit.hasNext() && !foundIt) {
			if(lit.next().equals(element)) {
				foundIt = true;
			}
		}
		if(!foundIt) {
			return -1;
		}
		return lit.previousIndex();
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

		return (indexOf(target) != -1);
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return (size == 0);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return new DLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		// TODO Auto-generated method stub
		return new DLLIterator(startingIndex);
	}

	private class DLLIterator implements ListIterator<T> {

		private Node<T> nextNode;
		private Node<T> lastReturned;
		private int iterModCount;
		private int nextIndex;

		public DLLIterator() {
			this(0);
		}

		public DLLIterator(int startingIndex) {
			if (startingIndex < 0 || startingIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			nextNode = head;
			for (int i = 0; i < startingIndex; i++) {
				nextNode = nextNode.getNext();
			}
			nextIndex = startingIndex;
			iterModCount = modCount;

		}

		public void add(T e) {
			Node<T> newNode = new Node<T>(e);
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (isEmpty()) {
				head = tail = newNode;
			} else {
				if (nextNode == head) {
					newNode.setNext(nextNode);
					nextNode.setPrev(newNode);
					head = newNode;
				} else if (nextNode == null) {
					tail.setNext(newNode);
					newNode.setPrev(tail);
					tail = newNode;
				} else {
					nextNode.getPrev().setNext(newNode);
					newNode.setNext(nextNode);
					newNode.setPrev(nextNode.getPrev());
					nextNode.setPrev(newNode);
				}
			}
			iterModCount++;
			modCount++;
			size++;
		}

		public boolean hasNext() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return (nextNode != null);
		}

		public T next() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastReturned = nextNode;
			nextNode = nextNode.getNext();
			nextIndex++;
			return lastReturned.getElement();
		}

		@Override
		public boolean hasPrevious() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}

			return (nextNode != head);
		}

		@Override
		public T previous() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (nextNode != null) {
				nextNode = nextNode.getPrev();
			} else {
				nextNode = tail;
			}
			nextIndex--;
			lastReturned = nextNode;
			return nextNode.getElement();
		}

		@Override
		public int nextIndex() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			// TODO Auto-generated method stub
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			return nextIndex - 1;
		}

		@Override
		public void set(T e) {
			// TODO Auto-generated method stub
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}

			lastReturned.setElement(e);
			modCount++;
			iterModCount++;

		}

		public void remove() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (lastReturned == null) {
				throw new IllegalStateException();
			}

			if (lastReturned != head) {
				lastReturned.getPrev().setNext(lastReturned.getNext());
			} else {
				head = lastReturned.getNext();
				if (head != null) {
					head.setPrev(null);
				}
			}

			if (lastReturned != tail) {
				lastReturned.getNext().setPrev(lastReturned.getPrev());
			} else {
				tail = lastReturned.getPrev();
				if (tail != null) {
					tail.setNext(null);
				}
			}
			if (lastReturned == nextNode) {
				nextNode = nextNode.getNext();
			} else {
				nextIndex--;
			}

			modCount++;
			iterModCount++;
			size--;
			lastReturned = null;
		}
	}
}

