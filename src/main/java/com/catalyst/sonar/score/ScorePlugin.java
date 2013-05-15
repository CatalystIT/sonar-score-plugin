package com.catalyst.sonar.score;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import com.catalyst.sonar.score.batch.PointsCalculator;
import com.catalyst.sonar.score.batch.PointsDecorator;
import com.catalyst.sonar.score.batch.TrophiesDecorator;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.EnhancedListFilter;
import com.catalyst.sonar.score.ui.ImageUploadPage;
import com.catalyst.sonar.score.ui.ProjectComparisonWidget;
import com.catalyst.sonar.score.ui.ScoreRubyWidget;
import com.catalyst.sonar.score.ui.TrophyWidget;

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
		description = "Specify the number of trophies earned by a project.",
		project = true,
		global = false,
		multiValues = true),
	  @Property(
	    key = ScorePlugin.TROPHY,
	    name = "Trophy",
	    description = " Specify the number of trophies earned.",
	    project = false,
	    global = true,
	    multiValues = true)})

/**
 * This class is the entry point for the SCORE extension/plugin
 */
public class ScorePlugin extends SonarPlugin{
	public static final String PROJECT_GROUP = "sonar.score.projectGroup";
	public static final String PROJECT_TROPHY = "sonar.score.projectTrophy";
	public static final String TROPHY = "sonar.score.Trophy";
	/**
	 * returns a list of the various classes used to create the SCORE extension/plugin
	 */	
	@SuppressWarnings({ "unchecked"})
	public List getExtensions() {
		
		return Arrays.asList(
		// Definition of Score's points metric		
		ScoreMetrics.class,
		//the decorator class (batch)
		PointsDecorator.class, PointsCalculator.class, TrophiesDecorator.class,
		// Score's ui/widgets
		ScoreRubyWidget.class, EnhancedListFilter.class, ProjectComparisonWidget.class, TrophyWidget.class, ImageUploadPage.class
		
		
		);
		
	}
	
	

}
