/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.api.Award;
import com.catalyst.sonar.score.api.AwardSet;
import com.catalyst.sonar.score.api.ReceiverEntity;
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

		public AwardSet<NewAward> getAll() {
			return null;
		}

		public NewAward create(NewAward entity) {
			return null;
		}

		public NewAward update(NewAward entity) {
			return null;
		}

		@Override
		protected NewAward getAssignedFromUser(NewAward award, ScoreUser user) {
			// TODO Auto-generated method stub
			return null;
		}

		protected NewAward getAssignedFromProject(NewAward award,
				ScoreProject project) {
			return null;
		}

		protected AwardSet<NewAward> getAllAssignedFromUser(ScoreUser user) {
			return null;
		}

		protected AwardSet<NewAward> getAllAssignedFromProject(
				ScoreProject project) {
			return null;
		}

		@Override
		protected String entityTypeKey() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected AwardParser<NewAward> makeParser(Property property) {
			// TODO Auto-generated method stub
			return null;
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
		assertFalse(testDao.assign(new NewAward(), new ReceiverEntity() {

			public String getUniqueId() {
				return null;
			}
			
		}));
	}

}
