/**
 * 
 */
package com.catalyst.sonar.score.batch.trophies;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for TrohySet
 *
 */
public class TrophySetTest {
	
	private static final String NAME_IN_SET = "Trophy In Set";
	private static final String NAME_NOT_IN_SET = "Trophy Not In Set";
	private static final String NAME_ADD_TO_SET = "Trophy To Add To Set";
	
	private TrophySet testSet;
	private Trophy trophyInSet;
	private Trophy trophyWithSameNameAsTrophyInSet;
	private Trophy trophyNotInSet;
	private Trophy trophyToAddToSet;
	private Criteria criteria1;
	private Criteria criteria2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		trophyInSet = new Trophy(NAME_IN_SET);
		trophyWithSameNameAsTrophyInSet = new Trophy(NAME_IN_SET);
		
		criteria1 = new Criteria("metric1", 90.0, 5);
		criteria2 = new Criteria("metric2", 90.0, 5);
		
		trophyInSet.addCriteria(criteria1);
		trophyWithSameNameAsTrophyInSet.addCriteria(criteria2);
		
		trophyToAddToSet = new Trophy(NAME_ADD_TO_SET);
		trophyNotInSet = new Trophy(NAME_NOT_IN_SET);
		
		testSet = new TrophySet();
		testSet.add(trophyInSet);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.trophies.TrophySet#add(com.catalyst.sonar.score.batch.trophies.Trophy)}.
	 */
	@Test
	public void testAddTrophy_SameNameAsTrophyAlreadyInSet() {
		assertFalse(trophyInSet.getCriteria().contains(criteria2));
		assertTrue(testSet.add(trophyWithSameNameAsTrophyInSet));
		assertTrue(trophyInSet.getCriteria().contains(criteria2));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.trophies.TrophySet#add(com.catalyst.sonar.score.batch.trophies.Trophy)}.
	 */
	@Test
	public void testAddTrophy_TrophyNotYetInSet() {
		assertTrue(testSet.add(trophyToAddToSet));
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.trophies.TrophySet#get(com.catalyst.sonar.score.batch.trophies.Trophy)}.
	 */
	@Test
	public void testGet_TrophyInSet() {
		assertSame(trophyInSet, testSet.get(trophyWithSameNameAsTrophyInSet));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.batch.trophies.TrophySet#get(com.catalyst.sonar.score.batch.trophies.Trophy)}.
	 */
	@Test
	public void testGet_TrophyNotInSet() {
		assertNull(testSet.get(trophyNotInSet));
	}

}
