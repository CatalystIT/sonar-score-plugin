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

import static com.catalyst.sonar.score.ScorePlugin.TITLECUP;
import static com.catalyst.sonar.score.log.Logger.LOG;

import java.util.Date;
import java.util.List;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;
import com.catalyst.sonar.score.api.TitleCup;

/**
 * The {@link TitleCupDao} class implements methods from {@link AwardDao}{@code <}
 * {@link TitleCup}{@code >} to specifically retrieve and/or manipulate
 * {@link TitleCup} information in the database.
 * 
 * @author JDunn
 * 
 */
public class TitleCupDao extends AwardDao<TitleCup> {

	/**
	 * @param session
	 */
	public TitleCupDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * @see {@link TitleCupDao#assignToProject(Award, ScoreUser)	
	 */
	public boolean assign(TitleCup cup, ScoreProject project) {
		return assignToProject(cup, project);
	}

	/**
	 * @see {@link AwardDao#assignToUser(Award, ScoreUser)	
	 */
	@Override
	protected boolean assignToUser(TitleCup cup, ScoreUser user) {
		getTitleCupProperty(cup.getName()).setUserId(user.getId());
		return false;
	}

	/**
	 * @see {@link AwardDao#assignToProject(Award, ScoreUser)	
	 */
	protected boolean assignToProject(TitleCup cup, ScoreProject project) {
		LOG.beginMethod("assignToProject");
		Integer projectId = (project != null) ? project.getId() : null;
		String cupName = (cup != null) ? cup.getName() : null;
		LOG.log("Cup name = " + cupName + ", ResourceId = " + projectId);
		Property property = getTitleCupProperty(cupName);
		LOG.log(property);
		property.setResourceId(projectId);
		LOG.log(property);
		property.setValue(Long.toString(new Date().getTime()));
		LOG.log(property);
		getSession().save(property);
		LOG.endMethod();
		return true;
	}

	/**
	 * Retrieves the {@link Property} from the properties table in the database
	 * that assigns the TitleCup to a {@link Project}.
	 * 
	 * @param name
	 * @return the property that assigns the TitleCup.
	 */
	public Property getTitleCupProperty(String name) {
		LOG.beginMethod("Getting " + entityTypeKey() + " Property");
		String key = this.entityTypeKey() + "=" + name;
		Property property = getSession().getSingleResult(Property.class, "key",
				key);
		LOG.log(property);
		if (property == null) {
			property = new Property(key, null, null);
		}
		LOG.log(property).endMethod();
		return property;
	}

	/** 
	 * @see {@link AwardDao#getAssignedFromUser(Award, ScoreUser)}
	 */
	@Override
	protected TitleCup getAssignedFromUser(TitleCup cup, ScoreUser user) {
		List<Property> properties = getSession().getResults(Property.class,
				"userId", user.getId());
		return getCupFromProperties(cup, properties);
	}

	/**
	 * Retrieves the {@link TitleCup} if it has been assigned to the project. If
	 * it has not been assigned, {@code null} is returned.
	 * 
	 * @see {@link AwardDao#getAssignedFromProject(Award, ScoreProject)}
	 */
	@Override
	protected TitleCup getAssignedFromProject(TitleCup cup, ScoreProject project) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", project.getId());
		return getCupFromProperties(cup, properties);
	}

	/** 
	 * @see AwardDao#getAllAssignedFromUser(ScoreUser)
	 */
	@Override
	protected AwardSet<TitleCup> getAllAssignedFromUser(ScoreUser user) {
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
	protected AwardSet<TitleCup> getAllAssignedFromProject(ScoreProject project) {
		List<Property> properties = getSession().getResults(Property.class,
				"resourceId", project.getId());
		return getAllCupsFromProperties(properties);
	}

	/**
	 * @see {@link AwardDao#get(String)}
	 */
	@Override
	public TitleCup get(String name) {
		// TODO implement
		return null;
	}

	/**
	 * @see {@link AwardDao#create(Award)}
	 */
	@Override
	public TitleCup create(TitleCup cup) {
		LOG.beginMethod("creating a new cup: " + cup.getName());
		Property property = new Property("sonar.score.TitleCup:"
				+ cup.getName(), null, null);
		getSession().save(property);
		LOG.endMethod();
		return cup;
	}

	/**
	 * @see {@link AwardDao#update(Award)}
	 */
	@Override
	public TitleCup update(TitleCup cup) {
		// TODO implement
		return null;
	}

	private TitleCup getCupFromProperties(TitleCup cup,
			List<Property> properties) {
		retainOnlyTitleCups(properties);
		for (Property property : properties) {
			if (property.getKey().contains(cup.getName())) {
				return get(cup);
			}
		}
		return null;
	}

	private AwardSet<TitleCup> getAllCupsFromProperties(
			List<Property> properties) {
		retainOnlyTitleCups(properties);
		AwardSet<TitleCup> cups = new AwardSet<TitleCup>();
		for (Property property : properties) {
			String cupName = property.getKey().split(":")[1];
			cups.add(get(cupName));
		}
		return cups;
	}

	private List<Property> retainOnlyTitleCups(List<Property> properties) {
		for (int i = 0; i < properties.size(); i++) {
			while (properties.get(i).getKey().contains(TITLECUP)) {
				properties.remove(i);
			}
		}
		return properties;
	}

	@Override
	protected String entityTypeKey() {
		return TITLECUP;
	}

	@Override
	protected AwardParser<TitleCup> makeParser(Property property) {
		return new TitleCupParser(getSession(), property.getKey(),
				property.getValue());
	}
}
