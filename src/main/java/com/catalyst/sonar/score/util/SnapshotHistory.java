package com.catalyst.sonar.score.util;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author mwomack
 *
 */
public class SnapshotHistory {
	
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
