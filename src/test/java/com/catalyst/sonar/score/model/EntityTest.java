/**
 * 
 */
package com.catalyst.sonar.score.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author JDunn
 *
 */
public class EntityTest {
	
	private static final String NAME = "testgroup";
	private ProjectGroup group;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		group = new ProjectGroup(NAME);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.Entity#fileName()}.
	 */
	@Test
	public void testFileName() {
		assertEquals(NAME + ".ProjectGroup", group.fileName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.Entity#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(NAME, group.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.Entity#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		group.setName(NAME + NAME);
		assertEquals(NAME + NAME, group.getName());
	}

}
