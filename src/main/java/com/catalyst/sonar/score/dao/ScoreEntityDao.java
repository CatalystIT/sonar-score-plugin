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

import com.catalyst.sonar.score.api.ScoreEntity;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * The {@link ScoreEntityDao} class defines methods, mostly abstract, that will
 * work with a {@link ScoreEntity} of type {@code E} and the database.
 *
 * @param <E>
 * 
 * @author JDunn
 */
public abstract class ScoreEntityDao<E extends ScoreEntity> extends EntityDao<E> {

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public ScoreEntityDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * Returns a {@link ScoreEntity} from the database with the same uniqueId by
	 * calling {@code get(entity.getUniqueId())}.
	 * 
	 * @param entity
	 * @return
	 */
	public E get(E entity) {
		return this.get(entity.getUniqueId());
	}

	/**
	 * Returns a {@link ScoreEntity} from the database with a name equal to the
	 * String name argument.
	 * 
	 * @param name
	 * @return
	 */
	public abstract E get(String uniqueId);

	/**
	 * Retrieves all the {@code ScoreEntiti}es of type {@code E} in the
	 * database.
	 * 
	 * @return
	 */
	public abstract SearchableHashSet<E> getAll();

	/**
	 * Creates a {@link ScoreEntity} in the database. Returns {@code true} if
	 * successful and {@code false} if not.
	 * 
	 * @param entity
	 * @return
	 */
	public abstract E create(E entity);

	/**
	 * Updates a {@link ScoreEntity} in the database. Returns {@code true} if
	 * successful and {@code false} if not.
	 * 
	 * @param entity
	 * @return
	 */
	public abstract E update(E entity);

}
