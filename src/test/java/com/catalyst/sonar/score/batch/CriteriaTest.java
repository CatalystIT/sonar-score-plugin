package com.catalyst.sonar.score.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lsajeev
 * 
 */
public class CriteriaTest {
	Criteria testCriteria;
	Criteria testCriteriaOne;
	Criteria CopyOfTestCriteriaOne;
	String testMetricOne = "Coverage";
	double testRequiredAmtOne = 90;
	int testDaysOne = 4;
	//criteria 2
	Criteria testCriteriaTwo;
	String testMetricTwo = "Coverage";
	double testRequiredAmtTwo = 90;
	int testDaysTwo = 4;
	//criteria 3
	Criteria testCriteriaThree;
	String testMetricThree = "Violation";
	double testRequiredAmtThree = 10;
	int testDaysThree = 14;
	//criteria 4
	Criteria testCriteriaFour;
	String testMetricFour;
	double testRequiredAmtFour;
	int testDaysFour;
	//Criteria 5
	Criteria testCriteriaFive;
	String testMetricFive = "Violation";
	double testRequiredAmtFive;
	int testDaysFive = 14;
	//Criteria 6
	Criteria testCriteriaSix;
	String testMetricSix = "Coverage";
	double testRequiredAmtSix = 0;;
	int testDaysSix;

	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {

		testCriteria = new Criteria();
		testCriteriaOne = new Criteria(testMetricOne, testRequiredAmtOne,
				testDaysOne);
		testCriteriaTwo = new Criteria(testMetricTwo, testRequiredAmtTwo,
				testDaysTwo);
		testCriteriaThree = new Criteria(testMetricThree, testRequiredAmtThree,
				testDaysThree);
		testCriteriaFour = new Criteria(testMetricFour, testRequiredAmtFour,
				testDaysFour);
		testCriteriaFive = new Criteria(testMetricFive, testRequiredAmtFive,
				testDaysFive);
		testCriteriaSix = new Criteria(testMetricSix, testRequiredAmtSix,
				testDaysSix);
		CopyOfTestCriteriaOne = new Criteria(testMetricOne, testRequiredAmtOne,
				testDaysOne);

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
	public void testEquals() {
		assertEquals("Expected same as actual", testCriteriaOne,
				testCriteriaTwo);

	}

	/**
	 * tests if two criteria are not equal
	 */
	@Test
	public void testNotEquals() {
		assertNotSame(testCriteriaOne, testCriteriaThree);
	}

	/**
	 * tests criteria does not equal to null
	 */
	@Test
	public void testIfCriteriaNull() {
		assertNotSame(testCriteriaOne, testCriteriaFour);
	}

	/**
	 * Tests if requiredAmt value zero in one criteria is not equal to another criteria with requiredAmt > 0
	 */
	@Test
	public void testNotEqualsIfRequiredAmtisZero() {
		assertNotSame(testCriteriaOne, testCriteriaSix);
	}
	/**
	 * Tests if hashcode of one Criteria equals hashCode of another criteria
	 */
	@Test
	public void testHashCode() {
		assertTrue(testCriteriaOne.hashCode() == testCriteriaTwo.hashCode());

	}

	/**
	 * tests hashcode of one Criteria is not equal to another criteria
	 */
	@Test
	public void testHashCodeNotSame() {
		assertFalse(testCriteriaOne.hashCode() == testCriteriaThree.hashCode());

	}

	/**
	 * tests hashcode where criteria is null
	 */
	@Test
	public void testHashCodeNull() {

		assertFalse(testCriteriaOne.hashCode() == testCriteriaFour.hashCode());

	}

	/**
	 * Tests hashcode when requiredAmt is zero
	 */
	@Test
	public void testHashCodeifRequiredAmtIsZero() {

		assertFalse(testCriteriaOne.hashCode() == testCriteriaFive.hashCode());

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
