import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

	private class Node<T> {

		T item;
		Node<T> next;
		Node<T> previous;

		public Node(T item) {
			this.item = item;
		}
	}

	private class DequeIterator<T> implements Iterator<T> {

		private Node<T> current;

		public DequeIterator(Node<T> head){
			this.current = head;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			T result = null;
			if(!this.hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			result = this.current.item;
			current = current.next;
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	private Node<Item> head;
	private Node<Item> tail;
	private int size;

	// construct an empty deque
	public Deque() {
	}

	// is the deque empty?
	public boolean isEmpty() {
		return this.size() == 0;
	}

	// return the number of items on the deque
	public int size() {
		return this.size;
	}

	// add the item to the front
	public void addFirst(Item item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		Node<Item> newNode = new Node<Item>(item);
		newNode.next = this.head;
		if(this.size() == 0) {
			this.head = newNode;
			this.tail = head;
		} else {
			this.head.previous = newNode;
			this.head = newNode;
		}
		this.size++;
	}

	// add the item to the back
	public void addLast(Item item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		Node<Item> newNode = new Node<Item>(item);
		newNode.previous = this.tail;
		if(this.size() == 0) {
			this.tail = newNode;
			this.head = newNode;
		} else {
			this.tail.next = newNode;
			tail = newNode;
		}
		this.size++;
	}

	// remove and return the item from the front
	public Item removeFirst() {
		Item result = null;
		if(this.size() == 0) {
			throw new NoSuchElementException();
		} else {
			result = this.head.item;
			if(this.size() == 1) {
				this.head = null;
				this.tail = null;
			} else {
				this.head = this.head.next;
				this.head.previous = null;
			}
		}
		this.size--;
		return result;
	}

	// remove and return the item from the back
	public Item removeLast() {
		Item result = null;
		if(this.size() == 0) {
			throw new NoSuchElementException();
		} else {
			result = this.tail.item;
			if(this.size() == 1) {
				this.head = null;
				this.tail = null;
			} else {
				this.tail = this.tail.previous;
				this.tail.next = null;
			}
		}
		this.size--;
		return result;
	}


	// return an iterator over items in order from front to back
	public Iterator<Item> iterator(){
		return new DequeIterator<Item>(this.head);
	}

	// unit testing (required)
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<>();
		StdOut.println(deque.isEmpty());
		deque.addFirst(11);
		deque.addLast(21);
		deque.addFirst(12);
		deque.addLast(22);
		deque.addFirst(13);
		Iterator<Integer> it = deque.iterator();
		while(it.hasNext()) {
			StdOut.println(it.next());
		}
		deque.removeFirst();
		deque.removeLast();
		it = deque.iterator();
		while(it.hasNext()) {
			StdOut.println(it.next());
		}
		StdOut.println(deque.isEmpty());
		StdOut.println(deque.size());
	}

}
