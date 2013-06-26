/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author JDunn
 *
 */
public class AwardTest {
	
	private static final String AWARD_NAME = "Award One";
	private static final String DIFFERENT_AWARD_NAME = "Award Two";
	
	private Award testAward;
	private SearchableHashSet<Criterion> realSet;
	private Criterion criterion1;
	private Criterion criterion2;
	
	private Object referenceToTestAward;
	private Object nullAward;
	private Object notAnAward;
	private Award nullNameAward;
	private Object anotherNullNameAward;
	private Object differentNameAward;
	private Object sameNameAward;

	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		testAward = new TitleCup(AWARD_NAME);
		realSet = (SearchableHashSet<Criterion>) getField(testAward, "criteria");
		criterion1 = new Criterion(null, 1.1, 1);
		criterion2 = new Criterion(null, 2.2, 2);
		
		referenceToTestAward = testAward;
		nullAward = null;
		notAnAward = "I'm actually a String.";
		nullNameAward = new TitleCup(null);
		anotherNullNameAward = new TitleCup(null);
		differentNameAward = new TitleCup(DIFFERENT_AWARD_NAME);
		sameNameAward = new TitleCup(AWARD_NAME);
		((Award)sameNameAward).addCriterion(criterion1);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#hashCode()}.
	 * Asserts that two TitleCups with the same name have the same hashCode.
	 */
	@Test
	public void testHashCode() {
		assertEquals(new TitleCup(AWARD_NAME).hashCode(), testAward.hashCode());
		assertEquals(new TitleCup(null).hashCode(), nullNameAward.hashCode());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#Award()}.
	 * Asserts that an <code>Award</code> created without a name parameter
	 * will be given the name of the constant <code>UNNAMED_AWARD</code>. 
	 */
	@Test
	public void testAward() {
		assertEquals(Award.UNNAMED_AWARD, new TitleCup().getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#Award(java.lang.String)}.
	 */
	@Test
	public void testAwardString() {
		assertEquals(AWARD_NAME, testAward.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#addCriterion(com.catalyst.sonar.score.api.Criterion)}.
	 * Asserts that {@code testAward.addCriteria(criteria)} adds the Criteria.
	 */
	@Test
	public void testAddCriterion() {
		testAward.addCriterion(criterion1);
		assertTrue(testAward.getCriteria().contains(criterion1));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#addCriteria(com.catalyst.sonar.score.api.Criterion)}.
	 * Asserts that {@code testAward.addCriteria(criteria)} adds the Criteria.
	 */
	@Test
	public void testAddCriteria() {
		setField(testAward, "criteria", realSet);
		testAward.addCriteria(criterion1, criterion2);
		assertTrue(testAward.getCriteria().contains(criterion1));
		assertTrue(testAward.getCriteria().contains(criterion2));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#addMembers(com.catalyst.sonar.score.api.Member)}.
	 * Asserts that {@code testAward.addMembersToInclude(members...)} adds the members.
	 */
	@Test
	public void testAddMembersToInclude() {
		setField(testAward, "membersToInclude", GROUP1);
		testAward.addMembersToInclude(USER1, USER2);
		assertTrue(testAward.getMembersToInclude().contains(USER1));
		assertTrue(testAward.getMembersToInclude().contains(USER2));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#addCriteria(com.catalyst.sonar.score.api.Member)}.
	 * Asserts that {@code testAward.addMembersToExclude(members...)} adds the members.
	 */
	@Test
	public void testAddMembersToExclude() {
		setField(testAward, "membersToExclude", GROUP2);
		testAward.addMembersToExclude(USER1, USER2);
		assertTrue(testAward.getMembersToExclude().contains(USER1));
		assertTrue(testAward.getMembersToExclude().contains(USER2));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are equal when they are the same <code>Award</code> in memory.
	 */
	@Test
	public void testEqualsObject_isSameObjectInMemory_equals() {
		assertEquals(testAward, referenceToTestAward);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are not equal when the <code>Object</code> is <code>null</code>.
	 */
	@Test
	public void testEqualsObject_isNull_notEquals() {
		assertNotEquals(testAward, nullAward);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two "<code>Award</code>s" are not equal when the <code>Object</code> is
	 * not an instance of <code>Award</code>.
	 */
	@Test
	public void testEqualsObject_isNotAnAward_notEquals() {
		assertNotEquals(testAward, notAnAward);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are not equal when the Award has a <code>null</code>
	 * name and the <code>Object</code> has a non-<code>null</code> name.
	 */
	@Test
	public void testEqualsObject_hasNonNullNameWhenAwardHasNullName_notEquals() {
		assertNotEquals(nullNameAward, sameNameAward);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are equal when they both have <code>null</code> names.
	 */
	@Test
	public void testEqualsObject_hasNullNameWhenAwardHasNullName_equals() {
		assertEquals(nullNameAward, anotherNullNameAward);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are not equal when they each have different names.
	 */
	@Test
	public void testEqualsObject_differentName_notEquals() {
		assertNotEquals(testAward, differentNameAward);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#equals(java.lang.Object)}.
	 * Asserts that two <code>Award</code>s are equal when they both have the same name.
	 */
	@Test
	public void testEqualsObject_sameName_equals() {
		assertEquals(testAward, sameNameAward);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(AWARD_NAME, testAward.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		testAward.setName(DIFFERENT_AWARD_NAME);
		assertEquals(DIFFERENT_AWARD_NAME, testAward.getName());
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Award#getCriteria()}.
	 */
	@Test
	public void testGetCriteria() {
		assertEquals(realSet, getField(testAward, "criteria"));
	}

}
