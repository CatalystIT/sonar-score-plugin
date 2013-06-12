package com.catalyst.sonar.score.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author lsajeev
 * 
 */
public class TrophyTest {

	Trophy testTrophy;
	Trophy testTrophyCopy = testTrophy;
	Trophy testTrophyOne;
	Trophy testTrophyTwo;
	Trophy testTrophyThree;
	Trophy testTrophyFour;
	Criteria testCriteria;
	Criteria testCriteriaOne;
	Criteria testCriteriaTwo;
	Criteria testCriteriaThree;
	List<Criteria> testList = new ArrayList<Criteria>();
	// trophyName
	String trophy = "Great Code";
	String trophyOne = "Great Code";
	String trophyTwo = null;
	String trophyThree = "No Violation";
	String trophyFour = "PointsTrophy";
	// criteria values
	String testMetric = "Violation";
	double testRequiredAmt = 10;
	int testDays = 14;
	String testMetricOne = "Coverage";
	double testRequiredAmtOne = 90;
	int testDaysOne = 4;
	String testMetricTwo = "Coverage";
	double testRequiredAmtTwo = 90;
	int testDaysTwo = 4;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testTrophy = new Trophy();
		testTrophy.setTrophyName(trophy);
		testCriteriaOne = new Criteria();
		testTrophy.addCriteria(testCriteriaOne);
		Criteria testCriteriaCopy = testCriteriaOne;
		testTrophyCopy = new Trophy(trophyOne);
		testTrophyCopy.addCriteria(testCriteriaCopy);
		testCriteriaTwo = new Criteria();
		testTrophyThree = new Trophy(trophyThree);
		testTrophyThree.addCriteria(testCriteriaThree);
		testTrophyTwo = new Trophy(trophyTwo);

	}

	/**
	 * tests setter and getter for trophyName
	 */
	@Test
	public void testGetTrophyName() {
		testTrophy.setTrophyName("TrophyOne");
		assertEquals("TrophyOne", testTrophy.getTrophyName());
	}

	/**
	 * tests if Criteria can be added to the list
	 */
	@Test
	public void testAddCriteria() {
		assertEquals(testList.size(), 0);
		testList.add(testCriteria);
		testList.add(testCriteriaTwo);
		assertEquals(testList.size(), 2);
	}

	/**
	 * tests getter for Criteria
	 */

	@Test
	public void testGetCriteria() {
		assertEquals("1", 1, testTrophy.getCriteria().size());
	}

	/**
	 * tests if trophy objects are equal
	 */
	@Test
	public void testTrophyEquals() {
		assertTrue(testTrophy.equals(testTrophyCopy));

	}

	/**
	 * tests if trophy objects are not equal
	 */
	@Test
	public void testTrophyNotEquals() {
		assertFalse(testTrophy.equals(testTrophyOne));
	}

	/**
	 * tests if a trophy object is null
	 */
	@Test
	public void testTrophyEqualsNull() {
		assertNull(testTrophyFour);

	}

	/**
	 * tests if trophy object's name is null
	 */
	@Test
	public void testTrophyNameEqualsToNull() {
		assertNull(testTrophyTwo.getTrophyName());

	}

	/**
	 * tests if trophy object has a name and is not null
	 */
	@Test
	public void testTrophyNameNotEqualToNull() {
		assertNotNull(testTrophy.getTrophyName());

	}

	/**
	 * tests if trophy name is not equal to the trophy object's trophy name
	 */
	@Test
	public void testTrophyNameNotEquals() {
		assertFalse(trophy.equals(testTrophyThree.getTrophyName()));

	}

	/**
	 * tests if trophy name is equal to trophy object's trophy name
	 */
	@Test
	public void testTrophyNameEquals() {
		assertTrue(trophy.equals(testTrophy.getTrophyName()));

	}

	/**
	 * tests if the haschCode of two trophyObjects are equal
	 */
	@Test
	public void testHashCodeIsEqual() {
		assertEquals("Expected same as actual", testTrophy.hashCode(),
				testTrophyCopy.hashCode());
	}

	/**
	 * tests if the hashCode of two objects are not equal
	 */
	@Test
	public void testHashCodeIsNotEqual() {
		Assert.assertNotEquals(testTrophy.hashCode(),
				testTrophyThree.hashCode());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
