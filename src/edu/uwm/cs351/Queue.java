package edu.uwm.cs351;

public class Queue<E> {
	private static final int INITIAL_CAPACITY = 1;
	
	private E[] data;
	private int front, rear;
	
	@SuppressWarnings("unchecked") // we'll take care of it.
	private E[] makeArray(int cap) {
		return (E[]) new Object[cap];
	}
	
	public Queue() {
		data = makeArray(INITIAL_CAPACITY);
		front = 0;
		rear = 0;
	}
	
	/**
	 * 
	 * @param cap minimum size of the array
	 */
	private void ensureCapacity(int cap) {
		if (data.length >= cap ) return;
		if (cap < data.length*2) cap = data.length*2;
		E[] newData = makeArray(cap);
		int j = 0;
		for (int i =front; i < ((rear < front) ? data.length : rear); ++i) {
			newData[j] = data[i];
			++j;
		}
		if (rear < front) {
			for (int i=0 ; i < rear; ++i) {
				newData[j] = data[i];
				++j;
			}
		}
		data = newData;
		front = 0;
		rear = j;
	}
	
	public boolean isEmpty() {
		return front == rear;
	}
	
	public void enqueue(E elem) {
		ensureCapacity(size()+2);
		data[rear] = elem;
		++rear;
		if (rear == data.length) rear = 0;
	}
}
