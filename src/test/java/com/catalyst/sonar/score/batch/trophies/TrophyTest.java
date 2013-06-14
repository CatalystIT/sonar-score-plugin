package com.catalyst.sonar.score.batch.trophies;

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

import com.catalyst.sonar.score.batch.trophies.Criteria;
import com.catalyst.sonar.score.batch.trophies.Trophy;

/**
 * @author lsajeev
 * 
 */
public class TrophyTest {
	//Trophy One
	Trophy testTrophy;
	String trophyName = "Great Code";
	Criteria testCriteria;
	//Trophy with same name
	Trophy testTrophySameName = testTrophy;
	String trophyNameSame = "Great Code";
	Criteria testCriteriaSame = testCriteria;
	//Trophy with different name
	Trophy testTrophyDifferentName;
	String trophyNameDifferent = "No Violation";
	Criteria testCriteriaDifferent;
	//Trophy with null trophyName
	Trophy testTrophyWithNameNull;
	String trophyNameNull;
	Criteria testCriteriaForNameNull;
	
	//Null Trophy
	Trophy testTrophyNull;
	
	List<Criteria> testList = new ArrayList<Criteria>();
	// trophyName

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//TrophyOne
		testTrophy = new Trophy();
		testTrophy.setTrophyName(trophyName);
		testCriteria = new Criteria();
		testTrophy.addCriteria(testCriteria);
		//Trophy with same name
		testTrophySameName = new Trophy();
		testTrophySameName = new Trophy(trophyNameSame);
		testCriteriaSame = new Criteria();
		testTrophySameName.addCriteria(testCriteriaSame);
		// Trophy with different name
		testTrophyDifferentName = new Trophy();
		testTrophyDifferentName.setTrophyName(trophyNameDifferent);
		testCriteriaDifferent = new Criteria();
		testTrophyDifferentName.addCriteria(testCriteriaDifferent);
		// Trophy with name null
		testTrophyWithNameNull = new Trophy();
		testCriteriaForNameNull = new Criteria();
		testTrophyWithNameNull.addCriteria(testCriteriaForNameNull);
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
		assertEquals(0, testList.size());
		testList.add(testCriteria);
		testList.add(testCriteriaDifferent);
		assertEquals(2, testList.size());
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
		assertTrue(testTrophy.equals(testTrophySameName));

	}

	/**
	 * tests if a trophy object is null
	 */
	@Test
	public void testTrophyEqualsNull() {
		assertFalse(testTrophy.equals(testTrophyNull));

	}

	/**
	 * tests if trophy objects are not the same
	 */
	@Test
	public void testTrophyNotEquals() {
		assertFalse(testTrophy.equals(testTrophyDifferentName));
	}

	
	/**
	 * tests if a trophy object's name is null
	 */
	@Test
	public void testTrophyNameEqualsToNull() {
		Assert.assertFalse(testTrophy.getTrophyName().equals(testTrophyWithNameNull.getTrophyName()));
	}

	/**
	 * tests if trophy name is not equal to the trophy object's trophy name
	 */
	@Test
	public void testTrophyNameNotEquals() {
		assertFalse(testTrophy.getTrophyName().equals(testTrophyDifferentName.getTrophyName()));

	}

	/**
	 * tests if trophy name is equal to trophy object's trophy name
	 */
	@Test
	public void testTrophyNameEquals() {
		assertEquals(testTrophy.getTrophyName(), testTrophySameName.getTrophyName());

	}

	/**
	 * tests if the haschCode of two trophyObjects are equal
	 */
	@Test
	public void testHashCodeIsEqual() {
		assertEquals(testTrophy.hashCode(), testTrophySameName.hashCode());
	}

	/**
	 * tests if the hashCode of two objects are not equal
	 */
	@Test
	public void testHashCodeIsNotEqual() {
		Assert.assertNotEquals(testTrophy.hashCode(), testTrophyDifferentName.hashCode());
	}

	/**
	 * tests if the hashCode of one object is null
	 */
	@Test
	public void testHashCodeIsNotEqualIfOneTrophyNameIsNull() {
		Assert.assertNotEquals(testTrophy.hashCode(), testTrophyWithNameNull.hashCode());
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
