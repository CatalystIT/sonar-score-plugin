/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for {@link Group}.
 * 
 * @author JDunn
 */
public class GroupTest {

	private static final String GROUP_NAME = "The Group";
	private static final String DIFFERENT_GROUP_NAME = "Another Group";
	private static final String DESCRIPTION = "I'm a Group!";

	private Group<ScoreUser> testGroup;
	private Group<ScoreUser> sameNameGroup;
	private Group<ScoreUser> differentNameGroup;

	private Object referenceToTestGroup;
	private Object nullGroup;
	private Object nullNameGroup;
	private Object notAGroup;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testGroup = new Group<ScoreUser>(GROUP_NAME);
		sameNameGroup = new Group<ScoreUser>(GROUP_NAME);
		testGroup.add(USER1);
		testGroup.add(USER2);
		sameNameGroup.add(USER3);
		sameNameGroup.add(USER4);
		differentNameGroup = new Group<ScoreUser>(DIFFERENT_GROUP_NAME, USER1,
				USER2);

		referenceToTestGroup = testGroup;
		nullNameGroup = new Group<ScoreUser>();
		notAGroup = "I'm Actually A String!";
	}

	/**
	 * Test method for {@link Group#hashCode()}. Asserts that two {@code Group}s
	 * with the same name but different members have the same {@code hashCode}.
	 */
	@Test
	public void testHashCode() {
		assertEquals(testGroup.hashCode(), sameNameGroup.hashCode());
		assertEquals(nullNameGroup.hashCode(),
				new Group<ScoreUser>().hashCode());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#Group()}.
	 * Asserts that a {@code Group} is an instance of {@link SearchableHashSet}.
	 */
	@Test
	public void testGroup() {
		assertTrue(nullNameGroup instanceof SearchableHashSet);
	}

	/**
	 * Test method for
	 * {@link com.catalyst.sonar.score.api.Group#Group(java.lang.String)}.
	 * Asserts that the constructor with the {@code String} argument sets the
	 * name.
	 */
	@Test
	public void testGroupString() {
		assertEquals(GROUP_NAME, testGroup.getName());
	}

	/**
	 * Test method for {@link Group#Group(String, M[])}. Asserts that the
	 * constructor with the {@code String} and member argument sets the name and
	 * populates the members.
	 */
	@Test
	public void testGroupStringMArray() {
		assertEquals(DIFFERENT_GROUP_NAME, differentNameGroup.getName());
		assertTrue(differentNameGroup.contains(USER1));
		assertTrue(differentNameGroup.contains(USER2));
	}

	/**
	 * Test method for {@link Group#equals(Object)} . Asserts that two
	 * {@code Group}s are equal when they are the same {@code Group} in memory.
	 */
	@Test
	public void testEqualsObject_isSameObjectInMemory_equals() {
		assertEquals(testGroup, referenceToTestGroup);
	}

	/**
	 * Test method for {@link Group#equals(Object)} . Asserts that two
	 * {@code Group}s are not equal when the {@code Group}> is {@code null}.
	 */
	@Test
	public void testEqualsObject_isNull_notEquals() {
		assertNotEquals(testGroup, nullGroup);
	}

	/**
	 * Test method for {@link Group#equals(Object)} . Asserts that two "
	 * {@code Group}s" are not equal when the {@code Object} is not an instance
	 * of {@code Group}.
	 */
	@Test
	public void testEqualsObject_isNotAGroup_notEquals() {
		assertNotEquals(testGroup, notAGroup);
	}

	/**
	 * Test method for {@link Group#equals(Object)} . Asserts that two
	 * {@code Group}s are not equal when the Group has a {@code null} name and
	 * the {@code Object} has a non- {@code null} name.
	 */
	@Test
	public void testEqualsObject_hasNonNullNameWhenGroupHasNullName_notEquals() {
		assertNotEquals(nullNameGroup, sameNameGroup);
	}

	/**
	 * Test method for {@link Group#equals(Object)} . Asserts that two
	 * {@code Group}s are equal when they both have {@code null} names.
	 */
	@Test
	public void testEqualsObject_hasNullNameWhenGroupHasNullName_equals() {
		assertEquals(nullNameGroup, new Group<ScoreUser>());
	}

	/**
	 * Test method for {@link Group#equals(Object)}. Asserts that two
	 * {@code Group}s are not equal when they each have different names.
	 */
	@Test
	public void testEqualsObject_differentName_notEquals() {
		assertNotEquals(testGroup, differentNameGroup);
	}

	/**
	 * Test method for {@link Group#equals(Object)}. Asserts that two
	 * {@code Group}s are equal when they both have the same name.
	 */
	@Test
	public void testEqualsObject_sameName_equals() {
		assertEquals(testGroup, sameNameGroup);
	}

	/**
	 * Test method for {@link Group#equalsMembers(Object)} . Asserts that two
	 * {@code Group}s equalMembers if they have the same members.
	 */
	@Test
	public void testEqualsMembers() {
		assertTrue(testGroup.equalsMembers(differentNameGroup));
	}

	/**
	 * Test method for {@link Group#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(GROUP_NAME, testGroup.getName());
	}

	/**
	 * Test method for {@link Group#setName(String)}.
	 */
	@Test
	public void testSetName() {
		testGroup.setName(DIFFERENT_GROUP_NAME);
		assertEquals(DIFFERENT_GROUP_NAME, testGroup.getName());
	}

	/**
	 * Test method for {@link Group#getUniqueId()}. Asserts that the uniqueId is
	 * the name.
	 */
	@Test
	public void testGetUniqueId() {
		assertEquals(testGroup.getName(), testGroup.getUniqueId());
	}

	/**
	 * Test method for {@link Group#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
		testGroup.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, testGroup.getDescription());
	}

	/**
	 * Test method for {@link Group#setDescription(String)}.
	 */
	@Test
	public void testSetDescription() {
		testGroup.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, testGroup.getDescription());
	}

}
