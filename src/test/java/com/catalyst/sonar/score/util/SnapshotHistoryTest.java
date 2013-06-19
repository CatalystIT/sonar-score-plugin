package com.catalyst.sonar.score.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class SnapshotHistoryTest {
	
	
	private Date buildDate = new Date(1371193200000l);	
	private BigDecimal measureValue = new BigDecimal(500.00);
	private SnapshotHistory sh = new SnapshotHistory (measureValue, buildDate);
	private SnapshotHistory snapshot1;
	@Before
	public void setUp(){
	sh.setBuildDate(buildDate)	;
	sh.setMeasureValue(measureValue);
	snapshot1 = new SnapshotHistory (new BigDecimal(9090), new Date(756267889000l));
	}
	
	
/**
 * Testing that the build date returned when calling the getBuildDate method
 */
	@Test
	public void testGetBuildDate(){
		assertSame(buildDate, sh.getBuildDate());
	}

/**
 * Testing that the measure value is returned when calling the getMeasureValue method
 */
	@Test
	public void testGetMeasureValue(){
		assertSame(measureValue, sh.getMeasureValue());
	}

	@Test
	public void testEqualWhenObjectIsNull(){
		Object obj = null;
		assertEquals(sh.equals(obj), false);
	}

	@Test
	public void testEqualWhenNotInstanceOfSnapshotHistory(){
		String myString = "test";
		assertEquals(sh.equals(myString), false);
	}
	
	@Test
	public void testEqualWhenInstanceOfSnapshotHistory(){
		SnapshotHistory snapshot = new SnapshotHistory(null,null);
		//assertEquals(sh.equals(snapshot),false); 
	}

	
	/**
	 * If the build date is null when testing for SnapshotHistory equality, the overridden
	 * equals method returns false
	 */
	@Test
	public void testSnapshotHistoryBuildDateNullEquality(){
		SnapshotHistory snapshotTest = new SnapshotHistory(null, null);
		assertEquals(snapshotTest.equals(snapshot1), false);
	}
	
	/**
	 * Testing that if the build date is null and the other build date is null
	 * when testing for SnapshotHistory equality, the overridden equals
	 * method returns false
	 */
	@Test
	public void testSnapshotEqualityWhenBuildDatesNull(){
		SnapshotHistory snapshotTest = new SnapshotHistory(new BigDecimal (500),null );
		SnapshotHistory snapshotTest1 = new SnapshotHistory(null, null);
		assertEquals(snapshotTest.equals(snapshotTest1), false);
	}
	
	/**
	 * Testing that if the build dates are different, the SnapshotHistory
	 * objects are not eql.  Equals method returns false.
	 */
	@Test
	public void testDifferentBuildDatesSnapshotInEquality(){
		SnapshotHistory snapshot1 = new SnapshotHistory(new BigDecimal (500), new Date (774584689000l));
		SnapshotHistory snapshot2 = new SnapshotHistory(new BigDecimal (500), new Date (756267889000l));
		assertEquals(snapshot1.equals(snapshot2), false);
	}
	
	/**
	 * Testing that if two snapshots have the same build date, but the measure value is
	 * null, then the two snapshot history objects are not equal.
	 */
	@Test
	public void testSameBuildDateNullMeasureValue(){
		SnapshotHistory snapshot1 = new SnapshotHistory(null, new Date (774584689000l));
		SnapshotHistory snapshot2 = new SnapshotHistory(new BigDecimal(678), new Date (774584689000l));
		assertEquals(snapshot1.equals(snapshot2), false);	
	}
	
	/**
	 * Testing that when the measure values are null and the build dates are equal,
	 * the snapshot history objects are equal
	 */
	@Test
	public void testSnapshotHistoryEqualityWhenBuildDatesSameAndValuesAreNull(){
		SnapshotHistory snapshot1 = new SnapshotHistory(null, new Date (774584689000l));
		SnapshotHistory snapshot2 = new SnapshotHistory(null, new Date (774584689000l));
		assertEquals(snapshot1.equals(snapshot2), true);	

	}
	
	/**
	 * Testing that when testing for SnapshotHistory object equality,
	 * if the two snapshot objects are equal, the overridden equals method in the
	 * SnapshotHistory class returns true
	 */
	@Test
	public void testSnapshotObjectEquality(){
		assertEquals(snapshot1.equals(snapshot1), true);
	}

}
