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

import static com.catalyst.sonar.score.ScorePlugin.TROPHY;
import static com.catalyst.sonar.score.log.Logger.LOG;

import java.util.Date;
import java.util.List;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;
import com.catalyst.sonar.score.api.Trophy;

/**
 * The {link TrophyDao} class implements methods from {@link AwardDao}{@code <}
 * {@link Trophy}{@code >} to specifically retrieve and/or manipulate
 * {@link Trophy} information in the database.
 * 
 * @author JDunn
 * 
 */
public class TrophyDao extends AwardDao<Trophy> {

	private PropertyDao propertyDao;

	/**
	 * @param session
	 */
	public TrophyDao(DatabaseSession session) {
		super(session);
		this.propertyDao = new PropertyDao(session);
	}

	/**
	 * @see {@link TrophyDao#assignToProject(Award, ScoreUser)	
	 */
	public boolean assign(Trophy trophy, ScoreProject project) {
		return assignToProject(trophy, project);
	}

	/**
	 * @see {@link AwardDao#assignToUser(Award, ScoreUser)	
	 */
	@Override
	protected boolean assignToUser(Trophy trophy, ScoreUser user) {
		// TODO implement, and get rid of line below
		getEarnedTrophyProperty(trophy, user.getId()).setUserId(user.getId());
		return false;
	}

	/**
	 * @see {@link AwardDao#assignToProject(Award, ScoreUser)	
	 */
	protected boolean assignToProject(Trophy trophy, ScoreProject project) {
		Property property = getEarnedTrophyProperty(trophy, project.getId());
		if (property.getResourceId() == null || property.getValue() == null) {
			property.setResourceId(project.getId());
			property.setValue(Long.toString(new Date().getTime()));
			getSession().save(property);
			return true;
		}
		LOG.warn(trophy + " is Already Assigned to " + project.getName());
		return false;
	}

	/**
	 * Retrieves the {@link Property} from the properties table in the database
	 * that assigns the Trophy to the {@link Project} with the given Id.
	 * 
	 * 
	 * @param name
	 * @return the property that assigns the TitleCup.
	 */
	// TODO update javadoc
	public Property getEarnedTrophyProperty(Trophy trophy, Integer projectId) {
		LOG.beginMethod("Getting " + entityTypeKey() + " Property");
		String key = this.entityTypeKey() + "=" + trophy.getName();
		Property property = getSession().getSingleResult(Property.class, "key",
				key, "resourceId", projectId);
		if (property == null) {
			property = new Property(key, null, null);
		}
		LOG.endMethod();
		return property;
	}

	/**
	 * @see {@link AwardDao#getAssignedFromUser(Award, ScoreUser)}
	 */
	@Override
	protected Trophy getAssignedFromUser(Trophy trophy, ScoreUser user) {
		List<Property> properties = getSession().getResults(Property.class,
				"userId", user.getId());
		return getCupFromProperties(trophy, properties);
	}

	/**
	 * Retrieves the {@link TitleCup} if it has been assigned to the project. If
	 * it has not been assigned, {@code null} is returned.
	 * 
	 * @see {@link AwardDao#getAssignedFromProject(Award, ScoreProject)}
	 */
	@Override
	protected Trophy getAssignedFromProject(Trophy trophy, ScoreProject project) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", project.getId());
		return getCupFromProperties(trophy, properties);
	}

	/**
	 * @see {@link AwardDao#getAllAssignedFromUser(ScoreUser)}
	 */
	@Override
	protected AwardSet<Trophy> getAllAssignedFromUser(ScoreUser user) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", user.getId());
		return getAllCupsFromProperties(properties);
	}

	/**
	 * Gets all the {@link TitleCup}s earned by a {@link Project}.
	 * 
	 * @see AwardDao#getAllAssignedFromProject(ScoreProject)
	 */
	@Override
	protected AwardSet<Trophy> getAllAssignedFromProject(ScoreProject project) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", project.getId());
		return getAllCupsFromProperties(properties);
	}

	/**
	 * Unassigns the {@link Trophy} from the {@link Project}.
	 */
	public boolean unassignFromProject(Trophy trophy, ScoreProject project) {
		if (trophy != null && project != null) {
			Property property = getEarnedTrophyProperty(trophy, project.getId());
			if (property.getResourceId() != null) {
				getSession().remove(property);
				return true;
			}
		}
		return false;
	}

	/**
	 * @see {@link AwardDao#get(String)}
	 */
	@Override
	public Trophy get(String name) {
		// TODO implement
		return null;
	}

	/**
	 * @see {@link AwardDao#create(Award)}
	 */
	// TODO: this method should go
	@Override
	public Trophy create(Trophy trophy) {
		LOG.beginMethod("Creating a new trophy: " + trophy.getName());
		propertyDao.create("sonar.score.Trophy:" + trophy.getName(), trophy.getCriteria());
		LOG.endMethod();
		return trophy;
	}

	/**
	 * @see {@link AwardDao#update(Award)}
	 */
	@Override
	public Trophy update(Trophy trophy) {
		// TODO implement
		return null;
	}

	private Trophy getCupFromProperties(Trophy trophy, List<Property> properties) {
		retainOnlyTrophies(properties);
		for (Property property : properties) {
			if (property.getKey().contains(trophy.getName())) {
				return get(trophy);
			}
		}
		return null;
	}

	private AwardSet<Trophy> getAllCupsFromProperties(List<Property> properties) {
		retainOnlyTrophies(properties);
		AwardSet<Trophy> trophies = new AwardSet<Trophy>();
		for (Property property : properties) {
			String cupName = property.getKey().split(":")[1];
			trophies.add(get(cupName));
		}
		return trophies;
	}

	private List<Property> retainOnlyTrophies(List<Property> properties) {
		for (int i = 0; i < properties.size(); i++) {
			while (properties.get(i).getKey().contains(TROPHY)) {
				properties.remove(i);
			}
		}
		return properties;
	}

	@Override
	protected String entityTypeKey() {
		return TROPHY;
	}

	@Override
	protected AwardParser<Trophy> makeParser(Property property) {
		return new TrophyParser(getSession(), property.getKey(),
				property.getValue());
	}
}
