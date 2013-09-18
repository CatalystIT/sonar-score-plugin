/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sonar.api.database.BaseIdentifiable;
import org.sonar.api.database.DatabaseSession;

import com.catalyst.commons.util.SearchableHashSet;
import com.catalyst.sonar.score.ScoreTest;

/**
 * @author JDunn
 * 
 */
public class SonarEntityDaoTest extends ScoreTest {

	static final String KEY = "Greeting";
	static final String VALUE = "Hi There!";
	static final SonarEntity TEST_ENTITY = new SonarEntity();
	static final List<SonarEntity> TEST_LIST = new ArrayList<SonarEntity>();
	static class SonarEntity extends BaseIdentifiable {
		
	}

	static String testValue;

	SonarModelDao<SonarEntity> testDao;
	DatabaseSession mockSession;
	Object mockObject;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testValue = "";
		mockSession = mock(DatabaseSession.class);
		mockObject = mock(Object.class);
		testDao = new SonarModelDao<SonarEntity>(mockSession) {

			public SonarEntity create(String key, String value) {
				testValue = value;
				return TEST_ENTITY;
			}

			protected Class<SonarEntity> entityClass() {
				return SonarEntity.class;
			}

			public SonarEntity get(SonarEntity entity) {
				return null;
			}

			public SearchableHashSet<SonarEntity> getAll() {
				return null;
			}

			public SonarEntity update(SonarEntity entity) {
				return null;
			}
		};
	}

	/**
	 * Test method for {@link SonarModelDao#SonarEntityDao(DatabaseSession)}.
	 */
	@Test
	public void testSonarEntityDao() {
		assertSame(mockSession, testDao.getSession());
	}

	/**
	 * Test method for {@link SonarModelDao#get(String)}.
	 */
	@Test
	public void testGet() {
		when(
				mockSession.getSingleResult(SonarEntity.class, testDao.keyLabel(),
						KEY)).thenReturn(TEST_ENTITY);
		assertSame(TEST_ENTITY, testDao.get(KEY));
	}

	/**
	 * Test method for {@link SonarModelDao#getAll(String)}.
	 */
	@Test
	public void testGetAll() {
		when(mockSession.getResults(SonarEntity.class, testDao.keyLabel(), KEY))
				.thenReturn(TEST_LIST);
		assertSame(TEST_LIST, testDao.getAll(KEY));
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.dao.SonarModelDao#create(java.lang.Object)}
	 * .
	 */
	@Test
	public void testCreateE() {
		testDao.create(TEST_ENTITY);
		verify(mockSession).save(TEST_ENTITY);
	}

	/**
	 * Test method for {@link SonarModelDao#create(String)}. Tests that it
	 * called {@link SonarModelDao#create(String, String)} with parameters KEY and null.
	 */
	@Test
	public void testCreateString() {
		testDao.create(KEY);
		assertNull(testValue);
	}

	/**
	 * Test method for
	 * {@link SonarModelDao#create(String, Object)}
	 * .
	 */
	@Test
	public void testCreateStringObject() {
		when(mockObject.toString()).thenReturn(VALUE);
		assertSame(TEST_ENTITY, testDao.create(KEY, mockObject));
		assertSame(VALUE, testValue);
	}
	
	/**
	 * Test method for
	 * {@link SonarModelDao#create(String, Object)}
	 * .
	 */
	@Test
	public void testCreateStringObject_ObjectIsNull() {
		mockObject = null;
		assertSame(TEST_ENTITY, testDao.create(KEY, mockObject));
		assertNull(testValue);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		turnOffLogger();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		turnOnLogger();
	}

}
