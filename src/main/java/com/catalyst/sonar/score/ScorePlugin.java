package com.catalyst.sonar.score;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import com.catalyst.sonar.score.batch.PointsCalculator;
import com.catalyst.sonar.score.batch.PointsDecorator;
import com.catalyst.sonar.score.batch.TitleCupDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.batch.points.*;
import com.catalyst.sonar.score.batch.trophies.AwardTrophies;
import com.catalyst.sonar.score.dao.MetricDao;
import com.catalyst.sonar.score.dao.SnapShotDao;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.EnhancedListFilterWidget;
import com.catalyst.sonar.score.ui.ImageUploadPage;
import com.catalyst.sonar.score.ui.ProjectAwardsWidget;
import com.catalyst.sonar.score.ui.ProjectComparisonWidget;
import com.catalyst.sonar.score.ui.ScoreRubyWidget;
import com.catalyst.sonar.score.ui.TrophyPage;
import com.catalyst.sonar.score.ui.TrophyWidget;
import com.catalyst.sonar.score.ui.TitleCupWidget;
import com.catalyst.sonar.score.util.DateUtility;
import com.catalyst.sonar.score.util.SnapshotValue;
import com.catalyst.sonar.score.util.TrophiesHelper;

/**
 * Creates a property in the database with the key, name, description and default value set
 * for the SCORE property 
 */
@Properties({
	  @Property(
	    key = ScorePlugin.PROJECT_GROUP,
	    name = "Project Group",
	    description = "Specify the project group of your project.",
	    project = true,	    
	    global = false),
	  @Property(
		key = ScorePlugin.PROJECT_TROPHY,
		name = "Project Trophy",
		description = "Specify the trophies earned by a project.",
		project = false,
		global = false,
		multiValues = true),
	  @Property(
		key = ScorePlugin.TITLECUP,
		name = "Title Cup",
		description = "Specify the Title Cup.",
		project = false,
		global = false,
		multiValues = true),
		 @Property(
		key = ScorePlugin.PROJECT_AWARDS,
		name = "Project Awards",
		description = "Specify awards for a project.",
		project = false,
		global = false,
		multiValues = true),
	  @Property(
	    key = ScorePlugin.TROPHY,
	    name = "Trophy",
	    description = " The trophies awarded need to be specified in the following format - TrophyName{MetricName;Amount;Time} e.g.GreatCode{Coverage;90%;10d}. To take more than one metric into account for a particular trophy, add another value with the same TrophyName. e.g.GreatCode{Violations;50;2w}." +
	    		"Time can be denoted using 'd' for days and 'w for weeks. While naming the metrics please follow the naming convention as seen on the widgets e.g. Coverage, Rules Compliance, Violations, Comments, Duplications, Points etc.",
	    project = false,
	    global = false,
	    multiValues = true)})

/**
 * This class is the entry point for the SCORE extension/plugin
 */
public class ScorePlugin extends SonarPlugin{
	public static final String PROJECT_GROUP = "sonar.score.projectGroup";
	public static final String PROJECT_TROPHY = "sonar.score.projectTrophy";
	public static final String TITLECUP = "sonar.score.TitleCup";
	public static final String TROPHY = "sonar.score.Trophy";
	public static final String PROJECT_AWARDS = "sonar.score.Project_Awards";
	
	/**
	 * returns a list of the various classes used to create the SCORE extension/plugin
	 */	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getExtensions() {
		
		return Arrays.asList(
		// Definition of Score's points metric		
		ScoreMetrics.class,
		//the decorator class (batch)
		PointsDecorator.class, PointsCalculator.class, TrophiesDecorator.class, TitleCupDecorator.class,
		// Score's ui/widgets
		ScoreRubyWidget.class, EnhancedListFilterWidget.class, ProjectComparisonWidget.class, TrophyWidget.class, TitleCupWidget.class,ImageUploadPage.class, ProjectAwardsWidget.class,
		
		MetricBrackets.class, MetricBracketsParser.class, InvalidNumberOfDoublesException.class, 
		
		AwardTrophies.class, SnapShotDao.class, MetricDao.class, DateUtility.class, SnapshotValue.class, TrophiesHelper.class, TrophyPage.class//,
		
		//API
//		AwardSet.class, Criterion.class, Group.class, Member.class,
//		ScoreProject.class, ScoreUser.class, SearchableHashSet.class,
//		TitleCup.class		
		
		);
		
	}
	
	

}
