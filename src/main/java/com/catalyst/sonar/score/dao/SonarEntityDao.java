/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.jpa.dao.BaseDao;

/**
 * 
 * The {@code SonarEntityDao<E>} class defines a dao that specifically access a
 * SonarEntity. What is meant by a SonarEntity is an Entity that is represented
 * in Sonar's database through Hibernate. As a consequence, any implementation
 * of this class should use only classes defined by Sonar that map directly to a
 * table in Sonar's database for generic type {@code <E>}.
 * 
 * @author JDunn
 * 
 */
public abstract class SonarEntityDao<E> extends BaseDao {

	public static final String KEY_LABEL = "key";

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public SonarEntityDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * Gets the Entity of type {@code <E>} from Sonar's database.
	 * 
	 * @param key
	 * @return
	 */
	public E get(String key) {
		return getSession().getSingleResult(entityClass(), keyLabel(), key);
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
	 * @return the name of the column used as the key, or unique identifier, of
	 *         the Entity in Sonar's Database. Usually this is "{@code key}". If
	 *         not, this method needs to be overridden during implementation.
	 */
	protected String keyLabel() {
		return KEY_LABEL;
	}

	/**
	 * When this is implemented, it MUST return the class of the generic type.
	 * For example, if StringDao extends {@code SonarEntityDao<String>}, this
	 * method must return {@code String.class}.
	 * 
	 * @return (GenericParameterType).class
	 */
	protected abstract Class<E> entityClass();

}
