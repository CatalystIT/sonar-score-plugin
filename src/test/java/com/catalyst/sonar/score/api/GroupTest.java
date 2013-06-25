/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author James
 *
 */
public class GroupTest {
	
	private static final String GROUP_NAME = "The Group";
	private static final String DIFFERENT_GROUP_NAME = "Another Group";
	private static final String DESCRIPTION = "I'm a Group!";
	private static final String USER1_NAME = "John";
	private static final String USER2_NAME = "Mary";
	private static final String USER3_NAME = "James";
	private static final String USER4_NAME = "Sarah";
	private static final ScoreUser user1 = new ScoreUser(USER1_NAME, "Jn@Smith.com", USER1_NAME);
	private static final ScoreUser user2 = new ScoreUser(USER2_NAME, "Ma@Smith.com", USER2_NAME);
	private static final ScoreUser user3 = new ScoreUser(USER3_NAME, "Ja@Smith.com", USER3_NAME);
	private static final ScoreUser user4 = new ScoreUser(USER4_NAME, "Sa@Smith.com", USER4_NAME);
	
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
		testGroup.add(user1);
		testGroup.add(user2);
		sameNameGroup.add(user3);
		sameNameGroup.add(user4);
		differentNameGroup = new Group<ScoreUser>(DIFFERENT_GROUP_NAME, user1, user2);
		
		referenceToTestGroup = testGroup;
		nullNameGroup = new Group<ScoreUser>();
		notAGroup = "I'm Actually A String!";
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#hashCode()}.
	 * Asserts that two <code>Group</code>s with the same name but different
	 * members have the same <code>hashCode</code>.
	 */
	@Test
	public void testHashCode() {
		assertEquals(testGroup.hashCode(), sameNameGroup.hashCode());
		assertEquals(nullNameGroup.hashCode(), new Group<ScoreUser>().hashCode());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#Group()}.
	 * Asserts that a <code>Group</code> is an instance of <code>SearchableHashSet</code>. 
	 */
	@Test
	public void testGroup() {
		assertTrue(nullNameGroup instanceof SearchableHashSet);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#Group(java.lang.String)}.
	 * Asserts that the constructor with the <code>String</code> argument sets the name.
	 */
	@Test
	public void testGroupString() {
		assertEquals(GROUP_NAME, testGroup.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#Group(java.lang.String, M[])}.
	 * Asserts that the constructor with the <code>String</code> and member argument sets the name
	 * and populates the members.
	 */
	@Test
	public void testGroupStringMArray() {
		assertEquals(DIFFERENT_GROUP_NAME, differentNameGroup.getName());
		assertTrue(differentNameGroup.contains(user1));
		assertTrue(differentNameGroup.contains(user2));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are equal when they are the same <code>Award</code> in memory.
	 */
	@Test
	public void testEqualsObject_isSameObjectInMemory_equals() {
		assertEquals(testGroup, referenceToTestGroup);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are not equal when the <code>Object</code> is <code>null</code>.
	 */
	@Test
	public void testEqualsObject_isNull_notEquals() {
		assertNotEquals(testGroup, nullGroup);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two "<code>Award</code>s" are not equal when the <code>Object</code> is
	 * not an instance of <code>Award</code>.
	 */
	@Test
	public void testEqualsObject_isNotAnAward_notEquals() {
		assertNotEquals(testGroup, notAGroup);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are not equal when the Award has a <code>null</code>
	 * name and the <code>Object</code> has a non-<code>null</code> name.
	 */
	@Test
	public void testEqualsObject_hasNonNullNameWhenAwardHasNullName_notEquals() {
		assertNotEquals(nullNameGroup, sameNameGroup);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are equal when they both have <code>null</code> names.
	 */
	@Test
	public void testEqualsObject_hasNullNameWhenAwardHasNullName_notEquals() {
		assertEquals(nullNameGroup, new Group<ScoreUser>());
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are not equal when they both have different names.
	 */
	@Test
	public void testEqualsObject_differentName_notEquals() {
		assertNotEquals(testGroup, differentNameGroup);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are equal when they both have the same name.
	 */
	@Test
	public void testEqualsObject_sameName_equals() {
		assertEquals(testGroup, sameNameGroup);
	}



	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#equalsMembers(java.lang.Object)}.
	 * Asserts that two groups equalMembers if they have the same members.
	 */
	@Test
	public void testEqualsMembers() {
		assertTrue(testGroup.equalsMembers(differentNameGroup));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(GROUP_NAME, testGroup.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		testGroup.setName(DIFFERENT_GROUP_NAME);
		assertEquals(DIFFERENT_GROUP_NAME, testGroup.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#getUniqueId()}.
	 * Asserts that the uniqueId is the name.
	 */
	@Test
	public void testGetUniqueId() {
		assertEquals(testGroup.getName(), testGroup.getUniqueId());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#getDescription()}.
	 */
	@Test
	public void testGetDescription() {
		testGroup.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, testGroup.getDescription());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Group#setDescription(java.lang.String)}.
	 */
	@Test
	public void testSetDescription() {
		testGroup.setDescription(DESCRIPTION);
		assertEquals(DESCRIPTION, testGroup.getDescription());
	}

}
