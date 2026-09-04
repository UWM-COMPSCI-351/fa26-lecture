import java.util.Calendar;
import java.util.TimeZone;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Period;
import edu.uwm.cs351.Time;


public class TestPeriod extends LockedTestCase {
	protected void assertException(Class<?> excClass, Runnable r) {
		try {
			r.run();
			assertFalse("Should have thrown an exception, rather than returning", true);
		} catch (RuntimeException ex) {
			assertEquals("threw wrong exception type " + ex.getClass().getSimpleName(), excClass, ex.getClass());
		}
	}
	
	protected static void assertNotEquals(Object o1, Object o2) {
		assertFalse(o1 + " should not be equal to " + o2, o1.equals(o2));
	}
	
	private static Calendar lectureStart() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
		cal.set(2018, Calendar.SEPTEMBER, 4, 9, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	
	/// locked tests
	
	public void test() {
		Calendar cal = lectureStart();
		cal.set(2018, Calendar.NOVEMBER, 6, 7, 0);
		Time pollsOpen = new Time(cal);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		Time noon = new Time(cal);
		cal.set(Calendar.HOUR_OF_DAY, 20);
		Time pollsClose = new Time(cal);
		
		Period polls = new Period(pollsOpen,pollsClose);
		assertEquals(Ts(82369862),polls.getLength().toString()); // don't forget .0!
		
		Period elevenses = new Period(Duration.HOUR,noon);
		assertEquals(Tb(1293245769),elevenses.overlap(polls));
		
		Period nap = new Period(noon, Duration.HOUR);
		assertEquals(Tb(512696856),elevenses.overlap(nap));
		
		Period dinner = new Period(pollsClose,Duration.HOUR);
		assertEquals(Tb(1097059735),dinner.overlap(polls));
		
		assertEquals(Tb(731734514),nap.overlap(dinner));
		
		assertEquals(Tb(1674379837),polls.overlap(polls));
	}
	
	/// testAX: constructor calls and accessors
	
	public void testA1() {
		Calendar now = lectureStart();
		Time start = new Time(now);
		now.add(Calendar.MINUTE,50);
		Time stop = new Time(now);
		Duration length = Duration.MINUTE.scale(50);
		Period p;
		
		p = new Period(start,length);
		assertEquals(start,p.getStart());
		assertEquals(length,p.getLength());
		assertEquals(stop,p.getStop());
	}
		
	public void testA2() {
		Calendar now = lectureStart();
		Time start = new Time(now);
		now.add(Calendar.MINUTE,50);
		Time stop = new Time(now);
		Duration length = Duration.MINUTE.scale(50);
		Period p;

		p = new Period(start,stop);
		assertEquals(start,p.getStart());
		assertEquals(length,p.getLength());
		assertEquals(stop,p.getStop());
	}
	
	public void testA3() {
		Calendar now = lectureStart();
		Time start = new Time(now);
		now.add(Calendar.MINUTE,50);
		Time stop = new Time(now);
		Duration length = Duration.MINUTE.scale(50);
		Period p;

		p = new Period(length,stop);
		assertEquals(start,p.getStart());
		assertEquals(length,p.getLength());
		assertEquals(stop,p.getStop());
		
	}
	
	public void testA5() {
		Calendar now = lectureStart();
		Time start = new Time(now);
		now.add(Calendar.MINUTE,50);
		Duration length = Duration.INSTANTANEOUS;
		Period p;
		
		p = new Period(start,length);
		assertEquals(start,p.getStart());
		assertEquals(length,p.getLength());
		assertEquals(start,p.getStop());
	}
	
	public void testA6() {
		Calendar now = lectureStart();
		Time start = new Time(now);
		now.add(Calendar.MINUTE,50);
		Duration length = Duration.INSTANTANEOUS;
		Period p;
		
		p = new Period(start,start);
		assertEquals(start,p.getStart());
		assertEquals(length,p.getLength());
		assertEquals(start,p.getStop());
	}
	
	public void testA7() {
		Calendar now = lectureStart();
		now.add(Calendar.MINUTE,50);
		Time stop = new Time(now);
		Duration length = Duration.INSTANTANEOUS;
		Period p;

		p = new Period(length,stop);
		assertEquals(stop,p.getStart());
		assertEquals(length,p.getLength());
		assertEquals(stop,p.getStop());
	}
	
	public void testA8() {
		Time now = new Time();
		assertException(NullPointerException.class, () -> new Period(null,Duration.HOUR));
		assertException(NullPointerException.class, () -> new Period(now, (Duration)null));
		assertException(NullPointerException.class, () -> new Period(now, (Time)null));
		assertException(NullPointerException.class, () -> new Period((Time)null, now));
		assertException(NullPointerException.class, () -> new Period((Duration)null, now));
		assertException(NullPointerException.class, () -> new Period(Duration.HOUR, null));
	}
	
	public void testA9() {
		Time now = new Time();
		Time then = now.subtract(Duration.HOUR);
		assertException(IllegalArgumentException.class, () -> new Period(now, then));
	}
	
	
	
	/// testEX: testing equality
		
	public void testE1() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p1a = new Period(Duration.HOUR,p1.getStop());

		assertEquals(p1,p1a);
	}

	public void testE2() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p1b = new Period(p1.getStart(),p1.getStop());

		assertEquals(p1,p1b);
	}

	public void testE3() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p1a = new Period(Duration.HOUR,p1.getStop());
		Period p1b = new Period(p1.getStart(),p1.getStop());
		
		assertEquals(p1a,p1b);
	}

	public void testE4() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p2 = new Period(p1.getStart(), Duration.MINUTE);
		
		assertFalse(p1.equals(p2));
		assertFalse(p2.equals(p1));
	}

	public void testE5() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p3 = new Period(p1.getStop(), Duration.HOUR);
		
		assertFalse(p1.equals(p3));
		assertFalse(p3.equals(p1));
	}

	public void testE6() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		
		assertFalse(p1.equals(null));
	}

	public void testE7() {
		Period p1 = new Period(new Time(), Duration.HOUR);

		assertFalse(p1.equals(Duration.HOUR));
	}

	public void testE8() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		
		assertNotEquals(p1,p1.getStart());
	}

	
	/// testHX: testing hashcode
	
	public void testH1() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p1a = new Period(Duration.HOUR,p1.getStop());
		
		assertEquals(p1.hashCode(),p1a.hashCode());
	}

	public void testH2() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p1b = new Period(p1.getStart(),p1.getStop());
		
		assertEquals(p1.hashCode(),p1b.hashCode());		
	}

	public void testH3() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p2 = new Period(p1.getStart(), Duration.MINUTE);

		assertNotEquals(p1.hashCode(),p2.hashCode());
	}

	public void testH4() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p2 = new Period(p1.getStart(), Duration.MINUTE);
		Period p3 = new Period(p1.getStop(), Duration.HOUR);
		
		assertNotEquals(p2.hashCode(),p3.hashCode());
	}

	public void testH5() {
		Period p1 = new Period(new Time(), Duration.HOUR);
		Period p3 = new Period(p1.getStop(), Duration.HOUR);
		
		assertNotEquals(p3.hashCode(),p1.hashCode());
	}
	
	
	/// toString
	
	public void testS1() {
		Calendar lecture = lectureStart();
		Period p = new Period(new Time(lecture),Duration.MINUTE.scale(50));
		assertEquals("[UTC AD 2018/09/04 14:00:00; 50.0 min.]",p.toString());
	}

	
	/// TestVX: overlap tests.  ("O" looked too much like "0") 
	
	public void testV0() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));
		
		assertFalse(p35.overlap(new Period(t0,Duration.MILLISECOND)));
	}
	
	public void testV1() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t1,Duration.MILLISECOND)));
	}
	
	public void testV2() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t2,Duration.MILLISECOND)));
	}
	
	public void testV3() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertTrue(p35.overlap(new Period(t3,Duration.MILLISECOND)));
	}
	
	public void testV4() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertTrue(p35.overlap(new Period(t4,Duration.MILLISECOND)));
	}
	
	public void testV5() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t5,Duration.MILLISECOND)));
	}
	
	public void testV6() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		Time t6 = t5.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t6,Duration.MILLISECOND)));
		
	}
	
	public void testV7() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t0,Duration.MILLISECOND.scale(2))));
	}
	
	public void testV8() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t1,Duration.MILLISECOND.scale(2))));
	}
	
	public void testV9() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertTrue(p35.overlap(new Period(t2,Duration.MILLISECOND.scale(2))));
	}
	
	public void testW0() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertTrue(p35.overlap(new Period(t3,Duration.MILLISECOND.scale(2))));
	}
	
	public void testW1() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertTrue(p35.overlap(new Period(t4,Duration.MILLISECOND.scale(2))));
	}
	
	public void testW2() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t5,Duration.MILLISECOND.scale(2))));
	}
	
	public void testW3() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		Time t6 = t5.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t6,Duration.MILLISECOND.scale(2))));
	}
	
	public void testW4() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		Time t6 = t5.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(p35.overlap(new Period(t0,Duration.MILLISECOND.scale(3))));
		assertTrue(p35.overlap(new Period(t1,Duration.MILLISECOND.scale(3))));
		assertTrue(p35.overlap(new Period(t2,Duration.MILLISECOND.scale(3))));
		assertTrue(p35.overlap(new Period(t3,Duration.MILLISECOND.scale(3))));
		assertTrue(p35.overlap(new Period(t4,Duration.MILLISECOND.scale(3))));
		assertFalse(p35.overlap(new Period(t5,Duration.MILLISECOND.scale(3))));
		assertFalse(p35.overlap(new Period(t6,Duration.MILLISECOND.scale(3))));	
	}
	
	public void testW5() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		Time t6 = t5.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertTrue(p35.overlap(new Period(t0,Duration.MILLISECOND.scale(4))));
		assertTrue(p35.overlap(new Period(t1,Duration.MILLISECOND.scale(4))));
		assertTrue(p35.overlap(new Period(t2,Duration.MILLISECOND.scale(4))));
		assertTrue(p35.overlap(new Period(t3,Duration.MILLISECOND.scale(4))));
		assertTrue(p35.overlap(new Period(t4,Duration.MILLISECOND.scale(4))));
		assertFalse(p35.overlap(new Period(t5,Duration.MILLISECOND.scale(4))));
		assertFalse(p35.overlap(new Period(t6,Duration.MILLISECOND.scale(4))));
	}
	
	public void testW8() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		Time t6 = t5.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		// instantaneous periods must also be handled.
		// Try to avoid using a special case for them.
		
		assertFalse(p35.overlap(new Period(t0,Duration.INSTANTANEOUS)));
		assertFalse(p35.overlap(new Period(t1,Duration.INSTANTANEOUS)));
		assertFalse(p35.overlap(new Period(t2,Duration.INSTANTANEOUS)));
		assertFalse(p35.overlap(new Period(t3,Duration.INSTANTANEOUS)));
		assertTrue(p35.overlap(new Period(t4,Duration.INSTANTANEOUS)));
		assertFalse(p35.overlap(new Period(t5,Duration.INSTANTANEOUS)));
		assertFalse(p35.overlap(new Period(t6,Duration.INSTANTANEOUS)));
	}
	
	public void testW9() {
		Time t0 = new Time();
		Time t1 = t0.add(Duration.MILLISECOND);
		Time t2 = t1.add(Duration.MILLISECOND);
		Time t3 = t2.add(Duration.MILLISECOND);
		Time t4 = t3.add(Duration.MILLISECOND);
		Time t5 = t4.add(Duration.MILLISECOND);
		Time t6 = t5.add(Duration.MILLISECOND);
		
		Period p35 = new Period(t3,Duration.MILLISECOND.scale(2));

		assertFalse(new Period(t0,Duration.INSTANTANEOUS).overlap(p35));
		assertFalse(new Period(t1,Duration.INSTANTANEOUS).overlap(p35));
		assertFalse(new Period(t2,Duration.INSTANTANEOUS).overlap(p35));
		assertFalse(new Period(t3,Duration.INSTANTANEOUS).overlap(p35));
		assertTrue(new Period(t4,Duration.INSTANTANEOUS).overlap(p35));
		assertFalse(new Period(t5,Duration.INSTANTANEOUS).overlap(p35));
		assertFalse(new Period(t6,Duration.INSTANTANEOUS).overlap(p35));
	}
	
	
	// bad errors
	
	public void testX9() {
		// 9 different ways to pass null to a constructor
		try {
			new Period((Time)null,(Duration)null);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period((Time)null,(Time)null);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period((Duration)null,(Time)null);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period(new Time(),(Duration)null);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period(new Time(),(Time)null);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period(Duration.HOUR,(Time)null);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period((Time)null,Duration.HOUR);
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period((Time)null,new Time());
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		try {
			new Period((Duration)null,new Time());
		} catch (NullPointerException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
		
		Time t = new Time();
		try {
			new Period(t,t.subtract(Duration.MILLISECOND));
		} catch (ArithmeticException ex) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue("threw wrong exception: " + ex,ex instanceof IllegalArgumentException);
		}		
	}
}
