/**
 * 
 */
package com.catalyst.sonar.score;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.catalyst.sonar.score.batch.PointsCalculator;
import com.catalyst.sonar.score.batch.PointsDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException;
import com.catalyst.sonar.score.batch.points.MetricBrackets;
import com.catalyst.sonar.score.batch.points.MetricBracketsParser;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.EnhancedListFilter;
import com.catalyst.sonar.score.ui.ImageUploadPage;
import com.catalyst.sonar.score.ui.ProjectComparisonWidget;
import com.catalyst.sonar.score.ui.ScoreRubyWidget;
import com.catalyst.sonar.score.ui.TrophyWidget;

/**
 * @author JDunn
 *
 */
public class ScorePluginTest {
	
	private ScorePlugin scorePlugin;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		scorePlugin = new ScorePlugin();
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.ScorePlugin#getExtensions()}.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetExtensions() {
		assertEquals(scorePlugin.getExtensions(),
				Arrays.asList(
						ScoreMetrics.class,
						PointsDecorator.class,
						PointsCalculator.class,
						TrophiesDecorator.class,
						ScoreRubyWidget.class,
						EnhancedListFilter.class,
						ProjectComparisonWidget.class,
						TrophyWidget.class,
						ImageUploadPage.class,
						MetricBrackets.class,
						MetricBracketsParser.class,
						InvalidNumberOfDoublesException.class
						
						
		));
	}

}
