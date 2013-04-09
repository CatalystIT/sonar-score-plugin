package com.catalyst.sonar.reference.batch;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

import com.catalyst.sonar.reference.ScoreMetrics;
import com.catalyst.sonar.reference.ScorePlugin;
public class ScoreSensor implements Sensor{
	
	private Settings settings;

	public boolean shouldExecuteOnProject(Project project) {
		
		return true;
	}

	public void analyse(Project project, SensorContext context) {
		Measure measure = new Measure(ScoreMetrics.SCORE, settings.getString(ScorePlugin.MY_PROPERTY));
		context.saveMeasure(measure);
	}

}
