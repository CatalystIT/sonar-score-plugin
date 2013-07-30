/**
 * 
 */
package com.catalyst.sonar.score.batch;

import static com.catalyst.sonar.score.log.Logger.LOG;

import java.util.Collections;
import java.util.List;

import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.batch.util.TrophiesHelper;
import com.catalyst.sonar.score.dao.SnapShotDao;
import com.catalyst.sonar.score.util.SnapshotValue;
import com.catalyst.sonar.score.util.SnapshotValues;

/**
 * The {@code AbstractAwardDecorator} contains methods that Award Decorators
 * must use to determine for each available {@link Award} whether or not the
 * Project has earned it.
 * 
 * @author JDunn
 */
public class AbstractAwardDecorator {

	protected final DatabaseSession session;
	protected Project project;
	protected Settings settings;
	protected SnapShotDao measuresHelper;
	protected TrophiesHelper trophiesHelper;

	public AbstractAwardDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.session = session;
		this.project = project;
		this.settings = settings;
		this.measuresHelper = new SnapShotDao(session, project);
		this.trophiesHelper = new TrophiesHelper(settings);

	}

	/**
	 * Determines whether the project being decorated meets the Criteria of type
	 * "Good" specified by the Award.
	 * 
	 * @param award
	 * @return
	 */
	protected boolean typeGoodCriteriaMet(Award award) {
		LOG.beginMethod("criteriaMet()");
		LOG.log(award + " has " + award.getCriteria().size() + " Criteria:");
		try {
			for (Criterion criterion : award.getCriteria()) {
				LOG.log(criterion);
				if (criterion.getType() == Criterion.Type.BEST) {
					/*
					 * We don't want to process Criterion of type BEST, only of
					 * type GOOD.
					 */
					continue;
				}
				Metric metric = criterion.getMetric();
				List<SnapshotValue> entries = measuresHelper
						.getMeasureCollection(metric.getName());
				LOG.log("SnapshotHistories:").log(entries);
				if (!typeGoodCriterionMet(new SnapshotValues(entries),
						criterion)) {
					LOG.log("Leaving criteriaMet(), returning false")
							.endMethod();
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.log("Leaving criteriaMet(), returning true").endMethod();
		return true;
	}

	/**
	 * Determines for a specific Criterion whether or not the project being
	 * decorated meets it.
	 * 
	 * @param snapshots
	 * @param criterion
	 * @return
	 */
	protected boolean typeGoodCriterionMet(SnapshotValues snapshots,
			Criterion criterion) {
		LOG.beginMethod("Meets Criterion " + criterion);
		snapshots.retainAllWithinDaysAgo(criterion.getDays());
		for (SnapshotValue value : snapshots) {
			LOG.log(value);
			if (!SnapShotDao.isBetter(value.getMeasureValue().doubleValue(),
					criterion.getAmount(), criterion.getMetric())) {
				LOG.log("Doesn't meet Criterion").endMethod();
				return false;
			}
		}
		snapshots.restore();
		LOG.log("Meets Criterion").endMethod();
		return true;
	}

	protected ScoreProject better(ScoreProject project1, ScoreProject project2,
			Metric metric) {
		LOG.beginMethod("Which project is better?");
		ScoreProject projectToReturn;
		double value1 = currentValue(project1, metric);
		double value2 = currentValue(project2, metric);
		if (SnapShotDao.isBetter(value1, value2, metric)) {
			projectToReturn = project1;
		} else {
			projectToReturn = project2;
		}
		LOG.log(project1.getName() + " has " + value1 + ", "
				+ project2.getName() + " has " + value2 + ", so:");
		LOG.log(projectToReturn.getName() + " is better.").endMethod();
		return projectToReturn;
	}

	private double currentValue(ScoreProject thisProject, Metric metric) {
		LOG.beginMethod("Current Value");
		SnapShotDao helper = new SnapShotDao(session, thisProject);
		List<SnapshotValue> history = helper.getMeasureCollection(metric
				.getName());
		Collections.sort(history);
		SnapshotValue lastSnapshot = (history.size() > 0) ? history.get(history
				.size() - 1) : null;
		LOG.log("Last Snapshot for " + thisProject.getName() + " = "
				+ lastSnapshot);
		double value = (lastSnapshot != null) ? lastSnapshot.getMeasureValue()
				.doubleValue() : null;
		LOG.log("current value = " + value).endMethod();
		return value;
	}

}
