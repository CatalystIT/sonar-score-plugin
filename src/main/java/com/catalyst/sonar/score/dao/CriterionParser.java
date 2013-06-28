/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;

import com.catalyst.sonar.score.api.Criterion;

import org.sonar.jpa.dao.MeasuresDao;

/**
 * Parses a {@link Criterion) from a {@code String}.
 * 
 * @author JDunn
 */
public class CriterionParser extends Parser<Criterion> {

	public static final int METRIC_INDEX = 0;
	public static final int AMOUNT_INDEX = 1;
	public static final int DAYS_INDEX = 2;

	private String[] criterionFields;
	private MeasuresDao metricDao;

	/**
	 * Constructs a {@link CriterionParser}, instantiating its session its
	 * {@link MeasuresDao}, and instantiating the criterionFields to
	 * {@code criterionFields.split(";")}.
	 * 
	 * @param session
	 * @param criterionString
	 */
	public CriterionParser(DatabaseSession session, String criterionString) {
		super(session, criterionString);
		this.metricDao = new MeasuresDao(session);
		this.criterionFields = criterionString.split(";");
		;
	}

	/**
	 * Parses out a {@link Criterion} from the {@code criterionFields}.
	 * @see {@link Parser#parse()}
	 */
	@Override
	public Criterion parse() {
		Metric metric = parseMetric();
		double amount = parseAmount();
		int days = parseDays();
		return new Criterion(metric, amount, days);
	}

	/**
	 * parses out a {@link Metric} from {@code criterionFields[METRIC_INDEX]}.
	 */
	public Metric parseMetric() {
		return metricDao.getMetric(criterionFields[METRIC_INDEX]);
	}

	/**
	 * parses out an amount from {@code criterionFields[AMOUNT_INDEX]}.
	 */
	public double parseAmount() {
		return Double.parseDouble(criterionFields[AMOUNT_INDEX]);
	}

	/**
	 * parses out an number of days from {@code criterionFields[DAYS_INDEX]}.
	 */
	public int parseDays() {
		String daysString = criterionFields[DAYS_INDEX];
		String numberOf = daysString.substring(0, daysString.length() - 1);
		String period = daysString.substring(daysString.length() - 1);
		int periodFactor = (period.equalsIgnoreCase("w")) ? 7 : 1;
		return Integer.parseInt(numberOf) * periodFactor;
	}

}
