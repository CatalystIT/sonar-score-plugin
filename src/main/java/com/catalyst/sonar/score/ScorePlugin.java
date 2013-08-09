package com.catalyst.sonar.score;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;

import com.catalyst.sonar.score.batch.PointsDecorator;
import com.catalyst.sonar.score.batch.SetupDecorator;
import com.catalyst.sonar.score.batch.TitleCupDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.*;

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
		key = ScorePlugin.POINTS_DISABLED,
		name = "Points Disabled",
		description = "Disable Points being earned",
		project = true,
		global = true,
		type = PropertyType.BOOLEAN),
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
	public static final String POINTS_DISABLED = "sonar.score.PointsDisabled";
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
		// Metrics added by Score		
		ScoreMetrics.class,
		// Decorators
		SetupDecorator.class, PointsDecorator.class, TrophiesDecorator.class, TitleCupDecorator.class,
		// Widgets
		ScoreRubyWidget.class, ProjectComparisonWidget.class,
		TrophyWidget.class, TitleCupWidget.class, ProjectAwardsWidget.class, 
		EnhancedListFilterWidget.class,
		// Pages
		ImageUploadPage.class, TrophyPage.class, UserListPage.class, TeamListPage.class
		
	
		
		);
		
	}
	
	

}
