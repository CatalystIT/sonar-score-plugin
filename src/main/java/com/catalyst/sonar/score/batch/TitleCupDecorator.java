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
			System.out
			.println("\n******START*******************************************");
			TitleCupDao cupDao = new TitleCupDao(session);
			// Property tcProperty = cupDao.getTitleCupProperty("BestCoverage");
			// tcProperty.setResourceId(1);
			// session.save(tcProperty);
			AwardSet<TitleCup> cups = cupDao.getAll();
			System.out.println("!!! There are " + cups.size() + " TitleCups");
			ScoreProjectDao projectDao = new ScoreProjectDao(session);
			ScoreProject thisProject = projectDao.getProjectById(resource
					.getId());
			for (TitleCup cup : cups) {
				System.out.println("!!!Cup = " + cup.getName());
				Property titleCupProperty = cupDao.getTitleCupProperty(cup.getName());
				if(titleCupProperty==null) {
					cupDao.create(cup);
					titleCupProperty = cupDao.getTitleCupProperty(cup.getName());
				}
				Integer resourceId = titleCupProperty.getResourceId();
				System.out.println("!!! resourceId = " + resourceId);
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
				System.out.println("!!! currentHolder = "
						+ currentHolderName + " ("
						+ currentHolderKey + ")");
				System.out.println("!!! " + currentHolderName
						+ " currently Holds the " + cup.getName() + " cup.");
				System.out.println("!!! The Challenger is "
						+ thisProject.getName());
				winner = whoShouldEarnCup(cup, thisProject,
						currentHolder);
				if (winner != null) {
					System.out.println("!!! The Winner is " + winner.getName());
				} else {
					System.out
							.println("!!! Woops!! The Winner is null, so neither project earned "
									+ cup + ".");
				}
				cupDao.assign(cup, winner);
			}
		} catch (NullPointerException e) {
			// e.printStackTrace();
		}
		System.out
				.println("*******************************************STOP!******\n");

	}

	private ScoreProject whoShouldEarnCup(TitleCup cup,
			ScoreProject thisProject, ScoreProject currentHolder) {
		System.out.println("\t<><><><><><><><>");
		System.out.println("\tIn whoShouldEarnCup()");
		if (!criteriaMet(cup)) {
			return null;
		} else if (currentHolder==null){
			return thisProject;
		}
		ScoreProject projectToReturn = null;
		ScoreProject potential;
		SearchableHashSet<Criterion> criteria = cup.getCriteria();
		System.out.println("\tThere are " + criteria.size() + " Criteria in " + cup + ":");
		for (Criterion criterion : cup.getCriteria()) {
			System.out.println("\tCriterion = " + criterion);
			if (criterion.getType() == Criterion.Type.BEST) {
				System.out.println("\tWho has the best score for " + criterion.getMetric().getName() + "?");
				Metric metric = criterion.getMetric();
				potential = better(thisProject, currentHolder, metric);
				if (projectToReturn != potential) {
					// If we get here, than one project is best at one
					// criterion, but the other project is better at another,
					// so neither project should earn this TitleCup.
					System.out
							.println("\tLeaving whoShouldEarnCup(), returning null");
					System.out.println("\t<><v><><><><><><>");
					return null;
				} else {
					projectToReturn = potential;
				}
			}
		}
		System.out.println("\tLeaving whoShouldEarnCup(), returning "
				+ projectToReturn.getName());
		System.out.println("\t<><v><><><><><><>");
		return projectToReturn;
	}

	private boolean criteriaMet(Award award) {
		System.out.println("\t\t----------------");
		System.out.println("\t\tIn criteriaMet()");
		System.out.println("\t\t" + award + " has "
				+ award.getCriteria().size() + " Criteria:");
		try {
			for (Criterion criterion : award.getCriteria()) {
				System.out.println("\t\t" + criterion);
				if (criterion.getType() == Criterion.Type.BEST) {
					continue;
				}
				Metric metric = criterion.getMetric();
				List<SnapshotHistory> entries = measuresHelper
						.getMeasureCollection(metric.getName());
				System.out.println("\t\tSnapshotHistories:");
				System.out.println("\t\t" + entries);
				if (!trophiesHelper.criteriaMet(entries, criterion.getAmount(),
						criterion.getDays(), metric.getName(), session)) {
					System.out
							.println("\t\tLeaving criteriaMet(), returning false");
					System.out.println("\t\t----------------");
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\t\tLeaving criteriaMet(), returning true");
		System.out.println("\t\t----------------");
		return true;
	}

	private double currentValue(ScoreProject thisProject, Metric metric) {
		MeasuresHelper helper = new MeasuresHelper(session, thisProject);
		List<SnapshotHistory> history = helper.getMeasureCollection(metric
				.getName());
		Collections.sort(history);
		double value = (history.size() > 0) ? history.get(history.size() - 1).getMeasureValue().doubleValue() : null;
		return value;
	}

	private ScoreProject better(ScoreProject project1, ScoreProject project2,
			Metric metric) {
		ScoreProject projectToReturn;
		if (MeasuresHelper.isBetter(currentValue(project1, metric),
				currentValue(project2, metric), metric)) {
			projectToReturn = project1;
		} else {
			projectToReturn = project2;
		}
		System.out.println("\t\t" + projectToReturn + " is better.");
		return projectToReturn;
	}

}
