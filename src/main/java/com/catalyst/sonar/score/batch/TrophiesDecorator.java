package com.catalyst.sonar.score.batch;

import static com.catalyst.sonar.score.log.Logger.LOG;

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
import com.catalyst.sonar.score.api.TitleCup;
import com.catalyst.sonar.score.api.Trophy;
import com.catalyst.sonar.score.batch.trophies.AwardTrophies;
import com.catalyst.sonar.score.dao.ScoreProjectDao;
import com.catalyst.sonar.score.dao.SnapShotDao;
import com.catalyst.sonar.score.dao.TitleCupDao;
import com.catalyst.sonar.score.dao.TrophyDao;
import com.catalyst.sonar.score.util.SnapshotValue;
import com.catalyst.sonar.score.util.TrophiesHelper;

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
	private SnapShotDao measuresHelper;
	private TrophiesHelper trophiesHelper;


	public TrophiesDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.session = session;
		this.project = project;
		this.settings = settings;
		this.measuresHelper = new SnapShotDao(session, project);
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
	public void decorate(@SuppressWarnings("rawtypes") final Resource resource,
			DecoratorContext context) {
		try {
			if(resource.getScope() != "PRJ") {
				return;
			}
			LOG.beginMethod("TrophiesDecorator.decorate()");
			TrophyDao trophyDao = new TrophyDao(session);
			AwardSet<Trophy> trophies = trophyDao.getAll();
			if(trophies == null) {
				LOG.endMethod();
				return;
			}
			LOG.logEmf("There are " + trophies.size() + " Trophies");
			ScoreProjectDao projectDao = new ScoreProjectDao(session);
			ScoreProject thisProject = projectDao.getProjectById(project
					.getId());
			for (Trophy trophy : trophies) {
				LOG.logEmf("Trophy = " + trophy.getName());
				if (criteriaMet(trophy)) {					
					trophyDao.assign(trophy, thisProject);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		LOG.endMethod();

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
				List<SnapshotValue> entries = measuresHelper
						.getMeasureCollection(metric.getName());
				LOG.log("SnapshotHistories:").log(entries);
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
	
	

}
