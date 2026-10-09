package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Iterator;

public class ArrayCollection<ElementType> extends AbstractCollection<ElementType> {
	private static final int INITIAL_CAPACITY = 1;
	
	private ElementType[] data;
	private int used;
	private int version;
	
	@SuppressWarnings("unchecked") // we'll take care of it.
	private ElementType[] makeArray(int cap) {
		return (ElementType[]) new Object[cap];
	}
	
	public ArrayCollection() {
		data = makeArray(INITIAL_CAPACITY);
	}
	
	private void ensureCapacity(int c) {
		if (data.length >= c) return;
		int newCap = data.length * 2;
		if (newCap < c) newCap = c;
		ElementType[] newArray = makeArray(newCap);
		for (int i=0; i < data.length; ++i) {
			newArray[i] = data[i];
		}
		data = newArray;
	}
	
	public boolean add(ElementType x) {
		ensureCapacity(used+1);
		data[used] = x;
		++used;
		++version;
		return true;
	}

	@Override
	public Iterator<ElementType> iterator() {
		return new MyIterator();
	}

	@Override
	public int size() {
		return used;
	}
	
	@Override
	public ElementType[] toArray() {
		return data; // XXX NEVER DO THIS!
	}
	
	private class MyIterator implements Iterator<ElementType> {
		private int currentIndex = -1;
		
		@Override
		public boolean hasNext() {
			return currentIndex +1 < used;
		}

		@Override
		public ElementType next() {
			++currentIndex;
			return data[currentIndex];
		}
		
	}
}
