package com.catalyst.sonar.score.batch;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTrophySet {
	
	TrophySet testTrophySet;
	Trophy testTrophy;
	Trophy copyOfTestTrophy;
	String trophyName = "Violation";
	Trophy testTrophyOne;
	String trophyNameOne = "GreatCode";
	Trophy testTrophyTwo;
	String trophyNameTwo = "GreatCode";
	Criteria testCriteria;
	String testMetric = "Violation";
	double testRequiredAmt = 10;
	int testDays = 14;
	Criteria testCriteriaOne;
	String testMetricOne = "Coverage";
	double testRequiredAmtOne = 90;
	int testDaysOne = 4;
	Criteria testCriteriaTwo;
	String testMetricTwo = "Points";
	double testRequiredAmtTwo = 1000;
	int testDaysTwo = 21;
	List<Criteria> testList = new ArrayList<Criteria>();
	
	
	@Before
	public void setUp() throws Exception {
		
		testTrophySet = new TrophySet();
		testTrophy = new Trophy(trophyName);
		copyOfTestTrophy = testTrophy;
		testTrophy.addCriteria(testCriteria);
		copyOfTestTrophy.addCriteria(testCriteriaOne);
		testTrophyOne = new Trophy(trophyNameOne);
		testTrophyTwo = new Trophy(trophyNameTwo);
		testTrophyTwo.addCriteria(testCriteriaTwo);
		testList.add(testCriteria);
		testList.add(testCriteriaOne);
		testCriteriaOne = new Criteria(testMetricOne, testRequiredAmtOne, testDaysOne);
		testCriteriaTwo = new Criteria(testMetricTwo, testRequiredAmtTwo, testDaysTwo);
		
		
	}

	@Test
	public void testIfSuperAddTrophyWorks() {
		assertEquals(0, testTrophySet.size());
		testTrophySet.add(testTrophy);
		assertEquals(1, testTrophySet.size());
		
	}
	
//	@Test
//	public void testIfSameTrophyCanBeAddedAgain(){
//		testTrophySet.add(testTrophy);
//		assertEquals(1, testTrophySet.size());
//		assertFalse("Same Trophy cannot be added", testTrophySet.add(copyOfTestTrophy));
//		}
//	
	@Test
	public void testIfDifferentCriteriaWithSameTrophyNameCanBeAddedtoTrophy(){
		testTrophyOne.addCriteria(testCriteriaOne);
		testTrophyTwo.addCriteria(testCriteriaTwo);
		testTrophySet.add(testTrophyOne);
		testTrophySet.add(testTrophyTwo);
		assertTrue(testTrophyOne.getCriteria().contains(testCriteriaTwo));
	}

	@Test
	public void testGet(){
			testTrophySet.add(testTrophy);
			assertTrue(testTrophy == testTrophySet.get(copyOfTestTrophy));
		
	}

	@Test
	public void testDeepCopy(){
		
	}
	
	@After
	public void tearDown() throws Exception {
	}

	

}
