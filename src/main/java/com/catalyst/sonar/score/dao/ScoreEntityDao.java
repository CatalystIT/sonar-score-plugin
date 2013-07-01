/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;
import org.sonar.jpa.dao.BaseDao;

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
public abstract class ScoreEntityDao<E extends ScoreEntity> extends BaseDao {

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
	 * Retrieves all the {@code ScoreEntiti}es of type {@code e} in the
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
	public abstract boolean create(E entity);

	/**
	 * Updates a {@link ScoreEntity} in the database. Returns {@code true} if
	 * successful and {@code false} if not.
	 * 
	 * @param entity
	 * @return
	 */
	public abstract boolean update(E entity);

}
