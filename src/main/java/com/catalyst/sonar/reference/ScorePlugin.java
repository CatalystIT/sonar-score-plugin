package com.catalyst.sonar.reference;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import com.catalyst.sonar.reference.batch.ScoreDecorator;
import com.catalyst.sonar.reference.batch.ScoreSensor;
import com.catalyst.sonar.reference.ui.ScoreRubyWidget;

@Properties({
	  @Property(
	    key = ScorePlugin.MY_PROPERTY,
	    name = "Plugin Property",
	    description = "Testing a property",
	    defaultValue = "Score")})
public class ScorePlugin extends SonarPlugin{
	public static final String MY_PROPERTY = "sonar.score.myproperty";
	@SuppressWarnings({ "unchecked"})
	public List getExtensions() {
		
		return Arrays.asList(
		//Definitions		
		ScoreMetrics.class,
		//batch
		ScoreDecorator.class,
		
		ScoreSensor.class,
		//ui
		ScoreRubyWidget.class
		);
		
	}
	
	

}
