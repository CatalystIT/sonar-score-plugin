/**
 * 
 */
package com.catalyst.sonar.score.batch.trophies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.api.BatchExtension;

/**
 * @author JDunn
 *
 */
public class CriteriaParser implements BatchExtension {
	
	public static final String SPLITTER = ";";
	public static final Pattern DECIMAL = Pattern.compile("([0-9]\\d*(\\.\\d+)?|\\.\\d+?)");
	public static final Pattern METRIC = Pattern.compile("\\w+.");
	public static final String DAYS_REGEX = "\\d+";
	public static final String PERIOD_REGEX = "[dDwW]";
	public static final Pattern DAYS = Pattern.compile(DAYS_REGEX);
	public static final Pattern PERIOD = Pattern.compile(PERIOD_REGEX);
	public static final Pattern TIME = Pattern.compile(DAYS_REGEX + PERIOD_REGEX);
	protected static final int metricIndex = 0;
	protected static final int amountIndex = 1;
	protected static final int timeIndex = 2;
	protected static final int components = 3;
	protected static final int day = 1;
	protected static final int week = 7;
	
	private final String[] criteriaStrings;
	private final Matcher metricMatcher;
	private final Matcher decimalMatcher;
	private final Matcher daysMatcher;
	private final Matcher periodMatcher;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls this("").
	 */
	public CriteriaParser() {
		this(null);
	}
	
	
	/**
	 * Constructs a CriteriaParser, setting the criteriaString
	 * to equal the String argument.
	 * @param metricBracketsString
	 */
	public CriteriaParser(String criteriaString) {
		String[] criteriaStrings = criteriaString.split(SPLITTER);
		if(criteriaStrings.length != components || !hasValidFormat()) {
			throw new IllegalArgumentException("The format of " + criteriaString + " is invalid");
		}
		this.metricMatcher = DECIMAL.matcher(criteriaStrings[metricIndex]);
		this.decimalMatcher = DECIMAL.matcher(criteriaStrings[amountIndex]);
		this.daysMatcher = DECIMAL.matcher(criteriaStrings[timeIndex]);
		this.periodMatcher = DECIMAL.matcher(criteriaStrings[timeIndex]);
		this.criteriaStrings = criteriaStrings;
	}
	
	/**
	 * Parses and returns the number of days from the criteriaString.
	 * @return
	 */
	public int getDays() {
		String days = daysMatcher.reset().group(1);
		String period = periodMatcher.reset().group(1);
		int daysInPeriod = daysInPeriod(period);
		return Integer.parseInt(days) * daysInPeriod;
		
	}
	
	/**
	 * Parses and returns the required amount for the metric from the criteriaString.
	 * @return
	 */
	public double getAmount() {
		return Double.parseDouble(decimalMatcher.reset().group(1));
	}
	
	/**
	 * Parses and returns the name of the metric from the criteriaString.
	 * @return
	 */
	public String getMetric() {
		return metricMatcher.reset().group(1);
	}
	
	private final boolean hasValidFormat() {
		return hasValidMetric() && hasValidAmount() && hasValidDays();
	}
	
	private final boolean hasValidMetric() {
		return Pattern.matches(criteriaStrings[metricIndex], METRIC.pattern());
	}
	
	private final boolean hasValidAmount() {
		return Pattern.matches(criteriaStrings[amountIndex], DECIMAL.pattern());
	}
	
	private boolean hasValidDays() {
		return Pattern.matches(criteriaStrings[timeIndex], DAYS.pattern());
	}
	
	private int daysInPeriod(String period) {
		int days = 1;
		if (period.matches("[wW]")) {
			days = week;
		}
		return days;
	}
	
	

}
