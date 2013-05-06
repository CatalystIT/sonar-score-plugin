package com.catalyst.sonar.score;

import java.util.Arrays;
import java.util.List;
import org.sonar.api.Extension;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import com.catalyst.sonar.score.batch.PointsCalculator;
import com.catalyst.sonar.score.batch.ScoreDecorator;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.catalyst.sonar.score.ui.ScoreRubyWidget;
import com.catalyst.sonar.score.ui.EnhancedListFilter;
import com.catalyst.sonar.score.ui.ProjectComparisonWidget;

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
	    global = false)})

/**
 * This class is the entry point for the SCORE extension/plugin
 */
public class ScorePlugin extends SonarPlugin{
	public static final String PROJECT_GROUP = "sonar.score.projectGroup";
	/**
	 * returns a list of the various classes used to create the SCORE extension/plugin
	 */	
	@SuppressWarnings({ "unchecked"})
	public List<Class<? extends Extension>> getExtensions() {
		
		return Arrays.asList(
		/*
		 * Definition of Score's points metric		
		 */
		ScoreMetrics.class,
		/*
		 * the decorator class (batch)
		 */
		ScoreDecorator.class, PointsCalculator.class,
		
		/*
		 * Score's ui/widgets
		 */
		ScoreRubyWidget.class, EnhancedListFilter.class, ProjectComparisonWidget.class
		
		
		);
		
	}
	
	

}
