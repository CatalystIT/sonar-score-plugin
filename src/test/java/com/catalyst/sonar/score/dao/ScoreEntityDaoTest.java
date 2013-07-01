/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.api.ScoreEntity;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * Test Class for {@link ScoreEntityDao}.
 * 
 * @author JDunn
 */
public class ScoreEntityDaoTest {
	
	private static final String UNIQUEID = "Unique!";
	
	private static class SEntity implements ScoreEntity {

		public String getUniqueId() {
			return UNIQUEID;
		}
		
	}
	private static final SEntity ENTITY = new ScoreEntityDaoTest.SEntity();
	
	private static class SEDao extends ScoreEntityDao<SEntity> {

		public SEDao(DatabaseSession session) {
			super(session);
		}

		public SEntity get(String uniqueId) {
			if(uniqueId.equals(UNIQUEID)) {
				return ENTITY;
			}
			return null;
		}

		public SearchableHashSet<SEntity> getAll() {
			return null;
		}

		public boolean create(SEntity entity) {
			return false;
		}

		@Override
		public boolean update(SEntity entity) {
			return false;
		}
		
	}
	
	private DatabaseSession mockSession;
	private ScoreEntityDao<SEntity> testDao;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockSession = mock(DatabaseSession.class);
		testDao = new SEDao(mockSession);
		
	}

	/**
	 * Test method for {@link ScoreEntityDao#get(ScoreEntity)}.
	 */
	@Test
	public void testGetE() {
		assertEquals(ENTITY, testDao.get(ENTITY));
	}
	
	/**
	 * Test method for {@ScoreEntityDao(DatabaseSession)}.
	 */
	@Test
	public void testAssignableScoreEntityDao() {
		assertEquals(mockSession, testDao.getSession());
	}

}
