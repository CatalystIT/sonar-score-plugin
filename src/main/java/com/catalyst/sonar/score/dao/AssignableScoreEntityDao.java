/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.AssignableScoreEntity;
import com.catalyst.sonar.score.api.ReceiverScoreEntity;

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
	public abstract boolean assign(A assignable, ReceiverScoreEntity receiver);

}
