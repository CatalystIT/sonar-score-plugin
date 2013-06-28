/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for {@link ProjectGroup}.
 * 
 * @author JDunn
 */
public class ProjectGroupTest {
	
	private static final ScoreProject p1 = new ScoreProject("p1");
	private static final ScoreProject p2 = new ScoreProject("p2");
	private static final String NAME = "name";
	
	private ProjectGroup testGroup;
	private ProjectGroup testGroupWithName;
	private ProjectGroup testGroupWithNameAndMembers;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testGroup = new ProjectGroup();
		testGroupWithName = new ProjectGroup(NAME);
		testGroupWithNameAndMembers = new ProjectGroup(NAME, p1, p2);
	}

	/**
	 * Test method for {@link ProjectGroup#ProjectGroup()}.
	 */
	@Test
	public void testProjectGroup() {
		assertEquals(null, testGroup.getName());
	}

	/**
	 * Test method for {@link ProjectGroup#ProjectGroup(String, ScoreProject[])}.
	 */
	@Test
	public void testProjectGroupStringScoreProjectArray() {
		assertEquals(NAME, testGroupWithName.getName());
	}

	/**
	 * Test method for {@link ProjectGroup#ProjectGroup(String)}.
	 */
	@Test
	public void testProjectGroupString() {
		assertEquals(NAME, testGroupWithNameAndMembers.getName());
		assertTrue(testGroupWithNameAndMembers.contains(p1));
		assertTrue(testGroupWithNameAndMembers.contains(p2));
	}

}
