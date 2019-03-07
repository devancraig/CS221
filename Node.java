
public class Node<T> {
	private T element;
	private Node<T> next;
	private Node<T> prev;
	
	
	public Node<T> getPrev() {
		return prev;
	}


	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}


	public Node(T element) {
		this.element = element;
		this.next = null;
	}


	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	@Override
	public String toString() {
		return "Element: " + element.toString() + " Has next: " + (next != null);
	}
}
