package com.catalyst.sonar.score.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lsajeev
 * 
 */
public class CriteriaTest {
	//testCriteria
	Criteria testCriteria;
	String testMetric = "Coverage";
	double testRequiredAmt = 90;
	int testDays = 4;
	//testCriteria with same name
	Criteria testCriteriaSame = testCriteria;
	String testMetricSame = "Coverage";
	double testRequiredAmtSame = 90;
	int testDaysSame= 4;
	//testCriteria with different name
	Criteria testCriteriaDifferentName;
	String testMetricDifferent = "Violation";
	double testRequiredAmtDifferent = 10;
	int testDaysDifferent = 14;
	//testCriteria null
	Criteria testCriteriaNull;
	//testCriteria with null metric
	Criteria testCriteriaWithMetricNull;
	String testMetricNull= null;
	double testRequiredAmtNotNull = 90;
	int testDaysNotNull = 4;
	//testCriteria with null requiredAmt
	Criteria testCriteriaWithReqdAmtZero;
	String testMetricNotNull = "Coverage";
	double testRequiredAmtNull = 0;
	int testDaysNotNullWithReqdAmtNull = 4;
	//testCriteria with null days
		Criteria testCriteriaWithDaysZero;
		String testMetricNotNullWithDaysNull = "Coverage";
		double testRequiredAmtNotNullWithDaysNull = 90;
		int testDaysNull = 0;
		
	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		testCriteria = new Criteria();
		testCriteria.setMetric(testMetric);
		testCriteria.setRequiredAmt(testRequiredAmt);
		testCriteria.setDays(testDays);
		testCriteriaSame = new Criteria(testMetric, testRequiredAmt,
				testDays);
		testCriteriaDifferentName = new Criteria(testMetricDifferent,  testRequiredAmtDifferent,
				testDaysDifferent);
		testCriteriaWithMetricNull = new Criteria(testMetricNull, testRequiredAmtNotNull,
				testDaysNotNull);
		testCriteriaWithReqdAmtZero = new Criteria(testMetricNotNull, testRequiredAmtNull,
				testDaysNotNullWithReqdAmtNull);
		testCriteriaWithDaysZero = new Criteria(testMetricNotNullWithDaysNull, testRequiredAmtNotNullWithDaysNull ,
				testDaysNull);
		
	}

	/**
	 * tests the setter and getter for metric
	 */
	@Test
	public void testGetMetric() {
		testCriteria.setMetric("TestMetric");
		assertEquals("TestMetric", testCriteria.getMetric());
	}

	/**
	 * tests the setter and getter for required amount
	 */
	@Test
	public void testGetRequiredAmt() {
		testCriteria.setRequiredAmt(40);
		assertEquals(40.0, testCriteria.getRequiredAmt(), 1e-8);
	}

	/**
	 * tests the the setter and getter for days
	 */
	@Test
	public void testGetDays() {
		testCriteria.setDays(5);
		assertSame(5, testCriteria.getDays());
	}

	/**
	 * tests if two criteria are equal
	 */
	@Test
	public void testIfCriteriaEquals() {
		assertTrue(testCriteria.equals(testCriteriaSame));

	}

	/**
	 * tests if two criteria are not equal
	 */
	@Test
	public void testIfCriteriaNotEquals() {
		assertFalse(testCriteria.equals(testCriteriaDifferentName));
	}

	/**
	 * tests criteria does not equal to null
	 */
	@Test
	public void testIfCriteriaNull() {
		assertFalse(testCriteria.equals(testCriteriaNull));
	}
	/**
	 * tests if metric value of one criteria is null
	 */
	@Test
	public void testNotEqualsIfMetricIsNull() {
		assertFalse(testCriteria.getMetric().equals(testCriteriaWithMetricNull.getMetric()));
	}
	/**
	 * tests if metric values of both criteria are not equal 
	 */
	@Test
	public void testNotEqualsIfMetricIsDifferent() {
		assertFalse(testCriteria.getMetric().equals(testCriteriaDifferentName.getMetric()));
	}
	/**
	 * Tests if requiredAmt value = 0 in one criteria is not equal to another criteria with requiredAmt > 0
	 */
	@Test
	public void testNotEqualsIfRequiredAmtisZero() {
		assertFalse(testCriteria.getRequiredAmt().equals(testCriteriaWithReqdAmtZero.getRequiredAmt()));
	}
	
	/**
	 * Tests if requiredAmt values in both criteria are different 
	 */
	@Test
	public void testNotEqualsIfRequiredAmtisDifferent() {
		assertFalse(testCriteria.getRequiredAmt().equals(testCriteriaDifferentName.getRequiredAmt()));
	}
	/**
	 * Tests if requiredAmt value = 0 in one criteria is not equal to another criteria with requiredAmt > 0
	 */
	@Test
	public void testNotEqualsIfDaysZero() {
		assertFalse(testCriteria.getDays().equals(testCriteriaWithDaysZero.getDays()));
	}
	
	/**
	 * Tests if requiredAmt values in both criteria are different 
	 */
	@Test
	public void testNotEqualsIfDaysDifferent() {
		assertFalse(testCriteria.equals(testCriteriaDifferentName));
	}
	/**
	 * Tests if hashcode of one Criteria equals hashCode of another criteria
	 */
	@Test
	public void testHashCode() {
		assertTrue(testCriteria.hashCode() == testCriteriaSame.hashCode());

	}

	/**
	 * tests hashcode of one Criteria is not equal to another criteria
	 */
	@Test
	public void testHashCodeNotSame() {
		assertFalse(testCriteria.hashCode() == testCriteriaDifferentName.hashCode());

	}

	/**
	 * tests hashcodes not equal where metric is null
	 */
	@Test
	public void testHashCodeNullIfMetricNull() {

		assertFalse(testCriteria.hashCode() == testCriteriaWithMetricNull.hashCode());

	}

	/**
	 * Tests hashcodes not equal when requiredAmt is zero
	 */
	@Test
	public void testHashCodeifRequiredAmtIsZero() {

		assertFalse(testCriteria.hashCode() == testCriteriaWithReqdAmtZero.hashCode());

	}
	/**
	 * Tests hashcodes not equal when int days is zero
	 */
	@Test
	public void testHashCodeifDaysIsZero() {

		assertFalse(testCriteria.hashCode() == testCriteriaWithDaysZero.hashCode());

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
