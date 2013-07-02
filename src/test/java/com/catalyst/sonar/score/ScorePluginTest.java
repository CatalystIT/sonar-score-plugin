/**
 * 
 */
package com.catalyst.sonar.score;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.Group;
import com.catalyst.sonar.score.api.Member;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;
import com.catalyst.sonar.score.api.SearchableHashSet;
import com.catalyst.sonar.score.api.TitleCup;
import com.catalyst.sonar.score.batch.PointsCalculator;
import com.catalyst.sonar.score.batch.PointsDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.batch.points.InvalidNumberOfDoublesException;
import com.catalyst.sonar.score.batch.points.MetricBrackets;
import com.catalyst.sonar.score.batch.points.MetricBracketsParser;
import com.catalyst.sonar.score.batch.trophies.AwardTrophies;
import com.catalyst.sonar.score.batch.trophies.Criteria;
import com.catalyst.sonar.score.batch.trophies.Trophy;
import com.catalyst.sonar.score.batch.trophies.TrophyAndCriteriaParser;
import com.catalyst.sonar.score.batch.trophies.TrophySet;
import com.catalyst.sonar.score.metrics.MetricsHelper;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.EnhancedListFilterWidget;
import com.catalyst.sonar.score.ui.ImageUploadPage;
import com.catalyst.sonar.score.ui.ProjectComparisonWidget;
import com.catalyst.sonar.score.ui.ScoreRubyWidget;
import com.catalyst.sonar.score.ui.TrophyPage;
import com.catalyst.sonar.score.ui.TrophyWidget;
import com.catalyst.sonar.score.util.DateUtility;
import com.catalyst.sonar.score.util.MeasuresHelper;
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
	@Test
	public void testGetExtensions() {
		assertEquals(scorePlugin.getExtensions(),
				Arrays.asList(
						ScoreMetrics.class,
						PointsDecorator.class,
						PointsCalculator.class,
						TrophiesDecorator.class,
						ScoreRubyWidget.class,
						EnhancedListFilterWidget.class,
						ProjectComparisonWidget.class,
						TrophyWidget.class,
						//ImageUploadPage.class,
						MetricBrackets.class,
						MetricBracketsParser.class,
						InvalidNumberOfDoublesException.class,
						Criteria.class,
						Trophy.class,						
						TrophyAndCriteriaParser.class,
						TrophySet.class,
						AwardTrophies.class, 
						MeasuresHelper.class, 
						MetricsHelper.class, 
						DateUtility.class, 
						SnapshotHistory.class, 
						TrophiesHelper.class,
						TrophyPage.class//,
//						AwardSet.class, Criterion.class, Group.class, Member.class,
//						ScoreProject.class, ScoreUser.class, SearchableHashSet.class,
//						TitleCup.class
						
						
						
		));
	}

}
