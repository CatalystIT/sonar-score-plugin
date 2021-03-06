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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.measures.Metric;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.commons.util.SearchableSetIterable;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.Criterion;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.TitleCup;

import org.sonar.api.config.Settings;

import com.catalyst.sonar.score.dao.*;

/**
 * The TitleCup Decorator awards TitleCups to qualifying projects when a project
 * is built.
 * 
 * @author JDunn
 * 
 */
public class TitleCupDecorator extends AbstractAwardDecorator implements
		Decorator {
	
	private final Logger logger = LoggerFactory.getLogger(TitleCupDecorator.class);
	

	/**
	 * 
	 * @param session
	 * @param project
	 * @param settings
	 */
	public TitleCupDecorator(DatabaseSession session, Project project,
			Settings settings) {
		super(session, project, settings);
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
			if (!resource.getScope().equals("PRJ")) {
				return;
			}
			logger.debug("TitleCupDecorator.decorate()");
			TitleCupDao cupDao = new TitleCupDao(session);
			AwardSet<TitleCup> cups = cupDao.getAll();
			if (cups == null) {
				return;
			}
			logger.info("There are " + cups.size() + " TitleCups");
			ScoreProjectDao projectDao = new ScoreProjectDao(session);
			ScoreProject thisProject = projectDao.getProjectById(resource
					.getId());
			for (TitleCup cup : cups) {
				logger.info("Cup = " + cup.getName());
				Property titleCupProperty = cupDao.getTitleCupProperty(cup
						.getName());
				Integer resourceId = titleCupProperty.getResourceId();
				logger.info("resourceId = " + resourceId);
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
				logger.info("currentHolder = " + currentHolderName + " ("
						+ currentHolderKey + ")");
				logger.info(currentHolderName + " currently Holds the "
						+ cup.getName() + " cup.");
				logger.info("The Challenger is " + thisProject.getName());
				winner = whoShouldEarnCup(cup, thisProject, currentHolder);
				if (winner != null) {
					logger.info("The Winner is " + winner.getName());
				} else {
					logger.info("Woops!! The Winner is null, so neither project earned "
							+ cup + ".");
				}
				cupDao.assign(cup, winner);
			}
		} catch (RuntimeException e) {
			logger.info(e.getStackTrace().toString());
		}
	}

	private ScoreProject whoShouldEarnCup(TitleCup cup,
			ScoreProject thisProject, ScoreProject currentHolder) {
		logger.debug("whoShouldEarnCup()");
		if (!typeGoodCriteriaMet(cup)) {
			logger.debug(project.getName() + " no long meets Criteria.");
			return null;
		} else if (currentHolder == null || currentHolder.equals(thisProject)) {
			logger.debug(project.getName()
					+ " meets Criteria, and currentHolder is "
					+ ((currentHolder != null) ? currentHolder.getName() : null));
			return thisProject;
		}
		ScoreProject projectToReturn = null;
		ScoreProject potential;
		SearchableSetIterable<Criterion> criteria = cup.getCriteria();
		logger.info("There are " + criteria.size() + " Criteria in " + cup + ":");
		for (Criterion criterion : cup.getCriteria()) {
			logger.debug("Criterion = " + criterion);
			if (criterion.getType() == Criterion.Type.BEST) {
				logger.debug("Who has the best score for "
						+ criterion.getMetric().getName() + "?");
				Metric metric = criterion.getMetric();
				potential = better(thisProject, currentHolder, metric);
				if (projectToReturn != null && projectToReturn != potential) {
					// If we get here, than one project is best at one
					// criterion, but the other project is better at another,
					// so neither project should earn this TitleCup.
					logger.debug("Potential Winner and ProjectToReturn do not match: Each project is better than the other at a metric.");
					logger.debug("Leaving whoShouldEarnCup(), returning null");
					return null;
				} else {
					projectToReturn = potential;
				}
			}
		}
		logger.debug("Leaving whoShouldEarnCup(), returning "
				+ projectToReturn.getName());
		return projectToReturn;
	}




}
