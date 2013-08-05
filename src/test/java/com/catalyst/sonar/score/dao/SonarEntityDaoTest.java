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
import org.sonar.api.database.DatabaseSession;

import com.catalyst.sonar.score.ScoreTest;
import com.catalyst.sonar.score.api.SearchableHashSet;

/**
 * @author JDunn
 * 
 */
public class SonarEntityDaoTest extends ScoreTest {

	static final String KEY = "Greeting";
	static final String VALUE = "Hi There!";
	static final Integer TEST_INT = 1;
	static final List<Integer> TEST_LIST = new ArrayList<Integer>();

	static String testValue;

	SonarEntityDao<Integer> testDao;
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
		testDao = new SonarEntityDao<Integer>(mockSession) {

			public Integer create(String key, String value) {
				testValue = value;
				return TEST_INT;
			}

			protected Class<Integer> entityClass() {
				return Integer.class;
			}

			public Integer get(Integer entity) {
				return null;
			}

			public SearchableHashSet<Integer> getAll() {
				return null;
			}

			public Integer update(Integer entity) {
				return null;
			}
		};
	}

	/**
	 * Test method for {@link SonarEntityDao#SonarEntityDao(DatabaseSession)}.
	 */
	@Test
	public void testSonarEntityDao() {
		assertSame(mockSession, testDao.getSession());
	}

	/**
	 * Test method for {@link SonarEntityDao#get(String)}.
	 */
	@Test
	public void testGet() {
		when(
				mockSession.getSingleResult(Integer.class, testDao.keyLabel(),
						KEY)).thenReturn(TEST_INT);
		assertSame(TEST_INT, testDao.get(KEY));
	}

	/**
	 * Test method for {@link SonarEntityDao#getAll(String)}.
	 */
	@Test
	public void testGetAll() {
		when(mockSession.getResults(Integer.class, testDao.keyLabel(), KEY))
				.thenReturn(TEST_LIST);
		assertSame(TEST_LIST, testDao.getAll(KEY));
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.dao.SonarEntityDao#create(java.lang.Object)}
	 * .
	 */
	@Test
	public void testCreateE() {
		testDao.create(TEST_INT);
		verify(mockSession).save(TEST_INT);
	}

	/**
	 * Test method for {@link SonarEntityDao#create(String)}. Tests that it
	 * called {@link SonarEntityDao#create(String, String)} with parameters KEY and null.
	 */
	@Test
	public void testCreateString() {
		testDao.create(KEY);
		assertNull(testValue);
	}

	/**
	 * Test method for
	 * {@link SonarEntityDao#create(String, Object)}
	 * .
	 */
	@Test
	public void testCreateStringObject() {
		when(mockObject.toString()).thenReturn(VALUE);
		assertSame(TEST_INT, testDao.create(KEY, mockObject));
		assertSame(VALUE, testValue);
	}
	
	/**
	 * Test method for
	 * {@link SonarEntityDao#create(String, Object)}
	 * .
	 */
	@Test
	public void testCreateStringObject_ObjectIsNull() {
		mockObject = null;
		assertSame(TEST_INT, testDao.create(KEY, mockObject));
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
