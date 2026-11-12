package edu.uwm.cs351;

public class DefaultEntry<K,V> extends AbstractEntry<K,V> {

	K key;
	V value;
	
	public DefaultEntry(K k, V v) {
		key = k;
		value = v;
	}
	
	@Override
	public K getKey() {
		return this.key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}

}
