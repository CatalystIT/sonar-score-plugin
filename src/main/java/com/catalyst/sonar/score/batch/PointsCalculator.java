/**
 * 
 */
package com.catalyst.sonar.score.batch;

import org.sonar.api.BatchExtension;
import com.catalyst.sonar.score.batch.points.MetricBrackets;

/**
 * @author JDunn
 *
 */
public class PointsCalculator implements BatchExtension {
	
	public static final double PERCENT = 100;
	public static final double MAGNIFY_PACKAGE_TANGLE = 100;
	public static final double POINTS_FLOOR = 0;
	
	public static final MetricBrackets PACKAGE_BRACKET = new MetricBrackets("First 3 count 1, next 12 count .5, next 30 count .1");
	public static final MetricBrackets CLASS_BRACKET = new MetricBrackets("First 3 count 1, next 2 count .5, next 10 count .2");
	public static final MetricBrackets NCLOC_BRACKET = new MetricBrackets("First 30 count 3, next 30 count 2, next 30 count 1");
	
	/**
	 * Calculates the base points from the number of packages, classes, and lines.
	 * @param packages
	 * @param classes
	 * @param ncloc
	 * @return
	 */
	public static double calculateBasePoints(double packages, double classes, double ncloc) {
		return PACKAGE_BRACKET.metricFactor(packages)
				* CLASS_BRACKET.metricFactor(classes)
				* NCLOC_BRACKET.metricFactor(ncloc);
	}
	
	/**
	 * Retrieves all the necessary code metrics in order to calculate SCORE's
	 * points metric
	 * @param packages
	 * @param classes
	 * @param ncloc
	 * @param rulesCompliance
	 * @param docAPI
	 * @param coverage
	 * @param packageTangle
	 * @return
	 */
	public static double calculateTotalPoints(double packages, double classes, double ncloc,
			double rulesCompliance, double docAPI, double coverage, double packageTangle) {
		// SCORE's points algorithm
		double value = Math.round(
				(
						calculateBasePoints(packages, classes, ncloc) 
						* (rulesCompliance / PERCENT)
						* (docAPI / PERCENT)
						* (coverage / PERCENT)
				)
				- (packageTangle * MAGNIFY_PACKAGE_TANGLE)
			);

		// Preventing negative points. Points cannot go below zero.
		if (value < POINTS_FLOOR) {
			value = POINTS_FLOOR;
		}
		return value;
	}
	
}
