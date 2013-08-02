/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;
import org.sonar.api.resources.Resource;

/**
 * Accesses Properties in the Properties table in the database.
 * 
 * @author JDunn
 *
 */
public class PropertyDao extends SonarEntityDao<Property> {
	
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
	 * @see {@link SonarEntityDao#create(String, String)}
	 */
	@Override
	public Property create(String key, String value) {
		Property property = new Property(key, value);
		return getSession().save(property);
	}

	/**
	 * @see {@link SonarEntityDao#entityClass()}
	 * @return {@code Property.class}
	 */
	@Override
	protected Class<Property> entityClass() {
		return Property.class;
	}

}
