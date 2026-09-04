package edu.uwm.cs351;

/**
 * A time period within historical time.
 */
public class Period {
	// TODO: Choose fields
	
	/**
	 * Construct a period given the start time and length.
	 * @param s start time, must not be null
	 * @param l length, must not be null
	 */
	public Period(Time s, Duration l) {
		// TODO
	}
	
	/**
	 * Construct a period given the start and end time
	 * @param from start time, must not be null
	 * @param to end time, must not be null or before the start time
	 */
	public Period(Time from, Time to) {
		// TODO
	}
	
	/**
	 * Construct a period given the length and end time.
	 * @param len length of the period, must not be null
	 * @param end end time of the period.
	 */
	public Period(Duration len, Time end) {
		// TODO
	}
	
	/**
	 * Return the start time of the period.
	 * @return beginning time
	 */
	public Time getStart() {
		return null; // TODO
	}
	
	/**
	 * Return the stop time of the period.
	 * @return end time
	 */
	public Time getStop() {
		return null; // TODO
	}
	
	/**
	 * Return the length of the period.
	 * @return the amount of time in this period
	 */
	public Duration getLength() {
		return null; // TODO
	}
	
	@Override // implementation
	public boolean equals(Object x) {
		return false; // TODO
	}
	
	@Override // implementation
	public int hashCode() {
		return 0; // TODO
	}
	
	@Override // implementation
	public String toString() {
		return null; // TODO
	}
	
	/**
	 * Return whether this period overlaps with the parameter.
	 * If one appointment starts where the other ends, they do not overlap.
	 * @param p period to compare to, must not be null
	 * @return whether this period overlaps the parameter
	 */
	public boolean overlap(Period p) {
		return false; // TODO
	}
}
