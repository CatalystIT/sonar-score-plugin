package com.catalyst.sonar.score.api;

import org.sonar.api.measures.Metric;

/**
 * The <code>Criterion</code> class defines criteria for earning an award: A
 * <code>Metric</code>, a <code>double</code> for how good the
 * <code>Metric</code> must be, and an
 * <code>int<code> for the number of days the <code>Metric</code> must be that
 * good in order for the <code>Award</code> to be earned, and finally a
 * <code>Group</code> of members in case the <code>Award</code> is meant to only
 * apply to those members.
 * 
 */
public class Criterion implements ScoreEntity {

	public static final String ALL_MEMBERS = "ALL_MEMBERS";

	private Metric metric;
	private double amount;
	private int days;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls
	 * super().
	 */
	public Criterion() {
		super();
	}

	/**
	 * Constructor with standard parameters, sets the metric, required amount
	 * for the metric, and the number of days that the metric must be held at
	 * the required amount.
	 * 
	 * @param metric
	 * @param amount
	 * @param days
	 */
	public Criterion(Metric metric, double amount, int days) {
		this.metric = metric;
		this.amount = amount;
		this.days = days;
	}

	/**
	 * Overrides the equals method
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Criterion)) {
			return false;
		}
		if (!fieldsMatch((Criterion) obj)) {
			return false;
		}
		return true;
	}

	/**
	 * Called only by equals(Object o) to help reduce the cyclomatic complexity.
	 * 
	 * @param criterion
	 * @return
	 */
	private boolean fieldsMatch(Criterion criterion) {
		if (metric == null) {
			if (criterion.metric != null) {
				return false;
			}
		} else if (!metric.equals(criterion.metric)) {
			return false;
		}
		if (!(amount == criterion.amount)) {
			return false;
		}
		if (!(days == criterion.days)) {
			return false;
		}
		return true;
	}

	/**
	 * overrides the hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + days;
		result = prime * result + ((metric == null) ? 0 : metric.hashCode());
		long temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> (prime + 1)));
		return result;
	}

	/**
	 * @return the metric
	 */
	public Metric getMetric() {
		return metric;
	}

	/**
	 * @param metric
	 *            the metric to set
	 */
	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(int days) {
		this.days = days;
	}
}