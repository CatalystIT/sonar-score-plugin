/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for {@link Criterion}.
 * 
 * @author JDunn
 */
public class CriterionTest {

	private Criterion testCriterion;
	private Criterion twinCriterion;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testCriterion = new Criterion(METRIC_1, AMOUNT_1, DAYS_1);
		twinCriterion = new Criterion(METRIC_1, AMOUNT_1, DAYS_1);
	}

	/**
	 * Test method for {@link Criterion#hashCode()} .
	 */
	@Test
	public void testHashCode() {
		assertEquals(testCriterion.hashCode(), twinCriterion.hashCode());
		testCriterion.setMetric(NULL_METRIC);
		twinCriterion.setMetric(NULL_METRIC);
		assertEquals(testCriterion.hashCode(), twinCriterion.hashCode());

	}

	/**
	 * Test method for {@link Criterion#Criterion()}.
	 */
	@Test
	public void testCriterion() {
		Criterion defaultCriterion = new Criterion();
		assertEquals(0, defaultCriterion.getAmount(), 0);
		assertNull(defaultCriterion.getMetric());
		assertEquals(0, defaultCriterion.getDays());
	}

	/**
	 * Test method for
	 * {@link Criterion#Criterion(org.sonar.api.measures.Metric, double, int)} .
	 */
	@Test
	public void testCriterionMetricDoubleInt() {
		assertEquals(METRIC_1, testCriterion.getMetric());
		assertEquals(AMOUNT_1, testCriterion.getAmount(), 0);
		assertEquals(DAYS_1, testCriterion.getDays());
	}

	/**
	 * Test method for {@link Criterion#getMetric()}.
	 */
	@Test
	public void testGetMetric() {
		assertEquals(METRIC_1, testCriterion.getMetric());
	}

	/**
	 * Test method for {@link Criterion#setMetric(Metric)} .
	 */
	@Test
	public void testSetMetric() {
		testCriterion.setMetric(METRIC_2);
		assertEquals(METRIC_2, testCriterion.getMetric());
	}

	/**
	 * Test method for {@link Criterion#getAmount()}.
	 */
	@Test
	public void testGetAmount() {
		assertEquals(AMOUNT_1, testCriterion.getAmount(), 0);
	}

	/**
	 * Test method for {@link Criterion#setAmount(double)}.
	 */
	@Test
	public void testSetAmount() {
		testCriterion.setAmount(AMOUNT_2);
		assertEquals(AMOUNT_2, testCriterion.getAmount(), 0);
	}

	/**
	 * Test method for {@link Criterion#getDays()}.
	 */
	@Test
	public void testGetDays() {
		assertEquals(DAYS_1, testCriterion.getDays());
	}

	/**
	 * Test method for {@link Criterion#setDays(int)}.
	 */
	@Test
	public void testSetDays() {
		testCriterion.setDays(DAYS_2);
		assertEquals(DAYS_2, testCriterion.getDays());
	}

}
