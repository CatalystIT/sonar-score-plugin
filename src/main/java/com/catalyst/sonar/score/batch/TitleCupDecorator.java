package com.catalyst.sonar.score.batch;

import java.util.Collections;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.TitleCup;

import org.sonar.api.config.Settings;

import com.catalyst.sonar.score.dao.*;
import com.catalyst.sonar.score.util.MeasuresHelper;
import com.catalyst.sonar.score.util.SnapshotHistory;
import com.catalyst.sonar.score.util.TrophiesHelper;

/**
 * The TitleCup Decorator awards TitleCups to qualifying projects when a project
 * is built.
 * 
 * @author JDunn
 * 
 */
public class TitleCupDecorator implements Decorator {

	private final DatabaseSession session;
	private Project project;
	private Settings settings;
	private MeasuresHelper measuresHelper;
	private TrophiesHelper trophiesHelper;

	public TitleCupDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.session = session;
		this.project = project;
		this.settings = settings;
		this.measuresHelper = new MeasuresHelper(session, project);
		this.trophiesHelper = new TrophiesHelper(settings);

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
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings("rawtypes")
	public boolean shouldCheckTrophyStatusForResource(final Resource resource) {
		return ResourceUtils.isProject(resource);
	}

	/**
	 * This method is called when build is scheduled for a given project
	 */
	public void decorate(@SuppressWarnings("rawtypes") final Resource resource,
			DecoratorContext context) {
		TitleCupDao cupDao = new TitleCupDao(session);
		AwardSet<TitleCup> cups = cupDao.getAll();
		ScoreProjectDao projectDao = new ScoreProjectDao(session);
		ScoreProject thisProject = projectDao.getProjectById(resource.getId());
		for (TitleCup cup : cups) {
			ScoreProject currentHolder = projectDao.getProjectById(cupDao
					.getCurrentlyHeld(cup).getResourceId());
			ScoreProject winner = whoShouldEarnCup(cup, thisProject, currentHolder);
			if(winner != null) {
				cupDao.assign(cup, winner);
			} else {
				//TODO unassign the cup so no one has it.
			}
		}

	}

	private ScoreProject whoShouldEarnCup(TitleCup cup, ScoreProject thisProject, ScoreProject currentHolder) {
		if (!criteriaMet(cup)) {
			return null;
		}
		ScoreProject projectToReturn = null;
		ScoreProject potential;
		for (Criterion criterion : cup.getCriteria()) {
			if (criterion.getType() == Criterion.Type.BEST) {
				Metric metric = criterion.getMetric();
				potential = better(thisProject, currentHolder, metric);
				if (projectToReturn != null && projectToReturn != potential) {
					// If we get here, than one project is best at one
					// criterion, but the other project is better at another,
					// so neither project should earn this TitleCup.
					return null;
				}
			}
		}

		return projectToReturn;
	}

	private boolean criteriaMet(Award award) {
		for (Criterion criterion : award.getCriteria()) {
			if (criterion.getType() == Criterion.Type.BEST) {
				continue;
			}
			Metric metric = criterion.getMetric();
			List<SnapshotHistory> entries = measuresHelper
					.getMeasureCollection(metric.getName());
			if (!trophiesHelper.criteriaMet(entries, criterion.getAmount(),
					criterion.getDays(), metric.getName(), session)) {
				return false;
			}
		}
		return true;
	}

	private double currentValue(ScoreProject thisProject, Metric metric) {
		MeasuresHelper helper = new MeasuresHelper(session, thisProject);
		List<SnapshotHistory> history = helper.getMeasureCollection(metric
				.getName());
		Collections.sort(history);
		return history.get(history.size() - 1).getMeasureValue().doubleValue();
	}

	private ScoreProject better(ScoreProject project1, ScoreProject project2, Metric metric) {
		if (MeasuresHelper.isBetter(currentValue(project1, metric),
				currentValue(project2, metric), metric)) {
			return project1;
		} else {
			return project2;
		}
	}

}
