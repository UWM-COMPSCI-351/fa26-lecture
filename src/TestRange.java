

import java.util.Collection;
import java.util.Iterator;

import edu.uwm.cs351.RangeCollection;
import junit.framework.TestCase;

public class TestRange extends TestCase {

	public void test07() {
		Collection<Integer> r = new RangeCollection(1,10);
		assertFalse(r.isEmpty());
		for (Iterator<Integer> it = r.iterator(); it.hasNext();) {
			Integer i = it.next();
			System.out.println(i);
		}
		for (Integer x : r) {
			System.out.println(x);
		}
		assertFalse(r.isEmpty());
		assertTrue(r.contains(5));
		assertFalse(r.contains(0));
		assertFalse(r.contains(10));
		assertTrue(r.contains(1));
	}
	
	public void test08() {
		Collection<Integer> r = new RangeCollection(10,10);
		for (Iterator<Integer> it = r.iterator(); it.hasNext();) {
			Integer i = it.next();
			System.out.println(i);
		}
		assertEquals(0,r.size());
		assertTrue(r.isEmpty());
		r.clear();
	}
	
	public void test09() {
		Collection<Integer> r = new RangeCollection(2,5);
		assertEquals(2, r.iterator().next().intValue());
	}
	
	public void test36() {
		Collection<Integer> r = new RangeCollection(0,10);
		try {
			r.add(-3);
			assertFalse("should have thrown an exception!", true);
		} catch (RuntimeException ex) {
			assertTrue(UnsupportedOperationException.class.isInstance(ex));
		}
	}
	
	public void test90() {
		Collection<Integer> r = new RangeCollection(0,10);
		assertFalse(r.contains(null));
	}
	
	public void test99() {
		Collection<Integer> r = new RangeCollection(-2000_000_000,2000_000_000);
		assertTrue(r.contains(999_999_999));
		assertFalse(r.contains(2_000_000_000));
	}
}
