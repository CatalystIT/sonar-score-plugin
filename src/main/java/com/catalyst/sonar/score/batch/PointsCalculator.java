/**
 * 
 */
package com.catalyst.sonar.score.batch;

import org.sonar.api.BatchExtension;

/**
 * @author JDunn
 *
 */
public class PointsCalculator implements BatchExtension {
	
	public static final int LOWEST_POINTS = 0;
	public static final double PERCENT = 100.0;
	public static final double MAGNIFY_PACKAGE_TANGLE = 100.0;
	
	// The following constants give weight to the lines of code in each class in brackets.
	public static final double FACTOR1 = 3;
	public static final double BRACKET1 = 30;
	public static final double FACTOR2 = 2;
	public static final double BRACKET2 = 60;
	public static final double FACTOR3 = 1;
	public static final double BRACKET3 = 90;
	public static final double FACTOR4 = 0;

	/**
	 * calculates the base points from the number of lines and classes.
	 * @param lines
	 * @param classes
	 * @return
	 */
	public static double calculateBasePoints(double lines, double classes) {
		double basePoints = 0;
		if (lines <= classes*BRACKET1) {
			//average lines per class 0 to 30
			basePoints = lines*FACTOR1;
		} else if (lines <= classes*BRACKET2) {
			//average lines per class 31 to 60
			basePoints = classes*BRACKET1*FACTOR1;
			basePoints += (lines-BRACKET1)*FACTOR2;
		} else if (lines <= classes*BRACKET3) {
			//average lines per class 61 to 90
			basePoints = classes*BRACKET1*FACTOR1;
			basePoints += classes*(BRACKET2-BRACKET1)*FACTOR2;
			basePoints += classes*(lines-BRACKET2)*FACTOR3;
		} else {
			//average lines per class > 90
			basePoints = classes*BRACKET1*FACTOR1;
			basePoints += classes*(BRACKET2-BRACKET1)*FACTOR2;
			basePoints += classes*(BRACKET3-BRACKET2)*FACTOR3;
			basePoints += classes*(lines-BRACKET3)*FACTOR4;
		}
		
		return basePoints;
	}
	
	/**
	 * Retrieves all the necessary code metrics in order to calculate SCORE's
	 * points metric
	 * @param classes
	 * @param lines
	 * @param rulesComplexity
	 * @param docAPI
	 * @param coverage
	 * @param packageTangle
	 * @return
	 */
	public static double calculateTotalPoints(double classes, double lines,
			double rulesComplexity, double docAPI, double coverage, double packageTangle) {
		// SCORE's points algorithm
		double value = Math.round(
				(
						calculateBasePoints(lines, classes) 
						* (rulesComplexity / PERCENT)
						* (docAPI / PERCENT)
						* (coverage / PERCENT)
				)
				- (packageTangle * MAGNIFY_PACKAGE_TANGLE)
			);

		// Preventing negative points. Points cannot go below zero.
		if (value < LOWEST_POINTS) {
			value = LOWEST_POINTS;
		}
		return value;
	}
	
}
