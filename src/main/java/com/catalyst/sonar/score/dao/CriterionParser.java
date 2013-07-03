/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;

import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.metrics.MetricsHelper;

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

	private MetricsHelper metricDao;

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
		this.metricDao = new MetricsHelper(session);
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
		Criterion criterion;
		Metric metric = parseMetric();
		double amount = 0;
		int days = 0;
		try {
			amount = parseAmount();
			days = parseDays();
			criterion = new Criterion(metric, amount, days);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("\t\t\tCREATEING A \"BEST\" CRITERION");
			criterion = new Criterion(metric);
		}
		System.out.println("\t\t\tCRITERION = " + criterion);
		return criterion;
	}

	/**
	 * Parses out a {@link Metric} from the criterionString.
	 */
	public Metric parseMetric() {
		System.out.println("\t\t\tPARSING METRIC");
		String metricName = get(METRIC_INDEX);
		System.out.println("\t\t\tMETRIC NAME = " + metricName);
		Metric metric = metricDao.findMetricByName(metricName);
		System.out.println("\t\t\tRETURNING = " + metric);
		return metric;
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
