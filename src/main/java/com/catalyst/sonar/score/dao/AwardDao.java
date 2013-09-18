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

import java.util.ArrayList;
import java.util.List;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.commons.util.SearchableHashSet;
import com.catalyst.sonar.score.api.AssignableScoreEntity;
import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.ReceiverEntity;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;

/**
 * The {@link AwardsDao}{@code <A>} class defines methods, mostly abstract,
 * which will interact with {@code Award}s of type {@code A} and their
 * representation as properties in the properties table in the database.
 * 
 * @author JDunn
 * 
 */
public abstract class AwardDao<A extends Award> extends
		AssignableScoreEntityDao<A> {

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public AwardDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * Gets all the Awards of type A from the database.
	 */
	@Override
	public AwardSet<A> getAll() {
		List<Property> properties = getAllAsProperties();
		if (properties == null || properties.size() == 0) {
			return null;
		}
		AwardSet<A> awards = new AwardSet<A>();
		for (Property property : properties) {
			A award = makeParser(property).parse();
			awards.add(award);
		}
		return awards;
	}

	/**
	 * Gets all the Properties from the Property Table that declare Awards.
	 * @return
	 */
	protected List<Property> getAllAsProperties() {
		List<Property> allProperties = getSession().getResults(Property.class);
		List<Property> properties = new ArrayList<Property>();
		for (Property property : allProperties) {
			if (property.getKey().contains(entityTypeKey() + ":")) {
				properties.add(property);
			}
		}
		return properties;
	}

	/**
	 * @see {@link AssignableScoreEntityDao#assign(AssignableScoreEntity, ReceiverScoreEntity)}
	 */
	public boolean assign(A award, ReceiverEntity receiver) {
		if (receiver instanceof ScoreProject) {
			return assignToProject(award, (ScoreProject) receiver);
		} else if (receiver instanceof ScoreUser) {
			return assignToUser(award, (ScoreUser) receiver);
		}
		return false;
	}

	/**
	 * Retrieves all the {@link Award}s of type {@code A} that have been
	 * assigned to the receiver.
	 * 
	 * @param award
	 * @param receiver
	 * @return
	 */
	public SearchableHashSet<A> getAllAssigned(ReceiverEntity receiver) {
		if (receiver instanceof ScoreProject) {
			return getAllAssignedFromProject((ScoreProject) receiver);
		} else if (receiver instanceof ScoreUser) {
			return getAllAssignedFromUser((ScoreUser) receiver);
		}
		return null;
	}

	/**
	 * Retrieves the {@link Award} of type {@code A} if it has been assigned to
	 * the receiver. If it has not been assigned, {@code null} is returned.
	 * 
	 * @param award
	 * @param receiver
	 * @return
	 */
	public A getAssigned(A award, ReceiverEntity receiver) {
		return null;
	}

	/**
	 * Awards an {@link Award} to the given {@link ScoreUser}. Returns
	 * {@code true} if successful and {@code false} if not.
	 * 
	 * @param award
	 * @param user
	 * @return
	 */
	protected abstract boolean assignToUser(A award, ScoreUser user);

	/**
	 * Awards an {@code Award} to the given {@code ScoreProject} and records the
	 * current time as millis to show when the {@code Award} was earned. Returns
	 * {@code true} if successful and {@code false} if not.
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	protected abstract boolean assignToProject(A award, ScoreProject project);
	


	/**
	 * Retrieves the {@link Award} of type {@code A} if it has been assigned to
	 * the user. If it has not been assigned, {@code null} is returned.
	 * 
	 * @param award
	 * @param user
	 * @return
	 */
	protected abstract A getAssignedFromUser(A award, ScoreUser user);

	/**
	 * Retrieves the {@link Award} of type {@code A} if it has been assigned to
	 * the project. If it has not been assigned, {@code null} is returned.
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	protected abstract A getAssignedFromProject(A award, ScoreProject project);

	/**
	 * Retrieves all the {@link Award}s of type {@code A} that have been
	 * assigned to the user.
	 * 
	 * @param award
	 * @param user
	 * @return
	 */
	protected abstract AwardSet<A> getAllAssignedFromUser(ScoreUser user);

	/**
	 * Retrieves all the {@link Award}s of type {@code A} that have been
	 * assigned to the project.
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	protected abstract AwardSet<A> getAllAssignedFromProject(
			ScoreProject project);

	/**
	 * 
	 * @return the String used as the first part of the property key for the
	 *         {@link Award} in the properties table. The entire key is like so:
	 *         {@code [AWARD_TYPE_KEY]:[AWARD_NAME]}. The {@code AWARD_TYPE_KEY}
	 *         will be {@code sonar.score.[AWARD_TYPE]}. For example, in the
	 *         {@link TitleCup} implementation of {@link Award}, this method
	 *         will return "{@code sonar.score.TitleCup}".
	 */
	protected abstract String entityTypeKey();

	/**
	 * Makes an {@link AwardParser} to parse the given property.
	 * 
	 * @param property
	 * @return
	 */
	protected abstract AwardParser<A> makeParser(Property property);

}
