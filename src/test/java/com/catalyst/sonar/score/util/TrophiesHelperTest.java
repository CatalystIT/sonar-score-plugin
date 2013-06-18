package com.catalyst.sonar.score.util;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrophiesHelperTest {
	private Settings settings;
	private Map<String, String> allProperties;
	private TrophiesHelper trophiesHelper;
	private DatabaseSession mockSession;
	private String nullMetric;
	private List<SnapshotHistory> entries = new ArrayList<SnapshotHistory>();
	private List<SnapshotHistory> entries2 = new ArrayList<SnapshotHistory>();
	private List<SnapshotHistory> entries3 = new ArrayList<SnapshotHistory>();
	private List<SnapshotHistory> entries4 = new ArrayList<SnapshotHistory>();
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
	
	private SnapshotHistory sh1;
	private SnapshotHistory sh2;
	private SnapshotHistory sh5;
	private SnapshotHistory sh6;
	private BigDecimal bd1;
	private Date date1;
	private BigDecimal bd2;
	private Date date2;
	
	private SnapshotHistory sh3;
	private SnapshotHistory sh4;
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
	private SnapshotHistory sh10;
	private SnapshotHistory sh11;
	private SnapshotHistory sh12;
	private SnapshotHistory sh13;	
	
	
	
	private BigDecimal bd15;
	private BigDecimal bd16;
	private BigDecimal bd17;
	
	private Date date15;
	private Date date16;
	private Date date17;
	
	private SnapshotHistory sh15;
	private SnapshotHistory sh16;
	private SnapshotHistory sh17;
		
	
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
	
	private Metric.ValueType classesType;
	
	@Before
	public void setUp(){	 	
		mockSession = mock(DatabaseSession.class);
		settings = new Settings();
		trophiesHelper = new TrophiesHelper(settings);
		allProperties = new HashMap<String, String>();
		allProperties.put("sonar.core.version", "3.4.1");
		allProperties.put("sonar.score.projectTrophy", "Trophy1");
		settings.addProperties(allProperties);
		
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
		sh1 = new SnapshotHistory(bd1,date1); //new snapshotHistory (1200,6/5/2013)
		sh2 = new SnapshotHistory(bd2,date2);//new snapshotHistory (1500, 2/5/2013)	
		entries.add(sh1);
		entries.add(sh2);
		
		bd3= new BigDecimal (500);
		bd4 = new BigDecimal (600);
		date3 = new Date(1370464358000l); //6/5/2013
		date4 = new Date (1360099958000l); //2/15/2013
		sh3 = new SnapshotHistory(bd3,date3);
		sh4 = new SnapshotHistory(bd4,date4);
		entries2.add(sh3);
		entries2.add(sh4);
		
		bd3= new BigDecimal (500);
		bd4 = new BigDecimal (600);
		date3 = new Date(1370464358000l); //6/5/2013
		date4 = new Date (1360099958000l); //2/15/2013
		sh3 = new SnapshotHistory(bd3,date3);
		sh4 = new SnapshotHistory(bd4,date4);
		entries2.add(sh3);
		entries2.add(sh4);
		
		bd5= new BigDecimal (500);
		bd6 = new BigDecimal (600);
		date5 = new Date(1370464358000l); //6/5/2013
		date6 = new Date (1360099958000l); //2/15/2013
		sh5 = new SnapshotHistory(bd5,date5);
		sh6 = new SnapshotHistory(bd6,date6);
		entries2.add(sh5);
		entries2.add(sh6);
			
		pointsKey = "Points";
		pointsName = "Points";
		pointsType = Metric.ValueType.INT;
		
		pointsMetric = new Metric.Builder(pointsKey, pointsName, pointsType).create();
		pointsMetric.setDirection(1);
		pointsMetric.setId(1);
		
		
		complexityKey = "Complexity";
		complexityName = "Complexity";
		complexityType = Metric.ValueType.INT;
		
		complexityMetric = new Metric.Builder(complexityKey, complexityName, complexityType).create();
		complexityMetric.setDirection(0);
		complexityMetric.setId(2);
				
		linesKey = "Lines";
		linesName = "Lines";
		linesType = Metric.ValueType.INT;
		
		linesMetric = new Metric.Builder(linesKey, linesName, linesType).create();
		linesMetric.setDirection(-1);
		linesMetric.setId(3);
		
		classesKey = "Classes";
		classesName = "Classes";
		classesType = Metric.ValueType.INT;
		
		classessMetric = new Metric.Builder(classesKey, classesName, classesType).create();
		classessMetric.setDirection(3);
		classessMetric.setId(10);
			
		bd10= new BigDecimal (500);
		bd11= new BigDecimal (300);
		bd12= new BigDecimal (300);
		bd13= new BigDecimal (300);
		date10 = new Date(1370464358000l); //6/5/2013
		date11 = new Date(1367785958000l);//5/5/2013
		date12 = new Date(1362519158000l); //3/5/2013
		date13 = new Date (1360099958000l); //2/15/2013
		
		sh10 = new SnapshotHistory(bd10,date10);
		sh11 = new SnapshotHistory(bd11,date11);
		sh12 = new SnapshotHistory(bd12,date12);
		sh13 = new SnapshotHistory(bd13,date13);
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
				
		sh15 = new SnapshotHistory(bd15,date15);
		sh16 = new SnapshotHistory(bd16,date16);
		sh17 = new SnapshotHistory(bd17,date17);
		
		entries4.add(sh15);
		entries4.add(sh16);
		entries4.add(sh17);
		
		
	
	}
	
	/**
	 * Testing that if a trophy doesn't exist for a given project, the 
	 * 'newTrophyForThisProject' returns true and that if a trophy does exist
	 * for a given project the method returns false
	 */
	@Test
	public void testNewTrophyForThisProject() {		
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
	 * Testing that when a metric with a direction of '-1' (DIRECTION.WORSE) is found and the criteria
	 * is met, criteriaMet returns true
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundDirectionNegativeOne(){
		when(mockSession.getSingleResult(Metric.class, "name",linesName, "enabled", true)).thenReturn(linesMetric);	
		assertTrue(trophiesHelper.criteriaMet(entries2, requiredAmount3, days3, linesName, mockSession));
	}

	/**
	 * Tests that when a metric of direction '1' is found and the criteria is not met,
	 * criteriaMet returns false
	 */
	@Test 
	public void testCriteriaMetWhenMetricFoundDirectionNegativeOneRequirementNotMet(){
		when(mockSession.getSingleResult(Metric.class, "name",linesName, "enabled", true)).thenReturn(linesMetric);	
		assertFalse(trophiesHelper.criteriaMet(entries2, requiredAmount, days3, linesName, mockSession));
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
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundButBadDirection(){
		when(mockSession.getSingleResult(Metric.class, "name",classesName, "enabled", true)).thenReturn(classessMetric);
		assertFalse(trophiesHelper.criteriaMet(entries2, requiredAmount3, days4, classesName, mockSession));	
	}
///////////////////////////////////////////////////////////////
	/**
	 * Testing that when criteriaMetForLargerMeasureValue is called and only the first measure value
	 * is met, but the other requirement measures and dates are not met,
	 * the method returns false
	 */
	@Test
	public void testCriteriaMetWhenMetricFoundAndMeasureValuesEqualRequireAmount(){
		when(mockSession.getSingleResult(Metric.class, "name", pointsName, "enabled", true)).thenReturn(pointsMetric);
		assertFalse(trophiesHelper.criteriaMet(entries3, requiredAmount4, days4, pointsName, mockSession));
				
	}
	////////////////////////////////////////////////////////////////////////////

}
