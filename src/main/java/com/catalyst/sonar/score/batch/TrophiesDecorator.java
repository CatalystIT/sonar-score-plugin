package com.catalyst.sonar.score.batch;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.api.database.DatabaseSession;
import com.catalyst.sonar.score.batch.trophies.AwardTrophies;
import org.sonar.api.config.Settings;

/**
 * The Trophies Decorator awards Trophies to qualifying projects when
 * a build is scheduled for a project
 * 
 * @author Team Build Meister
 * 
 */
public class TrophiesDecorator implements Decorator {

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
		 * if the resource is a project, award any earned trophies
		 */
		if (shouldCheckTrophyStatusForResource(resource)
				&& shouldDecorateResource(resource, context)) {
			awardTrophies.awardTrophies(context, resource);

		}

	}

}
