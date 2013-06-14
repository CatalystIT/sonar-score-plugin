/**
 * 
 */
package com.catalyst.sonar.score.batch.points;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.api.BatchExtension;

/**
 * The MetricBracketsParser class contains a String metricBracketsString,
 * and a methods to parse the metricBracketsString into a two-dimensional double array
 * representing an array of metricBrackets, which in turn can be encapsulated
 * by an instance of {@link com.catalyst.sonar.score.batch.points.MetricBrackets}.
 *
 */
public class MetricBracketsParser implements BatchExtension {
	
	public static final int DIVISOR = MetricBrackets.EXPECTED_LENGTH;
	public static final Pattern DECIMAL = Pattern.compile("[0-9]\\d*(\\.\\d+)?|\\.\\d+?");
	
	private final String metricBracketsString;
	private Matcher matcher;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work,
	 * calls this(String metricBracketsString) and passes in an empty String.
	 */
	public MetricBracketsParser() {
		this("");
	}
	
	/**
	 * Constructs a MetricBracketsParser, setting the metricBracketsString
	 * to equal the String argument, and instantiates the matcher
	 * from the Pattern DECIMAL with the metricBracketsString.
	 * @param metricBracketsString
	 */
	public MetricBracketsParser(String metricBracketsString) {
		this.metricBracketsString = metricBracketsString;
		matcher = DECIMAL.matcher(this.metricBracketsString);
	}
	
	/**
	 * Resets the matcher.
	 * @return
	 */
	protected Matcher resetMatcher() {
		return matcher.reset();
	}
	
	/**
	 * Using the matcher, numberOfDoubles() calculates the numberOfDoubles
	 * that can be parsed out of the metricBracketsString, resetting the
	 * matcher before and after to ensure continual data integrity.
	 * @return
	 */
	protected int numberOfDoubles() {
		resetMatcher();
		int numberOfDoubles = 0;
		while(matcher.find()) {
			numberOfDoubles++;
		}
		resetMatcher();
		return numberOfDoubles;
	}
	
	/**
	 * Takes the single-dimension array returned from parseDoubles(),
	 * and condenses it into a two-dimensional Array, which in turn can be encapsulated
	 * by an instance of {@link com.catalyst.sonar.score.batch.points.MetricBrackets}. 
	 * @return
	 */
	public double[][] parseMetricBrackets() {
		double[] doubles = parseDoubles();
		double[][] metricBrackets = new double[doubles.length / DIVISOR][DIVISOR];
		for(int doublesIndex = 0; doublesIndex < doubles.length; doublesIndex+=DIVISOR) {
			double[] bracket = new double[DIVISOR];
			System.arraycopy(doubles, doublesIndex, bracket, 0, DIVISOR);
			metricBrackets[doublesIndex / DIVISOR] = bracket;
		}
		return metricBrackets;
	}
	
	/**
	 * Parses the metricBraketsString into a single-dimension double array.
	 * If the number of doubles in the array is odd, an InvalidNumberOfDoublesException is thrown.
	 * @return
	 */
	public double[] parseDoubles() {
		int numberOfDoubles = numberOfDoubles();
		if(numberOfDoubles % DIVISOR != 0) {
			throw new InvalidNumberOfDoublesException(metricBracketsString, numberOfDoubles);
		}
		double[] doubles = new double[numberOfDoubles];
		int index = 0;
		while(matcher.find()) {
			String match = metricBracketsString.substring(matcher.start(), matcher.end());
			doubles[index] = Double.parseDouble(match);
			index++;
		}
		return doubles;
	}

}
