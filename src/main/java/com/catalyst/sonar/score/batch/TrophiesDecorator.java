package com.catalyst.sonar.score.batch;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import com.catalyst.sonar.score.metrics.ScoreMetrics;
import com.google.common.collect.ImmutableList;

import org.sonar.api.config.Settings;

/**
 * The Trophies Decorator obtains, analyzes and calculates the information for
 * the trophies metric
 * 
 * @author Team Build Meister
 * 
 */
public class TrophiesDecorator implements Decorator {

	private final DatabaseSession session;
	private Property newProperty;
	private Project project;
	private Settings settings;

	public TrophiesDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.session = session;
		this.project = project;
		this.settings = settings;

	}

	/**
	 * Depends on various core metrics mentioned to be calculated before the
	 * trophies measure can be calculated
	 * 
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
	 * The trophies metric is calculated after the other metrics on which it
	 * depends upon are calculated
	 * 
	 * @return trophies metric as a list
	 */
	@DependedUpon
	public Collection<Metric> generatedMetrics() {
		return Arrays.asList(ScoreMetrics.TROPHY_POINTS);
	}

	/**
	 * Persist the trophy property if it doesn't already exist for a particular
	 * project
	 */
	public void persistPropterty() {
		int projectId = project.getId();
		newProperty = new Property("sonar.score.TestTrophyPersistence",
				"You've earned a trophy!", projectId);
		if (!trophyPropertyExists("sonar.score.TestTrophyPersistence")) {
			session.save(newProperty);
		}

	}

	/**
	 * Check to make sure the trophy doesn't exist for a given project before
	 * persisting the trophy property.
	 * 
	 * @param propertyKey
	 * @returns true if the property exists for the given project
	 */
	public boolean trophyPropertyExists(String propertyKey) {
		boolean trophyExists = false;
		Map<String, String> allProperties = new HashMap<String, String>();
		allProperties = settings.getProperties();
		for (Map.Entry<String, String> entry : allProperties.entrySet()) {
			String key = entry.getKey();
			if (key.equals(propertyKey)) {
				trophyExists = true;
			}
		}

		return trophyExists;
	}

	/**
	 * returns analysis type of the project
	 */
	public boolean shouldExecuteOnProject(Project project) {
		
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
	 * Checks whether or not the resource is a project.
	 * 
	 * @param resource
	 * @returns true if the Resource is a project, otherwise returns false
	 */
	public boolean shouldCheckTrophyStatusForResource(final Resource resource) {
		return ResourceUtils.isProject(resource);
	}

	/**
	 * Decorates a project's resources. Retrieves the amount of points earned
	 * for a trophy and saves the value to the database. If the resource's scope
	 * of the project is "PRJ" then the trophy propert is persisted.
	 */
	public void decorate(final Resource resource, DecoratorContext context) {
		// if the resource is not a unit test class
		if (shouldDecorateResource(resource, context)) {
			double value = getEarnedTrophyPoints(context);
			context.saveMeasure(ScoreMetrics.TROPHY_POINTS, value);

		}
		// if the resource is a project then persist the property, otherwise do
		// not persist the trophy property
		if (shouldCheckTrophyStatusForResource(resource)) {
			persistPropterty();
		}
	}

	public double getEarnedTrophyPoints(final DecoratorContext context) {
		double trophyPoints = 0;
		double points = 0;
		double codeCoverage = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.COVERAGE), 0.0);
		double linesOfCode = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.NCLOC), 0.0);
		double violations = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.VIOLATIONS_DENSITY), 0.0);
		double docApi = MeasureUtils.getValue(
				context.getMeasure(CoreMetrics.PUBLIC_DOCUMENTED_API_DENSITY),
				0.0);
		if (points == codeCoverage) {
			trophyPoints = TrophiesCalculator
					.calculateConsistentTrophyPoints(codeCoverage);
		} else if (points == linesOfCode) {
			trophyPoints = TrophiesCalculator
					.calculateConsistentTrophyPoints(linesOfCode);
		} else if (points == violations) {
			trophyPoints = TrophiesCalculator
					.calculateConsistentTrophyPoints(violations);
		} else if (points == docApi) {
			trophyPoints = TrophiesCalculator
					.calculateConsistentTrophyPoints(docApi);
		}
		return trophyPoints;

	}
}
