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

import static com.catalyst.sonar.score.log.Logger.LOG;

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
import com.catalyst.sonar.score.api.Trophy;
import com.catalyst.sonar.score.batch.util.TrophiesHelper;
import com.catalyst.sonar.score.dao.ScoreProjectDao;
import com.catalyst.sonar.score.dao.SnapShotDao;
import com.catalyst.sonar.score.dao.TrophyDao;
import com.catalyst.sonar.score.util.SnapshotValue;

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
	private SnapShotDao measuresHelper;
	private TrophiesHelper trophiesHelper;

	/**
	 * Constructs a TrophiesDecorator, setting the session, project, and
	 * settings.
	 * 
	 * @param session
	 * @param project
	 * @param settings
	 */
	public TrophiesDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.session = session;
		this.project = project;
		this.measuresHelper = new SnapShotDao(session, project);
		this.trophiesHelper = new TrophiesHelper(settings);
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
	 * @return checks if resource is a unit test class
	 */
	public boolean shouldDecorateResource(@SuppressWarnings("rawtypes") final Resource resource,
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
	public boolean shouldCheckTrophyStatusForResource(@SuppressWarnings("rawtypes") final Resource resource) {
		return ResourceUtils.isProject(resource);
	}

	/**
	 * This method is called when build is scheduled for a given project
	 */
	public void decorate(@SuppressWarnings("rawtypes") final Resource resource,
			DecoratorContext context) {
		try {
			if(!resource.getScope().equals("PRJ")) {
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
		} catch (RuntimeException e) {
			LOG.log(e);
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
		} catch (RuntimeException e) {
			LOG.log(e);
		}
		LOG.log("Leaving criteriaMet(), returning true").endMethod();
		return true;
	}
	
	

}
