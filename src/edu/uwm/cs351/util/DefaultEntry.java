package edu.uwm.cs351.util;

/**
 * A map entry that satisfies all the requirements of an entry,
 * and is mutable.
 * @param K key type
 * @param V value type
 */
public class DefaultEntry<K, V> extends AbstractEntry<K,V> {

	private final K key;
	private V value;
	
	public DefaultEntry(K k, V v) {
		key = k;
		value = v;
	}
	
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V v) {
		V result = value;
		value = v;
		return result;
	}

}
