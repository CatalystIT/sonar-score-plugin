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
	
	private double lines;
	private double classes;
	private double rulesCompliance;
	private double docAPI;
	private double coverage;
	private double packageTangle;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		lines = 0;
		classes = 1;
		rulesCompliance = 98;
		docAPI = 78;
		coverage = 63;
		packageTangle = 0;
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateBasePoints(double, double)};
	 * lines per class <= BRACKET1.
	 */
	@Test
	public void testCalculateBasePoints1() {
		for(lines = 0; lines <= BRACKET1; lines++) {			
			assertEquals(Math.round(lines*FACTOR1), Math.round(calculateBasePoints(lines, classes)));
		}
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateBasePoints(double, double)};
	 * BRACKET1 < lines per class <= BRACKET2.
	 */
	@Test
	public void testCalculateBasePoints2() {
		long basePoints = 0;
		for(lines = BRACKET1 + 1; lines <= BRACKET2; lines++) {
			basePoints = Math.round(classes*BRACKET1*FACTOR1);
			basePoints += (lines-FACTOR1)*FACTOR2;
			assertEquals(basePoints, Math.round(calculateBasePoints(lines, classes)));
		}
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateBasePoints(double, double)};
	 * BRACKET2 < lines per class <= BRACKET3.
	 */
	@Test
	public void testCalculateBasePoints3() {
		long basePoints = 0;
		for(lines = BRACKET2 + 1; lines <= BRACKET3; lines++) {
			basePoints = Math.round(classes*BRACKET1*FACTOR1);
			basePoints += classes*(BRACKET2-BRACKET1)*FACTOR2;
			basePoints += classes*(lines-BRACKET2)*FACTOR3;
			assertEquals(basePoints, Math.round(calculateBasePoints(lines, classes)));
		}
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateBasePoints(double, double)};
	 * BRACKET3 < lines per class <= BRACKET4.
	 */
	@Test
	public void testCalculateBasePoints4() {
		long basePoints = 0;
		lines = BRACKET3 + 1;
		basePoints = Math.round(classes*BRACKET1*FACTOR1);
		basePoints += classes*(BRACKET2-BRACKET1)*FACTOR2;
		basePoints += classes*(BRACKET3-BRACKET2)*FACTOR3;
		basePoints += classes*(lines-BRACKET3)*FACTOR4;
		assertEquals(basePoints, Math.round(calculateBasePoints(lines, classes)));
		
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateTotalPoints(double, double, double, double, double, double)}.
	 */
	@Test
	public void testCalculateTotalPoints() {
		lines = 3000;
		classes = 20;
		packageTangle = 0;
		long totalPoints = Math.round(
				(
						calculateBasePoints(lines, classes) 
						* (rulesCompliance / PERCENT)
						* (docAPI / PERCENT)
						* (coverage / PERCENT)
				)
				- (packageTangle * MAGNIFY_PACKAGE_TANGLE)
			);
		assertEquals(totalPoints,
				Math.round(calculateTotalPoints(
						classes, lines, rulesCompliance,
						docAPI, coverage, packageTangle)));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.PointsCalculator#calculateTotalPoints(double, double, double, double, double, double)};
	 * total score would be negative if not caught and set to zero.
	 */
	@Test
	public void testCalculateTotalPointsNegativeTurnsToZero() {
		lines = 3000;
		classes = 20;
		packageTangle = 100;//should produce negative score
		assertEquals(0,
				Math.round(calculateTotalPoints(
						classes, lines, rulesCompliance,
						docAPI, coverage, packageTangle)));
	}

}
