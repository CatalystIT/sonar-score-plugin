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
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;

import com.catalyst.sonar.score.batch.util.SetupExecuter;
import com.catalyst.sonar.score.dao.PropertyDao;

/**
 * 
 * The SetupDecorator may eventually need to be deprecated or removed. The
 * purpose of the SetupDecorator is to check if certain conditions are present
 * needed for the Score Plugin to work properly. Currently, this means
 * automatically adding certain images to the SonarQube Server's images
 * directory. Achieving this through Java has proved elusive as the images are
 * installed in a path relative to the directory of the CI Engine (Hudson or
 * Jenkins) rather than Sonar's Directory.
 * 
 * @see {@link SetupExecuter}
 * @see {@link FileInstaller}
 * 
 * @author JDunn
 * 
 */
public class SetupDecorator implements Decorator {

	private final Logger logger = LoggerFactory.getLogger(SetupDecorator.class);
	
	
	public static final String SETUP = "sonar.score.setup";

	/*
	 * {@code int tries} prevents SetupDecorator.decorate() from running its
	 * execution branch more than once. Set to 1 to prevent this decorator from
	 * executing at all during normal operations until the code functions
	 * correctly.  If functioning correctly, should be set to 0.
	 */
	private static int tries = 1;

	private Project project;
	private PropertyDao propertyDao;

	/**
	 * Note: the settings parameter is not used.
	 * 
	 * @param session
	 * @param project
	 * @param settings
	 */
	// TODO: See if the settings parameter can be removed and the decorator
	// still work.
	public SetupDecorator(DatabaseSession session, Project project,
			Settings settings) {
		this.project = project;
		this.propertyDao = new PropertyDao(session);

	}

	/**
	 * Only returns true if it hasn't tried yet {@code (tries = 0)} and the
	 * SetUp property in the database = {@code false}.
	 * 
	 * @see org.sonar.api.batch.CheckProject#shouldExecuteOnProject(org.sonar.api.resources.Project)
	 */
	public boolean shouldExecuteOnProject(Project project) {
		if (tries > 0) {
			return false;
		}
		boolean should = !Boolean.parseBoolean(getSetUpProperty().getValue());
		logger.info("System is " + ((should) ? "not " : "") + "setup");
		return should;
	}

	/**
	 * If the score plugin is not setup, the decorate method will run once and
	 * set things up by calling SetupExecuter.execute().
	 * 
	 * @see org.sonar.api.batch.Decorator#decorate(org.sonar.api.resources.Resource,
	 *      org.sonar.api.batch.DecoratorContext)
	 */
	public void decorate(@SuppressWarnings("rawtypes") Resource resource,
			DecoratorContext context) {
		if (!shouldExecuteOnProject(project) || !project.getScope().equals("PRJ")) {
			return;
		} else {
			logger.info("Project Scope = " + project.getScope() + ", Project = "
					+ project.getKey());
			logger.info("Setting Up System");
			SetupExecuter executer = new SetupExecuter(propertyDao);
			setSetUpProperty(executer.execute());
			tries++;
		}
	}

	/**
	 * Gets the SetUp Property from the database; if it does not exist, creates
	 * one with the SetUp key and null values.
	 * 
	 * @return the SetUpProperty
	 */
	private Property getSetUpProperty() {
		Property setUpProperty = propertyDao.get(SETUP);
		if (setUpProperty == null) {
			setUpProperty = new Property();
			setUpProperty.setKey(SETUP);
			setUpProperty = propertyDao.create(setUpProperty);
		}
		return setUpProperty;
	}

	/**
	 * Sets the value of the SetUpProperty, either to {@code true} or
	 * {@code false}.
	 * 
	 * @param isSetUp
	 * @return
	 */
	private Property setSetUpProperty(boolean isSetUp) {
		return propertyDao.setValue(SETUP, isSetUp);
	}
}
