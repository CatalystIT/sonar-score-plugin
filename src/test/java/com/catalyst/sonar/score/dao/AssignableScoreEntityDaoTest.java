/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.ReceiverScoreEntity;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * Test Class for {@link AssignableScoreEntityDao}.
 * 
 * @author JDunn
 */
public class AssignableScoreEntityDaoTest {
	
	private static class ASEDao extends AssignableScoreEntityDao<Award> {

		public ASEDao(DatabaseSession session) {
			super(session);
		}

		public boolean assign(Award assignable,
				ReceiverScoreEntity receiver) {
			return false;
		}

		public Award get(String uniqueId) {
			return null;
		}

		public SearchableHashSet<Award> getAll() {
			return null;
		}

		public boolean create(Award entity) {
			return false;
		}

		public boolean update(Award entity) {
			return false;
		}

		public SearchableHashSet<Award> getAllAssigned(
				ReceiverScoreEntity receiver) {
			return null;
		}

		public Award getAssigned(Award assignable, ReceiverScoreEntity receiver) {
			return null;
		}
		
	}

	private DatabaseSession mockSession;
	private AssignableScoreEntityDao<Award> testDao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockSession = mock(DatabaseSession.class);
		testDao = new ASEDao(mockSession);
		
	}

	/**
	 * Test method for {@AssignableScoreEntityDao(DatabaseSession)}.
	 */
	@Test
	public void testAssignableScoreEntityDao() {
		assertEquals(mockSession, testDao.getSession());
	}

}
