/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for {@link GroupSet}.
 * 
 * @author JDunn
 */

public class GroupSetTest {

	GroupSet<Group<Member>> testSet;
	Group<Member> group1 = new Group<Member>("Group One");
	Group<Member> group2 = new Group<Member>("Group Two");
	Member m1a = new ScoreProject("one");
	Member m1b = new ScoreProject("two");
	Member m2a = new ScoreProject("three");
	Group<Member> group1Copy = new Group<Member>("Group One");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testSet = new GroupSet<Group<Member>>();
		group1.add(m1a);
		group1Copy.add(m1a);
		group2.add(m2a);
		testSet.add(group1);
	}

	/**
	 * Test method for {@link GroupSet#add(Group)}. Asserts that {@code add()}
	 * returns false if no new {@code Group} is added and no new {@code Member}s
	 * are added to the {@code GroupSet}.
	 */
	@Test
	public void testAddA_AddGroupWithSameNameAndSameMembers_false() {
		assertFalse(testSet.add(group1Copy));
	}

	/**
	 * Test method for {@link GroupSet#add(Group)}. Asserts that {@code add()}
	 * returns true if the {@code Group} passed in as the argument has the same
	 * name as an {@code Group} already in this {@code GroupSet} but also has a
	 * new {@code Member}, and that the {@code Group} with the same name in this
	 * {@code GroupSet} now contains the new {@code Member}.
	 */
	@Test
	public void testAddA_AddGroupWithSameNameAndExtraMember_true() {
		group1Copy.add(m1b);
		assertTrue(testSet.add(group1Copy));
		assertTrue(group1.contains(m1b));
	}

	/**
	 * Test method for {@link GroupSet#add(Group)}. Asserts that {@code add()}
	 * returns true if the {@code Group} passed in as the argument is not
	 * meaningfully equal (that is, does not have an equal name) to any
	 * {@code Group}s already in this {@code GroupSet}, and that this
	 * {@code GroupSet} now contains the new {@code Group}.
	 */
	@Test
	public void testAddA_AddGroupWithDifferentName_true() {
		assertFalse(testSet.contains(group2));
		assertTrue(testSet.add(group2));
		assertTrue(testSet.contains(group2));
	}

}
