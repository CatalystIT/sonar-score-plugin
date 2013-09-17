/*
 * Copyright 2013 Catalyst IT Services
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.catalyst.sonar.score.batch;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.ScoreProject;
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

	private final Logger logger = LoggerFactory.getLogger(AbstractAwardDecorator.class);
	
	protected final DatabaseSession session;
	protected Project project;
	protected Settings settings;
	protected SnapShotDao measuresHelper;

	/**
	 * Constructs an AbstractAwardDecorator, setting the session, project, and
	 * settings.
	 * 
	 * @param session
	 * @param project
	 * @param settings
	 */
	public AbstractAwardDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.session = session;
		this.project = project;
		this.settings = settings;
		this.measuresHelper = new SnapShotDao(session, project);

	}

	/**
	 * Determines whether the project being decorated meets the Criteria of type
	 * "Good" specified by the Award.
	 * 
	 * @param award
	 * @return
	 */
	// TODO: Code Smell, why is this returning true after an exception is
	// caught? Or is the try catch left over from debugging? If so it should be
	// removed.
	protected boolean typeGoodCriteriaMet(Award award) {
		logger.info("{} has {} Criteria:", award, award.getCriteria().size());
		try {
			for (Criterion criterion : award.getCriteria()) {
				logger.info("{}", criterion);
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
				logger.info("SnapshotHistories:");
				logger.info(entries.toString());
				if (!typeGoodCriterionMet(new SnapshotValues(entries),
						criterion)) {
					return false;
				}
			}
		} catch (RuntimeException e) {
			logger.info(e.getStackTrace().toString());
		}
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
		snapshots.retainAllWithinDaysAgo(criterion.getDays());
		for (SnapshotValue value : snapshots) {
			logger.info("Value = {}", value);
			if (!isBetter(value.getMeasureValue().doubleValue(),
					criterion.getAmount(), criterion.getMetric())) {
				logger.info("Doesn't meet Criterion");
				return false;
			}
		}
		snapshots.restore();
		logger.info("Meets Criterion");
		return true;
	}

	/**
	 * Given the two ScoreProjects and the Metric, figures out which one has the
	 * better Metric value in its latest snapshot.
	 * 
	 * @param project1
	 * @param project2
	 * @param metric
	 * @return the project that has the better metric value.
	 */
	protected ScoreProject better(ScoreProject project1, ScoreProject project2,
			Metric metric) {
		logger.info("Which project is better?");
		ScoreProject projectToReturn;
		double value1 = currentValue(project1, metric);
		double value2 = currentValue(project2, metric);
		if (isBetter(value1, value2, metric)) {
			projectToReturn = project1;
		} else {
			projectToReturn = project2;
		}
		logger.info(project1.getName() + " has " + value1 + ", "
				+ project2.getName() + " has " + value2 + ", so:");
		logger.info(projectToReturn.getName() + " is better.");
		return projectToReturn;
	}

	/**
	 * Given the ScoreProject, finds its current value for the given Metric.
	 * @param thisProject
	 * @param metric
	 * @return the latest value for the metric attained by the ScoreProject.
	 */
	private double currentValue(ScoreProject thisProject, Metric metric) {
		logger.info("Current Value");
		SnapShotDao helper = new SnapShotDao(session, thisProject);
		List<SnapshotValue> history = helper.getMeasureCollection(metric
				.getName());
		Collections.sort(history);
		SnapshotValue lastSnapshot = (history.size() > 0) ? history.get(history
				.size() - 1) : null;
		logger.info("Last Snapshot for " + thisProject.getName() + " = "
				+ lastSnapshot);
		double value = (lastSnapshot != null) ? lastSnapshot.getMeasureValue()
				.doubleValue() : 0;
		logger.info("current value = " + value);
		return value;
	}
	
	/**
	 * Corrective for Metric Direction, since for some inexplicable reason
	 * Sonar's size metrics are set to DIRECTION_WORSE. Also changes
	 * DIRECTION_NONE to DIRECTION_BETTER.
	 * 
	 * @param metric
	 * @return
	 */
	public static int getDirection(Metric metric) {
		if (metric.getDomain().equals("Size")) {
			return Metric.DIRECTION_BETTER;
		} else if (metric.getDirection() == Metric.DIRECTION_NONE) {
			return Metric.DIRECTION_BETTER;
		}
		return metric.getDirection();
	}

	/**
	 * Checks if the double value is better than the double compare, given the
	 * direction of the Metric.
	 * 
	 * @param value
	 * @param compare
	 * @param metric
	 * @return
	 */
	public static boolean isBetter(double value, double compare, Metric metric) {
		if (getDirection(metric) == Metric.DIRECTION_BETTER) {
			return value > compare;
		}
		return compare < value;
	}

}
