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
	 * Checks for meaningful equality based on the fields {@code metric},
	 * {@code amount}, {@code days}, and {@code type}, as well as if the object
	 * is an instance of {@code Criterion}.
	 * 
	 * @return true if this Criterion is meaningfully equal to the obj argument;
	 *         false otherwise.
	 * @see {@link Object#equals(Object)}
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
	 * In keeping with the overriding of {@link Criterion.equals(Object},
	 * {@code hashCode} is overridden using the same fields.
	 * 
	 * @return a hash code value for the {@code Criterion}, based on the fields
	 *         {@code metric}, {@code amount}, {@code days}, and {@code type}.
	 * @see {@link Object#hashCode()}
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
	 * @return the duration in days
	 */
	public int getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the duration in days to set
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

	/**
	 * Returns a String representation of the Criterion including the type, the
	 * metricName, the amount, and the duration in days.
	 * 
	 * @return a String representation of the Criterion.
	 */
	@Override
	public String toString() {
		String typeString = type.toString();
		String metricName = (metric != null) ? metric.getName() : null;
		String amountString = Double.toString(amount);
		String daysString = Integer.toString(days);
		return "[" + typeString + " " + metricName + " " + amountString + " "
				+ daysString + "]";
	}

	/**
	 * a special String representation of the Criterion, as the Criterion would
	 * appear in the {@code value} column in the Properties table in the
	 * database. Used by {@link CriterionSet#toString()}.
	 * 
	 * @return
	 */
	public String toValueString() {
		return '{' + metric.getName() + ';' + amount + ';' + getDayString()
				+ '}';
	}

	/**
	 * Returns a String representing the duration; if divisible by 7, the number
	 * of weeks followed by {@code w}, otherwise the number of days followed by
	 * {@code d}.
	 * 
	 * @return a String representation of the duration
	 */
	public String getDayString() {
		return (days % DAYS_IN_WEEK == 0) ? days / DAYS_IN_WEEK + WEEK : days
				+ DAY;
	}

	/**
	 * The {@code enum Criterion.Type} represents the types of {@code Criterion}
	 * : {@link Type.GOOD}, and {@link Type.BEST}.
	 * 
	 * @author JDunn
	 * 
	 */
	public static enum Type {
		
		/**
		 * GOOD represents a {@link Criterion} where, to earn an {@link Award},
		 * a Project must have a reading for the metric at least as good as the amount.
		 */
		GOOD,
		
		/**
		 * BEST represents a {@link Criterion} where, to earn an {@link Award},
		 * a Project must have a better reading for the metric than any other Project.
		 */
		BEST;
	}
}