package com.catalyst.sonar.score.batch.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.sonar.api.config.Settings;
import org.sonar.api.database.DatabaseSession;
import org.sonar.api.measures.Metric;

import com.catalyst.sonar.score.ScorePlugin;
import com.catalyst.sonar.score.batch.util.TrophiesHelper;
import com.catalyst.sonar.score.util.SnapshotValue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrophiesHelperTest {
	private Settings settings;
	private Map<String, String> allProperties;
	private Map<String, String> mostProperties;
	private TrophiesHelper trophiesHelper;
	private DatabaseSession mockSession;
	private String nullMetric;
	private List<SnapshotValue> entries = new ArrayList<SnapshotValue>();
	private List<SnapshotValue> entries2 = new ArrayList<SnapshotValue>();
	private List<SnapshotValue> entries3 = new ArrayList<SnapshotValue>();
	private List<SnapshotValue> entries4 = new ArrayList<SnapshotValue>();
	private List<SnapshotValue> smallEntries = new ArrayList<SnapshotValue>();
	private double requiredAmount;
	private double requiredAmount2;
	private double requiredAmount3;
	private double requiredAmount4;
	private int days;
	private int days2;
	private int days3;
	private int days4;
	private String name;
	private Query query;
	private String defaultValue;
	
	private SnapshotValue sh1;
	private SnapshotValue sh2;
	private SnapshotValue sh5;
	private SnapshotValue sh6;
	private BigDecimal bd1;
	private Date date1;
	private BigDecimal bd2;
	private Date date2;
	
	private SnapshotValue sh3;
	private SnapshotValue sh4;
	private BigDecimal bd3;
	private Date date3;
	private BigDecimal bd4;
	private BigDecimal bd5;
	private BigDecimal bd6;	
	private BigDecimal bd10;
	private BigDecimal bd11;
	private BigDecimal bd12;
	private BigDecimal bd13;
	private Date date10;
	private Date date11;
	private Date date12;
	private Date date13;	
	private SnapshotValue sh10;
	private SnapshotValue sh11;
	private SnapshotValue sh12;
	private SnapshotValue sh13;	
	
	private BigDecimal bd15;
	private BigDecimal bd16;
	private BigDecimal bd17;
	
	private Date date15;
	private Date date16;
	private Date date17;
	
	private SnapshotValue sh15;
	private SnapshotValue sh16;
	private SnapshotValue sh17;	
	
	private Date date4;
	private Date date5;
	private Date date6;
	
	private String pointsName;
	private String pointsKey;
	private Metric pointsMetric;
	private Metric.ValueType pointsType;
	
	private String complexityName;
	private String complexityKey;
	private Metric complexityMetric;
	private Metric.ValueType complexityType;

	private String linesName;
	private String linesKey;
	private Metric linesMetric;
	private Metric.ValueType linesType;
	
	private String classesName;
	private String classesKey;
	private Metric classessMetric;
	private String violationsKey;
	private String violationsName;
	private Metric.ValueType violationsType;
	
	private BigDecimal bd20;
	private BigDecimal bd21;
	private BigDecimal bd22;
	private BigDecimal bd23;
	private Date date20;
	private Date date21;
	private Date date22;
	private Date date23;	
	private SnapshotValue sh20;
	private SnapshotValue sh21;
	private SnapshotValue sh22;
	private SnapshotValue sh23;	
	
	private Metric violationsMetric;
		
	private Metric.ValueType classesType;
	private String trophyProperty = "sonar.score.projectTrophy";
	
	@Before
	public void setUp(){	 	
		mockSession = mock(DatabaseSession.class);
		settings = new Settings();
		trophiesHelper = new TrophiesHelper(settings);
		allProperties = new HashMap<String, String>();
		mostProperties = new HashMap<String,String>();
			
		requiredAmount = 50.0;
		requiredAmount2= 100.0;
		requiredAmount3 = 1000.0;
		requiredAmount4 = 500.0;
		days = 30;
		days2 = 15;
		days3 = 30;
		days4 = 900;
		name = "Points";
		defaultValue = null;
				
		bd1 = new BigDecimal(1200);
		bd2 = new BigDecimal(1500);
		date1 = new Date(1370464358000l); //6/5/2013
		date2 = new Date (1360099958000l); //2/15/2013
		sh1 = new SnapshotValue(bd1,date1); //new snapshotHistory (1200,6/5/2013)
		sh2 = new SnapshotValue(bd2,date2);//new snapshotHistory (1500, 2/5/2013)	
		entries.add(sh1);
		entries.add(sh2);
		
		bd3= new BigDecimal (500);
		bd4 = new BigDecimal (600);
		date3 = new Date(1370464358000l); //6/5/2013
		date4 = new Date (1360099958000l); //2/15/2013
		sh3 = new SnapshotValue(bd3,date3);
		sh4 = new SnapshotValue(bd4,date4);
		entries2.add(sh3);
		entries2.add(sh4);
		
		bd3= new BigDecimal (500);
		bd4 = new BigDecimal (600);
		date3 = new Date(1370464358000l); //6/5/2013
		date4 = new Date (1360099958000l); //2/15/2013
		sh3 = new SnapshotValue(bd3,date3);
		sh4 = new SnapshotValue(bd4,date4);
		entries2.add(sh3);
		entries2.add(sh4);
		
		bd5= new BigDecimal (500);
		bd6 = new BigDecimal (600);
		date5 = new Date(1370464358000l); //6/5/2013
		date6 = new Date (1360099958000l); //2/15/2013
		sh5 = new SnapshotValue(bd5,date5);
		sh6 = new SnapshotValue(bd6,date6);
		entries2.add(sh5);
		entries2.add(sh6);
			
		pointsKey = "Points";
		pointsName = "Points";
		pointsType = Metric.ValueType.INT;
		
		pointsMetric = new Metric.Builder(pointsKey, pointsName, pointsType).create();
		pointsMetric.setDirection(1);
		pointsMetric.setId(1);
		pointsMetric.setDomain("General");
		
		
		complexityKey = "Complexity";
		complexityName = "Complexity";
		complexityType = Metric.ValueType.INT;
		
		complexityMetric = new Metric.Builder(complexityKey, complexityName, complexityType).create();
		complexityMetric.setDirection(0);
		complexityMetric.setId(2);
		complexityMetric.setDomain("Complexity");
				
		linesKey = "Lines";
		linesName = "Lines";
		linesType = Metric.ValueType.INT;
		
		linesMetric = new Metric.Builder(linesKey, linesName, linesType).create();
		linesMetric.setDirection(-1);
		linesMetric.setId(3);
		linesMetric.setDomain("Size");
		
		classesKey = "Classes";
		classesName = "Classes";
		classesType = Metric.ValueType.INT;
		
		classessMetric = new Metric.Builder(classesKey, classesName, classesType).create();
		classessMetric.setDirection(3);
		classessMetric.setId(10);
		classessMetric.setDomain("General");
		
		violationsKey = "Violations";
		violationsName = "Violations";
		violationsType = Metric.ValueType.INT;
				
		violationsMetric = new Metric.Builder(violationsKey, violationsName, violationsType).create();
		violationsMetric.setDirection(-1);
		violationsMetric.setId(10);
		violationsMetric.setDomain("Rules");
			
		bd10= new BigDecimal (500);
		bd11= new BigDecimal (300);
		bd12= new BigDecimal (300);
		bd13= new BigDecimal (300);
		date10 = new Date(1370464358000l); //6/5/2013
		date11 = new Date(1367785958000l);//5/5/2013
		date12 = new Date(1362519158000l); //3/5/2013
		date13 = new Date (1360099958000l); //2/15/2013
		
		sh10 = new SnapshotValue(bd10,date10);
		sh11 = new SnapshotValue(bd11,date11);
		sh12 = new SnapshotValue(bd12,date12);
		sh13 = new SnapshotValue(bd13,date13);
		entries3.add(sh10);
		entries3.add(sh11);
		entries3.add(sh12);
		entries.add(sh13);
		
		bd15= new BigDecimal (500);
		bd16= new BigDecimal (500);
		bd17= new BigDecimal (500);
		
		date15 = new Date(1370464358000l); //6/5/2013
		date16 = new Date(1367785958000l);//5/5/2013
		date17 = new Date(1362519158000l); //3/5/2013
				
		sh15 = new SnapshotValue(bd15,date15);
		sh16 = new SnapshotValue(bd16,date16);
		sh17 = new SnapshotValue(bd17,date17);
		
		entries4.add(sh15);
		entries4.add(sh16);
		entries4.add(sh17);
		/////////////////////smaller values/////////////////////////
		bd20= new BigDecimal (25);
		bd21= new BigDecimal (20);
		bd22= new BigDecimal (20);
		bd23= new BigDecimal (80);
		date20 = new Date(1370464358000l); //6/5/2013
		date21 = new Date(1367785958000l);//5/5/2013
		date22 = new Date(1362519158000l); //3/5/2013
		date23 = new Date (1360099958000l); //2/15/2013
		
		sh20 = new SnapshotValue(bd20,date20);
		sh21 = new SnapshotValue(bd21,date21);
		sh22 = new SnapshotValue(bd22,date22);
		sh23 = new SnapshotValue(bd23,date23);
		smallEntries.add(sh20);
		smallEntries.add(sh21);
		smallEntries.add(sh22);
		smallEntries.add(sh23);
		
	
	}
	
	/**
	 * Testing that if a trophy doesn't exist for a given project, the 
	 * 'newTrophyForThisProject' returns true and that if a trophy does exist
	 * for a given project the method returns false
	 */
	@Test
	public void testNewTrophyForThisProject() {		 
		allProperties.put("sonar.core.version", "3.4.1");
		allProperties.put("sonar.score.projectTrophy", "Trophy1");
		settings.addProperties(allProperties);
		assertTrue(trophiesHelper.newTrophyForThisProject("Trophy2"));
		assertEquals(trophiesHelper.newTrophyForThisProject("Trophy1"), false);
	
	}

	
	/**
	 * Testing that the criteria is not met when a metric cannot be found.
	 * The criteriaMet method returns false.
	 */
	@Test
	public void testCriteriaNotMetWhenMetricNotFound(){
	when(mockSession.getSingleResult(query, defaultValue)).thenReturn(nullMetric);
	assertEquals(trophiesHelper.criteriaMet(entries, requiredAmount, days, name, mockSession), false);
	
	}

	/**
	 * Testing that when a metric with a direction of '1' (DIRECTION.BETTER) is found and the criteria
	 * is met, criteriaMet returns true
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundDirectionOne(){
	when(mockSession.getSingleResult(Metric.class, "name", pointsName, "enabled", true)).thenReturn(pointsMetric);
	assertEquals(trophiesHelper.criteriaMet(entries, requiredAmount, days, pointsName, mockSession), true);
	
	}

	/**
	 * Testing that when a metric with a direction of '0' (DIRECTION.NO_MEANING) is found and the criteria
	 * is met, criteriaMet returns true
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundDirectionZero(){
	when(mockSession.getSingleResult(Metric.class, "name",complexityName, "enabled", true)).thenReturn(complexityMetric);
	assertTrue(trophiesHelper.criteriaMet(entries2, requiredAmount2, days2, complexityName, mockSession));
	
	}

	/**
     *Testing that when a metric with a direction of '-1' (DIRECTION.WORSE) is found and the criteria
	 * is met, criteriaMet returns true
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundDirectionNegativeOne(){
		when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);	
		assertTrue(trophiesHelper.criteriaMet(entries2, requiredAmount3, days3, violationsName, mockSession));
 }
	
	/**
	 * Testing that when a metric with a direction of '-1' (DIRECTION.WORSE) is found and the criteria
	 * is not met, criteriaMet returns false 
	 * 
	 */
	@Test
	public void testCriteriaMetFalseWhenMetricFoundDirectionNegativeOne(){
		when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);	
		assertFalse(trophiesHelper.criteriaMet(smallEntries, requiredAmount, 1000, violationsName, mockSession));	
	}
	/**
	 * Testing that when the domain of a metric is 'size' and the direction is negative one, the direction will
	 * be set to 1
	 */
	@Test
	public void testChangeDirection(){
		when(mockSession.getSingleResult(Metric.class, "name",linesName, "enabled", true)).thenReturn(linesMetric);	
		assertFalse(trophiesHelper.criteriaMet(entries2, requiredAmount3, days3, linesName, mockSession));	
	}
	
	/**
	 * Testing that when the criteriaMet returns false when the next measure value for a metric with a direction of -1
	 * does not meet the criteria 
	 */
	@Test
	public void testCriteriaNotMetNextMeasureValue(){
		BigDecimal bd20= new BigDecimal (25);
		BigDecimal bd21= new BigDecimal (100);
		Date date20 = new Date(1370464358000l); //6/5/2013
		Date date21 = new Date(1367785958000l);//5/5/2013
		
		SnapshotValue sh20 = new SnapshotValue(bd20,date20);
		SnapshotValue sh21 = new SnapshotValue(bd21,date21);
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		info.add(sh20);
		info.add(sh21);
		when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);	
		assertFalse(trophiesHelper.criteriaMet(info, requiredAmount, 1, violationsName, mockSession));	
	}
	
	/** Testing that criteriaMet returns true when the last snapshot criteria is met and it is the next
	 * measure value */	
	
	@Test
	public void testCriteriaMetWhenNextMeasureValueIsTheLastIndex(){
	BigDecimal bd20= new BigDecimal (25); 
	BigDecimal bd21= new BigDecimal (24);
	
	Date date20 = new Date(1370464358000l); //6/5/2013
	Date date21 = new Date(1367785958000l);//5/5/2013
		
	SnapshotValue sh20 = new SnapshotValue(bd20,date20);
	SnapshotValue sh21 = new SnapshotValue(bd21,date21);
	SnapshotValue sh22 = new SnapshotValue(bd22,date22);
	List<SnapshotValue> info = new ArrayList<SnapshotValue>();
	info.add(sh20);
	info.add(sh21);
	
	int day = 1;
	when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);	
	assertTrue(trophiesHelper.criteriaMet(info, requiredAmount, day, violationsName, mockSession));
	}
	
	/**
	 * Testing that criteriaMet returns true when criteria is met between measure values that do not
	 * meet the criteria and the last measure value equals the required amount
	 */
	@Test
	public void testCriteriaMetWhenGoodMeasureValuesInMiddleAndLastMeasureValueEqualsRequiredAmount(){
		BigDecimal bd20= new BigDecimal (50); 
		BigDecimal bd21= new BigDecimal (25);
		BigDecimal bd22= new BigDecimal (25); 
		BigDecimal bd23= new BigDecimal (50);
		Date date20 = new Date(1370464358000l); //6/5/2013
		Date date21 = new Date(1367785958000l);//5/5/2013
		Date date22 = new Date(1360099958000l);//2/5/2013
		Date date23 = new Date(1360099958000l);//2/5/2013
		SnapshotValue sh20 = new SnapshotValue(bd20,date20);
		SnapshotValue sh21 = new SnapshotValue(bd21,date21);
		SnapshotValue sh22 = new SnapshotValue(bd22,date22);
		SnapshotValue sh23 = new SnapshotValue(bd23,date23);
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		info.add(sh20);
		info.add(sh21);
		info.add(sh22);
		info.add(sh23);
		int day = 30;
		when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);	
		assertTrue(trophiesHelper.criteriaMet(info, requiredAmount, day, violationsName, mockSession));	
	}
		
	/**
	 * Testing that when a metric of direction '0' is found and the criteria is not met,
	 * criteriaMet returns false.
	 */
	@Test 
	public void testCriteriaMetWhenMetricFoundDirectionZeroRequirementsNotMet(){
		when(mockSession.getSingleResult(Metric.class, "name",complexityName, "enabled", true)).thenReturn(complexityMetric);
		assertFalse(trophiesHelper.criteriaMet(entries2, requiredAmount3, days2, complexityName, mockSession));
	}

	/**
	 * Testing that when a metric is found but the direction is not 0,1 or -1, criteriaMet returns false
	 * 
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundButBadDirection(){
		when(mockSession.getSingleResult(Metric.class, "name",classesName, "enabled", true)).thenReturn(classessMetric);
		assertFalse(trophiesHelper.criteriaMet(entries2, requiredAmount3, days4, classesName, mockSession));	
	}

	/**
	 * Testing that when there is no build history, a trophy cannot be earned even though
	 * the criteria amount has been met (for direction '-1');
	 */
	@Test
	public void testCriteriaNotMetWhenThereIsNoBuildHistoryForDirectionNegativeOne(){
		//no build history
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		
		when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);
		assertFalse(trophiesHelper.criteriaMet(info, requiredAmount, days4, violationsName, mockSession));
	}
	
	/**
	 * Testing that when there is only one snapshot, a trophy cannot be earned for direction '-1'
	 * 
	 */
	@Test 
	public void testCriteriaNotMetWhenThereIsOnlyOneSnapshotForDirectionNegativeOne(){
		BigDecimal bd20= new BigDecimal (25);
		Date date20 = new Date(1370464358000l); //6/5/2013				
		SnapshotValue sh20 = new SnapshotValue(bd20,date20);
		
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		//only one snapshot
		info.add(sh20);
				
		when(mockSession.getSingleResult(Metric.class, "name",violationsName, "enabled", true)).thenReturn(violationsMetric);
		assertFalse(trophiesHelper.criteriaMet(info, requiredAmount, days4, violationsName, mockSession));
		
	}
	
	/**
	 * Testing that when there are no snapshots, a trophy cannot be earned for direction 1 and direction 0
	 */
	@Test 
	public void testCriteriaNotMetWhenThereIsNoBuildHistoryForDirectionOneAndZero(){
		//no build history
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
				
		when(mockSession.getSingleResult(Metric.class, "name",pointsName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(info, requiredAmount, days4, pointsName, mockSession));	
		when(mockSession.getSingleResult(Metric.class, "name",complexityName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(info,requiredAmount ,days4, complexityName, mockSession));
	
	}
	
	/**
	 * Testing that a the criteriaMet method returns false when there is only one snapshot a measure
	 * with direction 1
	 */
	@Test
	public void testCriteriaNotMetWhenThereOnlyOneSnapshotForDirectionOne(){
		BigDecimal bd20= new BigDecimal (1000);
		Date date20 = new Date(1370464358000l); //6/5/2013				
		SnapshotValue sh20 = new SnapshotValue(bd20,date20);
		
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		//only one snapshot
		info.add(sh20);
				
		when(mockSession.getSingleResult(Metric.class, "name",pointsName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(info, requiredAmount, days4, pointsName, mockSession));
	
	}
	
	/**
	 * Testing that when criteriaMetForLargerMeasureValue is called and only the first measure value
	 * is met, but the other requirement measures and dates are not met,
	 * the method returns false
	 */
	@Test
	public void testCriteriaNotMetWhenMetricFoundAndMeasureValuesEqualRequireAmount(){
		when(mockSession.getSingleResult(Metric.class, "name", pointsName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(entries3, requiredAmount4, days4, pointsName, mockSession));
				
	}
	/**
	 * Testing that the criteriaMet returns true for direction 1 and 0, when there is a value that doesn't meet the criteria
	 * somewhere in the middle of the build history, but after that time the criteria values and dates have been met
	 * and the trophy will be earned
	 */
	@Test
	public void testCriteriaMetWhenMiddleBuildValueBad(){
		BigDecimal bigDec1= new BigDecimal (1000);
		BigDecimal bigDec2= new BigDecimal (700);
		BigDecimal bigDec3= new BigDecimal (600);
		BigDecimal bigDec4= new BigDecimal (300); 
		BigDecimal bigDec5= new BigDecimal (800);
		BigDecimal bigDec6= new BigDecimal (1200);
		
		Date theDate1 = new Date(1370464358000l); //6/5/2013				
		Date theDate2 = new Date(1369945958000l); //5/30/2013
		Date theDate3 = new Date(1367181158000l); //4/28/2013
		Date theDate4 = new Date(1366489958000l); //4/20/2013
		Date theDate5 = new Date(1366403558000l);//4/19/2013
		Date theDate6 = new Date(1334867558000l);//4/19/2012		
		
		SnapshotValue snapShot1 = new SnapshotValue(bigDec1,theDate1);
		SnapshotValue snapShot2 = new SnapshotValue(bigDec2,theDate2);
		SnapshotValue snapShot3 = new SnapshotValue(bigDec3,theDate3); 
		SnapshotValue snapShot4 = new SnapshotValue(bigDec4,theDate4);
		SnapshotValue snapShot5 = new SnapshotValue(bigDec5,theDate5);
		SnapshotValue snapShot6 = new SnapshotValue(bigDec6,theDate6);
		
		
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		info.add(snapShot1);
		info.add(snapShot2);
		info.add(snapShot3);
		info.add(snapShot4);
		info.add(snapShot5);
		info.add(snapShot6);
		int requiredAmtForOne = 500;
		int daysForOne = 90;
				
		when(mockSession.getSingleResult(Metric.class, "name",pointsName, "enabled", true)).thenReturn(pointsMetric);
		assertTrue(trophiesHelper.criteriaMet(info,requiredAmtForOne ,daysForOne, pointsName, mockSession));
		when(mockSession.getSingleResult(Metric.class, "name",complexityName, "enabled", true)).thenReturn(pointsMetric);
		assertTrue(trophiesHelper.criteriaMet(info,requiredAmtForOne ,daysForOne, complexityName, mockSession));
	}
	/**
	 * Testing criteriaMet returns true, if the criteria is met before there is criteria that wasn't met
	 */
	@Test
	public void testCriteriaMetWhenCriteriaMetAfterBadCriteria(){
	BigDecimal bigDec1= new BigDecimal (1000);
	BigDecimal bigDec2= new BigDecimal (700);
	BigDecimal bigDec3= new BigDecimal (600);
	BigDecimal bigDec4= new BigDecimal (300);
	BigDecimal bigDec5= new BigDecimal (800);
	BigDecimal bigDec6= new BigDecimal (1200);
	BigDecimal bigDec7 = new BigDecimal (100);
	
	Date theDate1 = new Date(1370464358000l); //6/5/2013				
	Date theDate2 = new Date(1369945958000l); //5/30/2013
	Date theDate3 = new Date(1367181158000l); //4/28/2013
	Date theDate4 = new Date(1366489958000l); //4/20/2013
	Date theDate5 = new Date(1366403558000l);//4/19/2013
	Date theDate6 = new Date(1334867558000l);//4/19/2012	
	Date theDate7 = new Date(1327008758000l);//1/19/2012
	
	SnapshotValue snapShot1 = new SnapshotValue(bigDec1,theDate1);
	SnapshotValue snapShot2 = new SnapshotValue(bigDec2,theDate2);
	SnapshotValue snapShot3 = new SnapshotValue(bigDec3,theDate3);
	SnapshotValue snapShot4 = new SnapshotValue(bigDec4,theDate4);
	SnapshotValue snapShot5 = new SnapshotValue(bigDec5,theDate5);
	SnapshotValue snapShot6 = new SnapshotValue(bigDec6,theDate6);
	SnapshotValue snapShot7 = new SnapshotValue(bigDec7,theDate7);
	
	
	List<SnapshotValue> info = new ArrayList<SnapshotValue>();
	info.add(snapShot1);
	info.add(snapShot2);
	info.add(snapShot3);
	info.add(snapShot4);
	info.add(snapShot5);
	info.add(snapShot6);
	info.add(snapShot7);
	int requiredAmtForOne = 500;
	int daysForOne = 90; 
			
	when(mockSession.getSingleResult(Metric.class, "name",pointsName, "enabled", true)).thenReturn(pointsMetric);
	assertTrue(trophiesHelper.criteriaMet(info,requiredAmtForOne ,daysForOne, pointsName, mockSession));
	when(mockSession.getSingleResult(Metric.class, "name",complexityName, "enabled", true)).thenReturn(pointsMetric);
	assertTrue(trophiesHelper.criteriaMet(info,requiredAmtForOne ,daysForOne, complexityName, mockSession));
	
	}
	
	/**
	 * Testing that criteriaMet returns false for direction 1 and 0 when the criteria has not been met and when
	 * the next measure value is equal to the required amount
	 */
	@Test
	public void testCriteriaNotMetNextMeasureValueEqualToRequiredAmount(){
		BigDecimal bigDec1= new BigDecimal (1000);
		BigDecimal bigDec2= new BigDecimal (500);
		BigDecimal bigDec3= new BigDecimal (300);
				
		Date theDate1 = new Date(1370464358000l); //6/5/2013				
		Date theDate2 = new Date(1376857958000l);//02/18/2013 
		Date theDate3 = new Date(1327008758000l);//12/19/2011 
		
		SnapshotValue snapShot1 = new SnapshotValue(bigDec1,theDate1);
		SnapshotValue snapShot2 = new SnapshotValue(bigDec2,theDate2);
		SnapshotValue snapShot3 = new SnapshotValue(bigDec3,theDate3);
		
		List<SnapshotValue> info = new ArrayList<SnapshotValue>();
		info.add(snapShot1);
		info.add(snapShot2);
		info.add(snapShot3); 
				
		int requiredAmtForOne = 500; 
		int daysForOne = 600; 
				
		when(mockSession.getSingleResult(Metric.class, "name",pointsName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(info,requiredAmtForOne ,daysForOne, pointsName, mockSession));
		when(mockSession.getSingleResult(Metric.class, "name",complexityName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(info,requiredAmtForOne ,daysForOne, complexityName, mockSession));	 
	}
	
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Testing that if the trophy property exists for a given project,
	 * the trophyPropertyExists method returns true.
	 */
	@Test
	public void testTrophyPropertyExists(){
		//allProperties.put("sonar.core.version", "3.4.1");
		allProperties.put("sonar.score.projectTrophy", "Trophy1");
		settings.addProperties(allProperties);
		assertTrue(trophiesHelper.trophyPropertyExists(trophyProperty));		
	}
	
	/**
	 * Testing that if the trophy property does not exist, the trophyPropertyExists method returns
	 * false
	 */
	@Test
	public void testTrophyPropertyDoesNotExist(){
		//mostProperties.put("sonar.core.version", "property info");
		mostProperties.put("sonar.score.sonar.profile.java", "property info");
		settings.addProperties(mostProperties);
		assertFalse(trophiesHelper.trophyPropertyExists(trophyProperty));
	}

	@Test
	public void testTrophyPropertyDoesExistButValueFieldIsEmpty(){		
		mostProperties.put("sonar.core.version", "info");
		//mostProperties.put("sonar.score.projectTrophy",null);
		settings.addProperties(mostProperties);
		assertFalse(trophiesHelper.trophyPropertyExists(trophyProperty));
	
	}

	@Test
	public void testTrophyPropertyDoesNotExistAndValueFieldsEmpty(){
		
		mostProperties.put("sonar.core.version", null);
		mostProperties.put("sonar.otherProperty",null);
		settings.addProperties(mostProperties);
		//assertFalse(trophiesHelper.trophyPropertyExists(trophyProperty));
	
	}


}
