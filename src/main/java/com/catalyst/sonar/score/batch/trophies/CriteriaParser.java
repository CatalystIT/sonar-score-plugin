/**
 * 
 */
package com.catalyst.sonar.score.batch.trophies;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.api.BatchExtension;

/**
 * @author JDunn
 *
 */
public class CriteriaParser implements BatchExtension {
	
	public static final String SPLITTER = ";";
	public static final Pattern DECIMAL = Pattern.compile("\\d*(\\.\\d+)?");
	public static final Pattern METRIC = Pattern.compile("[\\w+\\s*]+");
	public static final String DAYS_REGEX = "\\d+";
	public static final String PERIOD_REGEX = "[dDwW]";
	public static final Pattern DAYS = Pattern.compile(DAYS_REGEX);
	public static final Pattern PERIOD = Pattern.compile(PERIOD_REGEX);
	public static final Pattern TIME = Pattern.compile("2d}");//DAYS_REGEX + PERIOD_REGEX + ".*");
	protected static final int METRIC_INDEX = 0;
	protected static final int AMOUNT_INDEX = 1;
	protected static final int TIME_INDEX = 2;
	protected static final int COMPONENTS = 3;
	protected static final int DAY = 1;
	protected static final int WEEK = 7;
	
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
		this.criteriaStrings = criteriaString.split(SPLITTER);
		this.metricMatcher = DECIMAL.matcher(criteriaStrings[METRIC_INDEX]);
		this.decimalMatcher = DECIMAL.matcher(criteriaStrings[AMOUNT_INDEX]);
		this.daysMatcher = DECIMAL.matcher(criteriaStrings[TIME_INDEX]);
		this.periodMatcher = DECIMAL.matcher(criteriaStrings[TIME_INDEX]);
		//System.out.println(!hasValidAmount());
		if(criteriaStrings.length != COMPONENTS || !hasValidFormat()) {
			throw new IllegalArgumentException("The format of " + Arrays.toString(criteriaStrings) + " is invalid");
		}
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
	
	private boolean hasValidFormat() {
		return hasValidMetric() & hasValidAmount() & hasValidDays();
	}
	
	private boolean hasValidMetric() {
		System.out.println("\"" + criteriaStrings[METRIC_INDEX] + "\"\t\"" + METRIC.pattern() + "\"\t" + (getMetric() != null));//Pattern.matches(criteriaStrings[METRIC_INDEX], METRIC.pattern()));
		return (getMetric() != null);//Pattern.matches(criteriaStrings[METRIC_INDEX], METRIC.pattern());
	}
	
	private boolean hasValidAmount() {
		System.out.println("\"" + criteriaStrings[AMOUNT_INDEX] + "\"\t\"" + DECIMAL.pattern() + "\"\t" + Pattern.matches(criteriaStrings[AMOUNT_INDEX], DECIMAL.pattern()));
		return Pattern.matches(criteriaStrings[AMOUNT_INDEX], DECIMAL.pattern());
	}
	
	private boolean hasValidDays() {
		System.out.println("\"" + criteriaStrings[TIME_INDEX] + "\"\t\"" + TIME.pattern() + "\"\t" + Pattern.matches(criteriaStrings[TIME_INDEX], TIME.pattern()));
		return Pattern.matches(criteriaStrings[TIME_INDEX], TIME.pattern());
	}
	
	private int daysInPeriod(String period) {
		int days = 1;
		if (period.matches("[wW]")) {
			days = WEEK;
		}
		return days;
	}
	
	

}
