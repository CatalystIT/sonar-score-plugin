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
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Resource;

import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * Accesses Properties in the Properties table in the database.
 * 
 * @author JDunn
 *
 */
public class PropertyDao extends SonarModelDao<Property> {
	
	public static final String POINTS_DISABLED = "sonar.score.PointsDisabled";
	private static final String RESOURCE_ID = "resourceId";

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public PropertyDao(DatabaseSession session) {
		super(session);
	}
	
	/**
	 * Sets the value of the property.
	 * @param key
	 * @param value
	 * @return the property after it's value is set
	 */
	public Property setValue(String key, Object value) {
		Property property = get(key);
		property.setValue(value.toString());
		return getSession().save(property);
	}
	
	/**
	 * Gets the {@link Property} with the given key and resource id.
	 * @param key
	 * @param resource
	 * @return the Property
	 */
	public Property getForResource(String key, @SuppressWarnings("rawtypes") Resource resource) {
		return get(key, RESOURCE_ID, resource.getId());
	}
	
	/**
	 * Gets the {@link Property} with the given key where the resource id is null.
	 * @param key
	 * @param resource
	 * @return the Property
	 */
	public Property getForGlobal(String key) {
		return get(key, RESOURCE_ID, null);
	}
	
	/**
	 * @see {@link SonarModelDao#create(String, String)}
	 */
	@Override
	public Property create(String key, String value) {
		Property property = new Property(key, value);
		return getSession().save(property);
	}

	/**
	 * @see {@link SonarModelDao#entityClass()}
	 * @return {@code Property.class}
	 */
	@Override
	protected Class<Property> entityClass() {
		return Property.class;
	}

	@Override
	public Property get(Property entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchableHashSet<Property> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Property update(Property entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
