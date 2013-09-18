/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

import com.catalyst.commons.util.SearchableHashSet;

/**
 * Test Class for {@link Award}.
 * 
 * @author JDunn
 */
public class AwardTest {

	private static final String AWARD_NAME = "Award One";
	private static final String DIFFERENT_AWARD_NAME = "Award Two";

	private Award testAward;
	private SearchableHashSet<Criterion> realSet;
	private Criterion criterion1;
	private Criterion criterion2;
	private Criterion criterionNullMetric;

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
		criterion1 = new Criterion(MOCK_METRIC_1, 1.1, 1);
		criterion2 = new Criterion(MOCK_METRIC_2, 2.2, 2);
		criterionNullMetric = new Criterion(null, 3.3, 3);

		referenceToTestAward = testAward;
		nullAward = null;
		notAnAward = "I'm actually a String.";
		nullNameAward = new TitleCup(null);
		anotherNullNameAward = new TitleCup(null);
		differentNameAward = new TitleCup(DIFFERENT_AWARD_NAME);
		sameNameAward = new TitleCup(AWARD_NAME);
		((Award) sameNameAward).addCriterion(criterion1);
	}

	/**
	 * Test method for {@link Award#hashCode()}. Asserts that two Awards with
	 * the same name have the same hashCode.
	 */
	@Test
	public void testHashCode() {
		assertEquals(new TitleCup(AWARD_NAME).hashCode(), testAward.hashCode());
		assertEquals(new TitleCup(null).hashCode(), nullNameAward.hashCode());
	}

	/**
	 * Test method for {@link Award#Award(java.lang.String)}. Asserts that the
	 * name field was instantiated accordingly.
	 */
	@Test
	public void testAwardString() {
		assertEquals(AWARD_NAME, testAward.getName());
	}

	/**
	 * Test method for {@link Award#addCriterion(Criterion)} . Asserts that
	 * {@code addCriteria(criteria)} adds the Criterion.
	 */
	@Test
	public void testAddCriterion() {
		testAward.addCriterion(criterion1);
		assertTrue(testAward.getCriteria().contains(criterion1));
	}
	
	/**
	 * Test method for {@link Award#addCriterion(Criterion)} . Asserts that
	 * {@code addCriteria(criteria)} does not add the Criterion if the Metric is null.
	 */
	@Test
	public void testAddCriterion_Criterion_Metric_Null() {
		testAward.addCriterion(criterionNullMetric);
		assertFalse(testAward.getCriteria().contains(criterion1));
	}

	/**
	 * Test method for {@link Award#addCriteria(Criterion)} . Asserts that
	 * {@code addCriteria(criteria)} adds the Criteria.
	 */
	@Test
	public void testAddCriteria() {
		setField(testAward, "criteria", realSet);
		testAward.addCriteria(criterion1, criterion2);
		assertTrue(testAward.getCriteria().contains(criterion1));
		assertTrue(testAward.getCriteria().contains(criterion2));
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two
	 * {@link Award}s are equal when they are the same {@code Award} in memory.
	 */
	@Test
	public void testEqualsObject_isSameObjectInMemory_equals() {
		assertEquals(testAward, referenceToTestAward);
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two
	 * {@link Award}s are not equal when the {@code Object} is
	 * {@code null}.
	 */
	@Test
	public void testEqualsObject_isNull_notEquals() {
		assertNotEquals(testAward, nullAward);
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two "
	 * {@link Award}s" are not equal when the {@code Object} is not an
	 * instance of {@code Award}.
	 */
	@Test
	public void testEqualsObject_isNotAnAward_notEquals() {
		assertNotEquals(testAward, notAnAward);
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two
	 * {@link Award}s are not equal when the {@code Award} has a {@code null} name
	 * and the {@code Object} has a non- {@code null} name.
	 */
	@Test
	public void testEqualsObject_hasNonNullNameWhenAwardHasNullName_notEquals() {
		assertNotEquals(nullNameAward, sameNameAward);
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two
	 * {@link Award}s are equal when they both have {@code null} names.
	 */
	@Test
	public void testEqualsObject_hasNullNameWhenAwardHasNullName_equals() {
		assertEquals(nullNameAward, anotherNullNameAward);
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two
	 * {@link Award}s are not equal when they each have different names.
	 */
	@Test
	public void testEqualsObject_differentName_notEquals() {
		assertNotEquals(testAward, differentNameAward);
	}

	/**
	 * Test method for {@link Award#equals(Object)}. Asserts that two
	 * {@link Award}s are equal when they both have the same name.
	 */
	@Test
	public void testEqualsObject_sameName_equals() {
		assertEquals(testAward, sameNameAward);
	}

	/**
	 * Test method for {@link Award#getName()}.
	 */
	@Test
	public void testGetName() {
		assertEquals(AWARD_NAME, testAward.getName());
	}

	/**
	 * Test method for {@link Award#setName(String)}.
	 */
	@Test
	public void testSetName() {
		testAward.setName(DIFFERENT_AWARD_NAME);
		assertEquals(DIFFERENT_AWARD_NAME, testAward.getName());
	}

	/**
	 * Test method for {@link Award#getCriteria()}.
	 */
	@Test
	public void testGetCriteria() {
		assertEquals(realSet, getField(testAward, "criteria"));
	}
	
	/**
	 * Test method for {@link Award#getUniqueId()}.
	 */
	@Test
	public void testGetUniqueId() {
		assertSame(testAward.getName(), testAward.getUniqueId());
	}

}
