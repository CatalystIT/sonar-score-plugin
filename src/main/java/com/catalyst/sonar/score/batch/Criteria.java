package com.catalyst.sonar.score.batch;

import org.sonar.api.measures.Metric;

public class Criteria {

	Metric metric;
	private double requiredAmt;
	private int days;
	
	public Criteria(){
		
	}
	
	public Criteria(Metric metric, double requiredAmt, int days){
		this.metric = metric;
		this.setRequiredAmt(requiredAmt);
		this.setDays(days);
	}

	public double getRequiredAmt() {
		return requiredAmt;
	}

	public void setRequiredAmt(double requiredAmt) {
		this.requiredAmt = requiredAmt;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
	
}
