/**
 * 
 */
package com.catalyst.sonar.score.dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.database.configuration.Property;

import com.catalyst.sonar.score.ScoreTest;

/**
 * @author JDunn
 *
 */
public class PropertyDaoTest extends ScoreTest {
	
	static final String KEY = "Greeting";
	static final String VALUE = "Hi There!";
	
	PropertyDao testDao;
	Property testProperty;
	DatabaseSession mockSession;
	Object mockObject;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		mockSession = mock(DatabaseSession.class);
		mockObject = mock(Object.class);
		testDao = new PropertyDao(mockSession);
		testProperty = new Property();
	}

	/**
	 * Test method for {@link PropertyDao#PropertyDao(DatabaseSession)}.
	 */
	@Test
	public void testPropertyDao() {
		assertSame(mockSession, testDao.getSession());
	}

	/**
	 * Test method for {@link PropertyDao#setValue(String, Object)}.
	 */
	@Test
	public void testSetValueStringObject() {
		when(mockObject.toString()).thenReturn(VALUE);
		when(mockSession.getSingleResult(Property.class, testDao.keyLabel(), KEY)).thenReturn(testProperty);
		testDao.setValue(KEY, mockObject);
		verify(mockSession).save(testProperty);
		assertEquals(VALUE, testProperty.getValue());
		
	}

	/**
	 * Test method for {@link PropertyDao#create(String, String)}.
	 */
	@Test
	public void testCreateStringString() {
		when(mockSession.save(any(Property.class))).thenReturn(testProperty);
		testProperty = testDao.create(KEY, VALUE);
		assertEquals(testProperty, testDao.create(KEY, VALUE));
	}

	/**
	 * Test method for {@link PropertyDao#entityClass()}.
	 */
	@Test
	public void testEntityClass() {
		assertEquals(Property.class, testDao.entityClass());
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

