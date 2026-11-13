package edu.uwm.cs351;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArrayMap<V> extends AbstractMap<Integer,V> {

	private final V[] data;
	
	public ArrayMap(V[] d) {
		data = d;
	}

	
	@Override // implementation
	public V put(Integer key, V value) {
		V oldValue = data[key];
		data[key] = value;
		return oldValue;
	}


	@Override // efficiency
	public V get(Object key) {
		if (key instanceof Integer) {
			Integer index = (Integer)key;
			if (index < data.length && index >= 0) {
				return data[index];
			}
		}
		return null;
	}


	@Override // required
	public Set<Entry<Integer, V>> entrySet() {
		return new EntrySet();
	}
	
	private class EntrySet extends AbstractSet<Entry<Integer,V>> {

		@Override // required
		public int size() {
			return data.length;
		}

		@Override // required
		public Iterator<Entry<Integer, V>> iterator() {
			return new MyIterator();
		}
		
	}
	
	private class MyIterator implements Iterator<Entry<Integer,V>> {

		private int index = -1;
		
		@Override // required
		public boolean hasNext() {
			return (index+1) < data.length;
		}

		@Override
		public Entry<Integer, V> next() {
			if (!hasNext()) throw new NoSuchElementException("no more");
			++index;
			return new DefaultEntry<>(index,data[index]);
		}
		
	}
}
