package com.catalyst.sonar.score.batch.trophies;

/**
 * The Criteria class defines Criteria for earning a trophy:
 * A metric, a required amount for that metric, and a number of days
 * that the project in question must have held the metric at the required amount.
 * For example, to earn a particular trophy, 
 * rules compliance may need to have been held
 * at 90.0% for 5 days.
 *
 */
public class Criteria {

	private String metric;
	private Double requiredAmt;
	private Integer days;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls super().
	 */
	public Criteria() {
		super();
	}

	/**
	 * Constructor with params
	 * 
	 * @param metric
	 * @param requiredAmt
	 * @param days
//	 */
	public Criteria(String metric, double requiredAmt, int days) {
		this.metric = metric;
		this.requiredAmt = requiredAmt;
		this.days = days;
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
		if(metric == null  && criteria.metric != null){			
			return false;
		}
		else if(!metric.equals(criteria.metric)){
				return false;
		}
		if(requiredAmt == 0 && criteria.requiredAmt != 0){
			return false;
		}
		else if(!requiredAmt.equals(criteria.requiredAmt)){
				return false;
			
		}
		if(days == 0 && criteria.days != 0){
			return false;
		}
		else if (!(days == criteria.days)){
				return false;
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