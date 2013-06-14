package com.catalyst.sonar.score.batch;

import java.util.Arrays;
import java.util.Collection;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependedUpon;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.batch.trophies.AwardTrophies;
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
public class TrophiesDecorator<PastSnapshotFinderByDays> implements Decorator {

	private final DatabaseSession session;
	private Project project;
	private Settings settings;
	private AwardTrophies awardTrophies;


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
	 * returns analysis type of the project
	 */
	public boolean shouldExecuteOnProject(Project project) {
		// TODO
		// !Project.AnalysisType.STATIC.equals(project.getAnalysisType())||
		// !Project.AnalysisType.DYNAMIC.equals(project.getAnalysisType());

		return true;
	}

	/**
	 * 
	 * @param resource
	 * @param context
	 * @return checks if resource is a unit test class
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
	 * This method is called when build is scheduled for a given project
	 */
	public void decorate(final Resource resource, DecoratorContext context) {
		awardTrophies = new AwardTrophies (session, project, settings);
				
		/*
		 * if the resource is a project award any earned trophies
		 */
		if (shouldCheckTrophyStatusForResource(resource)
				&& shouldDecorateResource(resource, context)) {
			awardTrophies.awardTrophies(context, resource);

		}

	}

}
