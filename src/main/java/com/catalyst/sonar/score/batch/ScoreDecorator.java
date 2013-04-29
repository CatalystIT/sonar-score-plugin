package com.catalyst.sonar.score.batch;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.MeasureUtils;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import java.util.Arrays;
import java.util.Collection;
import com.google.common.collect.ImmutableList;
import org.sonar.api.batch.DependsUpon;
/**
 * The ScoreDecorator class is responsible for obtaining, analyzing and calculating information and 
 * various code metrics for the points metric
 * @author Team Build Meister
 *
 */
public class ScoreDecorator implements Decorator {
	public static final int LOWEST_POINTS = 0;
	public static final double PERCENT = 100.0;
	public static final double MAGNIFY_PACKAGE_TANGLE = 100.0;
	
	/**
	 * @DependsUpon: The points metric depends upon the non-commented lines of code, the rules compliance 
	 * (violations density), unit test coverage, documented API, and package tangle measures to be calculated 
	 * first before the points measure can be calculated.
	 * @returns an immutable list of the above mentioned code metrics
	 */
	@DependsUpon
	public Collection<Metric> usedMetrics(){
		return ImmutableList.of(CoreMetrics.NCLOC, CoreMetrics.VIOLATIONS_DENSITY, CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY,
				CoreMetrics.COVERAGE, CoreMetrics.PACKAGE_TANGLE_INDEX );   
	}
  
	/**
	 * @DependedUpon: The points metric is calculated after the other metrics
	 *                that is depends upon are calculated first.
	 * @returns a list of point metrics
	 */

	@DependedUpon
	public Collection<Metric> generatedMetrics() {
		return Arrays.asList(ScoreMetrics.POINTS);
	}

	/**
	 * returns whether or not analysis should be ran on a particular project
	 */
	public boolean shouldExecuteOnProject(Project project) {
		// TODO: look into this further. Do we need this method?
		// return
		// !Project.AnalysisType.STATIC.equals(project.getAnalysisType());
		return true;
	}

	/**
	 * @param resource
	 * @param context
	 * @returns whether or not a resource is a unit test class
	 */
	public boolean shouldDecorateResource(final Resource resource,
			final DecoratorContext context) {
		// if the resource is not a unit test class, then proceed with
		// decoration/analysis
		return !ResourceUtils.isUnitTestClass(resource);
	}

	/**
	 * Calculates the points metric and saves it to the database. First, all the
	 * necessary measures are retrieved in order to calculate the points metric
	 * for a particular project.
	 */
	public void decorate(final Resource resource, final DecoratorContext context) {
		/*
		 * if the resource to decorate is not a unit test class, then retrieve
		 * the various code metrics and calculate the points for a given
		 * resource/project
		 */

		if (shouldDecorateResource(resource, context)) {
			double value = getPointsValue(context);

			// save the the point's value to the database for the given
			// resource/project
			context.saveMeasure(ScoreMetrics.POINTS, value);
		}

	}

	/**
	 * Retrieves all the necessary code metrics in order to calculate SCORE's
	 * points metric
	 * 
	 * @param context
	 * @returns the points value
	 */
	public double getPointsValue(final DecoratorContext context) {
		double lines = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.NCLOC), 0.0);
		double rulesComplexity = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.VIOLATIONS_DENSITY), 0.0);
		double docAPI = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY),
				0.0);
		double coverage = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.COVERAGE), 0.0);
		double packageTangle = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.PACKAGE_TANGLE_INDEX), 0.0);

		// SCORE's points algorithm
		double value = Math.round((lines * (rulesComplexity / PERCENT)
				* (docAPI / PERCENT) * (coverage / PERCENT))
				- (packageTangle * MAGNIFY_PACKAGE_TANGLE));

		// Preventing negative points. Points cannot go below zero.
		if (value < LOWEST_POINTS) {
			value = LOWEST_POINTS;
		}
		return value;
	}

}
