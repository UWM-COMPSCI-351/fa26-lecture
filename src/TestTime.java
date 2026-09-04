import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Time;


public class TestTime extends LockedTestCase {

	private static Calendar lectureStart() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
		cal.set(2026, Calendar.JANUARY, 27, 9, 30, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	private static void shortDelay() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// ignore.  We've woken up anyway
		}
	}

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
	
    
    // Locked tests
    
    public void test() {
    	Calendar cal = lectureStart();
    	Time t = new Time(cal);
    	String firstLectureDay = Ts(1697613965); // first lecture day in yyyy/mm/dd format
    	String firstLectureTime = Ts(1047099245); // first lecture time in hh:mm format (Greenwich mean time!)
    	assertEquals("UTC AD " + firstLectureDay + " " + firstLectureTime + ":00",t.toString());
    	
    	Time t2 = t.add(Duration.DAY);
    	assertEquals(Ts(1728211144),t2.toString()); // don't forget seconds: ":00"

    	Time t3 = t2.subtract(Duration.MINUTE);
    	assertEquals(Ts(1042721294),t3.toString()); // don't forget seconds: ":00"
    	
    	Duration d = t3.difference(t2);
    	assertEquals(Ts(942264642),d.toString()); // don't forget ".0"
    }
    
    
    /// test0X: equals tests
    
    public void test00() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		assertFalse(t1.equals(null));
    }
    
    public void test01() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t1a = new Time(cal);
		assertEquals(t1,t1a);
    }
    
    public void test02() {
    	Time t1 = new Time();
    	shortDelay();
    	Time t2 = new Time();
    	assertNotEquals(t1,t2);
    }

	public void test03() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 1);
		Time t2 = new Time(cal);
		assertNotEquals(t2,t1);
    }
    
	public void test09() {
		assertException(NullPointerException.class, () -> new Time(null));
	}
	
	
	/// text1X: testing hash codes
	
    public void test11() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t1a = new Time(cal);
		assertEquals(t1.hashCode(),t1a.hashCode());
    }
    
    public void test12() {
    	Time t1 = new Time();
    	shortDelay();
    	Time t2 = new Time();
    	assertNotEquals(t1.hashCode(),t2.hashCode());
    }

	public void test13() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 1);
		Time t2 = new Time(cal);
		assertNotEquals(t2.hashCode(),t1.hashCode());
    }
	

	/// test2X: testing compareTo

    public void test21() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t1a = new Time(cal);
		assertEquals(0,t1.compareTo(t1a));
    }
    
    public void test22() {
    	Time t1 = new Time();
    	shortDelay();
    	Time t2 = new Time();
    	assertNegative("then ? now",t1.compareTo(t2));
    }

	public void test23() {
    	Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 1);
		Time t2 = new Time(cal);
		assertPositive("start ? earlier",t1.compareTo(t2));
    }

	public void test24() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.add(Calendar.DAY_OF_MONTH, 40);
		Time t2 = new Time(cal);
		assertNegative("start ? midterm",t1.compareTo(t2));
	}
	
	public void test29() {
    	Time t1 = new Time();
		assertException(NullPointerException.class,() -> t1.compareTo(null));
	}
	

	/// test3X: testing toString()
	
	public void test31() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		assertEquals("UTC AD 2026/01/27 15:30:00",t1.toString());
	}
	
	public void test32() {
		Calendar cal = lectureStart();
		cal.add(Calendar.YEAR, -3000);
		// Three thousand years ago, lecture probably was NOT
		// held on the topic of Data structures at 9:30am.
		// And apparently during the iron age, there was no daylight saving time.
		// during that 3000 years transition.
		Time t1 = new Time(cal);
		assertEquals("UTC BC 0975/01/27 15:30:00",t1.toString());
	}
	
	public void test33() {
		Calendar cal = lectureStart();
		cal.set(1776, Calendar.JULY, 4, 12, 0, 0);
		Time t = new Time(cal);
		assertEquals("UTC AD 1776/07/04 18:00:00",t.toString());
	}
	
	
	/// test4X: test difference
	
	public void test40() {
		Time t = new Time();
		Duration d0 = t.difference(t);
		assertEquals(Duration.INSTANTANEOUS,d0);
	}
	
	public void test41() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t1a = new Time(cal);
		assertEquals(Duration.INSTANTANEOUS,t1.difference(t1a));
	}
	
	public void test42() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 29);
		Time t2 = new Time(cal);
		assertEquals(Duration.MINUTE,t1.difference(t2));
	}
	
	public void test43() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 31);
		Time t2 = new Time(cal);
		assertEquals(Duration.MINUTE,t2.difference(t1));
	}

	public void test49() {
		Time t = new Time();
		assertException(NullPointerException.class,() -> t.difference(null));
	}

	
	/// test5X: test add
	
	public void test50() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		assertEquals(t1,t1.add(Duration.INSTANTANEOUS));
	}
	
	public void test51() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		assertNotEquals(t1,t1.add(Duration.MILLISECOND));
	}
	
	public void test52() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t1a = new Time(cal);
		t1.add(Duration.DAY); // shouldn't have side-effect
		assertEquals(t1a,t1);
	}
	
	public void test53() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t2 = t1.add(Duration.DAY);
		assertEquals("UTC AD 2026/01/28 15:30:00",t2.toString());
	}
	
	public void test54() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		Time t2 = t1.add(Duration.YEAR.scale(4));
		assertEquals("UTC AD 2030/01/27 15:30:00",t2.toString());
	}
	
	public void test55() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 31);
		Time t2 = new Time(cal);
		assertEquals(t2,t1.add(Duration.MINUTE));
	}
	
	public void test59() {
		Time t = new Time();
		assertException(NullPointerException.class,() -> t.add(null));
	}
	
	
	// test 6X: testing subtract
	
	public void test60() {
		Time t = new Time();
		assertEquals(t, t.subtract(Duration.INSTANTANEOUS));
	}
	
	public void test61() {
		Time t = new Time();
		assertNotEquals(t, t.subtract(Duration.MILLISECOND));
	}
	
	public void test62() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.MINUTE, 31);
		Time t2 = new Time(cal);
		assertEquals(t1,t2.subtract(Duration.MINUTE));	
	}
	
	public void test63() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		cal.set(Calendar.HOUR, 8);
		Time t2 = new Time(cal);
		assertEquals(t2,t1.subtract(Duration.HOUR));
	}
	
	public void test64() {
		Time now = new Time();
		Time then = now.subtract(Duration.YEAR.scale(100));
		Duration diff = then.difference(now);
		assertEquals(100.0, diff.divide(Duration.YEAR));
	}
	
	public void test69() {
		Calendar cal = lectureStart();
		Time t1 = new Time(cal);
		assertException(NullPointerException.class,() -> t1.subtract(null));
	}

	
	public void test99() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(0));
		Object t = new Time(c);
		assertEquals("UTC AD 1970/01/01 00:00:00",t.toString());
		assertFalse(t.equals(c));
		assertFalse(t.equals(Duration.INSTANTANEOUS));
	}
}
