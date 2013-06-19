/**
 * 
 */
package com.catalyst.sonar.score.batch.trophies;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.sonar.api.Property;
import org.sonar.api.config.Settings;

import com.catalyst.sonar.score.batch.trophies.Criteria;
import com.catalyst.sonar.score.batch.trophies.Trophy;
import com.catalyst.sonar.score.batch.trophies.TrophyAndCriteriaParser;

/**
 * @author lsajeev
 *
 */
public class TrophyAndCriteriaParserTest {
	// Property strings
	private String GlobalPropertyKey = "sonar.score.Trophy";
	private String testPropertyValue = "GreatCode{Coverage;90%;5d},GreatCode{Points;1000;2w},Violations{violations;10;3w}";
	private String testString = "GreatCode{Coverage;90%;2d}";
	private String testCriteriaStringWithDays = "Coverage;90%;2d}";
	private String testCriteriaStringWithWeeksInput = "Points;1000;2W}";
	TrophyAndCriteriaParser trophyAndCriteriaParser;
	private Property mockProperty;
	private Settings mockSettings;
	//trophy
	Trophy testTrophy;
	private String testTrophyName = "GreatCode";
	//criteria
	Criteria testCriteria;
	private String testMetric = "Coverage";
	private double testReqdAmt = 90;
	private int testDays = 2;
	Criteria testCriteriaWithWeeksInput;
	private String testMetricWithWeeksInput = "Points";
	private double testReqdAmtWithWeeksInput = 1000;
	private int testDaysWithWeeksInput = 14;
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		trophyAndCriteriaParser = new TrophyAndCriteriaParser();
		mockProperty = mock(Property.class);
		mockSettings = mock(Settings.class);
		trophyAndCriteriaParser.setSettings(mockSettings);
		//trophyAndCriteriaParser.setProperty(mockProperty);
		testTrophy = new Trophy(testTrophyName);
		testCriteria = new Criteria(testMetric, testReqdAmt, testDays);
		testCriteriaWithWeeksInput = new Criteria(testMetricWithWeeksInput, testReqdAmtWithWeeksInput, testDaysWithWeeksInput);
		//trophies
		testTrophy.addCriteria(testCriteria);
	}
	
	@Test
	public void testExtractTrophyName() {
		assertEquals(testTrophy, TrophyAndCriteriaParser.extractTrophyName(testString));
		
	}

	@Test
	public void testParseCriteriaOnMetric(){
	//	assertEquals(testCriteria.getMetric(), TrophyAndCriteriaParser.parseCriteria(testCriteriaStringWithDays).getMetric());
		
	}
	
	@Test
	public void testParseCriteriaOnRequiredAmt(){
		assertEquals(testCriteria.getRequiredAmt(), TrophyAndCriteriaParser.parseCriteria(testCriteriaStringWithDays).getRequiredAmt());
		
	}
	
	@Test
	public void testParseCriteriaOnDays()throws IllegalArgumentException, NumberFormatException{
		assertEquals(testCriteria.getDays(), TrophyAndCriteriaParser.parseCriteria(testCriteriaStringWithDays).getDays());
		
	}
	@Test
	public void testParseCriteriaOnWeeksInput() throws IllegalArgumentException, NumberFormatException{
		assertEquals(testCriteriaWithWeeksInput.getDays(), TrophyAndCriteriaParser.parseCriteria(testCriteriaStringWithWeeksInput).getDays());
		
	}

	@Test
	public void testParseTrophiesAndGetTrophy(){
		assertEquals(testTrophy, TrophyAndCriteriaParser.parseTrophies(testString).get(testTrophy));
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
}
