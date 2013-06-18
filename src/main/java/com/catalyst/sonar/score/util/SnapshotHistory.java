package com.catalyst.sonar.score.util;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author mwomack
 *
 */
public class SnapshotHistory {
	
	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
		if (!(obj instanceof SnapshotHistory)) {
			return false;
		}
		SnapshotHistory other = (SnapshotHistory) obj;
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
	private Date buildDate;
	private BigDecimal measureValue;
	
	/**
	 * Creates a snapshot history entry: a snapshot measure value and build date
	 * @param measureValue - snapshot measure value of a given measure and project
	 * @param buildDate -corresponding snapshot build date 
	 */
	public SnapshotHistory (BigDecimal measureValue, Date buildDate){
		this.buildDate = buildDate;
		this.measureValue = measureValue;
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
	 * @param metricValue
	 */
	public void setMeasureValue(BigDecimal metricValue) {
		this.measureValue = metricValue;
	}



}
