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
import com.catalyst.sonar.score.batch.TitleCupDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException;
import com.catalyst.sonar.score.batch.points.MetricBrackets;
import com.catalyst.sonar.score.batch.points.MetricBracketsParser;
import com.catalyst.sonar.score.batch.trophies.AwardTrophies;
import com.catalyst.sonar.score.dao.MetricDao;
import com.catalyst.sonar.score.dao.SnapShotDao;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.*;
import com.catalyst.sonar.score.util.DateUtility;
import com.catalyst.sonar.score.util.SnapshotHistory;
import com.catalyst.sonar.score.util.TrophiesHelper;

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
						TitleCupDecorator.class,
						ScoreRubyWidget.class,
						EnhancedListFilterWidget.class,
						ProjectComparisonWidget.class,
						TrophyWidget.class,
						TitleCupWidget.class,
						//ImageUploadPage.class,
						MetricBrackets.class,
						MetricBracketsParser.class,
						InvalidNumberOfDoublesException.class,
						AwardTrophies.class, 
						SnapShotDao.class, 
						MetricDao.class, 
						DateUtility.class, 
						SnapshotHistory.class, 
						TrophiesHelper.class,
						TrophyPage.class
						
						
						
		));
	}

}
