package com.catalyst.sonar.score.api;

import org.sonar.api.measures.Metric;

/**
 * The {@link Criterion} class defines criteria for earning an {@link Award}: a
 * {@link Metric}, a {@code double} for how good the {@link Metric} must be, and
 * an {@code int} for the number of days the {@code Metric} must be that good in
 * order for the {@code Award} to be earned.
 * 
 */
public class Criterion {

	public static final String ALL_MEMBERS = "ALL_MEMBERS";
	public static final int DAYS_IN_WEEK = 7;
	public static final String WEEK = "w";
	public static final String DAY = "d";

	private Metric metric;
	private double amount;
	private int days;
	private final Type type;

	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls
	 * super().
	 */
	public Criterion() {
		super();
		this.type = Criterion.Type.GOOD;
	}
	
	/**
	 * Single-Arg constructor, sets the metric, and sets the Type to BEST.
	 */
	public Criterion(Metric metric) {
		this.metric = metric;
		this.type = Criterion.Type.BEST;
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
		this.type = Criterion.Type.GOOD;
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
		if (amount != criterion.amount) {
			return false;
		}
		if (days != criterion.days) {
			return false;
		}
		if (type != criterion.type) {
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		String typeString = type.toString();
		String metricName = (metric != null) ? metric.getName() : null;
		String amountString = Double.toString(amount);
		String daysString = Integer.toString(days);
		return "[" + typeString + " " + metricName + " " + amountString + " " + daysString + "]";
	}
	
	public String toValueString() {
		return '{' + metric.getName() + ';' + amount + ';' + getDayString() + '}'; 
	}
		
	public String getDayString() {
		return (days % DAYS_IN_WEEK == 0) ? days/DAYS_IN_WEEK + WEEK : days + DAY;
	}

	public static enum Type {
		GOOD, BEST;
	}
}