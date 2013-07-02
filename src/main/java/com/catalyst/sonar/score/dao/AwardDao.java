/**
 * 
 */
package com.catalyst.sonar.score.dao;

import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.AssignableScoreEntity;
import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.ReceiverScoreEntity;
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * The {@link AwardsDao}{@code <A>} class defines methods, mostly abstract,
 * which will interact with {@code Award}s of type {@code A} and their
 * representation as properties in the properties table in the database.
 * 
 * @author JDunn
 * 
 */
public abstract class AwardDao<A extends Award> extends
		AssignableScoreEntityDao<A> {

	/**
	 * Constructor with a parameter for the session to set the session.
	 */
	public AwardDao(DatabaseSession session) {
		super(session);
	}

	/**
	 * @see {@link AssignableScoreEntityDao#assign(AssignableScoreEntity, ReceiverScoreEntity)}
	 */
	public boolean assign(A award, ReceiverScoreEntity receiver) {
		if (receiver instanceof ScoreProject) {
			return assignToProject(award, (ScoreProject) receiver);
		} else if (receiver instanceof ScoreUser) {
			return assignToUser(award, (ScoreUser) receiver);
		}
		return false;
	}

	/**
	 * Retrieves all the {@link Award}s of type {@code A} that
	 * have been assigned to the receiver.
	 * 
	 * @param award
	 * @param receiver
	 * @return
	 */
	public SearchableHashSet<A> getAllAssigned(ReceiverScoreEntity receiver) {
		if (receiver instanceof ScoreProject) {
			return getAllAssignedFromProject((ScoreProject) receiver);
		} else if (receiver instanceof ScoreUser) {
			return getAllAssignedFromUser((ScoreUser) receiver);
		}
		return null;
	}
	
	/**
	 * Retrieves the {@link Award} of type {@code A} if it has been assigned to
	 * the receiver. If it has not been assigned, {@code null} is returned.
	 * 
	 * @param award
	 * @param receiver
	 * @return
	 */
	public A getAssigned(A award,
			ReceiverScoreEntity receiver) {
		return null;
	}

	/**
	 * Awards an {@link Award} to the given {@link ScoreUser}. Returns
	 * {@code true} if successful and {@code false} if not.
	 * 
	 * @param award
	 * @param user
	 * @return
	 */
	protected abstract boolean assignToUser(A award, ScoreUser user);

	/**
	 * Awards an {@code Award} to the given {@code ScoreProject}. Returns
	 * {@code true} if successful and {@code false} if not.
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	protected abstract boolean assignToProject(A award, ScoreProject project);
	
	/**
	 * Retrieves the {@link Award} of type {@code A} if it has been assigned to
	 * the user. If it has not been assigned, {@code null} is returned.
	 * 
	 * @param award
	 * @param user
	 * @return
	 */
	protected abstract A getAssignedFromUser(A award, ScoreUser user);

	/**
	 * Retrieves the {@link Award} of type {@code A} if it has been assigned to
	 * the project. If it has not been assigned, {@code null} is returned.
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	protected abstract A getAssignedFromProject(A award, ScoreProject project);
	
	/**
	 * Retrieves all the {@link Award}s of type {@code A} that
	 * have been assigned to the user.
	 * 
	 * @param award
	 * @param user
	 * @return
	 */
	protected abstract AwardSet<A> getAllAssignedFromUser(ScoreUser user);

	/**
	 * Retrieves all the {@link Award}s of type {@code A} that
	 * have been assigned to the project.
	 * 
	 * @param project
	 * @param user
	 * @return
	 */
	protected abstract AwardSet<A> getAllAssignedFromProject(ScoreProject project);

}
