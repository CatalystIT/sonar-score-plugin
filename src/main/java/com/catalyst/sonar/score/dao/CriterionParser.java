/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;

import com.catalyst.sonar.score.api.Criterion;

import org.sonar.jpa.dao.MeasuresDao;

/**
 * Parses a {@link Criterion} from a {@code String}.
 * 
 * @author JDunn
 */
public class CriterionParser extends Parser<Criterion> {

	public static final int METRIC_INDEX = 0;
	public static final int AMOUNT_INDEX = 1;
	public static final int DAYS_INDEX = 2;
	public static final int DAYS_IN_WEEK = 7;

	private MeasuresDao metricDao;

	/**
	 * Constructs a {@link CriterionParser}, instantiating its session its
	 * {@link MeasuresDao}, and instantiating the {@code String[] fields} to
	 * {@code criterionFields.split(";")}.
	 * 
	 * @param session
	 * @param criterionString
	 */
	public CriterionParser(DatabaseSession session, String criterionString) {
		super(session, criterionString.split(";"));
		this.metricDao = new MeasuresDao(session);
	}

	/**
	 * Parses out a {@link Criterion} from the criterionString. If the
	 * criterionString does not have fields for amount and days, then only the
	 * metric is set, and the Criterion Type is set to BEST.
	 * 
	 * @see {@link Parser#parse()}
	 * @see {@link Criterion#Criterion(Metric)}
	 * @see {@link Criterion.Type}
	 */
	@Override
	public Criterion parse() {
		Metric metric = parseMetric();
		double amount = 0;
		int days = 0;
		try {
			amount = parseAmount();
			days = parseDays();
		} catch (IndexOutOfBoundsException e) {
			return new Criterion(metric);
		}
		return new Criterion(metric, amount, days);
	}

	/**
	 * Parses out a {@link Metric} from the criterionString.
	 */
	public Metric parseMetric() {
		return metricDao.getMetric(get(METRIC_INDEX));
	}

	/**
	 * Parses out an amount from the criterionString.
	 */
	public double parseAmount() {
		return Double.parseDouble(get(AMOUNT_INDEX));
	}

	/**
	 * Parses out a number of days from the criterionString.
	 */
	public int parseDays() {
		String daysString = get(DAYS_INDEX);
		String numberOf = daysString.replaceAll("\\D", "");
		String period = daysString.substring(daysString.length() - 1);
		int periodFactor = (period.equalsIgnoreCase("w")) ? DAYS_IN_WEEK : 1;
		return Integer.parseInt(numberOf) * periodFactor;
	}

}
