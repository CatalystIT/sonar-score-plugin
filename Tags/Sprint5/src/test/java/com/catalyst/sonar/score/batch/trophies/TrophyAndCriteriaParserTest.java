/**
 * 
 */
package com.catalyst.sonar.score.batch.trophies;
import static com.catalyst.sonar.score.batch.trophies.TrophyAndCriteriaParser.*; 
import static org.mockito.Mockito.*;
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
	private String nullString = null;
	private String notMatch = "asf";
	TrophyAndCriteriaParser trophyAndCriteriaParser;
	private Property mockProperty;
	private Settings testSettings;
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
		testSettings = new Settings();
		trophyAndCriteriaParser.setSettings(testSettings);
		//trophyAndCriteriaParser.setProperty(mockProperty);
		testTrophy = new Trophy(testTrophyName);
		testCriteria = new Criteria(testMetric, testReqdAmt, testDays);
		testCriteriaWithWeeksInput = new Criteria(testMetricWithWeeksInput, testReqdAmtWithWeeksInput, testDaysWithWeeksInput);
		//trophies
		testTrophy.addCriteria(testCriteria);
	}
	
	@Test
	public void testGetGlobalProperty() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("hi", "hello");
		assertEquals("", trophyAndCriteriaParser.getGlobalProperty(testSettings));
		map.put(GLOBALPROPERTYKEY, "hi");
		testSettings.setProperties(map);
		assertEquals("hi", trophyAndCriteriaParser.getGlobalProperty(testSettings));
	}
	
	@Test
	public void testExtractTrophyName() {
		assertEquals(testTrophy, TrophyAndCriteriaParser.extractTrophyName(testString));
		
	}

	@Test
	public void testParseCriteriaOnMetric(){
		assertEquals(testCriteria.getMetric(), TrophyAndCriteriaParser.parseCriteria(testCriteriaStringWithDays).getMetric());
		
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
	
	@Test
	public void testParseTrophiesNullStringList(){
		assertEquals(0, TrophyAndCriteriaParser.parseTrophies(nullString).size());
	}
	
	@Test
	public void testParseTrophiesStringListWithBadString(){
		TrophySet set1 = TrophyAndCriteriaParser.parseTrophies(testPropertyValue);
		TrophySet set2 = TrophyAndCriteriaParser.parseTrophies(testPropertyValue + "," + notMatch);
		assertEquals(set1.size(), set2.size());
	}
}
