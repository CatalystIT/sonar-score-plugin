/**
 * 
 */
package com.catalyst.sonar.score.batch;

import org.sonar.api.BatchExtension;
import com.catalyst.sonar.score.batch.points.MetricBrackets;
import com.catalyst.sonar.score.util.CalculationComponent.CalculationComponentList;

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
	
	private final CalculationComponentList penalties;
	private final CalculationComponentList bonuses;
	
	/**
	 * Default Constructor, necessary for the SCORE plugin to work, calls this(0, 0).
	 */
	public PointsCalculator() {
		this(null, null);
	}
	
	/**
	 * Constructs a CalculationComponent, setting the fields accordingly.
	 * @param metricAmount
	 * @param factor
	 */
	public PointsCalculator(CalculationComponentList penalties, CalculationComponentList bonuses) {
		this.penalties = penalties;
		this.bonuses = bonuses;
	}

	/**
	 * Calculates the net Points gained or lost from the total by the penalty and the bonus.
	 * @return
	 */
	public double netBonusPenaltyPoints() {
		double penalty = (penalties != null) ? penalties.factoredTotal() : 0;
		double bonus = (bonuses != null) ? bonuses.factoredTotal() : 0;
		return bonus - penalty;
	}
	
	/**
	 * Calculates the base points from the number of packages, classes, and lines.
	 * @param packages
	 * @param classes
	 * @param ncloc
	 * @return
	 */
	public static double calculateBasePoints(double packages, double classes, double ncloc) {
		double packageFactor = PACKAGE_BRACKET.metricFactor(packages);
		double classFactor = CLASS_BRACKET.metricFactor(classes);
		double lineFactor = NCLOC_BRACKET.metricFactor(ncloc);
		packageFactor = (packageFactor != 0) ? packageFactor : 10;
		classFactor = (classFactor != 0) ? classFactor : 10;
		lineFactor = (lineFactor != 0) ? lineFactor : 10;
		return packageFactor * classFactor * lineFactor;
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
	public double calculateTotalPoints(double packages, double classes, double ncloc,
			double rulesCompliance, double docAPI, double coverage, double packageTangle) {
		// SCORE's points algorithm
		double value = Math.round(
				(
						calculateBasePoints(packages, classes, ncloc) 
						* (rulesCompliance / PERCENT)
						* (docAPI / PERCENT)
						* (coverage / PERCENT)
				)
				+ netBonusPenaltyPoints()
			);

		// Preventing negative points. Points cannot go below zero.
		if (value < POINTS_FLOOR) {
			value = POINTS_FLOOR;
		}
		return value;
	}
	
}
