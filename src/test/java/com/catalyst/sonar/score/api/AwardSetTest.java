/**
 * 
 */
package com.catalyst.sonar.score.api;

import static org.junit.Assert.*;
import static com.catalyst.sonar.score.api.ApiTestConstants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for
 * {@link com.catalyst.sonar.score.api.AwardSet}.
 * @author JDunn
 */
public class AwardSetTest {
	
	AwardSet<Award> testSet;
	Award award1 = new TitleCup("Award One");
	Award award2 = new TitleCup("Award Two");
	Criterion c1a = new Criterion(METRIC_1, AMOUNT_1, DAYS_1);
	Criterion c1b = new Criterion(METRIC_2, AMOUNT_2, DAYS_2);
	Criterion c2a = new Criterion(METRIC_1, AMOUNT_1, DAYS_1);
	Award award1Copy = new TitleCup("Award One");

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		testSet = new AwardSet<Award>();
		award1.addCriteria(c1a);
		award1.addMembersToInclude(USER1);
		award1.addMembersToExclude(USER3);
		award1Copy.addCriteria(c1a);
		award1Copy.addMembersToInclude(USER1);
		award1Copy.addMembersToExclude(USER3);
		award2.addCriteria(c2a);
		testSet.add(award1);
	}

	/**
	 * Test method for {@link com.catalyst.sonar.score.api.AwardSet#add(com.catalyst.sonar.score.api.Award)}.
	 * Asserts that {@code add()} returns false if no new {@code Award} is added and no new information is added
	 * to the {@code AwardSet}.
	 */
	@Test
	public void testAddA_AddAwardWithSameNameAndSameFields_false() {
		assertFalse(testSet.add(award1Copy));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.AwardSet#add(com.catalyst.sonar.score.api.Award)}.
	 * Asserts that {@code add()} returns true if the  {@code Award} passed in as the argument has the same
	 * name as an {@code Award} already in this {@code AwardSet} but also has a new {@code Criterion},
	 * and that the {@code Award} with the same name in this {@code AwardSet} now contains the new {@code Criterion}.
	 */
	@Test
	public void testAddA_AddAwardWithSameNameAndExtraCriterion_true() {
		award1Copy.addCriterion(c1b);
		assertTrue(testSet.add(award1Copy));
		assertTrue(award1.getCriteria().contains(c1b));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.AwardSet#add(com.catalyst.sonar.score.api.Award)}.
	 * Asserts that {@code add()} returns true if the  {@code Award} passed in as the argument has the same
	 * name as an {@code Award} already in this {@code AwardSet} but also has a new {@code Member} to include,
	 * and that the {@code Award} with the same name in this {@code AwardSet} now contains the new {@code Member}.
	 */
	@Test
	public void testAddA_AddAwardWithSameNameAndExtraMemberToInclude_true() {
		award1Copy.addMembersToInclude(USER2);
		assertTrue(testSet.add(award1Copy));
		assertTrue(award1.getMembersToInclude().contains(USER2));
	}
	
	/**
	 * Test method for {@link com.catalyst.sonar.score.api.AwardSet#add(com.catalyst.sonar.score.api.Award)}.
	 * Asserts that {@code add()} returns true if the  {@code Award} passed in as the argument has the same
	 * name as an {@code Award} already in this {@code AwardSet} but also has a new {@code Member} to exclude,
	 * and that the {@code Award} with the same name in this {@code AwardSet} now contains the new {@code Member}.
	 */
	@Test
	public void testAddA_AddAwardWithSameNameAndExtraMemberToExclude_true() {
		award1Copy.addMembersToExclude(USER4);
		assertTrue(testSet.add(award1Copy));
		assertTrue(award1.getMembersToExclude().contains(USER4));
		
	}

}
