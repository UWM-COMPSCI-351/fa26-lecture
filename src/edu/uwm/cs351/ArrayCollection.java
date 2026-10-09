package edu.uwm.cs351;

import java.util.AbstractCollection;

public class ArrayCollection<ElementType> extends AbstractCollection<ElementType> {
	private static final INITIAL_CAPACITY = 1;
	
	private ElementType[] data;
	
	public ArrayCollection() {
		data = new ElementType[INITIAL_CAPACITY];
	}
	
	private void ensureCapacity(int c) {
		if (data.length >= c) return;
		int newCap = data.length * 2;
		if (newCap < c) newCap = c;
		ElementType[] newArray = new ElementType[newCap];
		for (int i=0; i < data.length; ++i) {
			newArray[i] = data[i];
		}
		data = newArray;
	}
}
