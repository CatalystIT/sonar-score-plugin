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

import org.sonar.api.database.BaseIdentifiable;
import org.sonar.api.database.DatabaseSession;

/**
 * 
 * The {@code SonarModelDao<E>} class defines a dao that specifically accesses a
 * Sonar Database Model defined by SonarQube's database API and persited in
 * Sonar's database through Hibernate. As a consequence, any implementation of
 * this class should use only classes defined by Sonar that map directly to a
 * table in Sonar's database for generic type {@code <E>}.
 * 
 * @author JDunn
 * 
 */
public abstract class SonarModelDao<E extends BaseIdentifiable> extends
		EntityDao<E> {

	public static final String KEY_LABEL = "key";

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public SonarModelDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * Gets the first Entity of type {@code <E>} from Sonar's database.
	 * 
	 * @param key
	 * @return
	 */
	public E get(String key) {
		return getSession().getSingleResult(entityClass(), keyLabel(), key);
	}

	/**
	 * Gets a list of all Entities of type {@code <E>} from Sonar's database.
	 * 
	 * @param key
	 * @return
	 */
	public List<E> getAll(String key) {
		return getSession().getResults(entityClass(), keyLabel(), key);
	}

	/**
	 * Creates a record of type {@code <E>} in the database.
	 * 
	 * @param e
	 * @return
	 */
	public E create(E e) {
		return getSession().save(e);
	}

	/**
	 * Creates a {@link ScoreEntity} in the database with the given String as
	 * its key, and {@code null} fields.
	 * 
	 * @param entity
	 * @return
	 */
	public E create(String key) {
		return create(key, null);
	}

	/**
	 * Creates a {@link ScoreEntity} in the database with the String key as its
	 * key and {@code value.toString()} as its value (or relevant field).
	 * 
	 * @param entity
	 * @return
	 */
	public E create(String key, Object value) {
		String valueArg = (value != null) ? value.toString() : null;
		return create(key, valueArg);
	}

	/**
	 * Creates a {@link ScoreEntity} in the database with the first String arg
	 * as its key and the second String arg as its value (or relevant field).
	 * 
	 * @param entity
	 * @return
	 */
	public abstract E create(String key, String value);

	/**
	 * @return the name of the column used as the key, or unique identifier, of
	 *         the Entity in Sonar's Database. Usually this is "{@code key}". If
	 *         not, this method needs to be overridden during implementation.
	 */
	protected String keyLabel() {
		return KEY_LABEL;
	}

	/**
	 * Returns the SonarEntity with the given Key and matching field.
	 * 
	 * @param key
	 * @param criterias
	 * @return the SonarEntity
	 */
	protected E get(String key, String fieldName, Object fieldValue) {
		return getSession().getSingleResult(entityClass(), keyLabel(), key,
				fieldName, fieldValue);
	}

	/**
	 * When this is implemented, it MUST return the class of the generic type.
	 * For example, if StringDao extends {@code SonarModelDao<String>}, this
	 * method must return {@code String.class}.
	 * 
	 * @return (GenericParameterType).class
	 */
	protected abstract Class<E> entityClass();

}
