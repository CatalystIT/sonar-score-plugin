/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for
 * {@link com.catalyst.sonar.score.api.Criterion}.
 * @author JDunn
 */
public class ScoreProjectTest {
	
	private static final String KEY = "key";
	private static final String BRANCH = "branch";
	private static final String NAME = "name";
	ScoreProject testProject;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testProject = new ScoreProject(KEY, BRANCH, NAME);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreProject#ScoreProject(java.lang.String)}.
	 * Asserts that the constructor sets the appropriate fields.
	 */
	@Test
	public void testScoreProjectString() {
		testProject = new ScoreProject(KEY);
		assertTrue(testProject.getKey().contains(KEY));
		assertEquals(null, testProject.getBranch());
		assertEquals(null, testProject.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreProject#ScoreProject(java.lang.String, java.lang.String, java.lang.String)}.
	 * Asserts that the constructor sets the appropriate fields.
	 */
	@Test
	public void testScoreProjectStringStringString() {
		assertTrue(testProject.getKey().contains(KEY));
		assertEquals(BRANCH, testProject.getBranch());
		assertTrue(testProject.getName().contains(NAME));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.ScoreProject#getUniqueId()}.
	 * Asserts that {@code getUniqueId()} gets the key.
	 */
	@Test
	public void testGetUniqueId() {
		assertEquals(testProject.getKey(), testProject.getUniqueId());
	}

}
