/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.database.model.ResourceModel;

/**
 * Test Class for {@link ScoreProject}.
 * 
 * @author JDunn
 */
public class ScoreProjectTest {

	private static final String SCOPE = "scope";
	private static final String KEY = "key";
	private static final String QUALIFIER = "qualifier";
	private static final Integer ROOTID = 1;
	private static final String NAME = "name";
	ScoreProject testProject;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testProject = new ScoreProject(SCOPE, KEY, QUALIFIER, ROOTID, NAME);
	}

	/**
	 * Test method for {@link ScoreProject#ScoreProject(String)}. Asserts that
	 * the constructor sets the appropriate fields.
	 */
	@Test
	public void testScoreProjectString() {
		testProject = new ScoreProject(KEY);
		assertEquals(null, testProject.getScope());
		assertEquals(KEY, testProject.getKey());
		assertEquals(null, testProject.getQualifier());
		assertEquals(null, testProject.getRootId());
		assertEquals(null, testProject.getName());
	}

	/**
	 * Test method for {@link ScoreProject#ScoreProject(String, String, String)}
	 * . Asserts that the constructor sets the appropriate fields.
	 */
	@Test
	public void testScoreProjectStringStringString() {
		assertEquals(SCOPE, testProject.getScope());
		assertEquals(KEY, testProject.getKey());
		assertEquals(QUALIFIER, testProject.getQualifier());
		assertEquals(ROOTID, testProject.getRootId());
		assertEquals(NAME, testProject.getName());
	}

	/**
	 * Test method for {@link ScoreProject#ScoreProject(ResourceModel)} .
	 * Asserts that the constructor sets the appropriate fields.
	 */
	@Test
	public void testScoreProjectResourceModel() {
		ResourceModel resourceModel = new ResourceModel(SCOPE, KEY, QUALIFIER,
				ROOTID, NAME);
		testProject = new ScoreProject(resourceModel);
		assertEquals(SCOPE, testProject.getScope());
		assertEquals(KEY, testProject.getKey());
		assertEquals(QUALIFIER, testProject.getQualifier());
		assertEquals(ROOTID, testProject.getRootId());
		assertEquals(NAME, testProject.getName());
	}

	/**
	 * Test method for {@link ScoreProject#getUniqueId()}. Asserts that
	 * {@code getUniqueId()} gets the key.
	 */
	@Test
	public void testGetUniqueId() {
		assertEquals(testProject.getKey(), testProject.getUniqueId());
	}

}
