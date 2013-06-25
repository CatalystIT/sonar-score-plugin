/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Cases for all possible outcomes of
 * {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
 * @author JDunn
 */
public class CriterionTest_Equals {
	
	private Criterion testCriterion;
	private Object referenceToTestCriterion;
	private Object nullCriterion;
	private Object notACriterion;
	private Object sameFields;
	private Object differentMetric;
	private Object differentAmount;
	private Object differentDays;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testCriterion = new Criterion(MOCK_METRIC_1, AMOUNT_1, DAYS_1);
		referenceToTestCriterion = testCriterion;
		nullCriterion = null;
		notACriterion = "I'm Actually A String!";
		sameFields = new Criterion(MOCK_METRIC_1, AMOUNT_1, DAYS_1);
		differentMetric = new Criterion(MOCK_METRIC_2, AMOUNT_1, DAYS_1);
		differentAmount = new Criterion(MOCK_METRIC_1, AMOUNT_2, DAYS_1);
		differentDays = new Criterion(MOCK_METRIC_1, AMOUNT_1, DAYS_2);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are equal when they are the same <code>Criterion</code> in memory.
	 */
	@Test
	public void testEqualsObject_isSameObjectInMemory_equals() {
		assertEquals(testCriterion, referenceToTestCriterion);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are not equal when the <code>Object</code> is <code>null</code>.
	 */
	@Test
	public void testEqualsObject_isNull_notEquals() {
		assertNotEquals(testCriterion, nullCriterion);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two "<code>Criteria</code>" are not equal when the <code>Object</code> is
	 * not an instance of <code>Criterion</code>.
	 */
	@Test
	public void testEqualsObject_isNotACriterion_notEquals() {
		assertNotEquals(testCriterion, notACriterion);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are not equal when the Criterion has a <code>null</code>
	 * metric and the <code>Object</code> has a non-<code>null</code> metric.
	 */
	@Test
	public void testEqualsObject_hasNonNullMetricWhenCriterionHasNullMetric_notEquals() {
		testCriterion.setMetric(NULL_METRIC);
		assertNotEquals(testCriterion, sameFields);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are equal when they both have <code>null</code>
	 * metrics and all their other fields are the same.
	 */
	@Test
	public void testEqualsObject_hasNullMetricWhenCriterionHasNullMetric_equals() {
		testCriterion.setMetric(NULL_METRIC);
		((Criterion)sameFields).setMetric(NULL_METRIC);
		assertEquals(testCriterion, sameFields);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are not equal when they each have different metrics.
	 */
	@Test
	public void testEqualsObject_differentMetric_notEquals() {
		assertNotEquals(testCriterion, differentMetric);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are not equal when they each have different amounts.
	 */
	@Test
	public void testEqualsObject_differentAmount_notEquals() {
		assertNotEquals(testCriterion, differentAmount);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criteria</code> are not equal when they each have different days.
	 */
	@Test
	public void testEqualsObject_differentDays_notEquals() {
		assertNotEquals(testCriterion, differentDays);
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.Criterion#equals(java.lang.Object)}.
	 * Asserts that two <code>Criterion</code>s are equal when they both have the same fields.
	 */
	@Test
	public void testEqualsObject_sameName_equals() {
		assertEquals(testCriterion, sameFields);
	}

}
