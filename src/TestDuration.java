import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Duration;


public class TestDuration extends LockedTestCase {

	protected static void assertNotEquals(Object o1, Object o2) {
		assertFalse(o1 + " should not be equal to " + o2, o1.equals(o2));
	}
	
	protected static void assertNegative(String message, int value) {
		assertTrue(message + " should be negative: " + value, value < 0);
	}
	
	protected static void assertPositive(String message, int value) {
		assertTrue(message + " should be positive: " + value, value > 0);
	}
	
    protected static void assertException(Class<? extends Throwable> c, Runnable r) {
    	try {
    		r.run();
    		assertFalse("Exception should have been thrown",true);
        } catch (RuntimeException ex) {
        	assertTrue("should throw exception of " + c + ", not of " + ex.getClass(), c.isInstance(ex));
        }	
    }	

	
	private static Duration[] constants = { Duration.INSTANTANEOUS, Duration.MILLISECOND,
		Duration.SECOND, Duration.MINUTE, Duration.HOUR, Duration.DAY, Duration.YEAR
	};

	
	/// locked tests
	
	public void test() {
		// How many milliseconds in a second?
		assertEquals(Duration.SECOND,Duration.MILLISECOND.scale(Ti(635787154)));
		// How many seconds in a minutes?
		assertEquals(Duration.MINUTE,Duration.SECOND.scale(Ti(1867091686)));
		// How many hours in a day?
		assertEquals(Duration.DAY,Duration.HOUR.scale(Ti(1052568028)));
		// How many days in a year? (See the homework!)
		assertEquals(Duration.YEAR,Duration.DAY.scale(Tf(132499614)));
		
		// duration arithmetic:
		assertEquals(Duration.SECOND.add(Duration.HOUR),Duration.SECOND.scale(Ti(1820337929)));
		assertEquals(Duration.MINUTE.add(Duration.MINUTE),Duration.SECOND.scale(Ti(1256350997)));
		
		assertEquals(Duration.SECOND.subtract(Duration.MILLISECOND),Duration.MILLISECOND.scale(Ti(2103296898)));

		assertEquals(Td(2011871151),Duration.MINUTE.scale(15).divide(Duration.HOUR));
		assertEquals(Td(330030786),Duration.MINUTE.divide(Duration.MILLISECOND));
	}
	
	
	/// test0X: equals tests:
	
	public void test00() {
		assertNotEquals(Duration.INSTANTANEOUS, null);
	}
	
	public void test01() {
		assertEquals(Duration.INSTANTANEOUS, Duration.INSTANTANEOUS);
	}
	
	public void test02() {
		assertEquals(Duration.MILLISECOND, Duration.MILLISECOND);
		assertNotEquals(Duration.INSTANTANEOUS, Duration.MILLISECOND);
	}
	
	public void test03() {
		assertEquals(Duration.SECOND, Duration.SECOND);
		assertNotEquals(Duration.SECOND, Duration.INSTANTANEOUS);
		assertNotEquals(Duration.MILLISECOND, Duration.SECOND);
	}
	
	public void test04() {
		assertEquals(Duration.MINUTE, Duration.MINUTE);
		assertNotEquals(Duration.INSTANTANEOUS, Duration.MINUTE);
		assertNotEquals(Duration.MINUTE, Duration.MILLISECOND);
		assertNotEquals(Duration.SECOND, Duration.MINUTE);
	}
	
	public void test05() {
		assertEquals(Duration.HOUR, Duration.HOUR);
		assertNotEquals(Duration.HOUR, Duration.INSTANTANEOUS);
		assertNotEquals(Duration.MILLISECOND, Duration.HOUR);
		assertNotEquals(Duration.HOUR, Duration.SECOND);
		assertNotEquals(Duration.MINUTE, Duration.HOUR);
	}
	
	public void test06() {
		assertEquals(Duration.DAY, Duration.DAY);
		assertNotEquals(Duration.INSTANTANEOUS, Duration.DAY);
		assertNotEquals(Duration.DAY, Duration.MILLISECOND);
		assertNotEquals(Duration.SECOND, Duration.DAY);
		assertNotEquals(Duration.DAY, Duration.MINUTE);
		assertNotEquals(Duration.HOUR, Duration.DAY);
	}
	
	public void test07() {
		assertEquals(Duration.YEAR, Duration.YEAR);
		assertNotEquals(Duration.YEAR, Duration.INSTANTANEOUS);
		assertNotEquals(Duration.MILLISECOND, Duration.YEAR);
		assertNotEquals(Duration.YEAR, Duration.SECOND);
		assertNotEquals(Duration.MINUTE, Duration.YEAR);
		assertNotEquals(Duration.YEAR, Duration.HOUR);
		assertNotEquals(Duration.DAY, Duration.YEAR);
	}
	
	public void test08() {
		// hash codes of all named constants should differ:
		assertFalse(Duration.INSTANTANEOUS.hashCode() == Duration.MILLISECOND.hashCode());
		assertFalse(Duration.INSTANTANEOUS.hashCode() == Duration.SECOND.hashCode());
		assertFalse(Duration.INSTANTANEOUS.hashCode() == Duration.MINUTE.hashCode());
		assertFalse(Duration.INSTANTANEOUS.hashCode() == Duration.HOUR.hashCode());
		assertFalse(Duration.MILLISECOND.hashCode() == Duration.SECOND.hashCode());
		assertFalse(Duration.MILLISECOND.hashCode() == Duration.HOUR.hashCode());
		assertFalse(Duration.MILLISECOND.hashCode() == Duration.DAY.hashCode());
		assertFalse(Duration.HOUR.hashCode() == Duration.DAY.hashCode());
	}
	
	public void test09() {
		for (Duration d1 : constants) {
			for (Duration d2 : constants) {
				if (d1 == d2) {
					assertEquals(d1,d2);
				} else {
					assertNotEquals(d1,d2);
					assertFalse("Hashcode of " + d1 + " should not be the same as hash code of " + d2, d1.hashCode() == d2.hashCode());
				}
			}
		}
		assertNotEquals(Duration.SECOND,"1.0 s.");
	}
	

	/// test1X: add tests (also testing equals/hashCode)
	
	public void test10() {
		Duration zero = Duration.INSTANTANEOUS;
		Duration again = zero.add(zero);
		assertEquals(zero,again);
		assertEquals(again,zero);
	}
	
	public void test11() {
		Duration zero = Duration.INSTANTANEOUS;
		Duration one = Duration.SECOND;
		Duration again = zero.add(one);
		assertEquals(again,one);
		assertEquals(one,again);
		assertTrue(one.hashCode() == again.hashCode());
	}
	
	public void test12() {
		Duration two = Duration.SECOND.add(Duration.SECOND);
		assertNotEquals(two,Duration.SECOND);
	}
	
	public void test13() {
		Duration one = Duration.MINUTE;
		Duration two = one.add(one);
		Duration three = two.add(one);
		assertNotEquals(two,three);
		assertFalse(two.hashCode() == three.hashCode());
	}
	
	public void test14() {
		Duration one = Duration.HOUR;
		Duration two = one.add(one);
		Duration four = two.add(two);
		Duration eight = four.add(four);
		Duration sixteen = eight.add(eight);
		Duration twentyFour = eight.add(sixteen);
		assertEquals(Duration.DAY, twentyFour);
	}
	
	public void test15() {
		Duration one = Duration.SECOND;
		Duration two = one.add(one);
		Duration four = two.add(two);
		Duration eight = four.add(four);
		Duration sixteen = eight.add(eight);
		Duration thirtytwo = sixteen.add(sixteen);
		Duration sixty = thirtytwo.add(sixteen).add(eight).add(four);
		assertEquals(Duration.MINUTE,sixty);
		assertTrue(Duration.MINUTE.hashCode() == sixty.hashCode());
	}
	
	public void test16() {
		Duration one = Duration.MILLISECOND;
		Duration two = one.add(one);
		Duration four = two.add(two);
		Duration eight = four.add(four);
		Duration ten = eight.add(two);
		Duration twenty = ten.add(ten);
		Duration forty = twenty.add(twenty);
		Duration eighty = forty.add(forty);
		Duration hundred = eighty.add(twenty);
		Duration X200 = hundred.add(hundred);
		Duration X400 = X200.add(X200);
		Duration X800 = X400.add(X400);
		Duration thousand = X800.add(X200);
		assertEquals(thousand, Duration.SECOND);
		assertTrue(thousand.hashCode() == Duration.SECOND.hashCode());
	}
	
	public void test19() {
		try {
			Duration.YEAR.add(null);
			assertFalse("should not add null",true);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("throw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
	}

	
	/// test2X: tests of scale
	
	public void test20() {
		Duration zero = Duration.INSTANTANEOUS.scale(60);
		assertEquals(zero, Duration.INSTANTANEOUS);
	}
	
	public void test21() {
		Duration zero = Duration.DAY.scale(0);
		assertEquals(zero, Duration.INSTANTANEOUS);
	}
	
	public void test22() {
		Duration one = Duration.MILLISECOND.scale(1000);
		assertEquals(Duration.SECOND,one);
	}
	
	public void test23() {
		assertEquals(Duration.SECOND.scale(3600), Duration.HOUR);
	}
	
	public void test24() {
		assertEquals(Duration.SECOND.scale(0.001), Duration.MILLISECOND);
	}
	
	public void test25() {
		assertEquals(Duration.HOUR.scale(8766), Duration.YEAR);
	}
	
	public void test26() {
		Duration x84 = Duration.MINUTE.scale(84);
		Duration h1x4 = Duration.HOUR.scale(1.4);
		assertEquals(x84,h1x4);
	}
		
	public void test27() {
		Duration x23 = Duration.MILLISECOND.scale(0.66);
		assertEquals(Duration.MILLISECOND,x23);
		Duration x43 = Duration.MILLISECOND.scale(1.33);
		assertEquals(Duration.MILLISECOND,x43);
		assertEquals(Duration.SECOND,Duration.MILLISECOND.scale(1000.33));
	}
	
	public void test28() {
		Duration x13 = Duration.MILLISECOND.scale(0.33);
		assertEquals(Duration.INSTANTANEOUS,x13);
		Duration x53 = Duration.MILLISECOND.scale(1.66);
		assertEquals(Duration.MILLISECOND.add(Duration.MILLISECOND),x53);
		assertEquals(Duration.SECOND,Duration.MILLISECOND.scale(999.66));
	}

	public void test29() {
		assertException(IllegalArgumentException.class, () -> Duration.HOUR.scale(-2.0));
		Duration hp = Duration.HOUR.scale(1.0001);
		assertNotEquals(hp,Duration.HOUR);
	}
	
	
	/// test3X: tests of subtract
	
	public void test30() {
		Duration d = Duration.HOUR.subtract(Duration.INSTANTANEOUS);
		assertEquals(d,Duration.HOUR);
	}
	
	public void test31() {
		Duration d = Duration.DAY.subtract(Duration.DAY);
		assertEquals(d,Duration.INSTANTANEOUS);
	}
	
	public void test32() {
		Duration d = Duration.HOUR.subtract(Duration.MINUTE);
		d = Duration.HOUR.subtract(d);
		assertEquals(d,Duration.MINUTE);
	}
	
	public void test33() {
		Duration d = Duration.MINUTE;
		for (int i=0; i < 59; ++i) {
			d = d.subtract(Duration.SECOND);
		}
		assertEquals(d,Duration.SECOND);
	}
	
	public void test34() {
		Duration d = Duration.YEAR;
		d = d.subtract(Duration.MILLISECOND);
		assertNotEquals(d,Duration.YEAR);
	}
	
	public void test37() {
		assertException(ArithmeticException.class, () -> Duration.HOUR.subtract(Duration.DAY));
	}
	
	public void test38() {
		Duration hm = Duration.HOUR.subtract(Duration.MILLISECOND);
		assertException(ArithmeticException.class, () -> hm.subtract(Duration.HOUR));
	}
	
	public void test39() {
		assertException(NullPointerException.class,() -> Duration.YEAR.subtract(null));
	}
	
	
	/// test4X: divide
	
	public void test40() {
		assertEquals(0.0d, Duration.INSTANTANEOUS.divide(Duration.SECOND));
	}
	
	public void test41() {
		assertEquals(1.0d, Duration.SECOND.divide(Duration.SECOND));
	}
	
	public void test42() {
		assertEquals(1000.0d, Duration.SECOND.divide(Duration.MILLISECOND));
	}
	
	public void test43() {
		assertEquals((double)1.0/3600, Duration.SECOND.divide(Duration.HOUR));
	}
	
	public void test45() {
		assertEquals(365.25, Duration.YEAR.divide(Duration.DAY));
	}
	
	public void test47() {
		assertException(ArithmeticException.class, () -> Duration.MINUTE.divide(Duration.INSTANTANEOUS));
	}
	
	public void test48() {
		assertException(ArithmeticException.class, () -> Duration.INSTANTANEOUS.divide(Duration.INSTANTANEOUS));
	}
	
	public void test49() {
		assertException(NullPointerException.class, () -> Duration.HOUR.divide(null));
	}
	
	
	/// test5X: compareTo
	
	public void test50() {
		assertEquals(0,Duration.INSTANTANEOUS.compareTo(Duration.INSTANTANEOUS));
	}
	
	public void test51() {
		assertNegative("0 ? 1",Duration.INSTANTANEOUS.compareTo(Duration.MILLISECOND));
		assertPositive("1 ? 0",Duration.MILLISECOND.compareTo(Duration.INSTANTANEOUS));
		assertEquals(0, Duration.MILLISECOND.compareTo(Duration.MILLISECOND));
	}
	
	public void test52() {
		for (int i=0; i < constants.length; ++i) {
			for (int j=0; j < i; ++j) {
				assertPositive(i + " ? " + j,constants[i].compareTo(constants[j]));
				assertNegative(j + " ? " + i,constants[j].compareTo(constants[i]));
			}
			assertEquals(i + " ? " + i, 0, constants[i].compareTo(constants[i]));
		}
	}

	public void test53() {
		Duration one = Duration.MINUTE;
		Duration four = one.add(one).add(one).add(one);
		Duration eight = four.add(four);
		Duration sixteen = four.add(four).add(eight);
		Duration m32 = sixteen.add(sixteen);
		Duration m60 = m32.add(sixteen).add(eight).add(four);
		Duration m64 = m32.add(m32);
		assertEquals(0,m60.compareTo(Duration.HOUR));
		assertPositive("64 ? 60",m64.compareTo(Duration.HOUR));
		assertNegative("32 ? 60",m32.compareTo(Duration.HOUR));
	}
	
	public void test55() {
		// 40 days is more than MAXINT milliseconds.
		// This shows that compareTo must not cast a long to an int.
		Duration lengthy = Duration.DAY.scale(40);
		assertNegative("ms ? 40 days",Duration.MILLISECOND.compareTo(lengthy));
		Duration longer = lengthy.add(Duration.MILLISECOND);
		assertPositive("40 days+ ? 40 days",longer.compareTo(lengthy));
	}
	
	public void test59() {
		assertException(NullPointerException.class,() -> Duration.MINUTE.compareTo(null));
	}
	
	
	/// test6X: toString tests
	
	public void test60() {
		assertEquals("0.0 ms.",Duration.INSTANTANEOUS.toString());
	}
	
	public void test61() {
		assertEquals("1.0 ms.",Duration.MILLISECOND.toString());
	}
	
	public void test62() {
		assertEquals("1.0 s.",Duration.SECOND.toString());
	}
	
	public void test63() {
		assertEquals("1.0 min.",Duration.MINUTE.toString());
	}
	
	public void test64() {
		assertEquals("1.0 hr.",Duration.HOUR.toString());
	}
	
	public void test65() {
		assertEquals("1.0 days",Duration.DAY.toString());
	}
	
	public void test66() {
		assertEquals("1.0 years",Duration.YEAR.toString());
	}
	
	public void test67() {
		assertEquals("16.0 s.", Duration.SECOND.scale(16).toString());
	}
	
	public void test68() {
		assertEquals("1.5 min.", Duration.SECOND.scale(90).toString());
	}

	public void test69() {
		assertEquals("1.5 days", Duration.HOUR.scale(36).toString());
	}

}
