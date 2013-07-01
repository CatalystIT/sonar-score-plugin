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
import com.catalyst.sonar.score.api.ScoreProject;
import com.catalyst.sonar.score.api.ScoreUser;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * Test Class for {@link AwardDao}.
 * 
 * @author JDunn
 */
public class AwardDaoTest {

	private static class NewAward extends Award {
		
	}
	
	private static class NewAwardDao extends AwardDao<NewAward> {

		public NewAwardDao(DatabaseSession session) {
			super(session);
		}

		protected boolean assignToUser(NewAward award, ScoreUser user) {
			return true;
		}

		protected boolean assignToProject(NewAward award, ScoreProject project) {
			return true;
		}

		public NewAward get(String uniqueId) {
			return null;
		}

		public SearchableHashSet<NewAward> getAll() {
			return null;
		}

		public boolean create(NewAward entity) {
			return false;
		}

		public boolean update(NewAward entity) {
			return false;
		}
		
	}
	
	private DatabaseSession mockSession;
	private AwardDao<NewAward> testDao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockSession = mock(DatabaseSession.class);
		testDao = new NewAwardDao(mockSession);
		
	}

	/**
	 * Test method for {@link AwardDao#AwardDao(DatabaseSession)}.
	 */
	@Test
	public void testAwardDao() {
		assertEquals(mockSession, testDao.getSession());
	}
	
	/**
	 * Test method for {@link AwardDao#assign(Award, ReceiverScoreEntity)}.
	 */
	@Test
	public void testAssign_User_true() {
		assertTrue(testDao.assign(new NewAward(), new ScoreUser()));
	}
	
	/**
	 * Test method for {@link AwardDao#assign(Award, ReceiverScoreEntity)}.
	 */
	@Test
	public void testAssign_Project_true() {
		assertTrue(testDao.assign(new NewAward(), new ScoreProject("hi")));
	}
	
	/**
	 * Test method for {@link AwardDao#assign(Award, ReceiverScoreEntity)}.
	 */
	@Test
	public void testAssign_Neither_false() {
		assertFalse(testDao.assign(new NewAward(), new ReceiverScoreEntity() {

			public String getUniqueId() {
				return null;
			}
			
		}));
	}

}
