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
package com.catalyst.sonar.score.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.model.ResourceModel;

import com.catalyst.commons.util.SearchableHashSet;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.batch.AbstractAwardDecorator;

//TODO javadoc
public class ScoreProjectDao extends SonarModelDao<ScoreProject> {
	
	private final Logger logger = LoggerFactory.getLogger(ScoreProjectDao.class);
	

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 * 
	 * @see {@link ScoreEntityDao#ScoreEntityDao(DatabaseSession)}
	 */
	public ScoreProjectDao(DatabaseSession session) {
		super(session);
	}

	// TODO javadoc
	@Override
	public ScoreProject get(String key) {
		ResourceModel model = getSession().getResults(ResourceModel.class,
				"key", key).get(0);
		return new ScoreProject(model);
	}

	// TODO javadoc
	public ScoreProject getProjectById(Integer id) {
		logger.debug("getProjectById()");
		if (id == null) {
			return null;
		}
		ScoreProject scoreProject = null;

		List<ResourceModel> models = getSession().getResults(
				ResourceModel.class, "id", id);
		if (models == null) {
			logger.warn("models is null!!!!");
		} else if (models.size() < 1) {
			logger.warn("models.size() = " + models.size());
		} else {
			logger.info("We are only here because models.size() = " + models.size());
			logger.info("\tThere are " + models.size() + " Projects:");
			for (ResourceModel model : models) {
				logger.info(model.getName() + " (" + model.getKey() + ")");
			}
			ResourceModel model = models.get(0);
			if (model != null) {
				logger.info("model is null!!!!");
				logger.info("Here is the first Project:");
				logger.info(model.getName() + " (" + model.getKey() + ")");
				scoreProject = new ScoreProject(model);
				logger.info("Converted to a ScoreProject:");
				logger.info(scoreProject.getName() + " ("
								+ scoreProject.getKey() + ")");
			}
		}
		logger.info("returning " + scoreProject);
		return scoreProject;
	}

	// TODO javadoc
	@Override
	public SearchableHashSet<ScoreProject> getAll() {
		// TODO implement.
		return new SearchableHashSet<ScoreProject>();
	}

	// TODO javadoc
	@Override
	public ScoreProject create(ScoreProject project) {
		// TODO: log some sort of warning that this method should never be used.
		return null;
	}

	// TODO javadoc
	@Override
	public ScoreProject update(ScoreProject entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScoreProject create(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<ScoreProject> entityClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScoreProject get(ScoreProject entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
