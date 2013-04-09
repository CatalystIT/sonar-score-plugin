package com.catalyst.sonar.reference.batch;

import java.util.List;
import java.util.Arrays;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import com.catalyst.sonar.reference.ScoreMetrics;

public class ScoreDecorator implements Decorator{
	
	@DependsUpon
	public List<Metric> dependsUponMetrics(){
		return Arrays.asList(CoreMetrics.MAJOR_VIOLATIONS, CoreMetrics.MINOR_VIOLATIONS, CoreMetrics.INFO_VIOLATIONS);
	}
	
	@DependedUpon
	public Metric getMetrics(){
		return ScoreMetrics.SCORE;
	}
	
	public boolean shouldExecuteOnProject(Project project){
		return true;
	}

	
	public void decorate(Resource resource, DecoratorContext context) {
		calculateScorePoints(context);
		
	}
	
	private void calculateScorePoints(DecoratorContext context){
		//Measure measure = context.getMeasure(CoreMetrics.LINES);
		//double value = measure.getValue();
		Measure major = context.getMeasure(CoreMetrics.MAJOR_VIOLATIONS);
		Measure minor = context.getMeasure(CoreMetrics.MINOR_VIOLATIONS);
		Measure info = context.getMeasure(CoreMetrics.INFO_VIOLATIONS);
		double value = (major.getValue() + minor.getValue() + info.getValue());
		context.saveMeasure(ScoreMetrics.SCORE , value);		
	}
	
}