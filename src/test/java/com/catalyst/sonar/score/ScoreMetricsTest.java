/**
 * 
 */
package com.catalyst.sonar.score;

import static org.junit.Assert.*;

import java.util.Arrays;

import com.catalyst.sonar.score.metrics.ScoreMetrics;

import org.junit.Before;
import org.junit.Test;

/**
 * @author JDunn
 *
 */
public class ScoreMetricsTest {
	
	private ScoreMetrics scoreMetrics;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		scoreMetrics = new ScoreMetrics();
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.metrics.ScoreMetrics#getMetrics()}.
	 */
	@Test
	public void testGetMetrics() {
		assertEquals(scoreMetrics.getMetrics(), Arrays.asList(ScoreMetrics.POINTS));
	}

}
