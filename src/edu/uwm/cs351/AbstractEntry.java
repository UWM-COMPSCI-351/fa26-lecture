package edu.uwm.cs351;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractEntry<K, V> implements Map.Entry<K,V> {

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub (next week?)
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Map.Entry<?,?>)) return false;
		Map.Entry<?,?> e = (Map.Entry<?,?>)obj;
		return Objects.equals(e.getKey(), this.getKey()) &&
				Objects.equals(this.getValue(), e.getValue());
	}

	@Override
	public String toString() {
		return getKey() + "=" + getValue();
	}

}
