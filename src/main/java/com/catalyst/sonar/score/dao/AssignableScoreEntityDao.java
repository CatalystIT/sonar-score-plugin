/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.AssignableScoreEntity;
import com.catalyst.sonar.score.api.ReceiverScoreEntity;
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
	public abstract boolean assign(A assignable, ReceiverScoreEntity receiver);

	/**
	 * Retrieves all the {@code AssignableScoreEntiti}es of type {@code A} that
	 * have been assigned to the receiver.
	 * 
	 * @param receiver
	 * @return
	 */
	public abstract SearchableHashSet<A> getAllAssigned(
			ReceiverScoreEntity receiver);

	/**
	 * Retrieves the {@link AssignableScoreEntity} of type {@code A} if it has
	 * been assigned to the receiver. If it has not been assigned, {@code null}
	 * is returned.
	 * 
	 * @param assignable
	 * @param receiver
	 * @return
	 */
	public abstract A getAssigned(A assignable, ReceiverScoreEntity receiver);

}
