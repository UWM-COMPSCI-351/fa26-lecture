import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.uwm.cs351.ArrayMap;
import edu.uwm.cs351.DefaultEntry;
import junit.framework.TestCase;

public class TestArrayMap extends TestCase {

	private String[] array;
	private ArrayMap<String> am;
	private Set<Map.Entry<Integer,String>> es;
	
	protected void setUp() {
		array = new String[] { "dog", "cat", "rat", "snake" };
		am = new ArrayMap<>(array);
		es = am.entrySet();
	}
	
	public void test0() {
		assertEquals(4, am.size());
		assertEquals(4, es.size());
	}
	
	public void test1() {
		assertEquals("dog", am.get(0));
		assertEquals("cat", am.get(1));
		assertEquals("rat", am.get(2));
		assertEquals("snake", am.get(3));
		assertNull(am.get(4));
		assertNull(am.get(-1));
	}
	
	public void test2() {
		assertTrue(am.containsKey(0));
		assertTrue(am.containsKey(1));
		assertTrue(am.containsKey(2));
		assertTrue(am.containsKey(3));
		assertFalse(am.containsKey(4));
		assertFalse(am.containsKey(-1));
	}
	
	public void test3() {
		assertEquals("snake", am.put(3, "ferret"));
		assertEquals("ferret", array[3]);
	}
	
	public void test4() {
		Map.Entry<Integer,String> en1 = new DefaultEntry<>(3, "rat");
		Map.Entry<Integer,String> en2 = new DefaultEntry<>(3,"rat");
		assertEquals(en1, en2);
		for (Map.Entry<Integer,String> e : es) {
			System.out.println(e);
			System.out.println(e.equals(en1));
		}
		assertTrue(es.contains((Object)new DefaultEntry<>(3, "snake")));
		assertFalse(es.contains((Object)new DefaultEntry<>(3, "ferret")));
	}
	
	public void test5() {
		Set<Integer> indices = am.keySet();
		Iterator<Integer> it = indices.iterator();
		assertEquals(Integer.valueOf(0), it.next());
		assertTrue(indices.contains(3));
		assertFalse(indices.contains(4));
	}
	
	public void test6() {
		assertTrue(am.values().contains("snake"));
		assertFalse(am.values().contains("ferret"));
	}
	
	public void test7() {
		assertNull(am.get(new Object()));
	}
	
	public void test8() {
		assertNull(am.get(null));
	}
}
