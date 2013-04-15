/**
 * 
 */
package com.catalyst.sonar.score.model;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.web.Filter;

/**
 * @author JDunn
 *
 */
public class ProjectGroupTest {
	public static final String TAG1 = "Happy";
	public static final String TAG2 = "Sad";
	public static final String NAME1 = "EmotiveProjects";
	public static final String NAME2 = "DullProjects";
	
	private ProjectGroup group;
	private Filter filter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		group = new ProjectGroup(NAME1);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#ProjectGroup()}.
	 */
	@Test
	public void testProjectGroup() {
		assertEquals(NAME1, group.getName());
		assertNotNull(group.getTags());
		assertTrue(group.getTags() instanceof HashSet);
		assertNull(group.getFilter());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#add(java.lang.String)}.
	 */
	@Test
	public void testAdd() {
		assertTrue(group.add(TAG1));
		assertTrue(group.contains(TAG1));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#remove(java.lang.String)}.
	 */
	@Test
	public void testRemove() {
		group.add(TAG1);
		group.add(TAG2);
		assertTrue(group.contains(TAG1));
		assertTrue(group.contains(TAG2));
		group.remove(TAG1);
		assertFalse(group.contains(TAG1));
		assertTrue(group.contains(TAG2));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#contains(java.lang.String)}.
	 */
	@Test
	public void testContains() {
		assertFalse(group.contains(TAG1));
		assertFalse(group.contains(TAG2));
		group.add(TAG1);
		assertTrue(group.contains(TAG1));
		assertFalse(group.contains(TAG2));
		group.remove(TAG1);
		assertFalse(group.contains(TAG1));
		assertFalse(group.contains(TAG2));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(NAME1, group.getName());
		group.setName(NAME2);
		assertEquals(NAME2, group.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		assertEquals(NAME1, group.getName());
		group.setName(NAME2);
		assertEquals(NAME2, group.getName());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#getFilter()}.
	 */
	@Test
	public void testGetFilter() {
		assertNull(group.getFilter());
		filter = Filter.create();
		group.setFilter(filter);
		assertEquals(filter, group.getFilter());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.model.ProjectGroup#setFilter(org.sonar.api.web.Filter)}.
	 */
	@Test
	public void testSetFilter() {
		filter = Filter.create();
		group.setFilter(filter);
		assertEquals(filter, group.getFilter());
	}

}
