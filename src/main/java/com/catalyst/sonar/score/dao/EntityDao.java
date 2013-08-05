/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.jpa.dao.BaseDao;

import com.catalyst.sonar.score.api.Entity;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * @author JDunn
 *
 */
public abstract class EntityDao<E> extends BaseDao {

	/**
	 * Constructor with a parameter for the session to set the session.
	 * 
	 * @param session
	 */
	public EntityDao(DatabaseSession session) {
		super(session);
	}
	
	/**
	 * Returns an {@link Entity} from the database meaningfully equal to
	 * the entity argument.
	 * 
	 * @param entity
	 * @return
	 */
	public abstract E get(E entity);
	
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
