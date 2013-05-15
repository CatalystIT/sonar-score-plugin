package com.catalyst.sonar.score.batch;

import java.util.Arrays;
import java.util.Collection;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;

import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.google.common.collect.ImmutableList;
/**
 * The Trophies Decorator obtains, analyzes and calculates the information for the trophies metric
 * @author Team Build Meister
 *
 */
public class TrophiesDecorator implements Decorator {
	
    /**
     * Depends on various core metrics mentioned to be calculated before the
     * trophies measure can be calculated
     * @return Returns an immutable list of the metrics mentioned below
     */
	@DependsUpon
	public Collection<Metric> usedMetrics() {
		
		return ImmutableList.of(CoreMetrics.COVERAGE, CoreMetrics.NCLOC,
				CoreMetrics.VIOLATIONS_DENSITY,
				CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY,
				CoreMetrics.PACKAGE_TANGLE_INDEX, ScoreMetrics.POINTS);
	}

	/**
	 * The trophies metric is calculated after the other metrics on which it depends upon are calculated 
	 * @return trophies metric as a list
	 */
	@DependedUpon
	public Collection<Metric> generatedMetrics() {
		return Arrays.asList(ScoreMetrics.TROPHY_POINTS);
	}

	/**
	 * returns analysis type of the project
	 */
	public boolean shouldExecuteOnProject(Project project) {
		//TODO 
		// !Project.AnalysisType.STATIC.equals(project.getAnalysisType())||
		// !Project.AnalysisType.DYNAMIC.equals(project.getAnalysisType());
		return true;
	}
	/**
	 * 
	 * @param resource
	 * @param context
	 * @return checks if resource is a unit class
	 */
	public boolean shouldDecorateResource(final Resource resource,
			final DecoratorContext context) {
		// the resource is not a unit test class
		return !ResourceUtils.isUnitTestClass(resource);
	}

	/**
	 * Calculates the trophies metric
	 */
	public void decorate(final Resource resource, DecoratorContext context) {
		// if the resource is not a unit test class
		if (shouldDecorateResource(resource, context)) {
			double value = getEarnedTrophyPoints(context);
			context.saveMeasure(ScoreMetrics.TROPHY_POINTS, value);
		}

	}

	public double getEarnedTrophyPoints(final DecoratorContext context) {
		double trophyPoints = 0;
		double points = 0;
		double codeCoverage = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.COVERAGE), 0.0);
		double linesOfCode = MeasureUtils.getValue(context.getMeasure(CoreMetrics.NCLOC), 0.0);
		double violations = MeasureUtils.getValue(context.getMeasure(CoreMetrics.VIOLATIONS_DENSITY),0.0);
		double docApi = MeasureUtils.getValue(context.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY), 0.0);
		double packageTangle = MeasureUtils.getValue(context.getMeasure(CoreMetrics.PACKAGE_TANGLE_INDEX), 0.0);
		if(points == codeCoverage){
			trophyPoints = TrophiesCalculator
				.calculateConsistentTrophyPoints(codeCoverage);
		}
		else if(points == linesOfCode){
			trophyPoints = TrophiesCalculator.calculateConsistentTrophyPoints(linesOfCode);
		}
		else if (points == violations){
			trophyPoints = TrophiesCalculator.calculateConsistentTrophyPoints(violations);
		}
		else if (points == docApi){
			trophyPoints = TrophiesCalculator.calculateConsistentTrophyPoints(docApi);
		}
		return trophyPoints;
	}
}
