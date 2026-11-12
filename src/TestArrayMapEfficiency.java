import java.util.Map;

import edu.uwm.cs351.ArrayMap;
import junit.framework.TestCase;

public class TestArrayMapEfficiency extends TestCase {

	String[] array;
	Map<Integer, String> am;
	
	private static final int POWER = 20;
	private static final int MAX = 1 << POWER;
	
	@Override
	protected void setUp() {
		array = new String[MAX];
		for (int i=0; i < MAX; ++i) {
			array[i] = "#" + i;
		}
		am = new ArrayMap<>(array);
	}
	
	public void testA() {
		for (int i=0; i < MAX; ++i) {
			assertEquals(MAX, am.size());
		}
	}
	
	public void testB() {
		for (int i=0; i < MAX; ++i) {
			assertEquals("#" + i, am.get(i));
		}
	}
	
	public void testC() {
		for (int i=0; i < MAX; ++i) {
			assertEquals(null, am.get(-1-i));
		}
	}
	
	public void testD() {
		for (int i=0; i < MAX; ++i) {
			assertTrue(am.containsKey(i));
		}
	}
	
	public void testE() {
		for (int i=0; i < MAX; ++i) {
			assertFalse(am.containsKey(-1-i));
		}
	}
}
