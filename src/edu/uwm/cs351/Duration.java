package edu.uwm.cs351;


/**
 * An amount of time, always positive.
 * To create a duration, scale an existing duration.
 */
public class Duration implements Comparable<Duration> {
	private final long extent; // this must remain private.  Do NOT add a getter!
	
	// this constructor must remain private:
	private Duration(long milliseconds) {
		extent = milliseconds;
	}
	
	// TODO: For all constants, have a line:
	// public static final Duration CONSTANT = new Duration(...);
	public static final Duration INSTANTANEOUS = new Duration(0);
	public static final Duration MILLISECOND = new Duration(1);
	public static final Duration SECOND = new Duration(1000);
	public static final Duration MINUTE = new Duration(60000);
	public static final Duration HOUR = new Duration(60 * 60 * 1000);
	public static final Duration DAY = new Duration(HOUR.extent * 24);
	public static final Duration YEAR = DAY.scale(365.25);

	
	// If you are overriding a method from a super class, always
	// annotate it "@Override" as here, overriding Object#equals(Object)
	@Override // implementation
	public boolean equals(Object x) {
		return false; // TODO 
	}
	
	@Override // implementation
	public int hashCode() {
		return 0; // TODO: Do something efficient.  Do NOT create a String.
	}
	
	// If you are overriding a method from an interface, then Java 5
	// says you CANNOT use Override, but anything later says you MAY.
	// Please always do unless you write a javadoc comment. 
	@Override // required
	public int compareTo(Duration other) {
		return 0; // TODO: Remember what compareTo is supposed to return.
	}
	
	@Override // implementation
	public String toString() {
		return null; // TODO
	}
	
	// Methods that are not standard or private must have a documentation comment:
	
	/**
	 * Add two durations together to get a larger duration.
	 * @param d duration being added to this, must not be null
	 * @return new duration equal to the combined duration of this and the argument.
	 * @throws NullPointerException if d is null
	 */
	public Duration add(Duration d) {
		return null; // TODO
	}
	
	// TODO: three other public methods: subtract, scale & divide
	// Don't forget to write documentation comments.
	
	public Duration subtract(Duration other) {
		return null; // TODO
	}
	
	public Duration scale(double amt) {
		return null; // TODO
	}
	
	public double divide(Duration d) {
		return 0.0; // TODO
	}
}
