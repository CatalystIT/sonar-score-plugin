/**
 * 
 */
package com.catalyst.sonar.score.batch.points;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author James
 *
 */
public class MetricBracketsTest {
	
	public static final double[][] BRACKETS = {{5, 1}, {5, 0.5}, {10, 0.1}};
	public static final double[][] BRACKETS_ODD = {{1}, {5, 0.5}, {10, 0.1}};
	public static final String BRACKETS_STRING = "asdf5fdsa1d5.000jkasjkg0.5s10.0d.1";
	public static final String BRACKETS_STRING_ODD = "afdsa1d5.000jkasjkg0.5s10.0.1";
	public static final double TWENTY = 20;
	public static final double TWENTY_RESULT = 8.5;
	private MetricBrackets testBrackets;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testBrackets = new MetricBrackets(BRACKETS_STRING);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#MetricBrackets(double[][])}.
	 */
	@Test
	public void testMetricBracketsDoubleArrayArray() {
		testBrackets = new MetricBrackets(BRACKETS);
		assertEquals(Arrays.deepToString(BRACKETS), testBrackets.toString());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#MetricBrackets(double[][])}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMetricBracketsDoubleArrayArrayThrowsIllegalArgumentException() {
		testBrackets = new MetricBrackets(BRACKETS_ODD);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#MetricBrackets(java.lang.String)}.
	 */
	@Test
	public void testMetricBracketsString() {
		assertEquals(Arrays.deepToString(BRACKETS), testBrackets.toString());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#MetricBrackets(java.lang.String)}.
	 */
	@Test(expected=InvalidNumberOfDoublesException.class)
	public void testMetricBracketsStringThrowsInvalidNumberOfDoublesException() {
		testBrackets = new MetricBrackets(BRACKETS_STRING_ODD);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#metricFactor(double)}.
	 */
	@Test
	public void testMetricFactor() {
		assertEquals(new Double(TWENTY_RESULT), new Double(testBrackets.metricFactor(TWENTY)));
		assertEquals(new Double(0), new Double(testBrackets.metricFactor(0)));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#smallerOf(double, double)}.
	 */
	@Test
	public void testSmallerOf() {
		assertEquals(new Double(1.1), new Double(MetricBrackets.smallerOf(1.1, 2.2)));
		assertEquals(new Double(3.3), new Double(MetricBrackets.smallerOf(4.4, 3.3)));
		assertEquals(new Double(5.5), new Double(MetricBrackets.smallerOf(-6.6, 5.5)));
		assertEquals(new Double(6.6), new Double(MetricBrackets.smallerOf(6.6, -5.5)));
		assertEquals(new Double(0), new Double(MetricBrackets.smallerOf(-1.1, -1.1)));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBrackets#arraysIncorrectLength(double[][])}.
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 */
	@Test
	public void testArraysIncorrectLength() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		assertEquals("", testBrackets.arraysIncorrectLength());
		Field field = MetricBrackets.class.getDeclaredField("metricBrackets");
		field.setAccessible(true);
		field.set(testBrackets, (Object) BRACKETS_ODD);
		assertNotEquals("", testBrackets.arraysIncorrectLength());
	}

}
