/**
 * 
 */
package com.catalyst.sonar.score.batch;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.batch.PointsCalculator.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author JDunn
 *
 */
public class PointsCalculatorTest {
	
	private static final double packages = 10;
	private static final double classes = 100;
	private static final double ncloc = 3000;
	private static final double rulesCompliance = 80;
	private static final double docAPI = 80;
	private static final double coverage = 80;
	private static final double packageTangle = 0;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateBasePoints(double, double, double)};
	 */
	@Test
	public void testCalculateBasePoints() {
		double packageFactor = PACKAGE_BRACKET.metricFactor(packages);
		double classFactor = CLASS_BRACKET.metricFactor(classes);
		double nclocFactor = NCLOC_BRACKET.metricFactor(ncloc);
		Double expected = new Double(packageFactor*classFactor*nclocFactor);
		Double actual = new Double(calculateBasePoints(packages, classes, ncloc));
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateTotalPoints(double, double, double, double, double, double)}.
	 */
	@Test
	public void testCalculateTotalPoints() {
		Double expected = new Double(
				Math.round(
				(
						calculateBasePoints(packages, ncloc, classes) 
						* (rulesCompliance / PERCENT)
						* (docAPI / PERCENT)
						* (coverage / PERCENT)
				)
				- (packageTangle * MAGNIFY_PACKAGE_TANGLE)
			)
			);
		Double actual = new Double(calculateTotalPoints(packages,
				classes, ncloc, rulesCompliance,
				docAPI, coverage, packageTangle));
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateTotalPoints(double, double, double, double, double, double)};
	 * total score would be negative if not caught and set to zero.
	 */
	@Test
	public void testCalculateTotalPointsNegativeTurnsToZero() {
		//should produce negative score
		double packageTangleBad = 100;
		Double expected = new Double(0);
		Double actual = new Double(calculateTotalPoints(packages,
				classes, ncloc, rulesCompliance,
				docAPI, coverage, packageTangleBad));
		assertEquals(expected, actual);
	}
	
	/**
	 * tests the PointsCalculator class.
	 */
	@Test
	public void testPointsCalculator() {
		PointsCalculator testCalc = new PointsCalculator() {
			
		};
		assertTrue(testCalc instanceof PointsCalculator);
	}

}
