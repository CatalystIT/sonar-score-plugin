/**
 * 
 */
package com.catalyst.sonar.score.batch.points;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.regex.Matcher;

import org.junit.Before;
import org.junit.Test;

/**
 * @author James
 *
 */
public class MetricBracketsParserTest {
	private static final int BRACKETS_STRING_NUMBER_OF_DOUBLES = 4;
	private static final int BRACKETS_STRING_INDEX_OF_FIRST_MATCH = 5;
	private static final int BRACKETS_STRING_ODD_NUMBER_OF_DOUBLES = 3;
	private static final String BRACKETS_STRING = "hjsdf1als9.8asdfj7.4asdf3";
	private static final double[] DOUBLES = {1.0, 9.8, 7.4, 3.0};
	private static final double[][] BRACKETS = {{1.0, 9.8}, {7.4, 3.0}};
	private static final String BRACKETS_STRING_ODD = "hasdf1jkddsjf8.0asf9";
	
	MetricBracketsParser testParser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testParser = new MetricBracketsParser(BRACKETS_STRING);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#MetricBracketsParser(java.lang.String)}.
	 */
	@Test
	public void testMetricBracketsParser() {
		assertEquals(MetricBracketsParser.DECIMAL.matcher(BRACKETS_STRING).toString(), testParser.resetMatcher().toString());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#resetMatcher()}.
	 */
	@Test
	public void testResetMatcher() {
		Matcher matcher = testParser.resetMatcher();
		matcher.find();
		assertEquals(BRACKETS_STRING_INDEX_OF_FIRST_MATCH, matcher.start());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#resetMatcher()}.
	 */
	@Test(expected=IllegalStateException.class)
	public void testResetMatcherThrowsIllegalStateException() {
		testParser.resetMatcher().start();
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#numberOfDoubles()}.
	 */
	@Test
	public void testNumberOfDoubles() {
		assertEquals(BRACKETS_STRING_NUMBER_OF_DOUBLES, testParser.numberOfDoubles());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#parseMetricBrackets()}.
	 */
	@Test
	public void testParseMetricBrackets() {
		assertEquals(Arrays.deepToString(BRACKETS), Arrays.deepToString(testParser.parseMetricBrackets()));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#parseDoubles()}.
	 */
	@Test(expected=InvalidNumberOfDoublesException.class)
	public void testParseMetricBracketsThrowsInvalidNumberOfDoublesException() {
		testParser = new MetricBracketsParser(BRACKETS_STRING_ODD);
		testParser.parseMetricBrackets();
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#parseDoubles()}.
	 */
	@Test
	public void testParseDoubles() {
		assertEquals(Arrays.toString(testParser.parseDoubles()), Arrays.toString(DOUBLES));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.points.MetricBracketsParser#parseDoubles()}.
	 */
	@Test(expected=InvalidNumberOfDoublesException.class)
	public void testParseDoublesThrowsInvalidNumberOfDoublesException() {
		testParser = new MetricBracketsParser(BRACKETS_STRING_ODD);
		testParser.parseDoubles();
	}

}
