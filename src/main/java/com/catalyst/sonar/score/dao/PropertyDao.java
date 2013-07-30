/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

/**
 * Accesses Properties in the Properties table in the database.
 * 
 * @author JDunn
 *
 */
public class PropertyDao extends SonarEntityDao<Property> {

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
	 * @see com.catalyst.sonar.score.dao.SonarEntityDao#entityClass()
	 * @return {@code Property.class}
	 */
	@Override
	protected Class<Property> entityClass() {
		return Property.class;
	}

}
