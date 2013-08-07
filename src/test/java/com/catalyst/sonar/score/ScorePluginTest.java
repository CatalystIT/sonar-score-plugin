/**
 * 
 */
package com.catalyst.sonar.score;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.catalyst.sonar.score.batch.PointsDecorator;
import com.catalyst.sonar.score.batch.SetupDecorator;
import com.catalyst.sonar.score.batch.TitleCupDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.*;

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
						// Metrics added by Score		
						ScoreMetrics.class,
						// Decorators
						SetupDecorator.class, PointsDecorator.class, TrophiesDecorator.class, TitleCupDecorator.class,
						// Widgets
						ScoreRubyWidget.class, ProjectComparisonWidget.class,
						TrophyWidget.class, TitleCupWidget.class, ProjectAwardsWidget.class, 
						EnhancedListFilterWidget.class,
						// Pages
						ImageUploadPage.class, TrophyPage.class, UserProfilePage.class						
						
		));
	}

}
