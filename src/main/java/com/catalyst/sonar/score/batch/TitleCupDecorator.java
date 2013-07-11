package com.catalyst.sonar.score.batch;

import static com.catalyst.sonar.score.log.Logger.LOG;
import java.util.Collections;
import java.util.List;

import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.SearchableHashSet;
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
		try {
			if(resource.getScope() != "PRJ") {
				return;
			}
			LOG.beginMethod("TitleCupDecorator.decorate()");
			TitleCupDao cupDao = new TitleCupDao(session);
			AwardSet<TitleCup> cups = cupDao.getAll();
			LOG.logEmf("There are " + cups.size() + " TitleCups");
			ScoreProjectDao projectDao = new ScoreProjectDao(session);
			ScoreProject thisProject = projectDao.getProjectById(resource
					.getId());
			for (TitleCup cup : cups) {
				LOG.logEmf("Cup = " + cup.getName());
				Property titleCupProperty = cupDao.getTitleCupProperty(cup.getName());
				if(titleCupProperty==null) {
					cupDao.create(cup);
					titleCupProperty = cupDao.getTitleCupProperty(cup.getName());
				}
				Integer resourceId = titleCupProperty.getResourceId();
				LOG.logEmf("resourceId = " + resourceId);
				ScoreProject currentHolder = projectDao
						.getProjectById(resourceId);
				ScoreProject winner;
				String currentHolderName;
				String currentHolderKey;
				if (currentHolder == null) {
					currentHolderName = "Nobody";
					currentHolderKey = "I mean Nobody";
				} else {
					currentHolderName = currentHolder.getName();
					currentHolderKey = currentHolder.getKey();
				}
				LOG.logEmf("currentHolder = "
						+ currentHolderName + " ("
						+ currentHolderKey + ")");
				LOG.logEmf(currentHolderName
						+ " currently Holds the " + cup.getName() + " cup.");
				LOG.logEmf("The Challenger is "
						+ thisProject.getName());
				winner = whoShouldEarnCup(cup, thisProject,
						currentHolder);
				if (winner != null) {
					LOG.logEmf("The Winner is " + winner.getName());
				} else {
					LOG.logEmf("Woops!! The Winner is null, so neither project earned "
									+ cup + ".");
				}
				cupDao.assign(cup, winner);
			}
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}
		LOG.endMethod();

	}

	private ScoreProject whoShouldEarnCup(TitleCup cup,
			ScoreProject thisProject, ScoreProject currentHolder) {
		LOG.beginMethod("whoShouldEarnCup()");
		if (!criteriaMet(cup)) {
			return null;
		} else if (currentHolder==null){
			return thisProject;
		}
		ScoreProject projectToReturn = null;
		ScoreProject potential;
		SearchableHashSet<Criterion> criteria = cup.getCriteria();
		LOG.log("There are " + criteria.size() + " Criteria in " + cup + ":");
		for (Criterion criterion : cup.getCriteria()) {
			LOG.log("Criterion = " + criterion);
			if (criterion.getType() == Criterion.Type.BEST) {
				LOG.log("Who has the best score for " + criterion.getMetric().getName() + "?");
				Metric metric = criterion.getMetric();
				potential = better(thisProject, currentHolder, metric);
				if (projectToReturn != potential) {
					// If we get here, than one project is best at one
					// criterion, but the other project is better at another,
					// so neither project should earn this TitleCup.
					LOG.log("Leaving whoShouldEarnCup(), returning null").endMethod();
					return null;
				} else {
					projectToReturn = potential;
				}
			}
		}
		LOG.log("Leaving whoShouldEarnCup(), returning "
				+ projectToReturn.getName()).endMethod();
		return projectToReturn;
	}

	private boolean criteriaMet(Award award) {
		LOG.beginMethod("criteriaMet()");
		LOG.log(award + " has "
				+ award.getCriteria().size() + " Criteria:");
		try {
			for (Criterion criterion : award.getCriteria()) {
				LOG.log(criterion);
				if (criterion.getType() == Criterion.Type.BEST) {
					continue;
				}
				Metric metric = criterion.getMetric();
				List<SnapshotHistory> entries = measuresHelper
						.getMeasureCollection(metric.getName());
				LOG.log("SnapshotHistories:");
				System.out.println("\t\t" + entries);
				if (!trophiesHelper.criteriaMet(entries, criterion.getAmount(),
						criterion.getDays(), metric.getName(), session)) {
					LOG.log("Leaving criteriaMet(), returning false").endMethod();
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.log("Leaving criteriaMet(), returning true").endMethod();
		return true;
	}

	private double currentValue(ScoreProject thisProject, Metric metric) {
		LOG.beginMethod("Current Value");
		MeasuresHelper helper = new MeasuresHelper(session, thisProject);
		List<SnapshotHistory> history = helper.getMeasureCollection(metric
				.getName());
		Collections.sort(history);
		SnapshotHistory lastSnapshot = (history.size() > 0) ? history.get(history.size() - 1) : null;
		LOG.log("Last Snapshot for " + thisProject.getName() + " = " + lastSnapshot);
		double value = (lastSnapshot != null) ? lastSnapshot.getMeasureValue().doubleValue() : null;
		LOG.beginMethod("current value = " + value).endMethod();
		return value;
	}

	private ScoreProject better(ScoreProject project1, ScoreProject project2,
			Metric metric) {
		LOG.beginMethod("Which project is better?");
		ScoreProject projectToReturn;
		if (MeasuresHelper.isBetter(currentValue(project1, metric),
				currentValue(project2, metric), metric)) {
			projectToReturn = project1;
		} else {
			projectToReturn = project2;
		}
		LOG.log(projectToReturn + " is better.").endMethod();
		return projectToReturn;
	}

}
