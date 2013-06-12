package com.catalyst.sonar.score.batch;


public class Criteria {

	private String metric;
	private Double requiredAmt;
	private Integer days;
	
	public Criteria() {
		// no args Constructor
	}

	/**
	 * Constructor with params
	 * 
	 * @param metric
	 * @param requiredAmt
	 * @param days
//	 */
	public Criteria(String metric, double requiredAmt, int days) {
		this.setMetric(metric);
		this.setRequiredAmt(requiredAmt);
		this.setDays(days);
	}

	
	/**
	 * getter for required amt
	 * 
	 * @return
	 */
	public Double getRequiredAmt() {
		return requiredAmt;
	}

	/**
	 * setter for required amt
	 * 
	 * @param requiredAmt2
	 */
	public void setRequiredAmt(double requiredAmtTwo) {
		this.requiredAmt = requiredAmtTwo;
	}

	/**
	 * getter for days
	 * 
	 * @return
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * setter for days
	 * 
	 * @param days
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * getter for metric name
	 * 
	 * @return
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * setter for metric name
	 * 
	 * @param metric
	 */
	public void setMetric(String metric) {
		this.metric = metric;
	}

	/**
	 * Overrides the equals method
	 */
	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}	
		if(obj == null){
			return false;
		}
		if(!getClass().equals(obj.getClass())){
			return false;
		}
		Criteria criteria = (Criteria) obj;
		if(metric == null){
			if(criteria.metric != null){
				return false;
			}
			else if(!metric.equals(criteria.metric)){
				return false;
			}
		}
		if(requiredAmt == null){
			if(!(criteria.requiredAmt == null)){
				return false;
			}
			else if(!(requiredAmt == criteria.requiredAmt)){
				return false;
			}
		}
		if(days == null){
			if(!(criteria.days == null)){
				return false;
			}
			else if (!(days == criteria.days)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * overrides the hashCode
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int hash = 1;
		hash+= (prime * hash + ((metric == null) ? 0 : metric.hashCode()));
		hash+= (prime * hash + ((requiredAmt == null) ? 0 : requiredAmt.hashCode()));
		hash+= (prime * hash + ((days == null) ? 0 : days.hashCode()));
		return hash;
	}
}