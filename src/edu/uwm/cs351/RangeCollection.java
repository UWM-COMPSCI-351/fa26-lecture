package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RangeCollection extends AbstractCollection<Integer> {
	private final int lo, hi;
	
	/**
	 * Represent the integers in the range [lo, hi)
	 * @param lo inclusive lower bound
	 * @param hi exclusive upper bound
	 */
	public RangeCollection(int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new MyIterator();
	}

	@Override
	public int size() {
		return hi-lo;
	}

	private class MyIterator implements Iterator<Integer> {

		private int current = lo-1;
		
		@Override
		public boolean hasNext() {
			return (current+1) < hi;
		}

		@Override
		public Integer next() {
			if (!hasNext()) throw new NoSuchElementException("no more");
			++current;
			return current;
		}
		
	}
}
