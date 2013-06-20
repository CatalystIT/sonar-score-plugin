/**
 * 
 */
package com.catalyst.sonar.score.batch.points;

import java.util.Arrays;

import org.sonar.api.BatchExtension;

/**
 * The MetricBrackets class encapsulates a two-dimensional double array
 * representing an array of brackets for weighting metric values.
 * An object of type MetricBrackets contains a single, private final field
 * whose type is double[][].  There is no getter for this field.
 * Each internal double[] represents a bracket, whose length is assumed and enforced to be 2.
 * The first index (0) represents the amount of a metrics value that will be weighted;
 * the second index (1) represents how much that amount will be weighted.
 *
 */
public class MetricBrackets implements BatchExtension {
	
	public static final double[][] EMPTY_BRACKETS = {};
	
	public static final int NUMBER_IN_BRACKET = 0;
	public static final int WEIGHT_OF_BRACKET = 1;
	public static final int EXPECTED_LENGTH = 2;
	public static final String NOT_TWO_IN_LENGTH_MESSAGE = "\nThe following internal doubles[] do not have a length of exactly two: ";
	
	private final double[][] metricBrackets;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work,
	 * calls this(double[][] metricBrackets) and passes in EMPTY_BRACKETS.
	 */
	public MetricBrackets() {
		this(EMPTY_BRACKETS);
	}

	/**
	 * Constructs a new MetricBrackets and sets the MetricBrackets field to the double[][] argument. 
	 * If the double[][] argument is null, or if any of its internal double[]s do not have a length
	 * of exactly two, an IllegalArgumentException is thrown.
	 * @param metricBrackets
	 */
	public MetricBrackets(double[][] metricBrackets) {
		if(metricBrackets == null) {
			throw new IllegalArgumentException("The double[][] argument must not be null.");
		}
		this.metricBrackets = Arrays.copyOf(metricBrackets, metricBrackets.length);
		String arraysNotTwoInLength = arraysIncorrectLength();
		if(!arraysNotTwoInLength.isEmpty()) {
			throw new IllegalArgumentException(NOT_TWO_IN_LENGTH_MESSAGE + arraysNotTwoInLength);
		}
	}
	/**
	 * Constructs a new MetricBrackets and sets the MetricBrackets field
	 * by parsing the String argument with a MetricBracketsParser.
	 * If any internal double[] is not exactly two in length, an InvalidNumberOfDoublesException is thrown.
	 * @param metricBracketsString
	 */
	public MetricBrackets(String metricBracketsString) {
		this.metricBrackets = new MetricBracketsParser(metricBracketsString).parseMetricBrackets();
	}
	
	/**
	 * takes a double representing a value for a metric, and calculates a weight for that value from
	 * the values in the metricBrackets.
	 * a metric factor is then calculated from the metricAmount and the values in the metricBrackets.
	 * @param metricAmount
	 * @return
	 */
	public double metricFactor(double metricAmount) {
		double metricFactor = 0;
		double metricAmountLeft = metricAmount;
		for(int bracket = 0; bracket < metricBrackets.length; bracket++) {
			if(metricAmountLeft > 0) {
				metricFactor += (
					smallerOf(metricAmountLeft, metricBrackets[bracket][NUMBER_IN_BRACKET]) * metricBrackets[bracket][WEIGHT_OF_BRACKET]
							);
			}
			metricAmountLeft -= metricBrackets[bracket][NUMBER_IN_BRACKET];
		}
		return metricFactor;
	}
	
	/**
	 * Returns the smaller of two numbers, provided the number is greater than or equal to 0.
	 * If one number is negative and one is positive, the postive number is returned.
	 * If both numbers are negative, 0 is returned.
	 * @param one
	 * @param two
	 * @return
	 */
	protected static double smallerOf(double one, double two) {
		if(one >= 0 && (one < two || two < 0)) {
			return one;
		} else if (two >= 0) {
			return two;
		} else {
			return 0;
		}
	}
	
	/**
	 * Takes the double[][] metricBrackets and concatenates a String displaying each internal array without a length of exactly 2.
	 * If there are no such internal arrays, an empty String is returned.
	 * @return
	 */
	protected final String arraysIncorrectLength() {
		String arraysNotTwoInLength = "";
		for (int index = 0; index < metricBrackets.length; index++) {
			if(metricBrackets[index].length != EXPECTED_LENGTH) {
				arraysNotTwoInLength += "\n" + Arrays.toString(metricBrackets[index]) + " (at index " + index + "); ";
			}
		}
		if (!arraysNotTwoInLength.isEmpty()) {
			//Replaces "; " at the end of the string with "."
			arraysNotTwoInLength = arraysNotTwoInLength.substring(0, arraysNotTwoInLength.lastIndexOf(';')) + '.';
		}
		return arraysNotTwoInLength;
	}
	
	/**
	 * Returns the String representation of the double[][] metricBrackets.
	 */
	@Override
	public String toString() {
		return Arrays.deepToString(metricBrackets);
	}

}
