import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private class RandomizedQueueIterator<T> implements Iterator<T> {

		private int shuffled_indexes[];
		private RandomizedQueue<T> rq;
		private int index;

		public RandomizedQueueIterator(RandomizedQueue<T> rq){
			this.rq = rq;
			this.shuffled_indexes = new int[rq.size()];
			for(int i = 0; i < rq.size(); i++) {
				shuffled_indexes[i] = i;
			}
			StdRandom.shuffle(shuffled_indexes);
		}

		@Override
		public boolean hasNext() {
			return index < shuffled_indexes.length;
		}

		@Override
		public T next() {
			T result = null;
			if(!this.hasNext()) {
				throw new java.util.NoSuchElementException();
			}
			result = this.rq.list[shuffled_indexes[index]];
			index++;
			return result;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
	
	private Item list[];
	private int size;
	
	private static final int INITIAL_SIZE = 8;

    // construct an empty randomized queue
	public RandomizedQueue() {
    	this.list = (Item[]) new Object[RandomizedQueue.INITIAL_SIZE];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
    	return this.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return this.size;
    }

    // add the item
    public void enqueue(Item item) {
		if(item == null) {
			throw new IllegalArgumentException();
		}
		if(this.size() == this.list.length) {
			Item newList[] = (Item[]) new Object[this.list.length * 2];
			for(int i = 0; i < list.length; i++) {
				newList[i] = list[i];
			}
			list = newList;
		}
		this.list[size] = item;
		size++;
    }

    // remove and return a random item
    public Item dequeue() {
		if(this.size() == 0) {
			throw new NoSuchElementException();
		}
		int index = StdRandom.uniformInt(this.size());
		Item result = this.list[index];
		this.list[index] = this.list[size-1];
		this.list[size-1] = null;
		size--;
		return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
		if(this.size() == 0) {
			throw new NoSuchElementException();
		}
		int index = StdRandom.uniformInt(this.size());
    	return this.list[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
    	return new RandomizedQueueIterator<Item>(this);
    }

    // unit testing (required)
    public static void main(String[] args) {
    	RandomizedQueue<Integer> rqueue = new RandomizedQueue<>();
		StdOut.println(rqueue.isEmpty());
		rqueue.enqueue(1);
		rqueue.enqueue(2);
		rqueue.enqueue(3);
		rqueue.enqueue(4);
		rqueue.enqueue(5);
		StdOut.println("Dequeue: " + rqueue.dequeue());
		StdOut.println("Dequeue: " + rqueue.dequeue());
		Iterator<Integer> it = rqueue.iterator();
		while(it.hasNext()) {
			StdOut.println("IT1: " + it.next());
		}
		it = rqueue.iterator();
		while(it.hasNext()) {
			StdOut.println("IT2: " + it.next());
		}
		StdOut.println(rqueue.isEmpty());
		StdOut.println(rqueue.size());
		StdOut.println(rqueue.sample());
    }

}
