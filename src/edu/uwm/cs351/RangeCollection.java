package edu.uwm.cs351;

import java.util.AbstractCollection;
import java.util.Iterator;

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

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Integer next() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
