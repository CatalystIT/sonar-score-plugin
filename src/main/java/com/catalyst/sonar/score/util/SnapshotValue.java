package com.catalyst.sonar.score.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author mwomack
 * 
 */
public class SnapshotValue implements Comparable<SnapshotValue> {

	public static final int MILLIS = 1000;
	public static final int SECONDS = 60;
	public static final int MINUTES = 60;
	public static final int HOURS = 24;

	private Date buildDate;
	private BigDecimal measureValue;

	/**
	 * Creates a snapshot history entry: a snapshot measure value and build date
	 * 
	 * @param measureValue
	 *            - snapshot measure value of a given measure and project
	 * @param buildDate
	 *            -corresponding snapshot build date
	 */
	public SnapshotValue(BigDecimal measureValue, Date buildDate) {
		this.buildDate = buildDate;
		this.measureValue = measureValue;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((buildDate == null) ? 0 : buildDate.hashCode());
		result = prime * result
				+ ((measureValue == null) ? 0 : measureValue.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SnapshotValue)) {
			return false;
		}
		SnapshotValue other = (SnapshotValue) obj;
		if (buildDate == null) {
			if (other.buildDate != null) {
				return false;
			}
		} else if (!buildDate.equals(other.buildDate)) {
			return false;
		}
		if (measureValue == null) {
			if (other.measureValue != null) {
				return false;
			}
		} else if (!measureValue.equals(other.measureValue)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks to see if the build date is within the specified number of days
	 * ago.
	 * 
	 * @param days
	 * @return {@code true} if the buildDate is within the number of days ago,
	 *         {@code false} otherwise.
	 */
	public boolean isWithinDaysAgo(int days) {
		Date checkDate = new Date();
		checkDate.setTime(checkDate.getTime() - days * HOURS * MINUTES
				* SECONDS * MILLIS);
		return buildDate.compareTo(checkDate) > 0;
	}

	/**
	 * 
	 * @returns the snapshot build date
	 */
	public Date getBuildDate() {
		return buildDate;
	}

	/**
	 * Sets a snapshot build date
	 * 
	 * @param buildDate
	 */
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	/**
	 * 
	 * @returns a snapshot measure value for a given metric
	 */
	public BigDecimal getMeasureValue() {
		return measureValue;
	}

	/**
	 * Sets a snapshot measure value for a given metric
	 * 
	 * @param metricValue
	 */
	public void setMeasureValue(BigDecimal metricValue) {
		this.measureValue = metricValue;
	}

	/**
	 * Compares this Snapshot to another Snapshot based on the values of their buildDates.
	 * @see {@link Comparable#compareTo()}
	 */
	public int compareTo(SnapshotValue other) {
		return buildDate.compareTo(other.buildDate);
	}

	/**
	 * @see {@link Object#toString}
	 */
	@Override
	public String toString() {
		SimpleDateFormat formater = new SimpleDateFormat("E yyyy.MM.dd kk:mm");
		String dateString = formater.format(buildDate);
		return measureValue.floatValue() + " on " + dateString;
	}

}
