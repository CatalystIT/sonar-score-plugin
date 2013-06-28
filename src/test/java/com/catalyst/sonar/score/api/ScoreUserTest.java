/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.sonar.api.database.model.User;

/**
 * @author James
 *
 */
public class ScoreUserTest {
	
	public static final String NAME = "Jacob";
	public static final String EMAIL = "JBoone@CatalystITServices.com";
	public static final String LOGIN = "JBoone";
	public static final String DESCRIPTION = "I'm an imaginary developer at Catalyst.";
	
	private ScoreUser testUser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testUser = new ScoreUser(NAME, EMAIL, LOGIN);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreUser#ScoreUser()}.
	 * Asserts that a <code>ScoreUser</code> is an instance of {@link org.sonar.api.database.model.User}.
	 */
	@Test
	public void testScoreUser() {
		testUser = new ScoreUser();
		assertTrue(testUser instanceof User);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreUser#ScoreUser(java.lang.String, java.lang.String, java.lang.String)}.
	 * Tests that the three-arg constructor sets fields appropriately.
	 */
	@Test
	public void testScoreUserStringStringString() {
		assertEquals(NAME, testUser.getName());
		assertEquals(EMAIL, testUser.getEmail());
		assertEquals(LOGIN, testUser.getLogin());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreUser#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
		testUser.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, testUser.getDescription());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreUser#setDescription(java.lang.String)}.
	 * Asserts that setter sets description.
	 */
	@Test
	public void testSetDescription() {
		testUser.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, testUser.getDescription());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreUser#getUniqueId()}.
	 * Asserts that the uniqueId equals the login.
	 */
	@Test
	public void testGetUniqueId() {
		assertEquals(testUser.getLogin(), testUser.getUniqueId());
	}

}
