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

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.AssignableScoreEntity;
import com.catalyst.sonar.score.api.ReceiverEntity;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * The {@link ScoreEntityDao} class defines methods, mostly abstract, that will
 * work with an {@link AssignableScoreEntity} of type {@code A} and the
 * database.
 * 
 * @param <A>
 * 
 * @author JDunn
 */
public abstract class AssignableScoreEntityDao<A extends AssignableScoreEntity>
		extends ScoreEntityDao<A> {

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public AssignableScoreEntityDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * Assigns an {@link AssignableScoreEntity} to the given
	 * {@link ReceiverScoreEntity}. Returns {@code true} if successful and
	 * {@code false} if not.
	 * 
	 * @param assignable
	 * @param receiver
	 * @return
	 */
	public abstract boolean assign(A assignable, ReceiverEntity receiver);

	/**
	 * Retrieves all the {@code AssignableScoreEntiti}es of type {@code A} that
	 * have been assigned to the receiver.
	 * 
	 * @param receiver
	 * @return
	 */
	public abstract SearchableHashSet<A> getAllAssigned(
			ReceiverEntity receiver);

	/**
	 * Retrieves the {@link AssignableScoreEntity} of type {@code A} if it has
	 * been assigned to the receiver. If it has not been assigned, {@code null}
	 * is returned.
	 * 
	 * @param assignable
	 * @param receiver
	 * @return
	 */
	public abstract A getAssigned(A assignable, ReceiverEntity receiver);

}
