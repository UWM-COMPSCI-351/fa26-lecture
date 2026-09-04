package edu.uwm.cs351;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A point in time.
 */
public class Time implements Comparable<Time> {
	// TODO: data structure for Time (very simple)
	// The solution also has a private constructor,
	// which is very useful.
	private long point;
	
	/**
	 * Create a time for now.
	 */
	public Time() {
		// TODO (see homework)
	}
	
	/**
	 * Create a time according to the time parameter.
	 * @param c calendar object representing a time, must not be null
	 */
	public Time(Calendar c) {
		// TODO
	}
	
	// Override/implement methods standard for immutable classes.
	
	/**
	 * Return the difference between two time points.
	 * The order doesn't matter --- the difference is always a
	 * (positive) Duration.
	 * @param t time point to compute difference with
	 * @return duration between two time points.
	 */
	public Duration difference(Time t) {
		return null; // TODO 
	}

	/**
	 * Return the time point after a particular duration.
	 * If the point advances too far into the future,
	 * more than a hundred million years from now, this
	 * method may malfunction.
	 * @param d duration to advance, must not be null
	 * @return new time after given duration
	 */
	public Time add(Duration d) {
		return null; // TODO 
	}
	
	/**
	 * Return the time point before a particular duration.
	 * If a point regresses too far into the past,
	 * more than a hundred million years from now,
	 * this method may malfunction.
	 * @param d duration to regress, must not be null
	 * @return new time before this one by the given duration.
	 */
	public Time subtract(Duration d) {
		return null; // TODO 
	}
	
	/**
	 * Return the time as a (mutable) Calendar object.
	 * @return new Calendar object for time.
	 */
	public Calendar asCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(point);
		return cal;
	}

	@Override
	public int compareTo(Time o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
